package com.example.mviarchitecture.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecture.R
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import java.lang.Exception


class MainFragment : Fragment(){

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity.")

        subscribeObservers()
    }

    private fun subscribeObservers(){

        viewModel.dataState.observe(viewLifecycleOwner, Observer {dataState ->

            println("DEBUG: Data state ${dataState}")
            dataState.blogPosts?.let {blogPosts ->
                viewModel.setBlogListData(blogPosts)

            }

            dataState.user?.let {blogUser ->
                viewModel.setBlogUser(blogUser)

            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { dataState ->

            dataState.blogPosts?.let {

            }

            dataState.user?.let {
                println(it.email)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.get_blogs -> getBlogEvent()
            R.id.get_user -> getUserEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun getBlogEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostEvent())
    }
}