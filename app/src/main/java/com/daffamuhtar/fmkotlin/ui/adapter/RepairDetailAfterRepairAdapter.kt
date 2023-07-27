package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemRepairDetailAfterRepairBinding
import com.daffamuhtar.fmkotlin.model.RepairDetailAfterRepair
import com.daffamuhtar.fmkotlin.util.RepairHelper

class RepairDetailAfterRepairAdapter() :
    RecyclerView.Adapter<RepairDetailAfterRepairAdapter.ItemViewHolder>() {

    private var items = listOf<RepairDetailAfterRepair>()
    private var repairStageId: Int = 0
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(items: List<RepairDetailAfterRepair>) {
        items.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    fun setRepairStageId(it: Int) {
        it.let {
            this.repairStageId = it
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: RepairDetailAfterRepair, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ItemViewHolder(private val view: ItemRepairDetailAfterRepairBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: RepairDetailAfterRepair, position: Int) {
            with(view) {
                tvParts.text = item.partName

                val photoAdapter = PhotoAdapter()
                photoAdapter.setItems(item.photos)
                rvPhoto.adapter = photoAdapter

                val isEditable = (RepairHelper.isEdistable(
                    view.root.context,
                    repairStageId,
                    item.photos.size
                ))

                RepairHelper.setViewAfterRepair(
                    view.root.context,
                    isEditable,
                    item.isRequiredScan,
                    item.totalUsedPart,
                    item.partQuantity,
                    btnScan,
                    btnScanSecondPart,
                    btnRemove,
                    lyPhoto
                )



                btnAddPhoto.visibility = if (isEditable) View.VISIBLE else View.GONE


//                btnAddPhoto.setOnClickListener{
//                    addUsedPartPosition = position
//                    selectedItemPosition = position
//                    selectedItemPartIdFromFleetify = partIdFromFleetify
//                    selectedItemPartSku = partSku
//                    selectedItemPartName = partName
//                    selectedItemItemRequiredScan = itemRequiredScan
//                    selectedItemTotalUsedPart = finalTotalUsedPart
//                    selectedItemPartQuantity = partQuantity
//                    selectedItemRealPartQuantity = realPartQuantity
//                    selectedItemRealPartQuantity = realPartQuantity
//                    selectedItemUsedPartType = usedPartType
//                    selectedItemIsAllowToSkip = isAllowToSkip
//                    selectedItemTirePositionId = tpositionid
//                    selectedItemFile1 = file1
//                    selectedItemFile2 = file2
//                    selectedItemFile3 = file3
//                    if (mworktype == "period") {
//                        val afterrepair = Intent(mContext, CameraNewActivity::class.java)
//                        afterrepair.putExtra(Constanta.EXTRA_REQCODE, "repairv_period")
//                        afterrepair.putExtra(Constanta.EXTRA_USERID, mmid)
//                        afterrepair.putExtra(Constanta.EXTRA_SPKID, spkId)
//                        afterrepair.putExtra(Constanta.EXTRA_WORKSHOPID, mwid)
//                        afterrepair.putExtra(
//                            Constanta.EXTRA_PARTID,
//                            mProblemList.get(position).getPartIdFromFleetify()
//                        )
//                        (mContext as RepairDetailActivity).arlAddPhotoVideo.launch(afterrepair)
//                    } else if (mworktype == "nonperiod") {
//                        val afterrepair = Intent(mContext, CameraNewActivity::class.java)
//                        afterrepair.putExtra(Constanta.EXTRA_REQCODE, "repairv_nonperiod")
//                        afterrepair.putExtra(Constanta.EXTRA_USERID, mmid)
//                        afterrepair.putExtra(Constanta.EXTRA_SPKID, spkId)
//                        afterrepair.putExtra(Constanta.EXTRA_WORKSHOPID, mwid)
//                        afterrepair.putExtra(
//                            Constanta.EXTRA_PARTID,
//                            mProblemList.get(position).getPartIdFromFleetify()
//                        )
//                        (mContext as RepairDetailActivity).arlAddPhotoVideo.launch(afterrepair)
//                    } else if (mworktype == "tire") {
//                        val afterrepair = Intent(mContext, CameraNewActivity::class.java)
//                        afterrepair.putExtra(Constanta.EXTRA_REQCODE, "repairv_tire")
//                        afterrepair.putExtra(Constanta.EXTRA_USERID, mmid)
//                        afterrepair.putExtra(Constanta.EXTRA_TIREPOSITIONID, tpositionid)
//                        afterrepair.putExtra(Constanta.EXTRA_SPKID, spkId)
//                        afterrepair.putExtra(Constanta.EXTRA_WORKSHOPID, mwid)
//                        afterrepair.putExtra(
//                            Constanta.EXTRA_PARTID,
//                            mProblemList.get(position).getPartIdFromFleetify()
//                        )
//                        (mContext as RepairDetailActivity).arlAddPhotoVideo.launch(afterrepair)
//                    } else {
//                        val afterrepair = Intent(mContext, CameraNewActivity::class.java)
//                        afterrepair.putExtra(Constanta.EXTRA_REQCODE, "repairv")
//                        afterrepair.putExtra(Constanta.EXTRA_USERID, mmid)
//                        afterrepair.putExtra(Constanta.EXTRA_SPKID, spkId)
//                        afterrepair.putExtra(Constanta.EXTRA_WORKSHOPID, mwid)
//                        afterrepair.putExtra(
//                            Constanta.EXTRA_PARTID,
//                            mProblemList.get(position).getPartIdFromFleetify()
//                        )
//                        (mContext as RepairDetailActivity).arlAddPhotoVideo.launch(afterrepair)
//                        //                    mContext.startActivity(afterrepair);
//                    }
//                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepairDetailAfterRepairBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)

//        if(!items[position].isActive) {
//            holder.itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(items[holder.adapterPosition], position)
//            }
//        }else{
//            holder.itemView.isClickable = false
//        }
    }

    override fun getItemCount(): Int = items.size
}