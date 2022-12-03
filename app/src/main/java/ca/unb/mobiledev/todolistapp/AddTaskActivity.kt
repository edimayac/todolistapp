package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.MainActivity.Companion.ADD
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val hashTagText = findViewById<EditText>(R.id.hashTagEditText)
        val calendarView = findViewById<CalendarView>(R.id.calendar)

        val name = editText.text
        val notes = notesText.text
        val hashTag = hashTagText.text
        var dueDate = SimpleDateFormat("MM/dd/yyyy").format(Date(calendarView.date))

        calendarView.setOnDateChangeListener { _, year, month, day ->
            dueDate = "$month/$day/$year"
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }

        saveButton.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            intent.putExtra("activity", ADD)
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

