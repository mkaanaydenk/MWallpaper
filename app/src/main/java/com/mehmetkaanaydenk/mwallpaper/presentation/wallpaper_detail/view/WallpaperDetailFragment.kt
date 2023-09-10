package com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.view

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mehmetkaanaydenk.mwallpaper.R
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpaperDetailBinding
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailEvent
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject


class WallpaperDetailFragment @Inject constructor(private val glide: RequestManager, private val wallpaperManager: WallpaperManager) : Fragment() {

    private var _binding: FragmentWallpaperDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WallpaperDetailViewModel
    private lateinit var bottomSheetDialog: BottomSheetDialog

    lateinit var imageUrl: String

    lateinit var imageBitmap : Bitmap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WallpaperDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWallpaperDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getString("wId")

        if (args!=null){

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

    fun getData(){

        val state = viewModel.state

        lifecycleScope.launch {

            state.collect(FlowCollector {

                imageUrl = it.wallpaperDetail?.largeImageURL.toString()

                glide.asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>(){
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

    fun openDialog(){

        val dialogInflater = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet,null)
        bottomSheetDialog.setContentView(dialogInflater)
        bottomSheetDialog.show()

        dialogInflater.findViewById<View>(R.id.set_wallpaper).setOnClickListener {

            bottomSheetDialog.dismiss()
            activity?.window?.decorView?.let { it1 -> Snackbar.make(it1.findViewById(R.id.constraint_layoutt),"Duvar kağıdı ayarlandı!", Snackbar.LENGTH_SHORT).show() }

            wallpaperManager.setBitmap(imageBitmap)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bottomSheetDialog.dismiss()
    }

}