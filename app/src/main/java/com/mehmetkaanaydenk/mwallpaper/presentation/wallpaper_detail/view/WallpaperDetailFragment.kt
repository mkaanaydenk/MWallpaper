package com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.view

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mehmetkaanaydenk.mwallpaper.R
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpaperDetailBinding
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailEvent
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailViewModel
import com.mehmetkaanaydenk.mwallpaper.util.scaleAndSetWallpaper
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject


class WallpaperDetailFragment @Inject constructor(
    private val glide: RequestManager,
    private val wallpaperManager: WallpaperManager
) : Fragment() {

    private var _binding: FragmentWallpaperDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WallpaperDetailViewModel
    private lateinit var bottomSheetDialog: BottomSheetDialog

    lateinit var imageUrl: String

    lateinit var imageBitmap: Bitmap

    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WallpaperDetailViewModel::class.java]
        registerPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWallpaperDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getString("wId")

        if (args != null) {

            println(args)
            viewModel.onEvent(WallpaperDetailEvent.GetById(args))

        }

        getData()


        bottomSheetDialog = BottomSheetDialog(requireContext())


        binding.downloadButton.setOnClickListener {

            openDialog()

        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    fun getData() {

        val state = viewModel.state

        lifecycleScope.launch {

            state.collect(FlowCollector {

                imageUrl = it.wallpaperDetail?.largeImageURL.toString()

                glide.asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            binding.imageView.setImageBitmap(resource)
                            imageBitmap = resource

                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }


                    })

            })

        }

    }

    fun openDialog() {

        val dialogInflater =
            LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(dialogInflater)
        bottomSheetDialog.show()

        dialogInflater.findViewById<View>(R.id.set_wallpaper).setOnClickListener {

            bottomSheetDialog.dismiss()
            activity?.window?.decorView?.let { it1 ->
                Snackbar.make(
                    it1.findViewById(R.id.constraint_layoutt),
                    "Duvar kağıdı ayarlandı!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            getMetrics(imageBitmap)
        }

        dialogInflater.findViewById<View>(R.id.save_gallery).setOnClickListener {

            bottomSheetDialog.dismiss()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageToMediaStore(imageBitmap)
            } else {
                activity?.window?.let { it1 -> saveImageToFile(it1.decorView, imageBitmap) }
            }


        }

    }

    private fun registerPermission() {

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->

                if (result) {


                } else {
                    Toast.makeText(
                        requireContext(),
                        "Galeriye kaydetmek için izin gerekli!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

    }

    private fun saveImageToFile(view: View, bitmap: Bitmap) {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

                Snackbar.make(
                    view,
                    "Galeriye kayıt için izin gereklidir",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("İzin ver") {

                    //permission request
                    permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

                }.show()

            } else {
                //permission request
                permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            }

        } else {
            //write file
            Snackbar.make(
                requireActivity().findViewById(R.id.constraint_layoutt),
                "Galeriye kaydedildi!",
                Snackbar.LENGTH_SHORT
            ).show()

            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            val uuid = UUID.randomUUID()
            val displayName = uuid.toString()

            val imageFile = File(directory, "$displayName.png")

            if (!directory.isDirectory) {
                directory.mkdir()
            }

            if (directory.isDirectory) {

                imageFile.outputStream().use {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }
                imageFile.toURI()

            }

        }

    }

    private fun saveImageToMediaStore(bitmap: Bitmap) {

        Snackbar.make(
            requireActivity().findViewById(R.id.constraint_layoutt),
            "Galeriye kaydedildi!",
            Snackbar.LENGTH_SHORT
        ).show()

        val imageCollections = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val uuid = UUID.randomUUID()

        val displayName = uuid.toString()

        val imageDetails = ContentValues().apply {

            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

        }

        val resolver = context?.applicationContext?.contentResolver
        val imageContentUri = resolver?.insert(imageCollections, imageDetails)

        if (imageContentUri != null) {
            resolver.openOutputStream(imageContentUri, "w").use { os ->

                if (os != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
                }

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageDetails.clear()
                imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageContentUri, imageDetails, null, null)
            }

        }


    }

    private fun getMetrics(cBitmap: Bitmap) {

        cBitmap.scaleAndSetWallpaper(requireActivity(), wallpaperManager)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bottomSheetDialog.dismiss()
    }

}