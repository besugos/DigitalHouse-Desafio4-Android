package com.besugos.desafio4dha.home.model

data class GameModel (
    val name: String = "DEFAULT NAME",
    val year: String = "DEFAULT YEAR",
    val desc: String = "DEFAULT DESC",
    val picPathString: String = "DEFAULT PATH"
) {
    fun getmName(): String? {
        return name
    }

    fun getmYear(): String? {
        return year
    }

    fun getmDesc(): String? {
        return desc
    }

    fun getmPath(): String? {
        return picPathString
    }
}

