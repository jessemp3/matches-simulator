package com.sporthela.matches_simulator.domain

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("nome")
    val name: String,
    @SerializedName("forca")
    val starts:Int,
    @SerializedName("imagem")
    val image:String
//class construtora já, sem precisar dos getters e setters do java
)