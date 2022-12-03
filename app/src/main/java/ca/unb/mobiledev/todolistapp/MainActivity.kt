package ca.unb.mobiledev.todolistapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.todolistapp.database.Task
import ca.unb.mobiledev.todolistapp.database.TaskDatabaseAdapter
import ca.unb.mobiledev.todolistapp.database.TaskViewModel
import ca.unb.mobiledev.todolistapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf<Task>()
    private var checkedTaskPositions: SparseBooleanArray = SparseBooleanArray()
    private lateinit var listView: ListView
    private lateinit var taskAdapter: TaskDatabaseAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bottom Navigation Bar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationBar()


        

        /*
            Set and Initialize Database Adapter
        */
        taskAdapter = TaskDatabaseAdapter(this@MainActivity, taskList)
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        listView = findViewById(R.id.listView)
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listView.adapter = taskAdapter
        taskList.addAll(taskViewModel.getAllTasks())
        taskAdapter.notifyDataSetChanged()

        // Adding the toast message to the list when an item on the list is pressed
        listView.setOnItemClickListener { _, _, i, _ ->
            Log.e("Task.isChecked", "You Selected the item --> " + taskList[i].isChecked)
        }

        listView.onItemLongClickListener =
            OnItemLongClickListener { _, view, pos, _ ->
                Log.e("long clicked", "pos: $pos")
                optionsMenu(view, pos)
                true
            }

        /*
           Initialize and Set Button Click Listeners
           1. Add
           2. Clear
           3. Delete
           4. Edit
        */
        // Adding the items to the list when the add button is pressed

        // Buttons
        val addButton = findViewById<Button>(R.id.addButton)
        val clear = findViewById<Button>(R.id.clearButton)
        val delete = findViewById<Button>(R.id.delete)
        val add = findViewById<Button>(R.id.addTaskBtn)
        val addTaskEditText = findViewById<EditText>(R.id.addTaskEditText)

        addButton.setOnClickListener {
            //Pull down keyboard
            val mgr: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(addTaskEditText.windowToken, 0)

            if (addTaskEditText.text.isNotEmpty()) {
                val newTask = Task.Builder().name(addTaskEditText.text.toString()).build()
                newTask.id = taskViewModel.addTask(newTask)
                taskList.add(newTask)
                taskAdapter.notifyDataSetChanged()
                addTaskEditText.text.clear()
            } else {
                Toast.makeText(this@MainActivity, "Please Enter a Task Name!", Toast.LENGTH_SHORT).show()
            }
        }

        add.setOnClickListener {
            val intent = Intent(this@MainActivity, EditTaskActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {
            taskList.clear()
            taskViewModel.deleteAllTasks()
            taskAdapter.notifyDataSetChanged()
        }

        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            taskList.forEachIndexed { index, task ->
                checkedTaskPositions.put(index, task.isChecked!!)
            }

            val count = taskList.count()
            var id: Int
            var item = count - 1
            while (item >= 0) {
                if (checkedTaskPositions[item])
                {
                    id = taskList[item].id!!
                    taskList.remove(taskList[item])
                    taskAdapter.notifyDataSetChanged()
                    taskViewModel.deleteTask(id)
                }
                item--
            }

            //Reset Checkboxes
            checkedTaskPositions.clear()
        }
    }

    private fun optionsMenu(view: View, pos: Int) {
        val optionsMenu = PopupMenu(this, view)
        val menuInflater = optionsMenu.menuInflater
        menuInflater.inflate(R.menu.options_menu, optionsMenu.menu)
        optionsMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteItemOption -> {
                    val id = taskList[pos].id!!
                    taskAdapter.remove(taskList[pos])
                    taskViewModel.deleteTask(id)
                    taskAdapter.notifyDataSetChanged()
                    true
                }
                R.id.editItemOption -> {
                    val task = taskList[pos]
                    val intent = Intent(this@MainActivity, EditTaskActivity::class.java)
                    intent.putExtra("id", task.id)
                    intent.putExtra("name", task.name)
                    intent.putExtra("notes", task.notes)
                    intent.putExtra("dueDate", task.dueDate)
                    intent.putExtra("elapsedTime", task.elapsedTime)
                    intent.putExtra("hashTag", task.hashTag)
                    startActivityForResult(intent, 1)
                    true
                }
                else -> { true }
            }
        }
        optionsMenu.show()
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK) {
                val newTask = Task.Builder()
                    .id(data?.getIntExtra("id", 0)!!)
                    .name(data.getStringExtra("name")!!)
                    .notes(data.getStringExtra("notes")!!)
                    .dueDate(data.getStringExtra("dueDate")!!)
                    .hashTag(data.getStringExtra("hashTag")!!)
                    .elapsedTime(data.getIntExtra("elapsedTime", 0))
                    .build()

                if (taskList.contains(newTask)) {
                    val index = taskList.indexOf(newTask)
                    val oldTask = taskList[index]
                    taskViewModel.editTask(newTask, oldTask)
                    taskList[index] = newTask
                } else {
                    newTask.id = taskViewModel.addTask(newTask)
                    taskList.add(newTask)
                }

                taskAdapter.notifyDataSetChanged()

            } else if (resultCode == RESULT_CANCELED) {
                taskAdapter.notifyDataSetChanged()
            }
    }

    private fun bottomNavigationBar(){

        binding.bottomNavigationView.setOnItemSelectedListener{item ->(

                when (item.itemId){

                    R.id.summary -> {
                        val intent = Intent(this,SummaryActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                        finish()
                    }

                    R.id.settings -> {
                        val intent = Intent(this,SettingsActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                        finish()
                    }

                    R.id.list -> {
                        val intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                        finish()
                    }

                }
                )
            true
        }


    }


}