package com.example.mviarchitecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.repository.Repository
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import com.example.mviarchitecture.ui.main.state.MainStateEvent.*
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.utils.AbsentLiveData
import com.example.mviarchitecture.utils.DataState
import javax.inject.Inject

class MainViewModel
    @Inject
    constructor(val repository: Repository): ViewModel(){

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState:  MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>  get() = _viewState
    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){
            handleStateEvent(it)
        }



    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>>{

        return when(stateEvent){
            
            is GetUserEvent -> {
              repository.getUser(stateEvent.userId)
            }

            is GetBlogPostEvent -> {
               repository.getBlogPost()
            }

            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPost: List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPost
        _viewState.value = update
    }

    fun setBlogUser(blogUser: User){
        val update = getCurrentViewStateOrNew()
        update.user = blogUser
        _viewState.value = update
    }


    private fun getCurrentViewStateOrNew(): MainViewState{
        return viewState.value?.let {
            it
        }?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }

}