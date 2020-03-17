package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    // Subject under test
    private lateinit var taskViewModel: TasksViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        taskViewModel = TasksViewModel()
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