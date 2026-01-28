# 백준 1181번 - 단어 정렬

**난이도**: Silver V  
**링크**: https://www.acmicpc.net/problem/1181  
**태그**: 정렬, 중복 제거, 다중 조건 정렬  

## 문제 요약
N개의 단어가 들어오면 아래 조건에 따라 정렬하여 출력:
1. 길이가 짧은 것부터
2. 길이가 같으면 사전 순으로
3. **중복된 단어는 하나만 출력**

## 핵심 아이디어
1. **다중 조건 정렬**: 길이 우선 → 사전순 보조
2. **중복 제거**: HashSet 또는 정렬 후 비교
3. **올바른 출력 형식**: Arrays.toString() ❌, 개별 출력 ✅

## 풀이 과정

### 1단계: 정렬 기준 분석
```java
// 비교 조건
if (a.length() != b.length()) {
    return a.length() - b.length(); // 길이 우선 (오름차순)
} else {
    return a.compareTo(b);          // 사전순 보조
}
```

### 2단계: 중복 제거 방법 선택
- **방법 1**: HashSet 활용 (입력 시점에 중복 제거)
- **방법 2**: 정렬 후 인접 원소 비교

## 최종 해법들

### 해법 1: 정렬 후 중복 제거 (기본)
```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    String[] strs = new String[N];
    
    for (int i = 0; i < N; i++) {
        strs[i] = br.readLine();
    }
    
    // 다중 조건 정렬
    Arrays.sort(strs, (a, b) -> {
        if (a.length() != b.length()) {
            return a.length() - b.length(); // 길이 우선
        } else {
            return a.compareTo(b);          // 사전순 보조
        }
    });
    
    // 올바른 출력 + 중복 제거
    System.out.println(strs[0]);
    for (int i = 1; i < strs.length; i++) {
        if (!strs[i].equals(strs[i-1])) { // 인접 원소 비교
            System.out.println(strs[i]);
        }
    }
}
```

### 해법 2: HashSet → 배열 변환 (Stream 미사용)
```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    Set<String> wordSet = new HashSet<>();
    
    for (int i = 0; i < N; i++) {
        wordSet.add(br.readLine()); // 입력과 동시에 중복 제거
    }
    
    // HashSet → 배열 변환
    String[] words = wordSet.toArray(new String[0]);
    
    // 정렬
    Arrays.sort(words, (a, b) -> {
        if (a.length() != b.length()) {
            return a.length() - b.length();
        } else {
            return a.compareTo(b);
        }
    });
    
    // 출력
    for (String word : words) {
        System.out.println(word);
    }
}
```

### 해법 3: HashSet → List 변환
```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    Set<String> wordSet = new HashSet<>();
    
    for (int i = 0; i < N; i++) {
        wordSet.add(br.readLine());
    }
    
    // HashSet → List 변환
    List<String> wordList = new ArrayList<>(wordSet);
    
    // List 정렬
    wordList.sort((a, b) -> {
        if (a.length() != b.length()) {
            return a.length() - b.length();
        } else {
            return a.compareTo(b);
        }
    });
    
    // 출력
    for (String word : wordList) {
        System.out.println(word);
    }
}
```

### 해법 4: TreeSet 활용 (가장 효율적!)
```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    
    // TreeSet에 커스텀 Comparator 설정 → 삽입과 동시에 정렬
    Set<String> wordSet = new TreeSet<>((a, b) -> {
        if (a.length() != b.length()) {
            return a.length() - b.length();
        } else {
            return a.compareTo(b);
        }
    });
    
    for (int i = 0; i < N; i++) {
        wordSet.add(br.readLine()); // 자동으로 정렬됨
    }
    
    // TreeSet은 이미 정렬된 상태
    for (String word : wordSet) {
        System.out.println(word);
    }
}
```

### 해법 5: Stream API 활용
```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int N = Integer.parseInt(br.readLine());
    Set<String> wordSet = new HashSet<>();
    
    for (int i = 0; i < N; i++) {
        wordSet.add(br.readLine());
    }
    
    wordSet.stream()
        .sorted((a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            } else {
                return a.compareTo(b);
            }
        })
        .forEach(System.out::println);
}
```

## 핵심 패턴들

### 1. toArray(new String[0])의 의미
```java
String[] words = wordSet.toArray(new String[0]);
//                               ↑
//                     크기 0인 배열 = 타입 힌트
```

**동작 원리**:
- `new String[0]`: 타입을 지정하는 힌트 역할
- Set 크기 > 배열 크기 → JVM이 적절한 크기의 새 배열 생성
- **Java 6+에서 권장되는 방식** (성능상 최적화됨)

### 2. 중복 제거 방법 비교
| 방법 | 장점 | 단점 | 상황 |
|------|------|------|------|
| **HashSet** | 입력 시점에 중복 제거, 효율적 | 추가 변환 필요 | 중복이 많을 때 |
| **정렬 후 비교** | 별도 자료구조 불필요 | 중복 체크 로직 필요 | 메모리 절약 |
| **TreeSet** | 자동 정렬 + 중복 제거 | 삽입 시 약간 느림 | **가장 권장** |

### 3. 백준 출력 형식 주의사항
```java
// ❌ 잘못된 출력 (배열 형태)
System.out.println(Arrays.toString(words)); 
// 출력: [but, i, go, to, out, play, want]

// ✅ 올바른 출력 (한 줄씩)
for (String word : words) {
    System.out.println(word);
}
// 출력:
// but
// i  
// go
// to
// ...
```

## 실수하기 쉬운 포인트

### 1. StringTokenizer 불필요 사용
```java
// ❌ 불필요한 복잡함
StringTokenizer st = new StringTokenizer(br.readLine());
strs[i] = st.nextToken();

// ✅ 간단하고 효율적 (한 줄에 단어 하나)
strs[i] = br.readLine();
```

### 2. 중복 제거 누락
```java
// 입력: ["i", "to", "i", "want", "to"]
// ❌ 중복 제거 안 함: [i, to, i, want, to]
// ✅ 중복 제거: [i, to, want]
```

### 3. 정렬 기준 실수
```java
// ❌ 길이 내림차순 (긴 것부터)
return b.length() - a.length();

// ✅ 길이 오름차순 (짧은 것부터)  
return a.length() - b.length();
```

## 성능 비교

| 해법 | 시간복잡도 | 공간복잡도 | 특징 |
|------|-----------|-----------|------|
| **해법 1** | O(N log N) | O(N) | 기본적, 메모리 효율 |
| **해법 2-3** | O(N log N) | O(N) | 변환 오버헤드 |
| **해법 4** | O(N log N) | O(N) | **가장 효율적** |
| **해법 5** | O(N log N) | O(N) | 함수형 스타일 |

**추천**: **TreeSet 방식 (해법 4)**가 가장 깔끔하고 효율적!

## 관련 문제
- 백준 10814번: 나이순 정렬 (안정 정렬)
- 백준 11650번: 좌표 정렬하기 (2차원 정렬)  
- 프로그래머스: 문자열 내 마음대로 정렬하기 (n번째 문자)

## 학습 포인트
1. **다중 조건 정렬**: 우선 조건과 보조 조건의 조합
2. **중복 제거**: Set의 다양한 활용법
3. **출력 형식**: 백준과 프로그래머스의 차이점
4. **자료구조 선택**: HashSet vs TreeSet vs 배열
5. **toArray() 패턴**: `new Type[0]`의 의미와 활용
6. **Stream vs 전통적 방식**: 상황에 따른 선택

**단어 정렬은 정렬 알고리즘의 기본기를 다지는 대표 문제입니다!** 📚✨