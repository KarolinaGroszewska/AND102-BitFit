package com.example.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class JournalAdapter(private val context: Context,val listener: JournalClickListener):
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>(){

    private val todoList = ArrayList<Journal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalAdapter.JournalViewHolder {
        return JournalViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: JournalAdapter.JournalViewHolder, position: Int) {
        val item = todoList[position]
        holder.title.text = item.title
        holder.title.isSelected = true
        holder.note.text = item.note
        holder.date.text = item.date
        holder.date.isSelected = true
        holder.todo_layout.setOnClickListener {
            listener.onItemClicked(todoList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateList(newList: List<Journal>){
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class JournalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val todo_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface JournalClickListener {
        fun onItemClicked(journal: Journal)
    }
}