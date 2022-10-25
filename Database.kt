package ca.unb.mobiledev.todolistapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Database(context: Context) : SQLiteOpenHelper(context, "Data.db" , null ,1) {


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
                workTime + "TIME," +
                dueDate + " DATE" +
                ");")


        db?.execSQL(createQuery1)
        val createQuery2 = ("CREATE TABLE " + table2Name + " ("
                + idReminder + "int NOT NULL, " +
                reminderTime + " DATE," +
                endReminder + " DATE," +
                interval + " TIME,"+
                " PRIMARY KEY ("+idReminder+"),"+
                "FOREIGN KEY ("+iDPrimaryKey+") REFERENCES "+table1Name+"("+iDPrimaryKey+")"+");")



        //db?.execSQL(createQuery2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addToTable1( taskNamel:String, hashTagl:String , workTimel:String , dueDatel:String){
        val db= this.writableDatabase
        val addQuery =
            "INSERT INTO$table1Name($taskName,$hashTag,$workTime,$dueDate)VALUES($taskNamel,$hashTagl,$workTimel,$dueDatel);"
        db?.execSQL(addQuery)

    }
    fun selectFromTable1(): Cursor? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM$table1Name"
        val cursor= db.rawQuery(selectQuery, null)
        cursor.moveToFirst()
        return cursor
    }


}