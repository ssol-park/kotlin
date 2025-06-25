## MEMO

1. **간결성 활용하기**
   - data class 사용하기
   - 람다와 고차 함수 적극 활용하기

2. **널 안전성 활용하기**
   - Nullable 타입과 안전 호출 연산자(?.) 적극 사용하기
   - !! 연산자는 확실한 경우에만 제한적으로 사용하기

3. **함수형 프로그래밍 스타일 도입하기**
   - 불변성(immutability) 선호하기: val을 기본으로 사용하기
   - 컬렉션 함수 활용하기: map, filter, fold 등
   - 순수 함수(pure function)와 side effect 최소화하기
   - 고차 함수로 코드 재사용성 높이기

4. **코틀린만의 특별한 기능 활용하기**
   - 확장 함수(extension function)로 기존 클래스 확장하기
   - 스코프 함수(let, apply, run, also, with) 적절히 사용하기
   - 중위 함수(infix function)와 연산자 오버로딩으로 가독성 높이기

5. **관용적인 코틀린 패턴 사용하기**
   - 싱글톤은 object 키워드로 간결하게 구현하기
   - when 식을 if-else 체인 대신 사용하기
   - sealed class로 제한된 계층 구조 만들기

6. **멀티 패러다임 프로그래밍 활용하기**
   - **객체 지향 프로그래밍(OOP)**
      - 클래스 상속과 인터페이스 구현으로 다형성 활용하기
      - 프로퍼티와 커스텀 getter/setter로 캡슐화 강화하기
      - 위임 패턴(by 키워드)으로 상속의 대안 제공하기

   - **함수형 프로그래밍(FP)**
      - 함수를 first-class citizen으로 활용하기
      - 람다와 클로저로 표현력 높은 코드 작성하기
      - 재귀와 꼬리 재귀 최적화(tailrec) 활용하기
      - 모나딕 접근(Option, Result 패턴 등)으로 오류 처리하기

   - **절차적 프로그래밍**
      - 최상위 함수와 프로퍼티 활용하기
      - 확장 함수로 모듈화 개선하기

   - **동시성 프로그래밍**
      - 코루틴으로 비동기 프로그래밍 단순화하기
      - 구조적 동시성(structured concurrency)으로 안전한 비동기 코드 작성하기
      - Flow API로 비동기 스트림 처리하기

7. **상호운용성 활용하기**
   - 자바 코드와의 원활한 통합을 위한 어노테이션(@JvmStatic, @JvmField 등) 활용하기
   - 플랫폼별 구현(expect/actual)으로 멀티플랫폼 코드 작성하기
     활용하여 간결하고 표현력 있는 코드를 작성하는 것입니다. 단순히 자바 코드를 코틀린 문법으로 옮기는 것이 아니라, 코틀린의 패러다임을 받아들여 더 현대적이고 안전한 코드를 작성하는 것이 중요합니다.
   
# 코틀린 메모 항목별 자바-코틀린 비교 예시

## 1. 간결성 활용하기

### a) data class 사용하기

**Java (with Lombok):**
```java
import lombok.Data;

@Data
public class Person {
    private final String name;
    private final int age;
    private final String email;
}
```

**Kotlin:**
```kotlin
data class Person(
    val name: String,
    val age: Int,
    val email: String
)
```

**설명:** 코틀린의 data class는 롬복의 @Data와 유사하지만 언어 자체에 내장되어 있으며, 복사(copy) 메서드도 제공합니다.

### b) 람다와 고차 함수 활용하기

**Java:**
```java
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserFilter {
    public List<User> filterUsers(List<User> users, Predicate<User> predicate) {
        return users.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    // 사용
    public List<User> getActiveAdults(List<User> users) {
        return filterUsers(users, user -> user.isActive() && user.getAge() >= 18);
    }
}
```

**Kotlin:**
```kotlin
class UserFilter {
    fun filterUsers(users: List<User>, predicate: (User) -> Boolean): List<User> {
        return users.filter(predicate)
    }
    
    // 사용
    fun getActiveAdults(users: List<User>): List<User> {
        return filterUsers(users) { it.isActive && it.age >= 18 }
    }
}
```

**설명:** 코틀린에서는 함수 타입이 더 간결하게 표현되며, 람다의 마지막 매개변수가 함수인 경우 괄호 밖으로 뺄 수 있습니다. 또한 `it`을 사용해 단일 매개변수를 더 간결하게 참조할 수 있습니다.

## 2. 널 안전성 활용하기

### a) Nullable 타입과 안전 호출 연산자(?.) 활용하기

**Java:**
```java
public String getUserCity(User user) {
    if (user == null) {
        return null;
    }
    
    Address address = user.getAddress();
    if (address == null) {
        return null;
    }
    
    City city = address.getCity();
    if (city == null) {
        return null;
    }
    
    return city.getName();
}
```

**Kotlin:**
```kotlin
fun getUserCity(user: User?): String? {
    return user?.address?.city?.name
}
```

**설명:** 코틀린에서는 타입 시스템에 null 가능성을 명시적으로 표현하고, 안전 호출 연산자(`?.`)를 사용해 null 체크를 간결하게 처리할 수 있습니다.

### b) !! 연산자 제한적 사용하기

**Java:**
```java
public void processUserData(User user) {
    // NPE 위험이 있지만 명시적 체크 없음
    String name = user.getName().toUpperCase();
    processName(name);
}
```

**Kotlin (나쁜 예):**
```kotlin
fun processUserData(user: User?) {
    // 확신이 없는데 !! 사용 - 위험한 코드
    val name = user!!.name!!.uppercase()
    processName(name)
}
```

**Kotlin (좋은 예):**
```kotlin
fun processUserData(user: User?) {
    // null 가능성 처리
    user?.name?.let { 
        val uppercaseName = it.uppercase()
        processName(uppercaseName)
    }
}
```

**설명:** `!!` 연산자는 "이 값은 null이 아님을 보증한다"는 의미로, NPE 위험이 있어 확실한 경우에만 제한적으로 사용해야 합니다.

## 3. 함수형 프로그래밍 스타일 도입하기

### a) 불변성(immutability) 선호하기

**Java:**
```java
public class Cart {
    private final List<Item> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    // 변경 연산은 새 객체 반환
    public Cart addItem(Item item) {
        List<Item> newItems = new ArrayList<>(items);
        newItems.add(item);
        return new Cart(newItems);
    }
    
    // 내부 생성자
    private Cart(List<Item> items) {
        this.items = List.copyOf(items);  // Java 10+ 불변 리스트
    }
    
    public List<Item> getItems() {
        return List.copyOf(items);  // 방어적 복사
    }
}
```

**Kotlin:**
```kotlin
class Cart(val items: List<Item> = emptyList()) {
    // 변경 연산은 새 객체 반환
    fun addItem(item: Item): Cart = Cart(items + item)
    
    // items는 기본적으로 읽기 전용이므로 방어적 복사 필요 없음
}
```

**설명:** 코틀린에서는 기본적으로 `val`과 읽기 전용 컬렉션을 사용하여 불변성을 더 쉽게 표현할 수 있습니다.

### b) 컬렉션 함수 활용하기

**Java:**
```java
public class OrderProcessor {
    public double calculateTotal(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(Item::getPrice)
                .sum();
    }
    
    public Map<Customer, List<Order>> groupByCustomer(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
    }
}
```

**Kotlin:**
```kotlin
class OrderProcessor {
    fun calculateTotal(orders: List<Order>): Double {
        return orders
            .filter { it.status == OrderStatus.COMPLETED }
            .flatMap { it.items }
            .sumOf { it.price }
    }
    
    fun groupByCustomer(orders: List<Order>): Map<Customer, List<Order>> {
        return orders.groupBy { it.customer }
    }
}
```

**설명:** 코틀린은 컬렉션에 대한 풍부한 함수형 API를 제공하며, 자바의 Stream API보다 더 간결하게 사용할 수 있습니다.

### c) 순수 함수와 side effect 최소화하기

**Java:**
```java
public class PriceCalculator {
    // 순수 함수 - 동일 입력에 항상 동일 출력, 부작용 없음
    public static double calculateDiscount(double price, int userLevel) {
        return switch (userLevel) {
            case 1 -> price * 0.05;
            case 2 -> price * 0.10;
            case 3 -> price * 0.15;
            default -> 0;
        };
    }
    
    // 순수 함수가 아님 - 외부 상태에 의존
    private double taxRate;
    
    public double calculateTax(double price) {
        return price * taxRate;  // 외부 상태(taxRate)에 의존
    }
}
```

**Kotlin:**
```kotlin
class PriceCalculator {
    // 순수 함수 - 동일 입력에 항상 동일 출력, 부작용 없음
    fun calculateDiscount(price: Double, userLevel: Int): Double {
        return when (userLevel) {
            1 -> price * 0.05
            2 -> price * 0.10
            3 -> price * 0.15
            else -> 0.0
        }
    }
    
    // 최상위 함수로 정의하면 더 함수형 스타일에 가까움
    companion object {
        fun calculateDiscountPure(price: Double, userLevel: Int): Double {
            return when (userLevel) {
                1 -> price * 0.05
                2 -> price * 0.10
                3 -> price * 0.15
                else -> 0.0
            }
        }
    }
}

// 최상위 함수로 정의
fun calculateDiscountPure(price: Double, userLevel: Int): Double {
    return when (userLevel) {
        1 -> price * 0.05
        2 -> price * 0.10
        3 -> price * 0.15
        else -> 0.0
    }
}
```

**설명:** 코틀린에서는 최상위 함수를 정의할 수 있어 더 함수형 스타일의 코드를 작성하기 쉽습니다. 순수 함수는 동일한 입력에 대해 항상 동일한 결과를 반환하고 부작용이 없는 함수입니다.

### d) 고차 함수로 코드 재사용성 높이기

**Java:**
```java
public class StringProcessor {
    public String transform(String input, Function<String, String> transformation) {
        return transformation.apply(input);
    }
    
    // 사용 예
    public void example() {
        String result1 = transform("hello", s -> s.toUpperCase());
        String result2 = transform("hello", s -> s + " world");
        String result3 = transform("hello", s -> new StringBuilder(s).reverse().toString());
    }
}
```

**Kotlin:**
```kotlin
class StringProcessor {
    fun transform(input: String, transformation: (String) -> String): String {
        return transformation(input)
    }
    
    // 사용 예
    fun example() {
        val result1 = transform("hello") { it.uppercase() }
        val result2 = transform("hello") { "$it world" }
        val result3 = transform("hello") { it.reversed() }
    }
    
    // 특정 변환을 위한 함수 반환
    fun createFormatter(prefix: String): (String) -> String {
        return { input -> "$prefix: $input" }
    }
}
```

**설명:** 코틀린에서는 함수를 더 자연스럽게 전달하고 반환할 수 있어, 고차 함수를 통한 코드 재사용성을 높이기 쉽습니다.

## 4. 코틀린만의 특별한 기능 활용하기

### a) 확장 함수(extension function)로 기존 클래스 확장하기

**Java (유틸리티 클래스 접근):**
```java
public class StringUtils {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static String truncate(String text, int maxLength) {
        if (text == null) return null;
        return text.length() <= maxLength 
            ? text 
            : text.substring(0, maxLength) + "...";
    }
}

// 사용
String email = "user@example.com";
if (StringUtils.isValidEmail(email)) {
    String shortEmail = StringUtils.truncate(email, 10);
}
```

**Kotlin (확장 함수):**
```kotlin
// String 클래스에 확장 함수 추가
fun String.isValidEmail(): Boolean {
    return matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
}

fun String.truncate(maxLength: Int): String {
    return if (length <= maxLength) this else substring(0, maxLength) + "..."
}

// 사용
val email = "user@example.com"
if (email.isValidEmail()) {
    val shortEmail = email.truncate(10)
}
```

**설명:** 코틀린의 확장 함수를 사용하면 기존 클래스에 새로운 메서드를 추가한 것처럼 사용할 수 있어, 더 자연스러운 API를 설계할 수 있습니다.

### b) 스코프 함수(let, apply, run, also, with) 적절히 사용하기

**Java:**
```java
public void processUser(User user) {
    if (user != null) {
        // 임시 변수
        String name = user.getName();
        user.setLastLoginTime(System.currentTimeMillis());
        System.out.println("Processing: " + name);
        userRepository.save(user);
    }
}

public User createUser(String name, String email) {
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setCreatedAt(System.currentTimeMillis());
    user.setActive(true);
    return user;
}
```

**Kotlin:**
```kotlin
fun processUser(user: User?) {
    // let: null이 아닌 경우에만 블록 실행, it으로 객체 참조
    user?.let {
        val name = it.name
        it.lastLoginTime = System.currentTimeMillis()
        println("Processing: $name")
        userRepository.save(it)
    }
}

fun createUser(name: String, email: String): User {
    // apply: 객체 초기화 후 그 객체 반환, this로 객체 참조
    return User().apply {
        this.name = name
        this.email = email
        this.createdAt = System.currentTimeMillis()
        this.isActive = true
    }
}

// with 예시: 특정 객체에 대해 여러 작업을 할 때
fun displayUserInfo(user: User) {
    with(user) {
        println("Name: $name")
        println("Email: $email")
        println("Active: $isActive")
    }
}

// run 예시: 객체 초기화와 결과 계산을 한번에
fun getUserStatus(user: User): String {
    return user.run {
        if (isActive) "Active since ${createdAt}" else "Inactive"
    }
}

// also 예시: 부수 효과를 위한 작업 수행하면서 객체 자체 반환
fun logAndGetUser(user: User): User {
    return user.also {
        println("Processing user: ${it.name}")
        analyticsService.trackUser(it.id)
    }
}
```

**설명:** 코틀린의 스코프 함수는 객체 컨텍스트 내에서 코드 블록을 실행하는 방법을 제공합니다. 각 함수는 약간씩 다른 용도로 사용됩니다:
- `let`: 객체가 null이 아닌 경우에만 블록 실행, 결과를 반환 (it으로 참조)
- `apply`: 객체 설정 후 그 객체 자체를 반환 (this로 참조)
- `run`: 객체의 컨텍스트에서 코드 실행 후 마지막 표현식 결과 반환 (this로 참조)
- `with`: 특정 객체를 컨텍스트로 코드 블록 실행 (this로 참조, 확장 함수 아님)
- `also`: 객체에 부수 효과를 실행하고 객체 자체 반환 (it으로 참조)

### c) 중위 함수(infix function)와 연산자 오버로딩으로 가독성 높이기

**Java:**
```java
public class Pair<A, B> {
    private final A first;
    private final B second;
    
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public A getFirst() { return first; }
    public B getSecond() { return second; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && 
               Objects.equals(second, pair.second);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

// 사용
Pair<String, Integer> pair = new Pair<>("answer", 42);
```

**Kotlin:**
```kotlin
data class Pair<A, B>(val first: A, val second: B)

// 중위 함수 정의
infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

// 연산자 오버로딩
operator fun <A, B, C> Pair<A, B>.plus(other: Pair<B, C>): Pair<A, C> {
    return Pair(first, other.second)
}

// 사용
val pair1 = "answer" to 42  // 중위 함수 사용
val pair2 = 42 to "meaning"
val combined = pair1 + pair2  // 연산자 오버로딩 사용

// 실제 코틀린에는 이미 to 중위 함수가 있어 Map 생성 시 유용
val map = mapOf("one" to 1, "two" to 2, "three" to 3)
```

**설명:** 코틀린에서는 중위 함수(infix function)와 연산자 오버로딩을 통해 더 읽기 쉽고 자연스러운 DSL을 만들 수 있습니다. 중위 함수는 점과 괄호 없이 호출할 수 있으며, 연산자 오버로딩을 통해 +, -, *, / 등의 연산자에 새로운 의미를 부여할 수 있습니다.

## 5. 관용적인 코틀린 패턴 사용하기

### a) 싱글톤은 object 키워드로 간결하게 구현하기

**Java:**
```java
public class DatabaseManager {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    
    private DatabaseManager() {
        // 초기화 코드
    }
    
    public static DatabaseManager getInstance() {
        return INSTANCE;
    }
    
    public void connect() {
        System.out.println("Connected to database");
    }
}

// 사용
DatabaseManager.getInstance().connect();
```

**Kotlin:**
```kotlin
object DatabaseManager {
    init {
        // 초기화 코드
    }
    
    fun connect() {
        println("Connected to database")
    }
}

// 사용
DatabaseManager.connect()
```

**설명:** 코틀린의 `object` 키워드를 사용하면 싱글톤 패턴을 언어 차원에서 지원하여 보일러플레이트 없이 간결하게 구현할 수 있습니다.

### b) when 식을 if-else 체인 대신 사용하기

**Java:**
```java
public double calculateShipping(Product product) {
    double shipping;
    
    if (product.getWeight() < 1) {
        shipping = 1.99;
    } else if (product.getWeight() < 5) {
        shipping = 4.99;
    } else if (product.getWeight() < 20) {
        shipping = 9.99;
    } else {
        shipping = 19.99;
    }
    
    if (product.isExpressShipping()) {
        shipping *= 1.5;
    }
    
    return shipping;
}
```

**Kotlin:**
```kotlin
fun calculateShipping(product: Product): Double {
    val baseShipping = when {
        product.weight < 1 -> 1.99
        product.weight < 5 -> 4.99
        product.weight < 20 -> 9.99
        else -> 19.99
    }
    
    return when {
        product.isExpressShipping -> baseShipping * 1.5
        else -> baseShipping
    }
}
```

**설명:** 코틀린의 `when` 식은 자바의 `switch`보다 강력하며, 조건식도 지원합니다. 또한 식(expression)으로 사용할 수 있어 값을 반환할 수 있습니다.

### c) sealed class로 제한된 계층 구조 만들기

**Java:**
```java
// Java 17+에서는 sealed 클래스 지원하지만, 여전히 제한적
public abstract sealed class Result 
    permits Success, Error, Loading {
    
    private Result() {}
    
    // 각 서브클래스마다 필요한 public 생성자와 getter가 필요
}

public final class Success extends Result {
    private final Object data;
    
    public Success(Object data) {
        this.data = data;
    }
    
    public Object getData() {
        return data;
    }
}

public final class Error extends Result {
    private final String message;
    
    public Error(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}

public final class Loading extends Result {
    public Loading() {}
}

// 사용
public void handleResult(Result result) {
    if (result instanceof Success success) {
        Object data = success.getData();
        // 성공 처리
    } else if (result instanceof Error error) {
        String message = error.getMessage();
        // 오류 처리
    } else if (result instanceof Loading) {
        // 로딩 처리
    }
}
```

**Kotlin:**
```kotlin
sealed class Result {
    data class Success(val data: Any) : Result()
    data class Error(val message: String) : Result()
    object Loading : Result()
}

// 사용
fun handleResult(result: Result) {
    when (result) {
        is Result.Success -> {
            val data = result.data
            // 성공 처리
        }
        is Result.Error -> {
            val message = result.message
            // 오류 처리
        }
        is Result.Loading -> {
            // 로딩 처리
        }
        // else 필요 없음 - 컴파일러가 모든 케이스 체크
    }
}
```

**설명:** 코틀린의 sealed 클래스는 제한된 계층 구조를 만들 수 있게 해주며, when 식과 함께 사용할 때 컴파일러가 모든 케이스를 처리했는지 확인해줍니다. 이는 대수적 데이터 타입(Algebraic Data Types)을 구현하는 좋은 방법입니다.

## 6. 멀티 패러다임 프로그래밍 활용하기

### a) 객체 지향 프로그래밍(OOP)

#### i) 클래스 상속과 인터페이스 구현으로 다형성 활용하기

**Java:**
```java
public interface Payable {
    double calculatePayment();
}

public abstract class Employee implements Payable {
    private String name;
    private int id;
    
    // 생성자, getter, setter 생략
    
    @Override
    public abstract double calculatePayment();
}

public class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;
    
    // 생성자, getter, setter 생략
    
    @Override
    public double calculatePayment() {
        return hourlyRate * hoursWorked;
    }
}

public class SalariedEmployee extends Employee {
    private double monthlySalary;
    
    // 생성자, getter, setter 생략
    
    @Override
    public double calculatePayment() {
        return monthlySalary;
    }
}
```

**Kotlin:**
```kotlin
interface Payable {
    fun calculatePayment(): Double
}

abstract class Employee(
    val name: String,
    val id: Int
) : Payable {
    abstract override fun calculatePayment(): Double
}

class HourlyEmployee(
    name: String,
    id: Int,
    val hourlyRate: Double,
    val hoursWorked: Double
) : Employee(name, id) {
    
    override fun calculatePayment(): Double = hourlyRate * hoursWorked
}

class SalariedEmployee(
    name: String,
    id: Int,
    val monthlySalary: Double
) : Employee(name, id) {
    
    override fun calculatePayment(): Double = monthlySalary
}
```

**설명:** 코틀린은 자바와 마찬가지로 클래스 상속과 인터페이스 구현을 지원하지만, 더 간결한 문법을 제공합니다. 또한 기본적으로 모든 클래스와 메서드는 final이므로, 상속과 오버라이드를 허용하려면 `open` 키워드가 필요합니다.

#### ii) 프로퍼티와 커스텀 getter/setter로 캡슐화 강화하기

**Java (with Lombok):**
```java
import lombok.Getter;

public class BankAccount {
    @Getter private final String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }
    
    // 커스텀 getter - 계산된 속성
    public boolean isOverdrawn() {
        return balance < 0;
    }
}
```

**Kotlin:**
```kotlin
class BankAccount(
    val accountNumber: String,
    initialBalance: Double
) {
    var balance: Double = initialBalance
        private set  // setter는 클래스 내부에서만 접근 가능
    
    fun deposit(amount: Double) {
        require(amount > 0) { "Deposit amount must be positive" }
        balance += amount
    }
    
    fun withdraw(amount: Double) {
        require(amount > 0) { "Withdrawal amount must be positive" }
        require(amount <= balance) { "Insufficient funds" }
        balance -= amount
    }
    
    // 계산된 속성 - getter만 있는 읽기 전용 프로퍼티
    val isOverdrawn: Boolean
        get() = balance < 0
    
    // 커스텀 getter와 setter가 있는 프로퍼티
    var interestRate: Double = 0.0
        get() = field  // 기본 getter
        set(value) {
            require(value >= 0.0) { "Interest rate cannot be negative" }
            field = value  // 'field'는 backing field를 참조
        }
}
```

**설명:** 코틀린에서는 프로퍼티가 언어의 일급 시민으로, getter와 setter를 쉽게 커스터마이징할 수 있습니다. `private set`을 통해 내부에서만 수정 가능한 프로퍼티를 만들거나, 계산된 속성(backing field가 없는 프로퍼티)을 쉽게 정의할 수 있습니다.

#### iii) 위임 패턴(by 키워드)으로 상속의 대안 제공하기

**Java:**
```java
public interface Repository {
    void save(Object entity);
    Object findById(String id);
    void delete(String id);
}

// 구현 클래스
public class DatabaseRepository implements Repository {
    @Override
    public void save(Object entity) {
        System.out.println("Saving to database");
    }
    
    @Override
    public Object findById(String id) {
        System.out.println("Finding in database");
        return new Object();
    }
    
    @Override
    public void delete(String id) {
        System.out.println("Deleting from database");
    }
}

// 위임을 수동으로 구현
public class CachingRepository implements Repository {
    private final Repository delegate;
    private final Map<String, Object> cache = new HashMap<>();
    
    public CachingRepository(Repository delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public void save(Object entity) {
        delegate.save(entity);
        // 캐시 관련 로직 추가
    }
    
    @Override
    public Object findById(String id) {
        // 캐싱 로직
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        Object result = delegate.findById(id);
        cache.put(id, result);
        return result;
    }
    
    @Override
    public void delete(String id) {
        delegate.delete(id);
        cache.remove(id);
    }
}
```

**Kotlin:**
```kotlin
interface Repository {
    fun save(entity: Any)
    fun findById(id: String): Any
    fun delete(id: String)
}

// 구현 클래스
class DatabaseRepository : Repository {
    override fun save(entity: Any) {
        println("Saving to database")
    }
    
    override fun findById(id: String): Any {
        println("Finding in database")
        return Any()
    }
    
    override fun delete(id: String) {
        println("Deleting from database")
    }
}

// 위임 패턴 사용
class CachingRepository(private val delegate: Repository) : Repository by delegate {
    private val cache = mutableMapOf<String, Any>()
    
    // findById만 오버라이드 - 다른 메서드는 delegate에서 자동으로 위임
    override fun findById(id: String): Any {
        return cache.getOrPut(id) { delegate.findById(id) }
    }
    
    override fun delete(id: String) {
        delegate.delete(id)
        cache.remove(id)
    }
}
```

**설명:** 코틀린의 `by` 키워드를 사용하면 인터페이스 구현을 다른 객체에 위임할 수 있습니다. 이는 "상속보다 합성"이라는 원칙을 쉽게 따를 수 있게 해주며, 필요한 메서드만 오버라이드하면서 나머지는 자동으로 위임됩니다.

### b) 함수형 프로그래밍(FP)

#### i) 함수를 first-class citizen으로 활용하기

**Java:**
```java
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalExample {
    public <T, R> List<R> map(List<T> items, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : items) {
            result.add(mapper.apply(item));
        }
        return result;
    }
    
    public <T> List<T> filter(List<T> items, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
    
    // 함수 합성
    public <T, R, V> Function<T, V> compose(Function<T, R> f, Function<R, V> g) {
        return t -> g.apply(f.apply(t));
    }
    
    // 사용 예
    public void example() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        
        // 함수 참조
        Function<Integer, Integer> square = this::square;
        
        // 람다 표현식
        Predicate<Integer> isEven = n -> n % 2 == 0;
        
        // 함수 합성
        Function<Integer, String> squareAndFormat = 
            compose(square, n -> "Square: " + n);
        
        List<Integer> squares = map(numbers, square);
        List<Integer> evenNumbers = filter(numbers, isEven);
        List<String> formattedSquares = map(numbers, squareAndFormat);
    }
    
    private int square(int n) {
        return n * n;
    }
}
```

**Kotlin:**
```kotlin
class FunctionalExample {
    fun <T, R> map(items: List<T>, mapper: (T) -> R): List<R> {
        return items.map(mapper)  // 기본 제공 함수 사용
    }
    
    fun <T> filter(items: List<T>, predicate: (T) -> Boolean): List<T> {
        return items.filter(predicate)  // 기본 제공 함수 사용
    }
    
    // 함수 합성
    fun <T, R, V> compose(f: (T) -> R, g: (R) -> V): (T) -> V {
        return { t -> g(f(t)) }
    }
    
    // 사용 예
    fun example() {
        val numbers = listOf(1, 2, 3, 4, 5)
        
        // 함수 참조
        val square: (Int) -> Int = ::square
        
        // 람다 표현식
        val isEven: (Int) -> Boolean = { n -> n % 2 == 0 }
        
        // 함수 합성
        val squareAndFormat = compose(square) { n -> "Square: $n" }
        
        val squares = map(numbers, square)
        val evenNumbers = filter(numbers, isEven)
        val formattedSquares = map(numbers, squareAndFormat)
        
        // 함수를 변수에 할당
        val adder: (Int, Int) -> Int = { a, b -> a + b }
        val result = adder(5, 3)  // 8
        
        // 함수 타입을 매개변수로 사용
        higherOrderFunction { x -> x * 2 }
    }
    
    private fun square(n: Int): Int {
        return n * n
    }
    
    private fun higherOrderFunction(func: (Int) -> Int) {
        val result = func(4)  // 8
        println("Result: $result")
    }
}
```

**설명:** 코틀린에서는 함수 타입이 언어에 내장되어 있어 함수를 변수에 할당하거나, 매개변수로 전달하거나, 반환값으로 사용하기가 더 자연스럽습니다. 또한 함수 참조(::)와 람다 표현식 문법도 더 간결합니다.

#### ii) 람다와 클로저로 표현력 높은 코드 작성하기

**Java:**
```java
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class LambdaExample {
    public void processItems(List<String> items, Consumer<String> processor) {
        for (String item : items) {
            processor.accept(item);
        }
    }
    
    public void example() {
        List<String> names = List.of("Alice", "Bob", "Charlie");
        
        // 간단한 람다
        processItems(names, name -> System.out.println("Hello, " + name));
        
        // 외부 변수 캡처 (클로저)
        String greeting = "Welcome";
        processItems(names, name -> System.out.println(greeting + ", " + name));
        
        // 람다와 메서드 참조
        processItems(names, System.out::println);
        
        // 람다를 사용한 변환
        Function<String, Integer> lengthFunc = String::length;
        List<Integer> lengths = names.stream()
                .map(lengthFunc)
                .toList();
    }
}
```

**Kotlin:**
```kotlin
class LambdaExample {
    fun processItems(items: List<String>, processor: (String) -> Unit) {
        for (item in items) {
            processor(item)
        }
    }
    
    fun example() {
        val names = listOf("Alice", "Bob", "Charlie")
        
        // 간단한 람다
        processItems(names) { name -> println("Hello, $name") }
        
        // 단일 매개변수일 때 it 사용
        processItems(names) { println("Hello, $it") }
        
        // 외부 변수 캡처 (클로저)
        val greeting = "Welcome"
        processItems(names) { println("$greeting, $it") }
        
        // 람다와 메서드 참조
        processItems(names, ::println)
        
        // trailing lambda - 마지막 인자가 함수인 경우 괄호 밖으로 뺄 수 있음
        names.forEach { println(it) }
        
        // 람다를 사용한 변환
        val lengthFunc: (String) -> Int = String::length
        val lengths = names.map(lengthFunc)
        
        // 람다로 인터페이스 구현
        val runnable = Runnable { println("Running") }
        
        // 수신 객체 지정 람다 (확장 람다)
        val append: StringBuilder.() -> Unit = { append("text") }
        val sb = StringBuilder().apply(append)
    }
    
    // DSL 스타일 코드 작성
    fun buildString(builderAction: StringBuilder.() -> Unit): String {
        val sb = StringBuilder()
        sb.builderAction()
        return sb.toString()
    }
    
    fun dslExample() {
        val result = buildString {
            append("Hello")
            append(", ")
            append("World!")
        }
        println(result)  // Hello, World!
    }
}
```

**설명:** 코틀린에서는 람다 표현식이 더 간결하고 다양한 형태로 사용될 수 있습니다. 특히 수신 객체 지정 람다(extension lambda)를 통해 DSL을 쉽게 만들 수 있으며, 마지막 인자가 함수인 경우 괄호 밖으로 빼는 trailing lambda 문법으로 더 읽기 쉬운 코드를 작성할 수 있습니다.

#### iii) 재귀와 꼬리 재귀 최적화(tailrec) 활용하기

**Java:**
```java
public class RecursionExample {
    // 일반 재귀 - 스택 오버플로우 위험 있음
    public long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
    
    // 꼬리 재귀로 재작성
    public long factorialTail(int n) {
        return factorialHelper(n, 1);
    }
    
    private long factorialHelper(int n, long acc) {
        if (n <= 1) return acc;
        return factorialHelper(n - 1, n * acc);
    }
    
    // 꼬리 재귀를 루프로 최적화 (자바에서는 수동으로 해야 함)
    public long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
```

**Kotlin:**
```kotlin
class RecursionExample {
    // 일반 재귀 - 스택 오버플로우 위험 있음
    fun factorial(n: Int): Long {
        if (n <= 1) return 1
        return n * factorial(n - 1)
    }
    
    // 꼬리 재귀 최적화 - 컴파일러가 자동으로 루프로 변환
    tailrec fun factorialTail(n: Int, acc: Long = 1): Long {
        if (n <= 1) return acc
        return factorialTail(n - 1, n * acc)
    }
    
    // 피보나치도 꼬리 재귀로 구현
    tailrec fun fibonacci(n: Int, a: Long = 0, b: Long = 1): Long {
        if (n == 0) return a
        return fibonacci(n - 1, b, a + b)
    }
}
```

**설명:** 코틀린에서는 `tailrec` 키워드를 사용하여 꼬리 재귀 함수를 최적화할 수 있습니다. 컴파일러가 자동으로 꼬리 재귀 호출을 루프로 변환하여 스택 오버플로우를 방지합니다. 이는 함수형 프로그래밍에서 중요한 패턴인 재귀를 안전하게 사용할 수 있게 해줍니다.

#### iv) 모나딕 접근(Option, Result 패턴 등)으로 오류 처리하기

**Java:**
```java
import java.util.Optional;

public class UserService {
    // Optional을 사용한 null 처리
    public Optional<User> findUserById(String id) {
        // 사용자를 찾지 못하면 Optional.empty() 반환
        if (userExists(id)) {
            return Optional.of(new User(id, "User " + id));
        }
        return Optional.empty();
    }
    
    // 결과와 오류를 함께 처리하는 패턴 (자바에는 내장되어 있지 않음)
    public Result<User> createUser(String name, String email) {
        if (name == null || name.isEmpty()) {
            return Result.failure("Name cannot be empty");
        }
        if (email == null || !isValidEmail(email)) {
            return Result.failure("Invalid email");
        }
        try {
            User user = new User(generateId(), name, email);
            saveUser(user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.failure("Failed to create user: " + e.getMessage());
        }
    }
    
    // Result 클래스 구현 (자바에서는 직접 만들어야 함)
    public static class Result<T> {
        private final T value;
        private final String error;
        private final boolean isSuccess;
        
        private Result(T value, String error, boolean isSuccess) {
            this.value = value;
            this.error = error;
            this.isSuccess = isSuccess;
        }
        
        public static <T> Result<T> success(T value) {
            return new Result<>(value, null, true);
        }
        
        public static <T> Result<T> failure(String error) {
            return new Result<>(null, error, false);
        }
        
        public boolean isSuccess() {
            return isSuccess;
        }
        
        public T getValue() {
            if (!isSuccess) throw new IllegalStateException("Cannot get value from failed result");
            return value;
        }
        
        public String getError() {
            if (isSuccess) throw new IllegalStateException("Cannot get error from successful result");
            return error;
        }
        
        public <R> Result<R> map(Function<T, R> mapper) {
            if (!isSuccess) return failure(error);
            return success(mapper.apply(value));
        }
        
        public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
            if (!isSuccess) return failure(error);
            return mapper.apply(value);
        }
    }
    
    // 사용 예시
    public void example() {
        // Optional 사용
        Optional<User> userOpt = findUserById("123");
        userOpt.ifPresent(user -> System.out.println("Found: " + user.getName()));
        
        // Result 패턴 사용
        Result<User> result = createUser("John", "john@example.com");
        if (result.isSuccess()) {
            User user = result.getValue();
            System.out.println("Created: " + user.getName());
        } else {
            System.out.println("Error: " + result.getError());
        }
        
        // 체이닝
        Result<String> nameResult = createUser("Jane", "jane@example.com")
                .map(User::getName);
    }
    
    // 헬퍼 메서드들 (구현 생략)
    private boolean userExists(String id) { return false; }
    private boolean isValidEmail(String email) { return true; }
    private String generateId() { return "id"; }
    private void saveUser(User user) {}
}
```

**Kotlin:**
```kotlin
// 코틀린에서는 Result 클래스가 내장되어 있음 (1.5 이상)
class UserService {
    // Nullable 타입과 Elvis 연산자로 null 처리
    fun findUserById(id: String): User? {
        return if (userExists(id)) {
            User(id, "User $id")
        } else {
            null
        }
    }
    
    // sealed class로 Result 패턴 구현
    sealed class Result<out T> {
        data class Success<T>(val value: T) : Result<T>()
        data class Failure(val error: String) : Result<Nothing>()
    }
    
    fun createUser(name: String, email: String): Result<User> {
        if (name.isEmpty()) {
            return Result.Failure("Name cannot be empty")
        }
        if (!isValidEmail(email)) {
            return Result.Failure("Invalid email")
        }
        return try {
            val user = User(generateId(), name, email)
            saveUser(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Failure("Failed to create user: ${e.message}")
        }
    }
    
    // 또는 코틀린 내장 Result 사용 (runCatching)
    fun createUserWithRunCatching(name: String, email: String): Result<User> {
        // 입력 검증
        if (name.isEmpty() || !isValidEmail(email)) {
            return Result.failure(IllegalArgumentException("Invalid input"))
        }
        
        // runCatching을 사용한 예외 처리
        return runCatching {
            val user = User(generateId(), name, email)
            saveUser(user)
            user
        }
    }
    
    // 사용 예시
    fun example() {
        // Nullable 사용
        val user = findUserById("123")
        user?.let { println("Found: ${it.name}") }
        
        // 커스텀 Result 패턴 사용
        when (val result = createUser("John", "john@example.com")) {
            is Result.Success -> println("Created: ${result.value.name}")
            is Result.Failure -> println("Error: ${result.error}")
        }
        
        // 내장 Result 사용
        createUserWithRunCatching("Jane", "jane@example.com")
            .onSuccess { println("Created: ${it.name}") }
            .onFailure { println("Error: ${it.message}") }
        
        // 함수형 체이닝
        val nameResult = createUserWithRunCatching("Jane", "jane@example.com")
            .map { it.name }
        
        // fold로 결과 처리
        val displayName = nameResult.fold(
            onSuccess = { it },
            onFailure = { "Unknown" }
        )
    }
    
    // 헬퍼 메서드들 (구현 생략)
    private fun userExists(id: String): Boolean = false
    private fun isValidEmail(email: String): Boolean = true
    private fun generateId(): String = "id"
    private fun saveUser(user: User) {}
}
```

**설명:** 코틀린에서는 sealed class를 사용하여 대수적 데이터 타입처럼 Result 패턴을 구현하거나, 내장된 `Result` 클래스와 `runCatching` 함수를 사용할 수 있습니다. 이러한 모나딕 접근 방식은 예외 처리나 null 처리를 더 함수형 스타일로 처리할 수 있게 해줍니다.

### c) 절차적 프로그래밍

#### i) 최상위 함수와 프로퍼티 활용하기

**Java:**
```java
// Java에서는 최상위 함수가 없음, 항상 클래스 필요
public class StringUtils {
    public static final String DEFAULT_SEPARATOR = ", ";
    
    public static String join(List<String> items) {
        return join(items, DEFAULT_SEPARATOR);
    }
    
    public static String join(List<String> items, String separator) {
        return String.join(separator, items);
    }
}

// 다른 클래스에서 사용
public class Example {
    public void useUtils() {
        List<String> items = List.of("apple", "banana", "orange");
        String result = StringUtils.join(items);
        System.out.println(result);
    }
}
```

**Kotlin:**
```kotlin
// utilities.kt 파일에 최상위 선언
const val DEFAULT_SEPARATOR = ", "

fun join(items: List<String>, separator: String = DEFAULT_SEPARATOR): String {
    return items.joinToString(separator)
}

// 최상위 확장 함수
fun List<String>.joinToString(separator: String = DEFAULT_SEPARATOR): String {
    return this.joinToString(separator)
}

// 다른 파일에서 사용
class Example {
    fun useUtils() {
        val items = listOf("apple", "banana", "orange")
        
        // 최상위 함수 직접 호출
        val result1 = join(items)
        
        // 확장 함수 사용
        val result2 = items.joinToString()
        
        println(result1)
        println(result2)
    }
}
```

**설명:** 코틀린에서는 최상위 함수와 프로퍼티를 정의할 수 있어, 유틸리티 클래스를 만들지 않고도 기능을 그룹화할 수 있습니다. 이는 절차적 프로그래밍 스타일을 지원하면서도 코드를 더 모듈화하고 조직화할 수 있게 해줍니다.

#### ii) 확장 함수로 모듈화 개선하기

**Java:**
```java
// 자바에서 유틸리티 메서드들은 보통 별도 클래스에 모음
public class DateUtils {
    public static LocalDate parseOrDefault(String dateStr, LocalDate defaultDate) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return defaultDate;
        }
    }
    
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}

// 사용 예
public class DateProcessor {
    public void process() {
        String input = "2023-09-15";
        LocalDate date = DateUtils.parseOrDefault(input, LocalDate.now());
        boolean isWeekend = DateUtils.isWeekend(date);
    }
}
```

**Kotlin:**
```kotlin
// 확장 함수로 모듈화 (LocalDateExtensions.kt 파일)
fun String.toLocalDateOrDefault(default: LocalDate = LocalDate.now()): LocalDate {
    return try {
        LocalDate.parse(this)
    } catch (e: DateTimeParseException) {
        default
    }
}

fun LocalDate.isWeekend(): Boolean {
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
}

// 비즈니스 로직에 특화된 확장 함수 (별도 파일로 구성 가능)
fun LocalDate.isHoliday(): Boolean {
    // 휴일 체크 로직
    return false
}

fun LocalDate.isBusinessDay(): Boolean {
    return !isWeekend() && !isHoliday()
}

// 사용 예
class DateProcessor {
    fun process() {
        val input = "2023-09-15"
        val date = input.toLocalDateOrDefault()
        val isWeekend = date.isWeekend()
        val isBusinessDay = date.isBusinessDay()
    }
}
```

**설명:** 코틀린의 확장 함수를 사용하면 기존 클래스에 기능을 추가하면서도 코드를 더 모듈화하고 도메인별로 구성할 수 있습니다. 이는 절차적 프로그래밍의 단점인 모듈화 부족 문제를 해결하면서도, 객체 지향적으로 API를 설계할 수 있게 해줍니다.

### d) 동시성 프로그래밍

#### i) 코루틴으로 비동기 프로그래밍 단순화하기

**Java (CompletableFuture 사용):**
```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserService {
    public CompletableFuture<User> fetchUser(long userId) {
        return CompletableFuture.supplyAsync(() -> {
            // 네트워크 호출 시뮬레이션
            sleep(1000);
            return new User(userId, "User " + userId);
        });
    }
    
    public CompletableFuture<UserProfile> fetchUserProfile(User user) {
        return CompletableFuture.supplyAsync(() -> {
            // 네트워크 호출 시뮬레이션
            sleep(1000);
            return new UserProfile(user.getId(), "Profile for " + user.getName());
        });
    }
    
    // 연쇄 호출 (체이닝)
    public CompletableFuture<UserProfile> fetchUserAndProfile(long userId) {
        return fetchUser(userId)
                .thenCompose(this::fetchUserProfile)
                .exceptionally(ex -> {
                    System.err.println("Error: " + ex.getMessage());
                    return null;
                });
    }
    
    // 병렬 호출
    public CompletableFuture<UserStats> fetchUserStats(long userId) {
        CompletableFuture<User> userFuture = fetchUser(userId);
        CompletableFuture<List<Order>> ordersFuture = 
                CompletableFuture.supplyAsync(() -> fetchOrders(userId));
        
        return CompletableFuture.allOf(userFuture, ordersFuture)
                .thenApply(v -> {
                    try {
                        User user = userFuture.get();
                        List<Order> orders = ordersFuture.get();
                        return new UserStats(user, orders.size());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new CompletionException(e);
                    }
                });
    }
    
    private List<Order> fetchOrders(long userId) {
        // 네트워크 호출 시뮬레이션
        sleep(1000);
        return List.of(new Order(1L), new Order(2L));
    }
    
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

**Kotlin (코루틴 사용):**
```kotlin
import kotlinx.coroutines.*

class UserService {
    suspend fun fetchUser(userId: Long): User {
        // 코루틴 내에서 비동기 처리
        delay(1000)  // 네트워크 호출 시뮬레이션 (스레드 차단 없음)
        return User(userId, "User $userId")
    }
    
    suspend fun fetchUserProfile(user: User): UserProfile {
        delay(1000)  // 네트워크 호출 시뮬레이션
        return UserProfile(user.id, "Profile for ${user.name}")
    }
    
    // 연쇄 호출 (순차적 실행)
    suspend fun fetchUserAndProfile(userId: Long): UserProfile {
        return try {
            val user = fetchUser(userId)
            fetchUserProfile(user)
        } catch (e: Exception) {
            println("Error: ${e.message}")
            throw e
        }
    }
    
    // 병렬 호출
    suspend fun fetchUserStats(userId: Long): UserStats {
        return coroutineScope {
            // async로 두 작업을 병렬로 시작
            val userDeferred = async { fetchUser(userId) }
            val ordersDeferred = async { fetchOrders(userId) }
            
            // 두 작업이 완료될 때까지 대기 후 결과 조합
            val user = userDeferred.await()
            val orders = ordersDeferred.await()
            UserStats(user, orders.size)
        }
    }
    
    private suspend fun fetchOrders(userId: Long): List<Order> {
        delay(1000)  // 네트워크 호출 시뮬레이션
        return listOf(Order(1L), Order(2L))
    }
    
    // 타임아웃 처리
    suspend fun fetchUserWithTimeout(userId: Long): User {
        return withTimeout(1500) {
            fetchUser(userId)
        }
    }
    
    // 에러 처리와 재시도 로직
    suspend fun fetchUserWithRetry(userId: Long): User {
        return retry(times = 3) {
            fetchUser(userId)
        }
    }
    
    private suspend fun <T> retry(
        times: Int,
        initialDelay: Long = 100,
        maxDelay: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: Exception) {
                // 재시도 전 지수 백오프 대기
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
            }
        }
        // 마지막 시도
        return block()
    }
}

// 사용 예
suspend fun main() {
    val userService = UserService()
    
    // 단일 코루틴에서 실행
    val profile = userService.fetchUserAndProfile(123)
    println("Profile: ${profile.description}")
    
    // 코루틴 스코프 내에서 실행
    coroutineScope {
        val userStats = userService.fetchUserStats(123)
        println("User ${userStats.user.name} has ${userStats.orderCount} orders")
        
        // launch로 새 코루틴 시작 (fire-and-forget)
        launch {
            val user = userService.fetchUser(456)
            println("Fetched user: ${user.name}")
        }
    }
}
```

**설명:** 코틀린의 코루틴은 비동기 프로그래밍을 단순화합니다. `suspend` 함수를 사용하면 비동기 코드를 마치 동기 코드처럼 작성할 수 있어 가독성이 높아지고, 콜백 지옥을 피할 수 있습니다. 또한 구조화된 동시성(structured concurrency)을 통해 메모리 누수나 예외 처리 문제를 해결할 수 있습니다.

#### ii) 구조적 동시성(structured concurrency)으로 안전한 비동기 코드 작성하기

**Java (비구조적 동시성):**
```java
import java.util.concurrent.*;

public class DownloadManager {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public void downloadFiles(List<String> urls) {
        List<CompletableFuture<File>> futures = new ArrayList<>();
        
        for (String url : urls) {
            CompletableFuture<File> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return downloadFile(url);
                } catch (Exception e) {
                    System.err.println("Failed to download: " + url);
                    return null;
                }
            }, executor);
            
            futures.add(future);
        }
        
        // 모든 다운로드 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> System.out.println("All downloads completed"));
                
        // 명시적인 종료 처리가 없으면 리소스 누수 가능성
    }
    
    private File downloadFile(String url) {
        // 다운로드 로직
        try {
            Thread.sleep(1000); // 다운로드 시뮬레이션
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new File(url.substring(url.lastIndexOf('/') + 1));
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
```

**Kotlin (구조적 동시성):**
```kotlin
import kotlinx.coroutines.*

class DownloadManager {
    // 다운로드 작업을 위한 코루틴 디스패처
    private val downloadDispatcher = Dispatchers.IO.limitedParallelism(4)
    
    suspend fun downloadFiles(urls: List<String>): List<File> {
        return coroutineScope {
            // urls의 각 항목에 대해 async 블록 생성 (병렬 실행)
            val downloadJobs = urls.map { url ->
                async(downloadDispatcher) {
                    try {
                        downloadFile(url)
                    } catch (e: Exception) {
                        println("Failed to download: $url")
                        null
                    }
                }
            }
            
            // 모든 작업이 완료될 때까지 대기하고 결과 수집
            downloadJobs.awaitAll().filterNotNull()
        } // coroutineScope가 모든 자식 코루틴이 완료될 때까지 보장
    }
    
    // 취소 가능한 다운로드 함수
    suspend fun downloadFilesWithProgress(
        urls: List<String>,
        onProgress: (Int, Int) -> Unit
    ): List<File> = coroutineScope {
        val downloadJobs = urls.mapIndexed { index, url ->
            async(downloadDispatcher) {
                val file = downloadFile(url)
                onProgress(index + 1, urls.size)
                file
            }
        }
        
        // 구조적 동시성 - 모든 자식 코루틴은 이 코루틴 스코프가 취소되면 함께 취소됨
        downloadJobs.awaitAll()
    }
    
    private suspend fun downloadFile(url: String): File {
        // 다운로드 로직
        delay(1000) // 다운로드 시뮬레이션
        
        // 주기적으로 취소 여부 확인
        ensureActive() // 코루틴이 취소됐는지 확인
        
        return File(url.substring(url.lastIndexOf('/') + 1))
    }
    
    // 타임아웃 적용
    suspend fun downloadWithTimeout(url: String): File {
        return withTimeout(5000) {
            downloadFile(url)
        }
    }
}

// 사용 예
suspend fun main() {
    val downloadManager = DownloadManager()
    val urls = listOf("https://example.com/file1.zip", "https://example.com/file2.zip")
    
    try {
        val files = downloadManager.downloadFiles(urls)
        println("Downloaded ${files.size} files")
        
        // 진행 상황 표시
        downloadManager.downloadFilesWithProgress(urls) { done, total ->
            println("Progress: $done/$total")
        }
        
        // 부모 코루틴 스코프에서 자식 코루틴 관리
        coroutineScope {
            val job = launch {
                val file = downloadManager.downloadWithTimeout(urls[0])
                println("Downloaded: ${file.name}")
            }
            
            delay(2000)
            // 필요시 명시적 취소
            // job.cancel()
        }
    } catch (e: TimeoutCancellationException) {
        println("Download timed out")
    } catch (e: CancellationException) {
        println("Download was cancelled")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
```

**설명:** 코틀린의 구조적 동시성은 모든 코루틴이 수명이 제한된 코루틴 스코프 내에서 시작되도록 보장합니다. 이는 "부모-자식" 관계를 형성하여 자원 누수를 방지하고 오류 전파와 취소를 일관되게 처리할 수 있게 합니다. 자바의 ExecutorService와 달리, 코루틴 스코프가 종료되면 그 안에서 시작된 모든 코루틴이 자동으로 취소됩니다.

#### iii) Flow API로 비동기 스트림 처리하기

**Java (Reactive Streams, e.g. RxJava):**
```java
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SensorDataProcessor {
    // 데이터 스트림 생성
    public Flowable<Integer> sensorDataStream() {
        return Flowable.range(1, 100)
                .map(i -> i * i)
                .filter(i -> i % 3 == 0)
                .subscribeOn(Schedulers.io());
    }
    
    // 스트림 변환 및 처리
    public void processSensorData() {
        sensorDataStream()
                .buffer(10)  // 10개씩 묶음
                .map(this::calculateAverage)
                .observeOn(Schedulers.computation())
                .subscribe(
                        avg -> System.out.println("Average: " + avg),
                        err -> System.err.println("Error: " + err),
                        () -> System.out.println("Completed")
                );
    }
    
    // 여러 스트림 결합
    public Flowable<CombinedData> combineDataStreams() {
        Flowable<Integer> tempStream = sensorDataStream();
        Flowable<Integer> humidityStream = Flowable.range(1, 100)
                .subscribeOn(Schedulers.io());
        
        return Flowable.zip(
                tempStream,
                humidityStream,
                (temp, humidity) -> new CombinedData(temp, humidity)
        );
    }
    
    private double calculateAverage(List<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).average().orElse(0);
    }
    
    static class CombinedData {
        final int temperature;
        final int humidity;
        
        CombinedData(int temperature, int humidity) {
            this.temperature = temperature;
            this.humidity = humidity;
        }
    }
}
```

**Kotlin (Flow API):**
```kotlin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SensorDataProcessor {
    // 데이터 스트림 생성
    fun sensorDataStream(): Flow<Int> = flow {
        for (i in 1..100) {
            delay(100)  // 센서 데이터 생성 시간 시뮬레이션
            emit(i * i)
        }
    }.filter { it % 3 == 0 }
      .flowOn(Dispatchers.IO)  // 데이터 생성 및 가공을 IO 디스패처에서 수행
    
    // 스트림 변환 및 처리
    suspend fun processSensorData() {
        sensorDataStream()
            .buffer(10)  // 10개씩 묶음
            .map { values -> calculateAverage(values) }
            .flowOn(Dispatchers.Default)  // 계산 작업은 Default 디스패처에서
            .collect { avg ->
                println("Average: $avg")
            }
    }
    
    // 여러 스트림 결합
    fun combineDataStreams(): Flow<CombinedData> {
        val tempStream = sensorDataStream()
        val humidityStream = flow {
            for (i in 1..100) {
                delay(150)
                emit(i)
            }
        }
        
        return tempStream.zip(humidityStream) { temp, humidity ->
            CombinedData(temp, humidity)
        }
    }
    
    // 백프레셔 처리
    suspend fun collectWithBackpressure() {
        sensorDataStream()
            .collectLatest { value ->  // 처리 중 새 값이 오면 이전 처리 취소
                println("Processing $value")
                delay(500)  // 오래 걸리는 처리 시뮬레이션
                println("Processed $value")
            }
    }
    
    // StateFlow 예시 (UI 상태 관리)
    class SensorViewModel {
        private val _sensorData = MutableStateFlow<Int>(0)
        val sensorData: StateFlow<Int> = _sensorData.asStateFlow()
        
        fun startCollecting() {
            CoroutineScope(Dispatchers.IO).launch {
                sensorDataStream().collect { value ->
                    _sensorData.value = value
                }
            }
        }
    }
    
    // SharedFlow 예시 (이벤트 브로드캐스팅)
    class SensorEventBus {
        private val _events = MutableSharedFlow<SensorEvent>(
            replay = 0,
            extraBufferCapacity = 64
        )
        val events: SharedFlow<SensorEvent> = _events.asSharedFlow()
        
        suspend fun publishEvent(event: SensorEvent) {
            _events.emit(event)
        }
    }
    
    data class SensorEvent(val type: String, val value: Int)
    
    private fun calculateAverage(values: List<Int>): Double {
        return values.average()
    }
    
    data class CombinedData(val temperature: Int, val humidity: Int)
}

// 사용 예
suspend fun main() {
    val processor = SensorDataProcessor()
    
    // 기본 Flow 처리
    coroutineScope {
        launch {
            processor.sensorDataStream()
                .take(5)  // 처음 5개만 처리
                .collect { println("Value: $it") }
        }
        
        // 타임아웃 적용
        launch {
            withTimeoutOrNull(2000) {
                processor.processSensorData()
            }
            println("Processing timed out or completed")
        }
        
        // 여러 스트림 결합
        launch {
            processor.combineDataStreams()
                .take(3)
                .collect { data ->
                    println("Combined: temp=${data.temperature}, humidity=${data.humidity}")
                }
        }
        
        // 예외 처리
        launch {
            processor.sensorDataStream()
                .catch { e -> println("Error caught: ${e.message}") }
                .onCompletion { println("Stream completed") }
                .collect { println(it) }
        }
    }
}
```

**설명:** 코틀린의 Flow API는 비동기 데이터 스트림을 선언적으로 처리할 수 있게 해주는 코루틴 기반 솔루션입니다. RxJava와 유사하지만 코루틴과 통합되어 더 간결한 코드로 비동기 스트림을 처리할 수 있습니다. 또한 Cold Stream 특성을 가지며, 백프레셔 처리, 예외 처리, 취소 전파 등 다양한 기능을 제공합니다.

## 7. 상호운용성 활용하기

### a) 자바 코드와의 원활한 통합을 위한 어노테이션(@JvmStatic, @JvmField 등) 활용하기

**Kotlin:**
```kotlin
// 자바에서 사용하기 좋게 설계된 코틀린 클래스
class UserRepository {
    // 자바에서 접근할 상수
    companion object {
        @JvmField
        val DEFAULT_PAGE_SIZE = 20
        
        @JvmStatic
        fun getInstance(): UserRepository = UserRepository()
        
        // @JvmStatic이 없으면 Companion.getDefaultUser() 형태로 호출해야 함
        @JvmStatic
        fun getDefaultUser(): User = User(0, "Guest")
    }
    
    // 기본 파라미터가 있는 함수
    @JvmOverloads
    fun findUsers(query: String = "", page: Int = 0, pageSize: Int = DEFAULT_PAGE_SIZE): List<User> {
        // 구현 생략
        return emptyList()
    }
    
    // 예외 명시
    @Throws(IOException::class)
    fun saveUser(user: User) {
        // 자바에서 체크드 예외로 인식되게 함
        if (user.id < 0) throw IOException("Invalid user ID")
    }
    
    // 함수 이름 변경
    @JvmName("saveAllUsers")
    fun save(users: List<User>) {
        // List<User> 파라미터용 오버로드
    }
    
    fun save(user: User) {
        // User 파라미터용 오버로드
    }
}

// 코틀린에서만 사용되는 확장 함수는 @JvmSynthetic으로 자바에서 숨김
@JvmSynthetic
fun User.isValid(): Boolean = id >= 0 && name.isNotEmpty()
```

**자바에서 사용:**
```java
public class JavaUserService {
    public void example() {
        // companion object 상수와 정적 메서드
        int pageSize = UserRepository.DEFAULT_PAGE_SIZE;
        User defaultUser = UserRepository.getDefaultUser();
        
        // 인스턴스 생성
        UserRepository repo = UserRepository.getInstance();
        
        // @JvmOverloads로 생성된 오버로드 메서드들
        List<User> allUsers = repo.findUsers();
        List<User> filteredUsers = repo.findUsers("active");
        List<User> pagedUsers = repo.findUsers("active", 1, 10);
        
        try {
            // @Throws로 명시된 예외
            repo.saveUser(new User(1, "John"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // @JvmName으로 이름 변경된 메서드
        repo.saveAllUsers(List.of(new User(1, "John"), new User(2, "Jane")));
        
        // @JvmSynthetic 메서드는 자바에서 보이지 않음
        // user.isValid(); // 컴파일 에러
    }
}
```

**설명:** 코틀린은 자바와의 상호운용성을 위한 다양한 어노테이션을 제공합니다:
- `@JvmStatic`: companion object의 메서드를 자바 정적 메서드로 노출
- `@JvmField`: 프로퍼티를 자바 필드로 노출 (getter/setter 없이)
- `@JvmOverloads`: 기본 파라미터가 있는 함수에 대해 여러 오버로드 메서드 생성
- `@Throws`: 코틀린 함수가 발생시키는 예외를 자바의 체크드 예외로 선언
- `@JvmName`: 코틀린 함수의 JVM 시그니처 이름 변경
- `@JvmSynthetic`: 자바 코드에서 요소를 숨김

### b) 플랫폼별 구현(expect/actual)으로 멀티플랫폼 코드 작성하기

**Kotlin (공통 코드):**
```kotlin
// commonMain/kotlin/com/example/DateUtils.kt
expect class DateFormatter() {
    fun format(timestamp: Long): String
}

expect fun getPlatformName(): String

expect object Logger {
    fun log(message: String)
}

// 플랫폼 독립적 코드
class User(val id: Long, val name: String, val createdAt: Long) {
    fun getFormattedCreationDate(): String {
        return DateFormatter().format(createdAt)
    }
    
    fun logUserInfo() {
        Logger.log("User $name created on ${getFormattedCreationDate()} on ${getPlatformName()}")
    }
}
```

**Kotlin (JVM 구현):**
```kotlin
// jvmMain/kotlin/com/example/DateUtils.kt
import java.text.SimpleDateFormat
import java.util.Date

actual class DateFormatter {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    
    actual fun format(timestamp: Long): String {
        return formatter.format(Date(timestamp))
    }
}

actual fun getPlatformName(): String = "JVM"

actual object Logger {
    actual fun log(message: String) {
        println("[JVM] $message")
    }
}
```

**Kotlin (JS 구현):**
```kotlin
// jsMain/kotlin/com/example/DateUtils.kt
actual class DateFormatter {
    actual fun format(timestamp: Long): String {
        // JavaScript Date API 사용
        val date = js("new Date(timestamp)")
        return js("date.toISOString()")
    }
}

actual fun getPlatformName(): String = "JavaScript"

actual object Logger {
    actual fun log(message: String) {
        js("console.log('[JS] ' + message)")
    }
}
```

**설명:** 코틀린 멀티플랫폼 프로젝트(KMP)에서 `expect`/`actual` 키워드를 사용하면 공통 코드에서는 인터페이스를 정의하고(`expect`), 각 플랫폼별 모듈에서 실제 구현을 제공(`actual`)할 수 있습니다. 이를 통해 비즈니스 로직은 공유하면서 플랫폼별 API 차이는 추상화할 수 있습니다.
