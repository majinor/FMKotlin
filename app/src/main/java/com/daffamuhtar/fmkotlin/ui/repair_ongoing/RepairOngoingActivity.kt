package com.daffamuhtar.fmkotlin.ui.repair_ongoing

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.app.Server
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairOngoingBinding
import com.daffamuhtar.fmkotlin.databinding.ItemTabRepairBinding
import com.daffamuhtar.fmkotlin.ui.check.CheckViewModel
import com.daffamuhtar.fmkotlin.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class RepairOngoingActivity : AppCompatActivity() {

    private lateinit var checkViewModel: CheckViewModel

    private lateinit var binding: ActivityRepairOngoingBinding

    private val context: Context = this@RepairOngoingActivity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairOngoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        initTab()
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

        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
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
                    0 -> fragment = RepairOngoingAdhocFragment()
                    1 -> fragment = RepairOngoingPeriodFragment()
                    2 -> fragment = RepairOngoingNonperiodFragment()
                    3 -> fragment = RepairOngoingTireFragment()
                }
            } else {
                when (position) {
                    0 -> fragment = RepairOngoingAdhocFragment()
                    1 -> fragment = RepairOngoingPeriodFragment()
                    2 -> fragment = RepairOngoingNonperiodFragment()
                    3 -> fragment = RepairOngoingTireFragment()
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