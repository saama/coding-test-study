# 프로그래머스 Lv1 - 신규 아이디 추천

## 📋 문제 정보
- **문제명**: 신규 아이디 추천
- **플랫폼**: 프로그래머스 Lv1
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/72410
- **파일명**: `day1_2.java`
- **출처**: 2021 KAKAO BLIND RECRUITMENT
- **완료일**: 2024-01-08 (Day 1)

## 🎯 문제 분석

신규 유저가 입력한 아이디를 **7단계 규칙**에 따라 변환하는 문제입니다.

### 7단계 변환 규칙
1. **1단계**: 대문자 → 소문자 변환
2. **2단계**: 허용 문자(알파벳 소문자, 숫자, `-`, `_`, `.`) 외 제거
3. **3단계**: 연속된 마침표(`.`) → 하나의 마침표로 치환
4. **4단계**: 처음/끝 마침표 제거
5. **5단계**: 빈 문자열이면 "a" 대입
6. **6단계**: 16자 이상이면 15자로 자르기 (끝이 `.`이면 제거)
7. **7단계**: 2자 이하면 마지막 문자를 반복해서 3자로 만들기

### 예시
```
입력: "...!@BaT#*..y.abcdefghijklm"
1단계: "...!@bat#*..y.abcdefghijklm"
2단계: "...bat..y.abcdefghijklm"
3단계: ".bat.y.abcdefghijklm"
4단계: "bat.y.abcdefghijklm"
5단계: "bat.y.abcdefghijklm" (변화 없음)
6단계: "bat.y.abcdefghi"
7단계: "bat.y.abcdefghi" (변화 없음)
```

## 🚀 해법 분석

### 1. 기본 해법 (단계별 처리)
```java
public String solution(String new_id) {
    String answer = new_id;
    
    // 1단계: 대문자 → 소문자
    answer = answer.toLowerCase();
    
    // 2단계: 허용 문자 외 제거
    StringBuilder sb = new StringBuilder();
    for (char c : answer.toCharArray()) {
        if (Character.isLowerCase(c) || Character.isDigit(c) || 
            c == '-' || c == '_' || c == '.') {
            sb.append(c);
        }
    }
    answer = sb.toString();
    
    // 3단계: 연속된 마침표 → 하나로 치환
    answer = answer.replaceAll("\\.{2,}", ".");
    
    // 4단계: 처음과 끝 마침표 제거
    if (answer.startsWith(".")) answer = answer.substring(1);
    if (answer.endsWith(".")) answer = answer.substring(0, answer.length() - 1);
    
    // 5단계: 빈 문자열이면 "a"
    if (answer.isEmpty()) answer = "a";
    
    // 6단계: 16자 이상이면 15자로 자르고 끝 마침표 제거
    if (answer.length() >= 16) {
        answer = answer.substring(0, 15);
        if (answer.endsWith(".")) {
            answer = answer.substring(0, answer.length() - 1);
        }
    }
    
    // 7단계: 2자 이하면 마지막 문자 반복
    while (answer.length() <= 2) {
        answer += answer.charAt(answer.length() - 1);
    }
    
    return answer;
}
```

### 2. 정규식 활용 해법 (간결 버전)
```java
public String solutionRegex(String new_id) {
    String answer = new_id
            .toLowerCase()                          // 1단계
            .replaceAll("[^a-z0-9\\-_.]", "")      // 2단계
            .replaceAll("\\.{2,}", ".")            // 3단계
            .replaceAll("^\\.|\\.$", "");          // 4단계

    if (answer.isEmpty()) answer = "a";             // 5단계

    if (answer.length() >= 16) {                    // 6단계
        answer = answer.substring(0, 15);
        answer = answer.replaceAll("\\.$", "");
    }

    while (answer.length() <= 2) {                  // 7단계
        answer += answer.charAt(answer.length() - 1);
    }

    return answer;
}
```

## 🔍 핵심 패턴 분석

### 1. 문자열 처리 메서드
```java
// 대소문자 변환
answer = answer.toLowerCase();

// 문자열 자르기
answer = answer.substring(0, 15);

// 시작/끝 확인
if (answer.startsWith(".")) { ... }
if (answer.endsWith(".")) { ... }

// 특정 위치 문자 접근
char lastChar = answer.charAt(answer.length() - 1);
```

### 2. 정규식 패턴
| 패턴 | 의미 | 예시 |
|------|------|------|
| `[^a-z0-9\\-_.]` | 허용 문자 외 모든 것 | `!@#$%` 제거 |
| `\\.{2,}` | 연속된 점 2개 이상 | `...` → `.` |
| `^\\.|\\.$` | 시작 또는 끝의 점 | `.abc.` → `abc` |

### 3. StringBuilder vs String 연산
```java
// 효율적 - StringBuilder 사용
StringBuilder sb = new StringBuilder();
for (char c : answer.toCharArray()) {
    if (isValid(c)) sb.append(c);
}

// 비효율적 - String 반복 연결
String result = "";
for (char c : answer.toCharArray()) {
    if (isValid(c)) result += c;  // 매번 새로운 String 생성
}
```

## 📊 복잡도 분석

- **시간복잡도**: O(N) - 문자열 길이에 비례
- **공간복잡도**: O(N) - StringBuilder 사용
- **정규식 버전**: 더 간결하지만 약간의 성능 오버헤드

## 🎯 테스트 케이스

| 입력 | 예상 출력 | 검증 포인트 |
|------|----------|------------|
| `"...!@BaT#*..y.abcdefghijklm"` | `"bat.y.abcdefghi"` | 모든 단계 종합 |
| `"z-+.^."` | `"z--"` | 7단계(문자 반복) |
| `"=.="` | `"aaa"` | 5단계(빈 문자열) + 7단계 |
| `"123_.def"` | `"123_.def"` | 변화 없는 경우 |
| `"abcdefghijklmn.p"` | `"abcdefghijklmn"` | 6단계(길이 제한) |

## 💡 학습 포인트

### 1. 문자열 처리 기법
- **Character 클래스**: `isLowerCase()`, `isDigit()` 활용
- **String 메서드**: `toLowerCase()`, `substring()`, `charAt()`
- **StringBuilder**: 효율적인 문자열 조작

### 2. 정규식 마스터
- **문자 클래스**: `[a-z0-9]`, `[^...]` (부정)
- **수량자**: `{2,}` (2개 이상), `+`, `*`
- **위치 지정**: `^` (시작), `$` (끝)
- **이스케이프**: `\\.` (리터럴 점)

### 3. 단계별 처리 패턴
```java
// 명확한 단계 구분
String step1 = input.toLowerCase();
String step2 = step1.replaceAll("[^a-z0-9\\-_.]", "");
String step3 = step2.replaceAll("\\.{2,}", ".");
// ...
```

### 4. 엣지 케이스 처리
- **빈 문자열**: 기본값 설정
- **길이 제한**: substring() 후 재검증
- **최소 길이**: while 루프로 보장

### 5. 코드 최적화
```java
// 일반 버전 - 명확성 우선
if (answer.startsWith(".")) {
    answer = answer.substring(1);
}
if (answer.endsWith(".")) {
    answer = answer.substring(0, answer.length() - 1);
}

// 정규식 버전 - 간결성 우선
answer = answer.replaceAll("^\\.|\\.$", "");
```

## 🔗 관련 패턴
- **문자열 파싱**: day3_1 (다트 게임)
- **정규식 활용**: 문자열 검증 및 치환
- **단계별 처리**: 복잡한 비즈니스 로직 구현

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐ (단계가 명확하게 정의됨)
- **정규식 이해도**: ⭐⭐⭐ (다양한 패턴 활용)
- **실수 가능성**: ⭐⭐ (각 단계가 독립적)

## 🎁 보너스 팁

### 정규식 디버깅
```java
// 단계별 확인용 메서드
public void debugRegex(String input) {
    System.out.println("1단계: " + input.toLowerCase());
    System.out.println("2단계: " + input.replaceAll("[^a-z0-9\\-_.]", ""));
    System.out.println("3단계: " + input.replaceAll("\\.{2,}", "."));
    // ...
}
```

### 성능 최적화
```java
// StringBuilder 패턴 (대용량 문자열 처리시)
StringBuilder sb = new StringBuilder();
for (char c : input.toCharArray()) {
    if (isValidChar(c)) {
        sb.append(c);
    }
}
```

---

**Day 1 완료! 정규식과 문자열 처리의 기본기를 확실히 다졌습니다! 🎯**