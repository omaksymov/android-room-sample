package sample.architecture_components.persistence

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object TestUtil {
    @Throws(InterruptedException::class)
    fun <T> getData(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(1, TimeUnit.SECONDS)

        return data[0] as T
    }

}