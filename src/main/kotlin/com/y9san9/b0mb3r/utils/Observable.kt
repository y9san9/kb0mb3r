package com.y9san9.b0mb3r.utils


typealias Observer<T> = (T) -> Unit

private val <T> Observable<T>.observers by baseDelegate(mutableListOf<Observer<T>>())
interface Observable<T> {
    fun subscribe(observer: Observer<T>){
        observers.add(observer)
    }
    fun push(item: T){
        observers.forEach { it(item) }
    }
}
