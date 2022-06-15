package id.indocyber.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import id.indocyber.common.BR

abstract class BaseFragment<VM : BaseViewModel, Binding : ViewDataBinding> : Fragment() {

    abstract val vm: VM
    lateinit var binding: Binding
    abstract val layoutResourceId: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.setVariable(BR.vm, vm)
        binding.lifecycleOwner = this
        initBinding(binding)
        return binding.root
    }


    open fun initBinding(binding: Binding) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.navigationtEvent.observe(this) {
            findNavController().navigate(it)
        }
        vm.popBackStackEvent.observe(this) {
            findNavController().popBackStack()
        }
    }

    fun <T> observeResponseData(
        data: MutableLiveData<AppResponse<T>>,
        success: ((T) -> Unit),
        error: ((Throwable) -> Unit)?,
        loading: (() -> Unit)? = {}
    ) {
        data.observe(this) { response ->
            when (response) {
                is AppResponse.AppResponseSuccess -> {
                    success.invoke(response.data)
                }
                is AppResponse.AppResponseError -> {
                    response.e?.let {
                        error?.invoke(it)
                    }
                }
                is AppResponse.AppResponseLoading -> {
                    loading?.invoke()
                }
            }
        }

    }

}