package com.daffamuhtar.fmkotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.appv4.RepairResponse4
import com.daffamuhtar.fmkotlin.constants.ConstantsRepair
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.data.response.ErrorResponse
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class RepairHelper {
    companion object {

        fun mapRepairItem(repairResponse4: RepairResponse4): Repair {

            return Repair(
                repairResponse4.orderId,
                repairResponse4.spkId,
                repairResponse4.pbId ,
                repairResponse4.stageId.toString(),
                repairResponse4.stageName ,
                repairResponse4.vehicleId,
                repairResponse4.vehicleBrand,
                repairResponse4.vehicleType,
                repairResponse4.vehicleVarian,
                repairResponse4.vehicleYear,
                repairResponse4.vehicleLicenseNumber,
                repairResponse4.vehicleLambungId,
                repairResponse4.vehicleDistrict,
                repairResponse4.noteFromSA,
                repairResponse4.workshopName ,
                repairResponse4.workshopLocation ,
                repairResponse4.scheduledDate,
                repairResponse4.additionalPartNote ,
                repairResponse4.startRepairOdometer.toString(),
                repairResponse4.locationOption,
                repairResponse4.isStoring,
                repairResponse4.orderType ,
                repairResponse4.colorCode

            )


        }

        fun getRepairOrderType(orderId: String, isStoring: String?): String {
            val suborder: String = orderId.substring(4, 6)
            val orderType: String

            when (suborder) {
                "PB" -> {
                    orderType = ConstantsRepair.ORDER_TYPE_MAINTENANCE
                }

                "PN" -> {
                    orderType = ConstantsRepair.ORDER_TYPE_NPM
                }

                "TI" -> {
                    orderType = ConstantsRepair.ORDER_TYPE_TIRE
                }

                else -> {
                    orderType = if (isStoring == "1") {
                        ConstantsRepair.ORDER_TYPE_ADHOC

                    } else {
                        ConstantsRepair.ORDER_TYPE_ADHOC
                    }
                }
            }

            return orderType
        }

        fun getRepairTitle(orderId: String, isStoring: String?): String {
            val suborder: String = orderId.substring(4, 6)
            var repairTitle: String

            when (suborder) {
                "PB" -> {
                    repairTitle = "Perbaikan Berkala"
                }

                "PN" -> {
                    repairTitle = "Perbaikan Non Berkala"
                }

                "TI" -> {
                    repairTitle = "Perbaikan Ban"
                }

                else -> {
                    repairTitle = if (isStoring == "1") {
                        "Perbaikan Darurat"

                    } else {
                        "Perbaikan Adhoc"
                    }
                }
            }

            return repairTitle
        }

        fun getRepairId(orderId: String, spkId: String?): String {
            var repairId: String

            if (spkId == null) {
                repairId = orderId
            } else {
                repairId = spkId
            }
            return repairId
        }

        fun getRepairDate(date: String): String {
            var repairDate: String

            if (date != null) {
                val locale = Locale("id")

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMM \n HH:mm", locale)
//                    repairDate=LocalDateTime.parse(date).format(dateTimeFormatter)
//
//                } else {
                val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
                val formatter = SimpleDateFormat("EEEE, d MMM yyyy\nHH:mm", locale)
                repairDate = parser.parse(date)?.let { formatter.format(it) }.toString()

            } else {
                repairDate = "menunggu dijadwalkan"

            }

//            try {
//                /** DEBUG dateStr = '2006-04-16T04:00:00Z' **/
//                val formatter = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", locale)
//                val mDate = formatter.parse(dateStr) // this never ends while debugging
//            } catch (_: Exception) {
//
//            }
            return repairDate
        }


        fun getRepairIcon(orderId: String, isStoring: String?): Int {
            val suborder: String = orderId.substring(4, 6)
            var resource = R.drawable.bg_circle_repair_pb


            when (suborder) {
                "PB" -> {
                    resource = R.drawable.bg_circle_repair_pb
                }

                "PN" -> {
                    resource = R.drawable.bg_circle_repair_pnb
                }

                "TI" -> {
                    resource = R.drawable.bg_circle_repair_tire
                }

                else -> {
                    resource = when (isStoring) {
                        "1" -> {
                            R.drawable.bg_circle_repair_storing
                        }

                        else -> {
                            R.drawable.bg_circle_repair_adhoc
                        }
                    }
                }
            }

            return resource
        }

        fun getRepairStageName(stageId: String, stageNameRaw: String?): String? {

            var stageName = stageNameRaw
            val stageId: Int = stageId.toInt()

            if (stageName == null) {
                when (stageId) {
                    12 -> {
                        stageName = "Menunggu diperiksa"
                    }

                    13 -> {
                        stageName = "Sedang diperiksa"
                    }

                    14 -> {
                        stageName = "Menunggu verifikasi hasil pemeriksaan"
                    }

                    15 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    16 -> {
                        stageName = "Menunggu Surat Penawaran diperbarui"
                    }

                    17 -> {
                        stageName = "Penawaran disetujui"
                    }

                    18 -> {
                        stageName = "Lakukan perbaikan"
                    }

                    19 -> {
                        stageName = "WO sedang diproses"
                    }

                    20 -> {
                        stageName = "Menunggu approval hasil perbaikan"
                    }

                    21 -> {
                        stageName = "Menunggu verifikasi hasil perbaikan"
                    }

                    22 -> {
                        stageName = "Perbaikan selesai"
                    }

                    23 -> {
                        stageName = "Dikomplain"
                    }

                    24 -> {
                        stageName = "WO selesai"
                    }

                    26, 27, 28 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    29 -> {
                        stageName = "Order pending"
                    }

                    31 -> {
                        stageName = "Penambahan part segera diproses"
                    }

                    32 -> {
                        stageName = "Surat penawaran sedang diperbarui"
                    }

                    33 -> {
                        stageName = "Revisi Surat Penawaran (penambahan part) Ditolak"
                    }

                    else -> {

                    }
                }

            }

            return stageName
        }

        fun setRepairStage(stageId: String, stageNameRaw: String?): String? {

            var stageName = stageNameRaw
            val stageIcon: Int
            val stageBackgroundColor: Int
            val stageId: Int = stageId.toInt()

            if (stageName == null) {
                when (stageId) {
                    12 -> {
                        stageName = "Menunggu diperiksa"
                        stageIcon = R.drawable.ic_stage_repair_check
                        stageBackgroundColor = R.color.orangesoft
                    }

                    13 -> {
                        stageName = "Sedang diperiksa"
                        stageIcon = R.drawable.ic_stage_repair_check
                        stageBackgroundColor = R.color.orangesoft
                    }

                    14 -> {
                        stageName = "Menunggu verifikasi hasil pemeriksaan"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    15 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    16 -> {
                        stageName = "Menunggu Surat Penawaran diperbarui"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    17 -> {
                        stageName = "Penawaran disetujui"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    18 -> {
                        stageName = "Lakukan perbaikan"
                        stageIcon = R.drawable.ic_stage_repair_onrepair
                        stageBackgroundColor = R.color.orangesoft
                    }

                    19 -> {
                        stageName = "WO sedang diproses"
                        stageIcon = R.drawable.ic_stage_repair_onrepair
                        stageBackgroundColor = R.color.orangesoft
                    }

                    20 -> {
                        stageName = "Menunggu approval hasil perbaikan"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    21 -> {
                        stageName = "Menunggu verifikasi hasil perbaikan"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    22 -> {
                        stageName = "Perbaikan selesai"
                        stageIcon = R.drawable.ic_stage_repair_done
                        stageBackgroundColor = R.color.green
                    }

                    23 -> {
                        stageName = "Dikomplain"
                        stageIcon = R.drawable.ic_stage_repair_complain
                        stageBackgroundColor = R.color.bluesoft
                    }

                    24 -> {
                        stageName = "WO selesai"
                        stageIcon = R.drawable.ic_stage_repair_done
                        stageBackgroundColor = R.color.green
                    }

                    26, 27, 28 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    29 -> {
                        stageName = "Order pending"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    31 -> {
                        stageName = "Penambahan part segera diproses"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    32 -> {
                        stageName = "Surat penawaran sedang diperbarui"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    33 -> {
                        stageName = "Revisi Surat Penawaran (penambahan part) Ditolak"
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft
                    }

                    else -> {
                        stageIcon = R.drawable.ic_stage_repair_ongoing
                        stageBackgroundColor = R.color.bluesoft

                    }
                }

            }

            return stageName
        }

        @SuppressLint("ResourceAsColor")
        fun setRepairStage(
            context: Context,
            stageId: Int,
            stageNameRaw: String?,
            tvStage: TextView,
            ivStageIcon: ImageView,
            lyStage: LinearLayout
        ) {


            val stageIcon: Int
            val stageBackgroundColor: Int
            val stageTextColor: Int
            val stageId: Int = stageId.toInt()
            var stageName = stageNameRaw

            if (stageName == null) {
                when (stageId) {
                    12 -> {
                        stageName = "Menunggu diperiksa"
                    }

                    13 -> {
                        stageName = "Sedang diperiksa"
                    }

                    14 -> {
                        stageName = "Menunggu verifikasi hasil pemeriksaan"
                    }

                    15 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    16 -> {
                        stageName = "Menunggu Surat Penawaran diperbarui"
                    }

                    17 -> {
                        stageName = "Penawaran disetujui"
                    }

                    18 -> {
                        stageName = "Lakukan perbaikan"
                    }

                    19 -> {
                        stageName = "Sedang diperbaiki"
                    }

                    20 -> {
                        stageName = "Menunggu approval hasil perbaikan"
                    }

                    21 -> {
                        stageName = "Menunggu verifikasi hasil perbaikan"
                    }

                    22 -> {
                        stageName = "Perbaikan selesai"
                    }

                    23 -> {
                        stageName = "Perbaikan dikomplain"
                    }

                    24 -> {
                        stageName = "Perbaikan selesai"
                    }

                    25 -> {
                        stageName = "Perbaikan selesai"
                    }

                    26, 27, 28 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    29 -> {
                        stageName = "Order pending"
                    }

                    31 -> {
                        stageName = "Penambahan part segera diproses"
                    }

                    32 -> {
                        stageName = "Surat penawaran sedang diperbarui"
                    }

                    33 -> {
                        stageName = "Revisi Surat Penawaran (penambahan part) Ditolak"
                    }

                    else -> {

                    }
                }

            }

            when (stageId) {
                12, 13 -> {
                    stageIcon = R.drawable.ic_stage_repair_check
                    stageBackgroundColor = R.color.orangesoft
                    stageTextColor = R.color.orange
                }

                14, 15, 16, 17, 20, 21 -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }

                18, 19 -> {
                    stageIcon = R.drawable.ic_stage_repair_onrepair
                    stageBackgroundColor = R.color.orangesoft
                    stageTextColor = R.color.orange
                }

                23 -> {
                    stageIcon = R.drawable.ic_stage_repair_complain
                    stageBackgroundColor = R.color.redsoft
                    stageTextColor = R.color.red
                }

                22, 24 -> {
                    stageIcon = R.drawable.ic_stage_repair_done
                    stageBackgroundColor = R.color.greensoft
                    stageTextColor = R.color.green
                }

                26, 27, 28, 29, 31, 32, 33 -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }

                else -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }
            }

            ivStageIcon.setImageResource(stageIcon)
            lyStage.setBackgroundResource(stageBackgroundColor)
            tvStage.text = stageName
            tvStage.setTextColor(ContextCompat.getColor(context, stageTextColor))

            RepairStageUiModel(stageIcon,stageBackgroundColor,stageName,stageTextColor)
//            tvStage.setTextColor(stageTextColor)

        }
        @SuppressLint("ResourceAsColor")
        fun getRepairStageModel(
            context: Context,
            stageId: Int,
            stageNameRaw: String?,
        ) : RepairStageUiModel{


            val stageIcon: Int
            val stageBackgroundColor: Int
            val stageTextColor: Int
            val stageId: Int = stageId.toInt()
            var stageName = stageNameRaw

            if (stageName == null) {
                when (stageId) {
                    12 -> {
                        stageName = "Menunggu diperiksa"
                    }

                    13 -> {
                        stageName = "Sedang diperiksa"
                    }

                    14 -> {
                        stageName = "Menunggu verifikasi hasil pemeriksaan"
                    }

                    15 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    16 -> {
                        stageName = "Menunggu Surat Penawaran diperbarui"
                    }

                    17 -> {
                        stageName = "Penawaran disetujui"
                    }

                    18 -> {
                        stageName = "Lakukan perbaikan"
                    }

                    19 -> {
                        stageName = "Sedang diperbaiki"
                    }

                    20 -> {
                        stageName = "Menunggu approval hasil perbaikan"
                    }

                    21 -> {
                        stageName = "Menunggu verifikasi hasil perbaikan"
                    }

                    22 -> {
                        stageName = "Perbaikan selesai"
                    }

                    23 -> {
                        stageName = "Perbaikan dikomplain"
                    }

                    24 -> {
                        stageName = "Perbaikan selesai"
                    }

                    25 -> {
                        stageName = "Perbaikan selesai"
                    }

                    26, 27, 28 -> {
                        stageName = "Menunggu konfirmasi penawaran"
                    }

                    29 -> {
                        stageName = "Order pending"
                    }

                    31 -> {
                        stageName = "Penambahan part segera diproses"
                    }

                    32 -> {
                        stageName = "Surat penawaran sedang diperbarui"
                    }

                    33 -> {
                        stageName = "Revisi Surat Penawaran (penambahan part) Ditolak"
                    }

                    else -> {

                    }
                }

            }

            when (stageId) {
                12, 13 -> {
                    stageIcon = R.drawable.ic_stage_repair_check
                    stageBackgroundColor = R.color.orangesoft
                    stageTextColor = R.color.orange
                }

                14, 15, 16, 17, 20, 21 -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }

                18, 19 -> {
                    stageIcon = R.drawable.ic_stage_repair_onrepair
                    stageBackgroundColor = R.color.orangesoft
                    stageTextColor = R.color.orange
                }

                23 -> {
                    stageIcon = R.drawable.ic_stage_repair_complain
                    stageBackgroundColor = R.color.redsoft
                    stageTextColor = R.color.red
                }

                22, 24 -> {
                    stageIcon = R.drawable.ic_stage_repair_done
                    stageBackgroundColor = R.color.greensoft
                    stageTextColor = R.color.green
                }

                26, 27, 28, 29, 31, 32, 33 -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }

                else -> {
                    stageIcon = R.drawable.ic_stage_repair_ongoing
                    stageBackgroundColor = R.color.bluesoft
                    stageTextColor = R.color.blue
                }
            }

            return RepairStageUiModel(stageIcon,stageBackgroundColor,stageName,stageTextColor)
//            tvStage.setTextColor(stageTextColor)

        }

        fun setDriverPhoto(
            context: Context,
            photoUrl: String,
            ivPhoto: ShapeableImageView,
        ) {
            Glide.with(ivPhoto.context)
                .load(photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .override(200, 200)
                .into(ivPhoto)
        }

        fun setOnClickChat(
            context: Context,
            phoneNumber: String,
            btnChat: ImageButton,
        ) {
            btnChat.setOnClickListener {
                val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }

        }

        fun isEdistable(
            context: Context,
            stageId: Int,
            totalPhoto: Int,
            actionType: String,
        ): Boolean {
            var isEditable = false

            when (actionType) {
                ConstantsRepair.REPAIR_SECTION_CHECK -> {
                    if (stageId == 13) {
                        isEditable = true
                    }
                }

                ConstantsRepair.REPAIR_SECTION_AFTER_REPAIR -> {
                    if (stageId == 19) {
                        isEditable = true
                    }
                }
            }

            return isEditable
        }

        fun setViewAfterRepair(
            context: Context,
            isEditable: Boolean,
            isRequiredScan: Int,
            totalUsedPart: Int,
            partQuantity: Int,
            btnScan: Button,
            btnScanSecondPart: Button,
            btnRemove: Button,
            lyPhoto: LinearLayout,
        ) {

            when (isRequiredScan) {
                1, 2, 3 -> {
                    btnScan.text =
                        "Scan QR Part Baru - [${totalUsedPart})/${partQuantity}]"
                    btnScan.visibility = View.VISIBLE
                    lyPhoto.visibility = View.INVISIBLE
                    if (totalUsedPart != 0) {
                        btnRemove.visibility = View.VISIBLE
                        if (totalUsedPart == partQuantity) {
                            btnScan.setVisibility(View.INVISIBLE)
                            lyPhoto.visibility = View.VISIBLE
                        }
                    } else {
                        btnRemove.visibility = View.GONE
                        btnScan.visibility = View.VISIBLE
                        lyPhoto.setVisibility(View.INVISIBLE)
                    }
                }

                4 -> {
                    btnScanSecondPart.setText("Scan Part - [${totalUsedPart}/${partQuantity}]")
                    //                btnScan.visibility = View.VISIBLE;
                    lyPhoto.visibility = View.VISIBLE
                    btnScanSecondPart.visibility = View.VISIBLE
                    if (totalUsedPart != 0) {
                        btnRemove.visibility = View.VISIBLE
                        if (totalUsedPart == partQuantity) {
                            btnScanSecondPart.setVisibility(View.GONE)
                            lyPhoto.visibility = View.VISIBLE
                        }
                    } else {
                        btnRemove.visibility = View.GONE
                        btnScanSecondPart.visibility = View.VISIBLE
                        lyPhoto.visibility = View.VISIBLE
                    }
                }
            }

            if (!isEditable) {
                btnScan.visibility = View.GONE
                btnScanSecondPart.visibility = View.GONE
                btnRemove.visibility = View.GONE
            }

        }

        fun setViewAfterRepairInspection(
            context: Context,
            isEditable: Boolean,
            isRequired: Int,
            tvLabelOptional: TextView,
        ) {
            when (isRequired) {
                1 -> {
                    tvLabelOptional.visibility = View.VISIBLE
                }

                else -> {
                    tvLabelOptional.visibility = View.GONE
                }
            }
        }

        fun setRepairDetailLayout(
            context: Context,
            stageId: Int,
            orderType: String,
            noteAfterRepairFromMechanic: String?,
            cvProblem: CardView,
            cvDriver: CardView,
            cvCheck: CardView,
            cvParts: CardView,
            cvAfterRepair: CardView,
            cvAfterRepairInspection: CardView,
            cvAfterRepairTireInspection: CardView,
            cvAfterRepairWaste: CardView,
            cvComplain: CardView,
            lyAfterCheckNote: LinearLayout,
            lyAfterRepairNote: LinearLayout,
            etAfterCheckNote: EditText,
            etAfterRepairNote: EditText,
            lyAdditionalPartRequest: LinearLayout,
            btnAdditionalPartRequest: Button,
            btnRepairStart: Button,
            btnRepairStart1: Button,
            btnRepairNext: Button,
            btnRepairDone: Button,
            btnCheckStart: Button,
            btnCheckDone: Button
        ) {

            when (stageId) {
                12 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.GONE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.VISIBLE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE

                }

                13 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, true)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.VISIBLE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                14 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                15, 16, 17 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                18 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.VISIBLE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                19 -> {

                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility =
                        if (noteAfterRepairFromMechanic != null) View.VISIBLE else View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, true)
                    lyAdditionalPartRequest.visibility = View.VISIBLE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility =
                        if (noteAfterRepairFromMechanic != null) View.GONE else View.VISIBLE
                    btnRepairDone.visibility =
                        if (noteAfterRepairFromMechanic != null) View.VISIBLE else View.GONE
                }

                20, 21, 22 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.VISIBLE
                    cvAfterRepairTireInspection.visibility = View.VISIBLE
                    cvAfterRepairWaste.visibility = View.VISIBLE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                23 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.VISIBLE
                    cvAfterRepairTireInspection.visibility = View.VISIBLE
                    cvAfterRepairWaste.visibility = View.VISIBLE
                    cvComplain.visibility = View.VISIBLE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                24, 25 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.VISIBLE
                    cvAfterRepairTireInspection.visibility = View.VISIBLE
                    cvAfterRepairWaste.visibility = View.VISIBLE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                26, 27, 28 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                29 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.VISIBLE
                    cvAfterRepairTireInspection.visibility = View.VISIBLE
                    cvAfterRepairWaste.visibility = View.VISIBLE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                31 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                32 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.GONE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                33 -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.GONE
                    cvAfterRepairInspection.visibility = View.GONE
                    cvAfterRepairTireInspection.visibility = View.GONE
                    cvAfterRepairWaste.visibility = View.GONE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }

                else -> {
                    cvProblem.visibility = View.VISIBLE
                    cvDriver.visibility = View.VISIBLE
                    cvCheck.visibility = View.VISIBLE
                    cvParts.visibility = View.VISIBLE
                    cvAfterRepair.visibility = View.VISIBLE
                    cvAfterRepairInspection.visibility = View.VISIBLE
                    cvAfterRepairTireInspection.visibility = View.VISIBLE
                    cvAfterRepairWaste.visibility = View.VISIBLE
                    cvComplain.visibility = View.GONE
                    setEditTextEnabled(context, lyAfterCheckNote, etAfterCheckNote, false)
                    setEditTextEnabled(context, lyAfterRepairNote, etAfterRepairNote, false)
                    lyAdditionalPartRequest.visibility = View.GONE
                    btnCheckStart.visibility = View.GONE
                    btnCheckDone.visibility = View.GONE
                    btnRepairStart.visibility = View.GONE
                    btnRepairNext.visibility = View.GONE
                    btnRepairDone.visibility = View.GONE
                }
            }

        }

        fun setEditTextEnabled(
            context: Context,
            lyEdit: LinearLayout,
            editText: EditText,
            isEnabled: Boolean
        ) {
            if (isEnabled) {
                lyEdit.setBackgroundResource(R.drawable.bg_button_rounded_outline_grey)
                editText.isFocusableInTouchMode = true
                editText.isFocusable = true
                editText.isLongClickable = true
                lyEdit.setOnClickListener { editText.requestFocus() }
            } else {
                lyEdit.setBackgroundResource(R.drawable.bg_rounded_textview_greysoft)
                editText.isFocusableInTouchMode = false
                editText.isFocusable = false
                editText.isLongClickable = false
                lyEdit.setOnClickListener { }
            }
        }

        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


        fun parseErrorMessageFromJson(message: String): ErrorResponse? {

            val gson = Gson()
            var obj: ErrorResponse? = null
            obj = gson.fromJson(message, ErrorResponse::class.java)

            return obj


        }
    }


}