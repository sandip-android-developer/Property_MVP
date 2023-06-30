package com.example.radiusagentproject.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.radiusagentproject.R
import com.example.radiusagentproject.core.BaseActivity
import com.example.radiusagentproject.databinding.ItemOptionsBinding
import com.example.radiusagentproject.repository.remote.model.Exclusion
import com.example.radiusagentproject.repository.remote.model.Option
import com.example.radiusagentproject.utils.PrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class OptionAdapter(
    var mContext: BaseActivity,
    var optionList: List<Option>,
    var exclusions: List<List<Exclusion>>,
    var facilityId: String
) :
    RecyclerView.Adapter<OptionAdapter.VHOptions>() {
    private val facilityList: HashMap<String, String> = hashMapOf()
    private val optionsList: HashMap<String, String> = hashMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ) = VHOptions(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_options,
            parent,
            false
        )
    )

    override fun getItemCount(): Int {
        return optionList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(
        holder: VHOptions,
        position: Int
    ) {
        setSelectedFacility(mContext)
        setSelectedOption(mContext)

        if (optionsList.containsValue(optionList[position].id)) {
            holder.cbObjectSelectItem.background =
                ContextCompat.getDrawable(mContext, R.drawable.bg_rectangle_orange)
        } else {
            holder.cbObjectSelectItem.background =
                ContextCompat.getDrawable(mContext, R.drawable.bg_rectangle_white)
        }

        holder.itemView.setOnClickListener {
            setSelectedFacility(mContext)
            setSelectedOption(mContext)
            var isSelectable = true

            if (holder.cbObjectSelectItem.background.constantState
                !! == mContext.resources.getDrawable(R.drawable.bg_rectangle_white).constantState
            ) {

                exclusions.forEach {
                    if ((facilityList.values.contains(it[0].facilityId)
                                || facilityList.values.contains(it[1].facilityId))
                        && (optionsList.values.contains(it[0].optionsId)
                                || (optionsList.values.contains(it[1].optionsId)))
                    ) {
                        if ((facilityId == it[0].facilityId || facilityId == it[1].facilityId)
                            && (optionList[position].id == it[0].optionsId || optionList[position].id == it[1].optionsId)
                        ) {
                            Toast.makeText(
                                mContext,
                                mContext.getString(R.string.you_can_t_select_this_option),
                                Toast.LENGTH_SHORT
                            ).show()
                            isSelectable = false
                            return@setOnClickListener
                        }
                    }
                }
                if (isSelectable) {
                    holder.cbObjectSelectItem.background =
                        ContextCompat.getDrawable(mContext, R.drawable.bg_rectangle_orange)
                    facilityList[facilityId] = facilityId
                    optionsList[facilityId] = optionList[position].id
                }
            } else {
                holder.cbObjectSelectItem.background =
                    ContextCompat.getDrawable(mContext, R.drawable.bg_rectangle_white)
                facilityList.remove(facilityId)
                optionsList.remove(facilityId)
            }

            PrefUtils.setOptionId(mContext, Gson().toJson(optionsList))
            PrefUtils.setFacilityId(mContext, Gson().toJson(facilityList))
            notifyDataSetChanged()
        }

        holder.binding(optionList[position])

    }

    private fun setSelectedOption(mContext: BaseActivity) {
        val storedOption = PrefUtils.getOptionId(mContext)
        if (!storedOption.isNullOrEmpty()) {
            val typeOption = object : TypeToken<HashMap<String, String>>() {}.type
            optionsList.putAll(Gson().fromJson(storedOption, typeOption))
        }
    }

    private fun setSelectedFacility(mContext: BaseActivity) {
        val storedFacility = PrefUtils.getFacilityId(mContext)
        if (!storedFacility.isNullOrEmpty()) {
            val type = object : TypeToken<HashMap<String, String>>() {}.type
            facilityList.putAll(Gson().fromJson(storedFacility, type))
        }
    }

    inner class VHOptions(private val binding: ItemOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var cbObjectSelectItem = binding.cbObjectSelectItem
        fun binding(option: Option) {
            binding.option = option
            binding.executePendingBindings()
        }

    }
}