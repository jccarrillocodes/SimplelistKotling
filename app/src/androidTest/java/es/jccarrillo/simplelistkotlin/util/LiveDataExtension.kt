package es.jccarrillo.simplelistkotlin.util


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicInteger


fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 5,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    discardFirstData: Boolean = false
): T {
    var data: T? = null
    var alreadyDiscarded = false
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            if (o is List<*>) {
                if ((o as List<*>).size == 0)
                    return
            }

            if (discardFirstData) {
                if (!alreadyDiscarded) {
                    alreadyDiscarded = true
                    return
                }
            }

            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> LiveData<T>.checkValues(
    time: Long = 10,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    values: List<*>
) {
    val latch = CountDownLatch(values.size)
    val watchingItem = AtomicInteger(0)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            if (o?.equals(values[watchingItem.get()]) == true) {
                latch.countDown()
                watchingItem.incrementAndGet()

                if (latch.count == 0L) {
                    this@checkValues.removeObserver(this)
                }
            }
        }
    }

    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }
    this.removeObserver(observer)
}