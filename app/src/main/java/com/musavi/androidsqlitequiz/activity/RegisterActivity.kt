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

            if (r_username != "" && r_password != ""){
                if (!databaseHandler!!.checkUser(r_username.trim())){

                    var user = LoginModelClass(logUsername = r_username.trim(),
                                               logEmail = r_email.trim(),
                                               logPassword = r_password.trim())

                    databaseHandler!!.addUser(user)

                    Toast.makeText(applicationContext,"user berhasil dibuat!", Toast.LENGTH_LONG).show()

                    val inte = Intent(this, LoginActivity::class.java)
                    startActivity(inte)

                } else{
                    Toast.makeText(applicationContext,"email sudah terpakai", Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(applicationContext,"email dan password tidak diperbolehkan kosong", Toast.LENGTH_LONG).show()
            }
        }
    }
}
