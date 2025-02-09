package com.example.kenroku_app.view.fragments.selectMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kenroku_app.R
import com.example.kenroku_app.databinding.FragmentSelectMapBinding

class SelectMapFragment : Fragment() {
    private var _binding: FragmentSelectMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val childFragment = MapListFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, childFragment)
        transaction.commit()

        return root
    }
}