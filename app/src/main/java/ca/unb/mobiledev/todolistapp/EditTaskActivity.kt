package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.MainActivity.Companion.EDIT
import ca.unb.mobiledev.todolistapp.database.Task
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    lateinit var timerSwitch: Switch
    lateinit var resetButton: Button
    lateinit var timerText: TextView
    lateinit var countDownTimer: CountDownTimer
    lateinit var hrEditText: EditText
    lateinit var minEditText: EditText
    lateinit var secEditText: EditText

    var startInMs = 0L
    var isRunning: Boolean = false
    var isPaused: Boolean = false
    var timeInMs = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val task = Task.Builder().build()

        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val editText = findViewById<EditText>(R.id.taskEditText)
        val notesText = findViewById<EditText>(R.id.notesEditText)
        val hashTagText = findViewById<EditText>(R.id.hashTagEditText)
        val calendarView = findViewById<CalendarView>(R.id.calendar)

        minEditText = findViewById(R.id.minEditText)
        secEditText = findViewById(R.id.secEditText)
        hrEditText = findViewById(R.id.hourEditText)
        timerText = findViewById(R.id.timerText)
        resetButton = findViewById(R.id.resetButton)
        timerSwitch = findViewById(R.id.timerSwitch)


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
        timerText.text = convertSecondsToHours(task.elapsedTime!!)

        val name = editText.text
        val notes = notesText.text
        val hashTag = hashTagText.text
        var dueDate = SimpleDateFormat("MM/dd/yyyy").format(Date(calendarView.date))

        timerSwitch.setOnCheckedChangeListener{ _, _ ->
            if ((isRunning)) {
                Toast.makeText(this,"PAUSE", Toast.LENGTH_SHORT).show()
                pauseTimer()
            } else if(isPaused){
                Toast.makeText(this, "RESUME", Toast.LENGTH_SHORT).show()
                startTimer(timeInMs)
                isPaused = false

            } else {
                if((hrEditText.text.isEmpty())&&(minEditText.text.isEmpty())&&(secEditText.text.isEmpty())) {
                    Toast.makeText(this,"Please enter a value!", Toast.LENGTH_SHORT).show()
                    timerSwitch.isChecked = false
                } else {
                    Toast.makeText(this, "START", Toast.LENGTH_SHORT).show()
                    timeInMs = convertTimeToMs(hrEditText, minEditText, secEditText)
                    startTimer(timeInMs)
                }
            }
            }

//        }

        resetButton.setOnClickListener{resetTimer()}

        calendarView.setOnDateChangeListener { view, year, month, day ->
           dueDate = "$month/$day/$year"
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        saveButton.setOnClickListener {
            val intent = Intent(this@EditTaskActivity, MainActivity::class.java)
            intent.putExtra("activity", EDIT)
            intent.putExtra("id", task.id)
            intent.putExtra("name", name.toString())
            intent.putExtra("notes", notes.toString())
            intent.putExtra("hashTag", hashTag.toString())
            intent.putExtra("dueDate", dueDate.toString())
            intent.putExtra("elapsedTime", convertTimeToS(hrEditText, minEditText, secEditText))
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun resetTimer() {
        timeInMs = startInMs
        updateTextUI()
        resetButton.visibility = View.INVISIBLE
        isPaused = false
//        timerSwitch.isChecked = false
        timerSwitch.text = "START"
        isRunning = false
    }

    private fun convertTimeToMs(
        hrEditText: EditText,
        minEditText: EditText,
        secEditText: EditText
    ): Long {
        var hour = hrEditText.text.toString()
        var minute = minEditText.text.toString()
        var second = secEditText.text.toString()


        if(hour.isEmpty())
            hour = "0"
        if(minute.isEmpty())
            minute = "0"
        if(second.isEmpty())
            second = "0"


        val hr = hour.toLong() * 3600000L
        val min = minute.toLong() * 60000L
        val sec = second.toLong() * 1000L

        return hr + min + sec
    }

    private fun convertTimeToS(
        hrEditText: EditText,
        minEditText: EditText,
        secEditText: EditText
    ): Int {
        var hour = hrEditText.text.toString()
        var minute = minEditText.text.toString()
        var second = secEditText.text.toString()


        if(hour.isEmpty())
            hour = "0"
        if(minute.isEmpty())
            minute = "0"
        if(second.isEmpty())
            second = "0"


        val hr = hour.toInt() * 3600
        val min = minute.toInt() * 60
        val sec = second.toInt() * 1

        return hr + min + sec
    }

    private fun convertSecondsToHours(
        totalSecs: Int
    ): String {

        val hours = totalSecs / 3600;
        val minutes = (totalSecs % 3600) / 60;
        val seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerSwitch.text = "START"
        isRunning = false
        isPaused = true
        resetButton.visibility = View.VISIBLE
        clearTimeInput()


    }

    private fun startTimer(timeInMilliSeconds: Long) {
        countDownTimer = object : CountDownTimer(timeInMilliSeconds, 1000) {
            override fun onFinish() {
                timerText.text = "Time's Up!"
                resetTimer()
            }

            override fun onTick(p0: Long) {
                timeInMs = p0
                updateTextUI()
            }

        }
        countDownTimer.start()
        isRunning = true
        timerSwitch.text = "PAUSE"
        resetButton.visibility = View.INVISIBLE
        clearTimeInput()

    }

    private fun clearTimeInput() {
        hrEditText.text.clear()
        minEditText.text.clear()
        secEditText.text.clear()

    }


    private fun updateTextUI() {
        val hour = (timeInMs/1000) % 86400 / 3600
        val minute = (timeInMs / 1000) % 86400 %3600 / 60
        val seconds = (timeInMs / 1000) % 86400 %3600 %60


        timerText.text = String.format("%02d:%02d:%02d",hour,minute,seconds)

    }



}

