package com.example.minesweepers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class WinningScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winning_screen)
        val winningbtn:Button=findViewById(R.id.winningbtn)
        winningbtn.setOnClickListener()
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("GO TO HOME PAGE")
        builder.setMessage("DO YOU WANT TO MOVE THE HOME PAGE OF THE APP")
        builder.setPositiveButton("YES")
        {
                dialog,which->
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("NO")
        {
                dialog,which->
        }
        val dialog=builder.create()
        dialog.show()
    }
}