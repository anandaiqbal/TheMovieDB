package id.indocyber.themoviedb.ui.movie_detail

import android.app.ProgressDialog
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
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
        val dialog = ProgressDialog.show(
            context, "Loading",
            "Please wait...", true
        )
        binding.movieReviewList.adapter = adapter
        vm.loadDetail(args.movies)
        observeResponseData(vm.movieDetailData, {
            dialog.dismiss()
            it.first.let {
                binding.data = it
            }
            it.second.results.get(0).key.let {
                binding.videoTrailer.addYouTubePlayerListener(object : YouTubePlayerListener {
                    override fun onApiChange(youTubePlayer: YouTubePlayer) {
                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                    }

                    override fun onPlaybackQualityChange(
                        youTubePlayer: YouTubePlayer,
                        playbackQuality: PlayerConstants.PlaybackQuality
                    ) {
                    }

                    override fun onPlaybackRateChange(
                        youTubePlayer: YouTubePlayer,
                        playbackRate: PlayerConstants.PlaybackRate
                    ) {
                    }

                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(it, 0f)
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                    }

                    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                    }

                    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                    }

                    override fun onVideoLoadedFraction(
                        youTubePlayer: YouTubePlayer,
                        loadedFraction: Float
                    ) {
                    }

                })

            }
            vm.movieReviewData.observe(this@MovieDetailFragment) {
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.submitData(it)
                }
            }
        }, {
            dialog.dismiss()
            binding.retryButt.visibility = View.VISIBLE
            binding.movieReviewList.visibility = View.GONE
        }, {
            dialog.show()
        })
        binding.retryButt.setOnClickListener {
            vm.loadDetail(args.movies)
            binding.retryButt.visibility = View.GONE
            binding.movieReviewList.visibility = View.VISIBLE
        }
    }
}