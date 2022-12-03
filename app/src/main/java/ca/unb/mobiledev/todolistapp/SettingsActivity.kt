package ca.unb.mobiledev.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.todolistapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity () {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Bottom Navigation Bar
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.menu.getItem(2).setChecked(true)
        bottomNavigationBar(this@SettingsActivity)




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