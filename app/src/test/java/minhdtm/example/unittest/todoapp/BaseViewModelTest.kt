package minhdtm.example.unittest.todoapp

import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import minhdtm.example.unittest.todoapp.data.source.DefaultTasksRepository

@ExperimentalCoroutinesApi
open class BaseViewModelTest<T : Any> : BaseTest() {

    protected lateinit var viewModel: T

    @MockK
    protected lateinit var repository: DefaultTasksRepository
}
