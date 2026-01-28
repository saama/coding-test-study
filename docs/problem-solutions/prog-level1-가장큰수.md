# 프로그래머스 Lv1 - 가장 큰 수

**난이도**: Level 2 (Level 1으로 분류된 경우도 있음)  
**링크**: https://school.programmers.co.kr/learn/courses/30/lessons/42746  
**태그**: 정렬, 문자열 조합, Comparator, 그리디  

## 문제 요약
0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배열하여 만들 수 있는 가장 큰 수를 문자열로 반환하라.

**예시**:
- `[6, 10, 2]` → `"6210"`
- `[3, 30, 34, 5, 9]` → `"9534330"`

## 핵심 아이디어
**문자열 조합 비교**: `a + b`와 `b + a`를 비교하여 더 큰 조합이 앞에 오도록 정렬

### 왜 단순 사전순 정렬로는 안 될까?
```java
// 잘못된 접근: 단순 사전순
["3", "30"] → "3" > "30" → ["30", "3"] → "303" ❌

// 올바른 접근: 조합 비교
"3" + "30" = "330" vs "30" + "3" = "303"
"330" > "303" → ["3", "30"] → "330" ✅
```

## 풀이 과정

### 1단계: 핵심 알고리즘 이해
```java
// 두 수 a, b를 비교할 때
String ab = a + b;  // a를 b 앞에 배치
String ba = b + a;  // b를 a 앞에 배치

// 더 큰 조합이 만들어지는 순서로 배치
if (ab.compareTo(ba) > 0) {
    // a를 b 앞에 배치
} else {
    // b를 a 앞에 배치
}
```

### 2단계: Comparator 구현
```java
// 내림차순으로 정렬 (큰 조합이 앞으로)
Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));
```

## 최종 코드

### 해법 1: 기본 구현
```java
public String solution(int[] numbers) {
    // 1. int를 String으로 변환
    String[] strNumbers = new String[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
        strNumbers[i] = String.valueOf(numbers[i]);
    }
    
    // 2. 조합 비교로 정렬
    Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));
    
    // 3. 엣지케이스: 모든 수가 0인 경우
    if (strNumbers[0].equals("0")) {
        return "0";
    }
    
    // 4. 문자열 연결
    StringBuilder sb = new StringBuilder();
    for (String str : strNumbers) {
        sb.append(str);
    }
    
    return sb.toString();
}
```

### 해법 2: Stream API 활용
```java
public String solution(int[] numbers) {
    String result = Arrays.stream(numbers)
        .mapToObj(String::valueOf)                          // int → String
        .sorted((a, b) -> (b + a).compareTo(a + b))         // 조합 비교 정렬
        .reduce("", (acc, str) -> acc + str);               // 문자열 연결
    
    // 엣지케이스 처리
    return result.startsWith("0") ? "0" : result;
}
```

### 해법 3: Comparator.comparing 활용 (고급)
```java
public String solution(int[] numbers) {
    String[] strNumbers = Arrays.stream(numbers)
        .mapToObj(String::valueOf)
        .toArray(String[]::new);
    
    // 커스텀 Comparator로 조합 비교
    Arrays.sort(strNumbers, (a, b) -> {
        return (b + a).compareTo(a + b); // 내림차순
    });
    
    if (strNumbers[0].equals("0")) return "0";
    
    return String.join("", strNumbers);
}
```

## 핵심 알고리즘 상세 분석

### 조합 비교의 원리
```java
numbers = [3, 30, 34, 5, 9]

// 비교 과정 예시:
"3" vs "30": "330" vs "303" → "330"이 더 큼 → "3"이 앞
"34" vs "5": "345" vs "534" → "534"가 더 큼 → "5"가 앞
"9" vs "5": "95" vs "59" → "95"가 더 큼 → "9"가 앞

// 최종 정렬 결과: ["9", "5", "34", "3", "30"]
// 결과: "9534330"
```

### 엣지케이스 처리
```java
// 모든 수가 0인 경우
[0, 0, 0] → ["0", "0", "0"] → "000" → "0"

// 체크 방법
if (strNumbers[0].equals("0")) return "0";
```

## 자주하는 실수들

### 1. 첫 번째 문자만 비교
```java
// ❌ 잘못된 접근
if (a.charAt(0) > b.charAt(0)) { ... }

// "3" vs "30" → 둘 다 첫 문자가 '3'로 같음
// 올바른 결과를 얻을 수 없음
```

### 2. int.toString() 사용
```java
// ❌ 컴파일 에러
String str = number.toString(); 

// ✅ 올바른 변환
String str = String.valueOf(number);
String str = Integer.toString(number);
```

### 3. 배열 타입 문제
```java
// ❌ int[] 배열은 Comparator 사용 불가
Arrays.sort(numbers, comparator);

// ✅ String[] 변환 후 정렬
String[] strNumbers = ...;
Arrays.sort(strNumbers, comparator);
```

### 4. 엣지케이스 누락
```java
// ❌ "000" 반환
return sb.toString();

// ✅ "0" 반환
if (result.startsWith("0")) return "0";
return result;
```

## 최적화 기법

### 1. StringBuilder vs String 연결
```java
// ❌ 비효율적
String result = "";
for (String str : strNumbers) {
    result += str;  // 매번 새로운 String 객체 생성
}

// ✅ 효율적
StringBuilder sb = new StringBuilder();
for (String str : strNumbers) {
    sb.append(str);  // 기존 버퍼에 추가
}
```

### 2. Stream reduce vs StringBuilder
```java
// Stream 방식 (함수형)
.reduce("", String::concat)

// StringBuilder 방식 (명령형, 더 효율적)
StringBuilder sb = new StringBuilder();
```

## 시간/공간 복잡도
- **시간복잡도**: O(N log N × M) 
  - N: 배열 길이, M: 문자열 평균 길이
  - 정렬: O(N log N), 문자열 비교: O(M)
- **공간복잡도**: O(N) (문자열 배열 생성)

## 유사 문제들

### 같은 패턴의 문제들
1. **백준 1431번**: 시리얼 번호 (다중 조건 정렬)
2. **백준 1181번**: 단어 정렬 (길이 → 사전순)
3. **프로그래머스**: H-Index (정렬 후 조건 확인)

### 핵심 응용
- **문자열 조합 최적화**: 여러 문자열을 연결할 때 최적 순서 찾기
- **그리디 알고리즘**: 매번 최선의 선택이 전체 최적해가 되는 경우

## 학습 포인트
1. **조합 비교의 아이디어**: 단순 크기 비교가 아닌 결과 비교
2. **그리디 증명**: 왜 매번 최선의 선택이 전체 최적해가 되는가
3. **문자열 처리**: int → String 변환과 연결 최적화
4. **엣지케이스**: 모든 수가 0인 특수한 경우
5. **정렬 활용**: 복잡한 비교 기준도 Comparator로 해결 가능

**이 문제의 핵심은 '조합 비교'라는 창의적 아이디어입니다!** 🧠✨