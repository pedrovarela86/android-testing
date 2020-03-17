package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(
        private val taskRepository: TaskRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = (TasksViewModel(taskRepository) as T)
}