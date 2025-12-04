package com.heofen.notes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heofen.notes.R
import com.heofen.notes.data.Note
import com.heofen.notes.ui.components.NotesList
import com.heofen.notes.ui.components.NotesNavigationDrawer
import com.heofen.notes.ui.dialogs.AddFolderDialog
import com.heofen.notes.ui.dialogs.MoveFolderDialog
import com.heofen.notes.ui.dialogs.NoteOptionsDialog
import com.heofen.notes.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onEditNote: (Note) -> Unit,
    onCreateNote: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showFolderDialog by remember { mutableStateOf(false) }
    var showNoteOptionsDialog by remember { mutableStateOf<Note?>(null) }
    var showMoveFolderDialog by remember { mutableStateOf<Note?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NotesNavigationDrawer(
        drawerState = drawerState,
        folders = uiState.folders,
        selectedFolderId = uiState.selectedFolderId,
        onFolderSelected = { folderId ->
            viewModel.selectFolder(folderId)
            scope.launch { drawerState.close() }
        },
        onAddFolder = { showFolderDialog = true },
        onDeleteFolder = { folder -> viewModel.deleteFolder(folder) }
    ) {
        Scaffold(
            topBar = {
                NotesTopBar(
                    title = getScreenTitle(uiState.selectedFolderId, uiState.folders),
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onCreateNote,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, stringResource(R.string.new_note))
                }
            }
        ) { paddingValues ->
            NotesContent(
                notes = uiState.notes,
                paddingValues = paddingValues,
                onNoteClick = onEditNote,
                onNoteLongClick = { note -> showNoteOptionsDialog = note }
            )
        }
    }

    if (showFolderDialog) {
        AddFolderDialog(
            onDismiss = { showFolderDialog = false },
            onConfirm = { name ->
                viewModel.createFolder(name)
                showFolderDialog = false
            }
        )
    }

    showNoteOptionsDialog?.let { note ->
        NoteOptionsDialog(
            note = note,
            onDismiss = { showNoteOptionsDialog = null },
            onEdit = {
                onEditNote(note)
                showNoteOptionsDialog = null
            },
            onDelete = {
                viewModel.deleteNote(note)
                showNoteOptionsDialog = null
            },
            onMove = {
                showMoveFolderDialog = note
                showNoteOptionsDialog = null
            }
        )
    }

    showMoveFolderDialog?.let { note ->
        MoveFolderDialog(
            folders = uiState.folders,
            currentFolderId = note.folderId,
            onDismiss = { showMoveFolderDialog = null },
            onConfirm = { folderId ->
                viewModel.moveNoteToFolder(note.id, folderId)
                showMoveFolderDialog = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesTopBar(
    title: String,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
private fun NotesContent(
    notes: List<Note>,
    paddingValues: PaddingValues,
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit
) {
    if (notes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_notes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        NotesList(
            notes = notes,
            paddingValues = paddingValues,
            onNoteClick = onNoteClick,
            onNoteLongClick = onNoteLongClick
        )
    }
}

@Composable
private fun getScreenTitle(selectedFolderId: Int?, folders: List<com.heofen.notes.data.Folder>): String {
    return when (selectedFolderId) {
        null -> stringResource(R.string.all_notes)
        -1 -> stringResource(R.string.without_folder)
        else -> folders.find { it.id == selectedFolderId }?.name ?: stringResource(R.string.notes)
    }
}
