package com.example.uangsakusqlitelazday.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uangsakusqlitelazday.App
import com.example.uangsakusqlitelazday.R
import kotlinx.android.synthetic.main.activity_add_data.*

class AddDataActivity : AppCompatActivity() {
        // buat variabel untuk menampung status apakah MASUK atau KELUAR
    var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        // menu title
        supportActionBar!!.title = "Tambah Uang"
        // memunculkan icon back
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            // radio status
        radio_addStatus.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radio_pemasukan -> status = "MASUK"
                R.id.radio_pengeluaran -> status = "KELUAR"
            }
                // cek apakah ketika radio di klik status terisi
            Log.e("_statuscek", status)
        }

        btn_submit.setOnClickListener{
            if (status.isBlank() || edit_nominal.text.isNullOrBlank() || edit_keterangan.text.isNullOrBlank()){
                Toast.makeText(applicationContext, "Mohon Diisi Semua", Toast.LENGTH_SHORT).show()
            }else{
               var id =  App.db!!.insertData(status, edit_nominal.text.toString(), edit_keterangan.text.toString())
                // check datanya masuk kaga
                Log.e("_dataMasuk", id.toString())

                if (id > 0){
                    Toast.makeText(applicationContext, "Uang Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }
    }

        // tutup activity ketika button kembali di klik
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
