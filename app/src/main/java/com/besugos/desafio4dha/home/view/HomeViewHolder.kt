package com.besugos.desafio4dha.home.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var root: LinearLayout
    var txtTitle: TextView
    var txtDesc: TextView
    var image: ImageView

    fun setTxtTitle(string: String?) {
        txtTitle.text = string
    }

    fun setTxtDesc(string: String?) {
        txtDesc.text = string
    }

    fun setPic(string: String?) {
        val firebase = FirebaseStorage.getInstance()
        val storage = firebase.getReference("uploads")

        storage.child(string!!).downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(image)
        }
    }

    init {
        root = itemView.findViewById(R.id.list_root)
        txtTitle = itemView.findViewById(R.id.list_title)
        txtDesc = itemView.findViewById(R.id.list_desc)
        image = itemView.findViewById(R.id.ivPic)
    }
}
