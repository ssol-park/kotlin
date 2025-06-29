package com.studykotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DataTypeTest {

    @Test
    fun 데이터_타입() {
        // 정수형
//        val byte: Byte = 127
//        val short: Short = 32767
//        val int: Int = 2_147_483_647
//        val long: Long = 9_223_372_036_854_775_807L
//
//        // 실수형
//        val float: Float = 3.14f
//        val double: Double = 3.1415926535897932384626433832795
//
//        // 불리언
//        val boolean: Boolean = true
//
//        // 배열
//        val intArray: IntArray = intArrayOf(1, 2, 3) // int[]
//        val array: Array<Int> = arrayOf(1, 2, 3)     // Integer[]
    }


    /**
     * 자바의 java.util.List 인터페이스는 읽기(get)와 수정(add, remove) 메서드를 모두 포함하는 가변(Mutable)을 전제로 설계
     * 반면, 코틀린은 이를 의도적으로 분리
     *  - kotlin.collections.List: 오직 읽기 전용 메서드만 가진 불변(Immutable) 인터페이스
     *  - kotlin.collections.MutableList: List를 상속받아 add, remove 같은 수정 메서드를 추가한 가변(Mutable) 인터페이스
     * */
    @Test
    fun 컬렉션_타입_List() {
        val immutList: List<Int> = listOf(1, 2, 3)
        val max = immutList.filter { it > 1 }.map { it * 2 }.max()

        val mutList: MutableList<Int> = mutableListOf(1, 2, 3)
        mutList.add(4)

        assertThat(max).isEqualTo(6)
        assertThat(mutList).isEqualTo(listOf(1, 2, 3, 4))
    }

    /**
     * 자바의 `Map`과 달리 코틀린은 읽기 전용 `Map`과 수정 가능한 `MutableMap` 인터페이스를 명확히 구분
     * - `mapOf()`로 생성된 `Map`은 생성 후 요소를 추가, 수정, 삭제할 수 없어 데이터의 안정성을 보장
     * - `mutableMapOf()`로 생성된 `MutableMap`은 `[]` 연산자나 `put`, `remove` 메서드를 통해 내용을 자유롭게 변경
     *
     * 관용적 사용법
     * - 생성: `mapOf("key" to "value")`, `mutableMapOf()`
     * - 접근/수정: `map["key"]`, `map["key"] = "newValue"`
     * - 반복: `for ((key, value) in map)` 구조 분해 선언을 통한 순회
     * 코틀린에서는 map[key] = value 처럼 대괄호([])를 사용한 '인덱스 접근' 방식이 .put()을 대체하는 더 관용적인 스타일
     *
     * 핵심 철학
     * 데이터의 안정성과 예측 가능성을 위해 기본적으로 불변 `Map`을 사용하고,
     * 내용 변경이 반드시 필요한 경우에만 `MutableMap`을 제한적으로 사용하는 것을 권장
     */
    @Test
    fun 컬렉션_타입_Map() {
        val immutMap: Map<String, Int> = mapOf("a" to 1, "b" to 2, "c" to 3)

        val mapValues = immutMap
            .filterKeys { it.equals("b") }
            .mapValues { 10 }

//        val mutMap: MutableMap<String, Int> = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
        val mutMap: MutableMap<String, Int> = mutableMapOf()
        mutMap["a"] = 1
        mutMap["b"] = 2
        mutMap.remove("a")

        assertThat(mapValues).isEqualTo(mapOf("b" to 10))
        assertThat(mutMap).isEqualTo(mapOf("b" to 2))
    }

    @Test
    fun Null_안전성_테스트() {
        // Nullable 타입
        val nullableString: String? = null
        val nonNullString: String = "Not Null"

        // Safe call 연산자 (?.)
        val length1 = nullableString?.length
        val length2 = nonNullString.length

        println("Nullable String 길이: $length1")
        println("Non-null String 길이: $length2")

        // Elvis 연산자 (?:)
        val length3 = nullableString?.length ?: 0

        println("Elvis 연산자 사용 길이: $length3")

        // let 함수 사용
        nullableString?.let {
            println("nullableString은 null이 아닙니다: $it")
        } ?: println("nullableString은 null입니다")

        // 검증
        assertThat(length1).isNull()
        assertThat(length2).isEqualTo(8)
        assertThat(length3).isEqualTo(0)
    }

    /**
     * @Any 타입
     *  - 코틀린의 모든 클래스의 최상위 슈퍼클래스(Superclass)
     *  - 적용 범위: 자바에서 int, float 같은 원시 타입(primitive type)은 Object가 아니지만, 코틀린에서는 Int, String, Boolean 등 모든 타입이 Any를 상속받음
     *  - 적극적으로 사용하는 것은 권장되지 않습니다. Any 타입을 사용하면 코틀린의 가장 큰 장점인 타입 안전성(Type Safety)을 잃게 됨.
     *    변수를 Any로 선언하는 순간, 컴파일러는 그 변수가 어떤 타입인지 알 수 없으므로 관련된 유용한 메서드(예: String의 length)를
     *    전혀 추천해주지 못하며, 사용하려면 매번 is로 타입을 검사하고 캐스팅해야 한다.
     * @is 연산자
     *  - is 연산자는 런타임에 객체의 타입을 검사하는 역할. 자바의 instanceof 연산자와 동일한 기능을 수행
     *    객체 is 타입의 형태로 사용되며, 결과로 Boolean(true 또는 false) 값을 반환한다.
     *  - is 연산자로 타입 검사를 통과한 경우, 코틀린 컴파일러는 해당 스코프(scope) 안에서 그 객체를 검사한 타입으로 자동으로 캐스팅 해주며
     *    이를 **스마트 캐스트(Smart Cast)**라고 부른다. (자바는 직접 형변환을 해줘야 함)
     *
     *    if (obj1 is String) {
     *     // 이 블록 안에서는 obj1이 String 타입임이 보장됨
     *     // 따라서 별도의 캐스팅 없이 바로 String의 메서드인 .length를 사용할 수 있음
     *     println("obj1은 String 타입입니다: ${obj1.length}")
     *    }
     *
     * @as, as? 연산자
     *  - as 연산자는 변수의 타입을 **명시적으로 강제 변환(캐스팅)**할 때 사용. 이를 "Unsafe Cast(안전하지 않은 캐스팅)"라고 부른다.
     *  - 객체가 해당 타입으로 변환될 수 없다면, 런타임에 ClassCastException 예외가 발생
     *  - 사용에 주의 필요, 일반적으로 권장되지 않음. 타입 검사를 마쳤거나, 100% 확신 할 수 있는 경우에 제한적으로 사용
     * @as? 연산자
     *  - as?는 as의 안전한 버전으로, "Safe Cast(안전한 캐스팅)"라고 부른다.
     *  - 캐스팅에 실패하면, 예외를 발생시키는 대신 null을 반환
     *  - as?의 사용은 매우 적극적으로 권장. 예기치 않은 타입이 들어왔을 때 프로그램이 중단되는 것을 막고,
     *    ?. (Safe Call)이나 ?: (엘비스 연산자)와 함께 사용하여 null인 경우를 우아하게 처리할 수 있다.
     * */
    @Test
    fun 타입_검사와_캐스팅_테스트() {
        val obj1: Any = "문자열입니다"
        val obj2: Any = 42

        // is 연산자로 타입 검사
        if (obj1 is String) {
            // 스마트 캐스트 - obj1은 이 블록 안에서 String으로 취급됨
            println("obj1은 String 타입입니다: ${obj1.length}")
        }

        // as 연산자로 타입 캐스팅
        val str = obj1 as String
        println("캐스팅 후 문자열 길이: ${str.length}")

        // 안전한 캐스팅 (as?)
        val num = obj2 as? Int
        val str2 = obj2 as? String

        println("obj2를 Int로 캐스팅: $num")
        println("obj2를 String으로 캐스팅: $str2") // null이 됨

        // 검증
        assertThat(obj1).isInstanceOf(String::class.java)
        assertThat(num).isEqualTo(42)
        assertThat(str2).isNull()
    }

    /**
     * 코틀린의 특별한 타입인 Unit과 Nothing, 그리고 그 배경이 되는
     * 문장(Statement)과 표현식(Expression)의 개념을 설명합니다.
     *
     * ----------------------------------------
     * 1. 문장(Statement) vs 표현식(Expression)
     * ----------------------------------------
     *
     * 문장 (Statement):
     * - 정의: 특정 동작을 수행하도록 지시하는 코드의 최소 실행 단위입니다.
     * - 특징: 그 자체가 값을 만들어내지는 않으며, '무엇을 하라'는 명령에 가깝습니다.
     * - 예시 (Java):
     * for (int i = 0; i < 5; i++) { System.out.println(i); }
     *
     * 표현식 (Expression):
     * - 정의: 평가되었을 때 하나의 값(value)을 만들어내는 코드 조각입니다.
     * - 특징: 모든 표현식은 값으로 귀결되므로, 타입을 가집니다.
     * - 예시 (Kotlin):
     * val resultText = if (5 > 4) "크다" else "작거나 같다";
     *
     *
     * ----------------------------------------
     * 2. Unit 타입: '값이 없음'을 나타내는 객체
     * ----------------------------------------
     *
     * 역할 및 개념:
     * - 자바의 void와 같이 '반환할 의미 있는 값이 없음'을 나타냅니다.
     * - 주로 특정 동작(side effect)만 수행하고 끝나는 함수에 사용됩니다.
     *
     * 핵심 특징 (vs void):
     * - 자바의 void가 키워드인 반면, 코틀린의 Unit은 실제로 존재하는
     * 싱글턴(singleton) 객체입니다. 따라서 Unit은 실제 타입입니다.
     *
     * 실용적 장점:
     * - Unit이 실제 타입이기 때문에 제네릭(Generics)의 타입 파라미터로
     * 사용할 수 있어 유연성이 높습니다. (예: Task<Unit>)
     *
     * 사용법:
     * - 함수의 반환 타입을 생략하면 컴파일러가 자동으로 Unit으로 간주하므로,
     * 대부분의 경우 명시적으로 선언할 필요가 없습니다.
     *
     *
     * ----------------------------------------
     * 3. Nothing 타입: '정상적 반환이 없음'을 나타내는 타입
     * ----------------------------------------
     *
     * 역할 및 개념:
     * - 함수가 정상적으로 값을 반환하는 일이 절대 없음을 명시적으로 나타냅니다.
     * - 함수가 항상 예외를 던지거나 무한 루프에 빠지는 경우에 사용됩니다.
     *
     * 핵심 특징 1: 'throw'는 표현식
     * - 자바의 throw는 문장(Statement)이지만, 코틀린의 throw는 값을 가지는
     * 표현식(Expression)이며 그 타입이 바로 Nothing입니다.
     *
     * 핵심 특징 2: 최하위 타입 (Bottom Type)
     * - Nothing의 가장 중요한 특성은 모든 타입의 하위 타입(subtype)으로
     * 간주되는 최하위 타입이라는 점입니다.
     *
     * 실용적 장점 및 예시:
     * - 최하위 타입이라는 특성은 컴파일러의 타입 추론을 크게 돕습니다.
     * - 아래 코드에서 throw 표현식의 타입(Nothing)이 String의 하위 타입으로
     * 간주되므로, 컴파일러는 이 코드를 타입 안전하다고 판단합니다.
     * val name: String = nullableName ?: throw IllegalArgumentException("이름이 null입니다.");
     *
     */
    @Test
    fun 특수_타입_테스트() {
        // Any 타입 - 모든 타입의 상위 타입
        val anyValue1: Any = 42
        val anyValue2: Any = "문자열"
        val anyValue3: Any = true

        println("Any 타입 예제1: $anyValue1 (실제 타입: ${anyValue1.javaClass.simpleName})")
        println("Any 타입 예제2: $anyValue2 (실제 타입: ${anyValue2.javaClass.simpleName})")
        println("Any 타입 예제3: $anyValue3 (실제 타입: ${anyValue3.javaClass.simpleName})")

        // Unit 타입 - 반환값이 없는 함수 (Java의 void와 유사)
        fun printHello(): Unit {
            println("Hello!")
        }

        val result = printHello()
        println("Unit 타입 반환값: $result")

        // Nothing 타입 - 함수가 정상적으로 종료되지 않음을 나타냄
        fun fail(): Nothing {
            throw RuntimeException("실패")
        }

        // fail() 호출은 예외를 발생시키므로 주석 처리
        // val failResult = fail()

        // 검증
        assertThat(anyValue1).isEqualTo(42)
        assertThat(result).isEqualTo(Unit)
    }

}