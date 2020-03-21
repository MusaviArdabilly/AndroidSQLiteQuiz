package com.musavi.androidsqlitequiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.musavi.androidsqlitequiz.R
import com.musavi.androidsqlitequiz.`object`.LoginModelClass
import com.musavi.androidsqlitequiz.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            val r_username = usernameRInput!!.text.toString()
            val r_email = emailRInput!!.text.toString()
            val r_password = passwordRInput!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //Melakukan aksi apabila username, email dan password tidak kosong
            if (r_username != "" && r_email != "" && r_password != ""){
                //Melakukan aksi apabila username belum ada pada database
                if (!databaseHandler!!.checkUser(r_username.trim())){
                    //Membuat variable untuk menampung data dari LoginModelClass yang telah di isi berdasarkan field pada form register
                    var user = LoginModelClass(logUsername = r_username.trim(),
                                               logEmail = r_email.trim(),
                                               logPassword = r_password.trim())
                    //Memanggil fungsi addUser pada databaseHandler dengan parameter user yang berisi LoginModelClass yang telah di isi berdasarkan field pada form register
                    databaseHandler!!.addUser(user)
                    //Menampilkan toast apabila addUser berhasil
                    Toast.makeText(applicationContext,"User berhasil dibuat", Toast.LENGTH_LONG).show()
                    //Memindahkan halaman pada halaman Login
                    val inte = Intent(this, LoginActivity::class.java)
                    startActivity(inte)
                } else{
                    //Menampilkan toast apabila username ada pada database
                    Toast.makeText(applicationContext,"Username sudah digunakan", Toast.LENGTH_LONG).show()
                }
            } else{
                //Menampilkan toast apabila terdapat field kosong
                Toast.makeText(applicationContext,"Username, Email dan Password tidak boleh kosong", Toast.LENGTH_LONG).show()
            }
        }
    }
}
