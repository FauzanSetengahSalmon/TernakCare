package org.fauzan0022.ternak.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddModerator
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.fauzan0022.ternak.model.Kesehatan
import org.fauzan0022.ternak.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KesehatanScreen(nav: NavHostController, id: Long?) {
    val context = LocalContext.current
    val vm: KesehatanViewModel = viewModel(factory = ViewModelFactory(context))
    val data by vm.getByTernak(id ?: 0L).collectAsState(initial = emptyList())

    var kondisi by remember { mutableStateOf("") }
    var suhu by remember { mutableStateOf("") }
    var diagnosa by remember { mutableStateOf("") }
    var tindakan by remember { mutableStateOf("") }
    var dokter by remember { mutableStateOf("") }

    var errorKondisi by remember { mutableStateOf(false) }
    var errorSuhu by remember { mutableStateOf(false) }
    var errorDiagnosa by remember { mutableStateOf(false) }
    var errorTindakan by remember { mutableStateOf(false) }

    val currentTanggal = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rekam Medis", fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = Color.Unspecified
                ),
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    DetailCard(title = "Pemeriksaan Baru", icon = Icons.Default.AddModerator) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            DetailTextField(
                                value = kondisi,
                                onValueChange = { kondisi = it; errorKondisi = false },
                                label = "Kondisi",
                                modifier = Modifier.weight(1f),
                                isError = errorKondisi,
                                icon = Icons.Default.Description
                            )
                            DetailTextField(
                                value = suhu,
                                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) { suhu = it; errorSuhu = false } },
                                label = "Suhu °C",
                                modifier = Modifier.weight(0.7f),
                                isError = errorSuhu,
                                keyboardType = KeyboardType.Decimal,
                                icon = Icons.Default.Thermostat
                            )
                        }

                        DetailTextField(
                            value = diagnosa,
                            onValueChange = { diagnosa = it; errorDiagnosa = false },
                            label = "Diagnosa",
                            isError = errorDiagnosa,
                            icon = Icons.Default.HealthAndSafety
                        )

                        DetailTextField(
                            value = tindakan,
                            onValueChange = { tindakan = it },
                            label = "Tindakan / Obat",
                            isError = errorTindakan,
                            icon = Icons.Default.Medication
                        )

                        DetailTextField(
                            value = dokter,
                            onValueChange = { dokter = it },
                            label = "Tenaga Medis",
                            icon = Icons.Default.PersonSearch
                        )

                        Button(
                            onClick = {
                                if (kondisi.isNotBlank() && suhu.isNotBlank() && diagnosa.isNotBlank()) {
                                    vm.insert(
                                        Kesehatan(
                                            ternakId = id ?: 0L,
                                            tanggalPeriksa = currentTanggal,
                                            kondisi = kondisi,
                                            suhuTubuh = suhu.toDoubleOrNull() ?: 0.0,
                                            diagnosa = diagnosa,
                                            tindakan = tindakan,
                                            dokter = dokter
                                        )
                                    )
                                    kondisi = ""; suhu = ""; diagnosa = ""; tindakan = ""; dokter = ""
                                } else {
                                    errorKondisi = kondisi.isBlank()
                                    errorSuhu = suhu.isBlank()
                                    errorDiagnosa = diagnosa.isBlank()
                                    errorTindakan = kondisi.isBlank()
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Save, null, Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Simpan Rekam Medis", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.History, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Riwayat Pemeriksaan",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                items(data) { riwayat ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(0.5f))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        riwayat.tanggalPeriksa,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        riwayat.kondisi,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Surface(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "${riwayat.suhuTubuh}°C",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp),
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MedicalInformation, null, Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
                                Spacer(Modifier.width(6.dp))
                                Text("Diagnosa: ${riwayat.diagnosa}", style = MaterialTheme.typography.bodySmall)
                            }

                            if (riwayat.tindakan.isNotBlank()) {
                                Row(modifier = Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Healing, null, Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
                                    Spacer(Modifier.width(6.dp))
                                    Text("Tindakan: ${riwayat.tindakan}", style = MaterialTheme.typography.bodySmall)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Badge, null, Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        riwayat.dokter.ifBlank { "Umum" },
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(
                                    onClick = { vm.delete(riwayat.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.DeleteOutline, "Hapus", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}