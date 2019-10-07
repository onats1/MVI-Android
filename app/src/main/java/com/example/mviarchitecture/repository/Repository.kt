package com.example.mviarchitecture.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.remoteDB.ApiService

import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.utils.*
import javax.inject.Inject

class Repository
@Inject
constructor(val apiService: ApiService){

    fun getBlogPost(): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(apiService.getBlogPosts()){

                object: NetworkBoundResource<List<BlogPost>, MainViewState>(){
                    override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                        result.value = DataState.data(
                            data = MainViewState(
                                blogPosts = response.body
                            )
                        )
                    }

                    override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                        return apiService.getBlogPosts()
                    }

                }.asLiveData()
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(apiService.getUser(userId)){

                object: NetworkBoundResource<User, MainViewState>(){
                    override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                        result.value = DataState.data(
                            data = MainViewState(
                                user = response.body
                            )
                        )
                    }

                    override fun createCall(): LiveData<GenericApiResponse<User>> {
                        return apiService.getUser("1")
                    }

                }.asLiveData()
            }
    }
}