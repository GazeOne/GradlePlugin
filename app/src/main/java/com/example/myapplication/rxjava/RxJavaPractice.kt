package com.example.myapplication.rxjava

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import io.reactivex.observables.GroupedObservable
import io.reactivex.functions.*
import io.reactivex.functions.Function


class RxJavaPractice constructor(context: Context) {

    private var mContext: Context? = null

    init {
        mContext = context
    }

    companion object {
        const val TAG = "RxPractice"
    }

    //创建被观察者
    public var observable = Observable.create(ObservableOnSubscribe<Int> { e ->
        Log.i(TAG, "=========================currentThread name: " + Thread.currentThread().name)
        e.onNext(1)
        e.onNext(2)
        e.onNext(3)
        e.onComplete()
    })

    //创建观察者
    public var observer = object : Observer<Int> {
        override fun onNext(t: Int) {
            Log.i(TAG, "======================onNext$t")
        }

        override fun onSubscribe(d: Disposable) {
            Log.i(TAG, "======================onSubscribe")
        }

        override fun onError(e: Throwable) {
        }

        override fun onComplete() {
        }
    }

    //链式调用
    public fun linkUse() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            Log.i(TAG, "=========================currentThread nameLinkUse: " + Thread.currentThread().name)
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onComplete()
        })
            .subscribe(object : Observer<Int> {
                override fun onNext(t: Int) {
                    Log.i(TAG, "======================onNextLinkUse$t")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //just操作符使用,并发发送事件，且不超过10个
    public fun justUse() {
        Observable.just(1, 2, 3)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "=================onSubscribeJust")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "=================onNextJust $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //这个方法和 just() 类似，只不过 fromArray 可以传入多于10个的变量，并且可以传入一个数组。
    public fun fromArrayUse() {
        val array = arrayOf(1, 2, 3, 4)
        Observable.fromArray(*array)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "=================onSubscribe")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "=================onNext + $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }

    //这里的 Callable 是 java.util.concurrent 中的 Callable，
    // Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的。
    public fun fromCallableUse() {
        Observable.fromCallable { 1 }.subscribe { p0 -> Log.i(TAG, "================acceptfromCallable $p0"); }

//        Observable.fromCallable(object : Callable<Int> {
//            override fun call(): Int {
//                return 1
//            }
//        }).subscribe(object : Consumer<Int> {
//            override fun accept(p0: Int) {
//                Log.i(TAG, "================accept $p0"); }
//        })
    }

    //参数中的 Future 是 java.util.concurrent 中的 Future，
    // Future 的作用是增加了 cancel() 等方法操作 Callable，它可以通过 get() 方法来获取 Callable 返回的值
    public fun fromFeatrueUse() {
        val futureTask = FutureTask(Callable {
            Log.i(TAG, "CallableDemo is Running")
            "返回结果"
        })

        Observable.fromFuture(futureTask)
            .doOnSubscribe { futureTask.run() }
            .subscribe { s -> Log.i(TAG, "================acceptfromFeatrue $s") }
    }

    //直接发送一个 List 集合数据给观察者
    public fun fromIterableUse() {
        val list = mutableListOf<Int>().apply {
            add(0)
            add(1)
            add(2)
            add(3)
        }

        Observable.fromIterable<Int>(list)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "=================onSubscribefromIterable")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "=================onNextfromIterable$integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //这个方法的作用就是直到被观察者被订阅后才会创建被观察者
    //因为 defer() 只有观察者订阅的时候才会创建新的被观察者，所以每订阅一次就会打印一次，并且都是打印 i 最新的值。
    public fun deferUse() {

        var i: Int? = 100
        val observable = Observable.defer { Observable.just(i!!) }

        i = 200

        val observer = object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(integer: Int) {
                Log.i(TAG, "================onNextdefer $integer")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }
        }
        observable.subscribe(observer)
        i = 300
        observable.subscribe(observer)
    }

    //当到指定时间后就会发送一个 0L 的值给观察者
    public fun timerUse() {
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(aLong: Long) {
                    Log.i(TAG, "===============onNexttimer $aLong")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字
    public fun intervalUse() {
        Observable.interval(4, TimeUnit.SECONDS)
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "==============onSubscribeinterva ")
                }

                override fun onNext(aLong: Long) {
                    Log.i(TAG, "==============onNextinterva $aLong")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }

    //同时发送一定范围的事件序列。
    public fun rangeUse() {
        Observable.range(2, 5)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "==============onSubscriberange ")
                }

                override fun onNext(aLong: Int) {
                    Log.i(TAG, "==============onNextrange $aLong")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }

    //操作符map,将int转成string
    fun mapUse() {
        Observable.just(1, 2, 3)
            .map { it.toString() + "mapuse" }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "===================onSubscribemap")
                }

                override fun onNext(s: String) {
                    Log.i(TAG, "===================onNextmap $s")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //flatMap() 其实与 map() 类似，但是 flatMap() 返回的是一个 Observerable。
    fun flatmapUse() {
        val personList = mutableListOf<Person>().apply {
            add(Person("first", ArrayList<Plan>().apply {
                add(Plan("1-1", "plan1-1"))
                add(Plan("1-2", "plane1-2"))
            }))
            add(
                Person(
                    "second",
                    ArrayList<Plan>().apply {
                        add(
                            Plan(
                                "2-1",
                                "plan2-1"
                            )
                        )
                    })
            )

        }

        Observable.fromIterable(personList)
            .flatMap { person -> Observable.fromIterable(person.planList) }
            .flatMap { plan -> Observable.just(plan.content).delay(10, TimeUnit.SECONDS) }  //加个delay用来验证flatmap是无序的
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(s: String) {
                    Log.i(TAG, "==================actionflatmap: $s")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //concatmap和flatmap类似，只是concatmap是有序的
    fun concatmapUse() {
        val personList = mutableListOf<Person>().apply {
            add(Person("first", ArrayList<Plan>().apply {
                add(Plan("1-1", "plan1-1"))
                add(Plan("1-2", "plane1-2"))
            }))
            add(
                Person(
                    "second",
                    ArrayList<Plan>().apply {
                        add(
                            Plan(
                                "2-1",
                                "plan2-1"
                            )
                        )
                    })
            )

        }

        Observable.fromIterable(personList)
            .concatMap { person -> Observable.fromIterable(person.planList) }
            .concatMap { plan -> Observable.just(plan.content).delay(10, TimeUnit.SECONDS) }  //加个delay用来验证flatmap是无序的
            .subscribe(object : Observer<String?> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(s: String) {
                    Log.i(TAG, "==================actionconcatMap: $s")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //buffer 有两个参数，一个是 count，另一个 skip。
    // count 缓冲区元素的数量，skip 就代表缓冲区满了之后，发送下一次事件序列的时候要跳过多少元素
    fun bufferUse() {
        Observable.just(1, 2, 3, 4, 5)
            .buffer(2, 1)
            .subscribe(object : Observer<List<Int>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(integers: List<Int>) {
                    Log.i(TAG, "================缓冲区大小： " + integers.size)
                    for (i in integers) {
                        Log.i(TAG, "================元素： $i")
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

    }

    //将发送的数据进行分组，每个分组都会返回一个被观察者
    fun groupbyUse() {
        Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
            .groupBy { integer -> integer % 3 }
            .subscribe(object : Observer<GroupedObservable<Int, Int>> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "====================onSubscribe ")
                }

                override fun onNext(integerIntegerGroupedObservable: GroupedObservable<Int, Int>) {
                    Log.i(TAG, "====================onNext ")
                    integerIntegerGroupedObservable.subscribe(object : Observer<Int> {
                        override fun onSubscribe(d: Disposable) {
                            Log.i(TAG, "====================GroupedObservable onSubscribe ")
                        }

                        override fun onNext(integer: Int) {
                            Log.i(
                                TAG,
                                "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.key + " value: " + integer
                            )
                        }

                        override fun onError(e: Throwable) {
                        }

                        override fun onComplete() {
                        }
                    })
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //将数据以一定的逻辑聚合起来
    fun scanUse() {
        Observable.just(1, 2, 3, 4, 5)
            .scan(object : BiFunction<Int, Int, Int> {
                override fun apply(t1: Int, t2: Int): Int {
                    Log.i(TAG, "====================acceptscan $t1+$t2")
                    return t1 + t2
                }
            })
            .subscribe(object : Consumer<Int> {
                override fun accept(integer: Int) {
                    Log.i(TAG, "====================acceptscan $integer")
                }
            })

    }

    //发送指定数量的事件时，就将这些事件分为一组。
    // window 中的 count 的参数就是代表指定的数量，例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。
    fun windowUse() {
        Observable.just(1, 2, 3, 4, 5)
            .window(2)
            .subscribe(object : Observer<Observable<Int>> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "=====================onSubscribe ")
                }

                override fun onNext(integerObservable: Observable<Int>) {
                    integerObservable.subscribe(object : Observer<Int> {
                        override fun onSubscribe(d: Disposable) {
                            Log.i(TAG, "=====================integerObservable onSubscribe ")
                        }

                        override fun onNext(integer: Int) {
                            Log.i(TAG, "=====================integerObservable onNext$integer")
                        }

                        override fun onError(e: Throwable) {
                        }

                        override fun onComplete() {
                        }
                    })
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。
    fun concatUse() {
        Observable.concat(
            Observable.just(1, 2),
            Observable.just(3, 4),
            Observable.just(5, 6),
            Observable.just(7, 8)
        )
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "================onNextconcat$integer ")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者
    fun concatarrayUse() {
        Observable.concatArray(
            Observable.just(1, 2),
            Observable.just(3, 4),
            Observable.just(5, 6),
            Observable.just(7, 8),
            Observable.just(9, 10)
        )
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(integer: Int) {
                    Log.d(TAG, "================onNextconcatarray $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件
    fun mergeUse() {
        Observable.merge(
            Observable.interval(1, TimeUnit.SECONDS).map(object : Function<Long, String> {
                override fun apply(aLong: Long): String {
                    return "A+ $aLong"
                }
            }),
            Observable.interval(1, TimeUnit.SECONDS).map(object : Function<Long, String> {
                override fun apply(aLong: Long): String {
                    return "B+ $aLong"
                }
            })
        )
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(s: String) {
                    Log.i(TAG, "=====================onNextmerge $s")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样
    //代码中有两个 Observable，第一个发送事件的数量为5个，第二个发送事件的数量为6个
    //最终接收到的事件数量是5，那么为什么第二个 Observable 没有发送第6个事件呢？
    // 因为在这之前第一个 Observable 已经发送了 onComplete 事件，所以第二个 Observable 不会再发送事件。
    fun zipUse() {
        Observable.zip(
            Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                .map(object : Function<Long, String> {
                    override fun apply(aLong: Long): String {
                        val s1 = "A$aLong"
                        Log.i(TAG, "===================A 发送的事件 $s1")
                        return s1
                    }
                }),
            Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                .map(object : Function<Long, String> {
                    override fun apply(aLong: Long): String {
                        val s2 = "B$aLong"
                        Log.i(TAG, "===================B 发送的事件 $s2")
                        return s2
                    }
                }),
            BiFunction<String, String, String> { s, s2 -> s + s2 })
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(s: String) {
                    Log.i(TAG, "===================onNextzip $s")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，
    // 区别在于 scan() 每处理一次数据就会将事件发送给观察者，而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。
    fun reduceUse() {
        Observable.just(0, 1, 2, 3)
            .reduce { integer, integer2 ->
                val res = integer + integer2
                Log.i(TAG, "====================integer1reduce $integer")
                Log.i(TAG, "====================integer2reduce $integer2")
                Log.i(TAG, "====================resreduce $res")
                res
            }
            .subscribe { integer -> Log.i(TAG, "==================acceptreduce $integer") }

    }

    //将数据收集到数据结构当中
    fun collectUse() {
        Observable.just(1, 2, 3, 4)
            .collect(
                Callable<ArrayList<Int>> { ArrayList() },
                object : BiConsumer<ArrayList<Int>, Int> {
                    @Throws(Exception::class)
                    override fun accept(integers: ArrayList<Int>, integer: Int) {
                        integers.add(integer)
                    }
                })
            .subscribe { integers -> Log.i(TAG, "===============acceptcollect $integers") }

    }

    //在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。
    fun startAndstartwithUse() {
        Observable.just(5, 6, 7)
            .startWithArray(2, 3, 4)
            .startWith(1)
            .subscribe { integer -> Log.i(TAG, "================accept $integer") }

    }

    //返回被观察者发送事件的数量
    fun countUse() {
        Observable.just(1, 2, 3)
            .count()
            .subscribe { count -> Log.i(TAG, "============count$count") }
    }

    //延迟一段事件发送事件
    fun delayUse() {
        Observable.just(1, 2, 3)
            .delay(2, TimeUnit.SECONDS)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "=======================onSubscribe")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "=======================onNext $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    Log.i(TAG, "=======================onSubscribe")
                }
            })
    }

    //Observable 每发送一件事件之前都会先回调这个方法
    fun dooneachUse() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            //      e.onError(new NumberFormatException());
            e.onComplete()
        })
            .doOnEach { integerNotification -> Log.i(TAG, "==================doOnEach " + integerNotification.value) }
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "==================onSubscribe ")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "==================onNextdooneach $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //Observable 每发送 onNext() 之前都会先回调这个方法
    fun doonnextUse() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onComplete()
        })
            .doOnNext { integer -> Log.d(TAG, "==================doOnNext " + integer!!) }
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "==================onNextdoonnext $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //doAfterNext，Observable 每发送 onNext() 之后都会回调这个方法。略


    //Observable 每发送 onComplete() 之前都会回调这个方法。
    fun dooncompleteUse() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onComplete()
        })
            .doOnComplete(object : Action {
                @Throws(Exception::class)
                override fun run() {
                    Log.i(TAG, "==================doOnComplete ")
                }
            })
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "==================onNextdooncomplete $integer")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //doOnError() Observable 每发送 onError() 之前都会回调这个方法,略
    //doOnSubscribe() Observable 每发送 onSubscribe() 之前都会回调这个方法,略
    //doOnDispose() 当调用 Disposable 的 dispose() 之后回调该方法,略
    //doOnTerminate() & doAfterTerminate() doOnTerminate 是在 onError 或者 onComplete 发送之前回调，而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调
    //doFinally() 在所有事件发送完毕之后回调该方法,
    // doFinally() 和 doAfterTerminate() 区别？区别就是在于取消订阅，如果取消订阅之后 doAfterTerminate() 就不会被回调，而 doFinally() 无论怎么样都会被回调，且都会在事件序列的最后


    fun doonsomething() {
        Observable.create(ObservableOnSubscribe<Int> { emitter ->
            run {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
                emitter.onComplete()
            }
        }).doFinally { Log.i(TAG, "doFinally") }
            .doAfterTerminate { Log.i(TAG, "doAfterTerminate") }
            .doOnDispose { Log.i(TAG, "doOnDispose") }
            .subscribe(object : Observer<Int> {
                private var d: Disposable? = null

                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "==================onSubscribe ")
                    this.d = d; }

                override fun onNext(t: Int) {
                    Log.i(TAG, "==================onNext $t")
                    d?.dispose(); }

                override fun onError(e: Throwable) {
                }
            })
    }


    //如果出现错误事件，则会重新发送所有事件序列。times 是代表重新发的次数
    fun retryUse() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onError(Exception("404"))
        })
            .retry(2)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "==================onSubscribe ")
                }

                override fun onNext(integer: Int) {
                    Log.i(TAG, "==================onNext + $integer")
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, "==================onerrorretry + ")
                }

                override fun onComplete() {
                }
            })
    }

    //retryUntil() 出现错误事件之后，可以通过此方法判断是否继续发送事件
    //repeat() 重复发送被观察者的事件，times 为发送次数

    //subscribeOn() 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效
    //observeOn() 指定观察者的线程，每指定一次就会生效一次


    //filter() 通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送
    fun filterUse() {
        Observable.just(1, 2, 3)
            .filter { predicate -> predicate > 2 }
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    Log.i(TAG, "==================onNextfilter$t")
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    //ofType() 可以过滤符合该类型事件 Observable.just(1, 2, 3, "chan", "zhide").ofType(Integer.class)
    //skip() 跳过正序某些事件，count 代表跳过事件的数量
    //distinct() 过滤事件序列中的重复事件
    //distinctUntilChanged() 过滤掉连续重复的事件
    //take() 控制观察者接收的事件的数量,参数为数量

    //debounce() 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者,结合RxBind的RxView可以用来防止重复点击

    //firstElement() && lastElement() firstElement() 取事件序列的第一个元素，lastElement() 取事件序列的最后一个元素
    //elementAt() & elementAtOrError() elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件序列的总数的话就不会出现任何结果。
    // 这种情况下，你想发出异常信息的话就用 elementAtOrError()

    //all() 判断事件序列是否全部满足某个事件，如果都满足则返回 true，反之则返回 false
    fun allUse() {
        Observable.just(1, 2, 3, 4)
            .all(object : Predicate<Int> {
                @Throws(Exception::class)
                override fun test(integer: Int): Boolean {
                    return integer < 5
                }
            })
            .subscribe(object : Consumer<Boolean> {
                @Throws(Exception::class)
                override fun accept(aBoolean: Boolean?) {
                    Log.i(TAG, "==================aBoolean " + aBoolean!!)
                }
            })
    }

    //takeWhile() 可以设置条件，当某个数据满足条件时就会发送该数据，反之则不发送,感觉和filter相似
    //skipWhile() 可以设置条件，当某个数据满足条件时不发送该数据，反之则发送
    //takeUntil() 可以设置条件，当事件满足此条件时，下一次的事件就不会被发送了


    //当 skipUntil() 中的 Observable 发送事件了，原来的 Observable 才会发送事件给观察者。
    fun skipuntilUse() {
        Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS)
            .skipUntil(Observable.intervalRange(6, 5, 3, 1, TimeUnit.SECONDS))
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "========================onSubscribeskipuntil ")
                }

                override fun onNext(along: Long) {
                    Log.i(TAG, "========================onNextskipuntil $along")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
    }

    //sequenceEqual() 判断两个 Observable 发送的事件是否相同
    //contains() 判断事件序列中是否含有某个元素，如果有则返回 true，如果没有则返回 false
    //isEmpty() 判断事件序列是否为空

    //amb() 要传入一个 Observable 集合，但是只会发送最先发送事件的 Observable 中的事件，其余 Observable 将会被丢弃
    fun ambUse() {
        val list = mutableListOf<Observable<Long>>().apply {
            add(Observable.intervalRange(1, 5, 2, 1, TimeUnit.SECONDS))
            add(Observable.intervalRange(6, 5, 0, 1, TimeUnit.SECONDS))
        }

        Observable.amb(list)
            .subscribe(object : Consumer<Long> {
                @Throws(Exception::class)
                override fun accept(aLong: Long) {
                    Log.i(TAG, "========================aLong $aLong")
                }
            })
    }

    //defaultIfEmpty() 如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值
    fun defaultifemptyUse() {
        Observable.create(ObservableOnSubscribe<Int> { e -> e.onComplete() })
            .defaultIfEmpty(666)
            .subscribe(object : Consumer<Int> {
                @Throws(Exception::class)
                override fun accept(integer: Int?) {
                    Log.i(TAG, "========================onNext + $integer")
                }
            })
    }
}

