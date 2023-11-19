package com.keyur.todoapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AddNote : AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        var etTitle = findViewById<EditText>(R.id.etTitle)
        var etDes = findViewById<EditText>(R.id.etDes)

        try {
            var bundle:Bundle = intent.extras!!
            id = bundle.getInt("ID",0)
            if (id != 0) {
                etTitle.setText(bundle.getString("Name").toString())
                etDes.setText(bundle.getString("Description").toString())
            }
        }catch (ex:Exception)
        {

        }
    }

    fun buAdd(view: View)
    {
        var etTitle = findViewById<EditText>(R.id.etTitle)
        var etDes = findViewById<EditText>(R.id.etDes)

        var dbManag = dbManager(this)
        var values = ContentValues()
        values.put("Title", etTitle.text.toString())
        values.put("Description", etDes.text.toString())

        if (id == 0) {
            var ID = dbManag.Insert(values)
            if (ID > 0) {
                finish()
            } else {
                Toast.makeText(this, "Cannot add note", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            var selectionArray = arrayOf(id.toString())
            var ID = dbManag.update(values,"ID=?",selectionArray)
            if (ID > 0) {
                finish()
            } else {
                Toast.makeText(this, "Cannot add note", Toast.LENGTH_SHORT).show()
            }

        }

    }
}