package com.heofen.notes.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heofen.notes.data.Note
import com.heofen.notes.ui.theme.NotesTheme
import com.heofen.notes.ui.view.NoteCard

@Composable
fun NotesList(
    notes: List<Note>,
    paddingValues: PaddingValues,
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            NoteCard(
                note = note,
                onClick = { onNoteClick(note) },
                onLongClick = { onNoteLongClick(note) }
            )
        }
    }
}

@Preview
@Composable
fun NotesListPreview() {
    NotesTheme {
        NotesList(
            notes = listOf(
                Note(
                    id = 1,
                    title = "Первая заметка",
                    text = "Текст первой заметки",
                    createTime = System.currentTimeMillis()
                ),
                Note(
                    id = 2,
                    title = "Вторая заметка",
                    text = "Текст второй заметки с более длинным содержимым",
                    createTime = System.currentTimeMillis() - 86400000
                ),
                Note(
                    id = 3,
                    title = null,
                    text = "Заметка без заголовка",
                    createTime = System.currentTimeMillis() - 172800000
                )
            ),
            paddingValues = PaddingValues(0.dp),
            onNoteClick = {},
            onNoteLongClick = {}
        )
    }
}
