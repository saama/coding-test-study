# 프로그래머스 Lv1 - [1차] 다트 게임

## 📋 문제 정보
- **문제명**: [1차] 다트 게임
- **플랫폼**: 프로그래머스 Lv1
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/17682
- **파일명**: `day3_1.java`
- **완료일**: 2024-01-09 (Day 3)

## 🎯 문제 분석

다트 게임은 3라운드로 구성되며, 각 라운드는 다음과 같은 규칙을 따릅니다:

### 입력 형식
- 점수(0~10) + 보너스(S/D/T) + [옵션(*/#)]
- 예시: `"1S2D*3T"` → 1점 Single, 2점 Double에 스타상, 3점 Triple

### 점수 규칙
- **0~10점**: 숫자로 표현 (10은 두 자리)
- **Single(S)**: 1제곱
- **Double(D)**: 2제곱  
- **Triple(T)**: 3제곱

### 옵션 규칙
- **스타상(*)**: 해당 점수와 바로 전 점수를 각각 2배
- **아차상(#)**: 해당 점수는 마이너스

## 💡 사용자 원본 접근법 분석

### ✅ 뛰어난 아이디어
```java
int[] scores = new int[3];     // 점수 배열 분리
char[] options = new char[3];  // 옵션 배열 분리
int[] tempNum = new int[2];    // 두 자리 수 처리 고려!
```

**사용자의 핵심 통찰:**
1. **구조적 사고**: 점수와 옵션을 분리해서 관리
2. **두 자리 수 인식**: 10점 처리 필요성을 정확히 파악
3. **단계적 접근**: 파싱 → 계산 → 결과의 명확한 단계 구분

### ⚠️ 구현상 문제점
```java
tempNum = Character.getNumericValue(c); // 타입 오류: int[]에 int 대입
scoreIdx++; // 매 문자마다 증가 → 인덱스 초과
```

## 🚀 개선된 해법

### 핵심 아이디어
- **상태 관리**: `round` 변수로 현재 라운드 추적
- **즉시 처리**: 파싱과 동시에 점수 계산 및 적용
- **10점 처리**: 연속된 `'1'`, `'0'` 검사

### 구현 코드
```java
public int solutionOptimized(String dartResult) {
    int[] scores = new int[3];
    int round = -1;
    
    for (int i = 0; i < dartResult.length(); i++) {
        char c = dartResult.charAt(i);
        
        if (Character.isDigit(c)) {
            round++; // 새 라운드 시작
            int score = c - '0';
            
            // 10점 처리
            if (c == '1' && i + 1 < dartResult.length() 
                && dartResult.charAt(i + 1) == '0') {
                score = 10;
                i++; // '0' 스킵
            }
            scores[round] = score;
            
        } else if (Character.isAlphabetic(c)) {
            if (c == 'D') {
                scores[round] *= scores[round];
            } else if (c == 'T') {
                scores[round] = scores[round] * scores[round] * scores[round];
            }
            
        } else if (c == '*' || c == '#') {
            if (c == '*') {
                scores[round] *= 2;
                if (round > 0) scores[round - 1] *= 2;
            } else {
                scores[round] *= -1;
            }
        }
    }
    
    return scores[0] + scores[1] + scores[2];
}
```

## 🔍 핵심 패턴 분석

### 1. 문자열 파싱 패턴
```java
if (Character.isDigit(c)) {
    // 숫자 처리
} else if (Character.isAlphabetic(c)) {
    // 보너스 처리
} else if (c == '*' || c == '#') {
    // 옵션 처리
}
```

### 2. 두 자리 수 처리 패턴
```java
if (c == '1' && i + 1 < dartResult.length() 
    && dartResult.charAt(i + 1) == '0') {
    score = 10;
    i++; // 다음 문자 스킵
}
```

### 3. 이전 상태 영향 패턴 (스타상)
```java
scores[round] *= 2;           // 현재 점수 2배
if (round > 0) {              // 이전 라운드 존재 확인
    scores[round - 1] *= 2;   // 이전 점수 2배
}
```

## 📊 복잡도 분석

- **시간복잡도**: O(N) - 문자열 길이만큼 순회
- **공간복잡도**: O(1) - 고정 크기 배열 사용

## 🎯 테스트 케이스

| 입력 | 계산 과정 | 출력 |
|------|----------|------|
| `"1S2D*3T"` | 1¹ + (2²×2) + 3³ = 1 + 8 + 27 = 36 | 37 |
| `"1D2S#10S"` | 1² + (2¹×-1) + 10¹ = 1 - 2 + 10 = 9 | 9 |
| `"1D2S0T"` | 1² + 2¹ + 0³ = 1 + 2 + 0 = 3 | 3 |

## 💡 학습 포인트

### 1. 문자열 파싱 기법
- `Character.isDigit()`, `isAlphabetic()` 활용
- 문자 타입별 분기 처리

### 2. 상태 관리
- 현재 진행 상태 추적의 중요성
- 이전 상태에 영향을 주는 로직 처리

### 3. 두 자리 수 처리
- 연속 문자 검사 및 인덱스 관리
- 문자열 파싱에서 자주 나오는 패턴

### 4. 즉시 적용 vs 분리 저장
- **원본 아이디어**: 점수와 옵션을 분리 저장 → 나중에 일괄 처리
- **개선 방식**: 파싱과 동시에 즉시 적용 → 메모리 효율적

### 5. Math.pow() vs 직접 곱셈
```java
// 비효율적
(int) Math.pow(score, 2);

// 효율적
score * score;
```

## 🔗 관련 패턴
- **문자열 파싱**: day1_2 (신규 아이디 추천)
- **상태 관리**: day2_2 (실패율)
- **배열 활용**: day2_1 (K번째 수)

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐⭐ (문자열 파싱 + 상태 관리)
- **사고력 요구도**: ⭐⭐ (스타상의 이전 영향 파악)
- **실수 가능성**: ⭐⭐⭐ (10점 처리, 인덱스 관리)

---

**Day 3 학습 완료! 문자열 파싱의 핵심 패턴을 마스터했습니다! 🎯**