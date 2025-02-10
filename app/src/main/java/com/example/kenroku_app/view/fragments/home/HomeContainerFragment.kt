package com.example.kenroku_app.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.kenroku_app.R
import com.example.kenroku_app.viewmodel.HomeContainerViewModel

class HomeContainerFragment : Fragment() {
    private lateinit var homeContainerViewModel: HomeContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_home_container, container, false)

        // ViewModelを初期化
        homeContainerViewModel = ViewModelProvider(this)[HomeContainerViewModel::class.java]

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_home)
                as NavHostFragment
        val navController = navHostFragment.navController

        // 初回のみNavGraphをセット
        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.home_navigation)
        }

        return rootView
    }

    fun moveToState(state: HomeContainerViewModel.HomeState) {
        homeContainerViewModel.currentState = state
        val navController = findNavController()

        when (state) {
            HomeContainerViewModel.HomeState.TOURIST_SELECTION -> {
//                navController.navigate(R.id.action_navigation_select_map_to_navigation_home)
            }
            HomeContainerViewModel.HomeState.MAIN -> {
                navController.navigate(R.id.action_navigation_select_map_to_navigation_home)
            }
            HomeContainerViewModel.HomeState.DETAIL -> {
                navController.navigate(R.id.action_navigation_home_to_markerDetailFragment)
            }
        }
    }
}
