# Java 프로그래밍

이 저장소는 C 언어를 비롯한 다른 프로그래밍 언어를 통해 이미 기초적인 코딩 경험을 갖춘 학습자를 대상으로, Java를 사용하여 프로그램을 작성하는 데 필요한 기본 개념과 코드 작성 방식을 학습하기 위한 자료를 제공합니다.

이 저장소에서는 객체지향 개념을 기반으로 한 Java 프로그래밍을 학습의 중심으로 삼지 않습니다.

Java는 C 언어에 비해 개발자가 보다 편리하게 코드를 작성할 수 있도록 설계된 언어로, 메모리 관리와 문자열 처리 그리고 다양한 표준 라이브러리 제공을 통해 개발자가 직접 구현해야 했던 많은 기능을 언어와 실행 환경이 추상화하여 제공하고 있습니다.

이 저장소는 이러한 특성을 바탕으로 보다 편리하게, 안전한 방식으로 프로그램을 작성하는 과정을 통해 Java가 제공하는 기본적인 기능과 코드 작성 방식에 익숙해지는 것을 목표로 합니다.

이를 위해 클래스는 Java 언어에서 요구되는 최소한의 코드 단위로만 사용되며, 불필요한 추상화나 객체 모델링은 의도적으로 배제됩니다.

즉, 이 저장소는 객체지향 개념을 본격적으로 다루기 이전에 Java를 안정적이고 효율적으로 사용하기 위한 기초를 다지는 데 초점을 둡니다.

> C 언어의 개념 위에서 더 안전하고 더 편리한 언어로서의 Java를 이해하자

## 목차 및 노트

각 장별 학습 노트는 [`notes/`](./notes) 디렉토리에, 예시 코드는 [`example-code/`](./example-code) 디렉토리에 정리되어 있습니다.

| 번호 | 주제                         | 노트                                                 | 예시 코드        |
| :--  | :--------------------------- | :--------------------------------------------------- | :--------------: |
| 01   | 프로그래밍 언어의 분류       | [01-programming-language-classification]             |                  |
| 02   | 값과 참조                    | [02-values-and-references]                           | [example-02]     |
| 03   | 언어 차원의 표현력 확장      | [03-enhancing-expressiveness-at-the-language-level]  | [example-03]     |
| 04   | 언어 차원의 데이터 관리      | [04-data-management-at-the-language-level]           | [example-04]     |
| 05   | 언어 차원의 문법적 편의 기능 | ~~[05-syntactic-convenience-at-the-language-level]~~ | ~~[example-05]~~ |
| 06   | ~~타입을 정의하는 도구~~     | ~~[06-tools-for-defining-types]~~                    | ~~[example-06]~~ |
| 07   | ~~예외 처리~~                | ~~[07-exception-handling]~~                          | ~~[example-07]~~ |


## 실습(Lab)

실습에 대한 자세한 안내는 [`lab/`](./lab) 디렉토리의 [`README.md`](./lab/README.md)에 정리되어 있습니다.

| 번호 | 제목                   | 경로     |
| :--  | :--------------------- | :------: |
|  01  | Big Number Calculator  | [lab-01] |
|  02  | Expression Evaluator   | [lab-02] |
|  03  | TODO Analyzer          | [lab-03] |
|  04  | Space Convoy Simulator | [lab-04] |
|  05  | Data Structures        | [lab-05] |


## 학습 방법 제안

다음과 같은 순서로 학습하는 것을 권장합니다.

1. `notes/`의 학습 슬라이드를 통해 개념을 이해한다.
2. `example-code/`의 예제를 통해 해당 개념이 코드로 어떻게 나타나는지 확인한다.
3. `lab/`의 실습을 통해 직접 코드를 작성하며 적용해본다.

## 부정확한 내용에 대한 기여

정보가 부정확하거나 누락된 부분이 있을 수 있습니다.  
오류를 발견하시거나 추가하고 싶은 내용이 있으시면 Pull Request 또는 Issue를 통해 알려주세요.

여러분의 피드백은 더 나은 자료를 만드는 데 큰 도움이 됩니다.

## 문의

이 저장소를 통해 공부하시다가 내용, 실습, 설명 등에 대해 궁금한 점이 생기면 언제든지 potterLim0808@gmail.com 으로 문의 주시면 성실히 답변드리겠습니다.

> 최종 수정일: 2026-01-06


[01-programming-language-classification]: ./notes/01-programming-language-classification.pdf
[02-values-and-references]: ./notes/02-values-and-references.pdf
[03-enhancing-expressiveness-at-the-language-level]: ./notes/03-enhancing-expressiveness-at-the-language-level.pdf
[04-data-management-at-the-language-level]: ./notes/04-data-management-at-the-language-level.pdf

[example-02]: ./example-code/src/main/java/com/example/valuesandreferences/
[example-03]: ./example-code/src/main/java/com/example/expressiveness/
[example-04]: ./example-code/src/main/java/com/example/collections/

[lab-01]: ./lab/01-big-number-calculator/SPEC.md
[lab-02]: ./lab/02-expression-evaluator/SPEC.md
[lab-03]: ./lab/03-todo-analyzer/SPEC.md
[lab-04]: ./lab/04-space-convoy/SPEC.md
[lab-05]: ./lab/05-data-structures/SPEC.md
