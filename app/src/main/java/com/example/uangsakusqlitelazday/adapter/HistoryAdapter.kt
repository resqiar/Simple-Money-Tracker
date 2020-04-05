package com.example.uangsakusqlitelazday.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.getIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uangsakusqlitelazday.App
import com.example.uangsakusqlitelazday.Model
import com.example.uangsakusqlitelazday.R
import com.example.uangsakusqlitelazday.activity.BlankActivity
import com.example.uangsakusqlitelazday.activity.EditActivity
import com.example.uangsakusqlitelazday.activity.MainActivity
import com.example.uangsakusqlitelazday.database.Constants
import com.example.uangsakusqlitelazday.util.Converter
import kotlinx.android.synthetic.main.item_row_history.view.*
import kotlinx.android.synthetic.main.item_row_history.view.btn_delete
import kotlinx.android.synthetic.main.item_row_history.view.btn_edit

class HistoryAdapter(private val listHistory : List<Model>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val kasList = ArrayList<Model>()


    inner class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(history : Model){
            with(itemView) {
                if (App.db != null) {
                    // check jika uang masuk maka icon akan berubah (arrow_down) begitupula sebaliknya
                    if (history.status == "MASUK") {
                        tv_status_img.setImageResource(R.drawable.ic_arrow_downward_black_24dp)
                    } else {
                        tv_status_img.setImageResource((R.drawable.ic_arrow_upward_black_24dp))
                    }

                    // teks lainnya
                    tv_status.text = history.status
                    tv_keterangan.text = history.keterangan
                    tv_nominal.text = Converter.moneyFormat(history.nominal.toString())

                    try {
                        tv_tanggal.text = Converter.dateFormat(history.tanggal)
                    }catch (e : NullPointerException){
                        Log.e("null bagian tv_tanggal", e.toString())
                    }

                    btn_edit.setOnClickListener {
                        val intent = Intent(context, EditActivity::class.java)
                        intent.putExtra("TRANSAKSI_ID", history.transaksi_id.toString())
                        intent.putExtra("TRANSAKSI_STATUS", history.status)
                        intent.putExtra("TRANSAKSI_NOMINAL", history.nominal.toString())
                        intent.putExtra("TRANSAKSI_KETERANGAN", history.keterangan)
                        intent.putExtra("TRANSAKSI_TANGGAL", history.tanggal)
                        context.startActivity(intent)
                    }
                        // button delete
                        // oper ke main activity (?)
                    btn_delete.setOnClickListener{

                        menuDelete(context, history.transaksi_id, history)
                    }
                }
            }
        }
    }

    // delete dialog -- aksi yang akan dimunculkan ketika btn_delete di klik
    private fun menuDelete(context: Context, data: Int, model : Model) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Alerta!")
        builder.setMessage("Data yang dihapus tidak bisa dikembalikan, apakah anda yakin?")
        builder.setPositiveButton("YA", DialogInterface.OnClickListener { dialog, _ ->


            App.db!!.deleteData(data)


            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
            dialog.dismiss()

            context.startActivity(Intent(context, BlankActivity::class.java))

        })
        builder.setNegativeButton("TIDAK", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })

        builder.create().show()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_history, parent, false)
            return HistoryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }
}