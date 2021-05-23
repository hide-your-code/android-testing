package minhdtm.example.unittest.todoapp.statistics

import minhdtm.example.unittest.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class StatisticsUtilsTest {

    private val taskSuccess = Task("title", "description", true)
    private val taskActive = Task("title", "description", false)

    @Test
    fun `active and completed_list pass null`() {
        val list: List<Task>? = null

        val result = getActiveAndCompletedStats(list)

        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun `active and completed_list pass empty`() {
        val list = emptyList<Task>()

        val result = getActiveAndCompletedStats(list)

        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun `active and completed_2 active and 2 completed`() {
        val list = listOf(taskActive, taskActive, taskSuccess, taskSuccess)

        val result = getActiveAndCompletedStats(list)

        assertThat(result.activeTasksPercent, `is`(50f))
        assertThat(result.completedTasksPercent, `is`(50f))
    }

    @Test
    fun `active and complete_0 active and 2 completed`() {
        val list = listOf(taskSuccess, taskSuccess)

        val result = getActiveAndCompletedStats(list)

        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))
    }

    @Test
    fun `active and complete_2 active and 0 completed`() {
        val list = listOf(taskActive, taskActive)

        val result = getActiveAndCompletedStats(list)

        assertThat(result.activeTasksPercent, `is`(100f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }
}
