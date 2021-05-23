package minhdtm.example.unittest.todoapp.di

import android.content.Context
import androidx.room.Room
import minhdtm.example.unittest.todoapp.data.source.local.TasksDao
import minhdtm.example.unittest.todoapp.data.source.local.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext context: Context
    ): ToDoDatabase = Room.databaseBuilder(context, ToDoDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideTasksDao(toDoDatabase: ToDoDatabase): TasksDao = toDoDatabase.taskDao()

    companion object {
        private const val DATABASE_NAME = "Tasks.db"
    }
}
