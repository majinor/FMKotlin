package com.daffamuhtar.fmkotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.daffamuhtar.fmkotlin.R
import java.text.SimpleDateFormat
import java.util.*

class RepairHelper {
    companion object {
        fun getRepairTitle(orderId: String, isStoring: String): String {
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
                val formatter = SimpleDateFormat("EEEE, d MMM\nHH:mm", locale)
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



        fun getRepairIcon(orderId: String, isStoring: String): Int {
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
            stageId: String,
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
            tvStage.setTextColor(ContextCompat.getColor(context,stageTextColor))

//            tvStage.setTextColor(stageTextColor)

        }


    }
}