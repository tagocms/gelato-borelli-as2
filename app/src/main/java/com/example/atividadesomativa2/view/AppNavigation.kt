package com.example.atividadesomativa2.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atividadesomativa2.view.orders.OrderUpdateScreen
import com.example.atividadesomativa2.view.orders.OrdersListScreen
import com.example.atividadesomativa2.view.scoops.ScoopUpdateScreen
import com.example.atividadesomativa2.view.scoops.ScoopsListScreen
import com.example.atividadesomativa2.view.toppings.ToppingUpdateScreen
import com.example.atividadesomativa2.view.toppings.ToppingsListScreen
import com.example.atividadesomativa2.viewmodel.IceCreamViewModel
import com.example.atividadesomativa2.viewmodel.OrderViewModel
import com.example.atividadesomativa2.viewmodel.ScoopViewModel
import com.example.atividadesomativa2.viewmodel.ToppingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val orderViewModel: OrderViewModel = viewModel()
    val iceCreamViewModel: IceCreamViewModel = viewModel()
    val scoopsViewModel: ScoopViewModel = viewModel()
    val toppingViewModel: ToppingViewModel = viewModel()

    NavHost(
        navController, "orders"
    ) {
        composable("orders") {
            OrdersListScreen(
                viewModel = orderViewModel,
                onNewOrder = { navController.navigate("orders/") },
                onOrderUpdate = { id -> navController.navigate("orders/$id") },
                goToScoops = { navController.navigate("scoops") },
                goToToppings = { navController.navigate("toppings") }
            )
        }

        composable("orders/{orderId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("orderId")?.toIntOrNull()

            OrderUpdateScreen(
                orderViewModel = orderViewModel,
                iceCreamViewModel = iceCreamViewModel,
                orderId = id,
                onNewIceCream = { navController.navigate("orders/$id/") },
                onIceCreamUpdate = { iceCreamId -> navController.navigate("orders/$id/$iceCreamId") },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("orders/{orderId}/{iceCreamId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")?.toIntOrNull()
            val iceCreamId = backStackEntry.arguments?.getString("iceCreamId")?.toIntOrNull()

        }

        composable("scoops") {
            ScoopsListScreen(
                viewModel = scoopsViewModel,
                onNewScoop = { navController.navigate("scoops/") },
                onScoopUpdate = { id -> navController.navigate("scoops/$id") },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("scoops/{scoopId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("scoopId")?.toIntOrNull()

            ScoopUpdateScreen(
                viewModel = scoopsViewModel,
                scoopId = id,
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("toppings") {
            ToppingsListScreen(
                viewModel = toppingViewModel,
                onNewTopping = { navController.navigate("toppings/") },
                onToppingUpdate = { id -> navController.navigate("toppings/$id") },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable("toppings/{toppingId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("toppingId")?.toIntOrNull()

            ToppingUpdateScreen(
                viewModel = toppingViewModel,
                toppingId = id,
                onGoBack = { navController.popBackStack() }
            )
        }
    }
}