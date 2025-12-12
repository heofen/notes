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
                    title = "Заголовок",
                    text = "Заметка заметка",
                    createTime = System.currentTimeMillis()
                ),
                Note(
                    id = 2,
                    title = "",
                    text = "ТЗ \nНужно сделать приложение для создания заметок заметки должны хранится в бд. Информация в заметках; заголовок, текст заметки, дата создания или изменения этой заметки.Приложение должно обеспечивать создание заметок, редактирование по долгому нажатию на эту заметку, должен быть способ удаления этой заметки и должен быть способ по клику на эту заметку увидеть полностью текст если он длинный и не помещается.(способ lazy- обязателен).В карточке каждой заметки должен выводится заголовок, если он есть.Разделитель между основным текстом и заголовком.рАЗДЕЛИТЕЛЕЙ НЕ должно быть если нет заголовка или текста.Внизу надо показывать дату создания/изменения.\n",
                    createTime = System.currentTimeMillis() - 86400000
                ),
                Note(
                    id = 3,
                    title = "Тут оооооооооооооооооооооооооооооооооооооооооооооооооооооооочень много текста",
                    text = null,
                    createTime = System.currentTimeMillis() - 172800000
                )
            ),
            paddingValues = PaddingValues(0.dp),
            onNoteClick = {},
            onNoteLongClick = {}
        )
    }
}
