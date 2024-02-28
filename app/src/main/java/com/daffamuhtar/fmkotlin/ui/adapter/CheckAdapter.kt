package com.daffamuhtar.fmkotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.data.model.Repair

class CheckAdapter(
    private val mContext: Context,
    private val checkModels: ArrayList<Repair>
) : RecyclerView.Adapter<CheckAdapter.ExampleViewHolder>(), Filterable {

    private var checkModelsFiltered: ArrayList<Repair> = ArrayList()
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    init {
        checkModelsFiltered.addAll(checkModels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_repair, parent, false)
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = checkModels[position]

        val sid = currentItem.orderId
        val vid = currentItem.vehicleId
        val stid = currentItem.stageId
        val vbrand = currentItem.vehicleBrand
        val vtype = currentItem.vehicleType
        val vvar = currentItem.vehicleVarian
        val vyear = currentItem.vehicleYear
        val vlicen = currentItem.vehicleLicenseNumber
        val vlid = currentItem.vehicleLambungId
        val vdis = currentItem.vehicleDistrict
        val nsa = currentItem.noteSA
        val sass = currentItem.startAssignment
        val locationOption = currentItem.locationOption
        val isstoring = currentItem.isStoring

        if (isstoring == "1") {
            holder.tvordertype.text = "Storing"
            holder.tvordertype.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.storing))
        } else {
            holder.tvordertype.text = "Adhoc"
            holder.tvordertype.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.adhoc))
        }

        if (stid == "13") {
            holder.tvstatus.text = "Dalam Proses Pemeriksaan"
            holder.tvstatus.setTextColor(ContextCompat.getColorStateList(mContext, R.color.blue))
            holder.tvstatus.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.bluesoft))

        } else {
            holder.tvstatus.text = "Menunggu Diperiksa"
            holder.tvstatus.setTextColor(ContextCompat.getColorStateList(mContext, R.color.blue))
            holder.tvstatus.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.bluesoft))
        }

        val date = sass.substring(0, 10)
        val timeonly = sass.substring(11, 16)
        holder.tvdate.text = "$date - $timeonly"
        holder.tvspkid.text = sid
        holder.tvlocoption.text = locationOption
        holder.tvvlid.text = vlid
        holder.tvvdis.text = vdis
        holder.tvvehicle.text = "$vbrand $vtype $vvar $vyear\n$vlicen"
        holder.tvnote.text = nsa
    }

    override fun getItemCount(): Int {
        return checkModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvspkid: TextView = itemView.findViewById(R.id.tv_repair_id)
        val tvvehicle: TextView = itemView.findViewById(R.id.tv_vehicle_name)
        val tvstatus: TextView = itemView.findViewById(R.id.tv_repair_stage)
        val tvdate: TextView = itemView.findViewById(R.id.tv_repair_date)
        val tvlocoption: TextView = itemView.findViewById(R.id.tv_locoption)
        val tvvlid: TextView = itemView.findViewById(R.id.tv_vehicle_lambung_id)
        val tvvdis: TextView = itemView.findViewById(R.id.tv_vehicle_district)
        val tvnote: TextView = itemView.findViewById(R.id.tv_wowkshop_address)
        val tvordertype: TextView = itemView.findViewById(R.id.tv_repair_stage)

        init {
            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onItemClick(position)
                    }
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList = ArrayList<Repair>()

            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(checkModelsFiltered)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()

                for (item in checkModelsFiltered) {
                    if (item.vehicleLicenseNumber.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            checkModels.clear()
            checkModels.addAll(results.values as ArrayList<Repair>)
            notifyDataSetChanged()
        }
    }
}
