package com.example.mydemo.main.core.api.viewmodel

import androidx.lifecycle.*
import com.example.mydemo.base.BaseViewModel
import com.example.mydemo.dto.Resource
import com.example.mydemo.model.VectorResponse
import com.example.mydemo.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class VectorViewModel @Inject constructor(private val repository: InterviewRepository) : BaseViewModel() {
  companion object {
    private const val TYPE_DIVIDER = "divider"
    private const val TYPE_NEWS = "news"
  }

  private val _onGetVector: MutableLiveData<Resource<List<VectorResponse.Item>>> by lazy { MutableLiveData<Resource<List<VectorResponse.Item>>>() }
  val onGetVector: LiveData<Resource<List<VectorResponse.Item>>> = _onGetVector

  fun getVector() {
    repository.getVector().subscribe({
      it?.getVector?.items?.filter { it.type == TYPE_DIVIDER || it.type == TYPE_NEWS }
        ?.let { list ->
          val newList = mutableListOf<VectorResponse.Item>()
          list.forEachIndexed { index, item ->
            if (item.type == TYPE_NEWS) {
              newList.add(item)
            } else if (item.type == TYPE_DIVIDER) {
              list.getOrNull(index + 1)?.let {
                if (it.type == TYPE_NEWS) {
                  newList.add(item)
                }
              }
            }
          }
          _onGetVector.postValue(Resource.success(newList))
        } ?: run {
        _onGetVector.postValue(Resource.error("api is broken", null))
      }
    }, {
      _onGetVector.postValue(Resource.error(it.message, null))
    }).addTo(compositeDisposable)
  }
}