package com.example.parkmobile.DataClass.Transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkmobile.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.transaction_history_row.view.*

class TransactionAdapter(seznam: List<Transaction>): RecyclerView.Adapter<HistoryViewHolder>(){
    val seznamTransakcij = seznam

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.transaction_history_row, parent, false)
        return HistoryViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(seznamTransakcij[position])
    }

    override fun getItemCount(): Int {
        return seznamTransakcij.size
    }
}
class HistoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(transakcija: Transaction){
        val vendorField = parseVendorField(transakcija.vendorField)
        view.transaction_history_amount?.text = (transakcija.amount/100000000).toString()
        val timestamp = transakcija.timestamp.human.subSequence(0, 17)
        view.transaction_history_date?.text = timestamp.toString().replace("T", " ")
        view.transaction_history_location?.text = vendorField.naziv
        view.transaction_history_duration?.text = vendorField.cas.toString()
        view.transaction_history_registerska?.text = vendorField.reg
    }
    fun parseVendorField(string: String): VendorField{
        var json = string.replace("\\", "")
        return Gson().fromJson(json, VendorField::class.java)
    }
}