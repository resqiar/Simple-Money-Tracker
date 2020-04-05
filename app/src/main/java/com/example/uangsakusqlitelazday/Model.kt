package com.example.uangsakusqlitelazday

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model (
    val transaksi_id : Int,
    val status : String,
    val keterangan : String,
    val nominal : Int,
    val tanggal : String
): Parcelable