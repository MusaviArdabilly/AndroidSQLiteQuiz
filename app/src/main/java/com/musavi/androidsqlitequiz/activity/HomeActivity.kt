package com.musavi.androidsqlitequiz.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.musavi.androidsqlitequiz.R
import com.musavi.androidsqlitequiz.`object`.EmpModelClass
import com.musavi.androidsqlitequiz.helper.Adapter
import com.musavi.androidsqlitequiz.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.detail_dialog.*
import java.sql.Types.NULL

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewRecord()

        username.text = intent.getStringExtra("Username")

        btnAddForm.setOnClickListener {
            startActivity(Intent(this, AddEmployeeActivity::class.java))
        }
    }

//    fun saveRecord(view: View){
////        val id = u_id.text.toString()
//        val name = u_name.text.toString()
//        val email = u_email.text.toString()
//        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
//        if(name.trim()!="" && email.trim()!=""){
////            val status = databaseHandler.addEmployee(EmpModelClass(Integer.parseInt(null),name, email))
//            val status = databaseHandler.addEmployee(EmpModelClass(NULL, userName = name, userEmail = email))
//            if(status > -1){
//                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
////                u_id.text.clear()
//                u_name.text.clear()
//                u_email.text.clear()
//
//                viewRecord()
//            }
//        }else{
//            Toast.makeText(applicationContext,"id dan email tidak diperbolehkan kosong", Toast.LENGTH_LONG).show()
//        }
//
//    }

    fun viewRecord(){
        // membuat instanisasi databasehandler
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        // memamnggil fungsi viewemployee dari database handler untuk mengambil data
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        var index = 0

        // setiap data yang didapatkan dari database akan dimasukkan ke array
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            index++
        }

        // membuat customadapter untuk view UI
        val myListAdapter = Adapter(this,empArrayId,empArrayName,empArrayEmail)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            var x_id = empArrayId[position]
            var x_name = empArrayName[position]
            var x_email = empArrayEmail[position]

            showDetailRecord(x_id,x_name,x_email)
        }
    }

//    fun updateRecord(view: View){
//        val dialogBuilder = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView = inflater.inflate(R.layout.update_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        val edtId = dialogView.findViewById(R.id.updateId) as EditText
//        val edtName = dialogView.findViewById(R.id.updateName) as EditText
//        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText
//
//        dialogBuilder.setTitle("Pembaruan data")
//        dialogBuilder.setMessage("Isi data dibawha ini")
//        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
//
//            val updateId = edtId.text.toString()
//            val updateName = edtName.text.toString()
//            val updateEmail = edtEmail.text.toString()
//
//            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
//            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){
//
//                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateEmail))
//                if(status > -1){
//                    Toast.makeText(applicationContext,"data terupdate",Toast.LENGTH_LONG).show()
//                }
//            }else{
//                Toast.makeText(applicationContext,"id dan email tidak diperbolehkan kosong",Toast.LENGTH_LONG).show()
//            }
//
//        })
//        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
//            // tidak melakukan apa2 :)
//        })
//        val b = dialogBuilder.create()
//        b.show()
//    }

//    fun deleteRecord(view: View){
//        //creating AlertDialog for taking user id
//        val dialogBuilder = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
//        dialogBuilder.setTitle("Hapus data")
//        dialogBuilder.setMessage("Masukkan id yang akan dihapus")
//        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->
//
//            val deleteId = dltId.text.toString()
//
//            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
//            if(deleteId.trim()!=""){
//
//                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"",""))
//                if(status > -1){
//                    Toast.makeText(applicationContext,"data terhapus",Toast.LENGTH_LONG).show()
//                }
//            }else{
//                Toast.makeText(applicationContext,"id tidak boleh kosong",Toast.LENGTH_LONG).show()
//            }
//
//        })
//        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
//            // tidak melakukan apa2 :)
//        })
//        val b = dialogBuilder.create()
//        b.show()
//    }

    fun showDetailRecord(id: String, name: String, email: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.detail_dialog, null)
        dialogBuilder.setView(dialogView)

        //ambil data
        var detId = dialogView.findViewById(R.id.detailId) as EditText
        var detName = dialogView.findViewById(R.id.detailName) as EditText
        var detEmail = dialogView.findViewById(R.id.detailEmail) as EditText

        detId.setText(id)
        detName.setText(name)
        detEmail.setText(email)

        dialogBuilder.setTitle("Detail Data")

        dialogBuilder.setNeutralButton("Close"){_,_ ->

        }

        dialogBuilder.setNegativeButton("Update", DialogInterface.OnClickListener { dialog, which ->
//            val updateName = detailName.text.toString()
//            val updateEmail = detailEmail.text.toString()
//            val updateName = dialogView.findViewById(R.id.detailName) as TextView
//            val updateEmail = dialogView.findViewById(R.id.detailEmail).text.toString()
            val updateId = detId.text.toString()
            val updateName = detName.text.toString()
            val updateEmail = detEmail.text.toString()

            Log.i("asd", "$updateEmail+$updateName")

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)

            databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId), userName = updateName, userEmail = updateEmail))
//            databaseHandler.updateEmployee(EmpModelClass(NULL, updateName, updateEmail))
//            databaseHandler.updateEmployee(EmpModelClass(updateName, updateEmail))

            Toast.makeText(applicationContext,"Data Updated",Toast.LENGTH_SHORT).show()
            viewRecord()
        })

        dialogBuilder.setPositiveButton("Delete"){_,_ ->
            val deleteId = detId.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)

            databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"",""))
            viewRecord()

            Toast.makeText(applicationContext,"Data Deleted",Toast.LENGTH_SHORT).show()
        }

        val b = dialogBuilder.create()
        b.show()
    }
}
