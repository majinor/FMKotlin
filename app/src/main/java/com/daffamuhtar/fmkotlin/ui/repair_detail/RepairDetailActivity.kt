package com.daffamuhtar.fmkotlin.ui.repair_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.daffamuhtar.fmkotlin.databinding.ActivityCheckBinding
import com.daffamuhtar.fmkotlin.databinding.ActivityRepairDetailBinding
import com.daffamuhtar.fmkotlin.ui.check.CheckViewModel

class RepairDetailActivity : AppCompatActivity() {

    private lateinit var repairDetailViewModel: RepairDetailViewModel
    private lateinit var binding: ActivityRepairDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepairDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getData()
        repairDetailViewModel = ViewModelProvider(this)[RepairDetailViewModel::class.java]

    }

    private fun getData() {

    }
}