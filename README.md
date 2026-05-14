# Java 프로그래밍

이 저장소는 C 언어를 비롯한 다른 프로그래밍 언어로 기초적인 코딩 경험을 쌓은 학습자가 Java로 프로그램을 작성하는 데 필요한 기본 개념과 코드 작성 방식을 익히기 위한 학습 자료입니다.

이 자료는 객체지향 설계 자체를 학습의 중심으로 삼지는 않습니다.  
대신 C 언어의 개념 위에서 Java가 제공하는 더 안전하고 편리한 표현 방식, 메모리 관리, 문자열 처리, 표준 라이브러리, 클래스 기반 코드 구성을 단계적으로 익히는 데 초점을 둡니다.

목표는 Java 문법을 단순히 외우는 것이 아니라, 문제를 읽고 적절한 자료형과 흐름으로 나누어 안정적으로 구현하는 감각을 기르는 것입니다.  
객체지향 개념은 필요한 만큼만 사용하며, 불필요한 추상화나 과도한 객체 모델링은 의도적으로 배제합니다.

> [!NOTE]
> C 언어의 개념 위에서 더 안전하고 더 편리한 언어로서의 Java를 이해하자

## 자료 구성

각 장별 학습 노트는 [`notes/`](./notes) 디렉터리에, 예시 코드는 [`example-code/`](./example-code) 디렉터리에 정리되어 있습니다.

| 번호 | 주제                          | 노트                                               | 예시 코드    |
| :--  | :---------------------------- | :------------------------------------------------- | :----------: |
| 01   | 언어 분류와 설계 관점         | [01-language-classification-and-design-perspectives] |      -       |
| 02   | 값과 참조                     | [02-values-and-references]                         | [example-02] |
| 03   | 표현력과 메서드 설계          | [03-expressiveness-and-method-design]              | [example-03] |
| 04   | 컬렉션을 활용한 데이터 관리   | [04-managing-data-with-collections]                | [example-04] |
| 05   | 문법과 언어 사용성            | [05-syntax-and-language-ergonomics]                | [example-05] |
| 06   | 사용자 정의 자료형            | [06-user-defined-types]                            | [example-06] |

## 실습

실습은 개념을 코드로 옮기는 훈련을 위해 구성되어 있습니다.  
환경 설정과 진행 방법은 [`lab/README.md`](./lab/README.md)에 정리되어 있으며, Java 17 기준으로 진행합니다.

| 번호 | 제목                   | 경로     |
| :--  | :--------------------- | :------: |
|  01  | Big Number Calculator  | [lab-01] |
|  02  | Expression Evaluator   | [lab-02] |
|  03  | TODO Analyzer          | [lab-03] |
|  04  | Space Convoy Simulator | [lab-04] |
|  05  | Data Structures        | [lab-05] |

## 학습 방법

다음 순서로 학습하는 것을 권장합니다.

1. `notes/`의 학습 슬라이드를 통해 개념을 이해한다.
2. `example-code/`의 예제를 통해 해당 개념이 코드로 어떻게 나타나는지 확인한다.
3. `lab/`의 실습을 통해 직접 코드를 작성하며 적용해본다.

실습을 진행할 때는 단순히 동작하는 코드를 만드는 데서 그치지 않고, [코딩 표준](./java-coding-standard.md)을 바탕으로 일관된 이름, 명확한 흐름, 읽기 쉬운 구조를 갖춘 코드를 작성하는 훈련을 함께 한다.

강의 노트의 예시는 Java 문법과 개념을 설명하기 위해 일반적인 Java 관례를 기준으로 작성되어 있습니다. 일반적인 Java 관례와 다른 고유의 코딩 표준은 강의 노트 예시에 일괄 반영하지 않았습니다.

## 부정확한 내용에 대한 기여

정보가 부정확하거나 누락된 부분이 있을 수 있습니다.  
오류를 발견하시거나 추가하고 싶은 내용이 있으시면 Pull Request 또는 Issue를 통해 알려주세요.

여러분의 피드백은 더 나은 자료를 만드는 데 큰 도움이 됩니다.

## 문의

이 저장소를 통해 공부하시다가 내용, 실습, 설명 등에 대해 궁금한 점이 생기면 언제든지 potterLim0808@gmail.com으로 문의 주시면 성실히 답변드리겠습니다.

> 최종 수정일: 2026. 05. 14.

[01-language-classification-and-design-perspectives]: ./notes/01-language-classification-and-design-perspectives.pdf
[02-values-and-references]: ./notes/02-values-and-references.pdf
[03-expressiveness-and-method-design]: ./notes/03-expressiveness-and-method-design.pdf
[04-managing-data-with-collections]: ./notes/04-managing-data-with-collections.pdf
[05-syntax-and-language-ergonomics]: ./notes/05-syntax-and-language-ergonomics.pdf
[06-user-defined-types]: ./notes/06-user-defined-types.pdf

[example-02]: ./example-code/src/main/java/com/example/valuesandreferences/
[example-03]: ./example-code/src/main/java/com/example/expressiveness/
[example-04]: ./example-code/src/main/java/com/example/collections/
[example-05]: ./example-code/src/main/java/com/example/syntacticconvenience/
[example-06]: ./example-code/src/main/java/com/example/userdefinedtypes/

[lab-01]: ./lab/01-big-number-calculator/SPEC.md
[lab-02]: ./lab/02-expression-evaluator/SPEC.md
[lab-03]: ./lab/03-todo-analyzer/SPEC.md
[lab-04]: ./lab/04-space-convoy/SPEC.md
[lab-05]: ./lab/05-data-structures/SPEC.md
