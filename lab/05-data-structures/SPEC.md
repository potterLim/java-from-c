# Data Structures 실습

표준 라이브러리에서 제공하는 컬렉션을 사용하면 자료를 저장하고 관리하는 일은 비교적 쉽고 편리하다.  
그러나 내부에서 어떤 규칙으로 동작하는지 이해하지 않은 채 사용하면 예상과 다른 동작이나 성능 문제를 마주했을 때 원인을 파악하기가 쉽지 않아 왜 이런 결과가 나오는지 이해하지 못하는 상황도 자주 발생한다.

자료구조를 직접 구현해보는 훈련은 이러한 한계를 보완하기 위한 가장 확실한 방법 중 하나다.  
데이터를 직접 다루고 반복문과 조건문을 사용해 경계 상황을 하나하나 점검하는 과정은 데이터가 실제로 어떻게 저장되고 이동하는지를 분명하게 드러낸다.

이 실습에서는 배열 기반의 `List`를 시작으로 이를 응용한 `Stack`과 `Queue` 그리고 배열 방식의 한계를 보완하는 `Linked List`를 구현한다.  
모든 구현은 표준 라이브러리의 컬렉션에 의존하지 않고 정수(`int`) 자료형을 기준으로 각 자료구조의 핵심 동작과 상태 관리를 직접 작성하는 방식으로 진행된다.

이를 통해 각 자료구조가 내부에서 가질 수 있는 상태와 그 상태가 어떤 규칙에 따라 동작하는지를 분명히 이해하는 것을 목표로 한다.

## 전반적인 규칙

- 표준 라이브러리에서 제공하는 컬렉션(`List`, `Map`, `Set` 등)은 사용할 수 없다.

- 모든 자료구조는 정수(`int`) 자료형만을 대상으로 구현한다.

- 예외를 발생시키는 방식의 오류 처리는 허용하지 않는다.

- 값의 반환이 필요한 경우에는 out 파라미터를 사용한다.
    - 이를 위해 제공된 `IntValue` 타입을 사용한다.
    - 전달되는 out 파라미터는 `null`이 아니라고 가정한다.

- 잘못된 입력이 주어지거나 수행할 수 없는 연산이 요청된 경우 해당 연산은 실패해야 하며 내부 상태는 변경되지 않아야 한다.

- 실습 명세에서 제공된 메서드 시그니처와 멤버 변수는 수정할 수 없다.
    - 구현에 필요하다면 추가적인 생성자를 정의하는 것은 허용된다.
    - 구현에 필요하다면 추가적인 `private` 도움 메서드를 정의하는 것은 허용된다.
    - 구현에 필요하다면 추가적인 `private` 멤버 변수를 정의하는 것은 허용된다.

- 실습 명세에 명시되지 않은 동작은 제공된 예시 코드를 기준으로 추론하여 구현한다.
    - 예시 코드로도 추론이 어려운 경우 명세의 규칙을 위반하지 않는 범위 내에서 합리적으로 판단하여 구현한다.

## 1. 프로젝트를 준비한다

1. IntelliJ에서 `java-labs` 프로젝트를 연다.
2. `05-data-structures` 디렉터리로 이동한다.
3. `05-data-structures` 디렉터리 아래에 `src/main/java` 디렉터리를 생성한다.
4. `src/main/java` 디렉터리 아래에 `com.example.datastructures` 패키지를 생성한다.
5. `com.example.datastructures` 패키지에 `IntValue` 클래스를 정의한다.
6. `com.example.datastructures` 패키지에 `List` 클래스를 정의한다.
7. `com.example.datastructures` 패키지에 `Stack` 클래스를 정의한다.
8. `com.example.datastructures` 패키지에 `Queue` 클래스를 정의한다.
9. `com.example.datastructures` 패키지에 `LinkedList` 클래스를 정의한다.
10. `com.example.datastructures` 패키지에 `Main` 클래스를 정의한다.

## 2. 자료구조를 구현한다

### 2.1. `IntValue` 클래스를 수정한다

- `IntValue` 클래스를 아래와 같이 정의한다.

```java
package com.example.datastructures;

public final class IntValue {
    public int value;
}
```

- `IntValue`는 메서드 호출 결과로 값을 전달하기 위한 out 파라미터 용도로 사용된다.

### 2.2. `List` 클래스를 구현한다

- `List` 클래스는 정수(`int`) 값을 순서대로 저장하는 선형 자료구조이다.

- `List` 클래스는 내부적으로 저장된 원소들의 순서를 보존해야 하며 인덱스는 `0`부터 시작한다.

- 기본 생성자로 생성된 `List`는 초기에는 어떠한 원소도 저장하지 않으며 내부 저장 공간의 크기가 `0`인 상태로 생성된다.
    - 첫 번째 원소를 추가하려는 경우 내부 저장 공간의 크기는 `4`로 확보되어야 한다.

- 생성자의 매개변수로 초기 크기를 지정한 경우 해당 개수의 원소를 저장할 수 있는 내부 저장 공간을 확보한 상태로 생성된다.
    - 초기 크기는 음수로 주어지지 않는다고 가정한다.

- 내부 저장 공간이 가득 찬 상태에서 원소를 추가하려는 경우 기존 내부 저장 공간 크기의 2배에 해당하는 새로운 내부 저장 공간을 확보해야 한다.

- 확보된 내부 저장 공간은 어떠한 경우에도 축소되지 않는다.

- `List` 클래스는 아래 기능을 제공해야 한다.
    - 원소 추가, 삽입 및 제거
    - 특정 인덱스의 값 조회 및 변경
    - 현재 저장된 원소의 개수 조회
    - 현재 내부 저장 공간의 크기 조회
    - 현재 저장된 모든 원소 제거
    - 리스트가 비어 있는지 여부 확인

#### 2.2.1. `add` 메서드를 구현한다

- `add` 메서드는 리스트의 마지막 위치에 원소를 추가할 때 사용한다.

- 이 메서드는 유일한 인자로 `int value`를 받으며 이는 리스트에 추가할 정수 값을 의미한다.

- 원소 추가에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
List list = new List(3); // 내부 저장 공간 크기: 3,  상태: []

boolean result;
result = list.add(5);    // true,  상태: [5]
result = list.add(7);    // true,  상태: [5, 7]
result = list.add(9);    // true,  상태: [5, 7, 9]
result = list.add(11);   // true,  내부 저장 공간 크기: 6,  상태: [5, 7, 9, 11]
```

#### 2.2.2. `insert` 메서드를 구현한다

- `insert` 메서드는 리스트의 특정 위치에 원소를 삽입할 때 사용한다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `int value`

- 유효하지 않은 인덱스가 주어진 경우 삽입은 실패한다.

- 원소 삽입에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
List list = new List(3);    // 내부 저장 공간 크기: 3, 상태: []

boolean result;
list.add(5);                // 상태: [5]
list.add(7);                // 상태: [5, 7]

result = list.insert(2, 8); // true,   상태: [5, 7, 8]
result = list.insert(4, 9); // false,  상태: [5, 7, 8]
```

#### 2.2.3. `removeAt` 메서드를 구현한다

- `removeAt` 메서드는 리스트의 특정 위치에 저장된 원소를 제거할 때 사용한다.

- 이 메서드는 유일한 인자로 `int index`를 받으며 이는 제거할 원소의 인덱스를 의미한다.

- 유효하지 않은 인덱스가 주어진 경우 제거는 실패한다.

- 원소 제거에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
List list = new List(5);    // 내부 저장 공간 크기: 5,  상태: []

boolean result;
list.add(5);                // 상태: [5]
list.add(6);                // 상태: [5, 6]
list.add(7);                // 상태: [5, 6, 7]

result = list.removeAt(1);  // true,   상태: [5, 7]
result = list.removeAt(-1); // false,  상태: [5, 7]
```

#### 2.2.4. `get` 메서드를 구현한다

- `get` 메서드는 리스트의 특정 위치에 저장된 원소의 값을 조회할 때 사용한다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `IntValue outValue`

- 유효하지 않은 인덱스가 주어진 경우 조회는 실패한다.

- 조회에 성공한 경우 `true`를 반환하며 조회된 값은 `outValue.value`에 저장된다.

- 조회에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
List list = new List(3);         // 내부 저장 공간 크기: 3,  상태: []

boolean result;
IntValue outValue = new IntValue();

list.add(5);                     // 상태: [5]
list.add(7);                     // 상태: [5, 7]

result = list.get(1, outValue);  // true,  outValue.value == 7
result = list.get(2, outValue);  // false
```

#### 2.2.5. `set` 메서드를 구현한다

- `set` 메서드는 리스트의 특정 위치에 저장된 원소의 값을 변경할 때 사용한다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `int value`

- 유효하지 않은 인덱스가 주어진 경우 값 변경은 실패한다.

- 값 변경에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
List list = new List(3); // 내부 저장 공간 크기: 3,  상태: []

boolean result;
list.add(5);             // 상태: [5]
list.add(7);             // 상태: [5, 7]

result = list.set(1, 9); // true,  상태: [5, 9]
result = list.set(3, 4); // false, 상태: [5, 9]
```

#### 2.2.6. `getCount` 메서드를 구현한다

- `getCount` 메서드는 리스트에 현재 저장된 원소의 개수를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
List list = new List(3);     // 내부 저장 공간 크기: 3,  상태: []

list.add(5);                 // 상태: [5]
list.add(7);                 // 상태: [5, 7]

int count = list.getCount(); // 2
```

#### 2.2.7. `getCapacity` 메서드를 구현한다

- `getCapacity` 메서드는 리스트의 현재 내부 저장 공간의 크기를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
List list = new List(3);           // 내부 저장 공간 크기: 3,  상태: []

int capacity = list.getCapacity(); // 3

list.add(5);                       // 상태: [5]
list.add(7);                       // 상태: [5, 7]
list.add(9);                       // 상태: [5, 7, 9]
list.add(11);                      // 내부 저장 공간 크기: 6,  상태: [5, 7, 9, 11]

capacity = list.getCapacity();     // 6
```
#### 2.2.8.`clear` 메서드를 구현한다

- `clear` 메서드는 리스트에 현재 저장된 모든 원소를 제거할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 이 메서드는 반환값을 가지지 않는다.

```java
List list = new List(5);           // 내부 저장 공간 크기: 5,  상태: []

list.add(5);                       // 상태: [5]
list.add(7);                       // 상태: [5, 7]

list.clear();                      // 상태: []

int count = list.getCount();       // 0
int capacity = list.getCapacity(); // 5
```

#### 2.2.9. `isEmpty` 메서드를 구현한다

- `isEmpty` 메서드는 리스트가 비어 있는지 여부를 확인할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 리스트에 저장된 원소가 하나도 없는 경우 `true`를 반환하며 그렇지 않은 경우 `false`를 반환한다.

```java
List list = new List(); // 내부 저장 공간 크기: 0

boolean empty;
empty = list.isEmpty(); // true

list.add(5);            // 내부 저장 공간 크기: 4,  상태: [5]
empty = list.isEmpty(); // false

list.clear();           // 상태: []
empty = list.isEmpty(); // true
```

### 2.3. `Stack` 클래스를 구현한다

- `Stack` 클래스는 정수(`int`) 값을 LIFO(Last-In, First-Out) 규칙에 따라 저장하고 꺼내는 선형 자료구조이다.

- `Stack` 클래스에서 원소의 추가와 제거는 항상 한쪽 끝(Top)에서만 발생한다.

- 기본 생성자로 생성된 `Stack`은 초기에는 어떠한 원소도 저장하지 않으며 내부 저장 공간의 크기가 `0`인 상태로 생성된다.
    - 첫 번째 원소를 추가하려는 경우 내부 저장 공간의 크기는 `4`로 확보되어야 한다.

- 생성자의 매개변수로 초기 크기를 지정한 경우 해당 개수의 원소를 저장할 수 있는 내부 저장 공간을 확보한 상태로 생성된다.
    - 초기 크기는 음수로 주어지지 않는다고 가정한다.

- 내부 저장 공간이 가득 찬 상태에서 원소를 추가하려는 경우 기존 내부 저장 공간 크기의 2배에 해당하는 새로운 내부 저장 공간을 확보해야 한다.

- 확보된 내부 저장 공간은 어떠한 경우에도 축소되지 않는다.

- `Stack` 클래스는 아래 기능을 제공해야 한다.
    - 원소 추가(push) 및 제거(pop)
    - top 원소 조회(peek)
    - 현재 저장된 원소의 개수 조회
    - 현재 내부 저장 공간의 크기 조회
    - 현재 저장된 모든 원소 제거
    - 스택이 비어 있는지 여부 확인

#### 2.3.1. `push` 메서드를 구현한다

- `push` 메서드는 스택의 Top 위치에 원소를 추가할 때 사용한다.

- 이 메서드는 유일한 인자로 `int value`를 받으며 이는 스택에 추가할 정수 값을 의미한다.

- 원소 추가에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
Stack stack = new Stack(3); // 내부 저장 공간 크기: 3, 상태: []

boolean result;
result = stack.push(5);     // true,  상태: [5] <- top
result = stack.push(7);     // true,  상태: [5, 7] <- top
result = stack.push(9);     // true,  상태: [5, 7, 9] <- top
result = stack.push(11);    // true,  내부 저장 공간 크기: 6,  상태: [5, 7, 9, 11] <- top
```

#### 2.3.2. `pop` 메서드를 구현한다

- `pop` 메서드는 스택의 Top 위치에 저장된 원소를 제거하고 그 값을 반환할 때 사용한다.

- 이 메서드는 유일한 인자로 `IntValue outValue`를 받는다.

- 스택이 비어 있는 경우 제거는 실패한다.

- 제거에 성공한 경우 `true`를 반환하며 제거된 값은 `outValue.value`에 저장된다.

- 제거에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
Stack stack = new Stack(3);         // 내부 저장 공간 크기: 3,  상태: []

boolean result;
IntValue outValue = new IntValue();

stack.push(5);                      // 상태: [5] <- top
stack.push(7);                      // 상태: [5, 7] <- top

result = stack.pop(outValue);       // true,  outValue.value == 7,  상태: [5] <- top
result = stack.pop(outValue);       // true,  outValue.value == 5,  상태: []
result = stack.pop(outValue);       // false, 상태: []
```

#### 2.3.3. `peek` 메서드를 구현한다

- `peek` 메서드는 스택의 Top 위치에 저장된 원소의 값을 제거하지 않고 조회할 때 사용한다.

- 이 메서드는 유일한 인자로 `IntValue outValue`를 받는다.

- 스택이 비어 있는 경우 조회는 실패한다.

- 조회에 성공한 경우 `true`를 반환하며 조회된 값은 `outValue.value`에 저장된다.

- 조회에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
Stack stack = new Stack(3);         // 내부 저장 공간 크기: 3,  상태: []

boolean result;
IntValue outValue = new IntValue();

stack.push(5);                      // 상태: [5] <- top
stack.push(7);                      // 상태: [5, 7] <- top

result = stack.peek(outValue);      // true,  outValue.value == 7,  상태: [5, 7] <- top
result = stack.pop(outValue);       // true,  outValue.value == 7,  상태: [5] <- top
result = stack.peek(outValue);      // true,  outValue.value == 5,  상태: [5] <- top
```

#### 2.3.4. `getCount` 메서드를 구현한다

- `getCount` 메서드는 스택에 현재 저장된 원소의 개수를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
Stack stack = new Stack(3);   // 내부 저장 공간 크기: 3,  상태: []

stack.push(5);                // 상태: [5] <- top
stack.push(7);                // 상태: [5, 7] <- top

int count = stack.getCount(); // 2
```

#### 2.3.5. `getCapacity` 메서드를 구현한다

- `getCapacity` 메서드는 스택의 현재 내부 저장 공간의 크기를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
Stack stack = new Stack(3);         // 내부 저장 공간 크기: 3,  상태: []

int capacity = stack.getCapacity(); // 3

stack.push(5);                      // 상태: [5] <- top
stack.push(7);                      // 상태: [5, 7] <- top
stack.push(9);                      // 상태: [5, 7, 9] <- top
stack.push(11);                     // 내부 저장 공간 크기: 6,  상태: [5, 7, 9, 11] <- top

capacity = stack.getCapacity();     // 6
```

#### 2.3.6. `clear` 메서드를 구현한다

- `clear` 메서드는 스택에 현재 저장된 모든 원소를 제거할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 이 메서드는 반환값을 가지지 않는다.

```java
Stack stack = new Stack(5);         // 내부 저장 공간 크기: 5,  상태: []

stack.push(5);                      // 상태: [5] <- top
stack.push(7);                      // 상태: [5, 7] <- top

stack.clear();                      // 상태: []

int count = stack.getCount();       // 0
int capacity = stack.getCapacity(); // 5
```

#### 2.3.7. `isEmpty` 메서드를 구현한다

- `isEmpty` 메서드는 스택이 비어 있는지 여부를 확인할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 스택에 저장된 원소가 하나도 없는 경우 `true`를 반환하며 그렇지 않은 경우 `false`를 반환한다.

```java
Stack stack = new Stack(); // 내부 저장 공간 크기: 0

boolean empty;
empty = stack.isEmpty();   // true

stack.push(5);             // 내부 저장 공간 크기: 4,  상태: [5] <- top
empty = stack.isEmpty();   // false

stack.clear();             // 상태: []
empty = stack.isEmpty();   // true
```

### 2.4. `Queue` 클래스를 구현한다

- `Queue` 클래스는 정수(`int`) 값을 FIFO(First-In, First-Out) 규칙에 따라 저장하고 꺼내는 선형 자료구조이다.

- `Queue` 클래스는 원형 배열(circular array) 기반으로 구현해야 한다.
    - `front`는 현재 맨 앞 원소의 위치를 가리키는 배열 인덱스이다.
    - `rear`는 다음에 원소를 삽입할 위치를 가리키는 배열 인덱스이다.
    - `front`와 `rear`는 원형 구조를 이루며 배열의 끝에 도달하면 다시 처음 위치부터 이어서 사용된다.

- 기본 생성자로 생성된 `Queue`는 초기에는 어떠한 원소도 저장하지 않으며 내부 저장 공간의 크기가 `0`인 상태로 생성된다.
    - 첫 번째 원소를 추가하려는 경우 내부 저장 공간의 크기는 `4`로 확보되어야 한다.

- 생성자의 매개변수로 초기 크기를 지정한 경우 해당 개수의 원소를 저장할 수 있는 내부 저장 공간을 확보한 상태로 생성된다.
    - 초기 크기는 음수로 주어지지 않는다고 가정한다.

- 내부 저장 공간이 가득 찬 상태에서 원소를 추가하려는 경우 기존 내부 저장 공간 크기의 2배에 해당하는 새로운 내부 저장 공간을 확보해야 한다.
    - 이때 기존 원소들은 FIFO 순서를 유지하도록 새 배열의 앞쪽부터 연속적으로 복사되어야 한다.

- 확보된 내부 저장 공간은 어떠한 경우에도 축소되지 않는다.

- `Queue` 클래스는 아래 기능을 제공해야 한다.
    - 원소 추가(enqueue) 및 제거(dequeue)
    - 맨 앞 원소 조회(peek)
    - 현재 저장된 원소의 개수 조회
    - 현재 내부 저장 공간의 크기 조회
    - 현재 저장된 모든 원소 제거
    - 큐가 비어 있는지 여부 확인

#### 2.4.1. `enqueue` 메서드를 구현한다

- `enqueue` 메서드는 큐의 `rear` 위치에 원소를 추가할 때 사용한다.

- 이 메서드는 유일한 인자로 `int value`를 받으며 이는 큐에 추가할 정수 값을 의미한다.

- 원소 추가에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
Queue queue = new Queue(3); // 내부 저장 공간 크기: 3, 상태: [_, _, _]

boolean result;

result = queue.enqueue(5);  // true,  상태: [5, _, _],  (front=0, rear=1)
result = queue.enqueue(7);  // true,  상태: [5, 7, _],  (front=0, rear=2)
result = queue.enqueue(9);  // true,  상태: [5, 7, 9],  (front=0, rear=0)

result = queue.enqueue(11); // true,  내부 저장 공간 크기: 6,  상태: [5, 7, 9, 11, _, _],  (front=0, rear=4)
```

#### 2.4.2. `dequeue` 메서드를 구현한다

- `dequeue` 메서드는 큐의 `front` 위치에 저장된 원소를 제거하고 그 값을 반환할 때 사용한다.

- 이 메서드는 유일한 인자로 `IntValue outValue`를 받는다.

- 큐가 비어 있는 경우 제거는 실패한다.

- 제거에 성공한 경우 `true`를 반환하며 제거된 값은 `outValue.value`에 저장된다.

- 제거에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
Queue queue = new Queue(3);         // 내부 저장 공간 크기: 3, 상태: [_, _, _]

boolean result;
IntValue outValue = new IntValue();

queue.enqueue(5);                   // 상태: [5, _, _],  (front=0, rear=1)
queue.enqueue(7);                   // 상태: [5, 7, _],  (front=0, rear=2)

result = queue.dequeue(outValue);   // true,  outValue.value == 5,  상태: [_, 7, _],  (front=1, rear=2)
result = queue.dequeue(outValue);   // true,  outValue.value == 7,  상태: [_, _, _],  (front=2, rear=2)
result = queue.dequeue(outValue);   // false
```

#### 2.4.3. `peek` 메서드를 구현한다

- `peek` 메서드는 큐의 `front` 위치에 저장된 원소의 값을 제거하지 않고 조회할 때 사용한다.

- 이 메서드는 유일한 인자로 `IntValue outValue`를 받는다.

- 큐가 비어 있는 경우 조회는 실패한다.

- 조회에 성공한 경우 `true`를 반환하며 조회된 값은 `outValue.value`에 저장된다.

- 조회에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
Queue queue = new Queue(3);         // 내부 저장 공간 크기: 3, 상태: [_, _, _]

boolean result;
IntValue outValue = new IntValue();

queue.enqueue(5);                   // 상태: [5, _, _],  (front=0, rear=1)
queue.enqueue(7);                   // 상태: [5, 7, _],  (front=0, rear=2)

result = queue.peek(outValue);      // true,  outValue.value == 5,  상태: [5, 7, _],  (front=0, rear=2)
result = queue.dequeue(outValue);   // true,  outValue.value == 5,  상태: [_, 7, _],  (front=1, rear=2)
result = queue.peek(outValue);      // true,  outValue.value == 7,  상태: [_, 7, _],  (front=1, rear=2)
```

```java
Queue queue = new Queue(3);         // 내부 저장 공간 크기: 3, 상태: [_, _, _]

boolean result;
IntValue outValue = new IntValue(); 

result = queue.enqueue(5);          // true,  상태: [5, _, _],  (front=0, rear=1)
result = queue.enqueue(7);          // true,  상태: [5, 7, _],  (front=0, rear=2)
result = queue.enqueue(9);          // true,  상태: [5, 7, 9],  (front=0, rear=0)

result = queue.peek(outValue);      // true,  outValue.value == 5,  상태: [5, 7, 9],  (front=0, rear=0)
result = queue.dequeue(outValue);   // true,  outValue.value == 5,  상태: [_, 7, 9],  (front=1, rear=0)

result = queue.enqueue(11);         // true,  상태: [11, 7, 9], (front=1, rear=1)
result = queue.enqueue(13);         // true,  내부 저장 공간 크기: 6,  상태: [7, 9, 11, 13, _, _],  (front=0, rear=4)
```

#### 2.4.4. `getCount` 메서드를 구현한다

- `getCount` 메서드는 큐에 현재 저장된 원소의 개수를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
Queue queue = new Queue(3);   // 내부 저장 공간 크기: 3, 상태: [_, _, _]

queue.enqueue(5);             // 상태: [5, _, _]    (front=0, rear=1)
queue.enqueue(7);             // 상태: [5, 7, _]    (front=0, rear=2)

int count = queue.getCount(); // 2
```

#### 2.4.5. `getCapacity` 메서드를 구현한다

- `getCapacity` 메서드는 큐의 현재 내부 저장 공간의 크기를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
Queue queue = new Queue(3);         // 내부 저장 공간 크기: 3, 상태: [_, _, _]

int capacity = queue.getCapacity(); // 3

queue.enqueue(5);                   // 상태: [5, _, _] (front=0, rear=1)
queue.enqueue(7);                   // 상태: [5, 7, _] (front=0, rear=2)
queue.enqueue(9);                   // 상태: [5, 7, 9] (front=0, rear=0)
queue.enqueue(11);                  // 내부 저장 공간 크기: 6, 상태: [5, 7, 9, 11, _, _] (front=0, rear=4)

capacity = queue.getCapacity();     // 6
```

#### 2.4.6. `clear` 메서드를 구현한다

- `clear` 메서드는 큐에 현재 저장된 모든 원소를 제거할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 이 메서드는 반환값을 가지지 않는다.

```java
Queue queue = new Queue(3);         // 내부 저장 공간 크기: 3, 상태: [_, _, _]

queue.enqueue(5);                   // 상태: [5, _, _] (front=0, rear=1)
queue.enqueue(7);                   // 상태: [5, 7, _] (front=0, rear=2)

queue.clear();                      // 상태: [_, _, _] (front=0, rear=0)

int count = queue.getCount();       // 0
int capacity = queue.getCapacity(); // 3
```

#### 2.4.7. `isEmpty` 메서드를 구현한다

- `isEmpty` 메서드는 큐가 비어 있는지 여부를 확인할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 큐에 저장된 원소가 하나도 없는 경우 `true`를 반환하며 그렇지 않은 경우 `false`를 반환한다.

```java
Queue queue = new Queue(3); // 내부 저장 공간 크기: 3, 상태: [_, _, _]

boolean empty;
empty = queue.isEmpty();    // true

queue.enqueue(5);           // 상태: [5, _, _] (front=0, rear=1)
empty = queue.isEmpty();    // false

queue.clear();              // 상태: [_, _, _] (front=0, rear=0)
empty = queue.isEmpty();    // true
```

### 2.5. `LinkedList` 클래스를 구현한다

- 배열 기반의 `List`는 내부 저장 공간을 미리 확보하고 필요할 때 더 큰 배열을 새로 만들어 원소를 복사하는 방식으로 동작한다.
    - 인덱스 기반 접근이 빠르다는 장점이 있다.
    - 중간 삽입·제거 시 원소 이동이 발생하고 저장 공간 확장 시 전체 복사가 필요하다는 단점이 있다.

- Linked List(연결 리스트)는 배열 기반 방식의 한계를 보완하기 위한 선형 자료구조이다.

- `LinkedList` 클래스는 정수(`int`) 값을 순서대로 저장하는 선형 자료구조이다.

- `LinkedList` 클래스는 내부적으로 저장된 원소들의 순서를 보존해야 하며 인덱스는 `0`부터 시작한다.

- `LinkedList`는 각 원소를 노드(node) 단위로 저장하며 노드들이 다음 노드를 가리키는 참조로 연결되는 방식으로 동작한다.

- `LinkedList` 클래스는 아래 기능을 제공해야 한다.
    - 원소 추가, 삽입 및 제거
    - 특정 인덱스의 값 조회 및 변경
    - 현재 저장된 원소의 개수 조회
    - 현재 저장된 모든 원소 제거
    - 리스트가 비어 있는지 여부 확인

- 구현에 필요하다면 추가적인 클래스를 정의하는 것은 허용된다.

#### 2.5.1. `add` 메서드를 구현한다

- `add` 메서드는 리스트의 마지막 위치에 원소를 추가할 때 사용한다.

- 새로운 원소는 새로운 노드로 생성되며 기존 마지막 노드의 다음으로 연결된다.

- 이 메서드는 유일한 인자로 `int value`를 받으며 이는 리스트에 추가할 정수 값을 의미한다.

- 원소 추가에 성공한 경우 `true`를 반환한다.

```java
LinkedList list = new LinkedList(); // 상태: []

boolean result;
result = list.add(5);               // true,  상태: [5]
result = list.add(7);               // true,  상태: [5, 7]
result = list.add(9);               // true,  상태: [5, 7, 9]
```

#### 2.5.2. `insert` 메서드를 구현한다

- `insert` 메서드는 리스트의 특정 위치에 원소를 삽입할 때 사용한다.

- 삽입 시 해당 위치의 앞 노드와 뒤 노드 사이의 연결이 조정된다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `int value`

- 유효하지 않은 인덱스가 주어진 경우 삽입은 실패한다.

- 원소 삽입에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
LinkedList list = new LinkedList(); // 상태: []

boolean result;
list.add(5);                        // 상태: [5]
list.add(7);                        // 상태: [5, 7]

result = list.insert(2, 8);         // true,   상태: [5, 7, 8]
result = list.insert(4, 9);         // false,  상태: [5, 7, 8]
```

#### 2.5.3. `removeAt` 메서드를 구현한다

- `removeAt` 메서드는 리스트의 특정 위치에 저장된 원소를 제거할 때 사용한다.

- 제거 시 해당 노드를 가리키던 연결은 다음 노드를 가리키도록 변경된다.

- 이 메서드는 유일한 인자로 `int index`를 받으며 이는 제거할 원소의 인덱스를 의미한다.

- 유효하지 않은 인덱스가 주어진 경우 제거는 실패한다.

- 원소 제거에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
LinkedList list = new LinkedList(); // 상태: []

boolean result;
list.add(5);                        // 상태: [5]
list.add(6);                        // 상태: [5, 6]
list.add(7);                        // 상태: [5, 6, 7]

result = list.removeAt(1);          // true,   상태: [5, 7]
result = list.removeAt(-1);         // false,  상태: [5, 7]
```

#### 2.5.4. `get` 메서드를 구현한다

- `get` 메서드는 리스트의 특정 위치에 저장된 원소의 값을 조회할 때 사용한다.

- 조회할 인덱스에 도달할 때까지 노드를 순차적으로 따라가야 한다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `IntValue outValue`

- 유효하지 않은 인덱스가 주어진 경우 조회는 실패한다.

- 조회에 성공한 경우 `true`를 반환하며 조회된 값은 `outValue.value`에 저장된다.

- 조회에 실패한 경우 `false`를 반환하며 `outValue.value`는 의미를 가지지 않는다.

```java
LinkedList list = new LinkedList();

boolean result;
IntValue outValue = new IntValue();

list.add(5);
list.add(7);

result = list.get(1, outValue);  // true,  outValue.value == 7
result = list.get(2, outValue);  // false
```

#### 2.5.5. `set` 메서드를 구현한다

- `set` 메서드는 리스트의 특정 위치에 저장된 원소의 값을 변경할 때 사용한다.

- 값 변경 시 해당 인덱스에 해당하는 노드를 찾아 그 노드의 값을 갱신해야 한다.

- 이 메서드는 다음 순서의 인자들을 받는다:
    - `int index`
    - `int value`

- 유효하지 않은 인덱스가 주어진 경우 값 변경은 실패한다.

- 값 변경에 성공한 경우 `true`를 반환하며 실패한 경우 `false`를 반환한다.

```java
LinkedList list = new LinkedList(); // 상태: []

boolean result;
list.add(5);                        // 상태: [5]
list.add(7);                        // 상태: [5, 7]

result = list.set(1, 9);            // true,  상태: [5, 9]
result = list.set(3, 4);            // false, 상태: [5, 9]
```

#### 2.5.6. `getCount` 메서드를 구현한다

- `getCount` 메서드는 리스트에 현재 저장된 원소의 개수를 조회할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

```java
LinkedList list = new LinkedList(); // 상태: []

list.add(5);
list.add(7);

int count = list.getCount();        // 2
```

#### 2.5.7. `clear` 메서드를 구현한다

- `clear` 메서드는 리스트에 현재 저장된 모든 원소를 제거할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 이 메서드는 반환값을 가지지 않는다.

```java
LinkedList list = new LinkedList(); // 상태: []

list.add(5);                        // 상태: [5]
list.add(7);                        // 상태: [5, 7]

list.clear();                       // 상태: []

int count = list.getCount();        // 0
```

#### 2.5.8. `isEmpty` 메서드를 구현한다

- `isEmpty` 메서드는 리스트가 비어 있는지 여부를 확인할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 리스트에 저장된 원소가 하나도 없는 경우 `true`를 반환하며 그렇지 않은 경우 `false`를 반환한다.

```java
LinkedList list = new LinkedList(); // 상태: []

boolean empty;
empty = list.isEmpty();             // true

list.add(5);                        // 상태: [5]
empty = list.isEmpty();             // false

list.clear();                       // 상태: []
empty = list.isEmpty();             // true
```