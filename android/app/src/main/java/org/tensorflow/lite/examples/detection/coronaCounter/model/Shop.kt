package com.example.coronacounter.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigInteger

data class Shop(
    @SerializedName("onum")
    var onum:Integer?,

    @SerializedName("sid")
    var sid:String?,

    @SerializedName("sname")
    var shopName:String?,

    @SerializedName("rname")
    var location:String?,

    @SerializedName("maxPeople")
    var maximumPeople:Integer?,


    @SerializedName("businessType")
    var businessType:BusinessType?
) :Serializable{
    fun limitPeople (stage:Int) : Int{
        val limit = (this.maximumPeople!!.toInt() * (1-stage/4.0)).toInt()
        Log.d("limitpeople",limit.toString())
        return limit
    }
}