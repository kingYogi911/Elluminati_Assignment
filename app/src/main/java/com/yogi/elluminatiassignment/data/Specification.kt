package com.yogi.elluminatiassignment.data

import java.io.Serializable

data class Specification(
    val _id: String,
    val name: List<String>,
    val sequence_number: Int,
    val unique_id: Int,
    val type: Int,
    val range: Int,
    val max_range: Int,
    val is_required: Boolean,
    val modifierId: String?,
    val modifierGroupId: String?,
    val isAssociated: Boolean?,
    val isParentAssociate: Boolean?,
    val user_can_add_specification_quantity: Boolean?,
    val list: List<OptionsItem>
): Serializable

data class OptionsItem(
    val _id: String,
    val name: List<String>,
    val price: Int,
    val sequence_number: Int,
    val is_default_selected: Boolean,
    val specification_group_id: String,
    val unique_id: Int,
    var isSelected: Boolean = is_default_selected,
    var quantity: Int = 0
): Serializable