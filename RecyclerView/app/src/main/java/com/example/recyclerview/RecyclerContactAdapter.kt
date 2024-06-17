package com.example.recyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Needs View Holder
class RecyclerContactAdapter(val context: Context,val arrContacts:ArrayList<ContactModel>): RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Mapping the ids
         var txtName:TextView
         var txtNumber:TextView
         var imgContact:ImageView
         var llRow:LinearLayout

        init {
            txtName=itemView.findViewById(R.id.txtName)
            txtNumber=itemView.findViewById(R.id.txtNumber)
            imgContact=itemView.findViewById(R.id.imgContact)
            llRow=itemView.findViewById(R.id.llRow)
        }
    }

    private fun setAnimation(viewToAnimate:View,position: Int){
        val slideIn = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left)
        viewToAnimate.startAnimation(slideIn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create view holder

        //creating view by inflating the layout we made  attach root false so that it can be detached when scrolled
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.contact_row,parent,false)

            //creating viewholder using above view
        val viewHolder=ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return arrContacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Seting Data On the ViewHolder By mapping it to holdded view // Binding Data
        holder.imgContact.setImageResource(arrContacts.get(position).img)
        holder.txtName.setText(arrContacts.get(position).name)
        holder.txtNumber.setText(arrContacts.get(position).number)

        holder.llRow.setOnClickListener{
            val dialog:Dialog=Dialog(context);
            dialog.setContentView(R.layout.add_update_lay)

            val editName: EditText =dialog.findViewById(R.id.editName)
            val editNumber: EditText =dialog.findViewById(R.id.editNumber)
            val btnAction: Button =dialog.findViewById(R.id.btnAction)
            val txtTitle:TextView=dialog.findViewById(R.id.txtTitle)

            txtTitle.text="Update Contact"
            btnAction.text="Update"

            editName.setText(arrContacts.get(position).name)
            editNumber.setText(arrContacts.get(position).number)


            btnAction.setOnClickListener{
                val name:String=editName.text.toString()
                val number:String=editNumber.text.toString()

                //update changed data
                arrContacts.set(position,ContactModel(R.drawable.contact,name,number))

                //notify about change
                notifyItemChanged(position)

                //disapper dialog
                dialog.dismiss()
            }

            dialog.show()

            holder.llRow.setOnClickListener{
                it.setOnLongClickListener{
                    val builder=AlertDialog.Builder(context).setTitle("Delete Contact");
                    builder.setMessage("Are You Sure want to delete?").setIcon(com.google.android.material.R.drawable.ic_clear_black_24)
                        .setPositiveButton("Yes",DialogInterface.OnClickListener(){
                            dialog, which ->
                            arrContacts.removeAt(position)
                            notifyItemRemoved(position)
                        })
                        .setNegativeButton("No", DialogInterface.OnClickListener(){
                            dialog, which ->

                        })
                    builder.show()
                    true
                }
            }
        }
        setAnimation(holder.itemView,position)

    }
}