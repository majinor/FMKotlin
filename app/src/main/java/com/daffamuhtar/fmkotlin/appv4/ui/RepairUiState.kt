package com.daffamuhtar.fmkotlin.appv4.ui

import android.content.Context
import androidx.paging.LoadState
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.appv4.common.BaseUiState

/**
 * Created by Oguz Sahin on 11/11/2021.
 */
data class RepairUiState(
    private val loadState: LoadState,
    private val page: String
) : BaseUiState() {

    fun getProgressBarVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)

    fun getListVisibility() = getViewVisibility(isVisible = loadState is LoadState.NotLoading)

    fun getErrorVisibility() = getViewVisibility(isVisible = loadState is LoadState.Error)

    fun isRefreshing() = loadState is LoadState.Loading

    fun getPage() = page

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        loadState.error.localizedMessage ?: context.getString(R.string.something_went_wrong)
    } else ""
}