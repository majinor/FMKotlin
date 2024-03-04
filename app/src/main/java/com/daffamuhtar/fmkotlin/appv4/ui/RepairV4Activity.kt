package com.daffamuhtar.fmkotlin.appv4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairV4Binding

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.appv4.RepairRepositoryImpl4
import com.daffamuhtar.fmkotlin.appv4.common.FooterAdapter
import com.daffamuhtar.fmkotlin.appv4.ui.data_binding.RepairAdapter
import com.daffamuhtar.fmkotlin.appv4.ui.data_binding.RepairItemUiState
import com.daffamuhtar.fmkotlin.appv4.ui.view_binding.RepairAdapterNew
import com.daffamuhtar.fmkotlin.appv4.util.collect
import com.daffamuhtar.fmkotlin.appv4.util.collectLast
import com.daffamuhtar.fmkotlin.appv4.util.executeWithAction
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.services.RepairServices
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RepairV4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRepairV4Binding
    lateinit var viewModel: RepairV4ViewModel

    //data binding (ui state)
//    var userAdapter: RepairAdapter = RepairAdapter()

    //view binding
    var userAdapter: RepairAdapterNew = RepairAdapterNew()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = RepairV4ViewModel(
            RepairRepositoryImpl4(
                ApiConfig.getRetrofit2(this, ConstantsApp.BASE_URL_V2_0)!!
                    .create(RepairServices::class.java)
            ), this
        )

        setBinding()
        setListener()
        setAdapter()

        collectLast(viewModel.repairPagingData, ::setRepairList)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repair_v4)
    }
    var refreshCount: Int = 0

    private fun setListener() {
        binding.btnRetry.setOnClickListener { userAdapter.retry() }

        binding.srCheck.setOnRefreshListener {
            userAdapter.refresh()
            refreshCount = refreshCount+1
        }
    }


    private fun setAdapter() {
        collect(flow = userAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setRepairUiState
        )
        binding.rvUsers.adapter = userAdapter.withLoadStateFooter(FooterAdapter(userAdapter::retry))
    }

    private fun setRepairUiState(loadState: LoadState) {
        binding.executeWithAction {
            repairUiState = RepairUiState(loadState,refreshCount.toString())
        }

        lifecycleScope.launch {
            userAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.source.refresh is LoadState.NotLoading }
                .collect() { binding.rvUsers.scrollToPosition(0) }
        }
    }

    private fun setRepairUiStateListener(loadState: LoadState) {
        val repairUiState = RepairUiState(loadState,refreshCount.toString())

        repairUiState.isRefreshing()
    }

    //data binding (ui state)
//    private suspend fun setRepairList(repairItemsPagingData: PagingData<RepairItemUiState>) {
//        userAdapter.submitData(repairItemsPagingData)
//    }

    //view binding
    private suspend fun setRepairList(repairItemsPagingData: PagingData<Repair>) {
        userAdapter.submitData(repairItemsPagingData)
    }

}