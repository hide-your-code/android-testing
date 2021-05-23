package minhdtm.example.unittest.todoapp.tasks

import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import minhdtm.example.unittest.todoapp.BaseViewModelTest
import minhdtm.example.unittest.todoapp.R
import minhdtm.example.unittest.todoapp.data.Task
import minhdtm.example.unittest.todoapp.ext.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TasksViewModelTest : BaseViewModelTest<TasksViewModel>() {

    override fun setup() {
        super.setup()
        viewModel = spyk(TasksViewModel(repository), recordPrivateCalls = true)
    }

    @Test
    fun `add new task_set new task event`() {
        viewModel.addNewTask()

        val value = viewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun `completed task`() = mainCoroutineRule.runBlockingTest {
        val task = Task("title", "description", false)

        viewModel.completeTask(task, false)

        val value = viewModel.snackbarText.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), `is`(R.string.task_marked_active))

        // check method was called
        coVerify(exactly = 1) {
            repository.activateTask(task)
        }

        // check method was not called
        coVerify(inverse = true) {
            repository.completeTask(task)
        }
    }
}
