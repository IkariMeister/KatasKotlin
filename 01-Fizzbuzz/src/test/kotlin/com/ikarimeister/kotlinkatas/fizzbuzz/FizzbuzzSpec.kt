package com.ikarimeister.kotlinkatas.fizzbuzz

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

class FizzbuzzSpec : ShouldSpec() {
    init {
        "FizzBuzz" {
            should("When an int is input should say the int") {
                forAll(MultipleNot3Not5Gen()) { value ->
                    fizzbuzz(value) == "$value"
                }
            }.config(invocations = 100, threads = 4)
            should("When a multiple of 15 is input should say FizzBuzz") {
                forAll(MultipleOf15Gen()) { value: Int ->
                    fizzbuzz(value) == "FizzBuzz"
                }
            }.config(invocations = 100, threads = 4)
            should("When a multiple of 3 is input should say the Fizz") {
                forAll(MultipleOf3Gen()) { value ->
                    fizzbuzz(value) == "Fizz"
                }
            }.config(invocations = 100, threads = 4)
            should("When a multiple of 5 is input should say the Buzz") {
                forAll(MultipleOf5Gen()) { value ->
                    fizzbuzz(value) == "Buzz"
                }
            }.config(invocations = 100, threads = 4)
        }
    }

    companion object Generators {
        class MultipleOf15Gen : Gen<Int> {
            override fun generate(): Int =
                Gen.choose(-100, 100).generate().times(15)
        }

        class MultipleOf3Gen : Gen<Int> {
            override fun generate(): Int =
                Gen.choose(-100, 100).generate().takeUnless { it % 5 == 0 }?.times(3) ?: 3
        }

        class MultipleOf5Gen : Gen<Int> {
            override fun generate(): Int =
                Gen.choose(-100, 100).generate().takeUnless { it % 3 == 0 }?.times(5) ?: 5
        }

        class MultipleNot3Not5Gen : Gen<Int> {
            override fun generate(): Int =
                Gen.int().generate().takeUnless { (it % 3 == 0) || (it % 5 == 0) } ?: 1
        }
    }
}
