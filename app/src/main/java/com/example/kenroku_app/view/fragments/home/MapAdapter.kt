package com.example.kenroku_app.view.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kenroku_app.R

data class MapData(val imageResId: Int, val textId: Int)

class MapAdapter (private val data: List<MapData>) : RecyclerView.Adapter<MapAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //fragment_badge.xmlのレイアウトを取得
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_badge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.imageView.setImageResource(item.imageResId)
        holder.textView.setText(item.textId)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_badge)
        val textView: TextView = itemView.findViewById(R.id.text_badge)
    }
}