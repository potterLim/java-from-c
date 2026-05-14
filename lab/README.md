# Java 실습

이 Java 실습들은 Java 문법에 대한 기초 숙련부터 구조적 사고, 파일 처리, 클래스 설계, 그리고 자료구조 구현을 포함한 종합적인 구현 능력을 단계적으로 훈련할 수 있도록 설계되었다.

각 실습은 앞선 단계에서 익힌 개념을 자연스럽게 확장하며 단순한 문법 암기나 API 사용을 넘어 문제를 분석하고 해결 과정을 나누어 생각하는 연습을 하도록 구성되어 있다.

최종 목표는 Java 문법을 아는 것에 그치지 않고 Java로 문제를 설계하고 구현할 수 있는 개발자로 성장하는 것이다.

## 실습 환경(권장)

이 실습은 다음 환경을 기준으로 설명을 제공한다.
- IDE: [IntelliJ IDEA](https://www.jetbrains.com/ko-kr/idea/)
- JDK: [Java 17(LTS) Temurin OpenJDK](https://adoptium.net/temurin/releases?version=17)
- Language Level: Java 17
- 외부 라이브러리: 사용하지 않음 (Java 표준 라이브러리만 사용)

이미 익숙하게 사용 중인 IDE나 설치된 JDK가 있다면 그대로 사용해도 무방하다.  
다만 IDE나 JDK를 새로 설치해야 하거나 아직 특정 환경에 익숙하지 않은 경우에는
본 문서에서 안내하는 환경으로 설정하여 실습을 진행하는 것을 권장한다.

특정 IDE나 특정 JDK 배포판, 특정 Java 버전에 종속되는 내용은 포함하지 않으며
너무 오래된 버전이 아니라면 표준 Java 환경에서 동일하게 실습을 진행할 수 있다.

## Java 실습 목록

| 번호 | 제목                   | 과제                |
| :--  | :--------------------- | :-----------------: |
|  01  | Big Number Calculator  | [과제 보기][lab-01] |
|  02  | Expression Evaluator   | [과제 보기][lab-02] |
|  03  | TODO Analyzer          | [과제 보기][lab-03] |
|  04  | Space Convoy Simulator | [과제 보기][lab-04] |
|  05  | Linear Data Structures | [과제 보기][lab-05] |

각 실습 디렉터리에는 과제 설명 파일(`SPEC.md`), 검증용 `Main` 클래스, 예시 코드가 함께 제공된다.  
과제 명세를 꼼꼼히 읽고 검증용 `Main` 클래스로 주요 동작을 확인한 뒤, 예시 코드와 본인의 구현을 비교·분석하면 학습 효과를 더욱 높일 수 있다.

## 검증용 Main 실행

각 실습 디렉터리에는 구현 결과를 확인하기 위한 검증용 `Main` 클래스가 제공된다.

과제 명세에 따라 필요한 클래스를 구현한 뒤, 해당 실습의 `Main` 클래스를 실행하여 주요 동작이 기대 결과와 일치하는지 확인한다.

검증용 `Main`은 학습자가 직접 수정해야 하는 과제 대상이 아니며, 구현한 코드가 명세의 핵심 규칙을 만족하는지 확인하기 위한 실행 코드이다.

## 실습 흐름과 학습 목표

이 실습은 단계적으로 구현 범위를 확장하며 Java로 문제를 해결하는 사고 과정을 훈련하도록 구성되어 있다.

초반 실습에서는 문자열, 배열, 조건문, 반복문을 직접 다루며 Java 문법과 절차적 구현 방식에 익숙해지는 데 집중한다.  
이후에는 복잡한 문제를 작은 단위로 나누고 상태를 분리하는 구조적 사고를 훈련하며 파일을 읽고 파일에 포함된 데이터를 처리하는 과정을 통해 입력 데이터가 콘솔을 넘어 외부 자원으로 확장될 때의 전형적인 처리 흐름을 경험한다.

파일 입출력은 이 실습들의 중심 학습 목표라기보다, 외부에 저장된 데이터를 Java 코드 안의 문자열, 배열, 컬렉션, 객체로 가져오기 위한 입력 단계로 사용한다.  
파일에서 읽어온 데이터를 어떤 규칙으로 해석하고, 어떤 구조로 정리하며, 어떤 책임으로 나누어 처리할지에 더 집중한다.

다음 단계에서는 여러 클래스를 정의하고 역할에 따라 로직을 분리하여 연결하는 방식으로 객체 간 책임 분리와 상태 관리 등 클래스 기반 구현의 기본적인 사고 방식을 훈련한다.  
이 단계의 목적은 복잡한 객체지향 설계를 완성하는 것이 아니라 여러 클래스를 사용해 로직을 나누고 조합하는 경험을 쌓는 데 있다.

마지막 실습에서는 배열과 인덱스를 직접 다루며 다양한 자료구조를 구현한다.  
이 단계는 지금까지 익힌 문법, 조건문, 반복문, 클래스 구조를 모두 활용하여 정확성과 가독성을 동시에 요구하는 구현을 완성하는 종합 훈련 단계에 해당한다.

## 코딩 표준과 가독성

각 실습에서 학습자가 구현하는 코드, 검증용 `Main` 클래스, 제공 예시 코드는 [코딩 표준](../java-coding-standard.md)을 기준으로 작성되어 있다.

이 실습들은 단순히 동작하는 코드를 작성하는 데서 그치지 않고 일관된 스타일과 읽기 쉬운 구조를 유지하며 구현하는 습관을 함께 훈련하는 것을 목표로 한다.

변수명과 메서드 이름, 코드의 흐름과 책임 분리를 의식하며 다른 사람이 읽어도 의도를 파악할 수 있는 코드를 작성하는 경험을 쌓는 것이 중요하다.

이를 통해 실습 과정 전반에서 단순히 동작하는 코드를 넘어 의도와 구조가 드러나는 코드를 작성하는 감각을 익히기를 기대한다.

## 실습 프로젝트 초기 설정

### 1. IntelliJ에서 새 프로젝트 만들기

#### 1.1. 새 프로젝트 생성

1. IntelliJ 실행
2. New Project 선택
3. 왼쪽 목록에서 Java 선택
4. 아래 항목 설정

| 항목            | 설정 값              |
| :---------      | :------------------- |
| Name            | `java-labs`          |
| Location        | 원하는 작업 디렉터리 |
| Build system    | `Gradle`             |
| JDK             | `JDK 17`             |
| Gradle DSL      | `Groovy`             |
| Add sample code | 체크 해제            |

5. Advanced Settings 설정
    - Gradle distribution: `Wrapper`
    - GroupId: `com.example` 또는 개인 식별용 네임스페이스 (`com.yourname` 등)
        - 실습에서 안내되는 모든 문서는 `com.example`라고 가정
    - 나머지는 기본값 유지

6. Create 버튼을 클릭하여 프로젝트를 생성한다.

#### 1.2. 생성 후 디렉터리 정리

프로젝트 생성 직후 구조:

```
java-labs/
 ├─ .gradle/
 ├─ .idea/
 ├─ gradle/
 ├─ src/
 ├─ .gitignore
 ├─ build.gradle
 ├─ gradlew
 ├─ gradlew.bat
 ├─ settings.gradle
```

기본 생성된 src 디렉터리 삭제 후 구조:

```
java-labs/
 ├─ .gradle/
 ├─ .idea/
 ├─ gradle/
 ├─ .gitignore
 ├─ build.gradle
 ├─ gradlew
 ├─ gradlew.bat
 ├─ settings.gradle
```

### 2. 디렉터리 생성 및 Gradle 설정

#### 2.1. 디렉터리 생성

`java-labs` 루트 디렉터리에서 아래 디렉터리를 생성:
- `01-big-number-calculator`
- `02-expression-evaluator`
- `03-todo-analyzer`
- `04-space-convoy`
- `05-linear-data-structures`

생성 후 구조:

```
java-labs/
 ├─ .gradle/
 ├─ .idea/
 ├─ 01-big-number-calculator/
 ├─ 02-expression-evaluator/
 ├─ 03-todo-analyzer/
 ├─ 04-space-convoy/
 ├─ 05-linear-data-structures/
 ├─ gradle/
 ├─ .gitignore
 ├─ build.gradle
 ├─ gradlew
 ├─ gradlew.bat
 └─ settings.gradle
```

#### 2.2. settings.gradle 수정

`java-labs/settings.gradle`을 열고 아래처럼 작성:

```
rootProject.name = 'java-labs'

include '01-big-number-calculator'
include '02-expression-evaluator'
include '03-todo-analyzer'
include '04-space-convoy'
include '05-linear-data-structures'
```

#### 2.3. 루트 build.gradle 공통 설정 작성

`java-labs/build.gradle`을 아래처럼 설정:

```
group = 'com.example'
version = '1.0-SNAPSHOT'

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply plugin: 'java'

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}
```

#### 2.4. 각 디렉터리에 build.gradle 생성
각 디렉터리에 `build.gradle` 파일을 하나씩 생성한다.

- `01-big-number-calculator/build.gradle`
- `02-expression-evaluator/build.gradle`
- `03-todo-analyzer/build.gradle`
- `04-space-convoy/build.gradle`
- `05-linear-data-structures/build.gradle`

이 단계에서는 파일 내용은 비워 둔다.

#### 2.5. Gradle Reload

IntelliJ 오른쪽 Gradle 창에서 다음 버튼 클릭:

- `Sync All Gradle Projects` (새로고침 아이콘)

정상이라면 Gradle 창에 모든 실습 모듈이 표시된다.

[lab-01]: ./01-big-number-calculator/SPEC.md
[lab-02]: ./02-expression-evaluator/SPEC.md
[lab-03]: ./03-todo-analyzer/SPEC.md
[lab-04]: ./04-space-convoy/SPEC.md
[lab-05]: ./05-linear-data-structures/SPEC.md
