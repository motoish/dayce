package com.motoish.dayce.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.motoish.dayce.DayceApplication

@Composable
fun DayceApp() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as DayceApplication
    val viewModel: EventViewModel = viewModel(factory = EventViewModel.Factory(app.repository))

    NavHost(navController = navController, startDestination = "events") {
        composable("events") {
            EventListScreen(
                viewModel = viewModel,
                onAdd = { navController.navigate("event/new") },
                onOpen = { navController.navigate("event/$it") }
            )
        }
        composable("event/new") {
            EventFormScreen(
                title = "New event",
                existing = null,
                onCancel = { navController.popBackStack() },
                onSave = { name, date, kind, note ->
                    viewModel.addEvent(name, date, kind, note)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "event/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            EventDetailScreen(
                viewModel = viewModel,
                eventId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("event/$id/edit") },
                onDeleted = { navController.popBackStack("events", false) }
            )
        }
        composable(
            route = "event/{id}/edit",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: return@composable
            EventEditRoute(
                viewModel = viewModel,
                eventId = id,
                onCancel = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }
    }
}
