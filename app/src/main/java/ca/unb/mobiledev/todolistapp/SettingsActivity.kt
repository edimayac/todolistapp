package ca.unb.mobiledev.todolistapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Layout
import android.view.MenuItem
import android.view.View
import android.widget.SimpleAdapter.ViewBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ca.unb.mobiledev.todolistapp.databinding.ActivityMainBinding
import ca.unb.mobiledev.todolistapp.databinding.ActivitySettingsBinding
import ca.unb.mobiledev.todolistapp.databinding.ActivitySummaryBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class SettingsActivity : AppCompatActivity () {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var itemClicked: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Bottom Navigation Bar
        binding = ActivitySettingsBinding.inflate(layoutInflater)
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

    fun getItemClicked(): MenuItem {
        return itemClicked
    }


}