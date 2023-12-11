package com.yogi.elluminatiassignment.data

import java.io.Serializable

data class Item (
    val _id:String,
    val name:List<String>,
    val price:String,
    val item_taxes:List<Int>,
    val specifications:List<Specification>
): Serializable