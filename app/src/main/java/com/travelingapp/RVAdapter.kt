package com.travelingapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.travelingapp.databinding.CellBinding

class RVAdapter(private val cell: ArrayList<Cell>, var taskk: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CellViewHolder(var viewBinding: CellBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as CellViewHolder
        itemViewHolder.viewBinding.id.text = cell[position].id
        itemViewHolder.viewBinding.continent.text = cell[position].continent
        itemViewHolder.viewBinding.name.text = cell[position].name
        itemViewHolder.viewBinding.langCode.text = cell[position].langCode

        Picasso.get().load(cell[position].flagImg).into(itemViewHolder.viewBinding.image)

        itemViewHolder.viewBinding.card.setOnClickListener {

            Log.e("lplp", "onBindViewHolder: "+taskk )

            if(taskk =="map"){
                val intent = Intent(it.context, MapsActivity::class.java)
                intent.putExtra("id", cell[position].id)

                it.context.startActivity(intent)
            }else if(taskk == "quiz"){
                val intent = Intent(it.context, QuizActivity::class.java)
                intent.putExtra("id", cell[position].id)

                it.context.startActivity(intent)
            }else if(taskk == "phrase"){
                val intent = Intent(it.context, PhraseActivity::class.java)
                intent.putExtra("id", cell[position].id)

                it.context.startActivity(intent)
            }


        }

    }


    override fun getItemCount(): Int {
        return cell.size
    }




}