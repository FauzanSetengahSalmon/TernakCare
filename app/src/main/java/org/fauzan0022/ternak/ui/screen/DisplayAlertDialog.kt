package org.fauzan0022.ternak.ui.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.fauzan0022.ternak.ui.theme.TernakTheme

@Composable
fun DisplayAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        icon = {
            Icon(
                Icons.Default.DeleteForever,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },

        title = {
            Text(
                text = "Pindahkan ke Sampah?",
                fontWeight = FontWeight.Bold
            )
        },

        text = {
            Text(
                text = "Data ini akan dipindahkan ke Recycle Bin. Kamu masih bisa memulihkannya nanti sebelum dihapus permanen."
            )
        },

        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "Ya, Hapus",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        },

        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Batal")
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    TernakTheme {
        DisplayAlertDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}