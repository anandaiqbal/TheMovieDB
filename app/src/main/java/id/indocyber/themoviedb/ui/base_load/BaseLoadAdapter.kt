package id.indocyber.themoviedb.ui.base_load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.themoviedb.databinding.BaseLoadingPagingBinding

class BaseLoadAdapter(
    val retryAction: () -> Unit
) : LoadStateAdapter<BaseLoadViewHolder>() {

    override fun onBindViewHolder(
        holder: BaseLoadViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BaseLoadViewHolder =
        BaseLoadViewHolder(
            BaseLoadingPagingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ), retryAction
        ).apply {
            this.bind(loadState)
        }
}

class BaseLoadViewHolder(
    private val binding: BaseLoadingPagingBinding,
    val retryAction: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.retryButt.setOnClickListener {
            retryAction()
        }
        when (loadState) {
            is LoadState.Error -> {
                binding.loadingPB.visibility = View.GONE
                binding.retryButt.visibility = View.VISIBLE
            }
            is LoadState.Loading -> {
                binding.loadingPB.visibility = View.VISIBLE
                binding.retryButt.visibility = View.GONE
            }
            is LoadState.NotLoading -> {
                binding.loadingPB.visibility = View.GONE
                binding.retryButt.visibility = View.GONE
            }
        }
    }
}