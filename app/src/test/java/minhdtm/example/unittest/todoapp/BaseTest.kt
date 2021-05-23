package minhdtm.example.unittest.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
open class BaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        MockitoAnnotations.openMocks(this)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun for all mock
    }
}