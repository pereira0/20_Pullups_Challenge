package com.example.a20pullupschallenge.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.a20pullupschallenge.DayWorkout
import com.example.a20pullupschallenge.Sets
import org.jetbrains.anko.db.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
// Todo clean all LOGS
class MyDatabaseOpenHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "PlansDatabase", null, 1) {
    init {
        instance = this
    }

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: MyDatabaseOpenHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("Plan", true)
    }

    fun populatePlan (numInitialPullups: Int) {

        // get today's date
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val currentDate = current.format(formatter)

        // initialize DayWorkout class
        val dayWorkout : DayWorkout = DayWorkout()

        // open DB
        val db = this.writableDatabase

        // function to add new line to database
        fun addWorkoutToDB (dayWorkout: DayWorkout) {
            db.insert("Plan",
                "planId" to dayWorkout.planId,
                "startDate" to dayWorkout.startDate,
                "week" to dayWorkout.week,
                "day" to dayWorkout.day,
                "status" to dayWorkout.status,
                "setOne" to dayWorkout.workoutSets.setOne,
                "setTwo" to dayWorkout.workoutSets.setTwo,
                "setThree" to dayWorkout.workoutSets.setThree,
                "setFour" to dayWorkout.workoutSets.setFour,
                "setFive" to dayWorkout.workoutSets.setFive)
        }

        // WORKOUT PLANS
        when {

            // BEGINNERS PLANS (0 TO 3 PULLUPS)
            numInitialPullups < 3 -> { // beginners plan
                dayWorkout.planId = "beg_${numInitialPullups}_${currentDate}"
                dayWorkout.startDate = currentDate

                /// TODO dont forget to change this number
                dayWorkout.totNumWeeks = 2

                dayWorkout.status = "plan"


                when (numInitialPullups) {

                    0 -> {
                        for (week in 1..dayWorkout.totNumWeeks) {
                            for (day in 1..3) {
                                if (week == 1 && day == 1) {dayWorkout.status = "next"} else {dayWorkout.status = "plan"}
                                dayWorkout.week = week
                                dayWorkout.day = day
                                when {
                                    week == 1 && day == 1-> { dayWorkout.workoutSets = Sets(-1,-1,-1,-1,-1) }
                                    week == 1 && day == 2-> {dayWorkout.workoutSets = Sets(-1,-1,-1,-1,-1)}
                                    week == 1 && day == 3-> {dayWorkout.workoutSets = Sets(-1,-1,-1,-1,1) }

                                    week == 2 && day == 1-> {dayWorkout.workoutSets = Sets(1,-1,-1,-1,-1)}
                                    week == 2 && day == 2-> {dayWorkout.workoutSets = Sets(1,-1,1,-1,-1)}
                                    week == 2 && day == 3-> {dayWorkout.workoutSets = Sets(1,-1,1,-1,20)}
                                }

                                addWorkoutToDB(dayWorkout)
                            }
                        }
                    }
                    1 -> {
                        for (week in 1..dayWorkout.totNumWeeks) {
                            for (day in 1..3) {
                                dayWorkout.week = week
                                dayWorkout.day = day
                                when {
                                    week == 1 && day == 1-> {
                                        dayWorkout.workoutSets = Sets(1,-1,-1,-1,-1)
                                        dayWorkout.status = "next"}
                                    week == 1 && day == 2-> {dayWorkout.workoutSets = Sets(1,1,-1,1,-1)}
                                    week == 1 && day == 3-> {dayWorkout.workoutSets = Sets(1,1,-1,-1,2)}

                                    week == 2 && day == 1-> {dayWorkout.workoutSets = Sets(1,1,-1,1,-1)}
                                    week == 2 && day == 2-> {dayWorkout.workoutSets = Sets(1,1,-1,1,-1)}
                                    week == 2 && day == 3-> {dayWorkout.workoutSets = Sets(1,-1,1,-1,20)}
                                }

                                addWorkoutToDB(dayWorkout)
                            }
                        }
                    }
                    2 -> {
                        for (week in 1..dayWorkout.totNumWeeks) {
                            for (day in 1..3) {
                                dayWorkout.week = week
                                dayWorkout.day = day
                                when {
                                    week == 1 && day == 1-> {
                                        dayWorkout.workoutSets = Sets(2,1,1,2,-1)
                                        dayWorkout.status = "next"}
                                    week == 1 && day == 2-> {dayWorkout.workoutSets = Sets(2,1,1,2,-1)}
                                    week == 1 && day == 3-> {dayWorkout.workoutSets = Sets(2,1,1,1,2)}

                                    week == 2 && day == 1-> {dayWorkout.workoutSets = Sets(2,2,1,2,-1)}
                                    week == 2 && day == 2-> {dayWorkout.workoutSets = Sets(2,2,1,2,-1)}
                                    week == 2 && day == 3-> {dayWorkout.workoutSets = Sets(2,1,1,1,20)}
                                }

                                addWorkoutToDB(dayWorkout)
                            }
                        }
                    }
                }

            }

            /// TODO create plans for the other situations

            // BASIC PLANS (3 TO 5 PULLUPS)
            numInitialPullups in 3..5 -> { // basic plan

            }

            // BASIC PLANS (6 TO 9 PULLUPS)
            numInitialPullups in 6..9 -> { // intermediate plan

            }

            // ADVANCED PLANS (10+ PULLUPS)
            numInitialPullups >= 10 -> { // advanced plan

            }
        }

        db.close()
    }

    /// this function returns the full plan based on the answer given on the beginning
    fun getPlan() : ArrayList<DayWorkout> {
        val plan = ArrayList<DayWorkout>()
        val db = this.readableDatabase
        db.select("Plan", "planId", "week", "day", "status", "setOne", "setTwo", "setThree", "setFour", "setFive").exec {
            if (this.count != 0) {
                while (this.moveToNext()) {
                    val dayWorkout = DayWorkout()
                    dayWorkout.planId = this.getString(this.getColumnIndex("planId"))
                    dayWorkout.week = this.getInt(this.getColumnIndex("week"))
                    dayWorkout.day = this.getInt(this.getColumnIndex("day"))
                    dayWorkout.status = this.getString(this.getColumnIndex("status"))
                    dayWorkout.workoutSets.setOne = this.getInt(this.getColumnIndex("setOne"))
                    dayWorkout.workoutSets.setTwo = this.getInt(this.getColumnIndex("setTwo"))
                    dayWorkout.workoutSets.setThree = this.getInt(this.getColumnIndex("setThree"))
                    dayWorkout.workoutSets.setFour = this.getInt(this.getColumnIndex("setFour"))
                    dayWorkout.workoutSets.setFive = this.getInt(this.getColumnIndex("setFive"))
                    plan.add(dayWorkout)
                }
            }
            this.close()
        }
        db.close()
        return plan
    }

    /// this function returns the week and day number of the next workout
    fun getNextWorkoutWeekAndDay() : ArrayList<Int> {
        val weekAndDay = ArrayList<Int>()
        val db = this.readableDatabase
        Log.e("errorFinding","getNextWorkoutWeekAndDay started")
        db.select("Plan", "week", "day", "status")
            .whereArgs("status = {statusVar}", "statusVar" to "next")
            .exec {
            if (this.count != 0) {
                while (this.moveToNext()) {
                    if (this.getString(this.getColumnIndex("status")) == "next") {
                        weekAndDay.add(this.getInt(this.getColumnIndex("week")))
                        weekAndDay.add(this.getInt(this.getColumnIndex("day")))
                    }
                }
                this.close()
            }
        }
        Log.e("errorFinding","next week is ${weekAndDay[0]} and next day is ${weekAndDay[1]}")
        db.close()
        return weekAndDay
    }

    // TODO Finish this method to get the workout of the day

    fun getNextWorkout(week: Int, day: Int) : DayWorkout {
        Log.e("errorFinding","Db handler function initiated and week is $week and day is $day")
        val dayWorkout = DayWorkout()
        val db = this.readableDatabase
        Log.e("errorFinding","Db opened")
        db.select("Plan", "week", "day", "status", "setOne", "setTwo", "setThree", "setFour", "setFive")
            .whereArgs("(week = {weekNumber}) and (day = {dayNumber})", "weekNumber" to week, "dayNumber" to day)
            .exec {
                Log.e("errorFinding","db opened and retrieved where clause fine")
                Log.e("errorFinding","cursor count is ${this.count}")
            if (this.count != 0) {
                this.moveToFirst()
                Log.e("errorFinding","cursor on first and index is ${this.getColumnIndex("planId")}")
                Log.e("errorFinding","cursor on ${this} and week is ${this.getInt(this.getColumnIndex("week"))}")
                Log.e("errorFinding","cursor on ${this} and day is ${this.getInt(this.getColumnIndex("day"))}")
//                    dayWorkout.planId = this.getString(this.getColumnIndex("planId"))
                dayWorkout.week = this.getInt(this.getColumnIndex("week"))
                dayWorkout.day = this.getInt(this.getColumnIndex("day"))
                dayWorkout.status = this.getString(this.getColumnIndex("status"))
                dayWorkout.workoutSets.setOne = this.getInt(this.getColumnIndex("setOne"))
                dayWorkout.workoutSets.setTwo = this.getInt(this.getColumnIndex("setTwo"))
                dayWorkout.workoutSets.setThree = this.getInt(this.getColumnIndex("setThree"))
                dayWorkout.workoutSets.setFour = this.getInt(this.getColumnIndex("setFour"))
                dayWorkout.workoutSets.setFive = this.getInt(this.getColumnIndex("setFive"))
                Log.e("errorFinding","dayWorkout completed")

                this.close()
            }
        }
        Log.e("errorFinding","dayWorkout populated with week ${dayWorkout.week} and day ${dayWorkout.day}, ${dayWorkout.workoutSets.setOne} / ${dayWorkout.workoutSets.setTwo} / ... ")
        db.close()
        return dayWorkout
    }

    // this function drops the current table after the user asks to reset progress and start from the beginning
    fun dropTable() {
        val db = this.writableDatabase
        db.dropTable("Plan", true)
        createTable(db)
    }

    fun createTable(db: SQLiteDatabase) {
        db.createTable("Plan", true,
            "planId" to TEXT,
            "startDate" to TEXT,
            "week" to INTEGER,
            "day" to INTEGER,
            "status" to TEXT,
            "setOne" to INTEGER,
            "setTwo" to INTEGER,
            "setThree" to INTEGER,
            "setFour" to INTEGER,
            "setFive" to INTEGER)
    }

    // this function checks if there is a table created
    fun checkTable(): Boolean {
        val db = this.readableDatabase
        var week = 0
        db.select("Plan","week").limit(1).exec {
            while (this.moveToNext()) {
                week = this.getInt(this.getColumnIndex("week"))
            }
        }

        return week > 0
    }

    fun changeStatus(week: Int, day: Int) {
        val db = this.writableDatabase
        // update finished workout to show planDone
        db.update("Plan", "status" to "planDone")
            .whereArgs("(week = {weekN}) and (day = {dayN})", "weekN" to week,"dayN" to day)
            .exec()

        // update next workout to show next
        var newDay = day
        var newWeek = week
        if(day == 3) {
            newDay = 1
            newWeek = week + 1
        } else {newDay = day +1}

        db.update("Plan", "status" to "next")
            .whereArgs("(week = {weekN}) and (day = {dayN})", "weekN" to newWeek,"dayN" to newDay)
            .exec()
    }

    fun addAccomplishedWorkout(dayWorkoutComplete : DayWorkout) {
        val db = this.writableDatabase
        db.insert("Plan",
            "planId" to dayWorkoutComplete.planId,
            "startDate" to dayWorkoutComplete.startDate,
            "week" to dayWorkoutComplete.week,
            "day" to dayWorkoutComplete.day,
            "status" to dayWorkoutComplete.status,
            "setOne" to dayWorkoutComplete.workoutSets.setOne,
            "setTwo" to dayWorkoutComplete.workoutSets.setTwo,
            "setThree" to dayWorkoutComplete.workoutSets.setThree,
            "setFour" to dayWorkoutComplete.workoutSets.setFour,
            "setFive" to dayWorkoutComplete.workoutSets.setFive

        )
    }
}

