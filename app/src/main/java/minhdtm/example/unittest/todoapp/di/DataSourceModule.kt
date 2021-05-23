package minhdtm.example.unittest.todoapp.di

import minhdtm.example.unittest.todoapp.data.source.TasksDataSource
import minhdtm.example.unittest.todoapp.data.source.local.TasksDao
import minhdtm.example.unittest.todoapp.data.source.local.TasksLocalDataSource
import minhdtm.example.unittest.todoapp.data.source.remote.TasksRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    @Named("remote")
    fun provideTaskRemoteDataSource(): TasksDataSource = TasksRemoteDataSource()

    @Provides
    @Singleton
    @Named("local")
    fun provideTaskLocalDataSource(
        tasksDao: TasksDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TasksDataSource = TasksLocalDataSource(tasksDao, ioDispatcher)
}
