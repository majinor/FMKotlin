//package com.daffamuhtar.fmkotlin.appv2.data.remote
//
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.liveData
//
//class QuoteRepository (private val apiServices: RepairApiServices) {
//
//    fun getQuotes() = Pager(
//        config = PagingConfig(pageSize = 3, maxSize = 100),
//        pagingSourceFactory = { QuotePagingSource(apiServices) }
//    ).liveData
//}