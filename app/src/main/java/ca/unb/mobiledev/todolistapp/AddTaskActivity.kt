package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.database.Task

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val hashTagText = findViewById<EditText>(R.id.hashTagEditText)
        val dueDateText = findViewById<EditText>(R.id.dueDateEditText)
        val timerText = findViewById<EditText>(R.id.timerEditText)
        val timerSwitch = findViewById<Switch>(R.id.timerSwitch)

        val name = editText.text
        val notes = notesText.text
        val hashTag = hashTagText.text
        val dueDate = dueDateText.text


        cancelButton.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }

        saveButton.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
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

