package com.example.mviarchitecture.ui.main.state

import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User

data class MainViewState(

    var blogPosts: List<BlogPost>? = null,
    var user: User? = null

)