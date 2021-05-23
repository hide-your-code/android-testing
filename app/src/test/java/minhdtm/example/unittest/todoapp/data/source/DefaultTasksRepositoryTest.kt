package minhdtm.example.unittest.todoapp.data.source

import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import minhdtm.example.unittest.todoapp.BaseRepositoryTest
import minhdtm.example.unittest.todoapp.data.Result
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DefaultTasksRepositoryTest : BaseRepositoryTest<DefaultTasksRepository>() {

    override fun setup() {
        super.setup()

        repository = spyk(
            DefaultTasksRepository(taskRemoteDataSource, taskLocalDataSource, mainCoroutineRule.dispatcher),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `get task from remote_request all task from remote`() = mainCoroutineRule.runBlockingTest {
        val exception = Result.Error(Exception())

        coEvery { taskRemoteDataSource.getTasks() } returns exception

        assertThat(repository.getTasks(true), `is`(exception))
    }
}