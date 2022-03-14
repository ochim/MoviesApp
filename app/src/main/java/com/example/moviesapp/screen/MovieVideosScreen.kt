package com.example.moviesapp.screen

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.moviesapp.R
import com.example.moviesapp.domain.MovieVideosEntity
import com.example.moviesapp.domain.VideoEntity
import com.example.moviesapp.domain.testMovies
import com.example.moviesapp.ui.theme.AppContentColor
import com.example.moviesapp.ui.theme.AppThemeColor
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView

@ExperimentalAnimationApi
@Composable
fun MovieVideosScreen(popBackStack: () -> Unit, movieId: Int) {
    val movie = testMovies[movieId]
    Scaffold(
        topBar = {
            MovieVideosTopBar(popBackStack)
        },
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
            movie.movieVideosEntity?.let {
                if (it.count > 0) ShowMovieVideos(movieVideosEntity = it)
            }
        })
}

@Composable
fun MovieVideosTopBar(
    popBackStack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colors.AppContentColor
                )
            }
        },
        title = {
            Text(
                text = "Videos",
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )
        },
        elevation = 0.dp,
    )
}

@ExperimentalAnimationApi
@Composable
fun ShowMovieVideos(movieVideosEntity: MovieVideosEntity) {

    val playingIndex = remember {
        mutableStateOf(0)
    }

    fun onTrailerChange(index: Int) {
        playingIndex.value = index
    }

    Column {
        VideoPlayer(
            modifier = Modifier.weight(1f, fill = true),
            movieVideos = movieVideosEntity.entities,
            playingIndex = playingIndex,
            onTrailerChange = { newIndex -> onTrailerChange(newIndex) }
        )
        LazyColumn(
            modifier = Modifier.weight(1f, fill = true),
            content = {
                itemsIndexed(movieVideosEntity.entities) { index, trailer ->
                    ShowTrailers(
                        index = index,
                        trailer = trailer,
                        playingIndex = playingIndex,
                        onTrailerClicked = { newIndex -> onTrailerChange(newIndex) })
                }
            })
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ShowTrailers(
    index: Int,
    trailer: VideoEntity,
    playingIndex: State<Int>,
    onTrailerClicked: (Int) -> Unit
) {
    val currentlyPlaying = remember {
        mutableStateOf(false)
    }
    currentlyPlaying.value = index == playingIndex.value
    ConstraintLayout(modifier = Modifier
        .testTag("TrailerParent")
        .padding(8.dp)
        .wrapContentSize()
        .clickable {
            onTrailerClicked(index)
        }) {
        val (thumbnail, play, title, nowPlaying) = createRefs()
        Image(
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(data = trailer.preview, builder = {
                crossfade(true)
            }),
            contentDescription = "Trailer",
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(elevation = 20.dp)
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                }
        )
        if (currentlyPlaying.value) {
            Image(
                contentScale = ContentScale.Crop,
                colorFilter = if (trailer.preview.isEmpty()) ColorFilter.tint(MaterialTheme.colors.onBackground) else ColorFilter.tint(
                    MaterialTheme.colors.primary
                ),
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "Play Trailer",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .graphicsLayer {
                        clip = true
                        shadowElevation = 20.dp.toPx()
                    }
                    .constrainAs(play) {
                        top.linkTo(thumbnail.top)
                        start.linkTo(thumbnail.start)
                        end.linkTo(thumbnail.end)
                        bottom.linkTo(thumbnail.bottom)
                    }
            )
        }
        Text(
            text = trailer.name,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(thumbnail.top, margin = 8.dp)
                    start.linkTo(thumbnail.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.preferredWrapContent
                    height = Dimension.wrapContent
                },
            color = MaterialTheme.colors.background,
            textAlign = TextAlign.Center,
            softWrap = true,
            style = MaterialTheme.typography.subtitle1
        )
        if (currentlyPlaying.value) {
            Text(
                text = "Trailer Now Playing",
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.constrainAs(nowPlaying) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(thumbnail.end, margin = 8.dp)
                    bottom.linkTo(thumbnail.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.preferredWrapContent
                    height = Dimension.preferredWrapContent
                }
            )
        }
        TrailerDivider()
    }
}

@Composable
fun TrailerDivider() {
    Divider(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .testTag("Divider"),
        color = MaterialTheme.colors.surface
    )
}

@ExperimentalAnimationApi
@Composable
fun VideoPlayer(
    movieVideos: List<VideoEntity>,
    playingIndex: State<Int>,
    onTrailerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val visible = remember {
        mutableStateOf(true)
    }
    val videoTitle = remember {
        mutableStateOf(movieVideos[playingIndex.value].name)
    }
    val mediaItems = arrayListOf<MediaItem>()
    movieVideos.forEach {
        mediaItems.add(
            MediaItem.Builder().setUri(it.video).setMediaId(it.id.toString()).setTag(it)
                .setMediaMetadata(MediaMetadata.Builder().setDisplayTitle(it.name).build())
                .build()
        )
    }
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            this.setMediaItems(mediaItems)
            this.prepare()
            this.addListener(object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    if (player.contentPosition >= 200) visible.value = false
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    onTrailerChange(this@apply.currentPeriodIndex)
                    visible.value = true
                    videoTitle.value = mediaItem?.mediaMetadata?.displayTitle.toString()
                }
            })
        }
    }

    exoPlayer.seekTo(playingIndex.value, C.TIME_UNSET)
    exoPlayer.playWhenReady = true

    LocalLifecycleOwner.current.lifecycle.addObserver(object : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            if (exoPlayer.isPlaying.not()) {
                exoPlayer.play()
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            exoPlayer.pause()
            super.onStop(owner)
        }
    })

    ConstraintLayout(modifier = modifier.background(MaterialTheme.colors.background)) {
        val (title, videoPlayer) = createRefs()
        AnimatedVisibility(
            visible = visible.value,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250)),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = videoTitle.value,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
        DisposableEffect(
            AndroidView(
                modifier = modifier
                    .testTag("VideoPlayer")
                    .constrainAs(videoPlayer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                })
        ) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowTrailerPreview() {
    ShowTrailers(
        index = 0,
        trailer = VideoEntity(1, "", "Online: DeadLine GTA Online: DeadLine", ""),
        playingIndex = remember { mutableStateOf(0) },
        onTrailerClicked = { }
    )
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun ShowMovieVideosPreview() {
    ShowMovieVideos(
        movieVideosEntity = MovieVideosEntity(
            1,
            arrayListOf(VideoEntity(1, "", "GTA Online: DeadLine GTA Online: DeadLine", ""))
        )
    )
}


