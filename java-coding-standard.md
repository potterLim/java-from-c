# Java 코딩 표준

## 1. 기본 원칙

이 코딩 표준은 **가독성과 일관성을 최우선 가치**로 삼는다.  
코드는 단순히 동작하는 것을 넘어 읽는 사람에게 명확하게 의도를 전달해야 한다.

- **가독성을 최우선으로 한다.**  
   코드는 주석 없이도 의도를 이해할 수 있어야 하며 읽는 사람이 자연스럽게 흐름을 따라갈 수 있도록 작성한다.

- **특별한 이유가 없는 한 IDE의 자동 서식 규칙을 따른다.**  
   이 문서는 IntelliJ IDEA의 기본 코드 포맷(Windows 기준 `Ctrl + Alt + L`)을 기준으로 작성되었다.  
   개인 취향보다 일관성과 유지보수성을 우선한다.

- **변수명과 함수명은 의도와 역할이 명확히 드러나도록 작성한다.**  
   축약어나 임의의 약어보다는 코드의 의도를 바로 이해할 수 있는 이름을 사용한다.

이 코딩 표준은 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 기반으로 하되 실제 개발 환경에서의 가독성과 유지보수성을 고려하여 일부 항목을 현실적으로 조정하였다.

## 2. 파일, 패키지, 클래스 구조

### 2.1. 패키지 규칙
- 패키지명은 모두 소문자로 작성한다.

```java
package com.awesome.shop.order;
```

### 2.2. import 규칙

- 와일드카드(`*`) 사용은 금지한다.
- 필요한 클래스만 명시적으로 import 한다.

```java
// 잘못된 예
import com.awesome.shop.order.*;

// 올바른 예
import com.awesome.shop.order.OrderService;
import com.awesome.shop.order.OrderRepository;
```

### 2.3. 클래스와 파일 구조

- 하나의 `.java` 파일에는 하나의 최상위 클래스만 존재해야 한다.
- 파일명은 클래스명과 정확히 일치해야 하며 대소문자도 구분한다.

```java
// OrderProcessor.java
public class OrderProcessor {
}
```

## 3. 네이밍 규칙

### 3.1. 클래스 / 인터페이스 / Enum

- 클래스와 Enum은 PascalCase를 사용한다.
- 인터페이스는 이름 앞에 `I` 접두어를 사용한다.
- 비트 플래그 용도로 사용하는 Enum에는 `Flags` 접미사를 붙인다.
- Enum 멤버 이름은 모두 대문자로 작성하며 단어 사이는 언더스코어(`_`)로 구분한다.

```java
public class UserRegistrationService { }
public interface IPaymentGateway { }
public enum OrderStatus {
    PENDING,
    IN_PROGRESS,
    SHIPPED,
    COMPLETED
}
public enum PermissionFlags {
    READ,
    WRITE,
    EXECUTE
}
```

### 3.2. 메서드 이름 규칙

- 메서드 이름은 camelCase를 사용하며 반드시 동사로 시작한다.
- 반환값이 있는 메서드는 반환되는 값의 의미가 이름에 드러나야 한다.
- `Boolean` 반환 메서드는 `is`, `has`, `can`, `should`를 접두어로 사용한다. 
    - 위 표현이 부자연스러운 경우에는 상태를 나타내는 동사를 사용한다.
- 재귀 호출을 사용하는 메서드는 이름 끝에 `Recursive`를 붙인다.

```java
public User findUserById(Long id);
public void processPayment();
public boolean isAuthenticated();
public boolean hasPermission();
public boolean canAccess();
public boolean shouldRetry();
public int calculateSumRecursive(int value);
```

### 3.3. 변수 이름 규칙

- 변수명은 camelCase를 사용한다.
- 의미 없는 축약어나 단일 문자 이름은 사용하지 않는다.

```java
// 잘못된 예
int c;
int i;
User u;

// 올바른 예
int retryCount;
int currentIndex;
User targetUser;
```

### 3.4. 상수 이름 규칙

- 상수는 모두 대문자로 작성하며 단어 사이는 언더스코어(`_`)로 구분한다.

```java
public static final int MAX_RETRY_ATTEMPT = 5;
public static final String DEFAULT_TIME_ZONE = "Asia/Seoul";
```

## 4. 멤버 구성 및 순서

클래스 내부 구성은 다음 순서를 따른다.
1. `public` 필드
2. `default` 필드
3. `protected` 필드
4. `private` 필드
5. 생성자
6. `public` 메서드
7. `default` 메서드
8. `protected` 메서드
9. `private` 메서드

## 5. 접근 제어 및 캡슐화

- 원칙적으로 `public` 필드 사용은 금지한다.
- 모든 필드는 `private`으로 선언한다.
- 외부 접근이 필요한 경우 Getter / Setter를 통해 제공한다.

```java
private String userName;

public void setUserName(String userName) {
    this.userName = userName;
}
public String getUserName() {
    return userName;
}
```

## 6. 변수 및 메서드 설계 규칙

### 6.1. 지역 변수 선언 규칙
- 지역 변수는 사용하기 직전에 선언하여 변수의 생존 범위(Scope)를 최소화한다.

```java
// 잘못된 예
int result;
/*
    수백 줄의 코드
*/
if (condition) {
    result = calculate();
    process(result);
}

// 올바른 예
if (condition) {
    int result = calculate();
    process(result);
}
```

- 지역 변수 이름이 클래스의 필드 이름과 겹치는 것은 허용하지 않는다.
    - 단, 생성자 또는 setter 메서드의 매개변수는 예외로 한다.

```java
// 잘못된 예
public class User {
    private String name;

    public void printGreeting() {
        String name = "Guest";
        System.out.println("Hello, " + name);
    }
}

// 허용되는 예
public class User {
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
```

### 6.2. 메서드 오버로딩 규칙
- 타입만 다른 모호한 오버로딩은 허용하지 않는다.
- 메서드 이름만으로 동작의 차이가 명확히 드러나야 한다.

```java
// 잘못된 예
public void search(int id);
public void search(String keyword);

// 올바른 예
public void searchById(int id);
public void searchByKeyword(String keyword);
```

### 6.3. 입력 검증(Validation) 규칙
- 외부로부터 들어오는 데이터는 외부/내부 경계가 바뀌는 지점에서 검증(validate)한다.
- 검증에 실패하면 내부 메서드로 전달하기 전에 즉시 반환한다.
- 내부로 전달된 데이터는 유효하다고 가정한다.

```java
public User findUserByEmail(String email) {
    if (email == null || email.isBlank()) {
        return null;
    }

    return userRepository.findByEmail(email);
}

private User findByEmailInternal(String email) {
    // email은 유효하다고 가정한다.
    return userRepository.findByEmail(email);
}
```

### 6.4. @Override 사용 규칙

- 메서드를 오버라이딩할 때는 항상 `@Override` 어노테이션을 명시한다.

```java
public class CachedRepository extends Repository {

    @Override
    public User findUserById(long id) {
        return super.findUserById(id);
    }
}
```


## 7. 제어문 규칙

### 7.1. 중괄호 사용 규칙

- 모든 제어문(`if`, `else`, `for`, `while`, `switch`)에는 중괄호를 반드시 사용한다.
- 단일 실행문이라도 중괄호를 생략하지 않는다.

```java
if (isValid) {
    proceed();
}
```

### 7.2. switch 문 규칙

- `default`가 논리적으로 발생할 수 없는 상황이라도 `default`는 항상 포함한다.
    - 이 경우 `default`는 실행될 일 없는 방어용 코드로 작성하여 의도하지 않은 상태를 통제한다.
    - `enum`을 `switch`문에서 사용하는 경우 `default`는 실행될 일 없는 방어 코드로만 사용한다.
- 의도적인 fall-through가 필요한 경우에는 반드시 주석으로 명시한다.

```java
switch (orderStatus) {
    case PENDING:
        prepareShipping();
        // intentional fall-through
    case SHIPPED:
        notifyUser();
        break;
    default:
        assert false : "Unexpected status: " + orderStatus;
}
```

## 8. assert 사용 규칙

- assert는 논리적으로 절대 발생해서는 안 되는 상황을 검증할 때 사용한다.
- 반드시 괄호로 감싸서 작성한다.

```java
assert (denominator != 0) : "denominator must not be zero";
```

## 9. null 처리 규칙

- 메서드의 매개변수로 `null`을 전달하는 것은 지양한다.
- `null`을 허용해야 하는 경우 매개변수 이름 뒤에 `OrNull`을 붙인다.

```java
public User findUserByEmail(String emailOrNull);
```

- 메서드가 `null`을 반환하는 것은 지양한다.
- null을 반환해야 하는 경우 메서드 이름 끝에 OrNull을 붙인다.

```java
public String getDisplayNameOrNull();
```

## 10. var 사용 규칙

- var는 타입이 명확히 드러나는 경우에만 제한적으로 사용한다.

```java
// 잘못된 예
var result = service.execute();

// 올바른 예
var member = new Member();
var message = "Hello, World";
```

## 11. 포맷팅 규칙

### 11.1. 들여쓰기 및 중괄호 규칙

- 탭(tab)은 IntelliJ의 기본 설정을 따른다. 다른 편집기를 사용할 경우에는 탭 문자 대신 공백 4칸을 사용한다.

- 중괄호(`{}`)는 새로운 줄에서 열지 않으며 닫는 중괄호(`}`)는 새로운 줄에 위치시킨다.
```java
public void processData() {
    initialize();
    execute();
}
```

- `if`, `else if`, `else` 문은 예외적으로 닫는 중괄호 뒤에 `else`를 같은 줄에 작성한다.

```java 
if (condition) {
    processRequest();
} else if (otherCondition) {
    handleAlternativeCase();
} else {
    handleDefaultCase();
}
```

### 11.2. 제어문 작성 규칙

- 접근제어자(`public`, `protected`, `private`)는 다른 수정자(`static`, `final`, `abstract` 등)보다 앞에 작성한다.

```java
// 잘못된 예
static public void doSomething() {
}

static private final int RETRY_COUNT = 3;

// 올바른 예
public static void doSomething() {
}

private static final int RETRY_COUNT = 3;
```

### 11.3. 선언 및 배치 규칙

- 한 줄에 변수 하나만 선언한다.

```java
// 잘못된 예
int width = 0, height = 0, depth = 0;

// 올바른 예
int width = 0;
int height = 0;
int depth = 0;
```

### 11.4. 리터럴 표기 규칙

- `double` 타입이 반드시 필요한 경우가 아니라면 `float` 타입을 사용하고 리터럴에 반드시 `f`를 붙인다.

```java
// 잘못된 예
float ratio = 0.25;

// 올바른 예
float ratio = 0.25f;
```
- `long` 타입 리터럴에는 반드시 대문자 `L`을 사용한다.

```java
// 잘못된 예
long timeout = 1000l;

// 올바른 예
long timeout = 1000L;
```