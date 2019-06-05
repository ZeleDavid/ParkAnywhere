package com.example.parkmobile.DataClass


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkmobile.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.location_list_row.view.*

data class Parkirisce(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("cenaNaUro")
    val cenaNaUro: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("lastnik")
    val lastnik: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("naslov")
    val naslov: String,
    @SerializedName("naziv")
    val naziv: String,
    @SerializedName("stVsehMest")
    val stVsehMest: Int,
    @SerializedName("stZasedenihMest")
    val stZasedenihMest: Int

) {
    override fun toString(): String {
        return "Parkirisce(naziv='$naziv')"
    }
}
class ParkirisceAdapter(context: Context, seznam:List<Parkirisce>) : BaseAdapter(){
    private val mContext: Context = context
    private val seznamParkirisc = seznam
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.location_list_row, parent, false)
        row.findViewById<TextView>(R.id.location_list_text).text = seznamParkirisc[position].naziv
        row.findViewById<TextView>(R.id.location_list_adress).text = seznamParkirisc[position].naslov
        row.findViewById<TextView>(R.id.location_list_spots).text = (seznamParkirisc[position].stVsehMest - seznamParkirisc[position].stZasedenihMest).toString()
        return row
    }

    override fun getItem(position: Int): Any {
        return seznamParkirisc[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return seznamParkirisc.size
    }
}
class ParkirisceRecyclerAdapter(seznam: List<Parkirisce>): RecyclerView.Adapter<CustomViewHolder>(){
    val seznamParkirisc = seznam

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.location_list_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.location_list_text?.text = seznamParkirisc[position].naziv
        holder.view.location_list_adress?.text = seznamParkirisc[position].naslov
        holder.view.location_list_spots?.text = (seznamParkirisc[position].stVsehMest - seznamParkirisc[position].stZasedenihMest).toString()

    }

    override fun getItemCount(): Int {
        return seznamParkirisc.size
    }


}
class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}