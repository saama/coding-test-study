# Java 코딩테스트 베스트 프랙티스

> "빠른 풀이보다 안 틀리는 코드" - 실무형 개발자를 위한 안정성 중심 가이드

## 🎯 기본 원칙

1. **가독성 > 성능** (제한시간 내에서)
2. **검증된 패턴 사용** (창의적 해법보다는 안전한 해법)
3. **예외상황 미리 고려** (엣지 케이스 체크)
4. **단계별 구현** (한번에 완성하려 하지 말기)

---

## 📋 코딩테스트용 Java 필수 세팅

### Import 모음
```java
import java.io.*;
import java.util.*;
import java.util.stream.*;
```

### 기본 템플릿 구조
```java
public class Solution {
    // 전역 변수 (필요시)
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 처리
        
        // 메인 로직
        
        // 결과 출력
    }
    
    // 헬퍼 메서드들
}
```

---

## 🛠️ 입출력 처리 베스트 프랙티스

### 빠른 입력 처리
```java
// BufferedReader + StringTokenizer (추천)
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer st = new StringTokenizer(br.readLine());
int n = Integer.parseInt(st.nextToken());

// 한 줄에 여러 숫자
st = new StringTokenizer(br.readLine());
int a = Integer.parseInt(st.nextToken());
int b = Integer.parseInt(st.nextToken());

// 배열 입력
int[] arr = new int[n];
st = new StringTokenizer(br.readLine());
for (int i = 0; i < n; i++) {
    arr[i] = Integer.parseInt(st.nextToken());
}
```

### 빠른 출력 처리
```java
// 단순 출력
System.out.println(result);

// 대량 출력시 StringBuilder 사용
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append(result[i]).append('\n');
}
System.out.print(sb);
```

---

## 🏗️ 자료구조 선택 가이드

### Array vs ArrayList
```java
// 크기 고정, 빠른 접근
int[] arr = new int[n];

// 동적 크기, 편리한 메서드
List<Integer> list = new ArrayList<>();
```

### Map 종류별 용도
```java
// 일반적인 키-값 저장 (O(1) 평균)
Map<String, Integer> map = new HashMap<>();

// 정렬된 순서 유지 (O(log n))
Map<String, Integer> sortedMap = new TreeMap<>();

// 삽입 순서 유지
Map<String, Integer> orderedMap = new LinkedHashMap<>();
```

### Set 종류별 용도
```java
// 중복 제거 (O(1) 평균)
Set<Integer> set = new HashSet<>();

// 정렬된 상태로 중복 제거
Set<Integer> sortedSet = new TreeSet<>();
```

### Queue 구현체 선택
```java
// 일반 큐
Queue<Integer> queue = new LinkedList<>();

// 우선순위 큐 (최소힙)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// 우선순위 큐 (최대힙)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Deque (양방향 큐)
Deque<Integer> deque = new ArrayDeque<>();
```

---

## 🔍 문제 분석 체크리스트

### 1. 제약 조건 확인
- **N의 범위**: 시간복잡도 결정
- **데이터 타입**: int vs long 선택
- **특수 조건**: 음수, 0, 중복값 허용 여부

### 2. 입출력 형태 파악
- 테스트 케이스 개수
- 입력 순서와 형태
- 출력 형식 (공백, 줄바꿈)

### 3. 예제 분석
```java
// 예제를 직접 따라가며 로직 검증
// 1단계: 입력값 확인
// 2단계: 중간 과정 추적  
// 3단계: 출력값 확인
```

---

## ⚡ 성능 최적화 팁

### 시간복잡도 개선
```java
// ❌ 비효율적
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        if (list.contains(arr[i])) { // O(n) 연산을 반복
            // 처리
        }
    }
}

// ✅ 효율적  
Set<Integer> set = new HashSet<>(list); // 한번만 변환
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        if (set.contains(arr[i])) { // O(1) 연산
            // 처리
        }
    }
}
```

### 메모리 최적화
```java
// ❌ 불필요한 객체 생성
for (int i = 0; i < n; i++) {
    String temp = str.substring(i, i+1); // 매번 새 객체
}

// ✅ char 직접 사용
for (int i = 0; i < n; i++) {
    char c = str.charAt(i); // 객체 생성 없음
}
```

---

## 🐛 자주하는 실수 방지법

### 1. 배열 인덱스 실수
```java
// ✅ 안전한 패턴
for (int i = 0; i < arr.length; i++) {
    // arr[i] 안전
}

// ✅ 범위 체크 습관화
if (x >= 0 && x < n && y >= 0 && y < m) {
    // 접근 안전
}
```

### 2. null 체크
```java
// ✅ 방어적 프로그래밍
if (str != null && str.length() > 0) {
    // 문자열 처리
}

Map<String, Integer> map = new HashMap<>();
// ✅ 안전한 접근
int count = map.getOrDefault(key, 0);
```

### 3. 타입 변환 주의
```java
// ❌ 오버플로우 위험
int result = a * b; // a, b가 클 때 위험

// ✅ 안전한 타입 사용
long result = (long) a * b;
```

---

## 📝 코드 작성 단계별 가이드

### 1단계: 의사코드 작성
```java
/*
1. 입력받기: n, arr[]
2. 정렬: Arrays.sort(arr)
3. 이진탐색으로 target 찾기
4. 결과 출력
*/
```

### 2단계: 기본 구조 작성
```java
public static void main(String[] args) {
    // TODO: 입력
    
    // TODO: 메인 로직
    
    // TODO: 출력
}
```

### 3단계: 단계별 구현
```java
// 입력 먼저 완성
int n = Integer.parseInt(br.readLine());
// 테스트해보기

// 메인 로직 구현
// 간단한 예제로 검증

// 출력 완성
```

### 4단계: 테스트 케이스 검증
```java
// 주어진 예제
// 경계값 (최소/최대)
// 특수 케이스 (0, 음수 등)
```

---

## 🧪 디버깅 전략

### 1. 단계별 출력
```java
System.out.println("DEBUG: n = " + n); // 개발 중에만 사용
System.out.println("DEBUG: arr = " + Arrays.toString(arr));
```

### 2. 부분 검증
```java
// 복잡한 로직을 작은 단위로 나누어 검증
boolean isValid = checkCondition(x, y);
System.out.println("Condition check: " + isValid);
```

### 3. 예외 상황 로깅
```java
if (arr.length == 0) {
    System.out.println("WARN: Empty array");
    return 0;
}
```

---

## 🎯 문제 유형별 체크포인트

### DFS/BFS 문제
- [ ] 방문 배열 초기화 확인
- [ ] 범위 체크 (nx, ny)  
- [ ] 시작점 설정 정확한지
- [ ] 조건 체크 순서 (범위 → 방문 → 조건)

### 구현 문제
- [ ] 입출력 형식 정확한지
- [ ] 반복문 범위 올바른지
- [ ] 조건문 논리 정확한지
- [ ] 엣지 케이스 고려했는지

### 자료구조 문제  
- [ ] 적절한 자료구조 선택했는지
- [ ] 삽입/삭제 순서 올바른지
- [ ] 빈 상태 체크했는지
- [ ] 시간복잡도 만족하는지

---

## 🚀 실전 팁

### 시간 관리
- **분석 시간**: 5-10분 (충분히 이해하고 시작)
- **구현 시간**: 20-30분 (단계별로)
- **검증 시간**: 5-10분 (예제 + 엣지케이스)

### 막혔을 때 대처법
1. **문제 재독**: 놓친 조건은 없는지
2. **예제 추적**: 손으로 직접 따라가기
3. **단순화**: 더 쉬운 버전부터 해결
4. **패턴 매칭**: 비슷한 문제 떠올리기

### 제출 전 체크리스트
- [ ] 컴파일 에러 없음
- [ ] 주어진 예제 통과
- [ ] 시간복잡도 적절함
- [ ] 메모리 사용량 괜찮음
- [ ] 특수 케이스 고려됨

**Remember**: "완벽한 코드보다는 동작하는 코드!" 🎯