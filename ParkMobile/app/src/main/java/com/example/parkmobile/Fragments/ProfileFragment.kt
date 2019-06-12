package com.example.parkmobile.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkmobile.DataClass.Parkirisce
import com.example.parkmobile.DataClass.ParkirisceRecyclerAdapter
import com.example.parkmobile.DataClass.Transactions.Transaction
import com.example.parkmobile.DataClass.Transactions.TransactionAdapter
import com.example.parkmobile.R
import com.example.parkmobile.Retrofit.ApiV2Interface
import com.example.parkmobile.Retrofit.ParkchainInterface
import com.example.parkmobile.Transaction.Mnemonic.GeneratePass.generateWallet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.transaction_history_row.*
import org.arkecosystem.crypto.identities.Address.fromPassphrase

class ProfileFragment : Fragment() {

    //retrofit
    val parkchainInterface by lazy {
        ParkchainInterface.create()
    }
    val apiV2Interface by lazy {
        ApiV2Interface.create()
    }
    var disposable: Disposable? = null

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
        val walletCode = sharedPreferences.getString("wallet_code", "")
        Log.i("WALLET", walletCode)
        val walletName = sharedPreferences.getString("wallet_name", "Ime vaše denarnice")
        if(!walletCode.equals("")){
            nalozi_gumb.visibility = View.VISIBLE
            generate_wallet_button.visibility = View.GONE
            val address = fromPassphrase(walletCode, 55).toString()
            pokaziStanjeDenarnice(address)
            vrniTransakcije(address)
        }
        wallet_name.text = walletName
        generate_wallet_button.setOnClickListener {
            val generatedCode = generateWallet()
            val address = fromPassphrase(generatedCode, 55).toString()
            sharedPreferences
                .edit()
                .putString("wallet_code", generatedCode)
                .apply()
            registrirajDenarnico(address)
            Log.i("WALLET", address)
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
    fun vrniTransakcije(address: String){
        apiV2Interface.vrniTransakcije(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    transaction_history_list.layoutManager = LinearLayoutManager(context)
                    val adapter = TransactionAdapter(result.data)
                    transaction_history_list.adapter = adapter
                },
                { error ->
                    Log.i("TRANSACTIONS", error.message)
                }
            )
    }
    fun pokaziStanjeDenarnice(address: String){
        disposable =
            apiV2Interface.vrniDenarnico(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val balance = result.data.balance / 100000000
                        wallet_balance.text = balance.toString()
                    },
                    { error ->
                        Log.i("WALLET", error.message)
                    }
                )

    }
    fun registrirajDenarnico(address: String){
        disposable =
            parkchainInterface.starterpack(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        generate_wallet_button.visibility = View.GONE
                        Toast.makeText(context, "Uspešno ste ustvarili vašo denarnico", Toast.LENGTH_SHORT).show()
                        pokaziStanjeDenarnice(address)
                    },
                    { error -> Toast.makeText(context, "Prišlo je do napake pri prenosu podatkov "+error.message, Toast.LENGTH_SHORT).show()
                    }
                )
    }
    fun countWords(string:String):Int{
        val trim = string.trim()
        if (trim.isEmpty())
            return 0
        return trim.split("\\s+".toRegex()).size
    }
    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
