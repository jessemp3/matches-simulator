package com.sporthela.matches_simulator.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    @SerializedName("nome")
    val name: String,
    @SerializedName("forca")
    val starts: Int,
    @SerializedName("imagem")
    val image: String,
    var score: Int?
//class construtora já, sem precisar dos getters e setters do java
) : Parcelable