package com.besugos.desafio4dha.home.view

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.R

class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var root: LinearLayout
    var txtTitle: TextView
    var txtDesc: TextView

    fun setTxtTitle(string: String?) {
        txtTitle.text = string
        Log.d("APP", "MyViewHolder -> Title = " + string)
    }

    fun setTxtDesc(string: String?) {
        txtDesc.text = string
        Log.d("APP", "MyViewHolder -> Title = " + string)
    }

    init {
        root = itemView.findViewById(R.id.list_root)
        txtTitle = itemView.findViewById(R.id.list_title)
        txtDesc = itemView.findViewById(R.id.list_desc)
    }
}