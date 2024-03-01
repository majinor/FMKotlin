package com.daffamuhtar.fmkotlin.appv4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairV4Binding
import com.daffamuhtar.fmkotlin.ui.repair_ongoing.RepairOngoingViewModel

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.appv4.RepairRepositoryImpl4
import com.daffamuhtar.fmkotlin.appv4.common.FooterAdapter
import com.daffamuhtar.fmkotlin.appv4.util.collect
import com.daffamuhtar.fmkotlin.appv4.util.collectLast
import com.daffamuhtar.fmkotlin.appv4.util.executeWithAction
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.services.RepairServices
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

class RepairV4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRepairV4Binding
    lateinit var viewModel: RepairOngoingViewModel

    var userAdapter: RepairAdapter = RepairAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = RepairOngoingViewModel(
            null,
            RepairRepositoryImpl4(
                ApiConfig.getRetrofit2(this, ConstantsApp.BASE_URL_V2_0)!!
                    .create(RepairServices::class.java)
            )
        )


        setBinding()
        setListener()
        setAdapter()

        collectLast(viewModel.userItemsUiStates, ::setUsers)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repair_v4)
    }

    private fun setListener() {
        binding.btnRetry.setOnClickListener { userAdapter.retry() }

        binding.srCheck.setOnRefreshListener {
            userAdapter.refresh()
        }
    }


    private fun setAdapter() {
        collect(flow = userAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setUsersUiState
        )
        binding.rvUsers.adapter = userAdapter.withLoadStateFooter(FooterAdapter(userAdapter::retry))
    }

    private fun setUsersUiState(loadState: LoadState) {
        binding.executeWithAction {
            usersUiState = UsersUiState(loadState)
        }
    }

    private suspend fun setUsers(userItemsPagingData: PagingData<UserItemUiState>) {
        userAdapter.submitData(userItemsPagingData)
    }


}