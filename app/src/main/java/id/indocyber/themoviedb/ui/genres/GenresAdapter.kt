package id.indocyber.themoviedb.ui.genres

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genres.Genre
import id.indocyber.themoviedb.databinding.GenresFragmentItemBinding

class GenreAdapter(
    val isSelected: (Long) -> Boolean
) : RecyclerView.Adapter<GenreViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = differ.currentList[position].id.toLong()

    val differ = AsyncListDiffer(this, itemCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            GenresFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        ) {
            differ.currentList[it].id.toLong()
        }
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val data = differ.currentList[position]
        data?.apply {
            holder.bind(this, isSelected(this.id.toLong()))
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


class GenreViewHolder(val binding: GenresFragmentItemBinding, val getItemKey: (Int) -> Long) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: Genre, selection: Boolean) {
        binding.data = genre
        binding.isSelected = selection
    }

    fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getPosition(): Int = bindingAdapterPosition

        override fun getSelectionKey(): Long = getItemKey(bindingAdapterPosition)

    }
}


class GenreItemKeyProvider(val genreAdapter: GenreAdapter) : ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long? {
        return genreAdapter.differ.currentList[position].id.toLong()
    }

    override fun getPosition(key: Long): Int {
        return genreAdapter.differ.currentList.indexOfFirst {
            it.id.toLong() == key
        }
    }
}

class GenreItemDetailsLookup(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as GenreViewHolder).getItemDetails()
        }
    }
}