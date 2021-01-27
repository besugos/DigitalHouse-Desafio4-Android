package com.besugos.desafio4dha.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.besugos.desafio4dha.home.model.GameModel
import com.besugos.desafio4dha.home.repository.HomeRepository

class HomeViewModel(private val repository: HomeRepository): ViewModel(){

    fun fetchUserData(): LiveData<MutableList<GameModel>> {
        val mutableData = MutableLiveData<MutableList<GameModel>>()
        repository.getUserData().observeForever{
            mutableData.value = it
        }

        return mutableData
    }

    class HomeViewModelFactory(private val _repository: HomeRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(_repository) as T
        }
    }

}