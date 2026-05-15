# Expression Evaluator 실습

산술식은 단순해 보이지만 실제로는 사람이 여러 판단과 처리를 거쳐 계산을 수행한다. 연산자 우선순위를 판단하고 중간 결과를 기억하며 계산 순서를 조정하는 일련의 과정은 대부분 무의식적으로 이루어진다.

이 실습에서는 문자열로 주어진 산술식을 분석하여 정수 결과를 계산하는 계산기 `Expression Evaluator`를 구현한다.

실제 산술식 계산은 훨씬 복잡한 규칙과 다양한 경우를 포함하지만 이 실습에서는 `+`, `-`, `*`, `/`만을 사용하는 단순한 산술식으로 범위를 한정한다. 이를 통해 사람이 무의식적으로 수행하는 계산 과정을 하나하나 절차적인 단계로 분해하여 구현하는 데 집중한다.  
문자열로 주어진 산술식을 단계적으로 해석하고 연산자 우선순위를 고려하여 정확한 결과를 계산하며 잘못된 입력을 안정적으로 처리하는 로직을 작성한다.

이 실습의 목적은 복잡한 사고 과정을 작은 판단과 처리 단위로 나누어 절차적으로 구현하는 데 있다.

## 전반적인 규칙

- 숫자는 10진수 정수 형태로만 표현되며 선행하는 0을 포함할 수 없다.

- 지원하는 연산자는 다음과 같다:
    - 이항 연산자: `+`, `-`, `*`, `/`
    - 단항 부호: 음수를 나타내는 부호(`-`)

- 연산자 우선순위는 일반적인 산술 규칙을 따른다.

- 실습 명세에서 제공된 메서드 시그니처는 수정할 수 없으나 필요에 따라 추가적인 `private` 도움 메서드를 작성하는 것은 허용된다.

- 실습 명세에 명시되지 않은 동작은 제공된 검증용 `Main` 코드를 기준으로 추론하여 구현한다.
    - 검증용 `Main` 코드로도 추론이 어려운 경우 명세의 규칙을 위반하지 않는 범위 내에서 합리적으로 판단하여 구현한다.

## 1. 프로젝트를 준비한다

1. IntelliJ에서 `java-labs` 프로젝트를 연다.
2. `02-expression-evaluator` 디렉터리로 이동한다.
3. `src/main/java` 디렉터리를 생성한다.
4. `src/main/java` 아래에 `com.example.expressionevaluator` 패키지를 생성한다.
5. 제공된 검증용 [`Main.java`](./src/main/java/com/example/expressionevaluator/Main.java)와 같은 내용의 파일을 본인의 `java-labs` 프로젝트에 아래 경로로 생성한다.

```text
java-labs/
 └─ 02-expression-evaluator/
    └─ src/main/java/com/example/expressionevaluator/Main.java
```

6. 검증용 `Main` 클래스는 수정하지 않는다.
7. 같은 패키지에 `ExpressionEvaluator` 클래스를 정의한다.

## 2. `ExpressionEvaluator` 클래스를 구현한다

### 2.1. `evaluateOrNull()` 정적 메서드를 구현한다

- 이 메서드는 문자열로 주어진 산술식을 입력으로 받아 계산 결과를 반환한다.

- 이 메서드는 유일한 매개 변수로 `String expressionOrNull`을 받는다.
    - `expressionOrNull`에는 공백이 포함될 수 있으며 공백 문자의 종류와 개수는 일정하지 않을 수 있다.
    - 공백은 피연산자와 이항 연산자 사이에서만 무시하며, 숫자를 구성하는 각 자리 사이의 공백은 허용하지 않는다.

- 산술식은 이 실습에서 정의한 규칙에 부합하며 수학적으로 올바른 형태만을 올바른 산술식으로 간주한다.

- 이 실습에서 `-` 기호는 다음과 같이 해석한다.
    - 이항 연산자 `-`
    - 음수를 나타내는 부호(`-`)
        - 음수를 나타내는 부호(`-`)는 숫자 바로 앞에 붙어 있어야 한다.

- `/` 연산은 Java의 `int` 정수 나눗셈 규칙을 따른다. (소수점 이하 버림)

- 산술식에 포함되는 모든 피연산자는 `int` 범위 내에 있다고 간주한다.

- 산술식을 연산하는 과정에서 수행되는 모든 이항 연산의 결과는 `int` 범위 내에 있다고 간주한다.

- 반환 규칙은 다음과 같다:
    - 산술식이 올바르지 않은 경우 `null`을 반환한다.
    - 올바른 산술식인 경우 계산 결과를 공백이 없는 10진수 정수 문자열로 반환한다.
        - Java의 표준 변환을 이용해 `int` 값을 문자열로 변환해 반환한다.

```java
ExpressionEvaluator.evaluateOrNull("");                   // null
ExpressionEvaluator.evaluateOrNull("   ");                // null

ExpressionEvaluator.evaluateOrNull("7");                  // "7"
ExpressionEvaluator.evaluateOrNull("-7");                 // "-7"
ExpressionEvaluator.evaluateOrNull("- 7");                // null

ExpressionEvaluator.evaluateOrNull("1 + 2");              // "3"
ExpressionEvaluator.evaluateOrNull("  10  +   20 ");      // "30"

ExpressionEvaluator.evaluateOrNull("2 + 3 * 4");          // "14"
ExpressionEvaluator.evaluateOrNull("20 / 3");             // "6"

ExpressionEvaluator.evaluateOrNull("1 +");                // null
ExpressionEvaluator.evaluateOrNull("1 2 + 3");            // null
ExpressionEvaluator.evaluateOrNull("2 * * 3");            // null
```
