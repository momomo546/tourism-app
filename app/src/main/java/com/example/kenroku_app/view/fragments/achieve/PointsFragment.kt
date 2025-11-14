package com.example.kenroku_app.view.fragments.achieve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kenroku_app.R
import com.example.kenroku_app.viewmodel.PointsViewModel

class PointsFragment:Fragment() {
    private val pointsViewModel: PointsViewModel by viewModels()

    private lateinit var totalPointsTextView: TextView
    private lateinit var points1TextView: TextView
    private lateinit var points2TextView: TextView
    private lateinit var points3TextView: TextView
    private lateinit var rankTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_points, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalPointsTextView = view.findViewById(R.id.total_points)
        points1TextView = view.findViewById(R.id.points_1)
        points2TextView = view.findViewById(R.id.points_2)
        points3TextView = view.findViewById(R.id.points_3)
        rankTextView = view.findViewById(R.id.rank)

        var currentPoints1 = 0
        var totalPoints1 = 0
        var currentPoints2 = 0
        var totalPoints2 = 0
        var currentPoints3 = 0
        var totalPoints3 = 0

        pointsViewModel.points1.observe(viewLifecycleOwner) { current ->
            currentPoints1 = current
            points1TextView.text = "1ポイントスポット: $currentPoints1/$totalPoints1"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.points1Total.observe(viewLifecycleOwner) { total ->
            totalPoints1 = total
            points1TextView.text = "1ポイントスポット: $currentPoints1/$totalPoints1"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.points2.observe(viewLifecycleOwner) { current ->
            currentPoints2 = current
            points2TextView.text = "3ポイントスポット: $currentPoints2/$totalPoints2"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.points2Total.observe(viewLifecycleOwner) { total ->
            totalPoints2 = total
            points2TextView.text = "3ポイントスポット: $currentPoints2/$totalPoints2"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.points3.observe(viewLifecycleOwner) { current ->
            currentPoints3 = current
            points3TextView.text = "5ポイントスポット: $currentPoints3/$totalPoints3"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.points3Total.observe(viewLifecycleOwner) { total ->
            totalPoints3 = total
            points3TextView.text = "5ポイントスポット: $currentPoints3/$totalPoints3"
            totalPointsTextView.text = calculateTotalPoints()
        }

        pointsViewModel.rankText.observe(viewLifecycleOwner) { rankText ->
            rankTextView.text = rankText
        }
    }
    override fun onResume() {
        super.onResume()
        pointsViewModel.viewUpdate()
    }
    private fun calculateTotalPoints(): String {
        val points1 = pointsViewModel.points1.value ?: 0
        val points2 = pointsViewModel.points2.value ?: 0
        val points3 = pointsViewModel.points3.value ?: 0
        return "得点: "+(points1*1 + points2*3 + points3*5).toString()+"点"
    }
}