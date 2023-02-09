package com.july.wikipedia.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.july.wikipedia.database.repositories.ItemRepository
import com.july.wikipedia.database.rooms.getItemDatabase
import com.july.wikipedia.enums.WikiApiStatus
import com.july.wikipedia.models.WikiPageDto
import com.july.wikipedia.services.WikiApi
import kotlinx.coroutines.*
import java.io.IOException


class ItemViewModel(application: Application) : ViewModel() {
    // The data source this ViewModel will fetch results from
    private val itemRepository = ItemRepository(getItemDatabase(application))

    // Cache all items from the database using LiveData
    val itemList: LiveData<List<WikiPageDto>> = itemRepository.items

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<WikiApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<WikiApiStatus> = _status

    // Internally, use a MutableLiveData, then updating
    // with new values
    private val _wikiPageDto = MutableLiveData<List<WikiPageDto>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val wikiPageDto: LiveData<List<WikiPageDto>> = _wikiPageDto

    fun searchItems(term: String, skip: Int, take: Int) {
        viewModelScope.launch {
            _status.value = WikiApiStatus.LOADING
            try {
                _wikiPageDto.value = WikiApi.retrofitService.searchItems("query", 2, "json", "prefixsearch",
                    term, take, skip, "pageimages|info",
                    "thumbnail|url", 200, take, "description", "url")
                    .query!!.pages!!
                _status.value = WikiApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WikiApiStatus.ERROR
                _wikiPageDto.value = listOf()
            }
        }
    }

    /**
     * Refresh data from the API, then save data into repository. Use a coroutine launch to run in a
     * background thread.
     */
    fun refreshAndSaveData(page: Int) {
        viewModelScope.launch {
            _status.value = WikiApiStatus.LOADING
            try {
                _wikiPageDto.value = itemRepository.saveRefreshItems(page)
            } catch (ex: Exception) {
                _status.value = WikiApiStatus.ERROR
                _wikiPageDto.value = listOf()
            } finally {
                _status.value = WikiApiStatus.DONE
            }
        }
    }

    /**
     * Factory for constructing OverviewViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}