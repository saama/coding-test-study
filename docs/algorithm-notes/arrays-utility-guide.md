# Java Arrays 클래스 완벽 가이드

> 코딩테스트에서 필수적인 Arrays 유틸리티 함수들과 활용법

## 🎯 Arrays 클래스란?

`java.util.Arrays`는 배열을 다루는 다양한 정적 메서드를 제공하는 유틸리티 클래스입니다. 코딩테스트에서 배열 조작, 정렬, 검색, 변환 등에 자주 사용됩니다.

```java
import java.util.Arrays;
```

---

## 📋 핵심 메서드 목록

### 1. 배열 정렬 `sort()`

| 메서드 | 설명 | 시간복잡도 | 예시 |
|--------|------|-----------|------|
| `Arrays.sort(array)` | 오름차순 정렬 | O(n log n) | `Arrays.sort(arr)` |
| `Arrays.sort(array, from, to)` | 부분 정렬 | O(k log k) | `Arrays.sort(arr, 1, 4)` |
| `Arrays.sort(array, comparator)` | 커스텀 정렬 | O(n log n) | `Arrays.sort(arr, Collections.reverseOrder())` |

#### 기본 정렬 예시
```java
int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6};

// 전체 배열 정렬
Arrays.sort(numbers);
System.out.println(Arrays.toString(numbers)); // [1, 1, 2, 3, 4, 5, 6, 9]

// 부분 배열 정렬 (인덱스 2~5)
int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
Arrays.sort(arr, 2, 6); // 인덱스 2~5까지 정렬
System.out.println(Arrays.toString(arr)); // [3, 1, 1, 4, 5, 9, 2, 6]
```

#### 커스텀 정렬 예시
```java
String[] words = {"apple", "pie", "cherry", "banana"};

// 길이 기준 정렬
Arrays.sort(words, (a, b) -> Integer.compare(a.length(), b.length()));
System.out.println(Arrays.toString(words)); // [pie, apple, cherry, banana]

// 내림차순 정렬
Integer[] nums = {3, 1, 4, 1, 5, 9};
Arrays.sort(nums, Collections.reverseOrder());
System.out.println(Arrays.toString(nums)); // [9, 5, 4, 3, 1, 1]
```

### 2. 배열 복사 `copyOf()`, `copyOfRange()`

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `Arrays.copyOf(array, newLength)` | 배열 전체 복사 (길이 조정) | `Arrays.copyOf(arr, 5)` |
| `Arrays.copyOfRange(array, from, to)` | 부분 배열 복사 | `Arrays.copyOfRange(arr, 1, 4)` |

#### 배열 복사 예시
```java
int[] original = {1, 2, 3, 4, 5};

// 전체 복사 (길이 조정)
int[] copy1 = Arrays.copyOf(original, 3);     // [1, 2, 3]
int[] copy2 = Arrays.copyOf(original, 8);     // [1, 2, 3, 4, 5, 0, 0, 0]

// 부분 복사 (K번째 수 문제에서 사용!)
int[] subArray = Arrays.copyOfRange(original, 1, 4);  // [2, 3, 4] (인덱스 1~3)
```

### 3. 배열 검색 `binarySearch()`

| 메서드 | 설명 | 전제조건 | 시간복잡도 |
|--------|------|---------|-----------|
| `Arrays.binarySearch(array, key)` | 이진 검색 | 정렬된 배열 | O(log n) |
| `Arrays.binarySearch(array, from, to, key)` | 부분 이진 검색 | 정렬된 구간 | O(log k) |

```java
int[] sortedArray = {1, 3, 5, 7, 9, 11, 13};

int index = Arrays.binarySearch(sortedArray, 7);     // 3 (인덱스)
int notFound = Arrays.binarySearch(sortedArray, 6);  // -4 (삽입 위치의 음수값 - 1)
```

### 4. 배열 비교 및 동등성 `equals()`, `compare()`

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `Arrays.equals(array1, array2)` | 배열 동등성 비교 | `Arrays.equals(arr1, arr2)` |
| `Arrays.deepEquals(array1, array2)` | 다차원 배열 비교 | `Arrays.deepEquals(matrix1, matrix2)` |
| `Arrays.compare(array1, array2)` | 사전순 비교 | `Arrays.compare(arr1, arr2)` |

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
int[] arr3 = {1, 2, 4};

boolean isEqual = Arrays.equals(arr1, arr2);    // true
int comparison = Arrays.compare(arr1, arr3);    // -1 (arr1이 사전순으로 앞서)
```

### 5. 배열 출력 및 변환 `toString()`

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `Arrays.toString(array)` | 1차원 배열 문자열 변환 | `Arrays.toString(arr)` |
| `Arrays.deepToString(array)` | 다차원 배열 문자열 변환 | `Arrays.deepToString(matrix)` |

```java
int[] arr = {1, 2, 3, 4, 5};
System.out.println(Arrays.toString(arr));        // [1, 2, 3, 4, 5]

int[][] matrix = {{1, 2}, {3, 4}};
System.out.println(Arrays.deepToString(matrix)); // [[1, 2], [3, 4]]
```

### 6. 배열 채우기 `fill()`

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `Arrays.fill(array, value)` | 전체 배열을 특정 값으로 채우기 | `Arrays.fill(arr, 0)` |
| `Arrays.fill(array, from, to, value)` | 부분 배열을 특정 값으로 채우기 | `Arrays.fill(arr, 1, 4, -1)` |

```java
int[] arr = new int[5];
Arrays.fill(arr, 7);               // [7, 7, 7, 7, 7]
Arrays.fill(arr, 1, 3, -1);        // [7, -1, -1, 7, 7]
```

### 7. 스트림 변환 `stream()`

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `Arrays.stream(array)` | 배열을 스트림으로 변환 | `Arrays.stream(arr)` |
| `Arrays.stream(array, from, to)` | 부분 배열을 스트림으로 변환 | `Arrays.stream(arr, 1, 4)` |

```java
int[] numbers = {1, 2, 3, 4, 5};

// 스트림으로 변환하여 처리
int sum = Arrays.stream(numbers).sum();                    // 15
int max = Arrays.stream(numbers).max().orElse(0);          // 5
int[] doubled = Arrays.stream(numbers).map(x -> x * 2).toArray(); // [2, 4, 6, 8, 10]
```

---

## 🎯 코딩테스트 실전 활용

### 1. K번째 수 문제 (day2_1에서 사용)

```java
public int[] solution(int[] array, int[][] commands) {
    int[] answer = new int[commands.length];
    
    for (int i = 0; i < commands.length; i++) {
        int start = commands[i][0] - 1;
        int end = commands[i][1] - 1;  
        int k = commands[i][2] - 1;
        
        // 핵심: Arrays.copyOfRange로 부분 배열 추출
        int[] subArray = Arrays.copyOfRange(array, start, end + 1);
        
        // Arrays.sort로 정렬
        Arrays.sort(subArray);
        
        answer[i] = subArray[k];
    }
    
    return answer;
}
```

### 2. 배열 정렬 문제

```java
// 기본 정렬
Arrays.sort(arr);

// 내림차순 정렬 (Integer 배열만 가능)
Arrays.sort(integerArray, Collections.reverseOrder());

// 커스텀 정렬 (문자열 길이순)
Arrays.sort(strings, (a, b) -> Integer.compare(a.length(), b.length()));

// 2차원 배열 정렬 (첫 번째 원소 기준)
Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
```

### 3. 이진 탐색 활용

```java
// 정렬된 배열에서 특정 값 찾기
Arrays.sort(arr);
int index = Arrays.binarySearch(arr, target);

if (index >= 0) {
    System.out.println("찾은 인덱스: " + index);
} else {
    System.out.println("삽입 위치: " + (-index - 1));
}
```

### 4. 배열 비교 (중복 배열 찾기)

```java
// 두 배열이 같은지 확인
if (Arrays.equals(arr1, arr2)) {
    System.out.println("같은 배열");
}

// 2차원 배열 비교
if (Arrays.deepEquals(matrix1, matrix2)) {
    System.out.println("같은 행렬");
}
```

### 5. 스트림과 함께 사용

```java
// 배열을 스트림으로 변환하여 처리
int[] result = Arrays.stream(commands)
                .mapToInt(cmd -> {
                    int[] sub = Arrays.copyOfRange(array, cmd[0] - 1, cmd[1]);
                    Arrays.sort(sub);
                    return sub[cmd[2] - 1];
                })
                .toArray();
```

---

## 💡 성능 최적화 팁

### 1. Arrays.copyOfRange vs 직접 복사

```java
// ✅ 효율적 - Arrays.copyOfRange 사용
int[] subArray = Arrays.copyOfRange(array, start, end + 1);

// ❌ 비효율적 - 직접 복사
int[] subArray = new int[end - start + 1];
for (int i = start; i <= end; i++) {
    subArray[i - start] = array[i];
}
```

### 2. Arrays.sort vs Collections.sort

```java
// int[] 배열의 경우
Arrays.sort(intArray);              // ✅ 빠름

// List<Integer>의 경우  
Collections.sort(integerList);      // ✅ 적절
```

### 3. 부분 정렬 활용

```java
// 전체 배열 중 일부만 정렬이 필요한 경우
Arrays.sort(array, startIndex, endIndex);  // ✅ 효율적
```

---

## ⚠️ 주의사항

### 1. Primitive vs Object 배열

```java
// ❌ 원시 타입 배열은 Collections.reverseOrder() 사용 불가
int[] primitiveArray = {3, 1, 4};
// Arrays.sort(primitiveArray, Collections.reverseOrder()); // 컴파일 에러

// ✅ 객체 배열은 사용 가능
Integer[] objectArray = {3, 1, 4};
Arrays.sort(objectArray, Collections.reverseOrder()); // 정상 동작
```

### 2. binarySearch 전제조건

```java
int[] array = {3, 1, 4, 1, 5};

// ❌ 정렬되지 않은 배열에서 이진 탐색
int wrongResult = Arrays.binarySearch(array, 4); // 잘못된 결과

// ✅ 정렬 후 이진 탐색
Arrays.sort(array);
int correctResult = Arrays.binarySearch(array, 4); // 올바른 결과
```

### 3. copyOfRange의 인덱스 범위

```java
int[] array = {1, 2, 3, 4, 5};

// copyOfRange(array, from, to)에서 to는 exclusive
int[] sub1 = Arrays.copyOfRange(array, 1, 4);  // [2, 3, 4] (인덱스 1~3)
int[] sub2 = Arrays.copyOfRange(array, 0, 3);  // [1, 2, 3] (인덱스 0~2)
```

---

## 🔗 관련 문제 유형

### Arrays 클래스가 자주 사용되는 문제들:

1. **정렬 문제**: K번째 수, 가장 큰 수, H-Index
2. **이진 탐색**: 입국심사, 징검다리, 예산
3. **배열 조작**: 행렬의 곱셈, 배열 회전
4. **구간 처리**: 파괴되지 않은 건물, 표 편집

---

## 📚 추가 학습 자료

- [Oracle 공식 Arrays 문서](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html)
- [Stream API와 Arrays 연동](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)

**Remember**: Arrays 클래스는 코딩테스트의 기본기! 각 메서드의 시간복잡도와 제한사항을 숙지하자 🚀