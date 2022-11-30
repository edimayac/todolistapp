package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val dueDateText = findViewById<EditText>(R.id.dueDateEditText)


        // Initializing the array lists and the adapter
        val name = editText.text
        val notes = notesText.text
        val dueDate = dueDateText.text
//        val taskName = Task.Builder("one",title.toString(),"hi","hello",10)
//        tasks.add(taskName.build())



        cancelButton.setOnClickListener() {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        saveButton.setOnClickListener() {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            intent.putExtra("title", name.toString())
            intent.putExtra("notes", notes.toString())
            intent.putExtra("dueDate", dueDate.toString())
            setResult(RESULT_OK, intent)
            finish()
        }



    }
}

