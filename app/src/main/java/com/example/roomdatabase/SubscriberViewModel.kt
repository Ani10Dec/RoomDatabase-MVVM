package com.example.roomdatabase

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.database.SubscriberEntity
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    private var isUpdateOrDelete = false
    lateinit var updateOrDeleteSubscriberEntity: SubscriberEntity
    val allSubscriber = repository.allSubscriber
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateBtnText = MutableLiveData<String>()
    val deleteOrClearAllBtnText = MutableLiveData<String>()
    val cancelBtnText = MutableLiveData<String>()
    var visibility = MutableLiveData<Int>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        setInitialsInputs()
    }

    fun saveOrUpdate() {
        if (checkForValidation()) {
            val name = inputName.value!!
            val email = inputEmail.value!!
            if (isUpdateOrDelete) {
                updateOrDeleteSubscriberEntity.name = name
                updateOrDeleteSubscriberEntity.email = email
                updateSubscriber(updateOrDeleteSubscriberEntity)
            } else {
                insertSubscriber(SubscriberEntity(0, name, email))
            }
        }
    }

    fun deleteOrClearAll() {
        if (isUpdateOrDelete) {
            deleteSubscriber(updateOrDeleteSubscriberEntity)
        } else {
            clearAll()
        }
    }

    private fun clearInputs() {
        inputName.value = null
        inputEmail.value = null
    }

    private fun setInitialsInputs() {
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
        cancelBtnText.value = "Cancel"
        visibility.value = 8
    }

    fun cancel() {
        setInitialsInputs()
        isUpdateOrDelete = false
        clearInputs()
    }

    private fun insertSubscriber(subscriberEntity: SubscriberEntity) {
        viewModelScope.launch {
            repository.insertSubscriber(subscriberEntity)
            statusMessage.value = Event("Inserted Successfully")
            cancel()
        }
    }

    private fun updateSubscriber(subscriberEntity: SubscriberEntity) {
        viewModelScope.launch {
            repository.updateSubscriber(subscriberEntity)
            statusMessage.value = Event("Updated Successfully")
            cancel()
        }
    }

    private fun deleteSubscriber(subscriberEntity: SubscriberEntity) {
        viewModelScope.launch {
            repository.deleteSubscriber(subscriberEntity)
            statusMessage.value = Event("Deleted Successfully")
            cancel()
        }
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAllSubscriber()
        statusMessage.value = Event("All Cleared Successfully")
        cancel()
    }

    fun initUpdateAndDelete(subscriberEntity: SubscriberEntity) {
        visibility.value = 0
        inputName.value = subscriberEntity.name
        inputEmail.value = subscriberEntity.email
        saveOrUpdateBtnText.value = "Update"
        deleteOrClearAllBtnText.value = "Delete"
        isUpdateOrDelete = true
        updateOrDeleteSubscriberEntity = subscriberEntity
    }

    private fun checkForValidation(): Boolean {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter name")
            return false
        }
        if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter email id")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter correct email id")
            return false
        }
        return true
    }
}
