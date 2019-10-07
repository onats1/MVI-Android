package com.example.mviarchitecture.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecture.R
import com.example.mviarchitecture.di.viewModels.ViewModelProviderFactory
import com.example.mviarchitecture.ui.DataStateListener
import com.example.mviarchitecture.utils.DataState
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), DataStateListener {

    override fun onDataStateChanged(dataState: DataState<*>?) {
        handleDataStateChanged(dataState)
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment()

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun showFragment(){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
            .commit()
    }

    private fun handleDataStateChanged(dataState: DataState<*>?){
        dataState?.let {

            showProgressBar(it.loading)

            it.message?.let {event ->
                event.getContentIfNotHandled()?.let {message ->
                    showToast(message)
                }

            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(state: Boolean){
        if(state){
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }
}
