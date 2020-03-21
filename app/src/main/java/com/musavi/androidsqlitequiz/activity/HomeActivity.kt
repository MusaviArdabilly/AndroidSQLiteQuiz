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
        //Mengambil fungsi viewRecord agar user tidak perlu mengklik button untuk melihat isi database
        viewRecord()
        //Mengambil isi variable username yang telah di lempar dari halaman login
        username.text = intent.getStringExtra("Username")
        //Button untuk melakukan Pindah Halaman pada halaman AddEmployee
        btnAddForm.setOnClickListener {
            startActivity(Intent(this, AddEmployeeActivity::class.java))
        }
    }
    //Fungsi viewRecord yang digunakan untuk menampilkan isi table contacts
    fun viewRecord(){
        //Membuat instanisasi databasehandler
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        //Memamnggil fungsi viewemployee dari database handler untuk mengambil data
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        val empArrayPhone = Array<String>(emp.size){"null"}
        var index = 0
        //Memasukkan data yang didapatkan dari database kedalam array
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            empArrayPhone[index] = e.userPhone
            index++
        }
        //Membuat custom adapter untuk view UI
        val myListAdapter = Adapter(this,empArrayId,empArrayName,empArrayEmail,empArrayPhone)
        listView.adapter = myListAdapter
        //Memberikan aksi apabila item pada listview di klik oleh user
        listView.setOnItemClickListener { parent, view, position, id ->
            var x_id = empArrayId[position]
            var x_name = empArrayName[position]
            var x_email = empArrayEmail[position]
            var x_phone = empArrayPhone[position]
            //Memanggil fungsi showDetailRecord dengan memberikan parameter item yang di click
            showDetailRecord(x_id,x_name,x_email,x_phone)
        }
    }

    fun showDetailRecord(id: String, name: String, email: String, phone: String){
        //Inisialisasi AlertDialog dan LayoutInflatter
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        //Mengkoneksikan LayoutInflatter dengan layout detail_dialog.xml
        val dialogView = inflater.inflate(R.layout.detail_dialog, null)
        //Setview AlertDialog dengan Layoutinflater
        dialogBuilder.setView(dialogView)
        //Membuat variable untuk inisialisasi id dari setiap field detail_dialog.xml
        var detailId = dialogView.findViewById(R.id.detailId) as EditText
        var detailName = dialogView.findViewById(R.id.detailName) as EditText
        var detailEmail = dialogView.findViewById(R.id.detailEmail) as EditText
        var detailPhone = dialogView.findViewById(R.id.detailPhone) as EditText
        //Memberikan setiap field detail_dialog.xml isian berdasarkan parameter yang telah dilempar oleh fungsi viewRecord
        detailId.setText(id)
        detailName.setText(name)
        detailEmail.setText(email)
        detailPhone.setText(phone)
        //Memberikan judul pada judul AlertDialog
        dialogBuilder.setTitle("Detail Data")
        //Membuat button untuk menutup AlertDialog
        dialogBuilder.setNeutralButton("Close"){_,_ ->
        }
        //Membuat button untuk melakukan edit data
        dialogBuilder.setNegativeButton("Update", DialogInterface.OnClickListener { dialog, which ->
            //Membuat Variable untuk mengambil isian pada setiap field pada AlertDialog
            val updateId = detailId.text.toString()
            val updateName = detailName.text.toString()
            val updateEmail = detailEmail.text.toString()
            val updatePhone = detailPhone.text.toString()
            //Mencoba apakah data yang telah di ambil benar
            Log.i("asd", "$updateName+$updateEmail+$updateName")
            //Membuat inisialisasi database
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            //Memanggil fungsi updateEmployee pada databaseHandler dan memberikan parameter berdasarkan edittext pada AlertDialog (id tidak dapat di ubah karena sudah di set editable=false)
            databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId), userName = updateName, userEmail = updateEmail, userPhone = updatePhone))
            //Memanggil fungsi viewRecord agar user langsung bisa melihat perubahan pada data
            viewRecord()
            //Memberikan Toast bahwa data telah terubah
            Toast.makeText(applicationContext,"Data Updated",Toast.LENGTH_SHORT).show()
        })
        //Membuat button untuk melakukan delete data
        dialogBuilder.setPositiveButton("Delete"){_,_ ->
            //Membuat variable untuk mengambil data pafa field detailId dan menjadikannya string
            val deleteId = detailId.text.toString()
            //Membuat inisialisasi database
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            //Memanggil fungsi deleteEmployee pada databaseHandler dan memberikan parameter berdasarkan field id dan untuk field lainnya di kosongkan
            databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"","", ""))
            //Memanggil fungsi viewRecord agar user langsung bisa melihat perubahan pada data
            viewRecord()
            //Memberikan Toast bahwa data telah terhapus
            Toast.makeText(applicationContext,"Data Deleted",Toast.LENGTH_SHORT).show()
        }
        //Menampilkan AlertDialog
        val b = dialogBuilder.create()
        b.show()
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