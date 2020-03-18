package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking
import java.lang.Exception

@VisibleForTesting
class FakeAndroidTestRepository : TasksRepository {

    private var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()

    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    private var shouldReturnError = false

    fun addTasks(vararg tasks: Task) {
        tasks.forEach { tasksServiceData[it.id] = it }
        runBlocking { refreshTasks() }
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun refreshTasks() {
        observableTasks.postValue(getTasks())
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> =
            Result.Success(tasksServiceData.values.toList())

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        runBlocking { refreshTasks() }
        return observableTasks.map { tasks ->
            when (tasks) {
                is Result.Loading -> Result.Loading
                is Result.Error -> Result.Error(tasks.exception)
                is Result.Success -> {
                    val task = tasks.data.firstOrNull() { it.id == taskId }
                            ?: return@map Result.Error(Exception("Not found"))
                    Result.Success(task)
                }
            }
        }
    }

    override suspend fun clearCompletedTasks() {
        tasksServiceData = tasksServiceData.filterValues { !it.isCompleted } as LinkedHashMap<String, Task>
    }

    override suspend fun deleteAllTasks() {
        tasksServiceData.clear()
        refreshTasks()
    }

    override suspend fun deleteTask(taskId: String) {
        tasksServiceData.remove(taskId)
        refreshTasks()
    }

    override suspend fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, false)
        tasksServiceData[task.id] = activeTask
    }

    override suspend fun refreshTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        throw NotImplementedError()
    }

    override suspend fun saveTask(task: Task) {
        tasksServiceData[task.id] = task
        refreshTasks()
    }

    override suspend fun completeTask(task: Task) {
        throw NotImplementedError()
    }

    override suspend fun completeTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun activateTask(taskId: String) {
        throw NotImplementedError()
    }


}