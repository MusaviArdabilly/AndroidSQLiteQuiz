package com.musavi.androidsqlitequiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.musavi.androidsqlitequiz.R
import com.musavi.androidsqlitequiz.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Membuat aksi apabila button di click
        btnLogin.setOnClickListener {
            //Membuat variable untuk mengambil isi dari field login dan menjadikan tipe data string
            val u_username = usernameLInput!!.text.toString()
            val u_password = passwordLInput!!.text.toString()
            //Membuat variable untuk inisialisasi database
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            //Melakukan aksi apabila field pada login tidak kosong dan memotong spasi
            if (u_username.trim() != "" && u_password.trim() != ""){
                //Melakukan aksi apabila username terdapat pada database dan memotong spasi
                if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' })) {
                    //Melakukan aksi apabila username dan password benar dan memotong spasi
                    if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' },
                            u_password.trim { it <= ' ' })) {
                        //Melakukan perpindahan halaman ke halaman Home
                        val inte = Intent(this, HomeActivity::class.java)
                        //Melempar data pada username untuk dikirim ke halaman home
                        inte.putExtra("Username", u_username.trim { it <= ' ' })
                        //Mengosongkan edittext pada form login
                        usernameLInput.setText(null)
                        passwordLInput.setText(null)
                        //Memulai Perpindahan
                        startActivity(inte)
                    } else {
                        //Menampilkan toast apabila Password salah
                        Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_LONG).show()
                    }
                } else{
                    //Menampilkan toast apabila Username salah
                    Toast.makeText(applicationContext, "Username salah", Toast.LENGTH_LONG).show()
                }
            } else{
                //Menampilkan toast apabila field ada yang kosong
                Toast.makeText(applicationContext,"email dan password tidak diperbolehkan kosong", Toast.LENGTH_LONG).show()
            }
        }
        //Button untuk melakukan perpindahan ke halaman register
        btnRegisterForm.setOnClickListener {
            val inte = Intent(this, RegisterActivity::class.java)
            startActivity(inte)
        }
    }
}
