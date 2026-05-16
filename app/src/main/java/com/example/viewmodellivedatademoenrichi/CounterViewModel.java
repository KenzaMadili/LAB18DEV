package com.example.viewmodellivedatademoenrichi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {

    private final MutableLiveData<Integer> count = new MutableLiveData<>(0);

    public LiveData<Integer> getCount() {
        return count;
    }

    public void increment() {
        Integer value = count.getValue();
        if (value != null) {
            count.setValue(value + 1);
        }
    }

    public void decrement() {
        Integer value = count.getValue();
        if (value != null) {
            count.setValue(value - 1);
        }
    }

    public void reset() {
        count.setValue(0);
    }

    // ✅ Thread safe version (IMPORTANT)
    public void incrementFromThread() {
        new Thread(() -> {
            Integer value = count.getValue();
            if (value != null) {
                count.postValue(value + 1);
            }
        }).start();
    }
}