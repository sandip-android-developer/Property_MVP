package com.example.radiusagentproject

import android.widget.TextView
import androidx.databinding.BindingAdapter


open class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("android:customDrawableIcon")
        fun customDrawableIcon(view: TextView, type: String?) {
            when (type) {
                "apartment" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_apartment, 0, 0, 0)
                }

                "condo" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_condo, 0, 0, 0)
                }

                "boat" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_boat, 0, 0, 0)
                }

                "land" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_land, 0, 0, 0)
                }

                "rooms" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rooms, 0, 0, 0)
                }

                "no-room" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_no_rooms, 0, 0, 0)
                }

                "swimming" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_swimming, 0, 0, 0)
                }

                "garden" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_garden, 0, 0, 0)
                }

                "garage" -> {
                    view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_garage, 0, 0, 0)
                }
            }
        }
    }
}
