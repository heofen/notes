package com.heofen.notes.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heofen.notes.data.Note
import com.heofen.notes.ui.theme.NotesTheme
import com.heofen.notes.R

@Composable
fun NoteOptionsDialog(
    note: Note,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onMove: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.note_options),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            ) },
        text = {
            Column {
                NoteOptionButton(
                    icon = Icons.Default.Edit,
                    text = stringResource(R.string.edit),
                    onClick = onEdit
                )
                NoteOptionButton(
                    icon = ImageVector.vectorResource(id = R.drawable.folder),
                    text = stringResource(R.string.move_to_folder),
                    onClick = onMove
                )
                NoteOptionButton(
                    icon = Icons.Default.Delete,
                    text = stringResource(R.string.delete),
                    onClick = onDelete,
                    isDestructive = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.close))
            }
        }
    )
}

@Composable
private fun NoteOptionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isDestructive) MaterialTheme.colorScheme.error else LocalContentColor.current
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            color = if (isDestructive) MaterialTheme.colorScheme.error else LocalContentColor.current
        )
    }
}

@Preview
@Composable
fun NoteOptionsDialogPreview() {
    NotesTheme {
        NoteOptionsDialog(
            note = Note(
                id = 1,
                title = "Test",
                text = "Test text",
                createTime = System.currentTimeMillis()
            ),
            onDismiss = {},
            onEdit = {},
            onDelete = {},
            onMove = {}
        )
    }
}
