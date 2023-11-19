package com.keyur.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import kotlinx.android.main.ticket.view.*


class MainActivity : AppCompatActivity() {
    var listNotes = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadQuerry("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuerry("%")
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    fun LoadQuerry(title:String)
    {
        var lvNotes = findViewById<ListView>(R.id.lvNotes)
        val dbManager = dbManager(this)
        val projections = arrayOf("ID","Title","Description")
        val selectionArgs = arrayOf(title)
        val curser = dbManager.Query(projections,"Title like ?",selectionArgs,"Title")
        listNotes.clear()
        if (curser.moveToFirst())
        {
            do{
                val ID = curser.getInt(curser.getColumnIndex("ID"))
                val Title = curser.getString(curser.getColumnIndex("Title"))
                val Description = curser.getString(curser.getColumnIndex("Description"))

                listNotes.add(Notes(ID,Title,Description))
            }while (curser.moveToNext())
        }

        var MyNotesAdapter = MyNotesAdapter(this, listNotes)
        lvNotes.adapter = MyNotesAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            when(item.itemId) {
                R.id.addNote -> {
                    var intent = Intent(this,AddNote::class.java)
                    startActivity(intent)
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter {


        var listNotesAdapter = ArrayList<Notes>()
        var context: Context? = null

        constructor(context: Context,listNotesAdapter: ArrayList<Notes>) : super() {
            this.listNotesAdapter = listNotesAdapter
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNote = listNotesAdapter[position]
            myView.tvTitle.text = myNote.notesName
            myView.tvDes.text = myNote.notesDes
            myView.ivDelete.setOnClickListener(View.OnClickListener {
                var dbmanager = dbManager(this.context!!)
                var selectionArgs = arrayOf(myNote.noteID.toString())
                dbmanager.delete("ID=?",selectionArgs)
                LoadQuerry("%")
            })
            myView.ivEdit.setOnClickListener(View.OnClickListener {

                GoToUpdate(myNote)
            })

            return myView
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }

    }


    fun GoToUpdate(note:Notes)
    {
        var intent = Intent(this,AddNote::class.java)
        intent.putExtra("ID",note.noteID)
        intent.putExtra("Name",note.notesName)
        intent.putExtra("Description",note.notesDes)

        startActivity(intent)
    }
}