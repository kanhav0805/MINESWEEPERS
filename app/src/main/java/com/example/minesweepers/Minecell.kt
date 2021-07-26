package com.example.minesweepers

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
//making class minecell containing information about every minecell
class MineCell: AppCompatButton{
    constructor(context: Context?) : super(context!!)
    var values:Int = 0 //initial value of each mine cell
    var isRevealed: Boolean = false //initially no block is revealed
    var isMarked: Boolean = false //initially no block is marked
    var isMine : Boolean = false //initially no block contains mine

}
