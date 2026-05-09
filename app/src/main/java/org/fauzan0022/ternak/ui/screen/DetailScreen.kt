package org.fauzan0022.ternak.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.fauzan0022.ternak.R
import org.fauzan0022.ternak.model.Ternak
import org.fauzan0022.ternak.navigation.Screen
import org.fauzan0022.ternak.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nav: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val vm: DetailViewModel = viewModel(factory = ViewModelFactory(context))

    var kodeTernak by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var jenisHewan by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("Jantan") }
    var umurBulan by remember { mutableStateOf("") }
    var beratKg by remember { mutableStateOf("") }
    var statusSehat by remember { mutableStateOf(true) }
    var catatan by remember { mutableStateOf("") }

    var kodeError by remember { mutableStateOf(false) }
    var namaError by remember { mutableStateOf(false) }
    var jenisError by remember { mutableStateOf(false) }
    var umurError by remember { mutableStateOf(false) }
    var beratError by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id != null && id != 0L) {
            vm.getById(id)?.let {
                kodeTernak = it.kodeTernak
                nama = it.namaHewan
                jenisHewan = it.jenisHewan
                jenisKelamin = it.jenisKelamin
                umurBulan = it.umurBulan.toString()
                beratKg = it.beratKg.toString()
                statusSehat = it.statusSehat
                catatan = it.catatan
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(if (id == null) R.string.tambah_ternak else R.string.edit_ternak),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = Color.Unspecified
                ),
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.kembali))
                    }
                },
                actions = {
                    if (id != null) {
                        IconButton(onClick = { nav.navigate(Screen.Kesehatan.withId(id)) }) {
                            Icon(Icons.Default.MedicalServices, stringResource(R.string.medis), tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.DeleteSweep, stringResource(R.string.hapus), tint = MaterialTheme.colorScheme.error)
                        }
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailCard(title = stringResource(R.string.identitas_ternak), icon = Icons.Default.Badge) {
                DetailTextField(
                    value = kodeTernak,
                    onValueChange = { kodeTernak = it; kodeError = false },
                    label = stringResource(R.string.label_kode_ternak),
                    placeholder = stringResource(R.string.hint_kode_ternak),
                    isError = kodeError,
                    icon = Icons.Default.QrCode
                )
                DetailTextField(
                    value = nama,
                    onValueChange = { nama = it; namaError = false },
                    label = stringResource(R.string.label_nama_hewan),
                    isError = namaError,
                    icon = Icons.AutoMirrored.Filled.Label
                )
                DetailTextField(
                    value = jenisHewan,
                    onValueChange = { jenisHewan = it; jenisError = false },
                    label = stringResource(R.string.label_jenis_hewan),
                    isError = jenisError,
                    icon = Icons.Default.Category
                )
            }
            DetailCard(title = stringResource(R.string.karakteristik_fisik), icon = Icons.Default.Straighten) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DetailTextField(
                        value = umurBulan,
                        onValueChange = { input->
                            if (input.isEmpty() || input.toDoubleOrNull() != null) {
                                umurBulan = input
                                umurError = false
                            }
                        },
                        label = stringResource(R.string.label_umur_bln),
                        modifier = Modifier.weight(1f),
                        isError = umurError,
                        keyboardType = KeyboardType.Number,
                        icon = Icons.Default.Cake
                    )
                    DetailTextField(
                        value = beratKg,
                        onValueChange = { input->
                            if (input.isEmpty() || input.toDoubleOrNull() != null) {
                                beratKg = input
                                beratError = false
                            }
                        },
                        label = stringResource(R.string.label_berat_kg),
                        modifier = Modifier.weight(1f),
                        isError = beratError,
                        keyboardType = KeyboardType.Number,
                        icon = Icons.Default.Scale
                    )
                }

                Text(stringResource(R.string.label_jenis_kelamin), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GenderButton(
                        label = stringResource(R.string.jantan),
                        isSelected = (jenisKelamin == "Jantan"),
                        icon = Icons.Default.Male,
                        modifier = Modifier.weight(1f),
                        onClick = { jenisKelamin = "Jantan" }
                    )
                    GenderButton(
                        label = stringResource(R.string.betina),
                        isSelected = (jenisKelamin == "Betina"),
                        icon = Icons.Default.Female,
                        modifier = Modifier.weight(1f),
                        onClick = { jenisKelamin = "Betina" }
                    )
                }
            }
            DetailCard(title = stringResource(R.string.kesehatan_catatan), icon = Icons.AutoMirrored.Filled.Notes) {
                Text(stringResource(R.string.label_kondisi_saat_ini), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatusButton(
                        label = stringResource(R.string.sehat),
                        isSelected = statusSehat,
                        activeColor = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f),
                        onClick = { statusSehat = true }
                    )
                    StatusButton(
                        label = stringResource(R.string.sakit),
                        isSelected = !statusSehat,
                        activeColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.weight(1f),
                        onClick = { statusSehat = false }
                    )
                }

                OutlinedTextField(
                    value = catatan,
                    onValueChange = { catatan = it },
                    label = { Text(stringResource(R.string.label_catatan_tambahan)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    )
                )
            }

            Button(
                onClick = {
                    if (kodeTernak.isNotBlank() && nama.isNotBlank() && jenisHewan.isNotBlank() && umurBulan.isNotBlank() && beratKg.isNotBlank()) {
                        val data = Ternak(
                            id = id ?: 0L,
                            kodeTernak = kodeTernak,
                            namaHewan = nama,
                            jenisHewan = jenisHewan,
                            jenisKelamin = jenisKelamin,
                            umurBulan = umurBulan.toIntOrNull() ?: 0,
                            beratKg = beratKg.toDoubleOrNull() ?: 0.0,
                            statusSehat = statusSehat,
                            tanggalMasuk = if (id == null) System.currentTimeMillis().toString() else "",
                            catatan = catatan
                        )
                        if (id == null) vm.insert(data) else vm.update(data)
                        nav.popBackStack()
                    } else {
                        kodeError = kodeTernak.isBlank()
                        namaError = nama.isBlank()
                        jenisError = jenisHewan.isBlank()
                        umurError = umurBulan.isBlank()
                        beratError = beratKg.isBlank()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.simpan_data_ternak), fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.dialog_hapus_judul)) },
            text = { Text(stringResource(R.string.dialog_hapus_pesan)) },
            confirmButton = {
                TextButton(onClick = { id?.let { vm.delete(it) }; nav.popBackStack() }) {
                    Text(stringResource(R.string.hapus), color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text(stringResource(R.string.batal)) }
            }
        )
    }
}

@Composable
fun GenderButton(
    label: String,
    isSelected: Boolean,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = if (!isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    ) {
        Icon(icon, null, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Text(label, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusButton(
    label: String,
    isSelected: Boolean,
    activeColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) activeColor else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) {
                if (activeColor == MaterialTheme.colorScheme.error) MaterialTheme.colorScheme.onError
                else Color.White
            } else { MaterialTheme.colorScheme.onSurfaceVariant
            }
        ),
        border = if (!isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold)
        )
    }
}

@Composable
fun DetailCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            content()
        }
    }
}

@Composable
fun DetailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    icon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = stringResource(R.string.error_input_kosong, label),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        leadingIcon = icon?.let { { Icon(it, null, tint = MaterialTheme.colorScheme.primary) } },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorSupportingTextColor = MaterialTheme.colorScheme.error
        )
    )
}