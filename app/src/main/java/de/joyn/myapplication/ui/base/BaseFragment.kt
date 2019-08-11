package de.joyn.myapplication.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import de.joyn.myapplication.di.viewmodel.ViewModelFactory
import de.joyn.myapplication.ui.MainActivity
import de.joyn.myapplication.ui.fragments.photoDetail.PhotoDetailViewModel
import javax.inject.Inject


abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        //injectDependencies(this)
        super.onAttach(context)
    }

   // abstract fun injectDependencies(fragment: Fragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCreateCompleted();
    }

    @LayoutRes
    abstract fun getLayout(): Int


    /**
     * add your code here evry thing injected and view
     */
    protected abstract fun onCreateCompleted()


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    protected fun createViewModel(clazz: Class<VM>) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(clazz)
    }

    fun showToast(@StringRes string: Int) {
        if (string != 0)
            Toast.makeText(activity, getString(string), Toast.LENGTH_LONG).show()
    }

    fun showMessage(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}
