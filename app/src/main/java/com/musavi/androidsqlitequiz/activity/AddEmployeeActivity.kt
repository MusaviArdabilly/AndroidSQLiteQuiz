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

        btnAdd.setOnClickListener {
            val name = nameEInput!!.text.toString()
            val email = emailEInput!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if (name != "" && email != ""){
                databaseHandler.addEmployee(EmpModelClass(NULL, userName = name, userEmail = email))
                startActivity(Intent(this,HomeActivity::class.java))
            }
        }
    }
}
