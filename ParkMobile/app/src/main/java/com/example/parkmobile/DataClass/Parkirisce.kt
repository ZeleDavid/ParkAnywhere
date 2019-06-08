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
class CenaComparator{
    companion object: Comparator<Parkirisce>{
        override fun compare(o1: Parkirisce, o2: Parkirisce): Int {
            return (o1.cenaNaUro*10).toInt() - (o2.cenaNaUro*10).toInt()
        }
    }
}
class ParkirisceRecyclerAdapter(seznam: List<Parkirisce>, val clickListenerParkiraj: (Parkirisce)->Unit, val clickListenerNavigiraj: (Parkirisce)->Unit): RecyclerView.Adapter<CustomViewHolder>(){
    val seznamParkirisc = seznam
    var mExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.location_list_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(seznamParkirisc[position], clickListenerParkiraj, clickListenerNavigiraj)
        val isExpanded = position == mExpandedPosition
        holder.view.location_list_expandable. visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded
        holder.itemView.setOnClickListener {
            mExpandedPosition = if(isExpanded) -1 else position
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return seznamParkirisc.size
    }


}
class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bind(parkirisce: Parkirisce, clickListenerParkiraj: (Parkirisce) -> Unit, clickListenerNavigiraj: (Parkirisce) -> Unit){
        view.location_list_text?.text = parkirisce.naziv
        view.location_list_adress?.text = parkirisce.naslov
        view.location_list_spots?.text = (parkirisce.stVsehMest - parkirisce.stZasedenihMest).toString()
        view.location_list_cena?.text = parkirisce.cenaNaUro.toString()
        view.location_list_show.setOnClickListener { clickListenerParkiraj(parkirisce) }
        view.location_list_navigate.setOnClickListener { clickListenerNavigiraj(parkirisce) }
    }
}