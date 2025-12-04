package com.heofen.notes.repository

import com.heofen.notes.data.Folder
import com.heofen.notes.data.FolderDao
import kotlinx.coroutines.flow.Flow

class FolderRepository(private val folderDao: FolderDao) {

    fun getAllFolders(): Flow<List<Folder>> = folderDao.getAllFolders()

    suspend fun insertFolder(folder: Folder): Long = folderDao.insert(folder)

    suspend fun updateFolder(folder: Folder) = folderDao.update(folder)

    suspend fun deleteFolder(folder: Folder) = folderDao.delete(folder)
}
