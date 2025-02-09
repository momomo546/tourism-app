package com.example.kenroku_app.view.fragments.selectMap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kenroku_app.R

data class MapData(val imageResId: Int, val textId: Int, val mapPath: String)

class MapAdapter (
    private val data: List<MapData>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<MapAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //fragment_badge.xmlのレイアウトを取得
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_badge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val item = data[index]

        holder.imageView.setImageResource(item.imageResId)
        holder.textView.setText(item.textId)
        holder.itemView.setOnClickListener { onItemClick(item.mapPath) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_badge)
        val textView: TextView = itemView.findViewById(R.id.text_badge)
    }
}