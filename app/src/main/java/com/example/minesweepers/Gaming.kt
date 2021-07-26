package com.example.minesweepers

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Gaming : AppCompatActivity() {
    var status=Status.ONGOING  //INITIALLY THE STATUS OF GAME IS ONGOING
    private var totalmines:Int=0 //TOTAL NUMBER OF MINES PRESENT ON THE BOARD INITIALLY BEFORE SELECTING A LEVEL OR MAKING CUSTOM BOARD
    private var flaggedmines:Int=0 //TOTAL NO OF MINES FLAGGED BY THE USER INITIALLY IT IS 0
    private var choice:Int=1
    private var isfirstchance:Boolean=true //INITIALLY THE USER HAVE NOT STARTED THE GAME
    private var besttime:Int=0
    private var lasttime:Int=0
    lateinit var timer: Chronometer



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gaming)
        val boardselected:Int=intent.getIntExtra("customselect",2) //GETTING THE VALUE PASSED USING INTENT TO KNOW ANY BOARD WAS SELECTED OR CUSTOM BOARD IS MADE
        val levelstring:Int=intent.getIntExtra("gaminglevel",1) //GETTING THE VALUE PASSED USING TO KNOW WHICH LEVEL FROM EASY,MEDIUM AND HARD WAS SELECTED
        val colorbtn:Button=findViewById(R.id.minecolor)
        val restartbtn:ImageButton=findViewById(R.id.restartbtn) //FINDING THE RESTART BUTTON FROM THE SCREEN
        //SETTING ON CLICK LISTENER ON THE RESTART BUTTON IT RESTARTS THE GAME WHEN USER CLICKS ON IT
        restartbtn.setOnClickListener(){
            val builder=AlertDialog.Builder(this)
            builder.setTitle("RESTART GAME")
            builder.setMessage("DO WANT TO RESTART THE GAME YOUR ALL PROGRESS WILL BE LOST!!!\n"+"CLICK ON YES TO RESTART AND NO OTHERWISE")
            builder.setNegativeButton("NO")
            {
                    DIALOG,WHICH->
            }
            builder.setPositiveButton("YES")
            {
                    dialog,which->
                val intent = intent
                finish()
                startActivity(intent)
            }
            val dialog=builder.create()
            dialog.show()
        }
       //SETTING ONCLICK LISTENER TO CHANGE THE COLOR OF MINES ON EASY LEVEL
        colorbtn.setOnClickListener{
            if(isfirstchance==false)
            {
                val builder=AlertDialog.Builder(this)
                builder.setTitle("COLOR CHANGE ALERT")
                builder.setMessage("YOU HAVE ALREADY MADE YOUR FIRST MOVE IF YOU CHANGE THE COLOR THE GAME WILL RESTART ITSELF\n"+"DO YOU STILL WANT TO CHANGE MINE COLOR")
                builder.setNegativeButton("NO")
                {
                    dialog,which->
                }
                builder.setPositiveButton("YES")
                {
                    dialog,which->
                    val intent=Intent(this,MineColor::class.java)
                    startActivity(intent)
                }
                val dialog=builder.create()
                dialog.show()
            }
            else
            {
                val intent=Intent(this,MineColor::class.java)
                startActivity(intent)
            }
        }
        colorbtn.isEnabled=true
        if(boardselected==2) //IF ANY LEVEL WAS SELECTED
        {//EASY RADIO BUTTON WAS CLICKED
         if(levelstring.equals(1))
         {
            settingmyboard(9,9,9) //CALLING FUNCTION TO MAKE BOARD DYNAMICALLY


         }
         //MEDIUM RADIO BUTTON WAS CLICKED
            else if(levelstring.equals(2))
         {
            settingmyboard(12,12,12)
             colorbtn.isEnabled=false

            }
            //HARD RADIO BUTTON WAS CLICKED
            else if(levelstring.equals(3))
         {
                settingmyboard(16,16,16)
             colorbtn.isEnabled=false

            }
        }
        else //CUSTOM BOARD IS CHOSEN
        {
            val row:Int=intent.getIntExtra("height1",0) //GETTING THE VALUE OF HEIGHT PASSED THROUGH INTENT
            val col:Int=intent.getIntExtra("width1",0) //GETTING THE VALUE OF WIDTH PASSED THROUGH INTENT
            val mines:Int=intent.getIntExtra("mines1",0) //GETTING THE VALUE OF MINES NO PASSED THROUGH INTENT
            settingmyboard(row,col,mines)  //CALLING FUNCTION TO MAKE BOARD DYNAMICALLY
            colorbtn.isEnabled=false
        }


    }
 //FUNCTION TO SET GAME BOARD USING DYNAMIC LAYOUT
    private fun settingmyboard(row: Int, col: Int, minesno: Int)
    {   totalmines=minesno
        val colornumber:Int=intent.getIntExtra("colorn",0)
        val emojiimage:ImageView=findViewById(R.id.emoji)
        optionselectbyuser()
        //FINDING THE VERTICAL LINEAR LAYOUT FROM THE XML LAYOUT FILE
        val mainboard=findViewById<LinearLayout>(R.id.boards)
        //SETTING PARAMETER FOR HORIZONTAL LINEAR LAYOUT
        val params1=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )
        var counter=1
        //SETTING PARAMETER FOR THE BUTTON
        val params2=LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val arrbtn=Array(row){Array(col){MineCell(this)} }
        for(i in 0 until row)
        {
            val linearLayout=LinearLayout(this)
            linearLayout.orientation=LinearLayout.HORIZONTAL
            linearLayout.layoutParams=params1
            params1.weight=1.0f
            for(j in 0 until col)
            {
                val button=MineCell(this)
                arrbtn[i][j]=button
                button.textSize=19.0f
                button.layoutParams=params2
                params2.weight=1.0f
                button.id=counter

                button.setBackgroundResource(R.drawable.boardblocks)
                when(colornumber)
                {
                    1->button.setBackgroundResource(R.drawable.boardblocks)
                    2->button.setBackgroundResource(R.drawable.redblock)
                    3->button.setBackgroundResource(R.drawable.greens)
                    4->button.setBackgroundResource(R.drawable.yellows)
                    5->button.setBackgroundResource(R.drawable.reds)
                }
                button.setOnClickListener()
                {
                    if(isfirstchance)
                    {
                        isfirstchance=false
                        starttimer()
                        //CALLING FUNCTION TO SET MINE AFTER FIRST MOVE
                        setminesonboard(i,j,arrbtn,row,col,minesno)
                    }
                    movethebutton(choice,i,j,arrbtn,row,col,minesno,colornumber) //CALLING MOVE FUNCTION BY THE USER BY PASSING THE CHOICE
                    //CHECKING THE STATUS OF THE GAME
                    if(status==Status.LOST) //GAME LOST
                    { displaylostlayout(arrbtn,i,j)
                     emojiimage.setImageResource(R.drawable.sademoji)
                        callgamelostintent()
                        savegamescores()
                    }
                    else
                    {
                        if(status==Status.ONGOING) //GAME ONGOING
                    {display(arrbtn)}
                    else if(status==Status.WON) //GAME WON
                    {   Toast.makeText(this,"You Won",Toast.LENGTH_SHORT).show()
                        displaywinningboard(arrbtn)
                        emojiimage.setImageResource(R.drawable.winningemoji)
                        savegamescores()
                        displaywinningscreen()
                    }
                    }
                }
                linearLayout.addView(button)
                counter++
            }
            mainboard.addView(linearLayout)

        }
    }
    //FUNCTION TO KNOW THE CHOICE OF USER IF THE USER WANT'S TO REVEAL THE CELL OR MARK IT USING FLAG
    private fun optionselectbyuser() {
         val choosebtn:ImageView=findViewById(R.id.choicebutton) //FINDING THE CHOICE BUTTON FROM LAYOUT
         val minestext:TextView=findViewById(R.id.minecountertext) //FINDING TEXT VIEW TO SHOW INITIAL NO OF MINES
         minestext.text=totalmines.toString() //SETTING THE VALUE OF TEXT VIEW BY THE MINES AT STARTING
         choosebtn.setOnClickListener() //SETTING ONCLICKLISTENER ON THE CHOICE BUTTON
         {
             if(choice==1) //CHOICE WHEN USER WANT TO MARK THE CELL
             {
                 choosebtn.setImageResource(R.drawable.flagicon)
                 choice=2
             }
             else //CHOICE WHEN USER WANT TO REVEAL THE CELL
             {
                 choosebtn.setImageResource(R.drawable.bomb)
                 choice=1
             }
         }
    }

    //FUNCTION TO SET MINES ON THE BOARD RANDOMLY BY GETTING RANDOM VALUES OF ROW AND COLUMN
    private fun setminesonboard(srow: Int, scol: Int, arrbtn: Array<Array<MineCell>>, maxrow: Int, maxcol: Int,mines:Int)
    {
        val totalmines:Int=mines //TOTAL NO OF MINES PRESENT
        var i:Int=1
        //RUNNING A LOOP TO PUT GIVEN NO OF MINES ON BOARD
        while(i<=totalmines)
        {
            val rs= (Random(System.nanoTime()).nextInt(0,maxrow)) //GETTING ROWS RANDOMLY
            val cs= (Random((System.nanoTime())).nextInt(0,maxcol)) //GETTING COLUMNS RANDOMLY
            //CHECKING CONDITION IF THE MINECELL IS REVEALED OR ALREADY CONTAINING MINE IF IT IS TRUE THEN WE DO NOT PU MINE AT THAT POSITION
            if(!arrbtn[rs][cs].isRevealed && !arrbtn[rs][cs].isMine)
            {
                arrbtn[rs][cs].values= MINE //SETTING VALUE OF MINECELL AS MINE
                arrbtn[rs][cs].isMine=true //SETTING ISMINE TRUE
                updatemineneighbour(rs,cs,arrbtn,maxrow,maxcol) //CALLING UPDATE NEIGHBOUR TO PUT NUMBERS AROUND THE MINE
            }
            else
            {
                continue
            }
            i++
        }

    }
    //FUNCTION TO UPDATE THE MINE NEIGHBOURS
    private fun updatemineneighbour(r: Int, c: Int, arrbtn: Array<Array<MineCell>>, maxrow: Int, maxcol: Int)
    {
        for(i in movement)
        {
            for(j in movement)
            {   //CONDITION TO CHECK IF THE ROW AND COLUMN ARE IN RANGES AND THE MINECELL NOT CONTAINS MINE
                if(((r+i) in 0 until maxrow) && ((c+j) in 0 until maxcol) && !arrbtn[r+i][c+j].isMine)
                {
                    arrbtn[r+i][c+j].values++ //INCREASING THE VALUE BY ONE
                }
            }
        }
    }
    //FUNCTION TO SHOW THE LOST LAYOUT WHEN USER LOOSES THE GAME
    private fun displaylostlayout(arrbtn: Array<Array<MineCell>>,r:Int,c:Int)
    {   arrbtn[r][c].setBackgroundResource(R.drawable.minelostred) //SETTING THE BACKGROUND OF  THE MINE CLICKED
        arrbtn[r][c].values=10
        arrbtn.forEach { row->
            row.forEach {
                if(it.values==-1)
                {it.setBackgroundResource(R.drawable.mines)}
                else if(it.values==0)
                {it.setBackgroundResource(R.drawable.zeroes)}
                else
                {setbackground(it)}
            }
        }
    }
    //FUNCTION TO DISPLAY THE ONGOING CONDITION OF THE BOARD
    private fun display(arrbtn: Array<Array<MineCell>>) {
        arrbtn.forEach { row ->
            row.forEach {
                if (it.isRevealed) {
                    if (it.values == 0) {
                        it.setBackgroundResource(R.drawable.zeroes)
                    } else {
                        setbackground(it)
                    }
                } else if (it.isMarked) {
                    it.setBackgroundResource(R.drawable.flagged)
                }
            }

        }
    }
    //FUNCTION TO DISPLAY WINNING BOARD IN WHICH MINES BLOCK WILL BE SHOWN AS CROSSED FLAG
    private fun displaywinningboard(arrbtn: Array<Array<MineCell>>)
    {
        arrbtn.forEach { row->
            row.forEach {
                if(it.values==0)
                {it.setBackgroundResource(R.drawable.zeroes)}
                else if(it.isMarked==true)
                {it.setBackgroundResource(R.drawable.crossedflag)}
                else
                {setbackground(it)}
            }
        }
    }
    //FUNCTION TO RECORD THE MOVE OF USER THAT IS REVEAL OR MARK AND UPDATE THE STATUS OF THE GAME
    private fun movethebutton(choice: Int, i: Int, j: Int, arrbtn: Array<Array<MineCell>>, row: Int, col: Int,minesno:Int,colornumber:Int):Boolean
    {
        val minecounter:TextView=findViewById(R.id.minecountertext)
        minecounter.text=totalmines.toString()
        if(choice==1) //THIS MEANS YOU WANT TO REVEAL THE CELL
        {   //CONDITION WHEN THE CELL IS ALREADY REVEALED OR MARKED
            if(arrbtn[i][j].isRevealed || arrbtn[i][j].isMarked)
            {
                Toast.makeText(this,"You Cannot Reveal This Block",Toast.LENGTH_SHORT).show()
                return false
            }
            //CONDITION WHEN THE CELL CONTAINING MINE IS REVEALED
            if(arrbtn[i][j].isMine)
            {
                status=Status.LOST
                Toast.makeText(this,"You Lost",Toast.LENGTH_LONG).show()

                return true
            }
            //CONDITION WHEN CORRECT CELL IS REVEALED
            else
            {
                revealthecell(arrbtn,i,j,row,col)
                checkgamestatus(i,j,arrbtn, row, col,minesno)
            }
        }
        else //THIS MEANS YOU WANT TO MARK THE CELL
        {   //CONDITION WHEN THE CELL IS REVEALED
            if(arrbtn[i][j].isRevealed)
            {Toast.makeText(this,"You Cannot Mark A Revealed Box",Toast.LENGTH_SHORT).show()
                return false}
            else
            {
                arrbtn[i][j].isMarked=!arrbtn[i][j].isMarked //SETTING THE MARKED VALUE AS OPPOSITE
                if(!arrbtn[i][j].isMarked) //IF WE WANT TO UNMARK THE MARKED MINES
                {  if(colornumber==0)
                {arrbtn[i][j].setBackgroundResource(R.drawable.boardblocks)}
                else
                {when(colornumber)
                {
                  1->arrbtn[i][j].setBackgroundResource(R.drawable.boardblocks)
                    2->arrbtn[i][j].setBackgroundResource(R.drawable.redblock)
                    3->arrbtn[i][j].setBackgroundResource(R.drawable.greens)
                    4->arrbtn[i][j].setBackgroundResource(R.drawable.yellows)
                    5->arrbtn[i][j].setBackgroundResource(R.drawable.reds)
                }}
                    flaggedmines-- //FLAGGED MINES DECREASES
                    totalmines++ //TOTAL MINES INCREASES
                    minecounter.text=totalmines.toString()
                    checkgamestatus(i,j,arrbtn, row, col,minesno)
                }
                else //WHEN WE WANT TO MARK THE CELL
                {
                    flaggedmines++
//                    if(flaggedmines==minesno)
//                    {
//                        Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show()
//                    }
                    totalmines--
                    minecounter.text=totalmines.toString()
                    checkgamestatus(i,j,arrbtn, row, col,minesno)
                }
                return true
            }
        }
        return false
    }
    //FUNCTION TO REVEAL THE CELL IF REVEAL OPTION IS SELECTED BY THE USER
    private fun revealthecell(arrbtn: Array<Array<MineCell>>, r: Int, c: Int, row: Int, col: Int)
    {
        if(!arrbtn[r][c].isRevealed && !arrbtn[r][c].isMarked)
        {
            arrbtn[r][c].isRevealed=true //SETTING THE CLICKED BUTTON AS REVEALED
            if(arrbtn[r][c].values==0) //IF THE CLICKED BUTTON WAS 0 THEN REVEALING IT'S NEIGHBOURING CELL
            {
                for(i in movement)
                {
                    for(j in movement)
                    {
                        if((i!=0 || j!=0) && ((r+i) in 0 until row) && ((c+j) in 0 until col))
                        {
                            revealthecell(arrbtn,r+i,c+j,row,col)
                        }
                    }
                }
            }
        }
    }
    //FUNCTION TO CHECK THE STATUS OF THE GAME
    private fun checkgamestatus(i: Int, j: Int, arrbtn: Array<Array<MineCell>>, row: Int, col: Int,minesno:Int) :Boolean
    {
        var flag1=0
        var flag2=0
        var counter=0
        arrbtn.forEach { row ->
            row.forEach {
                if (it.values == MINE && it.isMarked == false) {
                    flag1 = 1
                }
                if (it.isRevealed == false) {
                    flag2 = 1
                }
                if (it.values == MINE && it.isMarked) {
                    counter++
                }

            }
        }

        if(counter==minesno) //CONDITION WHEN ALL THE MINES ARE MARKED
        {status=Status.WON
            return true}
        if(flag1!=0 || flag2!=0) //CONDITION WHEN ALL MINES ARE NOT MARKED
        {status=Status.ONGOING
            return false}
        else //CONDITION WHEN ALL THE MINES ARE MARKED AND ALL CELLS ARE REVEALED
        {
            status=Status.WON
            return true
        }
    }
    //FUNCTION TO GIVE DIFFERENT BACKGROUND AT DIFFERENT VALUE OF MINECELL
    private fun setbackground(it: MineCell) {
        if(it.values==1){it.setBackgroundResource(R.drawable.ones)}
        else if(it.values==2){it.setBackgroundResource(R.drawable.twos)}
        else if(it.values==3){it.setBackgroundResource(R.drawable.threes)}
        else if(it.values==4){it.setBackgroundResource(R.drawable.fours)}
        else if(it.values==5){it.setBackgroundResource(R.drawable.fives)}
        else if(it.values==6){it.setBackgroundResource(R.drawable.sixs)}
        else if(it.values==7){it.setBackgroundResource(R.drawable.sevens)}
        else if(it.values==8){it.setBackgroundResource(R.drawable.eights)}
    }
    //FUNCTION TO SET THE TIMER AFTER THE FIRST MOVE BY THE USER
    fun starttimer()
    {   timer=findViewById(R.id.chronometertext)
        timer.base=SystemClock.elapsedRealtime()
        timer.start()
    }
    //FUNCTION TO SHOW  THE GAME LOST SCREEN AFTER SOME DELAY USING HANDLER
    private fun callgamelostintent()
    { val intent=Intent(this,GameLost::class.java)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(intent)
        }, 2000)

    }
    //FUNCTION TO SHOW  THE GAME WON SCREEN AFTER SOME DELAY USING HANDLER
    private fun displaywinningscreen() {
       val intent=Intent(this,WinningScreen::class.java)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(intent)
        }, 2000)
    }
    //FUNCTION TO UPDATE THE GAME SCORES AND PASS THE TIME TO THE MAIN ACTIVITY
    private fun savegamescores() {
        timer.stop()
        val timetaken = SystemClock.elapsedRealtime() - timer.base //TIME TAKEN BY THE USER
        val timebyuser: Int = timetaken.toInt() / 1000 //CONVERTING MILLISECONDS TO SECONDS
        val sharedPref1:SharedPreferences=getSharedPreferences("timing", MODE_PRIVATE)
        val sharedPref2:SharedPreferences=getSharedPreferences("highscoring", MODE_PRIVATE)
        val getPref2:SharedPreferences=getSharedPreferences("highscoring", MODE_PRIVATE)
        var besttime:String
        var lasttime:String
        var highscores=getPref2.getInt("scoring", Int.MAX_VALUE)
        if(status==Status.WON)
        {
            if(highscores>timebyuser)
            {
                highscores=timebyuser
                sharedPref2.edit().putInt("scoring",highscores).apply()
                besttime=highscores.toString()
                lasttime=timebyuser.toString()
                sharedPref1.edit().putString("bestest",besttime).apply()
                sharedPref1.edit().putString("latest",lasttime).apply()
            }
            else if(highscores<timebyuser)
            {
                lasttime=timebyuser.toString()
                sharedPref1.edit().putString("latest",lasttime).apply()
            }
        }
        else if(status==Status.LOST)
        {
            lasttime="YOU LOST"
            sharedPref1.edit().putString("latest",lasttime).apply()
        }



    }
    //USE OF COMPANION OBJECT WHICH STORES THE VALUE OF BLOCK CONTAINING MINE AND THE MOVEMENT POSSIBLE IN THE DIRECTIONS
    companion object{
        const val MINE=-1
        val movement= intArrayOf(-1,0,1)
    }
    //USE OF ENUM CLASS WHICH CONTAINS ALL POSSIBLE STATES OF GAME THAT IS WON,ONGOING,LOST
    enum class Status{
        WON,
        ONGOING,
        LOST
    }
   //OVERRIDING THE FUNCTION OF BACK BUTTON WHEN USER CLICKS ON IT
    override fun onBackPressed() {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("EXIT THE GAME")
        builder.setMessage("DO YOU WANT TO QUIT THE ONGOING GAME?")
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


