package id.indocyber.themoviedb.ui.movie_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themoviedb.R
import id.indocyber.themoviedb.databinding.MovieDetailFragmentBinding
import id.indocyber.themoviedb.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel, MovieDetailFragmentBinding>() {
    override val layoutResourceId: Int = R.layout.movie_detail_fragment
    override val vm: MovieDetailViewModel by viewModels()
    val adapter = MovieDetailAdapter()
    val args by navArgs<MovieDetailFragmentArgs>()

    override fun initBinding(binding: MovieDetailFragmentBinding) {
        super.initBinding(binding)
        binding.movieReviewList.adapter = adapter
        vm.loadDetail(args.movies)
        observeResponseData(vm.movieDetailData, {
            binding.loading.visibility = View.GONE
            it.let {
                binding.data = it
            }
        },
            {
                binding.loading.visibility = View.GONE
                binding.retryButt.visibility = View.VISIBLE
                binding.movieReviewList.visibility = View.GONE
            },
            {
                binding.loading.visibility = View.VISIBLE
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
            binding.loading.visibility = View.GONE
        }, {
            binding.loading.visibility = View.VISIBLE
        })
        vm.movieReviewData.observe(this@MovieDetailFragment) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        binding.retryButt.setOnClickListener {
            vm.loadDetail(args.movies)
            binding.loading.visibility = View.GONE
            binding.retryButt.visibility = View.GONE
            binding.movieReviewList.visibility = View.VISIBLE
        }
    }
}