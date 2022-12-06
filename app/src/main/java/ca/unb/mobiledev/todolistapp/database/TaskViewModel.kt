package ca.unb.mobiledev.todolistapp.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DBHelper(application)
    var id = 0
    // TODO
    //  Add mapping calls between the UI and Database
    fun addTask(task: Task): Int{ //insert
        dbHelper.addToTable1(task.name, task.notes, task.hashTag, task.elapsedTime!!, task.dueDate)

        return dbHelper.selectLastInsertedId()
    }

    fun getAllTasks(): ArrayList<Task> {
        return dbHelper.selectAllFromTable1()
    }

    fun getTask(id: Int): Task? {
        return dbHelper.selectFromTable1(id)
    }

    fun deleteAllTasks() {
        dbHelper.deleteAllTable1()
    }

    fun deleteTask(id: Int) {
        dbHelper.deleteFromTable1(id)
    }

    fun editTask(newTask: Task, oldTask: Task) {
        dbHelper.changeNameTable1(newTask.name, oldTask.id)
        dbHelper.changeNotesTable1(newTask.notes, oldTask.id)
        dbHelper.changeTagTable1(newTask.hashTag, oldTask.id)
        dbHelper.changeWorkTimeTable1(newTask.elapsedTime!!, oldTask.id)
        dbHelper.changeDueDateTable1(newTask.dueDate, oldTask.id)
    }

    fun editTask(newTask: Task, id: Int) {
        dbHelper.changeNameTable1(newTask.name, id)
        dbHelper.changeNotesTable1(newTask.notes, id)
        dbHelper.changeTagTable1(newTask.hashTag, id)
        dbHelper.changeWorkTimeTable1(newTask.elapsedTime!!, id)
        dbHelper.changeDueDateTable1(newTask.dueDate, id)
    }

    fun getAllTags(): ArrayList<String>{
        return dbHelper.listTags()
    }

    fun getElapsedTimeByTag(tag: String): Int {
        return dbHelper.getElapsedTimeByTag(tag)
    }
}