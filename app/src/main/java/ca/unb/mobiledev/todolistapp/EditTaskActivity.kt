package ca.unb.mobiledev.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.MainActivity.Companion.EDIT
import ca.unb.mobiledev.todolistapp.database.Task
import java.text.SimpleDateFormat
import java.util.*


class EditTaskActivity : AppCompatActivity() {

    private lateinit var timerSwitch: Switch
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var timerText: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var hrEditText: EditText
    private lateinit var minEditText: EditText
    private lateinit var secEditText: EditText
    private lateinit var editText: EditText
    private lateinit var notesText: EditText
    private lateinit var hashTagText: EditText
    private lateinit var calendarView: CalendarView

    private var startInMs = 0L
    private var isRunning: Boolean = false
    private var isPaused: Boolean = false
    private var timeInMs = 0L
    private var elapsedTime = 0
    private var startTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task)

        val task = Task.Builder().build()

        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        editText = findViewById(R.id.taskEditText)
        notesText = findViewById(R.id.notesEditText)
        hashTagText = findViewById(R.id.hashTagEditText)
        calendarView = findViewById(R.id.calendar)
        minEditText = findViewById(R.id.minEditText)
        secEditText = findViewById(R.id.secEditText)
        hrEditText = findViewById(R.id.hourEditText)
        timerText = findViewById(R.id.timerText)
        resetButton = findViewById(R.id.resetButton)
        timerSwitch = findViewById(R.id.timerSwitch)


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
//        timerText.text = convertSecondsToHours(task.elapsedTime!!)

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
                    startInMs = timeInMs
//                    startTime = convertTimeToSs(hrEditText, minEditText, secEditText)

                    //Pull down keyboard
                    val mgr: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mgr.hideSoftInputFromWindow(hrEditText.windowToken, 0)
                    mgr.hideSoftInputFromWindow(minEditText.windowToken, 0)
                    mgr.hideSoftInputFromWindow(secEditText.windowToken, 0)

                    startTimer(timeInMs)
                }
            }
            }

//        }
        resetButton.setOnClickListener{resetTimer()}

        calendarView.setOnDateChangeListener { _, year, month, day ->
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
            intent.putExtra("elapsedTime", elapsedTime)
//            intent.putExtra("elapsedTime", convertTimeStringToSeconds())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun resetTimer() {
        timeInMs = 0L
        updateTextUI()
        resetButton.visibility = View.INVISIBLE
        isPaused = false
        timerSwitch.isChecked = false
        timerSwitch.text = "START"
        isRunning = false
        startInMs = 0
        elapsedTime = 0
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

    private fun convertTimeToSs(
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
        val min = minute.toInt()  * 60
        val sec = second.toInt() * 1

        return hr + min + sec
    }

    private fun convertTimeStringToSeconds(): Int {
        val time = timerText.text.split(":").toTypedArray()

        val hour = time[0].toInt()
        val minute = time[1].toInt()
        val second = time[2].toInt()

        var endingTime = second + 60 * minute + 3600 * hour

        //Get how much time the user worked on the task
        endingTime = startTime - endingTime
//        Log.v("Elapsed Time", s.toString())
        return endingTime
    }

    private fun convertSecondsToHours(
        totalSecs: Int
    ): String {
        Log.v("Time", totalSecs.toString())
        val hours = totalSecs / 3600
        val minutes = (totalSecs % 3600) / 60
        val seconds = totalSecs % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerSwitch.text = "START"
        isRunning = false
        isPaused = true
        resetButton.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        cancelButton.visibility = View.VISIBLE
//        Toast.makeText(this,timeInMs.toInt().toString(),Toast.LENGTH_SHORT).show()
        elapsedTime = ((startInMs - timeInMs)/1000).toInt()
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
        saveButton.visibility = View.INVISIBLE
        cancelButton.visibility = View.INVISIBLE
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

