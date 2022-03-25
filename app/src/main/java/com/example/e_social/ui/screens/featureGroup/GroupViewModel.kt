package com.example.e_social.ui.screens.featureGroup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_social.models.data.repo.group.GroupRepository
import com.example.e_social.models.data.response.Topic
import com.example.e_social.models.data.response.TopicList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {
    var allGroups = mutableStateOf<TopicList?>(null)
    val selectedGroup = mutableStateOf <Topic?>(null)

    init {
      queryAllGroup()
    }

    fun searchGroups(searchedText: String) {
        viewModelScope.launch(Dispatchers.Default) {

        }
    }

    fun onSelectedGroup(selected: Topic) {
        selectedGroup.value = selected
    }
    fun onSelectedGroupId(selectedGroupId: String) {
        val selected = allGroups.value?.groups?.find {selectedGroupId==it.id }
        selectedGroup.value = selected
    }
    fun queryAllGroup(){
        viewModelScope.launch(Dispatchers.IO) {
           val response = groupRepository.getGroups()
            if (response.data!=null){
                allGroups.value=response.data
            }
        }
    }

}