package com.yogi.elluminatiassignment.pricing

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yogi.elluminatiassignment.R
import com.yogi.elluminatiassignment.data.OptionsItem
import com.yogi.elluminatiassignment.data.Specification
import com.yogi.elluminatiassignment.databinding.OptionItemRv1Binding
import com.yogi.elluminatiassignment.databinding.OptionItemRv2Binding
import com.yogi.elluminatiassignment.databinding.SpecificationItemRvBinding

class SpecificationsAdapter(
    private val onItemSelected: (spec: Specification, opt: OptionsItem, isSelected: Boolean) -> Unit,
    private val onClickCount: (spec: Specification, opt: OptionsItem, increase: Boolean) -> Unit
) : RecyclerView.Adapter<SpecificationsAdapter.ViewHolder>() {

    private val data = mutableListOf<Specification>()
    fun updateData(newData: List<Specification>) {
        data.clear()
        data += newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SpecificationItemRvBinding.bind(itemView)
        val optAdapter1 = OptionsAdapter1(
            onItemSelected = { opt ->
                onItemSelected(data[bindingAdapterPosition], opt, true)
            }
        )
        val optAdapter2 = OptionsAdapter2(
            onItemSelected = { opt, isSelected ->
                onItemSelected(data[bindingAdapterPosition], opt, isSelected)
            },
            onClickCount = { opt, inc ->
                onClickCount(data[bindingAdapterPosition], opt, inc)
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.specification_item_rv, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spec = data[position]
        holder.binding.apply {
            tvName.text = spec.name.first()
            tvChoose.text = if (spec.type == 1) "Choose 1" else "Choose up to ${spec.max_range}"
            rv.adapter = if (spec.type == 1) holder.optAdapter1 else holder.optAdapter2
            holder.optAdapter1.setData(spec.list)
            holder.optAdapter2.setData(spec.list,spec.user_can_add_specification_quantity==true)
        }
    }

    class OptionsAdapter1(
        private val onItemSelected: (opt: OptionsItem) -> Unit
    ) : RecyclerView.Adapter<OptionsAdapter1.ViewHolder>() {

        private val data = mutableListOf<OptionsItem>()
        fun setData(newData: List<OptionsItem>) {
            data.clear()
            data += newData
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = OptionItemRv1Binding.bind(itemView)

            init {
                binding.rb.setOnClickListener {
                    onItemSelected(data[bindingAdapterPosition])
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(
                R.layout.option_item_rv_1,
                parent,
                false
            )
            return ViewHolder(v)
        }

        override fun getItemCount(): Int = data.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val opt = data[position]
            holder.binding.apply {
                rb.text = opt.name.first()
                rb.isChecked = opt.isSelected
                tvPrice.text = "₹ ${opt.price}.00"
            }
        }

    }

    class OptionsAdapter2(
        private val onItemSelected: (opt: OptionsItem, isSelected: Boolean) -> Unit,
        private val onClickCount: (opt: OptionsItem, increase: Boolean) -> Unit
    ) : RecyclerView.Adapter<OptionsAdapter2.ViewHolder>() {

        private val data = mutableListOf<OptionsItem>()
        private var user_can_add_specification_quantity:Boolean=false
        fun setData(newData: List<OptionsItem>,user_can_add_specification_quantity:Boolean) {
            this.user_can_add_specification_quantity = user_can_add_specification_quantity
            data.clear()
            data += newData
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = OptionItemRv2Binding.bind(itemView)

            init {
                binding.apply {
                    cb.setOnClickListener {
                        onItemSelected(data[bindingAdapterPosition], !data[bindingAdapterPosition].isSelected)
                    }
                    btPlus.setOnClickListener {
                        onClickCount(data[bindingAdapterPosition], true)
                        //notifyItemChanged(bindingAdapterPosition)
                    }
                    btMinus.setOnClickListener {
                        onClickCount(data[bindingAdapterPosition], false)
                        //notifyItemChanged(bindingAdapterPosition)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(
                R.layout.option_item_rv_2,
                parent,
                false
            )
            return ViewHolder(v)
        }

        override fun getItemCount(): Int = data.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val opt = data[position]
            holder.binding.apply {
                cb.text = opt.name.first()
                cb.isChecked = opt.isSelected
                Log.e("isVisible","user_can_add_specification_quantity:$user_can_add_specification_quantity && opt.isSelected:${opt.isSelected}")
                layoutCounts.isVisible = user_can_add_specification_quantity && opt.isSelected
                tvCount.text = "${opt.quantity}"
                tvPrice.text = if (opt.price==0) "" else "₹ ${opt.price}.00"
            }
        }
    }
}