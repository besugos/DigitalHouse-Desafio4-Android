package com.besugos.desafio4dha.home.repository

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.besugos.desafio4dha.home.model.GameModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeRepository {

    private lateinit var auth: FirebaseAuth

    private lateinit var mutableData:LiveData<MutableList<GameModel>>


    fun getUserData(): LiveData<MutableList<GameModel>> {

        var mutableData = MutableLiveData<MutableList<GameModel>>()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("2FyxkYV7TlcEJ0jybjUAfUq91pT2")


        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//              val game = snapshot.getValue(GameModel::class.java)
                val typeIndicator = object: GenericTypeIndicator<MutableList<GameModel>>() {}
                val gameList: MutableList<GameModel> = snapshot.getValue(typeIndicator)!!

                Log.d("MEU_APP", "VALUE IS: " + gameList.toString())
//                mutableData.value = game

                mutableData.value = gameList

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return mutableData
    }
}

