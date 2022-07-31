package com.zongmin.cook.management


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zongmin.cook.data.Management
import com.zongmin.cook.databinding.ItemManagementBinding

class ManagementAdapter(val viewModel: ManagementViewModel) : ListAdapter<Management, RecyclerView.ViewHolder>(DiffCallback) {

    class ManagementViewHolder(private var binding: ItemManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(management: Management, viewModel: ManagementViewModel) {
            binding.textManagementName.text = management.name
            binding.textManagementBelongName.text = management.belong
            binding.textManagementIngredientQuantity.text = management.quantity
            binding.textManagementIngredientUnit.text = management.unit
            binding.checkboxManagement.isChecked = management.prepare
            binding.checkboxManagement.setOnClickListener{
                if(binding.checkboxManagement.isChecked){
                    viewModel.minusQuantity()
                    viewModel.setPrepareResult(binding.checkboxManagement.isChecked,management.id)
                }else{
                    viewModel.addQuantity()
                    viewModel.setPrepareResult(binding.checkboxManagement.isChecked,management.id)
                }
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ManagementViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemManagementBinding.inflate(layoutInflater, parent, false)

                return ManagementViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ManagementViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ManagementViewHolder -> {
                val selectedItem = getItem(position) as Management
                holder.bind(selectedItem, viewModel)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Management>() {
        override fun areItemsTheSame(oldItem: Management, newItem: Management): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Management, newItem: Management): Boolean {
            return oldItem == newItem
        }
    }


}