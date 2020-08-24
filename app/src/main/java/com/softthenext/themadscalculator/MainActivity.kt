package com.softthenext.themadscalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    //ArrayList to store History
    private var mHistoryList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ArrayList Initialization
        mHistoryList = ArrayList()

        //Numbers
        tvOne.setOnClickListener { mAppendOnExpression("1", true) }
        tvTwo.setOnClickListener { mAppendOnExpression("2", true) }
        tvThree.setOnClickListener { mAppendOnExpression("3", true) }
        tvFour.setOnClickListener { mAppendOnExpression("4", true) }
        tvFive.setOnClickListener { mAppendOnExpression("5", true) }
        tvSix.setOnClickListener { mAppendOnExpression("6", true) }
        tvSeven.setOnClickListener { mAppendOnExpression("7", true) }
        tvEight.setOnClickListener { mAppendOnExpression("8", true) }
        tvNine.setOnClickListener { mAppendOnExpression("9", true) }
        tvZero.setOnClickListener { mAppendOnExpression("0", true) }

        //Operators
        tvPlus.setOnClickListener { mAppendOnExpression("+", false) }
        tvMinus.setOnClickListener { mAppendOnExpression("-", false) }
        tvMul.setOnClickListener { mAppendOnExpression("*", false) }
        tvDivide.setOnClickListener { mAppendOnExpression("/", false) }


        //On single click erase single character from textView
        tvClear.setOnClickListener {
            val string = tvExpression.text.toString()
            if(string.isNotEmpty()){
                tvExpression.text = string.substring(0,string.length-1)
            }
            tvResult.text = ""
        }

        //On Long click erase all character from textView
        tvClear.setOnLongClickListener{
            tvExpression.text = ""
            tvResult.text = ""
            return@setOnLongClickListener true
        }

        tvEquals.setOnClickListener {
            try {

                val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble()){
                    (mHistoryList as ArrayList<String>).add(tvExpression.text.toString()+" = "+longResult)
                    tvResult.text = longResult.toString()
                }
                else{
                    (mHistoryList as ArrayList<String>).add(tvExpression.text.toString()+" = "+longResult)
                    tvResult.text = result.toString()
                }
            }catch (e:Exception){
                Log.d("Exception"," message : " + e.message )
            }
        }
    }

    //Function for Append Our Expression
    private fun mAppendOnExpression(string: String, canClear: Boolean) {
        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if (canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        } else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }
    }

    //This function is used to show history
    private fun showHistoryDialog(mHistoryList: List<String>) {
        val layoutInflater = layoutInflater
        @SuppressLint("InflateParams") val view =
            layoutInflater.inflate(R.layout.dialog_show_history, null)
        // Dialog
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("History")
        builder.setIcon(android.R.drawable.ic_menu_recent_history)
        builder.setView(view)
        builder.setCancelable(true)

        //Alert Dialog
        val alertDialog = builder.create()

        //Stock Name Spinner
        val spinnerHistory = view.findViewById<Spinner>(R.id.spinnerHistory)
        spinnerHistory.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "You Select Nothing!", Toast.LENGTH_SHORT).show()
            }
        }
        //ARRAY_LIST
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, mHistoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHistory.adapter = adapter
        //Show Dialog
        alertDialog.show()
    }

    //Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_history ->{
                if (mHistoryList!!.isEmpty()) {
                    Toast.makeText(this, "No History Found!", Toast.LENGTH_SHORT).show()
                } else {
                    //call Show history dialog
                    showHistoryDialog(mHistoryList!!)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Exit from Application
    override fun onBackPressed() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Do you want to exit?")
            .setCancelable(false)
            .setNegativeButton(
                android.R.string.no
            ) { _, _ -> System.gc() }
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                System.gc()
                finish()
                moveTaskToBack(true)
                exitProcess(0)
            }.create().show()
    }
}
