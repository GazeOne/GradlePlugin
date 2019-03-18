package com.example.myapplication

class Person(var name: String?, planList: List<Plan>) {
    var planList: List<Plan> = ArrayList()

    init {
        this.planList = planList
    }

}
