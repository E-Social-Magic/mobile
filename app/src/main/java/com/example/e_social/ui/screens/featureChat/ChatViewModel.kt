package com.example.e_social.ui.screens.featureChat

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_social.MessagesActivity
import com.example.e_social.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.QueryUsersRequest
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor( val client: ChatClient) :ViewModel(){
    var searchBarState = mutableStateOf(false)
    var searchValue = mutableStateOf("")
    var userList = mutableStateOf<List<User>>(listOf())

    init{
        queryAllUsers()
    }

    fun createChannel(){
        viewModelScope.launch {
            client.channel(
                channelType = "messaging",
                channelId = UUID.randomUUID().toString()
            ).create(
                mapOf(
                    "name" to "trimmedChannelName",
                )
            )
        }
    }
    fun onSearchBarStateChange(newValue:Boolean){
        searchBarState.value=newValue
    }

    fun onSearchChange(newValue: String){
        searchValue.value = newValue

        if (newValue.isEmpty()) {
            queryAllUsers()
        }
        else{
            searchUser(newValue)
        }

    }


     fun searchUser(query: String) {
        val filters = Filters.and(
            Filters.autocomplete("id", query),
            Filters.ne("id", client.getCurrentUser()!!.id)
        )
        val request = QueryUsersRequest(
            filter = filters,
            offset = 0,
            limit = 100
        )
        client.queryUsers(request).enqueue { result ->
            if (result.isSuccess) {
                val users: List<User> = result.data()
                setData(users)
            } else {
                Log.e("UsersFragment", result.error().message.toString())
            }
        }
    }
     fun createNewChannel(selectedUser: String,action:(String)->Unit):String {
         var cid=""
        client.createChannel(
            channelType = "messaging",
            members = listOf(client.getCurrentUser()!!.id, selectedUser)
        ).enqueue { result ->
            if (result.isSuccess) {
                    cid = result.data().cid
                action.invoke(cid)
            } else {
                Log.e("UsersAdapter", result.error().message.toString())
            }
        }
         return cid
    }
     fun queryAllUsers() {
         if (client.getCurrentUser()==null){
             return
         }
        val request = QueryUsersRequest(
            filter = Filters.ne("id", client.getCurrentUser()!!.id),
            offset = 0,
            limit = 100
        )
        client.queryUsers(request).enqueue { result ->
            if (result.isSuccess) {
                val users: List<User> = result.data()
                setData(users)
            } else {
                Log.e("Error", result.error().message.toString())
            }
        }
    }

    fun setData(newList: List<User>) {
        userList.value = newList
    }

    fun getItemCount(): Int {
        return userList.value.size
    }

}