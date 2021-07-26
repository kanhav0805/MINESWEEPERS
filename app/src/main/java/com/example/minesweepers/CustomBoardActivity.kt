package com.example.minesweepers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.annotation.IntegerRes

class CustomBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_board)
        //FINDING DIFFERENT EDIT TEXT BY ID FROM THE LAYOUT
        val heightedit=findViewById<EditText>(R.id.heightedit) //HEIGHT EDIT TEXT
        val widthedit=findViewById<EditText>(R.id.widthedit)  //WIDTH EDIT TEXT
        val minesnoedit=findViewById<EditText>(R.id.minesnoedit) //MINES NO EDIT TEXT
        val submitbtn=findViewById<Button>(R.id.submitsbtn) //SUBMIT BUTTON
        //SETTING ON CLICK LISTENER ON SUBMIT BUTTON
        submitbtn.setOnClickListener()
        {var flag:Int=0
            //SHOWING AN ERROR IF USER HAVE NO ENTERED THE HEIGHT OF BOARD
            if(TextUtils.isEmpty(heightedit.text))
            {
                heightedit.setError("Please Enter Height")
                heightedit.requestFocus()
                flag=1
            }
            //SHOWING AN ERROR IF USER HAVE NO ENTERED THE WIDTH OF BOARD
            if(TextUtils.isEmpty(widthedit.text))
            {
                widthedit.setError("Please Enter Width")
                widthedit.requestFocus()
                flag=1
            }
            //SHOWING AN ERROR IF USER HAVE NO ENTERED THE MINES OF BOARD
            if(TextUtils.isEmpty(minesnoedit.text))
            {
                minesnoedit.setError("Please Enter Mines NO")
                minesnoedit.requestFocus()
                flag=1
            }
            //IF USER HAVE ENTERED ALL THE DETAILS THEN WE GO ON TO NEXT ACTIVITY BY PRESSING SUBMIT
            //CONVERTING THE EDITABLE TYPE VALUE TO STRING AND THEN TO INTEGER TYPE TO PASS IT TO THE NEXT ACTIVITY
            if(flag!=1)
            {val ht=Integer.parseInt(heightedit.text.toString()) //CONVERTING HEIGHT
            val wt= Integer.parseInt(widthedit.text.toString())//CONVERTING WIDTH
            val mn=Integer.parseInt(minesnoedit.text.toString()) //CONVERTING MINES NO
            val intent = Intent(this,Gaming::class.java).apply {
                putExtra("height1",ht)
                putExtra("width1",wt)
                putExtra("mines1",mn)
                putExtra("customselect",1) //PASSING VALUE 1 TO THE GAMING ACTIVITY TO KNOW THAT CUSTOM BOARD IS SELECTED

            }
            startActivity(intent)}
        }
    }
}