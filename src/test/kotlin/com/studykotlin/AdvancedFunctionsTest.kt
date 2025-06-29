package com.studykotlin

import org.assertj.core.api.Assertions.assertThat
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
    }
}