package com.musavi.androidsqlitequiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.musavi.androidsqlitequiz.R
import com.musavi.androidsqlitequiz.`object`.EmpModelClass
import com.musavi.androidsqlitequiz.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_add_employee.*
import java.sql.Types.NULL

class AddEmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        //Aksi Button Apabila di click
        btnAdd.setOnClickListener {
            //Mengambil data dari setiap editText
            val name = nameEInput!!.text.toString()
            val email = emailEInput!!.text.toString()
            val phone = phoneEInput!!.text.toString()
            //Inisialisasi Database pada class
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if (name != "" && email != "" && email != ""){ //nama, email, phone tidak boleh kosong
                databaseHandler.addEmployee(EmpModelClass(NULL, userName = name, userEmail = email, userPhone = phone)) //Apabila field tidak ada yang kosong maka data akan di simpan
                startActivity(Intent(this,HomeActivity::class.java)) //Melakukan Pindah Halaman ke Halaman Home
            }
        }
    }
}
