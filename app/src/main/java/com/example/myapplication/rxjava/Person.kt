package com.example.myapplication.rxjava


class Person(var name: String?, planList: List<Plan>) {
    var planList: List<Plan> = ArrayList()

    init {
        this.planList = planList
    }

}
