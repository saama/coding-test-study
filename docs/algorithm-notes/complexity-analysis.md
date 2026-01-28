# 시간복잡도와 공간복잡도

## 시간복잡도 (Time Complexity)

알고리즘이 실행되는 데 걸리는 시간을 입력 크기 n에 따라 표현한 것입니다.

### 주요 시간복잡도들

| 복잡도 | 이름 | 예시 | 설명 |
|--------|------|------|------|
| O(1) | 상수시간 | 배열 인덱스 접근 | 입력 크기와 무관하게 일정한 시간 |
| O(log n) | 로그시간 | 이진탐색 | 매번 절반씩 줄어드는 경우 |
| O(n) | 선형시간 | 배열 순회 | 입력 크기에 비례해서 증가 |
| O(n log n) | 선형로그시간 | 병합정렬, 힙정렬 | 효율적인 정렬 알고리즘 |
| O(n²) | 제곱시간 | 버블정렬, 선택정렬 | 이중 반복문 |
| O(n³) | 세제곱시간 | 삼중 반복문 | 매트릭스 곱셈 등 |
| O(2ⁿ) | 지수시간 | 모든 부분집합 생성 | 매우 비효율적 |

### 시간복잡도 분석 예시

```java
// O(1) - 상수시간
public int getFirst(int[] arr) {
    return arr[0];  // 항상 1번의 연산
}

// O(n) - 선형시간
public int sum(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {  // n번 반복
        sum += arr[i];
    }
    return sum;
}

// O(n²) - 제곱시간
public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {      // n번 반복
        for (int j = 0; j < arr.length - 1; j++) {  // n번 반복
            if (arr[j] > arr[j + 1]) {
                // swap
            }
        }
    }
}

// O(log n) - 로그시간
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

## 공간복잡도 (Space Complexity)

알고리즘이 실행되는 데 필요한 메모리 공간을 입력 크기 n에 따라 표현한 것입니다.

### 공간복잡도 구성 요소

1. **고정 공간**: 입력 크기와 무관한 공간 (변수, 상수 등)
2. **가변 공간**: 입력 크기에 따라 달라지는 공간 (배열, 재귀 호출 스택 등)

### 공간복잡도 분석 예시

```java
// O(1) - 상수 공간
public int sum(int[] arr) {
    int sum = 0;  // 고정된 크기의 변수만 사용
    for (int num : arr) {
        sum += num;
    }
    return sum;
}

// O(n) - 선형 공간
public int[] createCopy(int[] arr) {
    int[] copy = new int[arr.length];  // 입력 크기만큼 배열 생성
    for (int i = 0; i < arr.length; i++) {
        copy[i] = arr[i];
    }
    return copy;
}

// O(n) - 재귀 호출 스택
public int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);  // n번의 함수 호출이 스택에 쌓임
}

// O(n²) - 2차원 배열
public int[][] create2DArray(int n) {
    int[][] matrix = new int[n][n];  // n×n 크기의 2차원 배열
    return matrix;
}
```

## 복잡도 분석 팁

### 1. 최악의 경우를 고려
```java
// 최악의 경우 O(n) - 찾는 원소가 배열 끝에 있거나 없는 경우
public boolean linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) return true;
    }
    return false;
}
```

### 2. 반복문 분석
```java
// O(n) - 단일 반복문
for (int i = 0; i < n; i++) { ... }

// O(n²) - 이중 반복문
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) { ... }
}

// O(n log n) - 외부 n번, 내부 log n번
for (int i = 0; i < n; i++) {
    for (int j = 1; j < n; j *= 2) { ... }
}
```

### 3. 재귀 분석
```java
// T(n) = 2T(n/2) + O(1) → O(n) 시간, O(log n) 공간
public void divide(int n) {
    if (n <= 1) return;
    divide(n/2);
    divide(n/2);
}

// T(n) = T(n-1) + O(1) → O(n) 시간, O(n) 공간
public int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n-1) + fibonacci(n-2);  // 실제로는 O(2^n)
}
```

## 코딩테스트에서의 활용

### 시간제한별 적절한 복잡도

| 시간제한 | 적절한 복잡도 | N의 범위 |
|----------|---------------|----------|
| 1초 | O(n) | ~10^8 |
| 1초 | O(n log n) | ~10^6 |
| 1초 | O(n²) | ~10^4 |
| 1초 | O(n³) | ~500 |
| 1초 | O(2^n) | ~20 |

### 메모리 제한 고려
- 일반적으로 256MB 또는 512MB 제한
- int 배열 기준: 256MB ≈ 6700만 개 원소
- 공간복잡도도 함께 고려해야 함

### 실제 적용 예시
```java
// 문제: 배열에서 두 수의 합이 target인 인덱스 찾기
// 방법 1: O(n²) 시간, O(1) 공간
public int[] twoSum1(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[]{i, j};
            }
        }
    }
    return null;
}

// 방법 2: O(n) 시간, O(n) 공간
public int[] twoSum2(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        map.put(nums[i], i);
    }
    return null;
}
```

## 정리

- **시간복잡도**: 알고리즘 실행 시간의 증가율
- **공간복잡도**: 알고리즘이 사용하는 메모리의 증가율
- **Big-O 표기법**: 최악의 경우를 나타냄
- **트레이드오프**: 시간과 공간 사이의 균형 고려
- **실제 코딩테스트**: 시간/메모리 제한을 고려한 적절한 복잡도 선택이 중요