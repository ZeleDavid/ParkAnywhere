package com.example.parkmobile.Fragments

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.parkmobile.R
import com.example.parkmobile.Transaction.Mnemonic.GeneratePass
import com.example.parkmobile.Transaction.Mnemonic.GeneratePass.generateWallet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_profile.*
import org.arkecosystem.crypto.identities.Address.fromPassphrase

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
        val walletCode = sharedPreferences.getString("wallet_code", "Stanje vaše denarnice")
        if(!walletCode.equals("Stanje vaše denarnice")){
            generate_wallet_button.visibility = View.GONE
        }
        wallet_name.text = walletCode
        wallet_balance.text = sharedPreferences.getString("wallet_code", "Stanje vaše denarnice")
        generate_wallet_button.setOnClickListener {
            val generatedCode = generateWallet()
            sharedPreferences
                .edit()
                .putString("wallet_name", generatedCode)
                .apply()
            generate_wallet_button.visibility = View.GONE
            Log.i("WALLET", fromPassphrase(generatedCode))
            Toast.makeText(context, "Uspešno ste ustvarili vašo denarnico", Toast.LENGTH_SHORT).show()
        }
        import_wallet_button.setOnClickListener {
            val alertView = LayoutInflater.from(context).inflate(R.layout.edit_wallet_layout, getView() as ViewGroup, false)
            val wallet_code_field = alertView.findViewById<TextInputEditText>(R.id.wallet_input)

            val dialog = MaterialAlertDialogBuilder(context).create()
            dialog.setTitle("Povezava ARK denarnice")
            dialog.setMessage("Vpišite 12 besedno kodo vaše denarnice.")
            dialog.setView(alertView)
            dialog.setOnDismissListener {
                val parent = alertView.parent as ViewGroup
                parent.removeView(alertView)
            }
            val potrdi_button = alertView.findViewById<Button>(R.id.potrdi_car_button)
            potrdi_button.setOnClickListener {
                val wallet_code_string = wallet_code_field.text.toString()
                if(countWords(wallet_code_string)==12){
                    sharedPreferences
                        .edit()
                        .putString("wallet_code", wallet_code_string)
                        .apply()
                    dialog.dismiss()
                }
                else{
                    Toast.makeText(context, "Vpisana koda denarnice ni pravilna!", Toast.LENGTH_SHORT).show()
                }
            }
            val prekini_button = alertView.findViewById<Button>(R.id.prekini_car_button)
            prekini_button.setOnClickListener {
                wallet_code_field.text?.clear()
                dialog.dismiss()
            }

            dialog.show()
        }

        edit_wallet.setOnClickListener {
            val alertView = LayoutInflater.from(context).inflate(R.layout.edit_wallet_layout, getView() as ViewGroup, false)
            val wallet_name_field = alertView.findViewById<TextInputEditText>(R.id.wallet_input)

            val dialog = MaterialAlertDialogBuilder(context).create()
            dialog.setTitle("Povezava ARK denarnice")
            dialog.setMessage("Poimenujte vašo denarnico")
            dialog.setView(alertView)
            dialog.setOnDismissListener {
                    val parent = alertView.parent as ViewGroup
                    parent.removeView(alertView)
                }
            val potrdi_button = alertView.findViewById<Button>(R.id.potrdi_car_button)
            potrdi_button.setOnClickListener {
                val wallet_name_string = wallet_name_field.text.toString()
                if(!wallet_name_string.isEmpty()){
                    sharedPreferences
                        .edit()
                        .putString("wallet_name", wallet_name_string)
                        .apply()
                    wallet_name.text = wallet_name_string
                    dialog.dismiss()
                }
                else{
                    Toast.makeText(context, "Ime ne sme biti prazno!", Toast.LENGTH_SHORT).show()
                }
            }
            val prekini_button = alertView.findViewById<Button>(R.id.prekini_car_button)
            prekini_button.setOnClickListener {
                wallet_name_field.text?.clear()
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
