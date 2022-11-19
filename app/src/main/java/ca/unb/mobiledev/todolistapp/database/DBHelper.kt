package ca.unb.mobiledev.todolistapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context, "Data.db" , null ,1) {
    private val table1Name = " Task_Table "
    private val iDPrimaryKey = " TASK_ID "
    private val dueDate = " DUE_DATE "
    private val hashTag = " HASH_TAG "
    private val workTime = " TOTAL_TIME_WORKED "

    private val table2Name = " REMINDER_TABLE "
    private val reminderTime = " REMINDER_TIME "
    private val interval = " I_INTERVAL "
    private val endReminder = " END_REMINDER "
    private val idReminder = " REMINDER_ID "
    private val taskName = " TASK_NAME "

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery1 = ("CREATE TABLE "+ table1Name +
                " ( "+ iDPrimaryKey +"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                taskName +" VARCHAR(100), "+
                hashTag +" VARCHAR(100)," +
                workTime + "INT," +
                dueDate + " DATE" +
                ");")


        db?.execSQL(createQuery1)
        val createQuery2 = ("CREATE TABLE " + table2Name + " ("

                + idReminder + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                iDPrimaryKey + "INT"+
                reminderTime + " DATE," +
                endReminder + " DATE," +
                interval + " TIME,"+
                "FOREIGN KEY ("+iDPrimaryKey+") REFERENCES "+table1Name+"("+iDPrimaryKey+")"+");")

        db?.execSQL(createQuery2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addToTable1( taskNamel:String, hashTagl:String , workTimel:String , dueDatel:String){
        val db= this.writableDatabase
        val addQuery =
            "INSERT INTO$table1Name($taskName,$hashTag,$workTime,$dueDate)VALUES('$taskNamel','$hashTagl','$workTimel','$dueDatel');"
        db?.execSQL(addQuery)

    }
    fun selectFromTable1(taskNamel: String): ArrayList<Task> {
        val arraylist = ArrayList<Task>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $table1Name WHERE $taskName = '$taskNamel';"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()){
            do {
                val task = Task.Builder().id(cursor.getString(0))
                    .name(cursor.getString(1))
                    .notes(cursor.getString(2))
                    .dueDate(cursor.getString(3))
                    .elapsedTime(cursor.getInt(4)).build()
                arraylist.add(task)

            }while (cursor.moveToNext())
        }

        return arraylist
    }
    fun listTags() : ArrayList<String>{
        val arraylist = ArrayList<String>()
        val db = this.readableDatabase
        val selectQuery = "SELECT DISTINCT $hashTag FROM $table1Name;"
        val cursor = db.rawQuery(selectQuery, null)


        if (cursor.moveToFirst()){
            do {
                val tag =cursor.getString(0)
                arraylist.add(tag)

            }while (cursor.moveToNext())
        }

        return arraylist
    }

    fun deletFromTable1(taskNamel: String){
        val db= this.writableDatabase
        val query= "DELETE FROM $table1Name WHERE $taskName = '$taskNamel';"
        db.execSQL(query)
    }
    fun changeNameTable1(replaceWith: String, locatingVal: String){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $taskName = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }
    fun changeTagTable1(replaceWith: String, locatingVal: String){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $hashTag = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }
    fun changeDueDateTable1(replaceWith: String, locatingVal: String){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $dueDate = '$replaceWith' WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }
    fun changeWorkTimeTable1(replaceWith: Int, locatingVal: String){
        val db= this.writableDatabase

        val query= "UPDATE $table1Name SET $workTime = $replaceWith WHERE $taskName = '$locatingVal';"

        db.execSQL(query)

    }
    fun renewDatabase(context: Context){
        context.deleteDatabase("Data.db")
    }



}