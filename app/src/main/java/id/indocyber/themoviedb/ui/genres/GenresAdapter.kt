package id.indocyber.themoviedb.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genres.Genre
import id.indocyber.themoviedb.databinding.GenresFragmentItemBinding

class GenreAdapter(
    val getGenre: (Genre) -> Unit
) : RecyclerView.Adapter<GenreViewHolder>() {
    val differ = AsyncListDiffer(this, itemCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            GenresFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.binding.data = data
        holder.binding.cardView.setOnClickListener {
            getGenre(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

        }
    }

}


class GenreViewHolder(val binding: GenresFragmentItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

}