package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsKtTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnHundredZero() {
        // Given
        val tasks = listOf(Task("title", "desc", isCompleted = false))
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        Truth.assertThat(result.activeTasksPercent).isEqualTo(100)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(0)
    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {
        // Given
        val tasks = listOf(Task("title", "desc", isCompleted = true))
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        Truth.assertThat(result.activeTasksPercent).isEqualTo(0)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(100)
    }


    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {

        //Given
        val tasks = listOf(
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = false),
                Task("title", "desc", isCompleted = false)
        )

        //Call your function
        val result = getActiveAndCompletedStats(tasks)
        //Check the result
        Truth.assertThat(result.activeTasksPercent).isEqualTo(40)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(60)
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // Given
        val result = getActiveAndCompletedStats(null)

        // Check
        Truth.assertThat(result.activeTasksPercent).isEqualTo(0)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(0)
    }

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {
        // Given
        val tasks = listOf(Task("title", "desc", isCompleted = false))

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Check
        Truth.assertThat(result.activeTasksPercent).isEqualTo(100)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(0)
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        // Given
        val tasks = emptyList<Task>()
        // When
        val result = getActiveAndCompletedStats(tasks)
        // Then
        Truth.assertThat(result.activeTasksPercent).isEqualTo(0)
        Truth.assertThat(result.completedTasksPercent).isEqualTo(0)
    }
}