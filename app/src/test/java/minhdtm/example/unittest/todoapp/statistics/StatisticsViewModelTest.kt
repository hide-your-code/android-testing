package minhdtm.example.unittest.todoapp.statistics

import kotlinx.coroutines.ExperimentalCoroutinesApi
import minhdtm.example.unittest.todoapp.BaseViewModelTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StatisticsViewModelTest : BaseViewModelTest<StatisticsViewModel>() {

    @Test
    fun `test`() {
        mainCoroutineRule.pauseDispatcher()
    }
}