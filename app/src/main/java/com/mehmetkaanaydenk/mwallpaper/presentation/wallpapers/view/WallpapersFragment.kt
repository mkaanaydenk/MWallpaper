package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mehmetkaanaydenk.mwallpaper.R
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpapersBinding
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.WallpapersEvent
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.WallpapersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WallpapersFragment() : Fragment() {


    private lateinit var dataBinding: FragmentWallpapersBinding
    private lateinit var viewModel: WallpapersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallpapers, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WallpapersViewModel::class.java]

        val state = viewModel.state


        lifecycleScope.launch {

           state.collect(){

               val wallpapers = it.wallpapers

               if (wallpapers.isNotEmpty()){
                   println(wallpapers.size)
                   println(wallpapers[0].id)
               }

           }

        }



    }



}