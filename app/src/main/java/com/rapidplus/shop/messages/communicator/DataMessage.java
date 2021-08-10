package com.rapidplus.shop.messages.communicator;

public interface DataMessage<T> {

    void onReceiveData(T t);
}
