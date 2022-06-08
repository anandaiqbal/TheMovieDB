package id.indocyber.common.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter("custom:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .into(imageView)
        }
    }

    @BindingAdapter("custom:loadImageCircled")
    @JvmStatic
    fun loadImageCircled(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .circleCrop()
                .into(imageView)
        }
    }
}