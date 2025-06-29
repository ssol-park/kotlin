package com.studykotlin

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

// https://kotlinlang.org/docs/functions.html#unit-returning-functions

class BasicFunctionsTest {

    @Test
    fun 기본_함수_선언_및_호출() {

        fun sayHello() {
            println("@@@@@@ Hello")
        }

        fun sayHelloWithName(name: String) {
            println("@@@@@@ Hello $name")
        }

        fun add(a: Int, b: Int): Int {
            return a + b
        }

        fun multiply(a: Int, b: Int): Int = a * b
    }

    @Test
    fun Unit_타입_및_반환값_생략() {
        /**
         * Unit을 명시적으로 반환하는 함수
         */
        fun printMessage(message: String): Unit {
            println(message)
        }

        /**
         * Unit 생략 (위 함수와 동일)
         */
        fun printMessageSimple(message: String) {
            println(message)
        }

        /**
         * Nothing 타입 - 절대 정상적으로 반환되지 않는 함수
         */
        fun throwError(): Nothing {
            throw IllegalStateException("에러 발생!")
        }

        printMessage("Message1")
        printMessageSimple("Message2")

        assertThatThrownBy {
            throwError()
        }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("에러 발생!")

    }
}