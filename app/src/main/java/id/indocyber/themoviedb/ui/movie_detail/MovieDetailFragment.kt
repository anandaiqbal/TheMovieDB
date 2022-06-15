package id.indocyber.themoviedb.ui.movie_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themoviedb.R
import id.indocyber.themoviedb.databinding.MovieDetailFragmentBinding
import id.indocyber.themoviedb.ui.base_load.BaseLoadAdapter
import id.indocyber.themoviedb.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel, MovieDetailFragmentBinding>() {
    override val layoutResourceId: Int = R.layout.movie_detail_fragment
    override val vm: MovieDetailViewModel by viewModels()
    private val adapter = MovieDetailAdapter()
    private val loadStateAdapter = BaseLoadAdapter(::retryAction)
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun initBinding(binding: MovieDetailFragmentBinding) {
        super.initBinding(binding)
        vm.navigationtEvent
        binding.movieReviewList.adapter = adapter
        vm.loadDetail(args.movies)
        observeResponseData(vm.movieDetailData, {
            binding.loading.visibility = View.GONE
            it.let {
                binding.data = it
                binding.genre.visibility = View.VISIBLE
                binding.titleReview.visibility = View.VISIBLE
            }
        },
            {
                binding.loading.visibility = View.GONE
                binding.retryButt.visibility = View.VISIBLE
                binding.movieReviewList.visibility = View.GONE
            },
            {
                binding.loading.visibility = View.VISIBLE
                binding.genre.visibility = View.INVISIBLE
                binding.titleReview.visibility = View.INVISIBLE
            })
        observeResponseData(vm.movieVideoData, {
            binding.loading.visibility = View.GONE
            it.key.let {
                val listener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(it, 0f)

                        val defaultPlayerUiController =
                            DefaultPlayerUiController(binding.videoTrailer, youTubePlayer)
                        binding.videoTrailer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    }
                }
                val option = IFramePlayerOptions.Builder().controls(0).build()
                binding.videoTrailer.initialize(listener, option)
            }
        }, {
            binding.noVideo.visibility = View.VISIBLE
            binding.videoTrailer.visibility = View.INVISIBLE
        }, {
        })
        binding.movieReviewList.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.loading.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.loading.visibility = View.GONE
                binding.movieReviewList.visibility = View.GONE
                binding.retryButt.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.loading.visibility = View.GONE
                binding.noReview.visibility = View.VISIBLE
            } else if (it.refresh is LoadState.NotLoading) {
                binding.loading.visibility = View.GONE
            }
        }
        vm.movieReviewData.observe(this@MovieDetailFragment) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        binding.retryButt.setOnClickListener {
            vm.loadDetail(args.movies)
            adapter.retry()
            binding.noVideo.visibility = View.GONE
            binding.loading.visibility = View.GONE
            binding.retryButt.visibility = View.GONE
            binding.videoTrailer.visibility = View.VISIBLE
            binding.movieReviewList.visibility = View.VISIBLE
        }
    }

    fun retryAction() {
        adapter.retry()
        binding.movieReviewList.visibility = View.VISIBLE
    }
}