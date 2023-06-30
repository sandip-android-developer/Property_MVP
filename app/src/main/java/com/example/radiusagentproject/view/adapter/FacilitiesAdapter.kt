package com.example.radiusagentproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.radiusagentproject.R
import com.example.radiusagentproject.core.BaseActivity
import com.example.radiusagentproject.databinding.ItemFacilitiesBinding
import com.example.radiusagentproject.repository.remote.model.Exclusion
import com.example.radiusagentproject.repository.remote.model.Facility

class FacilitiesAdapter(
    private val mContext: BaseActivity,
    private val facilitiesList: MutableList<Facility>,
    private val exclusions: List<List<Exclusion>>
) :
    RecyclerView.Adapter<FacilitiesAdapter.VHFacilities>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHFacilities(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_facilities,
            parent,
            false
        ), mContext, exclusions
    )

    override fun getItemCount(): Int {
        return facilitiesList.size
    }

    override fun onBindViewHolder(holder: VHFacilities, position: Int) {
        holder.binding(facilitiesList[position])
    }

    class VHFacilities(
        private val binding: ItemFacilitiesBinding,
        private val mContext: BaseActivity,
        private val exclusions: List<List<Exclusion>>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(facilities: Facility) {
            binding.facility = facilities
            binding.executePendingBindings()

            binding.rvOptions.layoutManager = LinearLayoutManager(mContext)
            binding.rvOptions.setHasFixedSize(true)
            binding.rvOptions.adapter =
                OptionAdapter(mContext, facilities.options, exclusions, facilities.facilityId)
        }
    }
}