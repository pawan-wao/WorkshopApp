package com.assignment.ui.feed

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.model.Workshop
import com.assignment.databinding.ItemWorkshopBinding
import com.bumptech.glide.Glide

class WorkshopAdapter(
    private val workshops: List<Workshop>,
    private val listeners: ViewListeners
) : RecyclerView.Adapter<WorkshopAdapter.WorkShopViewHolder>() {
    inner class WorkShopViewHolder(val binding: ItemWorkshopBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkShopViewHolder {
        val binding = ItemWorkshopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkShopViewHolder, position: Int) {
        val workshop = workshops[position]
        Glide.with(holder.itemView.context).load(workshop.imageLink).into(holder.binding.ivWorkshop)
        holder.binding.apply {
            tvWorkshopName.text = workshop.name
            tvDate.text = workshop.date

            if (workshop.applied == 1){
                btnApply.isEnabled = false
                btnApply.text = "Applied"
                btnApply.setBackgroundResource(R.drawable.button_applied_bg)
            }
            btnApply.setOnClickListener {
                listeners.onApply(btnApply,workshop.id)
            }
        }
    }
    override fun getItemCount(): Int = workshops.size
}

interface ViewListeners {
    fun onApply(btn: Button, id: Int)
}