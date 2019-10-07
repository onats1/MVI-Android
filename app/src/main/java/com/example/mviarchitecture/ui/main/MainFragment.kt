package com.example.mviarchitecture.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mviarchitecture.R
import com.example.mviarchitecture.di.viewModels.ViewModelProviderFactory
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.ui.DataStateListener
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException
import java.lang.Exception
import javax.inject.Inject


class MainFragment : DaggerFragment(), MainRecyclerAdapter.Interaction{

    override fun onItemSelected(position: Int, item: BlogPost) {
        Toast.makeText(activity, "$position" + "${item.body}", Toast.LENGTH_SHORT).show()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel

    private lateinit var dataStateListener: DataStateListener

    private lateinit var recyclerAdapter: MainRecyclerAdapter


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
            ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity.")

        subscribeObservers()
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
//        AndroidSupportInjection.inject(this)
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

    /**
     * This method sets the user details to the fragment ui
     * */
    private fun setUserDetails(user: User){
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

    /**
     * This method subscribes and listens to the livedata objects present in the viewmodel.
     * With MVI architecture, we make use of two major livedata objects. One wraps the viewState
     * which is like the single source of truth that the ui makes use of while the other is the one that
     * gets the state of the data being stored in the viewstate. ie. The data state gets the set information
     * and then returns it to the viewmodel which then sets it to the viewstate.
     * */
    private fun subscribeObservers(){

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug Main fragment: $dataState")

            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let { data ->

                    data.blogPosts?.let {
                        viewModel.setBlogListData(it)
                    }

                    data.user?.let {
                        viewModel.setBlogUser(it)
                    }
                }
            }

           dataStateListener.onDataStateChanged(dataState)

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.blogPosts?.let {
                recyclerAdapter.submitList(it)
            }

            viewState.user?.let {
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
        viewModel.setStateEvent(MainStateEvent.GetBlogPostEvent)
    }
}