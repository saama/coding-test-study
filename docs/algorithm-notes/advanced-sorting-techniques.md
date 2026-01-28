# 고급 정렬 기법 완벽 가이드

> 코딩테스트에서 자주 출현하는 커스텀 정렬과 다중 조건 정렬의 모든 것

## 🎯 목차
1. [Comparator 심화 이해](#comparator-심화-이해)
2. [다중 조건 정렬 패턴](#다중-조건-정렬-패턴)
3. [문자열 조합 비교](#문자열-조합-비교)
4. [배열 타입별 정렬 방법](#배열-타입별-정렬-방법)
5. [중복 제거와 정렬](#중복-제거와-정렬)
6. [성능 최적화 기법](#성능-최적화-기법)

---

## Comparator 심화 이해

### Comparator.compare() 반환값의 정확한 의미

| 반환값 | 수학적 의미 | 정렬 결과 | 실제 동작 |
|--------|-------------|-----------|-----------|
| **양수 (> 0)** | a > b | a를 뒤로 배치 | a는 b보다 나중에 위치 |
| **0** | a == b | 순서 유지 | 상대적 위치 변경 없음 |
| **음수 (< 0)** | a < b | a를 앞으로 배치 | a는 b보다 먼저 위치 |

### 실제 동작 예시
```java
// 예시: [3, 1, 4, 1, 5] 오름차순 정렬
Arrays.sort(arr, (a, b) -> a - b);

// 비교 과정:
// compare(3, 1): 3 - 1 = 2 (양수) → 3이 뒤로 → [1, 3, ...]
// compare(4, 1): 4 - 1 = 3 (양수) → 4가 뒤로 → [1, 3, 4, ...]
// 최종 결과: [1, 1, 3, 4, 5]
```

### Comparator 작성 방법 3가지

#### 1. 람다식 (Java 8+)
```java
// 기본 오름차순
Arrays.sort(arr, (a, b) -> a - b);

// 내림차순  
Arrays.sort(arr, (a, b) -> b - a);

// 다중 조건
Arrays.sort(students, (a, b) -> {
    if (a.score != b.score) return b.score - a.score; // 점수 내림차순
    return a.name.compareTo(b.name);                   // 이름 오름차순
});
```

#### 2. Comparator.comparing 체이닝 (권장)
```java
// 단일 조건
Arrays.sort(students, Comparator.comparing(Student::getScore));

// 다중 조건 체이닝
Arrays.sort(students, Comparator
    .comparing(Student::getScore, Comparator.reverseOrder()) // 점수 내림차순
    .thenComparing(Student::getName));                       // 이름 오름차순

// 문자열 길이 → 사전순
Arrays.sort(words, Comparator
    .comparing(String::length)      // 길이 우선
    .thenComparing(String::compareTo)); // 사전순 보조
```

#### 3. 전통적 방식 (Java 8 이전)
```java
Arrays.sort(students, new Comparator<Student>() {
    @Override
    public int compare(Student a, Student b) {
        if (a.score != b.score) {
            return Integer.compare(b.score, a.score); // 점수 내림차순
        }
        return a.name.compareTo(b.name);              // 이름 오름차순
    }
});
```

### 안전한 비교 방법

#### 오버플로우 방지
```java
// ❌ 위험: int 오버플로우 가능
(a, b) -> a - b

// ✅ 안전: Integer.compare 사용
(a, b) -> Integer.compare(a, b)

// ✅ 안전: 조건문 사용
(a, b) -> {
    if (a < b) return -1;
    if (a > b) return 1;
    return 0;
}
```

---

## 다중 조건 정렬 패턴

### 패턴 1: if-else 분기
```java
// 2차원 배열 정렬: x좌표 → y좌표
Arrays.sort(points, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0]; // x좌표 우선
    return a[1] - b[1];                   // y좌표 보조
});
```

### 패턴 2: Comparator 체이닝 (권장)
```java
// 같은 결과, 더 읽기 쉬움
Arrays.sort(points, Comparator
    .comparing((int[] p) -> p[0])  // x좌표
    .thenComparing(p -> p[1]));    // y좌표
```

### 복잡한 다중 조건 예시

#### 학생 성적 정렬
```java
class Student {
    String name;
    int korean, english, math;
    
    // 정렬 기준:
    // 1. 국어 점수 내림차순
    // 2. 영어 점수 오름차순  
    // 3. 수학 점수 내림차순
    // 4. 이름 오름차순
}

// 해법 1: 람다식
Arrays.sort(students, (a, b) -> {
    if (a.korean != b.korean) return b.korean - a.korean;
    if (a.english != b.english) return a.english - b.english;
    if (a.math != b.math) return b.math - a.math;
    return a.name.compareTo(b.name);
});

// 해법 2: 체이닝 (더 깔끔)
Arrays.sort(students, Comparator
    .comparing((Student s) -> s.korean, Comparator.reverseOrder())
    .thenComparing(s -> s.english)
    .thenComparing(s -> s.math, Comparator.reverseOrder())
    .thenComparing(s -> s.name));
```

---

## 문자열 조합 비교

### 핵심 아이디어
일반적인 사전순 정렬로는 최적의 결과를 얻을 수 없는 경우, **조합을 직접 비교**

### 가장 큰 수 만들기 패턴
```java
// 문제: [3, 30, 34, 5, 9] → 가장 큰 수?
// 답: "9534330"

// 핵심 알고리즘
Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));

// 비교 과정:
// "3" vs "30": "330" vs "303" → "330" > "303" → "3"이 앞
// "34" vs "5": "345" vs "534" → "534" > "345" → "5"가 앞
```

### 다른 조합 비교 예시

#### URL 경로 최적화
```java
// 경로 조각들을 연결했을 때 가장 짧은 URL 만들기
String[] paths = {"abc", "ab", "a"};

// 연결 길이가 짧은 조합 우선
Arrays.sort(paths, (a, b) -> {
    String ab = a + b;
    String ba = b + a;
    return Integer.compare(ab.length(), ba.length());
});
```

#### 문자열 사전 생성
```java
// 문자열들을 연결했을 때 사전순으로 가장 앞선 결과
Arrays.sort(words, (a, b) -> (a + b).compareTo(b + a));
```

---

## 배열 타입별 정렬 방법

### Primitive 배열 vs Object 배열

#### int[] 배열 (Comparator 사용 불가)
```java
int[] arr = {3, 1, 4, 1, 5};

// ✅ 오름차순만 가능
Arrays.sort(arr);

// ❌ 커스텀 정렬 불가
// Arrays.sort(arr, comparator); // 컴파일 에러!

// 해결책 1: Integer[] 변환
Integer[] boxed = Arrays.stream(arr).boxed().toArray(Integer[]::new);
Arrays.sort(boxed, Comparator.reverseOrder());

// 해결책 2: 직접 뒤집기
Arrays.sort(arr);
for (int i = 0; i < arr.length / 2; i++) {
    int temp = arr[i];
    arr[i] = arr[arr.length - 1 - i];
    arr[arr.length - 1 - i] = temp;
}
```

#### Object 배열 (모든 방법 사용 가능)
```java
String[] words = {"apple", "banana", "cherry"};

// 모든 Comparator 방법 사용 가능
Arrays.sort(words);                                    // 기본 정렬
Arrays.sort(words, Comparator.reverseOrder());         // 내림차순
Arrays.sort(words, Comparator.comparing(String::length)); // 길이순
```

### 2차원 배열 정렬

#### 점 좌표 정렬
```java
int[][] points = {{1, 3}, {2, 1}, {1, 2}};

// x좌표 → y좌표 순
Arrays.sort(points, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0];
    return a[1] - b[1];
});

// 또는 체이닝
Arrays.sort(points, Comparator
    .comparing((int[] p) -> p[0])
    .thenComparing(p -> p[1]));
```

#### 거리 기준 정렬
```java
// 원점에서 가까운 순
Arrays.sort(points, (a, b) -> {
    int distA = a[0] * a[0] + a[1] * a[1];
    int distB = b[0] * b[0] + b[1] * b[1];
    return Integer.compare(distA, distB);
});
```

---

## 중복 제거와 정렬

### 방법 1: HashSet 활용
```java
// 장점: 입력 시점에 중복 제거, 효율적
// 단점: 추가 변환 과정 필요

Set<String> wordSet = new HashSet<>();
// 입력...

// HashSet → 배열 변환 후 정렬
String[] words = wordSet.toArray(new String[0]);
Arrays.sort(words, comparator);
```

### 방법 2: TreeSet 활용 (권장)
```java
// 장점: 삽입과 동시에 정렬, 가장 효율적
// 단점: 삽입 시 약간의 오버헤드

Set<String> wordSet = new TreeSet<>(comparator);
// 입력과 동시에 자동 정렬됨

for (String word : wordSet) {
    System.out.println(word); // 이미 정렬된 상태
}
```

### 방법 3: 정렬 후 중복 제거
```java
// 장점: 별도 자료구조 불필요
// 단점: 중복 체크 로직 필요

Arrays.sort(words, comparator);

// 첫 번째 원소는 항상 출력
System.out.println(words[0]);

// 나머지는 이전과 다를 때만 출력
for (int i = 1; i < words.length; i++) {
    if (!words[i].equals(words[i-1])) {
        System.out.println(words[i]);
    }
}
```

### toArray(new String[0]) 패턴 설명

#### 기본 개념
```java
Set<String> set = new HashSet<>();
String[] array = set.toArray(new String[0]);
//                           ↑
//                   타입 힌트 + JVM 최적화
```

#### 동작 원리
```java
// JVM 내부 동작
if (전달받은배열크기 >= Set크기) {
    // 전달받은 배열에 복사
    return 전달받은배열;
} else {
    // 적절한 크기의 새 배열 생성
    return new String[Set크기];
}
```

#### 왜 크기를 0으로?
```java
// 과거 방식 (비권장)
set.toArray(new String[set.size()]); // 크기 맞추기

// 현재 방식 (권장)
set.toArray(new String[0]);          // JVM이 최적화
```

**이유**:
1. **JVM 최적화**: 크기 0 배열을 캐시해서 재사용
2. **성능 향상**: 실제 벤치마크에서 더 빠름
3. **코드 간소화**: 크기 계산 불필요

---

## 성능 최적화 기법

### 1. Comparator 재사용
```java
// ❌ 매번 새로운 Comparator 생성
Arrays.sort(arr1, (a, b) -> a.length() - b.length());
Arrays.sort(arr2, (a, b) -> a.length() - b.length());

// ✅ Comparator 재사용
Comparator<String> lengthComparator = Comparator.comparing(String::length);
Arrays.sort(arr1, lengthComparator);
Arrays.sort(arr2, lengthComparator);
```

### 2. 적절한 정렬 알고리즘 선택
```java
// 거의 정렬된 데이터: TimSort (Arrays.sort)가 최적
Arrays.sort(arr);

// 완전 랜덤 데이터: QuickSort도 좋음
// 하지만 Java의 TimSort가 모든 상황에서 안정적
```

### 3. 메모리 효율적인 정렬
```java
// ❌ 불필요한 복사 생성
List<String> copy = new ArrayList<>(original);
Collections.sort(copy);

// ✅ 직접 정렬
Collections.sort(original); // 원본 수정이 가능한 경우
```

### 4. 대용량 데이터 처리
```java
// 스트림 병렬 처리 (대용량 데이터)
list.parallelStream()
    .sorted(comparator)
    .collect(Collectors.toList());

// 하지만 소용량에서는 오히려 느릴 수 있음
```

---

## 💡 실전 팁

### 코딩테스트에서 자주 나오는 패턴들

#### 1. 구간 정렬
```java
// 회의실 배정: 끝나는 시간순
Arrays.sort(meetings, (a, b) -> a[1] - b[1]);
```

#### 2. 우선순위 큐와 함께
```java
// 최소 힙을 최대 힙으로
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
```

#### 3. 이분 탐색 전 정렬
```java
Arrays.sort(arr);
int index = Arrays.binarySearch(arr, target);
```

#### 4. 안정 정렬이 필요한 경우
```java
// Arrays.sort()는 안정 정렬 (TimSort)
// 같은 값들의 상대적 순서 보장
Arrays.sort(students, Comparator.comparing(Student::getScore));
```

---

## 🔗 관련 문제 유형

1. **다중 조건 정렬**: 백준 1181(단어 정렬), 10814(나이순 정렬)
2. **문자열 조합**: 프로그래머스 가장 큰 수
3. **좌표 정렬**: 백준 11650, 11651
4. **구간 정렬**: 백준 1931(회의실 배정)
5. **우선순위**: 백준 11279, 1927(힙)

**고급 정렬은 알고리즘 문제의 기본기이자 핵심입니다!** 🚀