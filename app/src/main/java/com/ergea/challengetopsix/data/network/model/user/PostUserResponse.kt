package com.ergea.challengetopsix.data.network.model.user

import com.google.gson.annotations.SerializedName

data class PostUserResponse(

    @field:SerializedName("username")
    val username: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("password")
    val password: String? = null,
    @field:SerializedName("nama_lengkap")
    val namaLengkap: String? = null,
    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String? = null,
    @field:SerializedName("alamat")
    val alamat: String? = null,
)