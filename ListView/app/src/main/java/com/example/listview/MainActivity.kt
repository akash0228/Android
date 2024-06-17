package com.example.listview

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var arrName:ArrayList<String>
    private lateinit var arrIds:ArrayList<String>
    private lateinit var arrLanguages:ArrayList<String>
    private lateinit var spinner:Spinner
    private lateinit var autoCompleteTextView:AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listView=findViewById(R.id.listview)
        arrName= ArrayList<String>()

        arrName.add("Akash")
        arrName.add("Akash1")
        arrName.add("Akash2")
        arrName.add("Akash3")
        arrName.add("Akash4")
        arrName.add("Akash5")
        arrName.add("Akash6")
        arrName.add("Akash7")
        arrName.add("Akash8")
        arrName.add("Akash9")
        arrName.add("Akash10")
        arrName.add("Akash")
        arrName.add("Akash1")
        arrName.add("Akash2")
        arrName.add("Akash3")
        arrName.add("Akash4")
        arrName.add("Akash5")
        arrName.add("Akash6")
        arrName.add("Akash7")
        arrName.add("Akash8")
        arrName.add("Akash9")
        arrName.add("Akash10")

        val adapter:ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrName)

        listView.adapter=adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            Toast.makeText(this,"Clicked ${position+1} item",Toast.LENGTH_SHORT).show()
        }

        //Spinner
        spinner=findViewById(R.id.spinner)

        arrIds= ArrayList<String>()

        arrIds.add("Adhar Card")
        arrIds.add("PAN Card")
        arrIds.add("Voter ID Card")
        arrIds.add("Driving Card")
        arrIds.add("Ration Card")
        arrIds.add("Xth Card")
        arrIds.add("XIIth Card")

        val spinnerAdapter:ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrIds)
        spinner.adapter=spinnerAdapter

        //AutoCOmpleteTextView
        autoCompleteTextView=findViewById(R.id.actxtView)
        arrLanguages= ArrayList<String>()
        arrLanguages.add("JAVA")
        arrLanguages.add("C")
        arrLanguages.add("C++")
        arrLanguages.add("PYTHON")
        arrLanguages.add("JAVASCRIPT")

        val autoCompleteAdapter:ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrLanguages)
        autoCompleteTextView.setAdapter(autoCompleteAdapter)
        autoCompleteTextView.threshold=1

    }
}