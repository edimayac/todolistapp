package ca.unb.mobiledev.todolistapp

import android.R.string
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var itemList = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("check data1", this.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton = findViewById<Button>(R.id.addButton)
        // finding the edit text

        val clear = findViewById<Button>(R.id.clearButton)

        // Initializing the array lists and the adapter
        adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_multiple_choice, itemList)
        var listView = findViewById<ListView>(R.id.listView)
        var delete = findViewById<Button>(R.id.delete)


        listView.adapter = adapter
        itemList.add("Test1")
        itemList.add("Test2")

        // Adding the items to the list when the add button is pressed
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivityForResult(intent, 1)

//            itemlist.add(editText.text.toString())
//            listView.adapter =  adapter
//            adapter.notifyDataSetChanged()
            // This is because every time when you add the item the input      space or the eidt text space will be cleared
//            editText.text.clear()
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

        /*// Setting On Click Listener
        button.setOnClickListener {

            // Getting the user input
            val text = editText.text

            // Showing the user input
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }*/
    }
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putStringArrayList("itemList", itemList)
//
//        super.onSaveInstanceState(outState)
//    }
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        itemList = savedInstanceState.getStringArrayList("itemList")!!
//        intent.getStringExtra("title1")?.let { itemList.add(it) }
//        Log.e("check data", itemList.toString())
//
//        super.onRestoreInstanceState(savedInstanceState)
//    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("check rc", resultCode.toString())
            if (resultCode == RESULT_OK) {
                data!!.getStringExtra("title1")?.let { itemList.add(it) }
                adapter.notifyDataSetChanged()
                Log.e("check data", itemList.toString())
            }
    }
}