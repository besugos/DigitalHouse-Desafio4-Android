package com.besugos.desafio4dha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.detail.DetailActivity
import com.besugos.desafio4dha.home.model.GameModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var edt: EditText? = null
    private var button: Button? = null
    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: FirebaseRecyclerAdapter<*, *>? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.et)
        edt = findViewById(R.id.etd)
        button = findViewById<Button>(R.id.btn)
        recyclerView = findViewById(R.id.list)

        auth = FirebaseAuth.getInstance()

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        button!!.setOnClickListener(View.OnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().reference.child("2FyxkYV7TlcEJ0jybjUAfUq91pT2").push()
            val map: MutableMap<String, Any?> = HashMap()
//            map["id"] = databaseReference.key
            map["title"] = editText!!.getText().toString()
            map["Description"] = edt!!.getText().toString()
            databaseReference.setValue(map)
        })
        linearLayoutManager = GridLayoutManager(this,2)
        recyclerView!!.setLayoutManager(linearLayoutManager)
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
        adapter = object : FirebaseRecyclerAdapter<GameModel, MyViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: GameModel) {
                holder.setTxtTitle(model.name)
                holder.setTxtDesc(model.year)
                holder.setPic(model.picPathString)
                holder.root.setOnClickListener {
                    Toast.makeText(
                        this@MainActivity,
                        position.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        recyclerView!!.adapter = adapter
        adapter!!.startListening()
    }
}