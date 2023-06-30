package com.example.radiusagentproject.core

import com.example.radiusagentproject.repository.remote.model.Facilities

interface MainActvityContract {

    interface View{
        fun onLoading()

        fun onSuccess(facilities: Facilities)

        fun onError(message:String)

    }

    interface Presenter{

        fun getPropertyDetails()

        fun onDestroy()

    }

    interface Model{
         interface OnFinishListener{
           fun onLoading()
           fun onError(message: String)
           fun onSuccess(facilities: Facilities)
         }

        fun fetchProperty(onFinishListener: OnFinishListener)
    }
}