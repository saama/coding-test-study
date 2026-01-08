# 정규식(Regex) 패턴 완벽 가이드

> 코딩테스트에서 자주 사용되는 정규식 패턴과 Java에서의 활용법

## 🎯 정규식이란?

정규식(Regular Expression)은 문자열의 패턴을 정의하는 표현식으로, 문자열 검색, 치환, 검증에 사용됩니다.

**Java에서 주요 메서드:**
- `String.matches(regex)`: 패턴 매칭 검사
- `String.replaceAll(regex, replacement)`: 패턴에 해당하는 부분을 교체
- `String.split(regex)`: 패턴을 기준으로 문자열 분할

---

## 📋 기본 문법

### 1. 문자 클래스 `[...]`

| 패턴 | 의미 | 예시 |
|------|------|------|
| `[abc]` | a, b, c 중 하나 | "hello".replaceAll("[el]", "X") → "hXXXo" |
| `[a-z]` | a부터 z까지 소문자 | "Hello123".replaceAll("[a-z]", "X") → "HXXXX123" |
| `[A-Z]` | A부터 Z까지 대문자 | "Hello".replaceAll("[A-Z]", "X") → "Xello" |
| `[0-9]` | 0부터 9까지 숫자 | "abc123".replaceAll("[0-9]", "X") → "abcXXX" |
| `[a-zA-Z]` | 모든 영문자 | "Hello123".replaceAll("[a-zA-Z]", "X") → "XXXXX123" |
| `[^abc]` | a, b, c가 아닌 문자 | "hello".replaceAll("[^el]", "X") → "XellX" |

### 2. 미리 정의된 문자 클래스

| 패턴 | 동등한 표현 | 의미 | 예시 |
|------|-------------|------|------|
| `\\d` | `[0-9]` | 숫자 | "abc123".replaceAll("\\d", "X") → "abcXXX" |
| `\\w` | `[a-zA-Z0-9_]` | 단어 문자 | "hello_123!".replaceAll("\\w", "X") → "XXXXXXXXX!" |
| `\\s` | `[ \\t\\n\\r]` | 공백 문자 | "a b\\tc".replaceAll("\\s", "X") → "aXbXc" |
| `.` | - | 개행 제외 모든 문자 | "a.b".replaceAll(".", "X") → "XXX" |

### 3. 수량자 `{}, *, +, ?`

| 패턴 | 의미 | 예시 |
|------|------|------|
| `{n}` | 정확히 n번 | "aaa".replaceAll("a{2}", "X") → "Xa" |
| `{n,}` | n번 이상 | "aaa".replaceAll("a{2,}", "X") → "X" |
| `{n,m}` | n번 이상 m번 이하 | "aaaa".replaceAll("a{2,3}", "X") → "Xa" |
| `*` | 0번 이상 `{0,}` | "aaa".replaceAll("a*", "X") → "X" |
| `+` | 1번 이상 `{1,}` | "aaa".replaceAll("a+", "X") → "X" |
| `?` | 0번 또는 1번 `{0,1}` | "ab".replaceAll("a?b", "X") → "X" |

### 4. 위치 지정자 `^, $`

| 패턴 | 의미 | 예시 |
|------|------|------|
| `^` | 문자열 시작 | "hello".replaceAll("^h", "H") → "Hello" |
| `$` | 문자열 끝 | "hello".replaceAll("o$", "O") → "hellO" |

### 5. 그룹화 `(), |`

| 패턴 | 의미 | 예시 |
|------|------|------|
| `(abc)` | 그룹화 | "abc123".replaceAll("(abc)", "[$1]") → "[abc]123" |
| `\|` | OR 연산 | "cat".replaceAll("cat\|dog", "animal") → "animal" |

---

## 🎯 코딩테스트 실전 패턴

### 1. 신규 아이디 추천 (프로그래머스)

```java
public String solution(String new_id) {
    return new_id
        .toLowerCase()                          // 1단계: 소문자 변환
        .replaceAll("[^a-z0-9\\-_.]", "")      // 2단계: 허용문자 외 제거
        .replaceAll("\\.{2,}", ".")            // 3단계: 연속 점을 하나로
        .replaceAll("^\\.|\\.$", "");          // 4단계: 앞뒤 점 제거
        // ... 5,6,7단계 생략
}
```

**패턴 분석:**
- `[^a-z0-9\\-_.]`: a-z, 0-9, -, _, . 를 **제외한** 모든 문자
- `\\.{2,}`: 연속된 점 2개 이상 (`\\.`는 리터럴 점)
- `^\\.|\\.$`: 문자열 시작의 점 **또는** 끝의 점

### 2. 괄호 변환 (카카오)

```java
// 올바른 괄호 판별
public boolean isValid(String p) {
    return !p.matches(".*\\).*\\(.*"); // ')' 다음에 '('가 오면 잘못된 괄호
}
```

### 3. 문자열 압축 (카카오)

```java
// 숫자 추출
String s = "3a2bc4d";
String[] parts = s.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
// 결과: ["3", "a", "2", "bc", "4", "d"]
```

### 4. 파일명 정렬 (카카오)

```java
// 파일명에서 숫자 부분 추출
Pattern pattern = Pattern.compile("([a-zA-Z\\s\\.-]+)(\\d+)(.*)");
Matcher matcher = pattern.matcher("img12.png");
if (matcher.matches()) {
    String head = matcher.group(1);    // "img"
    int number = Integer.parseInt(matcher.group(2)); // 12
    String tail = matcher.group(3);    // ".png"
}
```

---

## 💡 문제 유형별 정규식 패턴

### 1. 데이터 검증

```java
// 이메일 검증 (간단 버전)
String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
boolean isValidEmail = email.matches(emailPattern);

// 전화번호 검증 (010-XXXX-XXXX)
String phonePattern = "^010-\\d{4}-\\d{4}$";
boolean isValidPhone = phone.matches(phonePattern);

// 비밀번호 검증 (8자 이상, 영문+숫자 포함)
String pwdPattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
boolean isValidPassword = password.matches(pwdPattern);
```

### 2. 데이터 추출

```java
// 숫자만 추출
String price = "가격: 15,500원";
String numbers = price.replaceAll("[^0-9]", ""); // "15500"

// 한글만 추출  
String text = "Hello안녕123세상";
String korean = text.replaceAll("[^가-힣]", ""); // "안녕세상"

// URL에서 도메인 추출
String url = "https://www.example.com/path";
String domain = url.replaceAll("^https?://([^/]+).*", "$1"); // "www.example.com"
```

### 3. 데이터 정제

```java
// 연속 공백을 하나로
String text = "hello    world";
text = text.replaceAll("\\s+", " "); // "hello world"

// HTML 태그 제거
String html = "<p>Hello <b>World</b></p>";
String plain = html.replaceAll("<[^>]*>", ""); // "Hello World"

// 특수문자 제거 (영문, 숫자, 공백만 남기기)
String dirty = "Hello@#$123 World!";
String clean = dirty.replaceAll("[^a-zA-Z0-9\\s]", ""); // "Hello123 World"
```

### 4. 형식 변환

```java
// 전화번호 형식 변환
String phone = "01012345678";
phone = phone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3"); // "010-1234-5678"

// 카멜케이스 → 스네이크케이스
String camel = "userName";
String snake = camel.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(); // "user_name"

// 날짜 형식 변환 (YYYY-MM-DD → YYYY/MM/DD)
String date = "2023-12-25";
date = date.replaceAll("-", "/"); // "2023/12/25"
```

---

## 🔧 고급 패턴

### 1. 전후방 탐색 (Lookahead/Lookbehind)

```java
// 양의 전방 탐색 (?=...)
String text = "Java JavaScript Python";
// 'a' 뒤에 'S'가 오는 경우의 'a'만 매칭
text.replaceAll("a(?=S)", "X"); // "Java JXvaScript Python"

// 양의 후방 탐색 (?<=...)  
// 'J' 뒤에 오는 'a'만 매칭
text.replaceAll("(?<=J)a", "X"); // "JXva JXvaScript Python"
```

### 2. 비탐욕적 수량자

```java
// 탐욕적 (기본)
String html = "<div>Hello</div><div>World</div>";
html.replaceAll("<.*>", "X"); // "X" (전체가 하나의 태그로 인식)

// 비탐욕적 (?)
html.replaceAll("<.*?>", ""); // "HelloWorld" (각 태그별로 인식)
```

### 3. 그룹 캡처와 참조

```java
// 그룹 캡처 ()와 역참조 $1, $2
String date = "2023-12-25";
date = date.replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1"); // "25/12/2023"

// 중복 단어 찾기
String text = "hello hello world world";
text.replaceAll("(\\b\\w+)\\s+\\1", "$1"); // "hello world"
```

---

## ⚠️ 정규식 주의사항

### 1. 이스케이프 처리

```java
// ❌ 잘못된 예
"a.b".replaceAll(".", "X");     // "XXX" (점이 모든 문자와 매칭)
"a$b".replaceAll("$", "X");     // "a$b" ($ 문자 끝 의미로 해석)

// ✅ 올바른 예
"a.b".replaceAll("\\.", "X");   // "aXb"
"a$b".replaceAll("\\$", "X");   // "aXb"
```

### 2. 성능 고려사항

```java
// ❌ 복잡한 정규식은 느림
String complexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

// ✅ 간단한 체크로 대체 가능
boolean hasLower = str.chars().anyMatch(Character::isLowerCase);
boolean hasUpper = str.chars().anyMatch(Character::isUpperCase);
boolean hasDigit = str.chars().anyMatch(Character::isDigit);
```

### 3. 가독성 vs 성능

```java
// 가독성 중심
String clean = text.replaceAll("[^a-zA-Z0-9]", "")
                  .replaceAll("\\s+", " ")
                  .trim();

// 성능 중심
StringBuilder sb = new StringBuilder();
for (char c : text.toCharArray()) {
    if (Character.isLetterOrDigit(c) || c == ' ') {
        sb.append(c);
    }
}
String clean = sb.toString().replaceAll("\\s+", " ").trim();
```

---

## 🚀 정규식 연습 문제

### 초급
1. 문자열에서 모든 숫자를 '*'로 치환하기
2. 이메일에서 도메인 부분만 추출하기  
3. 전화번호에서 하이픈 제거하기

### 중급
4. HTML 태그를 모두 제거하기
5. 연속된 공백을 하나의 공백으로 만들기
6. 카멜케이스를 스네이크케이스로 변환하기

### 고급
7. 괄호가 올바르게 매칭되는지 확인하기
8. 비밀번호 복잡성 검증 (대소문자+숫자+특수문자 포함)
9. URL에서 각 구성요소(프로토콜, 도메인, 경로) 추출하기

### 정답
```java
// 1. 숫자를 *로 치환
text.replaceAll("\\d", "*");

// 2. 이메일에서 도메인 추출
email.replaceAll(".*@", "");

// 3. 전화번호에서 하이픈 제거
phone.replaceAll("-", "");

// 4. HTML 태그 제거
html.replaceAll("<[^>]*>", "");

// 5. 연속 공백을 하나로
text.replaceAll("\\s+", " ");

// 6. 카멜케이스 → 스네이크케이스
camel.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();

// 7. 괄호 매칭 (간단 버전)
!str.matches(".*\\).*\\(.*");

// 8. 비밀번호 검증
pwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

// 9. URL 구성요소 추출
Pattern.compile("^(https?)://([^/]+)(.*)$");
```

---

## 📚 참고 자료

- [Oracle Java Pattern 공식 문서](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
- [정규식 테스트 사이트](https://regex101.com/)
- [정규식 시각화 도구](https://regexper.com/)

**Remember**: 정규식은 강력하지만 가독성을 해치지 않는 선에서 사용하기! 🎯