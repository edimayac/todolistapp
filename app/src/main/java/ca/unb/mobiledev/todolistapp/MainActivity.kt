package ca.unb.mobiledev.todolistapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.database.DBHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ca.unb.mobiledev.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var itemList = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbHelper: DBHelper
    private lateinit var binding: ActivityMainBinding
    private lateinit var settings: SettingsFragment
    private lateinit var summary: SummaryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("check data1", this.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this@MainActivity)
        
        // Buttons
        val addButton = findViewById<Button>(R.id.addButton)
        val clear = findViewById<Button>(R.id.clearButton)
        val delete = findViewById<Button>(R.id.delete)
        val edit = findViewById<Button>(R.id.editTask)

        // Initializing the array lists and the adapter
        adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_multiple_choice, itemList)

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




        var listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
        itemList.add("Test1")
        itemList.add("Test2")

        // Adding the items to the list when the add button is pressed
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivityForResult(intent, 1)
        }


        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {
            itemList.clear()
            adapter.notifyDataSetChanged()
        }
        // Adding the toast message to the list when an item on the list is pressed
        listView.setOnItemClickListener { _, _, i, _ ->
            Toast.makeText(this, "You Selected the item --> "+ itemList[i], Toast.LENGTH_SHORT).show()
        }
        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(itemList[item])
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
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
        Log.e("check rc", resultCode.toString())
            if (resultCode == RESULT_OK) {

                val taskName = data!!.getStringExtra("title1")
                data!!.getStringExtra("title1")?.let { itemList.add(it) }
                if (taskName != null) {
                    dbHelper.addToTable1(taskName.toString(), "#Test", "1000", "031600")
                }
                adapter.notifyDataSetChanged()
                //Log.e("check database", dbHelper.selectFromTable1(taskName.toString().get(1))
            } else if (resultCode == RESULT_CANCELED) {
                adapter.notifyDataSetChanged()
            }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.relative_layout,fragment)
        fragmentTransaction.commit()


    }
}