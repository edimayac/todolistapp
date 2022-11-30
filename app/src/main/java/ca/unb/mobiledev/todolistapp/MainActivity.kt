package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.todolistapp.database.*
import ca.unb.mobiledev.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf<Task>()
    private lateinit var taskAdapter: TaskDatabaseAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: SettingsFragment
    private lateinit var summary: SummaryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
            Set and Initialize Database Adapter
         */
        taskAdapter = TaskDatabaseAdapter(this@MainActivity, taskList)

        var listView = findViewById<ListView>(R.id.listView)
        listView.adapter = taskAdapter


        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        taskList.addAll(taskViewModel.getAllTasks()!!)
        taskAdapter.notifyDataSetChanged()

        // Adding the toast message to the list when an item on the list is pressed

        listView.setOnItemClickListener { _, _, i, _ ->
            Toast.makeText(this, "You Selected the item --> "+ taskList[i], Toast.LENGTH_SHORT).show()
        }



        // Bottom Navigation Bar
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.setOnClickListener{item ->
            when(item.id){

                R.id.settings -> replaceFragment(settings)
//                {
//                    val intent = Intent(this@MainActivity,SettingsActivity::class.java)
//                    startActivity(intent)
//                }

                R.id.summary -> replaceFragment(summary)
//                {
//                    val intent = Intent(this@MainActivity,SummaryActivity::class.java)
//                    startActivity(intent)
//                }
            }
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
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var id = 0
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    id = taskList[item].id!!
                    taskAdapter.remove(taskList[item])
                }
                item--
            }
            position.clear()
            taskViewModel.deleteTask(id)
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

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.relative_layout,fragment)
        fragmentTransaction.commit()


    }
}