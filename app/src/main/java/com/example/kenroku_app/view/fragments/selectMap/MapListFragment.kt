package com.example.kenroku_app.view.fragments.selectMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenroku_app.databinding.FragmentBadgeListBinding
import com.example.kenroku_app.model.repositories.data.TouristSpotData
import com.example.kenroku_app.viewmodel.MapListViewModel

class MapListFragment : Fragment() {

    private var _binding: FragmentBadgeListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val mapListViewModel =
            ViewModelProvider(this)[MapListViewModel::class.java]

        _binding = FragmentBadgeListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView
        val linearLayoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = linearLayoutManager

        mapListViewModel.mapList.observe(viewLifecycleOwner) {mapList ->
            val adapter = MapAdapter(mapList) { touristName ->
                TouristSpotData.touristSpotId = touristName
                val action = SelectMapFragmentDirections
                    .actionNavigationSelectMapToNavigationHome(touristName)
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}