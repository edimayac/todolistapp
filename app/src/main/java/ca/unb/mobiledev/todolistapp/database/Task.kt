package ca.unb.mobiledev.todolistapp.database

class Task private constructor(
    private val id: String?,
    private val name: String?,
    private val notes: String?,
    private val dueDate: String?,
    private val elapsedTime: Int?
) {
    // Only need to include getters
    val title: String
        get() = "$id: $name"

    data class Builder(
        var id: String? = null,
        var name: String? = null,
        var notes: String? = null,
        var dueDate: String? = null,
        var elapsedTime: Int? = 0,
    ) {

        /*
        How to create object for reference:
        1. Create a variable tasks
        private lateinit var tasks: ArrayList<Tasks>

        2. Use Task.Builder() to add values to it. For Example:
        val task = Task.Builder().name(taskName)
        OR
        val task = Task.Builder().name(taskName).dueDate("03/16/2000")

        *Note:  This is great since it gives the user the freedom if they want to add
                additional information in without forcing to add data to it*

        3. Lastly build it with this:
        tasks.add(task.build())


        Ref: Lab 5
         */

        fun id(id: String) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun notes(notes: String?) = apply { this.notes = name }
        fun dueDate(dueDate: String?) = apply { this.dueDate = dueDate }
        fun elapsedTime(elapsedTime: Int?) = apply { this.elapsedTime = elapsedTime }
        fun build() = Task(id, name, notes, dueDate, elapsedTime)

    }
}
