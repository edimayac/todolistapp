package ca.unb.mobiledev.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity () {
    private lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Bottom Navigation Bar
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.menu.getItem(1).setChecked(true)
        bottomNavigationBar(this@SummaryActivity)


    }

    private fun bottomNavigationBar(context: Context){

        binding.bottomNavigationView.setOnItemSelectedListener{item ->(

                when (item.itemId){

                    R.id.summary -> {
                        val intent = Intent(context,SummaryActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                        finish()
                    }

                    R.id.settings -> {
                        val intent = Intent(context,SettingsActivity::class.java)
                        intent.putExtra("item",item.itemId)
                        startActivity(intent)
                        finish()
                    }

                    R.id.list -> {
                        val intent = Intent(context,MainActivity::class.java)
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