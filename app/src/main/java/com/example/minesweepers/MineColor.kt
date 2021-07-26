package com.example.minesweepers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MineColor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_color)
        val black:Button=findViewById(R.id.blackbtn)
        val blue:Button=findViewById(R.id.bluebtn)
        val green:Button=findViewById(R.id.greenbtn)
        val yellow:Button=findViewById(R.id.yellowbtn)
        val red:Button=findViewById(R.id.redbtn)
        var colornumber:Int=0

        black.setOnClickListener()
        {
            Toast.makeText(this,"Black Selected",Toast.LENGTH_SHORT).show()
            colornumber=1
            val intent=Intent(this,Gaming::class.java).apply {
                putExtra("colorn",colornumber)
            }
            startActivity(intent)
        }
        blue.setOnClickListener()
        {
            Toast.makeText(this,"Blue Selected",Toast.LENGTH_SHORT).show()
            colornumber=2
            val intent=Intent(this,Gaming::class.java).apply {
                putExtra("colorn",colornumber)
            }
            startActivity(intent)
        }
        green.setOnClickListener()
        {
            Toast.makeText(this,"Green Selected",Toast.LENGTH_SHORT).show()
            colornumber=3
            val intent=Intent(this,Gaming::class.java).apply {
                putExtra("colorn",colornumber)
            }
            startActivity(intent)
        }
       yellow.setOnClickListener()
        {
            Toast.makeText(this,"Yellow Selected",Toast.LENGTH_SHORT).show()
            colornumber=4
            val intent=Intent(this,Gaming::class.java).apply {
                putExtra("colorn",colornumber)
            }
            startActivity(intent)
        }
       red.setOnClickListener()
        {
            Toast.makeText(this,"Red Selected",Toast.LENGTH_SHORT).show()
            colornumber=5
            val intent=Intent(this,Gaming::class.java).apply {
                putExtra("colorn",colornumber)
            }
            startActivity(intent)
        }
    }
}