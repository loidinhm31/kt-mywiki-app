package com.july.wikipedia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.july.wikipedia.database.entities.asDomainModel
import com.july.wikipedia.database.repositories.ItemRepository
import com.july.wikipedia.database.rooms.getItemDatabase
import com.july.wikipedia.models.WikiPageDto
import kotlinx.coroutines.launch


class HistoryItemViewModel(application: Application) : ViewModel() {
    // The data source this ViewModel will fetch results from
    private val itemRepository = ItemRepository(getItemDatabase(application))

    // Internally, use a MutableLiveData, then updating with new values
    private val _item = MutableLiveData<List<WikiPageDto>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val item: MutableLiveData<List<WikiPageDto>> = _item

    fun getHistoryItems() {
        viewModelScope.launch {
            _item.value = itemRepository.findItemByHistory(true).asDomainModel()
        }
    }

    fun clearHistoryItems() {
        viewModelScope.launch {
            itemRepository.deleteHistory(true)
            _item.value = listOf()
        }
    }

    /**
     * Factory for constructing OverviewViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryItemViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}