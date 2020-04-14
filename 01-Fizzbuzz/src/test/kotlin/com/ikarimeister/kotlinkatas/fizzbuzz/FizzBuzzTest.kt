package com.ikarimeister.kotlinkatas.fizzbuzz

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class FizzBuzzTest : StringSpec() {
    init {
        "Given a 3 When Fizzbuzz is called should return Fizz" {
            val input = 3
            val actual = fizzbuzz(input)
            actual shouldBe "Fizz"
        }

        "Given a 5 When Fizzbuzz is called Then Return Buzz" {
            val input = 5
            val actual = fizzbuzz(input)
            actual shouldBe "Buzz"
        }

        "Given A 15 When Fizzbuzz is called Then Return FizzBuzz" {
            val input = 15
            val actual = fizzbuzz(input)
            actual shouldBe "FizzBuzz"
        }

        "Given A 1 When Fizzbuzz is called Then Return 1" {
            val input = 1
            val actual = fizzbuzz(input)
            actual shouldBe "1"
        }
    }
}
