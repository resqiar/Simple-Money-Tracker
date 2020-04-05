package com.example.uangsakusqlitelazday.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.uangsakusqlitelazday.App
import com.example.uangsakusqlitelazday.R
import com.example.uangsakusqlitelazday.database.Constants
import com.example.uangsakusqlitelazday.util.Converter
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.menu_edit.*
import java.util.*

class EditActivity : AppCompatActivity(){
    // tanggal template
    var tanggal : String? = null
    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

            var bundle : Bundle? = intent.extras
            tanggal = bundle?.getString("TRANSAKSI_TANGGAL").toString()

            // status is checked
            status = bundle?.getString("TRANSAKSI_STATUS")

            when(status){
                "MASUK" -> radio_pemasukan_edit.isChecked = true
                "KELUAR" -> radio_pengeluaran_edit.isChecked = true
            }
        keterangan_edit.setText(bundle?.getString("TRANSAKSI_KETERANGAN"))
        nominal_edit.setText(bundle?.getString("TRANSAKSI_NOMINAL"))
        date_edit.text = bundle?.getString("TRANSAKSI_TANGGAL")?.let { Converter.dateFormat(it) }

            // cek intent extra dari ADAPTER        Log.e("cek cek cek", bundle?.getString("KETERANGAN"))

        supportActionBar!!.title = "Update"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // calendar variable setup
        var calendar = Calendar.getInstance()
        var d = calendar.get(Calendar.DAY_OF_MONTH)
        var m = calendar.get(Calendar.MONTH) + 1
        var y = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            tanggal = "$year-${Converter.onDecimalFormat(month)}-${Converter.onDecimalFormat(dayOfMonth)}"
            Log.e("uagdwiudgbai", tanggal.toString())
            date_edit.setText(Converter.dateFormat(tanggal.toString()))
        }, y, m, d)


        btn_datePicker.setOnClickListener{
            datePickerDialog.show()
        }
        date_edit.setOnClickListener{
            datePickerDialog.show()
        }

        // radio status
        radio_editStatus.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio_pemasukan_edit -> status = "MASUK"
                R.id.radio_pengeluaran_edit -> status = "KELUAR"
            }
            // cek apakah ketika radio di klik status terisi
            // Log.e("_statuscek", status)
        }

        btn_update.setOnClickListener{
            if ( nominal_edit.text.isNullOrBlank() || keterangan_edit.text.isNullOrBlank()){
                Toast.makeText(applicationContext, "Mohon Diisi Semua", Toast.LENGTH_SHORT).show()
            }else{
                App.db!!.updateData(bundle?.getString("TRANSAKSI_ID").toString().toInt(),
                    status.toString(),
                    nominal_edit.text.toString().toInt(),
                    keterangan_edit.text.toString(),
                    tanggal!!)
            }
            Toast.makeText(this, "Perubahan berhasil disimpan", Toast.LENGTH_LONG).show()
            finish()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
