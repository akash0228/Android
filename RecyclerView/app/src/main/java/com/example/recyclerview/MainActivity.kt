package com.example.recyclerview

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

   val arrContacts:ArrayList<ContactModel> = ArrayList<ContactModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView:RecyclerView=findViewById(R.id.recyclerContact)
        //deciding the layout
        recyclerView.layoutManager=LinearLayoutManager(this)

        //Adding Data To DATA Set

        arrContacts.add(ContactModel(R.drawable.contact,"A","100"))
        arrContacts.add(ContactModel(R.drawable.contact,"B","200"))
        arrContacts.add(ContactModel(R.drawable.contact,"C","300"))
        arrContacts.add(ContactModel(R.drawable.contact,"D","400"))
        arrContacts.add(ContactModel(R.drawable.contact,"E","500"))
        arrContacts.add(ContactModel(R.drawable.contact,"F","600"))
        arrContacts.add(ContactModel(R.drawable.contact,"G","700"))
        arrContacts.add(ContactModel(R.drawable.contact,"H","800"))
        arrContacts.add(ContactModel(R.drawable.contact,"I","900"))
        arrContacts.add(ContactModel(R.drawable.contact,"J","1000"))
        arrContacts.add(ContactModel(R.drawable.contact,"K","1100"))
        arrContacts.add(ContactModel(R.drawable.contact,"L","1200"))
        arrContacts.add(ContactModel(R.drawable.contact,"M","1300"))
        arrContacts.add(ContactModel(R.drawable.contact,"N","1400"))
        arrContacts.add(ContactModel(R.drawable.contact,"O","1500"))

        //Adapter
        //Pick Data from indexes  From Data set add it to the view and sends it in recycler view
        val adapter:RecyclerContactAdapter=RecyclerContactAdapter(this,arrContacts)

        //Setting adapter to the Recycler view
        recyclerView.adapter=adapter


        //Add New Contact
        val btnOpenDialog:FloatingActionButton=findViewById(R.id.btnOpenDialog)

        btnOpenDialog.setOnClickListener{
            //Creating Custom Dialog
            val dialog=Dialog(this)
            dialog.setContentView(R.layout.add_update_lay)

            val editName:EditText=dialog.findViewById(R.id.editName)
            val editNumber:EditText=dialog.findViewById(R.id.editNumber)
            val btnAction: Button =dialog.findViewById(R.id.btnAction)

            btnAction.setOnClickListener{
                val name:String=editName.text.toString()
                val number:String=editNumber.text.toString()
                arrContacts.add(ContactModel(R.drawable.contact,name,number))

                //notifying adapter about data add
                adapter.notifyItemInserted(arrContacts.size-1)

                recyclerView.scrollToPosition(arrContacts.size-1)

                //disapper dialog
                dialog.dismiss()
            }

            dialog.show()
        }

    }
}