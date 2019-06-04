package com.example.parkmobile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
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

        wallet_name.text = sharedPreferences.getString("wallet_name", "Ime vaše ARK denarnice")
        wallet_balance.text = sharedPreferences.getString("wallet_code", "Stanje vaše denarnice")
        val alertView = LayoutInflater.from(context).inflate(R.layout.edit_wallet_layout, getView() as ViewGroup, false)
        val wallet_name_field = alertView.findViewById<TextInputEditText>(R.id.wallet_name_input)
        val wallet_code_field = alertView.findViewById<TextInputEditText>(R.id.wallet_code_input)
        edit_wallet.setOnClickListener {

            val dialog = MaterialAlertDialogBuilder(context).create()
            dialog.setTitle("Povezava ARK denarnice")
            dialog.setMessage("Vpišite 12 besedno kodo vaše denarnice in jo poimenujte")
            dialog.setView(alertView)
            dialog.setOnDismissListener {
                    val parent = alertView.parent as ViewGroup
                    parent.removeView(alertView)
                }
            val potrdi_button = alertView.findViewById<Button>(R.id.potrdi_car_button)
            potrdi_button.setOnClickListener {
                val wallet_name_string = wallet_name_field.text.toString()
                val wallet_code_string = wallet_code_field.text.toString()
                if(wallet_name_string!=null && countWords(wallet_code_string)==20){
                    sharedPreferences
                        .edit()
                        .putString("wallet_name", wallet_name_string)
                        .putString("wallet_code", wallet_code_string)
                        .apply()
                    wallet_name.text = wallet_name_string
                    wallet_balance.text = wallet_code_string
                    dialog.dismiss()
                }
                else{
                    Toast.makeText(context, "Name: $wallet_name_string, Code: $wallet_code_string, count: ${countWords(wallet_code_string)}", Toast.LENGTH_SHORT).show()
                }
            }
            val prekini_button = alertView.findViewById<Button>(R.id.prekini_car_button)
            prekini_button.setOnClickListener {
                wallet_name_field.text?.clear()
                wallet_code_field.text?.clear()
                dialog.dismiss()
            }

            dialog.show()
        }

    }
    fun countWords(string:String):Int{
        val trim = string.trim()
        if (trim.isEmpty())
            return 0
        return trim.split("\\s+".toRegex()).size
    }
}
