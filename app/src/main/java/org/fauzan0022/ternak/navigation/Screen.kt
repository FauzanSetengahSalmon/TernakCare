package org.fauzan0022.ternak.navigation
const val KEY_ID = "id"
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object FormBaru : Screen("formBaru")
    data object Recycle : Screen("recycle")
    data object FormUbah : Screen("formUbah/{$KEY_ID}") {
        fun withId(id: Long) = "formUbah/$id"
    }
    data object Kesehatan : Screen("kesehatan/{$KEY_ID}") {
        fun withId(id: Long) = "kesehatan/$id"
    }
}