package com.android.argan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ConsumptionAdapter(private val items: List<ConsumptionItem>,        private val onDeleteClick: (position: Int) -> Unit // Lambda to handle delete
) : RecyclerView.Adapter<ConsumptionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val btnDeleteItem: ImageView = view.findViewById(R.id.btnDeleteItem) // Get delete button


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_consumption, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvType.text = item.type
        holder.tvAmount.text = "مبلغ: ${item.amount.toInt()} تومان"
        holder.tvDate.text = "تاریخ: ${item.date}"
        holder.btnDeleteItem.setOnClickListener {

            val pos = holder.bindingAdapterPosition

            if(pos != RecyclerView.NO_POSITION){

                onDeleteClick(pos)

            }

        }

    }
}
