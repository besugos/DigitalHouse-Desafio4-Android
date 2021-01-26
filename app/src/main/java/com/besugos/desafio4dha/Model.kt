package com.besugos.desafio4dha

class Model {
    //    var mid: String? = null
    var mTitle: String? = null
    var mDescription: String? = null

    constructor() {}
    constructor(
//        mid: String?,
        mTitle: String?,
        mDescription: String?) {
//        this.mid = mid
        this.mTitle = mTitle
        this.mDescription = mDescription
    }

    fun getmTitle(): String? {
        return mTitle
    }

    fun setmTitle(mTitle: String?) {
        this.mTitle = mTitle
    }

    fun getmDescription(): String? {
        return mDescription
    }

    fun setmDescription(mDescription: String?) {
        this.mDescription = mDescription
    }
}
