package sample.architecture_components.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestUtil {
    public static <T> T getData(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1]; // array is just to bypass 'final' limitations
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T object) {
                data[0] = object;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(1, TimeUnit.SECONDS);
        // noinspection unchecked
        return (T) data[0];
    }

}
