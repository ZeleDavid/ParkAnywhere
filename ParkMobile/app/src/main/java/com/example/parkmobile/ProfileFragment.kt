package com.example.parkmobile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("dark_mode", false))
            dark_theme_switch.isChecked = true
        super.onViewCreated(view, savedInstanceState)
        dark_theme_switch.setOnClickListener {

            if(dark_theme_switch.isChecked){
                sharedPreferences
                    .edit()
                    .putBoolean("dark_mode", true)
                    .apply()
            }
            else{
                sharedPreferences
                    .edit()
                    .putBoolean("dark_mode", false)
                    .apply()
            }
        }
    }
}
