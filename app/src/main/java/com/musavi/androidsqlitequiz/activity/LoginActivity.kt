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

        btnLogin.setOnClickListener {
            val u_username = usernameLInput!!.text.toString()
            val u_password = passwordLInput!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            if (u_username.trim() != "" && u_password.trim() != ""){

                if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' })) {

                    if (databaseHandler!!.checkUser(u_username.trim { it <= ' ' },
                            u_password.trim { it <= ' ' })) {
                        val inte = Intent(this, HomeActivity::class.java)
                        inte.putExtra("Username", u_username.trim { it <= ' ' })
                        usernameLInput.setText(null)
                        passwordLInput.setText(null)
                        startActivity(inte)
                    } else {
                        Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_LONG).show()
                    }

                } else{
                    Toast.makeText(applicationContext, "Email salah", Toast.LENGTH_LONG).show()
                }

            } else{
                Toast.makeText(applicationContext,"email dan password tidak diperbolehkan kosong", Toast.LENGTH_LONG).show()
            }


        }

        btnRegisterForm.setOnClickListener {
            val inte = Intent(this, RegisterActivity::class.java)
            startActivity(inte)
        }
    }
}
