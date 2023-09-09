package com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mehmetkaanaydenk.mwallpaper.R
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpaperDetailBinding
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpapersBinding
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailEvent
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.WallpaperDetailViewModel
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.WallpapersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject


class WallpaperDetailFragment @Inject constructor(private val glide: RequestManager) : Fragment() {

    private var _binding: FragmentWallpaperDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WallpaperDetailViewModel

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

        binding.downloadButton.setOnClickListener {

            openDialog()

        }

    }

    fun getData(){

        val state = viewModel.state

        lifecycleScope.launch {

            state.collect(FlowCollector {

                glide.load(it.wallpaperDetail?.largeImageURL)
                    .into(binding.imageView)

            })

        }

    }

    fun openDialog(){

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet,null)
        bottomSheetDialog.setContentView(inflater)
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}