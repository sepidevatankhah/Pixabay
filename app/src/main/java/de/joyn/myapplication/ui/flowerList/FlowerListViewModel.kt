package de.joyn.myapplication.ui.flowerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.joyn.myapplication.network.dto.Models
import de.joyn.myapplication.ui.base.BaseViewModel
import de.joyn.myapplication.ui.base.BaseViewState
import javax.inject.Inject

class FlowerListViewModel @Inject constructor() :
    BaseViewModel<FlowerListViewState>() {


    // Internally, we use a MutableLiveData, because we will be updating the List of Flowers
    // with new values
    private val _properties = MutableLiveData<List<Models.FlowerResponse>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Models.FlowerResponse>>
        get() = _properties


}