package com.example.e_social.ui.screens.featureGroup

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.Constants
import com.example.e_social.models.data.repo.topic.TopicRepository
import com.example.e_social.models.data.repo.topic.impl.TopicRepositoryImpl
import com.example.e_social.models.domain.model.TopicIndexListEntry
import com.example.e_social.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class TopicListViewModel @Inject constructor(
    private val repository: TopicRepository
) : ViewModel() {
    private var curPage = 0
    var topicList = mutableStateOf<List<TopicIndexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadTopicPaginated()
    }

    fun loadTopicPaginated() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            val result = repository.getTopicList(Constants.PAGE_SIZE, curPage * Constants.PAGE_SIZE)
            when(result) {
                is Resource.Success-> {
                    endReached.value = curPage * Constants.PAGE_SIZE >= result.data!!.count
                    val topicIndexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://gaplo.tech/content/images/2020/03/android-jetpack.jpg"
                        TopicIndexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    topicList.value += topicIndexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}