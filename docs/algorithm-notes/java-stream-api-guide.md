# Java Stream API 기초 완벽 가이드

> 코딩테스트를 위한 Stream API 핵심 개념과 실전 활용법

## 🎯 목차
1. [Stream API란?](#stream-api란)
2. [기본 개념과 구조](#기본-개념과-구조)
3. [스트림 생성 방법](#스트림-생성-방법)
4. [중간 연산 (Intermediate Operations)](#중간-연산)
5. [최종 연산 (Terminal Operations)](#최종-연산)
6. [실전 패턴과 예제](#실전-패턴과-예제)
7. [성능 고려사항](#성능-고려사항)
8. [코딩테스트 활용법](#코딩테스트-활용법)

---

## Stream API란?

### 정의
Java 8에서 도입된 **함수형 프로그래밍** 스타일의 데이터 처리 API

### 핵심 특징
1. **선언적 프로그래밍**: "어떻게" 보다 "무엇을" 할지 명시
2. **함수형 스타일**: 람다식과 메서드 참조 활용
3. **지연 연산**: 최종 연산이 호출될 때까지 실행 지연
4. **파이프라인**: 여러 연산을 연결하여 처리

### 전통적 방식 vs Stream 방식

#### 전통적 방식 (명령형)
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");
List<String> result = new ArrayList<>();

// 길이가 5 이상인 단어를 대문자로 변환
for (String word : words) {
    if (word.length() >= 5) {          // 필터링
        result.add(word.toUpperCase()); // 변환
    }
}
Collections.sort(result); // 정렬
```

#### Stream 방식 (함수형)
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

List<String> result = words.stream()
    .filter(word -> word.length() >= 5)    // 필터링
    .map(String::toUpperCase)              // 변환
    .sorted()                              // 정렬
    .collect(Collectors.toList());         // 수집
```

---

## 기본 개념과 구조

### Stream 파이프라인 구조

```java
데이터소스.stream()           // 1. 스트림 생성
  .중간연산1()                // 2. 중간 연산들
  .중간연산2()                //    (지연 실행)
  .중간연산3()
  .최종연산();                // 3. 최종 연산 (즉시 실행)
```

### 3단계 구성

#### 1단계: 스트림 생성
```java
// 컬렉션에서
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
Stream<Integer> stream = list.stream();

// 배열에서
int[] arr = {1, 2, 3, 4, 5};
IntStream stream = Arrays.stream(arr);

// 직접 생성
Stream<String> stream = Stream.of("a", "b", "c");
```

#### 2단계: 중간 연산 (Intermediate Operations)
- **특징**: 지연 실행, 스트림 반환
- **종류**: filter, map, sorted, distinct, limit 등

#### 3단계: 최종 연산 (Terminal Operations)
- **특징**: 즉시 실행, 결과 반환
- **종류**: collect, forEach, reduce, count 등

---

## 스트림 생성 방법

### 1. 컬렉션에서 생성

#### List/Set에서
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Stream<Integer> stream = numbers.stream();

Set<String> words = Set.of("apple", "banana");
Stream<String> stream = words.stream();
```

#### 병렬 스트림
```java
Stream<Integer> parallelStream = numbers.parallelStream();
// 또는
Stream<Integer> parallelStream = numbers.stream().parallel();
```

### 2. 배열에서 생성

#### Object 배열
```java
String[] words = {"apple", "banana", "cherry"};
Stream<String> stream = Arrays.stream(words);
```

#### Primitive 배열
```java
int[] numbers = {1, 2, 3, 4, 5};
IntStream stream = Arrays.stream(numbers);

// 범위 지정
IntStream stream = Arrays.stream(numbers, 1, 4); // 인덱스 1~3
```

### 3. 직접 생성

#### 값들로 직접 생성
```java
Stream<String> stream = Stream.of("a", "b", "c");
Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
```

#### 빈 스트림
```java
Stream<String> emptyStream = Stream.empty();
```

#### 무한 스트림
```java
// 1부터 무한대
IntStream infiniteStream = IntStream.iterate(1, n -> n + 1);

// 랜덤 수 생성
Stream<Double> randomStream = Stream.generate(Math::random);
```

### 4. 범위 생성 (숫자)

```java
// 1부터 10까지 (10 포함)
IntStream range = IntStream.rangeClosed(1, 10);

// 1부터 10 미만 (10 제외)  
IntStream range = IntStream.range(1, 10);

// long 타입
LongStream range = LongStream.rangeClosed(1L, 100L);
```

---

## 중간 연산

### 1. filter() - 조건 필터링

#### 기본 사용법
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 짝수만 필터링
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
// 결과: [2, 4, 6, 8, 10]
```

#### 복잡한 조건
```java
List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

// 길이가 5 이상이고 'a'로 시작하는 단어
List<String> filtered = words.stream()
    .filter(word -> word.length() >= 5)
    .filter(word -> word.startsWith("a"))
    .collect(Collectors.toList());
// 결과: ["apple"]

// 또는 하나의 filter로
List<String> filtered = words.stream()
    .filter(word -> word.length() >= 5 && word.startsWith("a"))
    .collect(Collectors.toList());
```

### 2. map() - 요소 변환

#### 기본 변환
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

// 대문자로 변환
List<String> upperCase = words.stream()
    .map(String::toUpperCase)  // 메서드 참조
    .collect(Collectors.toList());
// 결과: ["APPLE", "BANANA", "CHERRY"]

// 길이 구하기
List<Integer> lengths = words.stream()
    .map(String::length)
    .collect(Collectors.toList());
// 결과: [5, 6, 6]
```

#### 타입 변환
```java
List<String> numbers = Arrays.asList("1", "2", "3", "4", "5");

// String → Integer 변환
List<Integer> integers = numbers.stream()
    .map(Integer::parseInt)
    .collect(Collectors.toList());
// 결과: [1, 2, 3, 4, 5]
```

#### 객체 변환
```java
class Person {
    String name;
    int age;
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    String getName() { return name; }
    int getAge() { return age; }
}

List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 30),
    new Person("Charlie", 35)
);

// 이름만 추출
List<String> names = people.stream()
    .map(Person::getName)
    .collect(Collectors.toList());
// 결과: ["Alice", "Bob", "Charlie"]
```

### 3. flatMap() - 평면화

#### 중첩된 컬렉션 평면화
```java
List<List<String>> nestedList = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d"),
    Arrays.asList("e", "f")
);

// 평면화
List<String> flattened = nestedList.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
// 결과: ["a", "b", "c", "d", "e", "f"]
```

#### 문자열을 문자로 분해
```java
List<String> words = Arrays.asList("hello", "world");

// 각 문자로 분해
List<String> chars = words.stream()
    .flatMap(word -> Arrays.stream(word.split("")))
    .collect(Collectors.toList());
// 결과: ["h", "e", "l", "l", "o", "w", "o", "r", "l", "d"]
```

### 4. sorted() - 정렬

#### 자연 정렬
```java
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9);

List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());
// 결과: [1, 1, 3, 4, 5, 9]
```

#### 커스텀 정렬
```java
List<String> words = Arrays.asList("apple", "pie", "banana");

// 길이순 정렬
List<String> sortedByLength = words.stream()
    .sorted(Comparator.comparing(String::length))
    .collect(Collectors.toList());
// 결과: ["pie", "apple", "banana"]

// 길이 내림차순
List<String> sortedDesc = words.stream()
    .sorted(Comparator.comparing(String::length).reversed())
    .collect(Collectors.toList());
// 결과: ["banana", "apple", "pie"]
```

### 5. distinct() - 중복 제거

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5);

List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
// 결과: [1, 2, 3, 4, 5]
```

### 6. limit() / skip() - 범위 제한

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// 처음 5개만
List<Integer> first5 = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());
// 결과: [1, 2, 3, 4, 5]

// 처음 3개 건너뛰고 나머지
List<Integer> after3 = numbers.stream()
    .skip(3)
    .collect(Collectors.toList());
// 결과: [4, 5, 6, 7, 8, 9, 10]

// 3개 건너뛰고 5개만
List<Integer> middle = numbers.stream()
    .skip(3)
    .limit(5)
    .collect(Collectors.toList());
// 결과: [4, 5, 6, 7, 8]
```

---

## 최종 연산

### 1. collect() - 수집

#### List로 수집
```java
List<String> result = stream
    .collect(Collectors.toList());
```

#### Set으로 수집 (중복 자동 제거)
```java
Set<String> result = stream
    .collect(Collectors.toSet());
```

#### 배열로 수집
```java
String[] result = stream
    .toArray(String[]::new);
```

#### 문자열 연결
```java
String result = words.stream()
    .collect(Collectors.joining());           // "applebananacherry"

String result = words.stream()
    .collect(Collectors.joining(", "));       // "apple, banana, cherry"

String result = words.stream()
    .collect(Collectors.joining(", ", "[", "]")); // "[apple, banana, cherry]"
```

### 2. forEach() - 각 요소에 작업

```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

// 각 단어 출력
words.stream()
    .forEach(System.out::println);

// 인덱스와 함께 출력하려면 전통적 방식 사용
IntStream.range(0, words.size())
    .forEach(i -> System.out.println(i + ": " + words.get(i)));
```

### 3. reduce() - 누적 연산

#### 기본 reduce
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// 합계
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);  // 15

// 또는 메서드 참조
int sum = numbers.stream()
    .reduce(0, Integer::sum);     // 15

// 최댓값
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);
```

#### 문자열 연결
```java
List<String> words = Arrays.asList("Java", " ", "Stream", " ", "API");

String result = words.stream()
    .reduce("", (a, b) -> a + b);  // "Java Stream API"

// 또는
String result = words.stream()
    .reduce("", String::concat);
```

### 4. 검색 연산

#### anyMatch(), allMatch(), noneMatch()
```java
List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);

boolean hasEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0);    // true (하나라도 짝수?)

boolean allEven = numbers.stream()
    .allMatch(n -> n % 2 == 0);    // true (모두 짝수?)

boolean noOdd = numbers.stream()
    .noneMatch(n -> n % 2 == 1);   // true (홀수가 없는가?)
```

#### findFirst(), findAny()
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

Optional<String> first = words.stream()
    .filter(word -> word.startsWith("b"))
    .findFirst();  // Optional["banana"]

Optional<String> any = words.stream()
    .filter(word -> word.length() > 5)
    .findAny();    // Optional["banana"] (병렬에서는 다를 수 있음)
```

### 5. 집계 연산

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

long count = numbers.stream()
    .count();                      // 5

OptionalInt max = numbers.stream()
    .mapToInt(Integer::intValue)
    .max();                        // OptionalInt[5]

OptionalDouble average = numbers.stream()
    .mapToInt(Integer::intValue)
    .average();                    // OptionalDouble[3.0]

int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();                        // 15
```

---

## 실전 패턴과 예제

### 패턴 1: 데이터 필터링과 변환

#### 문제: 점수가 80 이상인 학생의 이름을 대문자로 변환
```java
class Student {
    String name;
    int score;
    Student(String name, int score) { this.name = name; this.score = score; }
    String getName() { return name; }
    int getScore() { return score; }
}

List<Student> students = Arrays.asList(
    new Student("alice", 85),
    new Student("bob", 75),
    new Student("charlie", 90),
    new Student("david", 70)
);

// Stream 방식
List<String> result = students.stream()
    .filter(student -> student.getScore() >= 80)
    .map(Student::getName)
    .map(String::toUpperCase)
    .collect(Collectors.toList());
// 결과: ["ALICE", "CHARLIE"]
```

### 패턴 2: 그룹핑과 집계

#### 문제: 성별로 학생을 그룹핑하고 평균 점수 계산
```java
class Student {
    String name, gender;
    int score;
    // 생성자, getter 생략
}

Map<String, Double> averageByGender = students.stream()
    .collect(Collectors.groupingBy(
        Student::getGender,
        Collectors.averagingInt(Student::getScore)
    ));
```

### 패턴 3: 문자열 조작

#### 문제: 단어들을 길이순으로 정렬하고 중복 제거
```java
List<String> words = Arrays.asList("apple", "banana", "apple", "cherry", "date");

List<String> result = words.stream()
    .distinct()                                    // 중복 제거
    .sorted(Comparator.comparing(String::length)   // 길이순 정렬
           .thenComparing(String::compareTo))      // 같은 길이면 사전순
    .collect(Collectors.toList());
// 결과: ["date", "apple", "banana", "cherry"]
```

### 패턴 4: 숫자 배열 처리

#### 문제: 배열에서 짝수만 찾아 제곱한 후 합계
```java
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

int result = Arrays.stream(numbers)
    .filter(n -> n % 2 == 0)    // 짝수 필터링
    .map(n -> n * n)            // 제곱
    .sum();                     // 합계
// 결과: 220 (4 + 16 + 36 + 64 + 100)
```

### 패턴 5: Optional 처리

#### 문제: 조건을 만족하는 첫 번째 요소 찾기
```java
List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 8, 9);

// 첫 번째 짝수 찾기
Optional<Integer> firstEven = numbers.stream()
    .filter(n -> n % 2 == 0)
    .findFirst();

// 안전한 처리
if (firstEven.isPresent()) {
    System.out.println("첫 번째 짝수: " + firstEven.get());
} else {
    System.out.println("짝수가 없습니다.");
}

// 또는 람다식으로
firstEven.ifPresent(n -> System.out.println("첫 번째 짝수: " + n));
firstEven.orElse(-1);  // 없으면 -1 반환
```

---

## 성능 고려사항

### 언제 Stream을 사용할까?

#### Stream 사용 권장 상황
```java
// ✅ 복잡한 데이터 처리 파이프라인
List<String> result = words.stream()
    .filter(word -> word.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .distinct()
    .collect(Collectors.toList());

// ✅ 함수형 스타일이 더 읽기 쉬운 경우
boolean hasLongWord = words.stream()
    .anyMatch(word -> word.length() > 10);

// ✅ 병렬 처리가 필요한 경우 (대용량 데이터)
long count = largeList.parallelStream()
    .filter(complexPredicate)
    .count();
```

#### 전통적 방식 권장 상황
```java
// ✅ 간단한 반복문
for (String word : words) {
    System.out.println(word);  // forEach보다 빠름
}

// ✅ 조기 종료가 필요한 경우
for (String word : words) {
    if (word.startsWith("target")) {
        return word;  // 즉시 반환
    }
}

// ✅ 인덱스가 필요한 경우
for (int i = 0; i < words.size(); i++) {
    System.out.println(i + ": " + words.get(i));
}
```

### 성능 최적화 팁

#### 1. 적절한 Primitive Stream 사용
```java
// ❌ 박싱/언박싱 오버헤드
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .mapToInt(Integer::intValue)  // 언박싱
    .sum();

// ✅ 처음부터 IntStream 사용
int[] numbers = {1, 2, 3, 4, 5};
int sum = Arrays.stream(numbers)
    .sum();
```

#### 2. 조기 필터링
```java
// ✅ 비싼 연산 전에 필터링
list.stream()
    .filter(cheapPredicate)      // 빠른 조건 먼저
    .filter(expensivePredicate)  // 비싼 조건 나중에
    .map(expensiveTransform)     // 필터링 후 변환
    .collect(Collectors.toList());
```

#### 3. 병렬 처리 주의사항
```java
// ✅ 대용량 데이터 + 독립적 연산
largeList.parallelStream()
    .filter(item -> isValid(item))    // CPU 집약적
    .map(item -> transform(item))     // 상태 무관
    .collect(Collectors.toList());

// ❌ 순서 의존적이거나 소용량 데이터
smallList.parallelStream()  // 오히려 느릴 수 있음
    .sorted()               // 순서 의존적
    .collect(Collectors.toList());
```

---

## 코딩테스트 활용법

### 자주 사용하는 Stream 패턴

#### 1. 배열/리스트 → 다른 타입 변환
```java
// String[] → List<Integer>
String[] strArr = {"1", "2", "3", "4", "5"};
List<Integer> intList = Arrays.stream(strArr)
    .map(Integer::parseInt)
    .collect(Collectors.toList());

// int[] → List<Integer>
int[] intArr = {1, 2, 3, 4, 5};
List<Integer> list = Arrays.stream(intArr)
    .boxed()
    .collect(Collectors.toList());
```

#### 2. 조건부 카운팅
```java
// 조건을 만족하는 개수
long count = numbers.stream()
    .filter(n -> n > 0 && n % 2 == 0)
    .count();

// 특정 문자가 포함된 단어 개수
long count = words.stream()
    .filter(word -> word.contains("a"))
    .count();
```

#### 3. 최대/최소/합계
```java
// 최대값
OptionalInt max = Arrays.stream(numbers)
    .max();

// 조건부 최대값
OptionalInt max = Arrays.stream(numbers)
    .filter(n -> n > 0)
    .max();

// 문자열 길이의 합
int totalLength = words.stream()
    .mapToInt(String::length)
    .sum();
```

#### 4. 정렬 후 수집
```java
// 다중 조건 정렬
List<Student> sorted = students.stream()
    .sorted(Comparator.comparing(Student::getScore).reversed()
           .thenComparing(Student::getName))
    .collect(Collectors.toList());

// 커스텀 정렬
List<String> sorted = words.stream()
    .sorted((a, b) -> (b + a).compareTo(a + b))  // 가장 큰 수 문제
    .collect(Collectors.toList());
```

#### 5. 중복 제거와 정렬
```java
// HashSet 대신 Stream 활용
List<String> unique = words.stream()
    .distinct()
    .sorted()
    .collect(Collectors.toList());
```

### 실전 문제 적용 예시

#### 프로그래머스 - 오픈채팅방 (Stream 버전)
```java
public String[] solution(String[] record) {
    // 최종 닉네임 수집
    Map<String, String> userMap = Arrays.stream(record)
        .map(rec -> rec.split(" "))
        .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Change"))
        .collect(Collectors.toMap(
            parts -> parts[1],          // key: userId
            parts -> parts[2],          // value: nickname
            (old, new) -> new           // 중복 시 새 값 사용
        ));
    
    // 결과 생성
    return Arrays.stream(record)
        .map(rec -> rec.split(" "))
        .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Leave"))
        .map(parts -> {
            String action = parts[0];
            String userId = parts[1];
            String nickname = userMap.get(userId);
            
            return action.equals("Enter") 
                ? nickname + "님이 들어왔습니다."
                : nickname + "님이 나갔습니다.";
        })
        .toArray(String[]::new);
}
```

---

## 💡 학습 팁

### 1. 단계별 학습 순서
1. **기본 생성**: `stream()`, `Arrays.stream()`
2. **필터링**: `filter()`
3. **변환**: `map()`
4. **수집**: `collect(Collectors.toList())`
5. **정렬**: `sorted()`
6. **고급 연산**: `reduce()`, `flatMap()`

### 2. 디버깅 팁
```java
// peek()을 이용한 중간 확인
List<String> result = words.stream()
    .filter(word -> word.length() > 3)
    .peek(System.out::println)        // 중간 결과 확인
    .map(String::toUpperCase)
    .peek(System.out::println)        // 변환 후 확인
    .collect(Collectors.toList());
```

### 3. 실수하기 쉬운 포인트
```java
// ❌ 스트림 재사용 불가
Stream<String> stream = words.stream();
stream.filter(word -> word.length() > 3).count();  // OK
stream.map(String::toUpperCase).count();            // ERROR!

// ❌ null 요소 처리 주의
List<String> wordsWithNull = Arrays.asList("apple", null, "banana");
wordsWithNull.stream()
    .filter(Objects::nonNull)  // null 제거 필수
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

---

## 🔗 관련 자료

### 추천 학습 순서
1. **기본 패턴**: filter + map + collect
2. **정렬**: sorted + Comparator
3. **집계**: reduce, count, sum
4. **그룹핑**: Collectors.groupingBy
5. **병렬**: parallelStream()

### 유용한 메서드 참조 패턴
```java
String::length          // str -> str.length()
String::toUpperCase     // str -> str.toUpperCase()
Integer::parseInt       // str -> Integer.parseInt(str)
System.out::println     // str -> System.out.println(str)
Math::max              // (a, b) -> Math.max(a, b)
```

**Stream API는 함수형 프로그래밍의 핵심입니다. 처음엔 어렵지만 익숙해지면 코드가 훨씬 깔끔해집니다!** ✨