package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
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
        bottomNavigationBar()

        //Remove the back arrow button
        supportActionBar!!.setHomeButtonEnabled(false)     // Disable the button
        supportActionBar!!.setDisplayHomeAsUpEnabled(false) // Remove the left caret
        supportActionBar!!.setDisplayShowHomeEnabled(false) // Remove the icon


        //Remove the back arrow button
        supportActionBar!!.setHomeButtonEnabled(false)     // Disable the button
        supportActionBar!!.setDisplayHomeAsUpEnabled(false) // Remove the left caret
        supportActionBar!!.setDisplayShowHomeEnabled(false) // Remove the icon



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