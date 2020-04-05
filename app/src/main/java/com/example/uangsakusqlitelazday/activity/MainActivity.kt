package com.example.uangsakusqlitelazday.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uangsakusqlitelazday.App
import com.example.uangsakusqlitelazday.Model
import com.example.uangsakusqlitelazday.R
import com.example.uangsakusqlitelazday.adapter.HistoryAdapter
import com.example.uangsakusqlitelazday.database.Constants
import com.example.uangsakusqlitelazday.util.Converter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_row_history.*
import kotlinx.android.synthetic.main.menu_edit.*
import kotlinx.android.synthetic.main.menu_edit.btn_delete
                                                                // implement interface dari adapter
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val kasList = ArrayList<Model>()
    private var historyAdapter = HistoryAdapter(kasList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        rv_history.setHasFixedSize(true)
        supportActionBar!!.title = "Dashboard"
            // recylerview show
        showRecyclerView()
            // floating action button
        fab.setOnClickListener(this)


    }


        // data dari DATABASE di load disini
    override fun onResume() {
            this.overridePendingTransition(0, 0)
        super.onResume()
        getData()  ; getTotal()
    }



        // recyclerview binding
    private fun showRecyclerView(){
        rv_history.layoutManager = LinearLayoutManager(this)
         historyAdapter = HistoryAdapter(kasList)

//            historyAdapter.setListener(object : HistoryAdapter.OnClickListener{
//                override fun onDeleteButton(data: Int) {
//                    menuDelete()
//                    Toast.makeText(this@MainActivity, "dwadwadwadaw", Toast.LENGTH_LONG).show()
//                }
//            })

        rv_history.adapter = historyAdapter
    }

        // delete dialog -- aksi yang akan dimunculkan ketika btn_delete di klik
//        private fun menuDelete() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Alerta!")
//        builder.setMessage("Apakah anda yakin untuk menghapus data tersebut?")
//        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
//            App.db!!.deleteData(Constants.transaksi_id!!)
//            Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
//
//            getData()
//            dialog.dismiss()
//        })
//        builder.setNegativeButton("TIDAK", DialogInterface.OnClickListener { dialog, _ ->
//            dialog.dismiss()
//        })
//            builder.show()
//    }

    private fun getTotal() {
        App.db!!.getTotal()
        saldo_pemasukan_dash.text = Converter.moneyFormat( Constants.masuk.toString() )
        saldo_pengeluaran_dash.text = Converter.moneyFormat(Constants.keluar.toString())
        saldo_kumulatif_dash.text = Converter.moneyFormat(((Constants.masuk - Constants.keluar).toString()))
    }


        /*get data*/
         fun getData(){
            // clear dulu agar tidak terjadi penumpukan data
            kasList.clear()

            // kosongkan dulu adapternya
            rv_history.adapter = null

            // ambil data dari db
            kasList.addAll(App.db!!.getData())

            //kemudian pasang adapternya
            rv_history.adapter = HistoryAdapter(kasList)
        }



//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab -> {
                startActivity(Intent(this, AddDataActivity::class.java))
            }
        }
    }

}
