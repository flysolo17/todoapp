package com.ketchupzzz.isaom.presentation.main.lessons

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.models.SignLanguageLesson
import com.ketchupzzz.isaom.utils.generateRandomString
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun LessonScreen(
    modifier: Modifier = Modifier,
    state: LessonState,
    events: (LessonEvents) -> Unit,
    navHostController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn {
            items(state.lessons, key = {it.id ?: generateRandomString() }) {
                LessonCard(lesson = it, lifecycleOwner = lifecycleOwner)
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LessonCard(modifier: Modifier =Modifier,lesson: SignLanguageLesson,lifecycleOwner: LifecycleOwner) {

    Card(modifier = modifier.fillMaxWidth().padding(3.dp)) {
        Column {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Adjust height as needed
                factory = { ctx ->
                    YouTubePlayerView(ctx).apply {

                        lifecycleOwner.lifecycle.addObserver(this)
                        addYouTubePlayerListener(
                            object  : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.loadVideo(lesson.videoId!!,0f)
                                    super.onReady(youTubePlayer)

                                }
                            }
                        )
                    }
                }
            )

            Column(modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Text(text = lesson.title!!, style = MaterialTheme.typography.titleMedium)
                Text(text = lesson.desc!!, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}