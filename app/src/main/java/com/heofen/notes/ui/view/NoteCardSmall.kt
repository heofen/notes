package com.heofen.notes.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heofen.notes.ui.theme.NotesTheme


@Composable
fun NoteCardSmall(
    createTime: String,
    tittle: String? = null,
    text: String? = null,
    editTime: String? = null
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (tittle != null) {
                Text(
                    text = tittle,
                    color = Color.Black,
                    fontSize = 36.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
            if (text != null && tittle != null) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            if (text != null) {
                Text(
                    text = text,
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Create time: $createTime",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                )
                if (editTime != null) {
                    Text(
                        text = "Edit time: $editTime",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun NoteCardSmallFullPreview() {
    NotesTheme {
        NoteCardSmall(
            createTime = "12:48 12.12.25",
            tittle = "Заголовок",
            text = "Нужно сделать приложение для создания заметок заметки должны хранится в бд. Информация в заметках; заголовок, текст заметки, дата создания или изменения этой заметки.Приложение должно обеспечивать создание заметок, редактирование по долгому нажатию на эту заметку, должен быть способ удаления этой заметки и должен быть способ по клику на эту заметку увидеть полностью текст если он длинный и не помещается.(способ lazy- обязателен).В карточке каждой заметки должен выводится заголовок, если он есть.Разделитель между основным текстом и заголовком.рАЗДЕЛИТЕЛЕЙ НЕ должно быть если нет заголовка или текста.Внизу надо показывать дату создания/изменения.",
            editTime = "12:48 12.12.25"
        )
    }
}

@Preview
@Composable
fun NoteCardSmallTextPreview() {
    NotesTheme {
        NoteCardSmall(
            createTime = "12:48 12.12.25",
            text = "Нужно сделать приложение для создания заметок заметки должны хранится в бд. Информация в заметках; заголовок, текст заметки, дата создания или изменения этой заметки.Приложение должно обеспечивать создание заметок, редактирование по долгому нажатию на эту заметку, должен быть способ удаления этой заметки и должен быть способ по клику на эту заметку увидеть полностью текст если он длинный и не помещается.(способ lazy- обязателен).В карточке каждой заметки должен выводится заголовок, если он есть.Разделитель между основным текстом и заголовком.рАЗДЕЛИТЕЛЕЙ НЕ должно быть если нет заголовка или текста.Внизу надо показывать дату создания/изменения.",
            editTime = "12:48 12.12.25"
        )
    }
}

@Preview
@Composable
fun NoteCardSmallTittlePreview() {
    NotesTheme {
        NoteCardSmall(
            createTime = "12:48 12.12.25",
            tittle = "Заголовок",
            editTime = "12:48 12.12.25"
        )
    }
}

@Preview
@Composable
fun NoteCardSmallNotEditPreview() {
    NotesTheme {
        NoteCardSmall(
            createTime = "12:48 12.12.25",
            tittle = "Заголовок",
            text = "Нужно сделать приложение для создания заметок заметки должны хранится в бд. Информация в заметках; заголовок, текст заметки, дата создания или изменения этой заметки.Приложение должно обеспечивать создание заметок, редактирование по долгому нажатию на эту заметку, должен быть способ удаления этой заметки и должен быть способ по клику на эту заметку увидеть полностью текст если он длинный и не помещается.(способ lazy- обязателен).В карточке каждой заметки должен выводится заголовок, если он есть.Разделитель между основным текстом и заголовком.рАЗДЕЛИТЕЛЕЙ НЕ должно быть если нет заголовка или текста.Внизу надо показывать дату создания/изменения."
        )
    }
}