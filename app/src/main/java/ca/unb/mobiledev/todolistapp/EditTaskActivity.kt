package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.database.Task

class EditTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val task = Task.Builder().build()

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val hashTagText = findViewById<EditText>(R.id.hashTagEditText)
        val dueDateText = findViewById<EditText>(R.id.dueDateEditText)

        if (intent != null) {
            task.id = intent.getIntExtra("id", 0)
            task.name = intent.getStringExtra("name")
            task.notes = intent.getStringExtra("notes")
            task.hashTag = intent.getStringExtra("hashTag")
            task.dueDate = intent.getStringExtra("dueDate")
            task.elapsedTime = intent.getIntExtra("elapsedTime", 0)
        }

        editText.setText(task.name)
        notesText.setText(task.notes)
        hashTagText.setText(task.hashTag)
        dueDateText.setText(task.dueDate)

        val name = editText.text
        val notes = notesText.text
        val hashTag = hashTagText.text
        val dueDate = dueDateText.text


        cancelButton.setOnClickListener {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        saveButton.setOnClickListener {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            intent.putExtra("name", name.toString())
            intent.putExtra("notes", notes.toString())
            intent.putExtra("hashTag", hashTag.toString())
            intent.putExtra("dueDate", dueDate.toString())
            intent.putExtra("elapsedTime", 0)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

