package com.yogi.elluminatiassignment.pricing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.yogi.elluminatiassignment.data.Item
import com.yogi.elluminatiassignment.data.OptionsItem
import com.yogi.elluminatiassignment.data.Specification
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PricingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val data: Item = savedStateHandle["item"]!!

    init {
        data.specifications.forEach {
            it.list.forEach { optionsItem->
                optionsItem.isSelected = optionsItem.is_default_selected
            }
        }
    }

    private val _specificationsList=MutableLiveData(data.specifications)
    private val _filteredList = MutableLiveData(data.specifications)
    val specificationsList:LiveData<List<Specification>> get() = _filteredList

    private val _totalPrice=MutableLiveData<Float>(0f)
    val totalPrice:LiveData<Float> get() = _totalPrice

    init {
        refreshFiltered()
        refreshTotal()
    }

    fun onAnyOptionSelectedChange(spec:Specification,opt:OptionsItem,isSelected:Boolean){
        when (spec.isParentAssociate) {
            true -> {
                _specificationsList.value!!.first{
                        specification -> specification._id == spec._id
                }.list.also {
                    var isOldNewSame=false
                    it.forEach { op ->
                        if (op._id == opt._id && op.isSelected) {
                            isOldNewSame = true
                        }
                        op.isSelected = op._id == opt._id
                        op.quantity = if (op.isSelected) 1 else 0
                    }
                    if (!isOldNewSame){ //reset all other selections
                        _specificationsList.value = _specificationsList.value!!.onEach {
                            if(it.isAssociated==true){
                                it.list.forEach {op->
                                    op.isSelected = op.is_default_selected
                                    op.quantity = 0
                                }
                            }
                        }
                    }
                }
                refreshFiltered()
            }
            else -> {
                _filteredList.value = _filteredList.value!!.apply {
                    first{
                        it._id == spec._id
                    }.list.firstOrNull {
                        it._id == opt._id
                    }?.also { op->
                        op.isSelected = isSelected
                        if (!isSelected){
                            op.quantity = 0
                        }else if (op.quantity ==0){
                            op.quantity = 1
                        }
                    }
                }
            }
        }
        refreshTotal()
    }

    fun changeCountOfSelected(spec:Specification,opt:OptionsItem,increase:Boolean){
        _filteredList.value = _filteredList.value!!.apply {
            first{
                it._id == spec._id
            }.list.firstOrNull {
                it._id == opt._id
            }?.also { op->

                if (increase){
                    op.quantity = op.quantity + 1
                }else{
                    op.quantity = op.quantity - 1
                    if (op.quantity ==0){
                        op.isSelected = false
                    }
                }
            }
        }
        refreshTotal()
    }

    private fun refreshFiltered(){
        val mainSel = _specificationsList.value!!.sortedBy { it.sequence_number }.first().list.firstOrNull { it.isSelected }
        _filteredList.value = _specificationsList.value!!.filter {
            it._id == mainSel?.specification_group_id || it.modifierId==mainSel?._id
        }
    }

    private fun refreshTotal(){
        var tPrice=0f
        _specificationsList.value!!.forEach {spec->
            spec.list.forEach {
                if (it.isSelected){
                    tPrice += (it.price * (if(it.quantity==0) 1 else it.quantity))
                }
            }
        }
        _totalPrice.value = tPrice
    }
}