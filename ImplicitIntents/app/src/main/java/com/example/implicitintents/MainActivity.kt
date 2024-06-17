package com.example.implicitintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnDial:Button=findViewById(R.id.btnDial)
        val btnMsg:Button=findViewById(R.id.btnMsg)
        val btnEmail:Button=findViewById(R.id.btnEmail)
        val btnShare:Button=findViewById(R.id.btnShare)

        btnDial.setOnClickListener{
            val iDial=Intent(Intent.ACTION_DIAL)
            iDial.setData(Uri.parse("tel: +917903752324"))
            startActivity(iDial)
        }

        btnMsg.setOnClickListener{
            val iMsg=Intent(Intent.ACTION_SENDTO)
            iMsg.setData(Uri.parse("smsto:"+Uri.encode("+917903752324")))
            iMsg.putExtra("sms_body","This is msg")
            startActivity(iMsg)
        }

        btnEmail.setOnClickListener{
            val iEmail=Intent(Intent.ACTION_SEND)
            iEmail.setType("message/rfc822")
            iEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("ankieekumar@gmail.com"))
            iEmail.putExtra(Intent.EXTRA_SUBJECT,"Queries")
            iEmail.putExtra(Intent.EXTRA_TEXT,"Please Solve Query")

            startActivity(Intent.createChooser(iEmail,"Email via"))
        }

        btnShare.setOnClickListener{
            val iShare=Intent(Intent.ACTION_SEND)
            iShare.setType("text/plain")
            iShare.putExtra(Intent.EXTRA_TEXT,"This is Share Msg")
            startActivity(Intent.createChooser(iShare,"Share Via"))
        }
    }
}