package com.studykotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AdvancedFunctionsTest {

    /**
     * 2. 명명된 인수 (Named Arguments)
     * 함수를 호출할 때, 어떤 매개변수에 어떤 값을 전달할지 이름을 명시적으로 지정하는 기능입니다.
     *
     * 가독성 향상: createConnection(host="...", port=..., timeout=...) 처럼 각 값이 어떤 의미인지 명확하게 알 수 있어, 특히 Boolean 값이나 동일한 타입의 인자가 여러 개일 때 유용합니다.
     * 인자 순서의 자유: 매개변수 이름을 명시하므로, 함수에 선언된 순서와 상관없이 인자를 전달할 수 있습니다.
     * 기본 매개변수와의 시너지: 기본 매개변수가 많은 함수에서, 원하는 특정 매개변수의 값만 건너뛰어 지정할 수 있게 해줍니다.
     * */
    @Test
    fun 명명된_인수_named_args() {

        fun configureServer(
            host: String,
            port: Int,
            protocol: String,
            timeout: Int,
            retries: Int,
            enableSsl: Boolean
        ): String {
            return "Server($host:$port, $protocol, timeout=$timeout, retries=$retries, ssl=$enableSsl)"
        }

        // 순서대로 인수 전달
        val server1 = configureServer("localhost", 8080, "HTTP", 5000, 3, false)
        assertThat(server1).contains("localhost:8080", "HTTP", "timeout=5000")

        // 일부는 순서대로, 일부는 named
        val server2 = configureServer("api.test.com", 9000, "HTTP", retries = 1, timeout = 2000, enableSsl = false)
        assertThat(server2).contains("api.test.com:9000", "timeout=2000")

        val server3 = configureServer(
            host = "api.test.com",
            port = 9000,
            protocol = "HTTP",
            retries = 1,
            timeout = 2000,
            enableSsl = false,
        )
    }

    /**
     * 3. 가변 인수 (Vararg)
     * 동일한 타입의 인자를 0개 이상, 개수 제한 없이 유연하게 전달받을 수 있도록 하는 기능입니다.
     *
     * - 배열로 처리: 함수 내부에서 vararg로 전달된 값들은 배열(Array)로 취급됩니다.
     * - 유연한 인자 전달: 함수 호출 시 인자를 전달하지 않거나, 1개 또는 여러 개를 콤마(,)로 구분하여 전달할 수 있습니다.
     * - 스프레드 연산자 (*): 이미 생성된 배열을 vararg 인자로 전달할 때는, 배열 앞에 *를 붙여 배열의 요소들을 펼쳐서 전달해야 합니다.
     */
    @Test
    @DisplayName("가변 인수: 인자를 0개, 여러 개, 또는 배열로 유연하게 전달할 수 있다")
    fun 가변_인수_vararg() {

        fun log(level: String, vararg messages: String): String {
            val combinedMessage = messages.joinToString(" - ")
            return "[$level] $combinedMessage"
        }

        // 1. 인자를 전달하지 않는 경우
        val log1 = log("INFO")
        assertThat(log1).isEqualTo("[INFO] ")
        println(log1)

        // 2. 여러 개의 인자를 직접 전달하는 경우
        val log2 = log("DEBUG", "사용자 조회", "캐시 확인", "DB 접근")
        assertThat(log2).isEqualTo("[DEBUG] 사용자 조회 - 캐시 확인 - DB 접근")
        println(log2)

        // 3. 이미 생성된 배열을 전달하는 경우 (스프레드 연산자 * 사용)
        // * 연산자는 배열의 각 요소를 꺼내어 인자로 전달하는 역할
        val messageArray = arrayOf("메모리 부족", "가비지 컬렉션 실행")

        val log3 = log("WARN", *messageArray)

        assertThat(log3).isEqualTo("[WARN] 메모리 부족 - 가비지 컬렉션 실행")
        println(log3)
    }
}