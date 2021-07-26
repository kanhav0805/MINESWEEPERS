package com.example.minesweepers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class GameLost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_lost)
        val tryagain:Button=findViewById(R.id.tryagain)
        tryagain.setOnClickListener()
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