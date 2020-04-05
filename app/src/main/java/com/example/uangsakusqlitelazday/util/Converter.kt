package com.example.uangsakusqlitelazday.util

import android.util.Log
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    companion object{
        fun dateFormat(date: String): String {
            //format date awal

            var format = SimpleDateFormat("yyyy-mm-dd")
            var newDate : Date? = null

            try {
                newDate = format.parse(date)
            }catch (e: ParseException){
                Log.e("_dateformatting", e.toString())
            }

            format = SimpleDateFormat("dd/mm/yyyy")
            return format.format(newDate)

        }

        fun moneyFormat(number: String): String {

            var rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            return rupiahFormat.format(number.toInt())
        }

        fun onDecimalFormat(number: Int): String{
            val numberFormat = DecimalFormat("00")
            return numberFormat.format(number.toLong())
        }
    }
}