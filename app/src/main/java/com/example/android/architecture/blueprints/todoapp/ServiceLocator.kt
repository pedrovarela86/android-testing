package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

object ServiceLocator {

    private var database: ToDoDatabase? = null

    @Volatile
    var taskRepository: TaskRepository? = null


    fun provideTaskRepository(context: Context): TaskRepository {
        synchronized(this) {
            return taskRepository ?: createTaskRepository(context)
        }
    }

    private fun createTaskRepository(context: Context): TaskRepository {
        val newTaskRepository = DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        taskRepository = newTaskRepository
        return newTaskRepository
    }

    private fun createTaskLocalDataSource(context: Context): TasksLocalDataSource {
        val database = database ?: createDataBase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDataBase(context: Context): ToDoDatabase {
        val result = Room.databaseBuilder(context.applicationContext, ToDoDatabase::class.java, "Task.db").build()
        database = result
        return result
    }
}