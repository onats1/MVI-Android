package com.example.mviarchitecture.ui

import com.example.mviarchitecture.utils.DataState

interface DataStateListener {

    fun onDataStateChanged(dataState: DataState<*>?)
}