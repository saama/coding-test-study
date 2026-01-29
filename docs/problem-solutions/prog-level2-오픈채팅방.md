# 프로그래머스 Lv2 - 오픈채팅방

**난이도**: Level 2  
**링크**: https://school.programmers.co.kr/learn/courses/30/lessons/42888  
**태그**: HashMap, 문자열 처리, 2단계 처리, 안전한 치환  

## 문제 요약
채팅방에 들어오고 나가거나, 닉네임을 변경하는 기록이 담긴 문자열 배열 record가 매개변수로 주어질 때, 모든 기록이 처리된 후 최종 출력될 메시지를 문자열 배열 형태로 반환하라.

**입력**: `["Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"]`  
**출력**: `["Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."]`

## 핵심 아이디어
1. **2단계 처리**: 먼저 최종 닉네임을 확정하고, 그 다음 메시지 생성
2. **안전한 문자열 조합**: replace() 대신 직접 문자열 조합 사용
3. **명령어 분류**: Enter/Leave(출력 대상) vs Change(닉네임 업데이트만)

## 주요 함정과 해결책

### 🚨 함정 1: 문자열 치환의 위험성

#### ❌ 위험한 원본 접근법
```java
// 문제가 있는 코드 패턴
for(String id : lastNickName.keySet()) {
    word = word.replace(id, lastNickName.get(id)); // 위험!
}
```

**문제 시나리오:**
- 사용자 ID: `uid1`, `uid12`, `uid123`  
- 메시지: `"uid123님이 들어왔습니다."`
- `uid1` 치환 시: `"Alice23님이 들어왔습니다."` (부분 치환!)
- `uid123`을 찾을 수 없게 되어 잘못된 결과

#### ✅ 안전한 해결책
```java
// 직접 문자열 조합
result.add(userMap.get(userId) + "님이 들어왔습니다.");
```

### 🚨 함정 2: 성능 문제

#### ❌ 비효율적 패턴
```java
// split() 반복 호출
if(record[i].split(" ")[0].equals("Enter")) {          // split 1번
    String action = record[i].split(" ")[0];            // split 2번
    String userId = record[i].split(" ")[1];            // split 3번
    String nickname = record[i].split(" ")[2];          // split 4번
}
```

#### ✅ 최적화된 패턴
```java
String[] parts = record[i].split(" ");  // 1번만 split
String action = parts[0];
String userId = parts[1];
String nickname = parts.length > 2 ? parts[2] : null;
```

## 최종 해법들

### 해법 1: 기본 2단계 처리 (권장)

```java
public String[] solution(String[] record) {
    // 1단계: 최종 닉네임 수집
    Map<String, String> userMap = new HashMap<>();
    for (String rec : record) {
        String[] parts = rec.split(" ");
        String action = parts[0];
        String userId = parts[1];
        
        if (action.equals("Enter") || action.equals("Change")) {
            String nickname = parts[2];
            userMap.put(userId, nickname); // 최종 닉네임만 저장
        }
    }
    
    // 2단계: 출력 메시지 생성 (Enter, Leave만)
    List<String> result = new ArrayList<>();
    for (String rec : record) {
        String[] parts = rec.split(" ");
        String action = parts[0];
        String userId = parts[1];
        
        if (action.equals("Enter")) {
            result.add(userMap.get(userId) + "님이 들어왔습니다.");
        } else if (action.equals("Leave")) {
            result.add(userMap.get(userId) + "님이 나갔습니다.");
        }
        // Change는 출력하지 않음
    }
    
    return result.toArray(new String[0]);
}
```

### 해법 2: 액션 객체 분리 (메모리 최적화)

```java
public String[] solution(String[] record) {
    Map<String, String> userMap = new HashMap<>();
    List<String[]> actions = new ArrayList<>(); // 액션 정보만 저장
    
    // 1단계: 파싱과 동시에 처리
    for (String rec : record) {
        String[] parts = rec.split(" ");
        String action = parts[0];
        String userId = parts[1];
        
        if (action.equals("Enter") || action.equals("Change")) {
            userMap.put(userId, parts[2]); // 닉네임 업데이트
        }
        
        // Enter, Leave만 결과에 포함
        if (action.equals("Enter") || action.equals("Leave")) {
            actions.add(new String[]{action, userId});
        }
    }
    
    // 2단계: 최종 닉네임으로 메시지 생성
    String[] result = new String[actions.size()];
    for (int i = 0; i < actions.size(); i++) {
        String action = actions.get(i)[0];
        String userId = actions.get(i)[1];
        String nickname = userMap.get(userId);
        
        if (action.equals("Enter")) {
            result[i] = nickname + "님이 들어왔습니다.";
        } else { // Leave
            result[i] = nickname + "님이 나갔습니다.";
        }
    }
    
    return result;
}
```

### 해법 3: Stream API 활용 (함수형 스타일)

```java
public String[] solution(String[] record) {
    // 최종 닉네임 수집
    Map<String, String> userMap = Arrays.stream(record)
        .map(rec -> rec.split(" "))
        .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Change"))
        .collect(Collectors.toMap(
            parts -> parts[1],          // key: userId
            parts -> parts[2],          // value: nickname
            (old, new) -> new           // 중복 시 새 값 사용
        ));
    
    // 결과 생성
    return Arrays.stream(record)
        .map(rec -> rec.split(" "))
        .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Leave"))
        .map(parts -> {
            String action = parts[0];
            String userId = parts[1];
            String nickname = userMap.get(userId);
            
            if (action.equals("Enter")) {
                return nickname + "님이 들어왔습니다.";
            } else {
                return nickname + "님이 나갔습니다.";
            }
        })
        .toArray(String[]::new);
}
```

## 알고리즘 분석

### 핵심 패턴: 2단계 처리
```java
// 1단계: 최종 상태 수집
Map<String, String> finalState = new HashMap<>();
for (record : records) {
    if (isUpdateOperation(record)) {
        finalState.put(key, newValue); // 최신 상태만 유지
    }
}

// 2단계: 결과 생성 (치환 없이 조합)
List<String> results = new ArrayList<>();
for (record : records) {
    if (isOutputOperation(record)) {
        results.add(generateMessage(record, finalState));
    }
}
```

### 시간/공간 복잡도
- **시간복잡도**: O(N) (각 레코드를 최대 2번 순회)
- **공간복잡도**: O(U + M) (U: 고유 사용자 수, M: 출력 메시지 수)

## 실수하기 쉬운 포인트

### 1. 부분 문자열 치환 위험
```java
// ❌ 위험: 부분 문자열이 치환될 수 있음
message.replace(userId, nickname);

// ✅ 안전: 직접 조합
nickname + "님이 들어왔습니다.";
```

### 2. HashMap 순회 순서 의존성
```java
// ❌ 위험: HashMap의 keySet() 순회 순서는 보장되지 않음
for (String key : map.keySet()) {
    message = message.replace(key, map.get(key));
}
```

### 3. Change 명령어 출력 처리
```java
// Change 명령어는 닉네임만 업데이트, 출력하지 않음
if (action.equals("Enter")) {
    // 출력 + 닉네임 업데이트
} else if (action.equals("Leave")) {
    // 출력만
} else if (action.equals("Change")) {
    // 닉네임 업데이트만, 출력 없음
}
```

### 4. split() 성능 최적화
```java
// ❌ 비효율적: 매번 split() 호출
if (record[i].split(" ")[0].equals("Enter")) {
    String[] parts = record[i].split(" ");
    // ...
}

// ✅ 효율적: 한 번만 split() 호출
String[] parts = record[i].split(" ");
if (parts[0].equals("Enter")) {
    // ...
}
```

## 확장 가능한 패턴

### 메시지 템플릿 활용
```java
private static final String ENTER_MSG = "%s님이 들어왔습니다.";
private static final String LEAVE_MSG = "%s님이 나갔습니다.";

// 메시지 생성
String message = String.format(
    action.equals("Enter") ? ENTER_MSG : LEAVE_MSG, 
    nickname
);
```

### Enum 활용한 명령어 처리
```java
enum Action {
    ENTER("Enter", true),
    LEAVE("Leave", true),
    CHANGE("Change", false);
    
    private final String command;
    private final boolean needsOutput;
    
    Action(String command, boolean needsOutput) {
        this.command = command;
        this.needsOutput = needsOutput;
    }
    
    public static Action from(String command) {
        return Arrays.stream(values())
            .filter(action -> action.command.equals(command))
            .findFirst()
            .orElseThrow();
    }
}
```

## 학습 포인트

### 1. 문자열 처리 안전성
- **replace() 위험성**: 부분 문자열 매칭 문제
- **안전한 조합**: 직접 문자열 연결이 더 안전
- **성능 최적화**: split() 결과 재사용

### 2. 2단계 처리 패턴
- **상태 수집 단계**: 최종 상태만 관리
- **결과 생성 단계**: 수집된 상태로 안전하게 결과 생성
- **분리의 이점**: 코드 가독성과 안전성 향상

### 3. HashMap 활용
- **자동 업데이트**: `put()`으로 최신 값 자동 관리
- **O(1) 접근**: 빠른 사용자 정보 조회
- **순회 주의**: keySet() 순서는 보장되지 않음

### 4. Stream vs 전통적 방식
- **Stream**: 함수형 스타일, 간결한 코드
- **전통적**: 명확한 제어 흐름, 디버깅 용이
- **선택 기준**: 팀 코딩 스타일과 성능 요구사항

## 관련 문제
- **백준 1764번**: 듣보잡 (문자열 교집합)
- **프로그래머스 Lv1**: 신규 아이디 추천 (다단계 문자열 변환)
- **백준 20291번**: 파일 정리 (확장자별 분류 및 카운팅)

**이 문제의 핵심은 '안전한 문자열 처리'와 '2단계 처리 패턴'입니다!** 🔒✨