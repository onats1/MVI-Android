package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.utils.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch {

            delay(NETWORK_DELAY_TIME)

            withContext(Main){

                val apiResponse = createCall()

                result.addSource(apiResponse){response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)

                }

            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>?) {
        when(response){

            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }

            is ApiErrorResponse -> {
                onReturnError(response.errorMessage)
            }

            is ApiEmptyResponse -> {

            }
        }
    }

    fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}