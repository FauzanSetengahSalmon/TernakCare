package org.fauzan0022.ternak.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.fauzan0022.ternak.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleScreen(nav: NavHostController) {
    val context = LocalContext.current
    val vm: MainViewModel = viewModel(factory = ViewModelFactory(context))
    val data by vm.recycleData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDFDFD))
                .padding(padding)
        ) {
            if (data.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.DeleteSweep, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text("Tempat sampah kosong", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text("Total di Sampah", style = MaterialTheme.typography.labelLarge)
                                    Text("${data.size} Ekor Ternak", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                                }
                                Icon(Icons.Default.DeleteSweep, null, tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }

                    items(data) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            ListItem(
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                headlineContent = {
                                    Text("${item.kodeTernak} - ${item.namaHewan}", fontWeight = FontWeight.Bold)
                                },
                                supportingContent = {
                                    Column {
                                        Text("${item.jenisHewan} • ${item.umurBulan} Bln • ${item.beratKg} Kg")

                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                                            Text(
                                                item.jenisKelamin,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                            Spacer(Modifier.size(8.dp))
                                            Text(
                                                if (item.statusSehat) "Sehat" else "Sakit",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (item.statusSehat) Color(0xFF2E7D32) else Color.Red
                                            )
                                        }
                                    }
                                },
                                trailingContent = {
                                    Row {
                                        IconButton(onClick = { vm.restore(item) }) {
                                            Icon(Icons.Default.Restore, "Pulihkan", tint = Color(0xFF2E7D32))
                                        }
                                        IconButton(onClick = { vm.deletePermanently(item) }) {
                                            Icon(Icons.Default.DeleteForever, "Hapus Permanen", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}