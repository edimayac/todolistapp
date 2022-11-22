package ca.unb.mobiledev.todolistapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.model.Task

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val dueDate = findViewById<EditText>(R.id.dueDateEditText)


        // Initializing the array lists and the adapter
        lateinit var tasks: ArrayList<Task>
        val prevTitle = intent.getStringExtra("item")
        editText.setText(prevTitle)
        val title = editText.text
//        val taskName = Task.Builder("one",title.toString(),"hi","hello",10)
//        tasks.add(taskName.build())




        cancelButton.setOnClickListener() {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        saveButton.setOnClickListener() {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            intent.putExtra("title", title.toString())
            intent.putExtra("prevTitle", prevTitle)
            setResult(RESULT_OK, intent)
            finish()
        }



    }
}

