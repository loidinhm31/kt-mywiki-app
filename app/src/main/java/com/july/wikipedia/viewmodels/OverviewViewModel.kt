package com.july.wikipedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.july.wikipedia.enums.WikiApiStatus
import com.july.wikipedia.models.WikiResult
import com.july.wikipedia.services.WikiApi
import kotlinx.coroutines.launch


class OverviewViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<WikiApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<WikiApiStatus> = _status

    // Internally, use a MutableLiveData, then updating
    // with new values
    private val _wikiResult = MutableLiveData<WikiResult>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val wikiResult: LiveData<WikiResult> = _wikiResult

    fun getRandomItems(page: Int) {
        viewModelScope.launch {
            _status.value = WikiApiStatus.LOADING
            try {
                _wikiResult.value = WikiApi.retrofitService.getRandomItems("query", "json", 2, "random",
                    0, "pageimages|info", page, "url", 200)
                _status.value = WikiApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WikiApiStatus.ERROR
                _wikiResult.value = WikiResult(null)
            }
        }
    }

    fun searchItems(term: String, skip: Int, take: Int) {
        viewModelScope.launch {
            _status.value = WikiApiStatus.LOADING
            try {
                _wikiResult.value = WikiApi.retrofitService.searchItems("query", 2, "json", "prefixsearch",
                    term, take, skip, "pageimages|info",
                    "thumbnail|url", 200, take, "description", "url")
                _status.value = WikiApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WikiApiStatus.ERROR
                _wikiResult.value = WikiResult(null)
            }
        }
    }
}