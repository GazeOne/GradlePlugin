package com.example.myapplication.livedataandviewmodel;

interface BaseViewModel<T> {
    T loadData();
    void clearData();
}
