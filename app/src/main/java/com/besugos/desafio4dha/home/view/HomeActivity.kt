package com.besugos.desafio4dha.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.detail.DetailActivity
import com.besugos.desafio4dha.edit.view.AddActivity
import com.besugos.desafio4dha.home.model.GameModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class HomeActivity : AppCompatActivity() {

    private lateinit var homeActivity: String

    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: FirebaseRecyclerAdapter<*, *>? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeActivity = this.localClassName

        recyclerView = findViewById(R.id.list)

        auth = FirebaseAuth.getInstance()

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        linearLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.setHasFixedSize(true)
        Load() //method containing  FirebaseRecyclerAdapter
    }

    private fun Load() {
        val query: Query = FirebaseDatabase.getInstance().reference.child(auth.currentUser!!.uid)
        val options = FirebaseRecyclerOptions.Builder<GameModel>()
            .setQuery(
                query
            ) { snapshot ->
                GameModel(
//                    snapshot.child("id").value.toString(),
                    snapshot.child("name").value.toString(),
                    snapshot.child("year").value.toString(),
                    snapshot.child("desc").value.toString(),
                    snapshot.child("picPathString").value.toString()
                )
            }
            .build()

        adapter = object : FirebaseRecyclerAdapter<GameModel, HomeViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return HomeViewHolder(view)
            }

            override fun onBindViewHolder(holder: HomeViewHolder, position: Int, model: GameModel) {
                holder.setTxtTitle(model.name)
                holder.setTxtDesc(model.year)
                holder.setPic(model.picPathString)
                holder.root.setOnClickListener {
                    val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                    with(intent) {
                        putExtra("NAME", model.name)
                        putExtra("DESC", model.desc)
                        putExtra("YEAR", model.year)
                        putExtra("PIC", model.picPathString)
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
        recyclerView!!.adapter = adapter
        adapter!!.startListening()
    }
}