package com.besugos.desafio4dha.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.home.model.GameModel
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class HomeAdapter(private val context: Context): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

//    class HomeAdapter(private val dataSet: List<GameModel>, private val listener: (GameModel) -> Unit): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var dataList = mutableListOf<GameModel>()

    fun setListData(data:MutableList<GameModel>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_game_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val game = dataList[position]
        holder.bindView(game)
    }

    override fun getItemCount() = dataList.size

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imgThumb = itemView.findViewById<ImageView>(R.id.ivPic)
        private val txtName = itemView.findViewById<TextView>(R.id.tvName)
        private val txtYear = itemView.findViewById<TextView>(R.id.tvYear)
        private val firebase = FirebaseStorage.getInstance()
        private val storage = firebase.getReference("uploads")

        fun bindView(game: GameModel) {

            txtName.text = game.name
            txtYear.text = game.year
            storage.child("1611493936994.jpg").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(imgThumb)
            }
        }

    }
}