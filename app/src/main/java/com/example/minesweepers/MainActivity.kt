package com.example.minesweepers

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val besttime:TextView=findViewById(R.id.best_timetext) //FINDING TEXT VIEW TO SHOW BEST TIME
        val lasttime:TextView=findViewById(R.id.last_timetext) //FINDING TEXT VIEW TO SHOW LATEST TIME
        val getPref1:SharedPreferences=getSharedPreferences("timing", MODE_PRIVATE) //MAKING SHAREDPREFERENCE TO GET DATA FROM TIMING
        val besttimestring:String="BEST TIME: "//MAKING STRING TO SHOW IN TEXT VIEW
        val lasttimestring:String="LAST GAME TIME: "
        val seconds:String="s"
        val bestest:String?=getPref1.getString("bestest","0.00")
        val latest:String?=getPref1.getString("latest","0.00")
        besttime.setText(besttimestring+bestest+seconds)
        lasttime.setText(lasttimestring+latest+seconds)
        //FINDING CUSTOM BUTTON FROM LAYOUT TO ALLOW USER TO MAKE A CUSTOM BOARD
        val custombtns=findViewById<Button>(R.id.customboardbtn)
        //SETTING ONCLICKLISTNER ON CUSTOM BUTTON AND OPENING NEW ACTIVITY NAMED CUSTOM BOARD ACTIVITY
        custombtns.setOnClickListener{

            val intent=Intent(this,CustomBoardActivity::class.java)
            startActivity(intent)
        }
        //DECLARING AN INTEGER VALUE WITH 0 IT IS USED TO KNOW WHICH DEFAULT LEVEL IS SELECTED
        var levelchange:Int=0
        //FINDING DIFFERENT RADIO BUTTON'S FROM THE LAYOUT
        val easybtn=findViewById<RadioButton>(R.id.easy_radiobtn) //FINDING EASY RADIO BUTTON
        val mediumbtn=findViewById<RadioButton>(R.id.medium_radiobtn) //FINDING MEDIUM RADIO BUTTON
        val hardbtn=findViewById<RadioButton>(R.id.hard_radiobtn) //FINDING HARD RADIO BUTTON
        //CHANGING THE LEVELCHANGE VALUE FROM 0 TO 1 WHEN EASY BUTTON IS CLICKED
         easybtn.setOnClickListener()
         {
             levelchange=1
         }
        //CHANGING THE LEVELCHANGE VALUE FROM 0 TO 2 WHEN MEDIUM BUTTON IS CLICKED
          mediumbtn.setOnClickListener()
          {
              levelchange=2
          }
        //CHANGING THE LEVELCHANGE VALUE FROM 0 TO 1 WHEN HARD BUTTON IS CLICKED
        hardbtn.setOnClickListener()
        {
            levelchange=3
        }
        //FINDING THE START BUTTON FROM LAYOUT
        val start=findViewById<Button>(R.id.startbtns)
        //SETTING ONCLICKLISTENER ON START BUTTON TO START THE GAME
        start.setOnClickListener()
        {
            if(levelchange.equals(0)) //THIS MEANS LEVELCHANGE VALUE IS STILL DEFAULT i.e. 0 AND USER HAS NOT CLICKED ANY BUTTON
            {Toast.makeText(this,"No Option Selected!!Please Select It From Above",Toast.LENGTH_SHORT).show()}//SHOWING TOAST WHEN NO LEVEL IS SELECTED
            else //RUNNING ELSE WHEN ONE OF THE OPTION IS SELECTED
            {   //MAKING AN INTENT TO GO TO THE GAMING ACTIVITY AFTER CLICKING START BUTTON
                val intent=Intent(this,Gaming::class.java).apply {
                putExtra("gaminglevel",levelchange) //PASSING AN INTEGER VALUE TO ACTIVITY TO KNOW WHICH LEVEL IS SELECTED
                }
            startActivity(intent)} //STARTING THE INTENT
        }

    }

}