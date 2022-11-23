package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.databinding.ActivityMainBinding
import ca.unb.mobiledev.todolistapp.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity () {
    private lateinit var binding: ActivitySummaryBinding
    private lateinit var itemClicked: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Bottom Navigation Bar
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
//            setContentView(this@SettingsActivity,R.layout.settings,binding)
        binding.bottomNavigationView.setOnItemSelectedListener{item ->(


                when (item.itemId){

                    R.id.summary -> {
                        val intent = Intent(this,SummaryActivity::class.java)
                        itemClicked = item
                        startActivity(intent)
                    }

                    R.id.settings -> {
                        val intent = Intent(this,SettingsActivity::class.java)
                        itemClicked = item
                        startActivity(intent)
                    }

                    R.id.list -> {
                        val intent = Intent(this,MainActivity::class.java)
                        itemClicked = item
                        startActivity(intent)
                    }

                }
                )
            true
        }


    }

    fun getItemClicked():MenuItem{
        return itemClicked
    }


}