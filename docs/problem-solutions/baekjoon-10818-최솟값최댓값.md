# 백준 10818번 - 최솟값, 최댓값

## 📋 문제 정보
- **문제명**: 최솟값, 최댓값
- **플랫폼**: 백준 (BOJ)
- **번호**: 10818번
- **URL**: https://www.acmicpc.net/problem/10818
- **파일명**: `day2_add.java`
- **완료일**: 2024-01-09 (Day 2)

## 🎯 문제 분석

N개의 정수가 주어졌을 때, 최솟값과 최댓값을 구하는 문제입니다.

### 입력 형식
```
N                    (정수의 개수)
A1 A2 ... AN        (N개의 정수)
```

### 출력 형식
```
min max             (최솟값과 최댓값을 공백으로 구분)
```

### 예시
```
입력:
5
20 10 35 30 7

출력:
7 35
```

## 💡 사용자 원본 코드 분석

```java
// 사용자 원본 해법 (정렬 사용)
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    int T = Integer.parseInt(br.readLine()); // T → n으로 변수명 개선 권장
    st = new StringTokenizer(br.readLine());
    int[] arr = new int[T]; // 배열 저장 (O(N) 공간복잡도)
    
    for (int i = 0; i < T; i++) {
        int a = Integer.parseInt(st.nextToken());
        arr[i] = a;
    }

    Arrays.sort(arr); // O(N log N) 시간복잡도 - 최솟값/최댓값만 구할 때는 과도함

    // 결과 출력
    System.out.println(arr[0]+" "+arr[arr.length-1]); // 정렬 후 첫번째/마지막 → 최솟값/최댓값
    
    // ✅ 원본 로직은 완전히 정확함. 다만 효율성 개선 여지 있음
}
```

**🔍 사용자 원본 코드 평가:**
- **정확성**: 완전히 정확한 로직으로 올바른 결과 도출
- **접근법**: 정렬을 통한 최솟값/최댓값 추출 - 직관적이고 확실한 방법
- **시간복잡도**: O(N log N) - 정렬로 인한 오버헤드
- **공간복잡도**: O(N) - 전체 배열 저장
- **개선점**: 최솟값/최댓값만 필요하므로 O(N) 최적화 가능

## 🚀 개선된 해법

```java
// 개선된 해법 (O(N) 시간복잡도, O(1) 공간복잡도)
public static void solutionOptimized() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;
    
    int n = Integer.parseInt(br.readLine()); // 명확한 변수명 사용
    st = new StringTokenizer(br.readLine());
    
    int min = Integer.MAX_VALUE; // 최댓값으로 초기화
    int max = Integer.MIN_VALUE; // 최솟값으로 초기화
    
    // 한 번의 순회로 최솟값과 최댓값 동시 추적
    for (int i = 0; i < n; i++) {
        int num = Integer.parseInt(st.nextToken());
        min = Math.min(min, num); // 현재까지의 최솟값 갱신
        max = Math.max(max, num); // 현재까지의 최댓값 갱신
    }
    
    System.out.println(min + " " + max);
}
```

## 🔍 핵심 패턴 분석

### 1. 정렬 기반 접근 (원본)
```java
// 장점: 구현이 직관적, 확실한 결과
int[] arr = new int[n];
for (int i = 0; i < n; i++) {
    arr[i] = Integer.parseInt(st.nextToken());
}
Arrays.sort(arr);
int min = arr[0];
int max = arr[n-1];
```

### 2. 실시간 추적 접근 (개선)
```java
// 장점: 메모리 효율적, 더 빠른 실행
int min = Integer.MAX_VALUE;
int max = Integer.MIN_VALUE;
for (int i = 0; i < n; i++) {
    int num = Integer.parseInt(st.nextToken());
    min = Math.min(min, num);
    max = Math.max(max, num);
}
```

### 3. Math 클래스 활용 패턴
```java
// Math.min() / Math.max() 활용
min = Math.min(min, currentValue);
max = Math.max(max, currentValue);

// 직접 비교 (동일한 효과)
if (currentValue < min) min = currentValue;
if (currentValue > max) max = currentValue;
```

## 📊 성능 비교

| 접근법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|--------|------------|------------|------|------|
| 원본 (정렬) | O(N log N) | O(N) | 직관적, 확실함 | 정렬 오버헤드, 메모리 사용 |
| 개선 (추적) | O(N) | O(1) | 최적 효율성 | - |
| 스트림 | O(N) | O(N) | 함수형 스타일 | 메모리 오버헤드 |

## 💡 학습 포인트

### 1. 초기화 패턴의 중요성
```java
// 올바른 초기화
int min = Integer.MAX_VALUE;  // 가능한 최댓값으로 초기화
int max = Integer.MIN_VALUE;  // 가능한 최솟값으로 초기화

// 잘못된 초기화 예시
int min = 0;  // 입력이 모두 음수면 오류
int max = 0;  // 입력이 모두 음수면 오류
```

### 2. Math 클래스 활용
```java
// 간결하고 읽기 쉬움
int min = Math.min(a, b);
int max = Math.max(a, b);

// 삼중 비교도 가능
int min = Math.min(Math.min(a, b), c);

// 배열 전체 최솟값/최댓값
int min = Arrays.stream(array).min().orElse(0);
int max = Arrays.stream(array).max().orElse(0);
```

### 3. 상수 활용의 안전성
```java
// Integer 상수 활용
Integer.MAX_VALUE  // 2,147,483,647
Integer.MIN_VALUE  // -2,147,483,648

// Long 범위가 필요한 경우
Long.MAX_VALUE
Long.MIN_VALUE

// 첫 번째 값으로 초기화하는 방법
int first = Integer.parseInt(st.nextToken());
int min = first, max = first;
for (int i = 1; i < n; i++) {
    // 나머지 처리
}
```

### 4. 시간복잡도 최적화 사고
```java
// 문제: "최솟값과 최댓값만 구하면 된다"
// ❌ 비효율적 접근: 정렬 → O(N log N)
// ✅ 효율적 접근: 선형 탐색 → O(N)

// 일반적 원칙: 필요한 정보만 추출할 때는 전체 정렬 피하기
```

## 🎯 테스트 케이스

| 입력 | 예상 출력 | 검증 포인트 |
|------|----------|------------|
| `5`<br>`20 10 35 30 7` | `7 35` | 기본 케이스 |
| `1`<br>`42` | `42 42` | 단일 요소 |
| `3`<br>`-10 -5 -20` | `-20 -5` | 음수만 있는 경우 |
| `4`<br>`-1000000 1000000 0 500` | `-1000000 1000000` | 극값 포함 |

## 🔗 관련 패턴

### 1. 선택 알고리즘 (Selection Algorithm)
- **QuickSelect**: K번째 요소 찾기 O(N) 평균
- **Min/Max 동시 찾기**: 비교 횟수 최적화

### 2. 스트림 API 활용
```java
// Java 8+ 스트림 방식
IntSummaryStatistics stats = Arrays.stream(array)
                                  .summaryStatistics();
int min = stats.getMin();
int max = stats.getMax();
```

### 3. 분할 정복적 접근
```java
// 큰 데이터에서 병렬 처리
int min = Arrays.stream(array).parallel().min().orElse(0);
int max = Arrays.stream(array).parallel().max().orElse(0);
```

## 📈 난이도 평가
- **구현 난이도**: ⭐ (매우 기본적)
- **최적화 사고**: ⭐⭐ (정렬 vs 선형탐색 판단)
- **실수 가능성**: ⭐ (초기화 실수 가능)

## 🎁 보너스 팁

### 메모리 극한 최적화
```java
// 배열 없이 바로 처리 (원본보다 더 효율적)
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int n = Integer.parseInt(br.readLine());
StringTokenizer st = new StringTokenizer(br.readLine());

int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
while (st.hasMoreTokens()) {
    int num = Integer.parseInt(st.nextToken());
    if (num < min) min = num;
    if (num > max) max = num;
}
```

### 함수형 스타일 구현
```java
// 스트림 활용 (Java 8+)
List<Integer> numbers = Arrays.stream(br.readLine().split(" "))
                             .skip(1)  // 첫 번째 N 제외
                             .map(Integer::parseInt)
                             .collect(Collectors.toList());
                             
int min = numbers.stream().mapToInt(i -> i).min().orElse(0);
int max = numbers.stream().mapToInt(i -> i).max().orElse(0);
```

### 확장 버전 (최솟값/최댓값 인덱스까지)
```java
// 값뿐만 아니라 인덱스까지 추적
int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
int minIndex = -1, maxIndex = -1;

for (int i = 0; i < n; i++) {
    int num = Integer.parseInt(st.nextToken());
    if (num < min) {
        min = num;
        minIndex = i;
    }
    if (num > max) {
        max = num;
        maxIndex = i;
    }
}
```

---

**Day 2 완료! 시간복잡도 최적화와 Math 클래스 활용의 핵심을 마스터했습니다! 🎯**