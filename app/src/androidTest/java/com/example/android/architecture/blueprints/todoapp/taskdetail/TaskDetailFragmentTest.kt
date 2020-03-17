package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    @Test
    fun activateTaskDetails_DisplayedInUI() {
        // Given - Add active (incomplete) task to the DB
        val activeTask = Task("Title", "Description", false)

        // When - Details fragment launched to display the task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()

        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        Thread.sleep(2000)
    }

}