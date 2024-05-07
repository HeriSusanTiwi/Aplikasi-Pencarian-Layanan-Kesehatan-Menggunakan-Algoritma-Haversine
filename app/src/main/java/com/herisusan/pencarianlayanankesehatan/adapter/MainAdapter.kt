package com.herisusan.pencarianlayanankesehatan.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.herisusan.pencarianlayanankesehatan.R
import com.herisusan.pencarianlayanankesehatan.activities.DetailLocationActivity
import com.herisusan.pencarianlayanankesehatan.adapter.MainAdapter.SearchViewHolder
import com.herisusan.pencarianlayanankesehatan.data.model.ModelResult
import com.herisusan.pencarianlayanankesehatan.utils.AlgortimaHaversine.getDistance
import kotlinx.android.synthetic.main.list_item_main.view.*
import java.text.DecimalFormat
import java.util.*

class MainAdapter(
    private val context: Context,
    private val lat: Double,
    private val long: Double,
) : RecyclerView.Adapter<SearchViewHolder>() {

    private val modelResults = ArrayList<ModelResult>()
    var onDetail: (model: ModelResult) -> Unit = {}

    fun setResultAdapter(items: ArrayList<ModelResult>) {
        modelResults.clear()
        modelResults.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = modelResults[position]

        //current location

        val strPlaceID = modelResults[position].placeId

        //location destination
        val strLat = modelResults[position].modelGeometry?.modelLocation?.lat!!
        val strLong = modelResults[position].modelGeometry?.modelLocation?.lng!!
        val strJarak = getDistance(strLat, strLong, lat, long)

        holder.tvNamaLokasi.text = item.name
        holder.tvAlamat.text = item.vicinity
        holder.tvJarak.text = DecimalFormat("#.##").format(strJarak) + " KM"

        holder.cvListLocation.setOnClickListener {
            onDetail(item)
        }
    }

    override fun getItemCount(): Int {
        return modelResults.size
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvListLocation: CardView
        val tvNamaLokasi: TextView
        val tvAlamat: TextView
        val tvJarak: TextView

        init {
            cvListLocation = itemView.cvListLocation
            tvNamaLokasi = itemView.tvNamaLokasi
            tvAlamat = itemView.tvAlamat
            tvJarak = itemView.tvJarak
        }
    }

}