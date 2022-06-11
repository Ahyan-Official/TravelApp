package com.travelingapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.travelingapp.R
import com.travelingapp.model.room.AppLanguage


class LanguageAdapter(private val mContext: Context) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    var data: List<AppLanguage> = emptyList()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_language, parent, false))
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.lineOne.text = data[position].lang

        Log.e("uuu", "onBindViewHolder: "+data[position].lang )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lineOne: TextView = itemView.findViewById(R.id.line_one)

    }
}
