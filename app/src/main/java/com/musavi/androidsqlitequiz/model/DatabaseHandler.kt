package com.musavi.androidsqlitequiz.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.musavi.androidsqlitequiz.`object`.EmpModelClass
import com.musavi.androidsqlitequiz.`object`.LoginModelClass

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"

        private val TABLE_USER = "UserTable"
        private val KEY_UID = "logId"
        private val KEY_UUSername = "logUsername"
        private val KEY_UEMAIL = "logEmail"
        private val KEY_UPASSWORD = "LogPassword"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // membuat tabel beserta definisi kolomnya
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + "); ")
        db?.execSQL(CREATE_CONTACTS_TABLE)
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER +
                "(" + KEY_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UEMAIL + " TEXT," + KEY_UUSername + " TEXT," + KEY_UPASSWORD + " TEXT " + ");")
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS + "," + TABLE_USER)
        onCreate(db)
    }

    fun addUser(log: LoginModelClass){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_UEMAIL, log.logUsername)
        contentValues.put(KEY_UPASSWORD, log.logPassword)

        db.insert(TABLE_USER, null, contentValues)
        db.close()
    }

    fun checkUser(username: String, password: String): Boolean{
        val columns = arrayOf(KEY_UID)
        val db = this.readableDatabase
        val selection = "$KEY_UEMAIL = ? AND $KEY_UPASSWORD =?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false
    }

    fun checkUser(email: String): Boolean{
        val columns = arrayOf(KEY_UID)

        val db = this.readableDatabase

        val selection = "$KEY_UEMAIL = ? "

        val selectionArgs = arrayOf(email)

        val cursor = db.query(TABLE_USER,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false
    }

    // fungsi untuk menambahkan data
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
//        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )
        // menambahkan data pada tabel
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    // fungsi untuk menampilkan data dari tabel ke UI
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    // fungsi untuk memperbarui data pegawai
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )

        // memperbarui data
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
    // fungsi untuk menghapus data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // employee id dari data yang akan dihapus
        contentValues.put(KEY_ID, emp.userId)
        // query untuk menghapus ata
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
}