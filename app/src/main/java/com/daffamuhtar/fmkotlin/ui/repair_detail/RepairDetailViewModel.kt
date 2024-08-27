package com.daffamuhtar.fmkotlin.ui.repair_detail

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.daffamuhtar.fmkotlin.app.ApiConfig
import com.daffamuhtar.fmkotlin.constants.ConstantsRepair
import com.daffamuhtar.fmkotlin.data.response.*
import com.daffamuhtar.fmkotlin.services.RepairServices
import com.daffamuhtar.fmkotlin.services.TireServices
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class RepairDetailViewModel : ViewModel() {

    private val _repairStageId = MutableLiveData<Int>()
    val repairStageId: LiveData<Int> = _repairStageId

    fun getRepairDetailRepairStage(
        context: Context,
        stageId: Int,
    ) {
        _repairStageId.value = stageId
    }
//    ======================================================

    private val _isLoadingGetAllRepairProblem = MutableLiveData<Boolean>()
    val isLoadingGetAllRepairProblem: LiveData<Boolean> = _isLoadingGetAllRepairProblem

    private val _isSuccessGetAllRepairProblem = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailProblemList: LiveData<Boolean> = _isSuccessGetAllRepairProblem

    private val _messageGetAllRepairProblem = MutableLiveData<String>()
    val messageGetAllRepairProblem: LiveData<String> = _messageGetAllRepairProblem

    private val _problemList = MutableLiveData<List<RepairDetailProblemResponse>>()
    val problemList: LiveData<List<RepairDetailProblemResponse>> = _problemList

    fun getRepairDetailProblemList(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetAllRepairProblem.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairProblem(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairProblem(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairProblem(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairProblem(spkId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairProblemNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairProblem(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailProblemResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailProblemResponse>>,
                response: Response<List<RepairDetailProblemResponse>>
            ) {
                _isLoadingGetAllRepairProblem.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val repair: List<RepairDetailProblemResponse>? = response.body()
                    if (responseBody != null) {
                        _problemList.value = repair!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetAllRepairProblem.value = status
                            _messageGetAllRepairProblem.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RepairDetailProblemResponse>>, t: Throwable) {
                _isLoadingGetAllRepairProblem.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetAllRepairProblem.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }


    private val _isLoadingGetRepairActiveDriver = MutableLiveData<Boolean>()
    val isLoadingGetRepairActiveDriver: LiveData<Boolean> = _isLoadingGetRepairActiveDriver

    private val _isSuccessGetRepairActiveDriver = MutableLiveData<Boolean>()
    val isSuccessGetRepairActiveDriver: LiveData<Boolean> = _isSuccessGetRepairActiveDriver

    private val _messageGetRepairActiveDriver = MutableLiveData<String>()
    val messageGetRepairActiveDriver: LiveData<String> = _messageGetRepairActiveDriver

    private val _driverActive = MutableLiveData<List<RepairDetailActiveDriverResponse>?>()
    val activeDriver: MutableLiveData<List<RepairDetailActiveDriverResponse>?> = _driverActive


    fun getRepairDetailActiveDriver(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetRepairActiveDriver.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairActiveDriver(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairActiveDriver(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairActiveDriver(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairActiveDriverPeriod(orderId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairActiveDriverNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairActiveDriver(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailActiveDriverResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailActiveDriverResponse>>,
                response: Response<List<RepairDetailActiveDriverResponse>>
            ) {
                _isLoadingGetRepairActiveDriver.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailActiveDriverResponse>? = response.body()
                    if (responseBody != null) {
                        _driverActive.value = data
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairActiveDriver.value = status
                            _messageGetRepairActiveDriver.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailActiveDriverResponse>>, t: Throwable) {
                _isLoadingGetRepairActiveDriver.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairActiveDriver.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    private val _isLoadingGetRepairWorkshopAndMechanicInfo = MutableLiveData<Boolean>()
    val isLoadingGetRepairWorkshopAndMechanicInfo: LiveData<Boolean> = _isLoadingGetRepairWorkshopAndMechanicInfo

// ==================================================================

    private val _isLoadingGetRepairWorkshopInfo = MutableLiveData<Boolean>()
    val isLoadingGetRepairWorkshopInfo: LiveData<Boolean> = _isLoadingGetRepairWorkshopInfo

    private val _isSuccessGetRepairWorkshopInfo = MutableLiveData<Boolean>()
    val isSuccessGetRepairWorkshopInfo: LiveData<Boolean> = _isSuccessGetRepairWorkshopInfo

    private val _messageGetRepairWorkshopInfo = MutableLiveData<String>()
    val messageGetRepairWorkshopInfo: LiveData<String> = _messageGetRepairWorkshopInfo

    private val _workshopInfo = MutableLiveData<List<RepairDetailWorkshopInfoResponse>?>()
    val workshopInfo: MutableLiveData<List<RepairDetailWorkshopInfoResponse>?> = _workshopInfo

// ==================================================================

    private val _isLoadingGetRepairMechanicInfo = MutableLiveData<Boolean>()
    val isLoadingGetRepairMechanicInfo: LiveData<Boolean> = _isLoadingGetRepairMechanicInfo

    private val _isSuccessGetRepairMechanicInfo = MutableLiveData<Boolean>()
    val isSuccessGetRepairMechanicInfo: LiveData<Boolean> = _isSuccessGetRepairMechanicInfo

    private val _messageGetRepairMechanicInfo = MutableLiveData<String>()
    val messageGetRepairMechanicInfo: LiveData<String> = _messageGetRepairMechanicInfo

    private val _mechanicInfo = MutableLiveData<List<RepairDetailMechanicInfoResponse>?>()
    val mechanicInfo: MutableLiveData<List<RepairDetailMechanicInfoResponse>?> = _mechanicInfo


    fun getRepairDetailWorkshopInfo(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetRepairWorkshopAndMechanicInfo.value = true
        _isLoadingGetRepairWorkshopInfo.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairWorkshopInfo(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairWorkshopInfo(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairWorkshopInfo(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairWorkshopInfoPeriod(orderId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairWorkshopInfoNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairWorkshopInfo(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailWorkshopInfoResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailWorkshopInfoResponse>>,
                response: Response<List<RepairDetailWorkshopInfoResponse>>
            ) {
                _isLoadingGetRepairWorkshopInfo.value = false
                if (_isLoadingGetRepairMechanicInfo.value == false){
                    _isLoadingGetRepairWorkshopAndMechanicInfo.value = false
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailWorkshopInfoResponse>? = response.body()
                    if (responseBody != null) {
                        _workshopInfo.value = data
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairWorkshopInfo.value = status
                            _messageGetRepairWorkshopInfo.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailWorkshopInfoResponse>>, t: Throwable) {
                _isLoadingGetRepairWorkshopInfo.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairWorkshopInfo.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    fun getRepairDetailMechanicInfo(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetRepairWorkshopAndMechanicInfo.value = true
        _isLoadingGetRepairMechanicInfo.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairMechanicInfo(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairMechanicInfo(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairMechanicInfo(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairMechanicInfoPeriod(orderId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairMechanicInfoNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairMechanicInfo(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailMechanicInfoResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailMechanicInfoResponse>>,
                response: Response<List<RepairDetailMechanicInfoResponse>>
            ) {
                _isLoadingGetRepairMechanicInfo.value = false
                if (_isLoadingGetRepairWorkshopInfo.value == false){
                    _isLoadingGetRepairWorkshopAndMechanicInfo.value = false
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailMechanicInfoResponse>? = response.body()
                    if (responseBody != null) {
                        _mechanicInfo.value = data
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairMechanicInfo.value = status
                            _messageGetRepairMechanicInfo.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailMechanicInfoResponse>>, t: Throwable) {
                _isLoadingGetRepairMechanicInfo.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairMechanicInfo.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

//    PART=======================================================================

    private val _isLoadingGetRepairDetailPartList = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailPartList: LiveData<Boolean> = _isLoadingGetRepairDetailPartList

    private val _isSuccessGetRepairDetailPartList = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailPartList: LiveData<Boolean> = _isSuccessGetRepairDetailPartList

    private val _messageGetRepairDetailPartList = MutableLiveData<String>()
    val messageGetRepairDetailPartList: LiveData<String> = _messageGetRepairDetailPartList

    private val _partList = MutableLiveData<List<RepairDetailPartResponse>>()
    val partList: MutableLiveData<List<RepairDetailPartResponse>> = _partList

    fun getRepairDetailPartList(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
        pbId: String?,
    ) {
        _isLoadingGetRepairDetailPartList.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailPart(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailPart(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailPart(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailPartPeriod(spkId!!)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailPartNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailPart(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailPartResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailPartResponse>>,
                response: Response<List<RepairDetailPartResponse>>
            ) {
                _isLoadingGetRepairDetailPartList.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailPartResponse>? = response.body()
                    if (responseBody != null) {
                        _partList.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailPartList.value = status
                            _messageGetRepairDetailPartList.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailPartResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailPartList.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailPartList.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    NOTE=======================================================================

    private val _isLoadingGetRepairDetailNote = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailNote: LiveData<Boolean> = _isLoadingGetRepairDetailNote

    private val _isSuccessGetRepairDetailNote = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailNote: LiveData<Boolean> = _isSuccessGetRepairDetailNote

    private val _messageGetRepairDetailNote = MutableLiveData<String>()
    val messageGetRepairDetailNote: LiveData<String> = _messageGetRepairDetailNote

    private val _note = MutableLiveData<List<RepairDetailNoteResponse>>()
    val note: MutableLiveData<List<RepairDetailNoteResponse>> = _note

    fun getRepairDetailNote(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetRepairDetailNote.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailNotes(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailNotes(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailNotes(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailNotesPeriod(spkId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailNotesNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailNotes(orderId)
        }
        client.enqueue(object : Callback<List<RepairDetailNoteResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailNoteResponse>>,
                response: Response<List<RepairDetailNoteResponse>>
            ) {
                _isLoadingGetRepairDetailNote.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailNoteResponse>? = response.body()
                    if (responseBody != null) {
                        _note.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailNote.value = status
                            _messageGetRepairDetailNote.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailNoteResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailNote.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailNote.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    CHECK=======================================================================

    private val _isLoadingGetRepairDetailAfterCheck = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterCheck: LiveData<Boolean> = _isLoadingGetRepairDetailAfterCheck

    private val _isSuccessGetRepairDetailAfterCheck = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterCheck: LiveData<Boolean> = _isSuccessGetRepairDetailAfterCheck

    private val _messageGetRepairDetailAfterCheck = MutableLiveData<String>()
    val messageGetRepairDetailAfterCheck: LiveData<String> = _messageGetRepairDetailAfterCheck

    private val _afterCheck = MutableLiveData<List<RepairDetailAfterCheckResponse>>()
    val afterCheck: MutableLiveData<List<RepairDetailAfterCheckResponse>> = _afterCheck

    fun getRepairDetailAfterCheck(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
    ) {
        _isLoadingGetRepairDetailAfterCheck.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        val client = services.getRepairDetailAfterCheck(orderId)

        client.enqueue(object : Callback<List<RepairDetailAfterCheckResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailAfterCheckResponse>>,
                response: Response<List<RepairDetailAfterCheckResponse>>
            ) {
                _isLoadingGetRepairDetailAfterCheck.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailAfterCheckResponse>? = response.body()
                    if (responseBody != null) {
                        _afterCheck.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterCheck.value = status
                            _messageGetRepairDetailAfterCheck.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailAfterCheckResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterCheck.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterCheck.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    AFTER REPAIR =======================================================================

    private val _isLoadingGetRepairDetailAfterRepairList = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterRepairList: LiveData<Boolean> = _isLoadingGetRepairDetailAfterRepairList

    private val _isSuccessGetRepairDetailAfterRepairList = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterRepairList: LiveData<Boolean> = _isSuccessGetRepairDetailAfterRepairList

    private val _messageGetRepairDetailAfterRepairList = MutableLiveData<String>()
    val messageGetRepairDetailAfterRepairList: LiveData<String> = _messageGetRepairDetailAfterRepairList

    private val _afterRepairList = MutableLiveData<List<RepairDetailAfterRepairResponse>>()
    val afterRepairList: MutableLiveData<List<RepairDetailAfterRepairResponse>> = _afterRepairList

    fun getRepairDetailAfterRepairList(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
        pbId: String?,
    ) {
        _isLoadingGetRepairDetailAfterRepairList.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailAfterRepair(orderId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailAfterRepair(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailAfterRepair(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailAfterRepairPeriod(orderId,pbId!!)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailAfterRepairNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailAfterRepair(orderId)
        }

        client.enqueue(object : Callback<List<RepairDetailAfterRepairResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailAfterRepairResponse>>,
                response: Response<List<RepairDetailAfterRepairResponse>>
            ) {
                _isLoadingGetRepairDetailAfterRepairList.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailAfterRepairResponse>? = response.body()
                    if (responseBody != null) {
                        _afterRepairList.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterRepairList.value = status
                            _messageGetRepairDetailAfterRepairList.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailAfterRepairResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterRepairList.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterRepairList.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    AFTER REPAIR INSPECTION =======================================================================

    private val _isLoadingGetRepairDetailAfterRepairInspectionList = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterRepairInspectionList: LiveData<Boolean> = _isLoadingGetRepairDetailAfterRepairInspectionList

    private val _isSuccessGetRepairDetailAfterRepairInspectionList = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterRepairInspectionList: LiveData<Boolean> = _isSuccessGetRepairDetailAfterRepairInspectionList

    private val _messageGetRepairDetailAfterRepairInspectionList = MutableLiveData<String>()
    val messageGetRepairDetailAfterRepairInspectionList: LiveData<String> = _messageGetRepairDetailAfterRepairInspectionList

    private val _afterRepairInspectionList = MutableLiveData<List<RepairDetailAfterRepairInspectionResponse>>()
    val afterRepairInspectionList: MutableLiveData<List<RepairDetailAfterRepairInspectionResponse>> = _afterRepairInspectionList

    fun getRepairDetailAfterRepairInspectionList(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
        pbId: String?,
    ) {
        _isLoadingGetRepairDetailAfterRepairInspectionList.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailAfterRepairInspection(orderId,spkId)
        }

        client.enqueue(object : Callback<List<RepairDetailAfterRepairInspectionResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailAfterRepairInspectionResponse>>,
                response: Response<List<RepairDetailAfterRepairInspectionResponse>>
            ) {
                _isLoadingGetRepairDetailAfterRepairInspectionList.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailAfterRepairInspectionResponse>? = response.body()
                    if (responseBody != null) {
                        _afterRepairInspectionList.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterRepairInspectionList.value = status
                            _messageGetRepairDetailAfterRepairInspectionList.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<RepairDetailAfterRepairInspectionResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterRepairInspectionList.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterRepairInspectionList.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    REPAIR DETAIL TIRE CONDITION CATEGORY =======================================================================

    private val _isLoadingGetRepairDetailTireConditionCategoryList = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailTireConditionCategoryList: LiveData<Boolean> = _isLoadingGetRepairDetailTireConditionCategoryList

    private val _isSuccessGetRepairDetailTireConditionCategoryList = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailTireConditionCategoryList: LiveData<Boolean> = _isSuccessGetRepairDetailTireConditionCategoryList

    private val _messageGetRepairDetailTireConditionCategoryList = MutableLiveData<String>()
    val messageGetRepairDetailTireConditionCategoryList: LiveData<String> = _messageGetRepairDetailTireConditionCategoryList

    private val _tireConditionCategoryList = MutableLiveData<List<TireConditionCategoryResponse>>()
    val tireConditionCategoryList: MutableLiveData<List<TireConditionCategoryResponse>> = _tireConditionCategoryList

    fun getRepairDetailTireConditionCategoryList(
        context: Context,
        apiVersion: String,
        vehicleId: String,
    ) {
        _isLoadingGetRepairDetailTireConditionCategoryList.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(TireServices::class.java)
        val client = services.getTireConditionCategory(vehicleId)

        client.enqueue(object : Callback<List<TireConditionCategoryResponse>> {
            override fun onResponse(
                call: Call<List<TireConditionCategoryResponse>>,
                response: Response<List<TireConditionCategoryResponse>>
            ) {
                _isLoadingGetRepairDetailTireConditionCategoryList.value = false

                if (response.isSuccessful) {
                    _isSuccessGetRepairDetailTireConditionCategoryList.value = true

                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<TireConditionCategoryResponse>? = response.body()
                    if (responseBody != null) {
                        _tireConditionCategoryList.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailTireConditionCategoryList.value = status
                            _messageGetRepairDetailTireConditionCategoryList.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<TireConditionCategoryResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailTireConditionCategoryList.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailTireConditionCategoryList.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }


//    AFTER REPAIR TIRE INSPECTION =======================================================================

    private val _isLoadingGetRepairDetailAfterRepairTireInspectionList = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterRepairTireInspectionList: LiveData<Boolean> = _isLoadingGetRepairDetailAfterRepairTireInspectionList

    private val _isSuccessGetRepairDetailAfterRepairTireInspectionList = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterRepairTireInspectionList: LiveData<Boolean> = _isSuccessGetRepairDetailAfterRepairTireInspectionList

    private val _messageGetRepairDetailAfterRepairTireInspectionList = MutableLiveData<String>()
    val messageGetRepairDetailAfterRepairTireInspectionList: LiveData<String> = _messageGetRepairDetailAfterRepairTireInspectionList

    private val _afterRepairTireInspectionList = MutableLiveData<List<TireInspectionItemResponse>>()
    val afterRepairTireInspectionList: MutableLiveData<List<TireInspectionItemResponse>> = _afterRepairTireInspectionList

    fun getRepairDetailAfterRepairTireInspectionList(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
        spkId: String?,
        vehicleId: String?,
    ) {
        _isLoadingGetRepairDetailAfterRepairTireInspectionList.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(TireServices::class.java)
        val client = services.getTireInspectionItemResultOnRepair(spkId,vehicleId)

        client.enqueue(object : Callback<List<TireInspectionItemResponse>> {
            override fun onResponse(
                call: Call<List<TireInspectionItemResponse>>,
                response: Response<List<TireInspectionItemResponse>>
            ) {
                _isLoadingGetRepairDetailAfterRepairTireInspectionList.value = false

                if (response.isSuccessful) {
                    _isSuccessGetRepairDetailAfterRepairTireInspectionList.value = true

                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<TireInspectionItemResponse>? = response.body()
                    if (responseBody != null) {
                        _afterRepairTireInspectionList.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterRepairTireInspectionList.value = status
                            _messageGetRepairDetailAfterRepairTireInspectionList.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")

                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }

                }

            }

            override fun onFailure(call: Call<List<TireInspectionItemResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterRepairTireInspectionList.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterRepairTireInspectionList.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    AFTER REPAIR WASTE =======================================================================

    private val _isLoadingGetRepairDetailAfterRepairWaste = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterRepairWaste: LiveData<Boolean> = _isLoadingGetRepairDetailAfterRepairWaste

    private val _isSuccessGetRepairDetailAfterRepairWaste = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterRepairWaste: LiveData<Boolean> = _isSuccessGetRepairDetailAfterRepairWaste

    private val _messageGetRepairDetailAfterRepairWaste = MutableLiveData<String>()
    val messageGetRepairDetailAfterRepairWaste: LiveData<String> = _messageGetRepairDetailAfterRepairWaste

    private val _afterRepairWaste = MutableLiveData<List<RepairDetailAfterRepairWasteResponse>>()
    val afterRepairWaste: MutableLiveData<List<RepairDetailAfterRepairWasteResponse>> = _afterRepairWaste

    fun getRepairDetailAfterRepairWaste(
        context: Context,
        apiVersion: String,
        orderType: String?,
        spkId: String?,
    ) {
        _isLoadingGetRepairDetailAfterRepairWaste.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailAfterRepairWaste(spkId)

        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailAfterRepairWaste(spkId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailAfterRepairWaste(spkId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailAfterRepairWastePeriod(spkId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailAfterRepairWasteNonperiod(spkId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailAfterRepairWaste(spkId)
        }

        client.enqueue(object : Callback<List<RepairDetailAfterRepairWasteResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailAfterRepairWasteResponse>>,
                response: Response<List<RepairDetailAfterRepairWasteResponse>>
            ) {
                _isLoadingGetRepairDetailAfterRepairWaste.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailAfterRepairWasteResponse>? = response.body()
                    if (responseBody != null) {
                        _afterRepairWaste.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterRepairWaste.value = status
                            _messageGetRepairDetailAfterRepairWaste.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RepairDetailAfterRepairWasteResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterRepairWaste.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterRepairWaste.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

    //    AFTER REPAIR COMPLAIN =======================================================================

    private val _isLoadingGetRepairDetailAfterRepairComplain = MutableLiveData<Boolean>()
    val isLoadingGetRepairDetailAfterRepairComplain: LiveData<Boolean> = _isLoadingGetRepairDetailAfterRepairComplain

    private val _isSuccessGetRepairDetailAfterRepairComplain = MutableLiveData<Boolean>()
    val isSuccessGetRepairDetailAfterRepairComplain: LiveData<Boolean> = _isSuccessGetRepairDetailAfterRepairComplain

    private val _messageGetRepairDetailAfterRepairComplain = MutableLiveData<String>()
    val messageGetRepairDetailAfterRepairComplain: LiveData<String> = _messageGetRepairDetailAfterRepairComplain

    private val _afterRepairComplain = MutableLiveData<List<RepairDetailAfterRepairComplainResponse>>()
    val afterRepairComplain: MutableLiveData<List<RepairDetailAfterRepairComplainResponse>> = _afterRepairComplain

    fun getRepairDetailAfterRepairComplain(
        context: Context,
        apiVersion: String,
        orderType: String?,
        orderId: String,
    ) {
        _isLoadingGetRepairDetailAfterRepairComplain.value = true

        val retrofit = ApiConfig.getRetrofit(context, apiVersion)!!
        val services = retrofit.create(RepairServices::class.java)
        var client = services.getRepairDetailAfterRepairComplain(orderId)

        when (orderType){
            ConstantsRepair.ORDER_TYPE_ADHOC -> client = services.getRepairDetailAfterRepairComplain(orderId)
            ConstantsRepair.ORDER_TYPE_ADHOC_STORING -> client = services.getRepairDetailAfterRepairComplain(orderId)
            ConstantsRepair.ORDER_TYPE_MAINTENANCE -> client = services.getRepairDetailAfterRepairComplainPeriod(orderId)
            ConstantsRepair.ORDER_TYPE_NPM -> client = services.getRepairDetailAfterRepairComplainNonperiod(orderId)
            ConstantsRepair.ORDER_TYPE_TIRE -> client = services.getRepairDetailAfterRepairComplain(orderId)
        }

        client.enqueue(object : Callback<List<RepairDetailAfterRepairComplainResponse>> {
            override fun onResponse(
                call: Call<List<RepairDetailAfterRepairComplainResponse>>,
                response: Response<List<RepairDetailAfterRepairComplainResponse>>
            ) {
                _isLoadingGetRepairDetailAfterRepairComplain.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.w(
                        "RESULT:",
                        GsonBuilder().setPrettyPrinting().create().toJson(response.body())
                    )

                    val data: List<RepairDetailAfterRepairComplainResponse>? = response.body()
                    if (responseBody != null) {
                        _afterRepairComplain.value = data!!
                    }

                } else {
                    val responseErrorBody = response.errorBody()
                    if (responseErrorBody != null) {
                        Log.w(
                            "RESULT:",
                            "onResponse: Not Success " + response.code() + GsonBuilder().setPrettyPrinting()
                                .create().toJson(responseErrorBody)
                        )
                        val converter: Converter<ResponseBody?, ErrorResponse> =
                            retrofit.responseBodyConverter(
                                ErrorResponse::class.java,
                                arrayOfNulls<Annotation>(0)
                            )
                        var errorModel: ErrorResponse? = null
                        try {
                            errorModel = converter.convert(responseErrorBody)
                            val status: Boolean = errorModel?.status ?: false
                            val message: String = errorModel?.message ?: "no message"

                            _isSuccessGetRepairDetailAfterRepairComplain.value = status
                            _messageGetRepairDetailAfterRepairComplain.value = message

                            Log.e("Call Failed", "Gagal Mengirim $message")
                                
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d("TAG", "onResponse: $e")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RepairDetailAfterRepairComplainResponse>>, t: Throwable) {
                _isLoadingGetRepairDetailAfterRepairComplain.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                _messageGetRepairDetailAfterRepairComplain.value =
                    "Gagal terhubung ke server, periksa koneksi Anda dan coba lagi nanti."
            }
        })
    }

}
