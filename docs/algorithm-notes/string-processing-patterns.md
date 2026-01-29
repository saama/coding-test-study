# 문자열 처리 패턴 완벽 가이드

> 코딩테스트에서 자주 나오는 문자열 처리의 핵심 패턴과 주의사항

## 🎯 목차
1. [문자열 치환의 위험성](#문자열-치환의-위험성)
2. [안전한 문자열 조합 패턴](#안전한-문자열-조합-패턴)
3. [문자열 파싱 최적화](#문자열-파싱-최적화)
4. [StringBuilder vs String 연결](#stringbuilder-vs-string-연결)
5. [정규식 vs 직접 처리](#정규식-vs-직접-처리)
6. [실전 문제 패턴](#실전-문제-패턴)

---

## 문자열 치환의 위험성

### ❌ 위험한 패턴: replace()/replaceAll() 남용

#### 문제 상황
```java
// 사용자 ID → 닉네임 치환 (위험!)
for (String id : userMap.keySet()) {
    message = message.replace(id, userMap.get(id));
}
```

#### 실제 버그 사례
```java
// 입력 데이터
Map<String, String> users = {
    "uid1" → "Alice",
    "uid12" → "Bob", 
    "uid123" → "Charlie"
};
String message = "uid123님이 들어왔습니다.";

// 잘못된 처리 과정
message.replace("uid1", "Alice");   // "Alice23님이 들어왔습니다." ❌
message.replace("uid12", "Bob");    // "Alice23님이 들어왔습니다." (변화없음)
message.replace("uid123", "Charlie"); // "Alice23님이 들어왔습니다." (찾을 수 없음)

// 결과: "Alice23님이 들어왔습니다." (잘못됨)
// 예상: "Charlie님이 들어왔습니다."
```

### 🔍 문제 원인 분석

#### 1. 부분 문자열 매칭 문제
```java
"uid123".contains("uid1") // true - 의도치 않은 매칭!
```

#### 2. HashMap 순회 순서 불확정성
```java
// HashMap.keySet() 순회 순서는 보장되지 않음
for (String key : map.keySet()) { ... } // 매번 다른 순서 가능
```

#### 3. 누적 치환 오류
```java
String text = "abc123";
text = text.replace("abc", "XYZ");     // "XYZ123"
text = text.replace("abc123", "DONE"); // "XYZ123" (변화없음)
```

---

## 안전한 문자열 조합 패턴

### ✅ 권장 패턴 1: 직접 조합

#### 기본 조합
```java
// ❌ 위험한 치환
message = message.replace(userId, nickname);

// ✅ 안전한 조합
String result = nickname + "님이 들어왔습니다.";
```

#### 템플릿 활용
```java
// 메시지 템플릿 정의
private static final String ENTER_MSG = "%s님이 들어왔습니다.";
private static final String LEAVE_MSG = "%s님이 나갔습니다.";

// 안전한 메시지 생성
String enterMessage = String.format(ENTER_MSG, nickname);
String leaveMessage = String.format(LEAVE_MSG, nickname);
```

### ✅ 권장 패턴 2: StringBuilder 활용

```java
// 복잡한 문자열 조합
StringBuilder sb = new StringBuilder();
sb.append(nickname)
  .append("님이 ")
  .append(action.equals("Enter") ? "들어왔" : "나갔")
  .append("습니다.");
return sb.toString();
```

### ✅ 권장 패턴 3: MessageFormat 활용

```java
// 다국어 지원 가능한 패턴
String pattern = "{0}님이 {1}습니다.";
String action = isEnter ? "들어왔" : "나갔";
String message = MessageFormat.format(pattern, nickname, action);
```

---

## 문자열 파싱 최적화

### 문제: 반복적인 split() 호출

#### ❌ 비효율적인 패턴
```java
for (String record : records) {
    if (record.split(" ")[0].equals("Enter")) {          // split 1번
        String action = record.split(" ")[0];            // split 2번
        String userId = record.split(" ")[1];            // split 3번  
        String nickname = record.split(" ")[2];          // split 4번
        // 총 4번의 split() 호출!
    }
}
```

#### ✅ 최적화된 패턴
```java
for (String record : records) {
    String[] parts = record.split(" ");  // 1번만 split
    String action = parts[0];
    String userId = parts[1];
    String nickname = parts.length > 2 ? parts[2] : null;
    
    if ("Enter".equals(action)) {
        // 처리...
    }
}
```

### 고급 파싱 패턴

#### StringTokenizer 활용 (성능 중시)
```java
StringTokenizer st = new StringTokenizer(record, " ");
String action = st.nextToken();
String userId = st.nextToken();
String nickname = st.hasMoreTokens() ? st.nextToken() : null;
```

#### indexOf() 활용 (메모리 절약)
```java
int firstSpace = record.indexOf(' ');
int secondSpace = record.indexOf(' ', firstSpace + 1);

String action = record.substring(0, firstSpace);
String userId = record.substring(firstSpace + 1, 
    secondSpace == -1 ? record.length() : secondSpace);
String nickname = secondSpace == -1 ? null : 
    record.substring(secondSpace + 1);
```

---

## StringBuilder vs String 연결

### 성능 비교

#### ❌ String 직접 연결 (느림)
```java
String result = "";
for (String word : words) {
    result += word;  // 매번 새 String 객체 생성
}
// 시간복잡도: O(N²)
```

#### ✅ StringBuilder 사용 (빠름)
```java
StringBuilder sb = new StringBuilder();
for (String word : words) {
    sb.append(word);  // 기존 버퍼에 추가
}
String result = sb.toString();
// 시간복잡도: O(N)
```

### 언제 StringBuilder를 써야 할까?

#### StringBuilder 권장 상황
```java
// 1. 반복문에서 문자열 누적
StringBuilder sb = new StringBuilder();
for (...) {
    sb.append(data);
}

// 2. 조건부 문자열 조합
StringBuilder sb = new StringBuilder(baseName);
if (hasPrefix) sb.insert(0, "prefix_");
if (hasSuffix) sb.append("_suffix");

// 3. 대용량 문자열 처리
StringBuilder sb = new StringBuilder(expectedSize); // 미리 용량 설정
```

#### String 직접 연결 허용 상황
```java
// 1. 간단한 2-3개 조합
String fullName = firstName + " " + lastName;

// 2. 컴파일 타임 최적화 가능
String message = "Hello " + "World";  // 컴파일러가 "Hello World"로 최적화

// 3. String.format() 사용
String formatted = String.format("%s: %d", name, count);
```

---

## 정규식 vs 직접 처리

### 성능과 복잡도 트레이드오프

#### 정규식 사용 (간결하지만 느림)
```java
// 이메일 검증
String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
boolean isValid = email.matches(emailRegex);

// 숫자만 추출
String numbers = text.replaceAll("[^0-9]", "");
```

#### 직접 처리 (복잡하지만 빠름)
```java
// 이메일 검증 (단순화)
boolean isValidEmail(String email) {
    int atIndex = email.indexOf('@');
    int dotIndex = email.lastIndexOf('.');
    return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
}

// 숫자만 추출
StringBuilder numbers = new StringBuilder();
for (char c : text.toCharArray()) {
    if (Character.isDigit(c)) {
        numbers.append(c);
    }
}
```

### 선택 가이드라인

| 상황 | 권장 방법 | 이유 |
|------|-----------|------|
| **간단한 패턴** | 직접 처리 | 성능 우수 |
| **복잡한 패턴** | 정규식 | 코드 간결성 |
| **일회성 사용** | 정규식 | 개발 속도 |
| **반복적 사용** | 직접 처리 | 성능 최적화 |
| **검증 로직** | 정규식 | 정확성 보장 |

---

## 실전 문제 패턴

### 패턴 1: 로그 파싱 및 변환

#### 문제 유형
- 채팅방 입출입 로그
- 서버 접속 기록
- 게임 플레이 로그

#### 핵심 해결 전략
```java
// 1단계: 최종 상태 수집
Map<String, String> finalState = new HashMap<>();
for (String record : records) {
    String[] parts = record.split(" ");
    if (parts[0].equals("UPDATE")) {
        finalState.put(parts[1], parts[2]);
    }
}

// 2단계: 결과 생성 (치환 없이 조합)
List<String> results = new ArrayList<>();
for (String record : records) {
    String[] parts = record.split(" ");
    if (parts[0].equals("ACTION")) {
        String finalValue = finalState.get(parts[1]);
        results.add(generateMessage(parts[0], finalValue));
    }
}
```

### 패턴 2: 문자열 조합 최적화

#### 문제 유형
- 가장 큰 수 만들기
- URL 경로 최적화
- 문자열 사전 생성

#### 핵심 해결 전략
```java
// 조합 비교를 통한 정렬
Arrays.sort(strings, (a, b) -> (b + a).compareTo(a + b));

// 결과 생성
StringBuilder result = new StringBuilder();
for (String str : strings) {
    result.append(str);
}
return result.toString();
```

### 패턴 3: 다중 조건 문자열 정렬

#### 문제 유형
- 단어 정렬 (길이 → 사전순)
- 파일명 정렬 (확장자 → 이름)
- 학생 정렬 (성적 → 이름)

#### 핵심 해결 전략
```java
Arrays.sort(items, (a, b) -> {
    // 1차 기준: 길이
    if (a.length() != b.length()) {
        return a.length() - b.length();
    }
    // 2차 기준: 사전순
    return a.compareTo(b);
});
```

---

## 💡 최적화 팁

### 1. 문자열 불변성 활용
```java
// String 객체는 불변 → 안전한 참조 공유 가능
String template = "님이 들어왔습니다.";
// 모든 곳에서 같은 template 재사용
```

### 2. 상수 문자열 미리 정의
```java
private static final String ENTER_SUFFIX = "님이 들어왔습니다.";
private static final String LEAVE_SUFFIX = "님이 나갔습니다.";

// 런타임에서 문자열 생성 비용 절약
```

### 3. StringBuilder 초기 용량 설정
```java
// ❌ 기본 용량 (16) - 확장 오버헤드 발생
StringBuilder sb = new StringBuilder();

// ✅ 예상 크기로 초기화 - 성능 최적화
StringBuilder sb = new StringBuilder(expectedSize);
```

### 4. String.intern() 활용 (메모리 절약)
```java
// 같은 문자열이 대량 반복될 때
String action = parts[0].intern(); // String pool에서 관리
```

---

## 🔗 관련 문제 유형

### 문자열 치환 패턴
1. **프로그래머스 Lv2 - 오픈채팅방**: ID → 닉네임 안전 치환
2. **백준 1316번 - 그룹 단어 체커**: 문자열 패턴 검증
3. **프로그래머스 Lv1 - 신규 아이디 추천**: 다단계 문자열 변환

### 문자열 조합 패턴
1. **프로그래머스 Lv2 - 가장 큰 수**: 조합 비교 정렬
2. **백준 1181번 - 단어 정렬**: 다중 조건 정렬
3. **프로그래머스 Lv2 - 파일명 정렬**: 복합 기준 정렬

### 문자열 파싱 패턴
1. **백준 20291번 - 파일 정리**: 확장자별 분류
2. **프로그래머스 Lv2 - 압축**: 문자열 압축 알고리즘
3. **백준 1764번 - 듣보잡**: 문자열 교집합

---

## 📝 체크리스트

### 코드 작성 전 확인사항
- [ ] 부분 문자열 매칭 위험성 검토
- [ ] split() 호출 횟수 최적화
- [ ] StringBuilder vs String 연결 선택
- [ ] 정규식 vs 직접 처리 성능 비교
- [ ] 메모리 사용량 예상

### 디버깅 시 확인사항
- [ ] 예상과 다른 치환 결과 확인
- [ ] HashMap 순회 순서 의존성 점검
- [ ] 문자열 길이 변화 추적
- [ ] null/empty 문자열 처리
- [ ] 인코딩 문제 확인

**문자열 처리는 세심함이 생명입니다. 항상 예외 케이스를 고려하세요!** ✨