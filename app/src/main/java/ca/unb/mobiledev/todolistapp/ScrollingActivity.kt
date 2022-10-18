package ca.unb.mobiledev.todolistapp

import android.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.databinding.ActivityScrollingBinding
import com.google.android.material.snackbar.Snackbar


class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private var items: ArrayList<String>? = null
    private var itemsAdapter: ArrayAdapter<String>? = null
    private var lvItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lvItems = binding.listView as ListView
        items = ArrayList<String>()
        itemsAdapter = ArrayAdapter<String>(
            this,
            R.layout.simple_list_item_1, items!!
        )
        if (lvItems != null) {}

        lvItems!!.setAdapter(itemsAdapter)
        items!!.add("First Item")
        items!!.add("Second Item")

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = title
        binding.btnAdd.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
        return true
    }
}