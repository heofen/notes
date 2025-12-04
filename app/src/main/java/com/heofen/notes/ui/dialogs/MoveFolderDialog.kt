package com.heofen.notes.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.heofen.notes.R
import com.heofen.notes.data.Folder
import com.heofen.notes.ui.theme.NotesTheme

@Composable
fun MoveFolderDialog(
    folders: List<Folder>,
    currentFolderId: Int?,
    onDismiss: () -> Unit,
    onConfirm: (Int?) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.move_to_folder),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
                )
                },
        text = {
            Column {
                TextButton(
                    onClick = { onConfirm(null) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.without_folder))
                }
                folders.forEach { folder ->
                    TextButton(
                        onClick = { onConfirm(folder.id) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = folder.id != currentFolderId
                    ) {
                        Text(folder.name)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Preview
@Composable
fun MoveFolderDialogPreview() {
    NotesTheme {
        MoveFolderDialog(
            folders = listOf(
                Folder(1, "Работа"),
                Folder(2, "Личное")
            ),
            currentFolderId = null,
            onDismiss = {},
            onConfirm = {}
        )
    }
}
