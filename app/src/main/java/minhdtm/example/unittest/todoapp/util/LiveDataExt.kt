package minhdtm.example.unittest.todoapp.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import minhdtm.example.unittest.todoapp.Event

fun <T> LiveData<Event<T>>.eventObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner) { it?.getContentIfNotHandled()?.let(observer) }
}

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner) { it?.let(observer) }
}
