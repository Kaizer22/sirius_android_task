package com.sirius.test_app.ui.game_detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sirius.test_app.databinding.ItemReviewBinding
import com.sirius.test_app.data.api.model.ApiReview
import com.sirius.test_app.domain.model.Review

class ReviewAdapter (
    private val context: Context
    ): ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(ITEM_COMPARATOR) {
    private lateinit var binding: ItemReviewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Review){
            with(binding){
                reviewAuthorName.text = model.userName
                // Подгружаем аватар в ImageView во ViewHolder'е
                Glide
                    .with(context) // Передаем глобальный контекст
                    .load(model.userImage) // URL до изображения
                    .listener(object: RequestListener<Drawable>{ // Определяем обработчики результатов
                        // в целях отладки
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("GLIDE_fail", e.toString())
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.i("GLIDE_ready", "resource ready")
                            return false
                        }
                    })
                    .into(reviewAuthorAvatar) // Указываем, куда подгрузить изображение,
                // используя view binding
                reviewDate.text = model.date
                reviewText.text = model.message
            }
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}


