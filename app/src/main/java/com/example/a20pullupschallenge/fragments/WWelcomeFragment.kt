package com.example.a20pullupschallenge.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a20pullupschallenge.R
import com.example.a20pullupschallenge.activities.WorkoutActivity
import kotlinx.android.synthetic.main.fragment_workout_welcome.*

class WWelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_welcome, container, false)

//        startWorkoutBtn.setOnClickListener() {
//
//        }

        // TODO: clean this empty code
//        val textView = view.findViewById<TextView>(R.id.txtMain)
//        textView.setText(R.string.first_fragment)
//
//        val imageView = view.findViewById<ImageView>(R.id.imgMain)
//        imageView.setImageResource(R.mipmap.ic_launcher)

        return view
    }
}