package com.heofen.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heofen.notes.R
import com.heofen.notes.data.Folder

@Composable
fun NotesNavigationDrawer(
    drawerState: DrawerState,
    folders: List<Folder>,
    selectedFolderId: Int?,
    onFolderSelected: (Int?) -> Unit,
    onAddFolder: () -> Unit,
    onDeleteFolder: (Folder) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    folders = folders,
                    selectedFolderId = selectedFolderId,
                    onFolderSelected = onFolderSelected,
                    onAddFolder = onAddFolder,
                    onDeleteFolder = onDeleteFolder
                )
            }
        },
        content = content
    )
}

@Composable
private fun DrawerContent(
    folders: List<Folder>,
    selectedFolderId: Int?,
    onFolderSelected: (Int?) -> Unit,
    onAddFolder: () -> Unit,
    onDeleteFolder: (Folder) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.folders),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.all_notes)) },
            selected = selectedFolderId == null,
            onClick = { onFolderSelected(null) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.without_folder)) },
            selected = selectedFolderId == -1,
            onClick = { onFolderSelected(-1) },
            icon = { Icon(Icons.Default.Create, contentDescription = null) },
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        folders.forEach { folder ->
            FolderDrawerItem(
                folder = folder,
                isSelected = selectedFolderId == folder.id,
                onFolderClick = { onFolderSelected(folder.id) },
                onDeleteClick = { onDeleteFolder(folder) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.add_folder)) },
            selected = false,
            onClick = onAddFolder,
            icon = { Icon(Icons.Default.Add, contentDescription = null) },
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun FolderDrawerItem(
    folder: Folder,
    isSelected: Boolean,
    onFolderClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(folder.name) },
        selected = isSelected,
        onClick = onFolderClick,
        icon = { Icon(Icons.Default.Face, contentDescription = null) },
        badge = {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}
