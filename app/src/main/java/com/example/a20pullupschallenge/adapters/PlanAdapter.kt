package com.example.a20pullupschallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.a20pullupschallenge.DayWorkout
import com.example.a20pullupschallenge.R
import com.example.a20pullupschallenge.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.view.dayText
import kotlinx.android.synthetic.main.activity_main.view.setFive
import kotlinx.android.synthetic.main.activity_main.view.setFour
import kotlinx.android.synthetic.main.activity_main.view.setOne
import kotlinx.android.synthetic.main.activity_main.view.setThree
import kotlinx.android.synthetic.main.activity_main.view.setTwo
import kotlinx.android.synthetic.main.lo_day_workout.view.*
import org.jetbrains.anko.textColorResource

class PlanAdapter(val context: Context, val plan :  ArrayList<DayWorkout>, val activity: MainActivity) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_day_workout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return plan.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayWorkout : DayWorkout = plan[position]

        if (dayWorkout.day > 1) {
            holder.week.alpha = 0f
        }

        if (dayWorkout.status == "accomp") {
            holder.tableLo.alpha = 0.4f
        } else if (dayWorkout.status == "next") {
            holder.day.setBackgroundResource(R.color.colorPrimary)
            holder.setOne.setBackgroundResource(R.color.colorPrimaryDark)
            holder.setTwo.setBackgroundResource(R.color.colorPrimaryDark)
            holder.setThree.setBackgroundResource(R.color.colorPrimaryDark)
            holder.setFour.setBackgroundResource(R.color.colorPrimaryDark)
            holder.setFive.setBackgroundResource(R.color.colorPrimaryDark)

            holder.day.textColorResource = R.color.colorTextWhite
            holder.setOne.textColorResource = R.color.colorTextWhite
            holder.setTwo.textColorResource = R.color.colorTextWhite
            holder.setThree.textColorResource = R.color.colorTextWhite
            holder.setFour.textColorResource = R.color.colorTextWhite
            holder.setFive.textColorResource = R.color.colorTextWhite
        }


        holder.week.text = dayWorkout.week.toString()
        holder.day.text = dayWorkout.day.toString()
        holder.setOne.text = dayWorkout.workoutSets.setOne.toString()
        holder.setTwo.text = dayWorkout.workoutSets.setTwo.toString()
        holder.setThree.text = dayWorkout.workoutSets.setThree.toString()
        holder.setFour.text = dayWorkout.workoutSets.setFour.toString()
        holder.setFive.text = dayWorkout.workoutSets.setFive.toString()
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.dayText
        val tableLo = itemView.dayWorkoutLo
        val setOne = itemView.setOne
        val setTwo = itemView.setTwo
        val setThree = itemView.setThree
        val setFour = itemView.setFour
        val setFive = itemView.setFive
        val week = itemView.weekText
    }
}