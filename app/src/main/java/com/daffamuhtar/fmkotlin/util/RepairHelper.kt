package com.daffamuhtar.fmkotlin.util

import android.os.Build
import com.daffamuhtar.fmkotlin.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RepairHelper {
    companion object {
        fun getRepairTitle(orderId: String, isStoring: String): String {
            val suborder: String = orderId.substring(4, 6)
            var repairTitle :String

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

        fun getRepairId(orderId: String, spkId: String): String {
            var repairId :String

            if (spkId==null) {
                repairId = orderId
            } else {
                repairId = spkId
            }
            return repairId
        }

        fun getRepairDate(date: String): String {
            var repairDate :String

            if (date!=null) {
                val locale = Locale("id")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMM \n HH:mm", locale)
                    repairDate=LocalDateTime.parse(date).format(dateTimeFormatter)

                } else {
                    val parser =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale)
                    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm",locale)
                    repairDate = parser.parse(date)?.let { formatter.format(it) }.toString()
                }

            } else {
                repairDate = "menunggu dijadwalkan"

            }

            return repairDate
        }

        fun getRepairIcon(orderId: String,isStoring: String): Int {
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


    }
}