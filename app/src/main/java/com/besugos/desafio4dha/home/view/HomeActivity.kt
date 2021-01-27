package com.besugos.desafio4dha.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.desafio4dha.R
import com.besugos.desafio4dha.detail.DetailActivity
import com.besugos.desafio4dha.home.model.GameModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query

class HomeActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: FirebaseRecyclerAdapter<*, *>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.rvGamesList2)

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        recyclerView!!.setHasFixedSize(true)
        Load() //method containing  FirebaseRecyclerAdapter
    }

    private fun Load() {
        Log.d("APP", "Home -> Load()")
        val query: Query = FirebaseDatabase.getInstance().reference.child("2FyxkYV7TlcEJ0jybjUAfUq91pT2")
        Log.d("APP", "Home -> def Query")
        val options = FirebaseRecyclerOptions.Builder<GameModel>()
            .setQuery(
                query
            ) { snapshot ->
                GameModel(
                    snapshot.child("name").value.toString(),
                    snapshot.child("year").value.toString(),
                    snapshot.child("desc").value.toString(),
                    snapshot.child("picPathString").value.toString()
                )
            }
            .build()
        Log.d("APP", "Home -> Got Query")
        adapter = object : FirebaseRecyclerAdapter<GameModel, MyViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                Log.d("APP", "Home -> Cria ViewHolder")
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                Log.d("APP", "Home -> inflate rv")
                return MyViewHolder(view)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: GameModel) {
                Log.d("APP", "Home -> bind")
                holder.setTxtTitle(model.name)
                Log.d("APP", "Home -> setTitle = " + model.name)
                holder.setTxtDesc(model.year)
                Log.d("APP", "Home -> setTxtDesc = " + model.year)
                holder.root.setOnClickListener {
                    Toast.makeText(
                        this@HomeActivity,
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




//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
//        val dummyList = mutableListOf<GameModel>()
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))
//        dummyList.add(GameModel("MORTAL KOMBAT X", "2018", "MORTAL KOMBAT X", "1611493936994.jpg"))


//        val viewAdapter = HomeAdapter(dummyList) {
//
//            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
//            with(intent) {
//                putExtra("NAME", it.name)
//                putExtra("YEAR", it.year)
//                putExtra("DESC", it.desc)
//                putExtra("PATH", it.picPathString)
//
//                startActivity(this)
//            }
//
//        }


//        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
//        mRecyclerView.adapter = viewAdapter
//        mRecyclerView.setHasFixedSize(true)
//
//        adapter.setListData(dummyList)
//        adapter.notifyDataSetChanged()



//    }

//    private fun viewModelProvider() {
//        _viewModel = ViewModelProvider(
//            this,
//            HomeViewModel.HomeViewModelFactory(HomeRepository())
//        ).get(HomeViewModel::class.java)
//    }

//


//}