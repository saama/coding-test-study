# 프로그래머스 Lv1 - 문자열 내 마음대로 정렬하기

**난이도**: Level 1  
**링크**: https://school.programmers.co.kr/learn/courses/30/lessons/12915  
**태그**: 정렬, Comparator, 다중 조건 정렬  

## 문제 요약
문자열로 구성된 리스트 strings와 정수 n이 주어졌을 때:
- 각 문자열의 n번째 문자를 기준으로 오름차순 정렬
- n번째 문자가 같다면 사전순으로 정렬

## 핵심 아이디어
**다중 조건 정렬**: 우선 조건(n번째 문자) → 보조 조건(사전순)

## 풀이 과정

### 1단계: 문제 분석
- n번째 문자 우선 비교 필요
- 같을 경우 전체 문자열 사전순 비교
- Arrays.sort + 커스텀 Comparator 활용

### 2단계: 다중 조건 정렬 구현
```java
Arrays.sort(answer, (a, b) -> {
    if (a.charAt(n) != b.charAt(n)) {
        return a.charAt(n) - b.charAt(n); // n번째 문자 우선
    } else {
        return a.compareTo(b);            // 사전순 보조
    }
});
```

## 최종 코드

### 해법 1: 기본 람다식 구현
```java
public String[] solution(String[] strings, int n) {
    String[] answer = strings.clone(); // 원본 보호
    Arrays.sort(answer, (a, b) -> {
        if (a.charAt(n) != b.charAt(n)) {
            return a.charAt(n) - b.charAt(n);
        } else {
            return a.compareTo(b);
        }
    });
    return answer;
}
```

### 해법 2: Comparator.comparing 체이닝 (권장)
```java
public String[] solution(String[] strings, int n) {
    String[] answer = strings.clone();
    Arrays.sort(answer, Comparator
        .comparing((String s) -> s.charAt(n))  // n번째 문자 우선
        .thenComparing(s -> s));               // 문자열 전체 비교
    return answer;
}
```

### 해법 3: 전통적인 Comparator 구현
```java
public String[] solution(String[] strings, int n) {
    String[] answer = strings.clone();
    Arrays.sort(answer, new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            char c1 = s1.charAt(n), c2 = s2.charAt(n);
            if (c1 != c2) return c1 - c2;
            return s1.compareTo(s2);
        }
    });
    return answer;
}
```

## 핵심 패턴

### 1. Comparator 반환값 규칙
| 반환값 | 의미 | 결과 |
|--------|------|------|
| **양수 (> 0)** | a > b | a를 뒤로 배치 |
| **0** | a == b | 순서 유지 |
| **음수 (< 0)** | a < b | a를 앞으로 배치 |

### 2. 다중 조건 정렬 패턴
```java
// 패턴 1: if-else 분기
if (우선조건 != 같음) return 우선조건결과;
else return 보조조건결과;

// 패턴 2: Comparator 체이닝
Comparator.comparing(우선조건).thenComparing(보조조건)
```

### 3. 문자열 비교 방법
```java
// 문자 하나 비교
a.charAt(i) - b.charAt(i)  // ASCII 값 차이

// 전체 문자열 사전순 비교
a.compareTo(b)  // -1, 0, 1 반환
```

## 실수하기 쉬운 포인트

### 1. 원본 배열 수정
```java
// ❌ 원본 수정
Arrays.sort(strings, comparator);
return strings;

// ✅ 원본 보호
String[] answer = strings.clone();
Arrays.sort(answer, comparator);
return answer;
```

### 2. 배열 타입 호환성
```java
// ❌ int[] 배열은 Comparator 사용 불가
int[] arr = {1, 2, 3};
Arrays.sort(arr, comparator); // 컴파일 에러

// ✅ Integer[] 또는 String[] 사용
Integer[] arr = {1, 2, 3};
Arrays.sort(arr, comparator); // 정상 동작
```

### 3. 문자열 연산 실수
```java
// ❌ 문자열끼리 뺄셈 불가
return a - b; // 컴파일 에러

// ✅ compareTo 사용
return a.compareTo(b);
```

## 시간/공간 복잡도
- **시간복잡도**: O(N log N) (Arrays.sort의 TimSort 알고리즘)
- **공간복잡도**: O(N) (배열 복사)

## 관련 문제
- 백준 1181번: 단어 정렬 (길이 → 사전순)
- 프로그래머스: 가장 큰 수 (문자열 조합 비교)
- 프로그래머스: 실패율 (실패율 → 스테이지 번호)

## 학습 포인트
1. **다중 조건 정렬**: Comparator 체이닝의 강력함
2. **Comparator 반환값**: -1, 0, 1의 정확한 의미
3. **원본 보호**: clone()을 활용한 안전한 정렬
4. **3가지 구현 방법**: 람다식, 체이닝, 전통적 방식의 장단점
5. **Java 버전별 스타일**: Java 8+ 함수형 vs 이전 객체지향

이 문제는 **커스텀 정렬의 기본기**를 다지는 중요한 문제입니다! 🎯