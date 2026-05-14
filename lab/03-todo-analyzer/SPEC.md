# Todo Analyzer 실습

소프트웨어 프로젝트를 진행하다 보면 코드 곳곳에 나중에 처리해야 할 작업을 의미하는 TODO 주석이 남게 된다. 작은 프로젝트에서는 눈으로 직접 확인할 수 있지만 파일 수가 늘어나면 어떤 TODO가 어디에 얼마나 남아 있는지 파악하기가 점점 어려워진다.

이 실습에서는 특정 디렉터리에 포함된 여러 텍스트 파일을 분석하여 코드에 남아 있는 TODO 항목을 체계적으로 추출하고 정해진 형식의 리포트 문자열을 생성하는 `Todo Analyzer`를 구현한다.
이를 통해 파일 입출력, 문자열 파싱, 상태 기반 처리, 그리고 결과 정규화 및 정렬과 같은 여러 처리 과정을 하나의 프로그램으로 엮는 경험을 한다.

이 실습에서 파일 입출력은 분석할 텍스트를 가져오고 리포트를 저장하기 위한 수단이며, 핵심은 읽어온 문자열에서 의미 있는 TODO 항목을 찾아 일관된 결과로 정리하는 데 있다.

이 실습의 목적은 단순히 파일을 읽는 데 그치지 않고 명확한 규칙에 따라 정보를 수집하고 그 결과를 일관된 형식으로 정리하여 제공하는 프로그램을 직접 설계하고 구현하는 데 있다.

## 전반적인 규칙

- 분석 대상 파일은 UTF-8 인코딩 텍스트 파일로 간주하며 파일은 UTF-8로 읽어서 처리한다.

- 이 실습에서 TODO는 대소문자를 포함하여 정확히 일치하는 다음 패턴만 인정한다.
    - 한 줄 주석 TODO: `"// TODO: "`
    - 블록 주석 TODO 시작: `"/* TODO: "`

- 분석 대상 파일의 확장자는 다음으로 한정한다.
    - `.c`, `.cpp`, `.h`
    - `.java`, `.cs`
    - `.py`
    - `.js`, `.ts`, `.html`

- 확장자 비교는 대소문자를 구분하지 않는다.

- 실습 명세에서 제공된 메서드 시그니처는 수정할 수 없으나 필요에 따라 추가적인 `private` 도움 메서드를 작성하는 것은 허용된다.

- 실습 명세에 명시되지 않은 동작은 제공된 예시 코드를 기준으로 추론하여 구현한다.
    - 예시 코드로도 추론이 어려운 경우 명세의 규칙을 위반하지 않는 범위 내에서 합리적으로 판단하여 구현한다.

## 1. 프로젝트를 준비한다

1. IntelliJ에서 `java-labs` 프로젝트를 연다.
2. `03-todo-analyzer` 디렉터리로 이동한다.
3. `03-todo-analyzer` 디렉터리에 제공된 `src/main/java` 디렉터리를 확인한다.
4. `src/main/java` 디렉터리 아래의 `com.example.todoanalyzer` 패키지를 확인한다.
5. `com.example.todoanalyzer` 패키지에 `TodoAnalyzer` 클래스를 정의한다.

## 2. `TodoAnalyzer` 클래스를 구현한다

### 2.1. `generateTodoReportOrNull` 정적 메서드를 구현한다

- `generateTodoReportOrNull` 메서드는 지정한 디렉터리에 존재하는 파일들을 분석하여 TODO 리포트를 생성할 때 사용한다.
    - 하위 디렉터리는 탐색하지 않으며 지정한 디렉터리에 바로 존재하는 파일들만을 분석 대상으로 삼는다.

- 이 메서드는 유일한 매개 변수로 `String directoryPathOrNull`을 받는다.

- 반환 규칙은 다음과 같다:
    - `directoryPathOrNull`이 유효하지 않은 경로이거나 TODO 리포트를 올바르게 생성하는 데 실패한다면 `null`을 반환한다.
    - TODO 리포트를 생성하는 데 성공한다면 생성한 리포트 파일의 절대 경로(absolute path)를 반환한다.

- 리포트 파일 생성 규칙은 다음과 같다:
    - 리포트 파일은 `directoryPathOrNull` 디렉터리 바로 아래에 생성한다.
    - 기본 파일명은 `report.txt`이다.
    - 동일한 이름의 파일이 이미 존재하는 경우 다음 규칙에 따라 파일명을 결정한다:
        - `report.txt`가 존재하면 `report-2.txt`
        - `report-2.txt`도 존재하면 `report-3.txt`
        - 같은 규칙으로 번호를 1씩 증가시킨다.

- 주석이 중첩되는 경우는 없다고 가정한다.

#### 2.1.1. TODO 추출 규칙

- 하나의 파일에는 TODO가 여러 개 존재할 수 있으며 TODO는 파일을 위에서 아래로 읽어가며 발견되는 순서대로 모두 추출한다.

- 한 줄 주석 TODO(`"// TODO: "`)의 추출 규칙은 다음과 같다:
    - TODO 패턴 이후부터 해당 줄의 끝까지를 TODO 내용으로 간주한다.
    - 줄 끝에는 개행 문자가 포함되지 않는다.

- 블록 주석 TODO 시작(`"/* TODO: "`)의 추출 규칙은 다음과 같다:
    - TODO 패턴 이후부터 블록 주석이 종료되는 `*/` 직전까지의 내용을 TODO 내용으로 간주한다.
    - 블록 주석은 여러 줄에 걸쳐 존재할 수 있다.
    - 블록 주석의 종료 표기 `*/`는 같은 줄에 있을 수도 있고 이후 줄에 있을 수도 있다.
    - 블록 주석 내부에서 가장 먼저 등장하는 `*/`를 블록 주석의 종료로 간주한다.
    - 파일 끝까지 탐색했음에도 `*/`를 찾지 못한 경우 해당 TODO는 무시한다.

#### 2.1.2. TODO 정규화 규칙

- 추출한 TODO 내용은 아래 규칙으로 정규화한 뒤 리포트에 반영한다.

- 정규화 규칙은 다음과 같다:
    - TODO 내용의 앞뒤 공백(스페이스, 탭, 개행 등 모든 공백 문자)을 제거한다.
    - TODO 내용 내부에 연속된 공백 문자가 2개 이상 존재하는 경우 이를 공백 1개로 축약한다.
        - 공백 문자는 스페이스, 탭, 개행 등 모든 whitespace를 포함한다.

- 정규화 결과가 빈 문자열이면 해당 TODO는 무시한다.

#### 2.1.3. 리포트 출력 포맷

- 리포트는 텍스트 파일이며 인코딩은 UTF-8로 저장한다.

- 리포트는 다음 규칙의 텍스트로 구성한다.
    1. 파일 경로
        - TODO가 1개 이상 존재하는 파일에 대해서만 리포트에 포함한다.
        - 파일명은 Java 문자열의 기본 비교 기준에 따라 오름차순으로 정렬하여 출력한다.
        - 각 파일의 첫 줄은 파일명(확장자 포함)으로 출력한다.

    2. TODO 항목
        - 파일명 다음 줄부터 해당 파일에서 추출된 TODO들을 한 줄에 하나씩 출력한다.
        - 각 TODO 항목의 출력 형식은 다음과 같다.
        ```
        - <normalizedTodoText>
        ```
        - TODO 항목은 파일 내에서 등장한 순서를 유지한다.

    3. 파일 간 구분
        - 서로 다른 파일의 블록(파일명 + TODO 목록) 사이에는 빈 줄 한 줄을 출력한다.

    4. TODO가 없는 경우
        - 모든 분석 대상 파일에서 TODO가 하나도 추출되지 않은 경우 리포트 파일 내용은 빈 문자열이어야 한다.

- 리포트의 마지막 줄에는 개행 문자(`'\n`)가 없어야 한다.
```c
// alpha.c
int add(int a, int b)
{
    return a + b; // TODO: handle overflow later
}
```

```java
// beta.java
public final class Beta {
    public static void main(String[] args) {
    /* TODO: refactor main logic
             - extract method
             - remove duplication 
    */
    int x = 10;


    // TODO: add input validation
    System.out.println(x);
    }
}
```

```java
String reportFilePath = TodoAnalyzer.generateTodoReportOrNull(directoryPath);

// reportFilePath == "/home/user/projects/todo-analyzer/testdata/report.txt"
```

생성된 리포트 파일(`report.txt`)의 내용은 다음과 같다.

```
alpha.c
- handle overflow later

beta.java
- refactor main logic - extract method - remove duplication
- add input validation
```
