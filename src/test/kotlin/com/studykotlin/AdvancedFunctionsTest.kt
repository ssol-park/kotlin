package com.studykotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AdvancedFunctionsTest {

    /**
     * 1. 기본 매개변수 (Default Parameters)
     * 함수의 매개변수에 기본값을 설정하여, 호출 시 해당 인자를 생략할 수 있게 하는 기능입니다.
     *
     * - 단순한 기본값: 상수 값을 기본값으로 설정
     * - 복잡한 표현식: 다른 매개변수를 참조하거나 함수 호출 결과를 기본값으로 설정
     * - 오버로딩 대신 사용: Java의 메서드 오버로딩 대신 더 간결하게 다양한 호출 방식 제공
     */
    @Test
    @DisplayName("기본 매개변수: 단순한 기본값부터 복잡한 표현식까지")
    fun 기본_매개변수_default_parameters() {

        // 단순한 기본값
        fun createUser(name: String, age: Int = 18, email: String = "unknown@example.com"): String {
            return "User(name=$name, age=$age, email=$email)"
        }

        // 모든 인자를 명시적으로 전달
        val user1 = createUser("김철수", 25, "kim@test.com")
        assertThat(user1).isEqualTo("User(name=김철수, age=25, email=kim@test.com)")

        // 일부 기본값 사용
        val user2 = createUser("이영희", 30)
        assertThat(user2).isEqualTo("User(name=이영희, age=30, email=unknown@example.com)")

        // 모든 기본값 사용 (name만 필수)
        val user3 = createUser("박민수")
        assertThat(user3).isEqualTo("User(name=박민수, age=18, email=unknown@example.com)")

        // 복잡한 표현식을 기본값으로 사용 - Kotlin다운 로컬 함수
        fun calculateDefaultTimeout(protocol: String): Int = when (protocol) {
            "HTTPS" -> 10000
            "HTTP" -> 5000
            else -> 3000
        }

        fun createConnection(
            host: String,
            port: Int = 8080,
            protocol: String = "HTTP",
            endpoint: String = "$protocol://$host:$port",  // 다른 매개변수 참조
            timeout: Int = calculateDefaultTimeout(protocol)  // 로컬 함수 호출
        ): String = "Connection to $endpoint (timeout: ${timeout}ms)"

        val connection1 = createConnection("api.example.com")
        assertThat(connection1).isEqualTo("Connection to HTTP://api.example.com:8080 (timeout: 5000ms)")

        val connection2 = createConnection("secure.api.com", protocol = "HTTPS")
        assertThat(connection2).isEqualTo("Connection to HTTPS://secure.api.com:8080 (timeout: 10000ms)")
    }

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
        assertThat(server3).contains("api.test.com:9000", "retries=1")
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

    /**
     * 4. 중위 함수 (Infix Functions)
     * 함수를 연산자처럼 두 피연산자 사이에 위치시켜 호출할 수 있게 하는 기능입니다.
     *
     * - 가독성 향상: obj1 plus obj2 형태로 자연어에 가까운 코드 작성 가능
     * - 확장 함수로 구현: 기존 클래스에 중위 함수를 추가할 수 있음
     * - 단일 매개변수만 가능: infix 함수는 반드시 하나의 매개변수만 가져야 함
     * - 함수 체이닝: 빌더 패턴과 조합하여 DSL(Domain Specific Language) 구현 가능
     */
    @Test
    @DisplayName("중위 함수: 확장 함수로 구현하여 자연스러운 코드 작성")
    fun 중위_함수_infix_functions() {

        // 1. 기본적인 중위 함수 - 확장 함수로 구현
        infix fun Int.power(exponent: Int): Int {
            var result = 1
            repeat(exponent) { // 표준 라이브러리(Standard Library) 에서 기본으로 제공하는 함수
                result *= this // this -> exponent 값
            }
            return result
        }

        // 일반 함수 호출 방식
        val result1 = 2.power(3)
        assertThat(result1).isEqualTo(8)

        // 중위 함수 호출 방식 (더 자연스러움)
        val result2 = 2 power 3
        assertThat(result2).isEqualTo(8)

        // 2. 문자열 처리를 위한 중위 함수
        infix fun String.repeat(times: Int): String = this.repeat(times)

        val repeated = "Hello " repeat 3
        assertThat(repeated).isEqualTo("Hello Hello Hello ")

        // 3. 사용자 정의 클래스에서의 중위 함수
        data class Point(val x: Int, val y: Int) {
            infix fun moveTo(other: Point): Point = Point(other.x, other.y)
            infix fun moveBy(offset: Point): Point = Point(this.x + offset.x, this.y + offset.y)
        }

        val point1 = Point(1, 2)
        val point2 = point1 moveTo Point(5, 7)
        val point3 = point2 moveBy Point(2, 3)

        assertThat(point2).isEqualTo(Point(5, 7))
        assertThat(point3).isEqualTo(Point(7, 10))

        // 4. DSL 구현 - 간단한 설정 빌더
        data class Config(val settings: Map<String, Any> = emptyMap()) {
            infix fun with(pair: Pair<String, Any>): Config = 
                Config(settings + pair)
        }

        // 확장 함수로 더 자연스러운 문법 제공
        infix fun String.setTo(value: Any): Pair<String, Any> = this to value

        val config = Config()
            .with("database" setTo "postgresql")
            .with("port" setTo 5432)
            .with("ssl" setTo true)
            .with("timeout" setTo 30000)

        assertThat(config.settings["database"]).isEqualTo("postgresql")
        assertThat(config.settings["port"]).isEqualTo(5432)
        assertThat(config.settings["ssl"]).isEqualTo(true)
        assertThat(config.settings["timeout"]).isEqualTo(30000)

        println("Config: ${config.settings}")
    }

    /**
     * 5. 실전 조합 예제
     * 기본 매개변수, 명명된 인수, 가변 인수, 중위 함수를 모두 조합한 실용적인 예제입니다.
     */
    @Test
    @DisplayName("실전 조합: 모든 고급 함수 기능을 함께 사용한 실용적인 예제")
    fun 실전_조합_예제() {

        // DSL을 활용한 API 요청 빌더
        data class ApiRequest(
            val method: String = "GET",
            val url: String,
            val headers: Map<String, String> = emptyMap(),
            val params: Map<String, String> = emptyMap(),
            val timeout: Int = 5000
        ) {
            // 중위 함수로 메서드 체이닝
            infix fun method(httpMethod: String): ApiRequest = copy(method = httpMethod)
            infix fun timeout(timeoutMs: Int): ApiRequest = copy(timeout = timeoutMs)
            
            // 헤더 추가를 위한 중위 함수
            infix fun withHeader(header: Pair<String, String>): ApiRequest = 
                copy(headers = headers + header)
        }

        // 확장 함수로 자연스러운 DSL 문법 제공
        infix fun String.header(value: String): Pair<String, String> = this to value

        // 가변 인수를 사용한 헤더 추가 함수 (기본 매개변수 활용)
        fun ApiRequest.withHeaders(
            vararg headerPairs: Pair<String, String>, 
            contentType: String = "application/json"
        ): ApiRequest {
            val newHeaders = headers.toMutableMap()
            newHeaders["Content-Type"] = contentType
            headerPairs.forEach { (key, value) -> newHeaders[key] = value }
            return copy(headers = newHeaders)
        }

        // 유효성 검증 함수 (명명된 인수와 기본 매개변수)
        fun ApiRequest.validate(
            requireHttps: Boolean = false,
            maxTimeout: Int = 60000,
            debug: Boolean = false
        ): ApiRequest {
            if (requireHttps && !url.startsWith("https://")) {
                throw IllegalArgumentException("HTTPS required")
            }
            if (timeout > maxTimeout) {
                throw IllegalArgumentException("Timeout too high: $timeout > $maxTimeout")
            }
            if (debug) {
                println("Validated request: $this")
            }
            return this
        }

        // 실제 사용 예제
        val authHeaders = arrayOf(
            "Authorization" header "Bearer token123",
            "User-Agent" header "MyApp/1.0"
        )

        val request = ApiRequest(url = "https://api.example.com/users")
            .method("POST")
            .timeout(10000)
            .withHeaders(*authHeaders, contentType = "application/json")
            .withHeader("X-Request-ID" header "req-123")
            .validate(requireHttps = true, debug = true)

        assertThat(request.method).isEqualTo("POST")
        assertThat(request.url).isEqualTo("https://api.example.com/users")
        assertThat(request.headers["Content-Type"]).isEqualTo("application/json")
        assertThat(request.headers["Authorization"]).isEqualTo("Bearer token123")
        assertThat(request.headers["X-Request-ID"]).isEqualTo("req-123")
        assertThat(request.timeout).isEqualTo(10000)
    }

    /**
     * 6. 실전 조합 예제 개선
     * 설계 원칙을 준수하여 책임을 명확히 분리한 실용적인 예제입니다.
     * 불변 객체와 빌더 패턴을 분리하여 타입 안전성과 유지보수성을 향상시켰습니다.
     */
    @Test
    @DisplayName("실전 조합 예제 개선: 책임 분리와 타입 안전성을 고려한 설계")
    fun 실전_조합_예제_개선() {

        // 최종적으로 생성될, 데이터를 담는 불변 객체
        // 여기에는 설정을 변경하는 메서드가 존재하지 않는다.
        data class ApiRequest(
            val method: String,
            val url: String,
            val headers: Map<String, String>,
            val params: Map<String, String>,
            val timeout: Int
        )

        // 설정을 담당하는 빌더 클래스
        class ApiRequestBuilder(
            private var url: String, // 내부적으로 변경 가능하도록 var로 선언
            private var method: String = "GET",
            private var headers: Map<String, String> = emptyMap(),
            private var params: Map<String, String> = emptyMap(),
            private var timeout: Int = 5000
        ) {
            // 각 메서드는 자기 자신(빌더)을 반환하여 체이닝을 유지한다.
            fun method(httpMethod: String): ApiRequestBuilder = apply { 
                this.method = httpMethod 
            }

            fun timeout(timeoutMs: Int): ApiRequestBuilder = apply { 
                this.timeout = timeoutMs 
            }

            fun withHeader(header: Pair<String, String>): ApiRequestBuilder = apply { 
                this.headers = this.headers + header 
            }

            fun withHeaders(vararg headerPairs: Pair<String, String>): ApiRequestBuilder = apply { 
                this.headers = this.headers + headerPairs.toMap() 
            }

            // --- 유효성 검증 및 최종 객체 생성 ---
            // 모든 설정이 끝난 후 마지막에 호출하는 메서드
            fun build(
                requireHttps: Boolean = false,
                maxTimeout: Int = 60000
            ): ApiRequest {
                // 1. 유효성 검증을 먼저 수행
                if (requireHttps && !url.startsWith("https://")) {
                    throw IllegalArgumentException("HTTPS required for url: $url")
                }
                if (timeout > maxTimeout) {
                    throw IllegalArgumentException("Timeout too high: $timeout > $maxTimeout")
                }

                // 2. 검증을 통과하면, 불변 ApiRequest 객체를 생성하여 반환
                return ApiRequest(
                    method = this.method,
                    url = this.url,
                    headers = this.headers,
                    params = this.params,
                    timeout = this.timeout
                )
            }
        }

        // 확장 함수는 그대로 활용 가능
        infix fun String.header(value: String): Pair<String, String> = this to value

        // --- 실제 사용 예제 ---
        val authHeaders = arrayOf(
            "Authorization" header "Bearer token123",
            "User-Agent" header "MyApp/1.0"
        )

        // 1. 빌더를 생성하고 설정을 체이닝
        val requestBuilder = ApiRequestBuilder(url = "https://api.example.com/users")
            .method("POST")
            .timeout(10000)
            .withHeaders(*authHeaders)
            .withHeader("X-Request-ID" header "req-123")

        // 2. 마지막에 build()를 호출하여 검증 및 최종 객체 생성
        val request: ApiRequest = requestBuilder.build(requireHttps = true)

        println("성공적으로 생성된 요청: $request")

        // 검증
//        assertThat(request.method).isEqualTo("POST")
//        assertThat(request.url).isEqualTo("https://api.example.com/users")
//        assertThat(request.headers["Authorization"]).isEqualTo("Bearer token123")
//        assertThat(request.headers["User-Agent"]).isEqualTo("MyApp/1.0")
//        assertThat(request.headers["X-Request-ID"]).isEqualTo("req-123")
//        assertThat(request.timeout).isEqualTo(10000)
//
//        // 타입 안전성 확인
//        assertThat(request.headers).isInstanceOf(Map::class.java)
//        assertThat(request.headers.keys.first()).isInstanceOf(String::class.java)
//        assertThat(request.headers.values.first()).isInstanceOf(String::class.java)

        // 예외 상황 테스트
        try {
            ApiRequestBuilder("http://insecure.com")
                .build(requireHttps = true)
            assert(false) { "예외가 발생해야 함" }
        } catch (e: IllegalArgumentException) {
            println(e.message)
            assertThat(e.message).contains("HTTPS required")
        }

        try {
            ApiRequestBuilder("https://api.test.com")
                .timeout(70000)
                .build(maxTimeout = 60000)
            assert(false) { "예외가 발생해야 함" }
        } catch (e: IllegalArgumentException) {
            println(e.message)
            assertThat(e.message).contains("Timeout too high")
        }
    }
}