package com.daffamuhtar.fmkotlin.ui.bottomsheet

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffamuhtar.fmkotlin.constants.Constanta
import com.daffamuhtar.fmkotlin.databinding.BsAdditionalPartRequestFormBinding
import com.daffamuhtar.fmkotlin.util.RepairHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AdditionalPartRequestBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: BsAdditionalPartRequestFormBinding
    private lateinit var spkId : String
    private lateinit var orderId : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BsAdditionalPartRequestFormBinding.inflate(inflater, container, false)

        getData()

        RepairHelper.setEditTextEnabled(requireActivity(), binding.lyAdditionalPartRequestForm, binding.etAdditionalPartRequestFormNote, false)

        binding.btnSend

        return binding.root
    }

    private fun getData() {
        orderId = arguments?.getString(Constanta.EXTRA_ORDERID)!!
        spkId = arguments?.getString(Constanta.EXTRA_SPKID)!!
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}