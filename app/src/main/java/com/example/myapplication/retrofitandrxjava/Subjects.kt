package com.example.myapplication.retrofitandrxjava

class Subjects constructor() {

    var title = ""
    var year = ""
    var id = ""

    constructor(title: String, year: String, id: String) : this() {
        this.title = title
        this.year = year
        this.id = id
    }

}