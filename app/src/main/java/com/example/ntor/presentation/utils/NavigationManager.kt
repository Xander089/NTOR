package com.example.ntor.presentation.utils

import android.os.Bundle
import androidx.navigation.NavController

object NavigationManager {

    fun navigateTo(
        navController: NavController,
        action: Int,
        bundle: Bundle? = null
    ) {
        if (bundle == null) {
            navController.navigate(
                action
            )
        } else {
            navController.navigate(
                action,
                bundle
            )
        }

    }
}