package id.indocyber.themoviedb.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie_review.Result
import id.indocyber.themoviedb.databinding.MovieDetailFragmentItemBinding

class MovieDetailAdapter : PagingDataAdapter<Result, MovieDetailViewHolder>(itemCallback) {

    override fun onBindViewHolder(holder: MovieDetailViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailViewHolder =
        MovieDetailViewHolder(
            MovieDetailFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}

class MovieDetailViewHolder(val binding: MovieDetailFragmentItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

}