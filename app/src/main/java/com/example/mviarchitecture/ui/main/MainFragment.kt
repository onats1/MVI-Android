package com.example.mviarchitecture.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mviarchitecture.R
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.ui.DataStateListener
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException
import java.lang.Exception


class MainFragment : Fragment(){

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    lateinit var recyclerAdapter: MainRecyclerAdapter

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
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener.")
        }
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerAdapter = MainRecyclerAdapter()
            adapter = recyclerAdapter
        }
    }

    private fun setUserDetails(user: User){
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

    private fun subscribeObservers(){

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug Main fragment: $dataState")

            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let {

                    it.blogPosts?.let {
                        viewModel.setBlogListData(it)
                    }

                    it.user?.let {
                        viewModel.setBlogUser(it)
                    }
                }
            }

           dataStateListener.onDataStateChanged(dataState)

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { dataState ->

            dataState.blogPosts?.let {
                recyclerAdapter.submitList(it)
            }

            dataState.user?.let {
                println(it.email)
                setUserDetails(
                    user = it
                )
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