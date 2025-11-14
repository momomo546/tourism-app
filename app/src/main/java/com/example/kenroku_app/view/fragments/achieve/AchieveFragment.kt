package com.example.kenroku_app.view.fragments.achieve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.kenroku_app.R
import com.example.kenroku_app.databinding.FragmentAchieveBinding
import com.example.kenroku_app.model.repositories.data.TouristSpotData
import com.example.kenroku_app.view.fragments.achieve.badge.BadgeListFragment
import com.example.kenroku_app.viewmodel.AchieveViewModel
import com.example.kenroku_app.viewmodel.PointsViewModel
import com.example.kenroku_app.viewmodel.activity.MainViewModel

class AchieveFragment : Fragment() {

    private var _binding: FragmentAchieveBinding? = null
    private lateinit var achieveViewModel: AchieveViewModel
    private lateinit var pointsViewModel: PointsViewModel
    private lateinit var mainViewModel: MainViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        achieveViewModel = ViewModelProvider(this)[AchieveViewModel::class.java]
        pointsViewModel = ViewModelProvider(this)[PointsViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        _binding = FragmentAchieveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val walkCountView: TextView = binding.numberOfSteps
        val checkPointView: TextView = binding.numberOfCheckpoints
        val visitCountView: TextView = binding.numberOfVisits
        achieveViewModel.checkPointText.observe(viewLifecycleOwner) {
            checkPointView.text = it
        }
        achieveViewModel.walkCountText.observe(viewLifecycleOwner) {
            walkCountView.text = it
        }
        achieveViewModel.visitCountText.observe(viewLifecycleOwner){
            visitCountView.text = it
        }
        if(TouristSpotData.touristSpotId == "yamanaka_onsen") {
            val pointsFragment = PointsFragment()
            val pointsTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            pointsTransaction.add(R.id.fragment_container_points, pointsFragment)
            pointsTransaction.commit()
        }

        val childFragment = BadgeListFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, childFragment)
        transaction.commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        achieveViewModel.viewUpdate()
    }
}