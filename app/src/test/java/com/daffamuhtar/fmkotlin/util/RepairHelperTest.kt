package com.daffamuhtar.fmkotlin.util

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.daffamuhtar.fmkotlin.constants.ConstantsRepair
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.math.log

@RunWith(RobolectricTestRunner::class)
class RepairHelperTest{

    lateinit var repairHelper: RepairHelper

    val isStoring1 = "1"
    val isStoring0 = "0"

    val spkIdAdhoc = "SPK/12312/12312/ASDAS"
    val spkIdPeriod = "SPK/PB/12312/12312/ASDAS"
    val spkIdNonperiod = "SPK/PNB/12312/12312/ASDAS"
    val spkIdTire = "SPK/TIRE/12312/12312/ASDAS"

    lateinit var context : Context

    @Before
    fun setUp() {
        repairHelper = RepairHelper()
    }

    @Test
    fun getRepairTypeAdhoc() {
        mockkObject(RepairHelper.Companion)
        assertEquals(ConstantsRepair.ORDER_TYPE_ADHOC, RepairHelper.getRepairOrderTypeWithContext(spkIdAdhoc, isStoring0,ApplicationProvider.getApplicationContext()))
    }

    @Test
    fun getRepairTypeStoring() {
        mockkObject(RepairHelper.Companion)
        assertEquals(ConstantsRepair.ORDER_TYPE_ADHOC_STORING, RepairHelper.getRepairOrderType(spkIdAdhoc, isStoring1))
    }

    @Test
    fun getRepairTypeMaintenance() {
        mockkObject(RepairHelper.Companion)
        every { RepairHelper.getRepairOrderType(spkIdPeriod, isStoring0) } returns ConstantsRepair.ORDER_TYPE_NPM
    }

    @Test
    fun getRepairTypeNonperiod() {
        mockkObject(RepairHelper.Companion)
        every { RepairHelper.getRepairOrderType(spkIdNonperiod, isStoring0) } returns  ConstantsRepair.ORDER_TYPE_NPM
    }

    @Test
    fun getRepairTypeTire() {
        mockkObject(RepairHelper.Companion)
        every { RepairHelper.getRepairOrderType(spkIdTire, isStoring0) } returns  ConstantsRepair.ORDER_TYPE_TIRE
    }

    @Test
    fun testclass() {
        assertEquals(4, 2 + 2)
    }
}