package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    lateinit var repository: TaskRepository

    @Before
    fun createRepository() {
        repository = FakeAndroidTestRepository()
    }

    @Test
    fun activateTaskDetails_DisplayedInUI() = runBlockingTest {

        // Given - Add active (incomplete) task to the DB
        val activeTask = Task("Title", "Description", false)
        repository.saveTask(activeTask)

        // When - Details fragment launched to display the task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        //Then
        onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text)).check(matches(withText("Title")))
        onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text)).check(matches(withText("Description")))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))

        delay(2000)
    }
}