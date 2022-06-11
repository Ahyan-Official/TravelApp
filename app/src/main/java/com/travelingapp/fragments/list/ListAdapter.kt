package com.travelingapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.travelingapp.R
import com.travelingapp.model.User

import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.score_txt.text = "Score "+currentItem.score.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}