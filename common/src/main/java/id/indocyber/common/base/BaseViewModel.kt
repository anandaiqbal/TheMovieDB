package id.indocyber.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import id.indocyber.common.ext.SingleLiveEvent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val navigationtEvent = SingleLiveEvent<NavDirections>()
    val popBackStackEvent = SingleLiveEvent<Any>()
    fun navigate(nav: NavDirections) {
        navigationtEvent.postValue(nav)
    }

    fun popBackStack() {
        popBackStackEvent.postValue(Any())
    }



}