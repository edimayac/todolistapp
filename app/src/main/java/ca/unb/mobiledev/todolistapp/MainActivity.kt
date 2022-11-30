package ca.unb.mobiledev.todolistapp


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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

        listView = findViewById<ListView>(R.id.listView)
        listView.adapter = taskAdapter
        taskList.addAll(taskViewModel.getAllTasks()!!)
        taskAdapter.notifyDataSetChanged()

        // Adding the toast message to the list when an item on the list is pressed
        listView.setOnItemClickListener { _, _, i, _ ->
            Log.e("Task.isChecked", "You Selected the item --> " + taskList[i].isChecked)
        }

        listView.onItemLongClickListener =
            OnItemLongClickListener { _, _, pos, _ ->
                Log.e("long clicked", "pos: $pos")
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
        val edit = findViewById<Button>(R.id.editTask)

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
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
            Log.e("check", listView.checkedItemPositions.toString())
            val count = taskList.count()
            var id = 0
            var item = count - 1
            while (item >= 0) {
                if (checkedTaskPositions.get(item))
                {
                    id = taskList[item].id!!
                    taskAdapter.remove(taskList[item])
                    taskViewModel.deleteTask(id)
                }
                item--
            }
            checkedTaskPositions.clear()
            taskAdapter.notifyDataSetChanged()
        }

        // Editing the task selected when the button is pressed
        edit.setOnClickListener{
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    // Show the details on this task
                }
                item--
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK) {
                val task = Task.Builder()
                    .name(data?.getStringExtra("title")!!)
                    .notes(data?.getStringExtra("notes")!!)
                    .dueDate(data?.getStringExtra("dueDate")!!)
                    .build()

                task.id = taskViewModel.addTask(task)
                taskList.add(task)
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
                    }

                    R.id.settings -> {
                        val intent = Intent(this,SettingsActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                    }

                    R.id.list -> {
                        val intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                    }

                }
                )
            true
        }


    }


}