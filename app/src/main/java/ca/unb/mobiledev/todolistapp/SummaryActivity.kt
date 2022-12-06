package ca.unb.mobiledev.todolistapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.todolistapp.database.Task
import ca.unb.mobiledev.todolistapp.database.TaskViewModel
import ca.unb.mobiledev.todolistapp.databinding.ActivitySummaryBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


class SummaryActivity : AppCompatActivity () {
    private lateinit var binding: ActivitySummaryBinding
    private lateinit var barChart: BarChart
    private lateinit var barData: BarData
    private lateinit var barDataSet: BarDataSet
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var barEntriesArrayList: ArrayList<BarEntry>
    private lateinit var xAxisFormatter: IndexAxisValueFormatter
    private var xAxisLabel: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        // Bottom Navigation Bar
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.menu.getItem(1).setChecked(true)
        bottomNavigationBar(this@SummaryActivity)

        //Set and Initialize Database Adapter
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        barChart = findViewById(R.id.idBarChart)

        createXAxis()

        getBarEntries()

        barDataSet = BarDataSet(barEntriesArrayList, "Seconds per #Hashtag")

        barData = BarData(barDataSet)

        barChart.data = barData

        barChart.invalidate()

        barDataSet.valueTextColor = Color.BLACK

        barDataSet.valueTextSize = 24f

        barChart.description.isEnabled = false


        //Remove the back arrow button
        supportActionBar!!.setHomeButtonEnabled(false)     // Disable the button
        supportActionBar!!.setDisplayHomeAsUpEnabled(false) // Remove the left caret
        supportActionBar!!.setDisplayShowHomeEnabled(false) // Remove the icon
    }

    private fun createXAxis() {
        xAxisLabel.addAll(taskViewModel.getAllTags())

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChart.setDrawGridBackground(false)
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
    }

    private fun getBarEntries() {
        barEntriesArrayList = ArrayList()

        xAxisLabel.forEachIndexed {index, hashTag ->
            val elapsedTimeByTag = taskViewModel.getElapsedTimeByTag(hashTag)
            barEntriesArrayList.add(BarEntry(index.toFloat(), elapsedTimeByTag.toFloat()))
        }
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