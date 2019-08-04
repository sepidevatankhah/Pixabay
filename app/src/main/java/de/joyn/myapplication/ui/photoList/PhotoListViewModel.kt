package de.joyn.myapplication.ui.photoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.joyn.myapplication.domain.interactor.GetPhotoUseCase
import de.joyn.myapplication.ui.base.BaseViewModel
import io.reactivex.processors.PublishProcessor
import org.reactivestreams.Publisher
import timber.log.Timber
import javax.inject.Inject
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.joyn.myapplication.data.repository.NetworkState
import de.joyn.myapplication.data.repository.PhotoDataSourceFactory
import de.joyn.myapplication.data.repository.PhotoPageKeyedDataSource
import de.joyn.myapplication.domain.entity.PhotoModel
import de.joyn.myapplication.network.RestApi
import de.joyn.myapplication.network.RestApiService
import de.joyn.myapplication.network.dto.Models
import io.reactivex.disposables.CompositeDisposable

/*
The view model will responsible for creating the PagedList and give to the activity
so it’s can observe the data changes and pass it to the adapter.
*/

class PhotoListViewModel  : ViewModel() {

    var photoList: LiveData<PagedList<Models.PhotoResponse>>
    private val pageSize = 15
    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory: PhotoDataSourceFactory

    init {
        Timber.d("PhotoListViewModelInit")
        sourceFactory = PhotoDataSourceFactory(compositeDisposable, RestApiService.getService())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        photoList = LivePagedListBuilder<Long, Models.PhotoResponse>(sourceFactory, config).build()

    }


    fun retry() {
        sourceFactory.photosDataSourceLiveData.value!!.retry()
    }


    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<PhotoPageKeyedDataSource, NetworkState>(
        sourceFactory.photosDataSourceLiveData, { it.networkState })

    fun getRefreshState(): LiveData<NetworkState> =
        Transformations.switchMap<PhotoPageKeyedDataSource, NetworkState>(
        sourceFactory.photosDataSourceLiveData
    ) { it.initialLoad }


    fun refresh() {
        sourceFactory.photosDataSourceLiveData.value!!.invalidate()
    }
}