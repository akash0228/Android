package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        noteViewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(NoteViewModel::class.java)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.floatingActionButton.setOnClickListener{
            val intent=Intent(this,DataInsertActivity::class.java)
            intent.putExtra("type","add more")
            startActivityForResult(intent,1)
        }
        binding.rv.setLayoutManager(LinearLayoutManager(this))
        binding.rv.setHasFixedSize(true)

        val adapter=RVAdapter(NoteDiffCallback())
        binding.rv.setAdapter(adapter)

        noteViewModel.getAllNotes().observe(this,Observer<List<Note>>(){
            notes -> adapter.submitList(notes)
        })

        val itemTouchHelperCallback = ItemTouchHelper(
            object:ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if(direction==ItemTouchHelper.RIGHT) {
                        Toast.makeText(this@MainActivity,"note deleted",Toast.LENGTH_SHORT).show()
                        noteViewModel.delete(adapter.getNote(viewHolder.adapterPosition))
                    }
                    else{
                        Toast.makeText(this@MainActivity,"Updating",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this@MainActivity,DataInsertActivity::class.java)
                        intent.putExtra("title",adapter.getNote(viewHolder.adapterPosition).title)
                        intent.putExtra("disp",adapter.getNote(viewHolder.adapterPosition).disp)
                        intent.putExtra("id",adapter.getNote(viewHolder.adapterPosition).id)
                        intent.putExtra("type","update")
                        startActivityForResult(intent,2)
                    }
                }

            }
        ).attachToRecyclerView(binding.rv)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("TAG", "onActivityResult: $data $resultCode")
        if(requestCode==1){
//            Log.d("TAG", "onActivityResult: DATA ADDED")
            val title:String= data?.getStringExtra("title").toString()
            val disp:String=data?.getStringExtra("disp").toString()
            val note=Note(title=title,disp=disp)
            noteViewModel.insert(note)
            Toast.makeText(this,"note added",Toast.LENGTH_SHORT).show()
        }
        else if(requestCode==2){
            val title:String= data?.getStringExtra("title").toString()
            val disp:String=data?.getStringExtra("disp").toString()
            val note=Note(title=title,disp=disp)
            if (data != null) {
                note.id=data.getIntExtra("id",0)
            }
            noteViewModel.update(note)
            Toast.makeText(this,"note updated",Toast.LENGTH_SHORT).show()
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            // Compare unique identifiers of the items, for example, their IDs
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            // Compare the contents of the items, such as their properties (title, disp, etc.)
            return oldItem.title == newItem.title && oldItem.disp == newItem.disp
        }
    }

}