package com.daffamuhtar.fmkotlin.ui.repair_tab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.appv4.RepairRepositoryImpl4
import com.daffamuhtar.fmkotlin.appv4.common.FooterAdapter
import com.daffamuhtar.fmkotlin.appv4.ui.RepairUiState
import com.daffamuhtar.fmkotlin.appv4.ui.view_binding.RepairAdapterNew
import com.daffamuhtar.fmkotlin.appv4.util.collect
import com.daffamuhtar.fmkotlin.appv4.util.collectLast
import com.daffamuhtar.fmkotlin.constants.Constants
import com.daffamuhtar.fmkotlin.constants.ConstantsApp
import com.daffamuhtar.fmkotlin.data.model.Filter
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.FragmentRepairTaskAdhocBinding
import com.daffamuhtar.fmkotlin.databinding.FragmentRepairTaskNonperiodBinding
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.repair_detail.RepairDetailActivity
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map

class RepairTaskNonperiodFragment : Fragment() {

//    data binding (ui state)
//    var userAdapter: RepairDataBindingAdapter = RepairDataBindingAdapter()

    //view binding

    private lateinit var viewModel: RepairTaskNonperiodFragmentViewModel

    private lateinit var binding: FragmentRepairTaskNonperiodBinding
    private lateinit var context: Context

    private val filterList = ArrayList<Filter>()

    private val repairAdapter: RepairAdapterNew = RepairAdapterNew()
    private val filterAdapter: FilterAdapter = FilterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepairTaskNonperiodBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        declare()
        setAdapter()
        setListener()
        prepareFilter()
    }

    private fun declare() {
        context = requireActivity()
        viewModel = RepairTaskNonperiodFragmentViewModel(
            RepairRepositoryImpl4(
                ApiConfig.getRetrofit2(context, ConstantsApp.BASE_URL_V2_0)!!
                    .create(RepairServices::class.java)
            ), context
        )

        collectLast(viewModel.repairPagingData, ::setRepairList)

    }

    private fun setListener() {
        binding.btnRetry.setOnClickListener { repairAdapter.retry() }

        binding.srCheck.setOnRefreshListener {
            repairAdapter.refresh()
        }
    }

//    ===================

    private fun loading(value: Boolean) {
        binding.srCheck.isRefreshing = value
    }

    private fun prepareFilter() {
        filterList.clear()

        filterList.add(
            Filter(
                true,
                0,
                "Semua"
            )
        )
        filterList.add(
            Filter(
                false,
                12,
                "Menunggu"
            )
        )
        filterList.add(
            Filter(
                false,
                13,
                "Diperiksa"
            )
        )
        filterList.add(
            Filter(
                false,
                14,
                "Approval"
            )
        )


        filterAdapter.setAllFilter(filterList)

        binding.rvFilter.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvFilter.adapter = filterAdapter


        filterAdapter.setOnItemClickCallback(object : FilterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Filter, position: Int) {

                for (i in filterList.indices) {
                    val filter = filterList[i]
                    filter.isActive = i == position
                }
                filterAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun setAdapter() {
        collect(flow = repairAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setRepairUiState
        )

        viewModel.dogBreeds.observe(requireActivity()){

        }
        binding.rvUsers.adapter = repairAdapter.withLoadStateFooter(FooterAdapter(repairAdapter::retry))
        repairAdapter.setOnItemClickCallback(object : RepairAdapterNew.OnItemClickCallback {
            override fun onItemClicked(data: Repair, position: Int) {
//                Toast.makeText(context, data.orderId, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, RepairDetailActivity::class.java)
                intent.putExtra(Constants.EXTRA_SPKID, data.spkId)
                intent.putExtra(Constants.EXTRA_ORDERID, data.orderId)
                intent.putExtra(Constants.EXTRA_VID, data.vehicleId)
                intent.putExtra(Constants.EXTRA_VBRAND, data.vehicleBrand)
                intent.putExtra(Constants.EXTRA_VTYPE, data.vehicleType)
                intent.putExtra(Constants.EXTRA_VVAR, data.vehicleVarian)
                intent.putExtra(Constants.EXTRA_VYEAR, data.vehicleYear)
                intent.putExtra(Constants.EXTRA_VLICEN, data.vehicleLicenseNumber)
                intent.putExtra(Constants.EXTRA_VDIS, data.vehicleDistrict)
                intent.putExtra(Constants.EXTRA_VLID, data.vehicleLambungId)
//                if (position ==0 ){
//                    intent.putExtra(Constanta.EXTRA_STAGEID, "13".toInt())
//                }else{
                intent.putExtra(Constants.EXTRA_STAGEID, data.stageId.toInt())
//                }
                intent.putExtra(Constants.EXTRA_STAGENAME, data.stageName)
                intent.putExtra(Constants.EXTRA_LOCOPTION, data.locationOption)
                intent.putExtra(Constants.EXTRA_SASSIGN, data.startAssignment)
                intent.putExtra(Constants.EXTRA_ISSTORING, data.isStoring)
                intent.putExtra(Constants.EXTRA_NOTESA, data.noteSA)
                startActivity(intent)
            }
        })

    }

    fun getViewVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE

    private fun setRepairUiState(loadState: LoadState) {
//        binding.executeWithAction {
//            repairUiState = RepairUiState(loadState,refreshCount.toString())
//        }

        binding.apply {
            progressBar.visibility = getViewVisibility(isVisible = loadState is LoadState.Loading)
            srCheck.isRefreshing = loadState is LoadState.Loading
            rvUsers.scrollToPosition(0);
            rvUsers.visibility = getViewVisibility(isVisible = loadState is LoadState.NotLoading)
            tvError.visibility = getViewVisibility(isVisible = loadState is LoadState.Error)
            btnRetry.visibility = getViewVisibility(isVisible = loadState is LoadState.Error)
        }


//        lifecycleScope.launch {
//            userAdapter.loadStateFlow
//                // Only emit when REFRESH LoadState for RemoteMediator changes.
//                .distinctUntilChangedBy { it.refresh }
//                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
//                .filter { it.source.refresh is LoadState.NotLoading }
//                .collect() { binding.rvUsers.scrollToPosition(0) }
//        }
    }



    private fun setRepairUiStateListener(loadState: LoadState) {
        val repairUiState = RepairUiState(loadState,2.toString())

        repairUiState.isRefreshing()
    }

    //view binding
    private suspend fun setRepairList(repairItemsPagingData: PagingData<Repair>) {
        repairAdapter.submitData(repairItemsPagingData)
    }

}
