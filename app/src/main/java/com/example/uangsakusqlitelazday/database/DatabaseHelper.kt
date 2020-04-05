package com.example.uangsakusqlitelazday.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uangsakusqlitelazday.Model

class DatabaseHelper : SQLiteOpenHelper {

    /* companion object */  companion object {
            const val DBname = "thisAppDB"
            const val DBversion = 1
            const val tableName = "transaksi"
            const val transaksiID = "transaksi_id"
            const val status = "status"
            const val jumlah = "jumlah"
            const val keterangan = "keterangan"
            const val tanggal = "tanggal"
        }
    /* variable used in constructor */
    var db: SQLiteDatabase
    var context : Context? = null

    constructor(context: Context) : super(context, DBname, null, DBversion){
        this.context = context
        db = this.writableDatabase
    }

    /* create table // use in onCreate() */
    private val createTable = "CREATE TABLE " + tableName + "(" +
            transaksiID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            status + " TEXT," +
            jumlah + " TEXT," +
            keterangan + " TEXT," +
            tanggal + " DATETIME DEFAULT CURRENT_DATE" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)   // create table
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName") // check if db already exist it will replaced
    }

    /* insert data */
     fun insertData(dbStatus : String, dbJumlah : String, dbKeterangan : String): Long {
        val values = ContentValues()
        values.put(status, dbStatus)
        values.put(jumlah, dbJumlah)
        values.put(keterangan, dbKeterangan)

        // buat callback untuk cek data apakah berhasil dibuat
        return db.insert(tableName, null, values)
    }

    /*Update Data*/
    fun updateData(id : Int, Status : String, nominal : Int, Keterangan : String, Tanggal : String){
        val DB : SQLiteDatabase = this.writableDatabase

        val values = ContentValues()
        values.put(status, Status)
        values.put(jumlah, nominal)
        values.put(keterangan, Keterangan)
        values.put(tanggal, Tanggal)

        DB.update(tableName, values, "$transaksiID='$id'", null)

    }

    /*DELETE DATA*/
    fun deleteData(id: Int){
        val DB : SQLiteDatabase = this.writableDatabase
        DB.delete(tableName, "$transaksiID='$id'", null)
        getData() ; getTotal()
    }

    /* Get Data SQLite */
    fun getData(): List<Model> {
        // kas
        val kas = ArrayList<Model>()

        // perintah mengambil data sqlite
        val sqliteCommand = "SELECT * FROM $tableName ORDER BY $transaksiID DESC"

        //baca indeks sqlite
        val cursor : Cursor = db.rawQuery(sqliteCommand, null)
        cursor.moveToFirst()

        // looping data sql
        for (i in 0 until cursor.count){
            // hitung indeks ke -
            cursor.moveToPosition(i)

            kas.add(Model(
                    // masukkan data dari sqlite ke MODEL()
                cursor.getInt(cursor.getColumnIndex(transaksiID)),
                cursor.getString(cursor.getColumnIndex(status)),
                cursor.getString(cursor.getColumnIndex(keterangan)),
                cursor.getInt(cursor.getColumnIndex(jumlah)),
                cursor.getString(cursor.getColumnIndex(tanggal))
            ))
        }
        return kas
    }

    fun getTotal(){
            // ambil total dari db
        val sqlCommand = "SELECT SUM($jumlah) AS total, " +
                "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'MASUK') AS masuk, " +
                "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'KELUAR') AS keluar " +
                "FROM $tableName"

        val cursor : Cursor = db.rawQuery(sqlCommand, null)
        cursor.moveToFirst()

        Constants.masuk = cursor.getLong(cursor.getColumnIndex("masuk"))
        Constants.keluar = cursor.getLong(cursor.getColumnIndex("keluar"))

    }

}