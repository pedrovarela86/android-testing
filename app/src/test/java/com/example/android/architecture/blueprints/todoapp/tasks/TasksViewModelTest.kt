package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TasksViewModelTest {

    // Subject under test
    private lateinit var taskViewModel: TasksViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val taskRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description 1")
        val task2 = Task("Title1", "Description 2", true)
        val task3 = Task("Title1", "Description 3", true)
        taskRepository.addTasks(task1, task2, task3)
        taskViewModel = TasksViewModel(taskRepository)
    }

    @Test
    fun addNewTask_setsNetTaskEvent() {

        // When adding a new task
        taskViewModel.addNewTask()

        // Then the new task event is triggered
        Truth.assertThat(taskViewModel.newTaskEvent.getOrAwaitValue().getContentIfNotHandled()).isNotNull()
    }


    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // When the filter type is ALL_TASK
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // The the "Add task" action is visible
        Truth.assertThat(taskViewModel.tasksAddViewVisible.getOrAwaitValue()).isTrue()
    }
}