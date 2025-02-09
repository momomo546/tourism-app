package com.example.kenroku_app.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kenroku_app.R

class MarkerDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // 先ほどのレイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_marker_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val markerId = arguments?.getString("markerId")
        val touristSpotId = arguments?.getString("touristSpotId")

        // マーカーIDに対応するリソースIDをマップから取得
        val resourceImg = getResources().getIdentifier("${touristSpotId}_img_${markerId}", "drawable", requireContext().packageName)
        val resourceText = getResources().getIdentifier("detail_info_${markerId}", "string", requireContext().packageName)

        val photoImageView = view.findViewById<ImageView>(R.id.photoImageView)
        val textTextView = view.findViewById<TextView>(R.id.textTextView)

//        val spotIdTest = "kenrokuen"
//
//        // Firebase Storageの参照を取得
//        val storage = FirebaseStorage.getInstance()
//        val storageRef = storage.reference
//        val imageRef = storageRef.child("$spotIdTest/image_marker_01") // 保存した画像のパス
//
//        // 画像のダウンロードURLを取得
//        imageRef.downloadUrl.addOnSuccessListener { uri ->
//            // Glideで画像をロード
//            Glide.with(this)
//                .load(uri)
//                .into(photoImageView)
//        }.addOnFailureListener {
//            // エラー時の処理
//            it.printStackTrace()
//        }

        // 写真とテキストを表示
        photoImageView.setImageResource(resourceImg)
        textTextView.text = getString(resourceText)

        val backButton = view.findViewById<TextView>(R.id.backButton)
        // 戻るボタンのクリックリスナー
        backButton.setOnClickListener {
            // 任意のフラグメントでNavControllerを取得する
            val navController = findNavController()

            // バックスタックに積まれたトランザクションをポップして前のフラグメントに戻る
            navController.popBackStack()
        }
    }
}