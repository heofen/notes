package com.heofen.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.heofen.notes.ui.EditNoteActivity
import com.heofen.notes.ui.screens.NotesScreen
import com.heofen.notes.ui.theme.NotesTheme
import com.heofen.notes.viewmodel.NotesViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: NotesViewModel by viewModels()

    private val editNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val noteId = data?.getIntExtra("NOTE_ID", -1) ?: -1
            val title = data?.getStringExtra("RESULT_TITLE") ?: ""
            val text = data?.getStringExtra("RESULT_TEXT") ?: ""

            if (noteId == -1) {
                viewModel.createNote(title, text)
            } else {
                viewModel.updateNote(noteId, title, text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                NotesScreen(
                    viewModel = viewModel,
                    onEditNote = { note ->
                        val intent = Intent(this, EditNoteActivity::class.java).apply {
                            putExtra("NOTE_ID", note.id)
                            putExtra("NOTE_TITLE", note.title ?: "")
                            putExtra("NOTE_TEXT", note.text ?: "")
                        }
                        editNoteLauncher.launch(intent)
                    },
                    onCreateNote = {
                        val intent = Intent(this, EditNoteActivity::class.java).apply {
                            putExtra("NOTE_ID", -1)
                        }
                        editNoteLauncher.launch(intent)
                    }
                )
            }
        }
    }
}
