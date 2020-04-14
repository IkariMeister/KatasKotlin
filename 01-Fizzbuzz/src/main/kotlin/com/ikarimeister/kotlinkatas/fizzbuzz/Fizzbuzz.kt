package com.ikarimeister.kotlinkatas.fizzbuzz

fun fizzbuzz(value: Int): String = when {
    value.is15Multiple() -> "FizzBuzz"
    value.is3Multiple() -> "Fizz"
    value.is5Multiple() -> "Buzz"
    else -> value.toString()
}

fun Int.is5Multiple() = this % 5 == 0

fun Int.is3Multiple() = this % 3 == 0

fun Int.is15Multiple() = this.is3Multiple() && this.is5Multiple()
