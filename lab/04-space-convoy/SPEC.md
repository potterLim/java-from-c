# Space Convoy Simulator 실습

이 실습에서는 여러 대의 우주선이 하나의 항로(convoy lane)에 진입하여 정해진 규칙에 따라 레이저 교전과 수리 과정을 반복하는 턴 기반 시뮬레이션인 `Space Convoy Simulator`를 구현한다.

항로는 수용 가능한 최대 우주선 수(capacity)를 가지며 수용 한도에 도달한 경우 추가로 우주선을 진입시킬 수 없다. 항로에 진입한 우주선들은 로드된 순서를 유지한 채 매 턴 행동을 수행한다. 각 우주선은 자신의 바로 앞에 위치한 우주선을 공격 대상으로 삼으며 첫 번째 우주선의 공격 대상은 순환 구조에 따라 마지막 우주선이 된다.

한 턴은 레이저 발사 단계와 수리 단계로 구성된다.  
레이저 발사 단계가 모두 끝난 뒤 내구도(`hull`)가 0이 된 우주선은 즉시 항로에서 제거되며 제거된 우주선은 해당 턴의 수리 단계에 참여하지 않는다. 이 과정을 반복하여 항로에 우주선이 1대 이하로 남은 경우 시뮬레이션은 더 이상 진행되지 않는다.

이 실습의 목적은 우주선, 공격 규칙, 피해 처리, 선체 등급, 항로 상태와 같은 요소를 객체 지향적으로 분리하고 모델링하여 여러 클래스가 협력하는 턴 기반 시뮬레이션을 명확한 구조로 설계·구현하는 경험을 하는 데 있다.

## 전반적인 규칙

- 실습 명세에서 제공된 메서드 시그니처와 멤버 변수는 수정할 수 없다.
    - 구현에 필요하다면 추가적인 `private` 도움 메서드를 작성하는 것은 허용된다.
    - 구현에 필요하다면 추가적인 `private` 멤버 변수를 작성하는 것은 허용된다.

- 실습 명세에서 제공된 멤버 변수에 해당하는 정보는 클래스 외부에서 조회 가능해야 한다.

- 실습 명세에 명시되지 않은 동작은 제공된 예시 코드를 통해 추론하여 구현한다.
    - 예시 코드로도 추론이 어려운 경우 명세의 규칙을 위반하지 않는 범위 내에서 합리적으로 판단하여 구현한다.

- 입력 파일의 데이터는 항상 올바르다고 가정한다.

## 1. 프로젝트를 준비한다

1. IntelliJ에서 `java-labs` 프로젝트를 연다.
2. `04-space-convoy` 디렉터리로 이동한다.
3. `04-space-convoy` 디렉터리 아래에 `src/main/java` 디렉터리를 생성한다.
4. `src/main/java` 디렉터리 아래에 `com.example.spaceconvoy` 패키지를 생성한다.
5. `com.example.spaceconvoy` 패키지에 `HullGrade` 열거형(enum)을 정의한다.
6. `com.example.spaceconvoy` 패키지에 `Ship` 클래스를 정의한다.
7. `com.example.spaceconvoy` 패키지에 `ConvoyLane` 클래스를 정의한다.
8. `com.example.spaceconvoy` 패키지에 `Main` 클래스를 정의한다.

## 2. 시뮬레이터를 구현한다

### 2.1. `HullGrade` 열거형을 구현한다

- 우주선은 다음의 4가지 선체 등급 중 하나를 가진다.
    - LIGHT
    - MEDIUM
    - HEAVY
    - ARMORED

- 선체 등급은 우주선이 레이저 공격을 받아 내구도가 감소하는 양에 영향을 미친다.

### 2.2. `Ship` 클래스를 구현한다

- `Ship` 클래스의 생성자는 다음 순서의 인자들을 받는다.
    - `String name`
    - `HullGrade hullGrade`
    - `int hull`
    - `int laserDamage`
    - `int shield`
    - `int repairAmount`

- 생성자의 `hull`, `laserDamage`, `shield`, `repairAmount` 인자로 음수값이 들어오지 않는다고 가정한다.

- `Ship` 클래스는 다음의 멤버 변수들을 가진다.
    - `String name`
    - `HullGrade hullGrade`
    - `int hull`
    - `int laserDamage`
    - `int shield`
    - `int repairAmount`

- 위 멤버 변수들은 클래스 외부에서 직접 수정할 수 없어야 한다.

- 내구도(`hull`)는 0 미만이 될 수 없으며 변경 결과가 0보다 작아질 경우 0으로 처리한다.

#### 2.2.1. `applyHullChange` 메서드를 구현한다

- `applyHullChange` 메서드는 우주선의 내구도(`hull`)를 변화시킬 때 사용한다.

- 이 메서드는 유일한 인자로 `int delta`를 받는다.

- 우주선의 내구도는 `delta`만큼 변화한다.

- 이 메서드는 반환값을 가지지 않는다.

```java
Ship ship = new Ship("Ship", HullGrade.MEDIUM, 40, 14, 8, 3);

ship.applyHullChange(6);  // ship.hull == 46
ship.applyHullChange(-9); // ship.hull == 37
```

#### 2.2.2. `fireLaser` 메서드를 구현한다

- `fireLaser` 메서드는 우주선이 다른 우주선에게 레이저를 발사할 때 사용한다.

- 이 메서드는 유일한 인자로 `Ship otherShip`을 받으며 이는 레이저 공격을 받는 우주선을 의미한다.

- 레이저의 기본 피해치는 공격하는 우주선의 `laserDamage`와 같다.

- 공격받는 우주선이 입는 피해치는 다음 규칙에 따라 계산한다.
    - 방패 감소 피해치는 `laserDamage - otherShip.shield`로 계산한다.
        - 방패 감소 피해치가 0 이하인 경우 최종 피해치는 0으로 취급한다.
    
    - 공격받는 우주선의 선체 등급에 따라 피해 배율을 적용한다.
        - 선체 등급별 피해 배율은 다음과 같다.
            - `LIGHT`: 1.25
            - `MEDIUM`: 1.00
            - `HEAVY`: 0.85
            - `ARMORED`: 0.70
    - 선체 등급 적용 피해치는 `방패 감소 피해치 × 배율`로 계산하며 소수점 이하는 버린다.
    - 방패 감소 피해치가 1 이상이었다면 선체 등급 적용 피해치는 반드시 1 이상이어야 한다.

- 위 규칙으로 계산된 값을 최종 피해치라고 한다.

- 공격받는 우주선은 최종 피해치만큼 내구도(`hull`)가 감소한다.

- 이 메서드는 반환값을 가지지 않는다.

```java
Ship defender = new Ship("Defender", HullGrade.HEAVY, 24, 11, 6, 4);
Ship attacker = new Ship("Attacker", HullGrade.MEDIUM, 28, 15, 10, 3);

attacker.fireLaser(defender); // defender.hull == 17
```

#### 2.2.3. `repair` 메서드를 구현한다

- `repair` 메서드는 우주선이 자신의 선체를 수리하여 내구도(`hull`)를 회복할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 회복량은 `repairAmount`와 같다.

- 우주선의 내구도는 회복량만큼 증가한다.

- 이 메서드는 반환값을 가지지 않는다.

```java
Ship defender = new Ship("Defender", HullGrade.HEAVY, 24, 11, 6, 4);

defender.repair(); // defender.hull == 28
```

### 2.3. `ConvoyLane` 클래스를 구현한다

- `ConvoyLane` 클래스의 생성자는 다음 순서의 인자들을 받는다.
    - `String laneName`
    - `int capacity`

- 생성자의 `capacity` 인자로 음수값이 들어오지 않는다고 가정한다.

- `ConvoyLane` 클래스는 다음의 멤버 변수들을 가진다.
    - `String laneName`
    - `int capacity`
    - `int turns`

- 위 값들은 클래스 외부에서 직접 수정할 수 없어야 한다.

- `ConvoyLane`은 우주선들의 상대적인 순서를 관리하며 우주선이 제거되더라도 나머지 우주선들의 순서는 유지된다.

#### 2.3.1. `loadShips` 메서드를 구현한다

- `loadShips` 메서드는 `.csv` 형식 파일로부터 우주선들을 순서대로 읽어 항로에 추가할 때 사용한다.

- 이 메서드는 유일한 인자로 `String filePath`를 받는다.

- `.csv` 형식 파일의 각 줄은 우주선 하나의 정보를 담고 있으며 각 줄의 포맷은 다음과 같다.
    - `Name,HullGrade,Hull,LaserDamage,Shield,RepairAmount`

```
Falcon,MEDIUM,32,14,8,3
```

- 이 메서드를 호출하면 현재 항로를 새로 구성하며 파일의 내용에 따라 우주선들을 다시 로드한다.

- 항로가 수용 인원(`capacity`)에 도달한 경우 남은 우주선은 추가하지 않는다.

- 파일이 존재하지 않거나 읽을 수 없는 경우 항로를 비운 뒤 그대로 종료한다.

```java
/*
datas/ships.csv

Falcon,MEDIUM,32,14,8,3
Vanguard,HEAVY,38,16,10,2
Swift,LIGHT,26,18,5,2
Bulwark,ARMORED,45,12,14,1
Raven,MEDIUM,30,15,7,3
*/

ConvoyLane lane = new ConvoyLane("Outer Rim Convoy", 5);
lane.loadShips("datas/ships.csv");
```

#### 2.3.2. `advanceTurn` 메서드를 구현한다

- `advanceTurn` 메서드는 항로의 시뮬레이션을 한 턴 진행할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 한 턴에는 레이저 발사 단계와 수리 단계가 포함된다.

- 레이저 발사 단계가 모두 끝난 뒤 내구도(`hull`)가 0인 우주선은 즉시 항로에서 제거되며, 해당 턴의 수리 단계에 참여하지 않는다.

- 우주선이 제거되더라도 나머지 우주선들의 상대적인 순서는 유지된다.

- 이 메서드는 반환값을 가지지 않는다.

#### 2.3.3. `predictIncomingDamageOrNull` 메서드를 구현한다

- `predictIncomingDamageOrNull` 메서드는 다음 턴의 레이저 발사 단계에서 각 우주선이 입게 될 예상 최종 피해치를 미리 계산할 때 사용한다.

- 이 메서드는 인자를 받지 않는다.

- 항로에 존재하는 우주선이 0대인 경우 `null`을 반환한다.

- 항로에 존재하는 우주선이 1대 이상인 경우 항로에 존재하는 우주선 수와 같은 길이의 `int[]`를 반환한다.
    - 반환 배열은 항로에 로드된 우주선들의 순서와 동일한 순서를 가지며 각 원소는 해당 우주선이 다음 턴에 입게 될 예상 최종 피해치를 의미한다.

- 이 메서드는 실제로 레이저를 발사하거나 우주선의 내구도(`hull`)를 변화시키지 않는다.

```java
/*
datas/ships.csv

Falcon,MEDIUM,32,14,8,3
Vanguard,HEAVY,38,16,10,2
Swift,LIGHT,26,18,5,2
Bulwark,ARMORED,45,12,14,1
Raven,MEDIUM,30,15,7,3
*/

ConvoyLane lane = new ConvoyLane("Outer Rim Convoy", 5);
lane.loadShips("datas/ships.csv");

int[] predicted = lane.predictIncomingDamageOrNull(); // [8, 6, 8, 1, 7]
```