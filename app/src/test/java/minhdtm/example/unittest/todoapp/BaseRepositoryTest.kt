package minhdtm.example.unittest.todoapp

import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import minhdtm.example.unittest.todoapp.data.source.local.TasksLocalDataSource
import minhdtm.example.unittest.todoapp.data.source.remote.TasksRemoteDataSource

@ExperimentalCoroutinesApi
open class BaseRepositoryTest<T : Any> : BaseTest() {

    protected lateinit var repository: T

    @MockK
    protected lateinit var taskLocalDataSource: TasksLocalDataSource

    @MockK
    protected lateinit var taskRemoteDataSource: TasksRemoteDataSource
}