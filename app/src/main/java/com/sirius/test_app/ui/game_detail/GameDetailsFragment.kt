package com.sirius.test_app.ui.game_detail

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sirius.test_app.R
import com.sirius.test_app.databinding.FragmentMainBinding
import com.sirius.test_app.test.SimpleTestDataProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment: Fragment() {
    companion object{
        private val TAG = "GameDetailsF"
    }

    private lateinit var binding: FragmentMainBinding
    // "Ленивая" инициализация viewModel при первом обращении
    private val viewModel: GameDetailsViewModel by lazy {
        Log.d(TAG, "vm init")
        ViewModelProvider(this).get(GameDetailsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Применения кастомного поведения для логотипа, названия и
        // динамического затенения строки состояния
        ((binding.logoContainer.layoutParams) as CoordinatorLayout.LayoutParams).behavior = LogoScrollBehavior(
            binding.nameHeader,
            binding.topShadowDynamic
        )
        val adapter = ReviewAdapter(requireContext())
        binding.reviewsList.adapter = adapter
        binding.reviewsList.layoutManager = LinearLayoutManager(requireContext())
        //Log.d(TAG, "test")
        observeViewStateUpdates(adapter)
    }

    // Подписываемя на изменение состояния viewModel
    // при изменении перерисовываем экран с новыми данными из state
    private fun observeViewStateUpdates(adapter: ReviewAdapter) {
        Log.d(TAG, "test2")
        viewModel.state.observe(viewLifecycleOwner) {
            Log.d(TAG, "test1")
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(state: GameDetailsViewModel.GameDetailsState, adapter: ReviewAdapter) {
        //it.loadingReviews
        adapter.submitList(state.reviews)
        with(binding){
            description.text = state.gameDetails.description
            nameHeader.text = state.gameDetails.name
            rating.text = state.gameDetails.rating.toString()
            indicatorRatingBar.rating = state.gameDetails.rating
            interactiveRatingBar.rating = state.gameDetails.rating
            gradeCounter.text = state.gameDetails.gradeCnt
            gradesCountBottom.text = "${state.gameDetails.gradeCnt} Reviews"
            buttonAction.text = state.gameDetails.action.name

            addTags(binding, state.gameDetails.tags)

            Glide.with(requireContext())
                .load(state.gameDetails?.logo)
                .into(logo)
            Glide.with(requireContext())
                .load(state.gameDetails?.image)
                .into(pageImg)
        }
    }

    // Динамически отрисовываем теги, добавляя их во Flow ConstraintLayout
    private val tagsViews: MutableList<View> = mutableListOf()
    private fun addTags(binding: FragmentMainBinding, tags: List<String>?) {
        Log.d(TAG, "tags")
        with(binding) {
            if (tags != null) {
                // FIXME оптимизировать перерисовку тегов
                for (tag in tagsViews) {
                    tagsFlow.removeView(tag)
                }
                for (tag in tags) {
                    val view =
                        LayoutInflater.from(context).inflate(R.layout.item_tag, tagsFlow, false)
                    view.id = View.generateViewId()
                    view.findViewById<TextView>(R.id.item_tag).text = tag
                    tagsViews.add(view)
                    tagsFlow.addView(view)
                    flow.addView(view)
                }
            }
        }
    }
}