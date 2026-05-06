package org.fauzan0022.ternak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.fauzan0022.ternak.navigation.SetupNavGraph
import org.fauzan0022.ternak.ui.theme.TernakTheme
import org.fauzan0022.ternak.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val dataStore = remember { SettingsDataStore(context) }
            val isDarkMode by dataStore.themeFlow.collectAsState(initial = false)
            TernakTheme(darkTheme = isDarkMode) {
                SetupNavGraph()
            }
        }
    }
}