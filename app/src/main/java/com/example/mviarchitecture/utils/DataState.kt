package com.example.mviarchitecture.utils

data class DataState<T>(
    var message: Event<String>? =null,
    var loading: Boolean = false,
    var data: Event<T>? = null
){
    companion object{

        fun<T> error(
            message: String
        ): DataState<T>{
            return DataState(
                message = Event(message),
                loading = false,
                data = null
            )
        }

        fun<T> loading(
            isLoading: Boolean
        ): DataState<T> {
            return DataState(
                message = null,
                loading = isLoading,
                data = null
            )
        }

        fun<T> data(
            data: T? = null,
            message: String? = null
        ): DataState<T>{
            return DataState(
                message = Event.messageEvent(message),
                data = Event.dataEvent(data),
                loading = false
            )
        }
    }
}