package com.daffamuhtar.fmkotlin.ui.repair_tab

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.viewpager.widget.ViewPager
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.appv4.common.FooterAdapter
import com.daffamuhtar.fmkotlin.appv4.ui.RepairUiState
import com.daffamuhtar.fmkotlin.appv4.ui.view_binding.RepairAdapterNew
import com.daffamuhtar.fmkotlin.appv4.util.collect
import com.daffamuhtar.fmkotlin.data.model.Repair
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairTaskBinding
import com.daffamuhtar.fmkotlin.databinding.ItemTabRepairBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.Nullable

class RepairTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepairTaskBinding
    lateinit var viewModel: RepairTaskViewModel

//    data binding (ui state)
//    var userAdapter: RepairDataBindingAdapter = RepairDataBindingAdapter()

    //view binding
    var repairAdapter: RepairAdapterNew = RepairAdapterNew()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


//        viewModel = RepairTabViewModel(
//            RepairRepositoryImpl4(
//                ApiConfig.getRetrofit2(this, ConstantsApp.BASE_URL_V2_0)!!
//                    .create(RepairServices::class.java)
//            ), this
//        )`

//        setBinding()
//        setListener()
//        setAdapter()

        binding.btnR4Awal.text = "Ini dia"

//        viewModel.hehe.observe(this){
//            binding.btnR4Awal.text = it
//        }
//        collectLast(viewModel.repairPagingData, ::setRepairList)
//        initTab()
        setupViewPager(binding.viewPagerContainer)
        initTabOnly()
    }

    private fun initTabOnly() {
        binding.tabs.setupWithViewPager(binding.viewPagerContainer)

        val _bindingTab0 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab1 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab2 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab3 = ItemTabRepairBinding.inflate(layoutInflater)

        binding.tabs.getTabAt(0)?.customView = _bindingTab0.root
        _bindingTab0.tvTitle.setTextColor(Color.BLACK)
        _bindingTab0.tvTitle.text = "Adhoc"

        binding.tabs.getTabAt(1)?.customView = _bindingTab1.root
        _bindingTab1.tvTitle.text = "PB"

        binding.tabs.getTabAt(2)?.customView = _bindingTab2.root
        _bindingTab2.tvTitle.text = "PNB"

        binding.tabs.getTabAt(3)?.customView = _bindingTab3.root
        _bindingTab3.tvTitle.text = "Ban"

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> _bindingTab0.tvTitle.setTextColor(Color.BLACK)
                    1 -> _bindingTab1.tvTitle.setTextColor(Color.BLACK)
                    2 -> _bindingTab2.tvTitle.setTextColor(Color.BLACK)
                    3 -> _bindingTab3.tvTitle.setTextColor(Color.BLACK)

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> _bindingTab0.tvTitle.setTextColor(Color.GRAY)
                    1 -> _bindingTab1.tvTitle.setTextColor(Color.GRAY)
                    2 -> _bindingTab2.tvTitle.setTextColor(Color.GRAY)
                    3 -> _bindingTab3.tvTitle.setTextColor(Color.GRAY)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(RepairTaskAdhocFragment(), "Adhoc")
        adapter.addFragment(RepairTaskPeriodFragment(), "Period")
        adapter.addFragment(RepairTaskNonperiodFragment(), "Nonperiod")
        adapter.addFragment(RepairTaskTireFragment(), "Tire")

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }

    // This "ViewPagerAdapter" class overrides functions which are
    // necessary to get information about which item is selected
    // by user, what is title for selected item and so on.*/
    class ViewPagerAdapter : FragmentPagerAdapter {

        // objects of arraylist. One is of Fragment type and
        // another one is of String type.*/
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        // this is a secondary constructor of ViewPagerAdapter class.
        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        // returns which item is selected from arraylist of fragments.
        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        // returns which item is selected from arraylist of titles.
        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        // returns the number of items present in arraylist.
        override fun getCount(): Int {
            return fragmentList1.size
        }

        // this function adds the fragment and title in 2 separate  arraylist.
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }
    }

    private fun setBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_repair_v4)
    }
    var refreshCount: Int = 0

    private fun setListener() {
        binding.btnRetry.setOnClickListener { repairAdapter.retry() }

        binding.srCheck.setOnRefreshListener {
            repairAdapter.refresh()
            refreshCount = refreshCount+1
        }

        binding.btnR4Awal.setOnClickListener {
            viewModel.fetchDogBreeds(refreshCount)
        }
    }


    private fun setAdapter() {
        collect(flow = repairAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setRepairUiState
        )

        viewModel.dogBreeds.observe(this){

        }
        binding.rvUsers.adapter = repairAdapter.withLoadStateFooter(FooterAdapter(repairAdapter::retry))
    }

    fun getViewVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE

    private fun setRepairUiState(loadState: LoadState) {
//        binding.executeWithAction {
//            repairUiState = RepairUiState(loadState,refreshCount.toString())
//        }

        binding.apply {
            progressBar.visibility = getViewVisibility(isVisible = loadState is LoadState.Loading)
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
        val repairUiState = RepairUiState(loadState,refreshCount.toString())

        repairUiState.isRefreshing()
    }

//    data binding (ui state)
//    private suspend fun setRepairList(repairItemsPagingData: PagingData<RepairItemUiState>) {
//        userAdapter.submitData(repairItemsPagingData)
//    }

    //view binding
    private suspend fun setRepairList(repairItemsPagingData: PagingData<Repair>) {
        repairAdapter.submitData(repairItemsPagingData)
    }


    private fun initTab() {

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        if (notiftype!=null){
//            mIntentSectionsPagerAdapter = new IntentSectionsPagerAdapter(getSupportFragmentManager());
//            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//        }else{
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
//        }

        // Set up the ViewPager with the sections adapter.
        //        }

        // Set up the ViewPager with the sections adapter.
        binding.viewPagerContainer.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPagerContainer)

        val _bindingTab0 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab1 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab2 = ItemTabRepairBinding.inflate(layoutInflater)
        val _bindingTab3 = ItemTabRepairBinding.inflate(layoutInflater)

        binding.tabs.getTabAt(0)?.customView = _bindingTab0.root
        _bindingTab0.tvTitle.setTextColor(Color.BLACK)
        _bindingTab0.tvTitle.text = "Adhoc"

        binding.tabs.getTabAt(1)?.customView = _bindingTab1.root
        _bindingTab1.tvTitle.text = "PB"

        binding.tabs.getTabAt(2)?.customView = _bindingTab2.root
        _bindingTab2.tvTitle.text = "PNB"

        binding.tabs.getTabAt(3)?.customView = _bindingTab3.root
        _bindingTab3.tvTitle.text = "Ban"

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> _bindingTab0.tvTitle.setTextColor(Color.BLACK)
                    1 -> _bindingTab1.tvTitle.setTextColor(Color.BLACK)
                    2 -> _bindingTab2.tvTitle.setTextColor(Color.BLACK)
                    3 -> _bindingTab3.tvTitle.setTextColor(Color.BLACK)

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> _bindingTab0.tvTitle.setTextColor(Color.GRAY)
                    1 -> _bindingTab1.tvTitle.setTextColor(Color.GRAY)
                    2 -> _bindingTab2.tvTitle.setTextColor(Color.GRAY)
                    3 -> _bindingTab3.tvTitle.setTextColor(Color.GRAY)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    class SectionsPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null

//            if (notiftype!=null){
//                if(notiftype.equals("nonperiod")){
//                    fragment = new UmRepairRepairNonperiodFragment();
//                }
//            }
            if (Server.companyType == "1") {
                when (position) {
                    0 -> fragment = RepairTaskAdhocFragment()
                    1 -> fragment = RepairTaskPeriodFragment()
                    2 -> fragment = RepairTaskPeriodFragment()
                    3 -> fragment = RepairTaskPeriodFragment()
                }
            } else {
                when (position) {
                    0 -> fragment = RepairTaskAdhocFragment()
                    1 -> fragment = RepairTaskPeriodFragment()
                    2 -> fragment = RepairTaskPeriodFragment()
                    3 -> fragment = RepairTaskPeriodFragment()
                }
            }
            val bundle = Bundle()
//            bundle.putInt(Constanta.EXTRA_STAGEID, stageId)
            // set Fragmentclass Arguments
            fragment!!.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Perbaikan"
                1 -> return "Berkala"
                2 -> return "Non Berkala"
                3 -> return "Ban"
            }
            return null
        }
    }
}
