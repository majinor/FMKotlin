package com.daffamuhtar.fmkotlin.appv2.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadType
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.data.model.Filter
import com.daffamuhtar.fmkotlin.data.model.HorizontalCalendar
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairOngoingBinding
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairV2Binding
import com.daffamuhtar.fmkotlin.ui.adapter.FilterAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.HorizontalCalendarAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.PagingListAdapter
import com.daffamuhtar.fmkotlin.ui.adapter.RepairAdapter
import com.daffamuhtar.fmkotlin.ui.repair_ongoing.RepairOngoingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepairV2Activity : AppCompatActivity() {

    private val repairV2ViewModel:RepairV2ViewModel by viewModel()

    private val context: Context = this@RepairV2Activity

    private lateinit var binding: ActivityRepairV2Binding

    private val repairList = ArrayList<Repair>()
    private val filterList = ArrayList<Filter>()
    private val calendarList = ArrayList<HorizontalCalendar>()

    private val repairAdapter: RepairV2AdapterNew = RepairV2AdapterNew()
    private var pagingListAdapter : PagingListAdapter=  PagingListAdapter()
    private val filterAdapter: FilterAdapter = FilterAdapter()
    private lateinit var horizontalCalendarAdapter: HorizontalCalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairV2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        declare()
        initViewModel()
        prepareCalendarView()
        prepareFilter()
        callData()
    }

    private fun callData() {
    }

    private fun prepareFilter() {
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

        binding.rvCoFilter.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvCoFilter.adapter = filterAdapter
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

    private fun prepareCalendarView() {

        calendarList.add(HorizontalCalendar("2024-2-1",5,true))
        calendarList.add(HorizontalCalendar("2024-2-2",0,false))
        calendarList.add(HorizontalCalendar("2024-2-3",0,false))
        calendarList.add(HorizontalCalendar("2024-2-4",1,false))
        calendarList.add(HorizontalCalendar("2024-2-5",0,false))
        calendarList.add(HorizontalCalendar("2024-2-6",0,false))
        calendarList.add(HorizontalCalendar("2024-2-7",1,false))
        calendarList.add(HorizontalCalendar("2024-2-8",1,false))

        horizontalCalendarAdapter.setItems(calendarList)

        binding.rvCoCalendar.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        binding.rvCoCalendar.adapter = horizontalCalendarAdapter

    }

    private fun initViewModel() {
        lifecycleScope.launch {
            repairV2ViewModel.beerPagingFlow.collectLatest { repairs -> pagingListAdapter?.submitData(repairs)}

        }
        binding.srCheck.setOnRefreshListener {
//            repairV2ViewModel.getRepairOngoingMetaData(this, ConstantsApp.BASE_URL_V2_0, "MEC-MBA-99")
            pagingListAdapter?.refresh()

            binding.srCheck.isRefreshing= false
        }
    }

    private fun declare() {
        horizontalCalendarAdapter = HorizontalCalendarAdapter(context)


        binding.rvCheck.layoutManager = LinearLayoutManager(this)
        binding.rvCheck.adapter = pagingListAdapter
    }

    private suspend fun setRepairList(repairItemsPagingData: PagingData<Repair>) {
        repairAdapter.submitData(repairItemsPagingData)
    }
}