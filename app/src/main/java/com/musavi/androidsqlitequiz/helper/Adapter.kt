package com.musavi.androidsqlitequiz.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.musavi.androidsqlitequiz.R

class Adapter(private val context: Activity, private val id: Array<String>,
              private val name: Array<String>, private val email: Array<String>, private val phone: Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        //Inisialisasi layoutInflater dan megkoneksikan pada custom_list.xml
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)
        //Mengkoneksikan field pada custom_list.xml dengan layout_inflater
        val idText = rowView.findViewById(R.id.textViewId) as TextView
        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        val emailText = rowView.findViewById(R.id.textViewEmail) as TextView
        val phoneText = rowView.findViewById(R.id.textViewPhone) as TextView
        //Memberikan isian pada setiap item berdasarkan id masing field
        idText.text = "${id[position]}"
        nameText.text = "${name[position]}"
        emailText.text = "${email[position]}"
        phoneText.text = "${phone[position]}"

        return rowView
    }
}