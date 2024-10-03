package com.ketchupzzz.isaom.presentation.main.leaderboard




sealed interface StudentLeaderboardEvents {
    data object OnGetLeaderboard :StudentLeaderboardEvents
}