package com.heofen.notes.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heofen.notes.data.Note
import com.heofen.notes.ui.theme.NotesTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (!note.title.isNullOrBlank()) {
                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (!note.title.isNullOrBlank() && !note.text.isNullOrBlank()) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.3f)
                )
            }

            if (!note.text.isNullOrBlank()) {
                Text(
                    text = note.text,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                text = note.getDisplayTime(),
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Preview
@Composable
fun NoteCardPreview() {
    NotesTheme {
        NoteCard(
            note = Note(
                id = 1,
                title = "Заголовок",
                text = "ТЗ \n" +
                        "Нужно сделать приложение для создания заметок заметки должны хранится в бд. Информация в заметках; заголовок, текст заметки, дата создания или изменения этой заметки.Приложение должно обеспечивать создание заметок, редактирование по долгому нажатию на эту заметку, должен быть способ удаления этой заметки и должен быть способ по клику на эту заметку увидеть полностью текст если он длинный и не помещается.(способ lazy- обязателен).В карточке каждой заметки должен выводится заголовок, если он есть.Разделитель между основным текстом и заголовком.рАЗДЕЛИТЕЛЕЙ НЕ должно быть если нет заголовка или текста.Внизу надо показывать дату создания/изменения.\n" +
                        "\n" +
                        "\n" +
                        "Доп.задание:\n" +
                        "Заметки все нужно распределять по папкам.Пользователь должен группировать эти папки и относить заметки к той или иной папке.В том числе иметь в возможность перекладывать заметки в ту или иную папку.Добавление возможности удаление/добавления папок",
                createTime = System.currentTimeMillis(),
                editTime = null
            ),
            onClick = {},
            onLongClick = {}
        )
    }
}
