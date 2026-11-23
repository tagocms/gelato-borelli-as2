package com.example.atividadesomativa2.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atividadesomativa2.view.orders.OrdersListScreen
import com.example.atividadesomativa2.view.scoops.ScoopsListScreen
import com.example.atividadesomativa2.view.toppings.ToppingsListScreen
import com.example.atividadesomativa2.viewmodel.OrderViewModel
import com.example.atividadesomativa2.viewmodel.ScoopViewModel
import com.example.atividadesomativa2.viewmodel.ToppingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val orderViewModel: OrderViewModel = viewModel()

    NavHost(
        navController, "orders"
    ) {
        composable("orders") {
            OrdersListScreen(
                orderViewModel = orderViewModel,
                onNewOrder = { navController.navigate("orders/") },
                onOrderUpdate = { id -> navController.navigate("orders/$id") },
                goToScoops = { navController.navigate("scoops") },
                goToToppings = { navController.navigate("toppings") }
            )
        }

        composable("orders/{orderId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("orderId")?.toIntOrNull()

        }

        composable("orders/{orderId}/iceCreams/{iceCreamId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")?.toIntOrNull()
            val iceCreamId = backStackEntry.arguments?.getString("iceCreamId")?.toIntOrNull()

        }

        composable("scoops") {
            val scoopsViewModel: ScoopViewModel = viewModel()

            ScoopsListScreen(
                viewModel = scoopsViewModel,
                onNewScoop = { navController.navigate("scoops/") },
                onScoopUpdate = { id -> navController.navigate("scoops/$id") },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("scoops/{scoopId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("scoopId")?.toIntOrNull()
        }

        composable("toppings") {
            val toppingViewModel: ToppingViewModel = viewModel()

            ToppingsListScreen(
                viewModel = toppingViewModel,
                onNewTopping = { navController.navigate("scoops/") },
                onToppingUpdate = { id -> navController.navigate("scoops/$id") },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("toppings/{toppingId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("toppingId")?.toIntOrNull()
        }
    }
}