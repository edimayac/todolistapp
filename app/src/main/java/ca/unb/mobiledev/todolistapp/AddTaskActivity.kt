package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.editText)

        // Initializing the array lists and the adapter
        var itemlist = arrayListOf<String>()
        val title = itemlist.add(editText.text.toString())


        cancelButton.setOnClickListener() {
            finish()
        }
        saveButton.setOnClickListener() {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            intent.putExtra("title", title)
            startActivity(intent)


        }


    }
}

