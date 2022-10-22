package com.ergea.challengetopsix.data.network.model.user

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("username")
    val username: String?,
    @SerializedName("nama_lengkap")
    val namaLengkap: String?,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String?,
    @SerializedName("alamat")
    val alamat: String?
)