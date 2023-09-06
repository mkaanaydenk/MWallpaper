package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.mwallpaper.databinding.FragmentWallpapersBinding
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.WallpapersEvent
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.WallpapersViewModel
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.adapter.WallpapersRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class WallpapersFragment @Inject constructor(val adapter: WallpapersRecyclerAdapter)  : Fragment() {


    private lateinit var binding: FragmentWallpapersBinding
    private lateinit var viewModel: WallpapersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WallpapersViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWallpapersBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(),4)
        binding.wallpapersRecyclerView.adapter = adapter
        binding.wallpapersRecyclerView.layoutManager = layoutManager



        val state = viewModel.state

        val scope = CoroutineScope(Dispatchers.IO)

        binding.searchEditText.addTextChangedListener {

            scope.launch {

                delay(1000)
                viewModel.onEvent(WallpapersEvent.Search(it.toString()))
            }
        }


        lifecycleScope.launch {

            state.collect() {

                val wallpapers = it.wallpapers

                if (wallpapers.isNotEmpty()) {
                    adapter.wallpaperList = wallpapers

                }

            }

        }


    }


}