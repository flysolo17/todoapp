package com.ketchupzzz.isaom.presentation.main.profle



sealed interface ProfileEvents {
    data object OnLoggedOut : ProfileEvents

}