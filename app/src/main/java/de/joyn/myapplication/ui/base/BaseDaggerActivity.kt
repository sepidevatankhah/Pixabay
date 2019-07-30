package de.joyn.myapplication.ui.base

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import de.joyn.myapplication.di.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseDaggerActivity<S : BaseViewState, VM : BaseViewModel<S>> : DaggerAppCompatActivity() {

    @Inject
    @JvmField
    var viewModelFactory: ViewModelFactory? = null

    protected lateinit var viewModel: VM

    override fun onStart() {
        super.onStart()
        startObserving()
    }

    fun createViewModel(clazz: Class<VM>) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(clazz)
    }


    private fun startObserving() {
        viewModel.stateLD.observe(this, Observer { state -> handleState(state) })
    }

    abstract fun handleState(state: S)

    fun showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}