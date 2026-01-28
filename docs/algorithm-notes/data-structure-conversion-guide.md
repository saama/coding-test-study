# 자료구조 변환 완벽 가이드

> 코딩테스트에서 필수적인 자료구조 간 변환 패턴과 최적화 기법

## 🎯 목차
1. [배열과 Collection 변환](#배열과-collection-변환)
2. [Set과 다른 자료구조 간 변환](#set과-다른-자료구조-간-변환)
3. [Map 활용 변환 패턴](#map-활용-변환-패턴)
4. [Stream API를 활용한 변환](#stream-api를-활용한-변환)
5. [성능 비교와 선택 기준](#성능-비교와-선택-기준)
6. [실전 변환 패턴](#실전-변환-패턴)

---

## 배열과 Collection 변환

### 배열 → List
```java
// 방법 1: Arrays.asList() (수정 불가능한 List)
int[] arr = {1, 2, 3, 4, 5};
List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5); // 직접 입력
// 주의: primitive 배열은 직접 변환 안 됨

// 방법 2: Stream 활용 (권장)
List<Integer> list2 = Arrays.stream(arr)
    .boxed()
    .collect(Collectors.toList());

// 방법 3: 직접 변환
List<Integer> list3 = new ArrayList<>();
for (int value : arr) {
    list3.add(value);
}

// String 배열의 경우 (더 간단)
String[] strArr = {"a", "b", "c"};
List<String> strList = Arrays.asList(strArr);          // 수정 불가
List<String> mutableList = new ArrayList<>(Arrays.asList(strArr)); // 수정 가능
```

### List → 배열
```java
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

// 방법 1: toArray() with type hint (권장)
Integer[] arr1 = list.toArray(new Integer[0]);

// 방법 2: toArray() with exact size
Integer[] arr2 = list.toArray(new Integer[list.size()]);

// 방법 3: Stream으로 primitive 배열
int[] primitiveArr = list.stream()
    .mapToInt(Integer::intValue)
    .toArray();
```

### toArray() 패턴 상세 분석

#### 왜 new Type[0]을 사용하는가?
```java
// JVM 내부 최적화 (Java 6+)
// 1. 크기 0 배열을 캐시해서 재사용
// 2. 적절한 크기의 새 배열을 효율적으로 생성
// 3. 메모리 할당 최적화

// 성능 벤치마크 결과:
list.toArray(new String[0])        // 가장 빠름 ✅
list.toArray(new String[size])     // 약간 느림  
list.toArray(new String[size*2])   // 가장 느림
```

#### 실제 사용 패턴
```java
// ✅ 현재 권장 방식
String[] array = list.toArray(new String[0]);
Integer[] array = list.toArray(new Integer[0]);

// ❌ 과거 방식 (비권장)
String[] array = list.toArray(new String[list.size()]);
```

---

## Set과 다른 자료구조 간 변환

### Set의 종류별 특성
```java
// HashSet: 순서 없음, O(1) 접근
Set<String> hashSet = new HashSet<>();

// LinkedHashSet: 삽입 순서 유지, O(1) 접근  
Set<String> linkedSet = new LinkedHashSet<>();

// TreeSet: 정렬된 순서, O(log n) 접근
Set<String> treeSet = new TreeSet<>();
```

### Set → 배열 (Stream 없이)
```java
Set<String> set = Set.of("c", "a", "b");

// 방법 1: toArray() 활용 (가장 간단)
String[] array = set.toArray(new String[0]);

// 방법 2: 직접 변환
String[] array2 = new String[set.size()];
int i = 0;
for (String item : set) {
    array2[i++] = item;
}
```

### Set → List (Stream 없이)
```java
Set<String> set = Set.of("apple", "banana", "cherry");

// 방법 1: ArrayList 생성자 활용 (권장)
List<String> list = new ArrayList<>(set);

// 방법 2: 직접 추가
List<String> list2 = new ArrayList<>();
list2.addAll(set);

// 방법 3: 직접 반복
List<String> list3 = new ArrayList<>();
for (String item : set) {
    list3.add(item);
}
```

### HashSet → 정렬된 자료구조

#### HashSet → 정렬된 배열
```java
Set<String> hashSet = new HashSet<>(Arrays.asList("c", "a", "b"));

// 방법 1: toArray + Arrays.sort
String[] sortedArray = hashSet.toArray(new String[0]);
Arrays.sort(sortedArray);

// 방법 2: List 변환 후 정렬
List<String> list = new ArrayList<>(hashSet);
Collections.sort(list);
String[] sortedArray2 = list.toArray(new String[0]);
```

#### HashSet → TreeSet (자동 정렬)
```java
Set<String> hashSet = new HashSet<>(Arrays.asList("c", "a", "b"));

// 기본 정렬
Set<String> treeSet = new TreeSet<>(hashSet);

// 커스텀 정렬
Set<String> customTreeSet = new TreeSet<>(Comparator.comparing(String::length));
customTreeSet.addAll(hashSet);
```

### TreeSet의 강력함
```java
// 삽입과 동시에 정렬되는 TreeSet
Set<String> words = new TreeSet<>((a, b) -> {
    if (a.length() != b.length()) {
        return a.length() - b.length(); // 길이 우선
    }
    return a.compareTo(b);              // 사전순 보조
});

words.add("cat");
words.add("a");  
words.add("dog");
words.add("bird");

// 결과: [a, cat, dog, bird] (자동으로 정렬됨)
```

---

## Map 활용 변환 패턴

### 빈도수 계산 → 정렬
```java
String[] words = {"apple", "banana", "apple", "cherry", "banana", "banana"};

// 1. 빈도수 계산
Map<String, Integer> freqMap = new HashMap<>();
for (String word : words) {
    freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
}

// 2. 빈도순 정렬 (높은 순)
List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(freqMap.entrySet());
sortedEntries.sort((a, b) -> b.getValue() - a.getValue());

// 또는 Stream으로
List<String> sortedWords = freqMap.entrySet().stream()
    .sorted((a, b) -> b.getValue() - a.getValue())
    .map(Map.Entry::getKey)
    .collect(Collectors.toList());
```

### Map.Entry 활용 패턴
```java
// Entry를 활용한 키-값 쌍 정렬
Map<String, Integer> scores = Map.of(
    "Alice", 95,
    "Bob", 87, 
    "Charlie", 92
);

// 점수 내림차순 정렬
List<Map.Entry<String, Integer>> sortedScores = scores.entrySet().stream()
    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
    .collect(Collectors.toList());

// 결과 출력
for (Map.Entry<String, Integer> entry : sortedScores) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

### Map → 다른 자료구조
```java
Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);

// 키만 추출
Set<String> keys = map.keySet();
List<String> keyList = new ArrayList<>(map.keySet());
String[] keyArray = map.keySet().toArray(new String[0]);

// 값만 추출  
Collection<Integer> values = map.values();
List<Integer> valueList = new ArrayList<>(map.values());
Integer[] valueArray = map.values().toArray(new Integer[0]);
```

---

## Stream API를 활용한 변환

### 기본 변환 패턴
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

// List → Array
String[] array = words.stream().toArray(String[]::new);

// List → Set
Set<String> set = words.stream().collect(Collectors.toSet());

// 필터링 + 변환
List<String> longWords = words.stream()
    .filter(w -> w.length() > 5)
    .collect(Collectors.toList());
```

### 고급 변환 패턴
```java
// 2차원 배열 → List<List<Integer>>
int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
List<List<Integer>> listMatrix = Arrays.stream(matrix)
    .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
    .collect(Collectors.toList());

// 문자열 → 문자 List
String text = "hello";
List<Character> chars = text.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.toList());
```

### Collectors의 다양한 활용
```java
List<Student> students = Arrays.asList(
    new Student("Alice", 95),
    new Student("Bob", 87),
    new Student("Charlie", 92)
);

// Grouping
Map<Integer, List<Student>> byScore = students.stream()
    .collect(Collectors.groupingBy(s -> s.score / 10 * 10)); // 점수대별 그룹

// Partitioning
Map<Boolean, List<Student>> passFail = students.stream()
    .collect(Collectors.partitioningBy(s -> s.score >= 90)); // 90점 이상/미만

// 커스텀 Collector
String names = students.stream()
    .map(Student::getName)
    .collect(Collectors.joining(", ")); // "Alice, Bob, Charlie"
```

---

## 성능 비교와 선택 기준

### 변환 방법별 성능 비교

#### Set → Array
| 방법 | 성능 | 가독성 | 메모리 | 추천도 |
|------|------|--------|---------|---------|
| `toArray(new T[0])` | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ✅ 최고 |
| `Stream.toArray()` | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| 직접 반복 | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |

#### Set → List  
| 방법 | 성능 | 가독성 | 메모리 | 추천도 |
|------|------|--------|---------|---------|
| `new ArrayList<>(set)` | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ✅ 최고 |
| `Stream.collect()` | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| `addAll()` | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ |

### 선택 기준

#### 언제 Stream을 쓸까?
```java
// ✅ Stream 권장: 변환과 동시에 가공이 필요한 경우
List<String> result = set.stream()
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());

// ❌ Stream 비권장: 단순 변환만 필요한 경우
List<String> result = new ArrayList<>(set); // 더 빠르고 간단
```

#### 언제 TreeSet을 쓸까?
```java
// ✅ TreeSet 권장: 중복 제거 + 정렬이 모두 필요한 경우
Set<String> words = new TreeSet<>(comparator);
// 입력과 동시에 정렬됨

// ❌ TreeSet 비권장: 한 번만 정렬하는 경우
List<String> words = new ArrayList<>(hashSet);
Collections.sort(words); // 더 효율적
```

---

## 실전 변환 패턴

### 코딩테스트에서 자주 사용하는 패턴들

#### 1. 입력 처리 패턴
```java
// 한 줄에 여러 정수 입력
String[] tokens = br.readLine().split(" ");
List<Integer> numbers = Arrays.stream(tokens)
    .map(Integer::parseInt)
    .collect(Collectors.toList());

// 또는 배열로
int[] arr = Arrays.stream(tokens)
    .mapToInt(Integer::parseInt)
    .toArray();
```

#### 2. 중복 제거 + 정렬 패턴
```java
// 입력 받으면서 중복 제거 + 정렬
Set<Integer> uniqueNumbers = new TreeSet<>();
for (int i = 0; i < n; i++) {
    uniqueNumbers.add(Integer.parseInt(br.readLine()));
}
// 자동으로 중복 제거 + 정렬 완료

// 결과 출력
for (int num : uniqueNumbers) {
    System.out.println(num);
}
```

#### 3. 빈도수 기반 정렬 패턴
```java
// 단계 1: 빈도수 계산
Map<String, Integer> freq = new HashMap<>();
for (String word : words) {
    freq.put(word, freq.getOrDefault(word, 0) + 1);
}

// 단계 2: 빈도수 기반 정렬
List<String> sortedWords = new ArrayList<>(freq.keySet());
sortedWords.sort((a, b) -> {
    int freqCompare = freq.get(b) - freq.get(a); // 빈도수 내림차순
    if (freqCompare != 0) return freqCompare;
    return a.compareTo(b); // 사전순 오름차순
});
```

#### 4. 좌표/구간 정렬 패턴
```java
// 2차원 배열을 List<int[]>로 변환 후 정렬
List<int[]> points = new ArrayList<>();
for (int i = 0; i < n; i++) {
    String[] tokens = br.readLine().split(" ");
    int x = Integer.parseInt(tokens[0]);
    int y = Integer.parseInt(tokens[1]);
    points.add(new int[]{x, y});
}

// x좌표 → y좌표 순 정렬
points.sort((a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0];
    return a[1] - b[1];
});
```

### 백준 vs 프로그래머스 차이점

#### 백준 스타일 (입출력 중심)
```java
// 입력 처리
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
Set<String> words = new TreeSet<>(comparator); // 자동 정렬

for (int i = 0; i < n; i++) {
    words.add(br.readLine());
}

// 출력 (개별적으로)
for (String word : words) {
    System.out.println(word);
}
```

#### 프로그래머스 스타일 (함수 중심)
```java
public String[] solution(String[] strings, int n) {
    // 배열 복사로 원본 보호
    String[] result = strings.clone();
    
    // 정렬
    Arrays.sort(result, comparator);
    
    return result; // 배열 반환
}
```

---

## 💡 실전 팁

### 자료구조 선택 체크리스트

#### Set 계열
- **HashSet**: 중복 제거만 필요 → O(1) 접근
- **LinkedHashSet**: 중복 제거 + 삽입 순서 유지
- **TreeSet**: 중복 제거 + 자동 정렬 → O(log n) 접근

#### 변환 방법 선택
- **단순 변환**: 생성자 또는 toArray() 활용
- **가공 필요**: Stream API 활용  
- **정렬 필요**: TreeSet 또는 변환 후 정렬

#### 성능 고려사항
```java
// 대용량 데이터에서는 Stream 병렬 처리 고려
list.parallelStream().sorted().collect(Collectors.toList());

// 하지만 소량 데이터에서는 오히려 느릴 수 있음
// 일반적으로 10만 개 이상에서 병렬 처리 고려
```

---

## 🔗 관련 문제 패턴

1. **중복 제거**: 백준 1181(단어 정렬), 10816(숫자 카드 2)
2. **빈도수 정렬**: 백준 2910(빈도 정렬), 프로그래머스 베스트앨범
3. **좌표 정렬**: 백준 11650, 11651, 1181
4. **자료구조 변환**: 대부분의 정렬 문제에서 필수

**자료구조 변환은 알고리즘 구현의 기본 도구입니다!** 🛠️✨