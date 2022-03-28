package com.example.e_social.ui.screens.featureGroup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.group.GroupRepository
import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import com.example.e_social.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val sessionManager: SessionManager
) :
    ViewModel() {
    var allGroups = mutableStateOf<TopicList?>(null)
    val selectedGroup = mutableStateOf<Topic?>(null)
    val isLoading = mutableStateOf(false)
    init {
        queryAllGroup()
    }

    fun searchGroups(searchedText: String) {
        viewModelScope.launch(Dispatchers.Default) {

        }
    }

    fun onSelectedGroup(selected: Topic?) {
        selectedGroup.value = selected
    }

    fun onSelectedGroupId(selectedGroupId: String?) {
        if(selectedGroupId == null){
            selectedGroup.value = null
        }
        else{
            val selected = allGroups.value?.groups?.find { selectedGroupId == it.id }
            selectedGroup.value = selected
        }
    }

    fun queryAllGroup() {
        viewModelScope.launch {
            isLoading.value=true
            val userId = sessionManager.fetchUserId()
            if (userId != null) {
                val response = groupRepository.getGroups(userId = userId)
                if (response.data != null) {
                    allGroups.value = response.data
                }
            }
            isLoading.value=false
        }
    }

}