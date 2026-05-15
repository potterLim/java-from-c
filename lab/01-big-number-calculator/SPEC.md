# Big Number Calculator 실습

`int`(32비트 정수형)와 `long`(64비트 정수형)은 표현할 수 있는 정수의 범위가 고정되어 있으므로 매우 큰 정수 값을 표현하고 연산하기에 적합하지 않다. 덧셈이나 뺄셈과 같은 기본 연산에서도 결과가 표현 가능한 최댓값을 넘거나 최솟값보다 작아지면 오버플로 또는 언더플로가 발생한다.  
`double`과 같은 부동소수점 자료형을 사용할 수도 있으나 이는 근본적으로 오차를 포함할 수 있으므로 정확한 정수 연산이 요구되는 상황에는 적합하지 않다.

이 실습에서는 이러한 한계를 극복하기 위해 매우 큰 정수를 문자열 기반으로 표현하고 처리하는 계산기 `Big Number Calculator`를 구현한다.  
이 실습의 목적은 언어에서 제공하는 내장 큰 정수 자료형에 의존하지 않고 문자열 기반 표현을 통해 매우 큰 정수의 덧셈과 뺄셈을 정확하게 수행할 수 있는 연산 로직을 직접 구현하는 데 있다.

## 전반적인 규칙

- `java.math.BigInteger`를 포함한 모든 Big Integer 관련 라이브러리는 허용되지 않는다.

- `double`, `float`, `BigDecimal` 등 부동소수점 및 고정소수점 자료형은 허용되지 않는다.

- 정수 연산을 위해 입력 전체를 `int` 또는 `long`으로 변환하는 방식은 허용되지 않는다.

- 입력되는 정수 문자열은 다음과 같은 형태를 가질 수 있다.
    - 선행 0을 포함할 수 있다. (예: `"000123"`)
    - 음수를 나타내기 위해 `-` 기호를 포함할 수 있다. (예: `"-00000000007"`)

- 모든 연산의 최종 결과는 정규화된 10진수 정수 문자열로 반환해야 한다.
    - 선행 0이 없어야 한다.
    - `-0`은 허용되지 않으며, 이 경우 반드시 `"0"`으로 반환해야 한다.
    - 결과는 수학적으로 정확한 값이어야 한다.

- 실습 명세에서 제공된 메서드 시그니처는 수정할 수 없으나 필요에 따라 추가적인 `private` 도움 메서드를 작성하는 것은 허용된다.

- 필수 요구 사항은 아니지만 `String`을 이용한 반복적인 수정 작업은 불필요한 연산 비용을 유발할 수 있으므로 상황에 따라 보다 효율적인 구현 방법을 고려하는 것이 바람직하다.

- 실습 명세에 명시되지 않은 동작은 제공된 검증용 `Main` 코드를 기준으로 추론하여 구현한다.
    - 검증용 `Main` 코드로도 추론이 어려운 경우 명세의 규칙을 위반하지 않는 범위 내에서 합리적으로 판단하여 구현한다.

## 1. 프로젝트를 준비한다

1. IntelliJ에서 `java-labs` 프로젝트를 연다.
2. `01-big-number-calculator` 디렉터리로 이동한다.
3. `src/main/java` 디렉터리를 생성한다.
4. `src/main/java` 아래에 `com.example.bignumber` 패키지를 생성한다.
5. 제공된 검증용 [`Main.java`](./src/main/java/com/example/bignumber/Main.java)와 같은 내용의 파일을 본인의 `java-labs` 프로젝트에 아래 경로로 생성한다.

```text
java-labs/
 └─ 01-big-number-calculator/
    └─ src/main/java/com/example/bignumber/Main.java
```

6. 검증용 `Main` 클래스는 수정하지 않는다.
7. 같은 패키지에 `BigNumberCalculator` 클래스를 정의한다.

```java
package com.example.bignumber;

public final class BigNumberCalculator {
    private BigNumberCalculator() {
    }

    public static String addOrNull(String leftNumberOrNull, String rightNumberOrNull) {
        return null;
    }

    public static String subtractOrNull(String leftNumberOrNull, String rightNumberOrNull) {
        return null;
    }
}
```

## 2. `BigNumberCalculator` 클래스를 구현한다

### 2.1. `addOrNull()` 정적 메서드를 구현한다

- 이 메서드는 두 개의 10진수 정수 문자열을 입력으로 받아 덧셈 결과를 10진수 정수 문자열로 반환한다.

- 이 메서드는 다음의 매개 변수를 받는다:
    - `String leftNumberOrNull`: 첫 번째 10진수 정수 문자열
    - `String rightNumberOrNull`: 두 번째 10진수 정수 문자열

- 반환 규칙은 다음과 같다:
    - `leftNumberOrNull` 또는 `rightNumberOrNull`이 올바른 10진수 정수 문자열이 아니면 `null`을 반환한다.
    - 두 입력이 올바르다면 덧셈 결과를 정규화된 10진수 정수 문자열로 반환한다.

```java
BigNumberCalculator.addOrNull("123", "456");            // "579"
BigNumberCalculator.addOrNull("-123", "456");           // "333"
BigNumberCalculator.addOrNull("123", "-456");           // "-333"
BigNumberCalculator.addOrNull("-123", "-456");          // "-579"

BigNumberCalculator.addOrNull("0000123", "000");        // "123"
BigNumberCalculator.addOrNull("-0000", "0");            // "0"
BigNumberCalculator.addOrNull("-0007", "0007");         // "0"

BigNumberCalculator.addOrNull(null, "1");               // null
BigNumberCalculator.addOrNull("", "1");                 // null
BigNumberCalculator.addOrNull("-", "1");                // null
BigNumberCalculator.addOrNull("12.3", "1");             // null
BigNumberCalculator.addOrNull("0xFF", "1");             // null
```

### 2.2. `subtractOrNull()` 정적 메서드를 구현한다

- 이 메서드는 두 개의 10진수 정수 문자열을 입력으로 받아 뺄셈 결과를 10진수 정수 문자열로 반환한다.

- 이 메서드는 다음의 매개 변수를 받는다:
    - `String leftNumberOrNull`: 첫 번째 10진수 정수 문자열
    - `String rightNumberOrNull`: 두 번째 10진수 정수 문자열

- 반환 규칙은 다음과 같다:
    - `leftNumberOrNull` 또는 `rightNumberOrNull`이 올바른 10진수 정수 문자열이 아니면 `null`을 반환한다.
    - 두 입력이 올바르다면 뺄셈 결과를 정규화된 10진수 정수 문자열로 반환한다.

```java
BigNumberCalculator.subtractOrNull("456", "123");       // "333"
BigNumberCalculator.subtractOrNull("123", "456");       // "-333"
BigNumberCalculator.subtractOrNull("-456", "-123");     // "-333"
BigNumberCalculator.subtractOrNull("-123", "-456");     // "333"

BigNumberCalculator.subtractOrNull("0000123", "000");   // "123"
BigNumberCalculator.subtractOrNull("0", "-0");          // "0"
BigNumberCalculator.subtractOrNull("-0007", "-0007");   // "0"

BigNumberCalculator.subtractOrNull(null, "1");          // null
BigNumberCalculator.subtractOrNull("", "1");            // null
BigNumberCalculator.subtractOrNull("-", "1");           // null
BigNumberCalculator.subtractOrNull("12.3", "1");        // null
BigNumberCalculator.subtractOrNull("0b1010", "1");      // null
```

## 3. 구현 힌트

### 3.1. 입력 검증을 분리할 수 있다

- 큰 정수 연산을 구현하기 전에 입력 문자열이 올바른 10진수 정수 문자열인지 확인하는 로직을 별도 도움 메서드로 분리할 수 있다.

- 예를 들어 아래와 같은 도움 메서드를 정의할 수 있다.

```java
private static boolean isValidSignedInteger(String numberOrNull) {
    return false;
}
```

- 이 도움 메서드는 필수 구현 메서드가 아니며, 같은 역할을 다른 이름이나 다른 구조로 구현해도 된다.

- 이 도움 메서드를 작성한 경우 아래와 같은 기대 결과를 확인할 수 있다.

```java
isValidSignedInteger(null);            // false
isValidSignedInteger("");              // false
isValidSignedInteger("-");             // false
isValidSignedInteger("   ");           // false

isValidSignedInteger("159");           // true
isValidSignedInteger("-00000000007");  // true
isValidSignedInteger("000123");        // true
isValidSignedInteger("0");             // true
isValidSignedInteger("-0");            // true
isValidSignedInteger("00000");         // true

isValidSignedInteger("+12");           // false
isValidSignedInteger("12.3");          // false
isValidSignedInteger("1e5");           // false
isValidSignedInteger("12a3");          // false
isValidSignedInteger("0xFF");          // false
isValidSignedInteger("0b1010");        // false
```

### 3.2. 정규화를 분리할 수 있다

- 큰 정수 연산을 단순하게 만들기 위해 입력 문자열에서 선행 0을 제거하고 `-0`을 `"0"`으로 바꾸는 정규화 로직을 별도 도움 메서드로 분리할 수 있다.

- 예를 들어 아래와 같은 도움 메서드를 정의할 수 있다.

```java
private static String normalizeSignedIntegerOrNull(String numberOrNull) {
    return null;
}
```

- 이 도움 메서드는 필수 구현 메서드가 아니며, 같은 역할을 다른 이름이나 다른 구조로 구현해도 된다.

- 정규화는 다음 규칙을 따르도록 구현할 수 있다:
    - 선행 0을 모두 제거한다.  
      _예: `"000123" → "123"`, `"-0000007" → "-7"`_

    - 값이 0이 아닌 경우 음수 부호 `-`는 유지한다.  
      _예: `"-00120" → "-120"`_

    - 결과가 0인 경우(모든 숫자가 0인 경우)에는 반드시 `"0"`을 반환한다.  
      _예: `"0" → "0"`, `"0000" → "0"`, `"-0" → "0"`, `"-0000" → "0"`_

- 이 도움 메서드를 작성한 경우 아래와 같은 기대 결과를 확인할 수 있다.

```java
normalizeSignedIntegerOrNull(null);            // null
normalizeSignedIntegerOrNull("");              // null
normalizeSignedIntegerOrNull("-");             // null
normalizeSignedIntegerOrNull("   ");           // null

normalizeSignedIntegerOrNull("0");             // "0"
normalizeSignedIntegerOrNull("0000");          // "0"
normalizeSignedIntegerOrNull("-0");            // "0"
normalizeSignedIntegerOrNull("-0000");         // "0"

normalizeSignedIntegerOrNull("000123");        // "123"
normalizeSignedIntegerOrNull("-00000000007");  // "-7"
normalizeSignedIntegerOrNull("159");           // "159"
normalizeSignedIntegerOrNull("-00120");        // "-120"

normalizeSignedIntegerOrNull("+12");           // null
normalizeSignedIntegerOrNull("12.3");          // null
normalizeSignedIntegerOrNull("1e5");           // null
normalizeSignedIntegerOrNull("12a3");          // null
normalizeSignedIntegerOrNull("0xFF");          // null
normalizeSignedIntegerOrNull("0b1010");        // null
```
