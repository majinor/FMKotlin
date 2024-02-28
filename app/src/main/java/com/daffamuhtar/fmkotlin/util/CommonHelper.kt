package com.daffamuhtar.fmkotlin.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommonHelper {
    companion object {

        fun getLocale() : Locale{
            return Locale("id")
        }

        fun getMonth(date: String): String {


            val parser = SimpleDateFormat("yyyy-MM-dd", getLocale())
            val formatter = SimpleDateFormat("MMM", getLocale())
            return parser.parse(date)?.let { formatter.format(it) }.toString()
        }

        fun getDate(date: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd", getLocale())
            val formatter = SimpleDateFormat("dd", getLocale())
            return parser.parse(date)?.let { formatter.format(it) }.toString()
        }

        fun getDay(date: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd", getLocale())
            val formatter = SimpleDateFormat("EE", getLocale())
            return parser.parse(date)?.let { formatter.format(it) }.toString()
        }
    }
}