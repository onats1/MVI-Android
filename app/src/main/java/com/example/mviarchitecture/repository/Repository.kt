package com.example.mviarchitecture.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User

import com.example.mviarchitecture.remoteDB.RetrofitInstance
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.utils.*

object Repository{

    fun getBlogPost(): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(RetrofitInstance.retrofitService.getBlogPosts()){

                object: NetworkBoundResource<List<BlogPost>, MainViewState>(){
                    override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                        result.value = DataState.data(
                            data = MainViewState(
                                blogPosts = response.body
                            )
                        )
                    }

                    override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                        return RetrofitInstance.retrofitService.getBlogPosts()
                    }

                }.asLiveData()
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>>{
        return Transformations
            .switchMap(RetrofitInstance.retrofitService.getUser(userId)){

                object: NetworkBoundResource<User, MainViewState>(){
                    override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                        result.value = DataState.data(
                            data = MainViewState(
                                user = response.body
                            )
                        )
                    }

                    override fun createCall(): LiveData<GenericApiResponse<User>> {
                        return RetrofitInstance.retrofitService.getUser("1")
                    }

                }.asLiveData()
            }
    }
}