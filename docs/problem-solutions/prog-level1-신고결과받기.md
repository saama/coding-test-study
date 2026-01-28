# 프로그래머스 Lv1 - 신고 결과 받기

## 📋 문제 정보
- **문제명**: 신고 결과 받기
- **플랫폼**: 프로그래머스 Lv1
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/92334
- **파일명**: `day4_1.java`
- **출처**: 2022 KAKAO BLIND RECRUITMENT
- **완료일**: 2024-01-20 (Day 4)

## 🎯 문제 분석

신고 시스템에서 각 사용자가 받게 될 메일 개수를 계산하는 문제입니다.

### 핵심 규칙
1. **중복 신고 처리**: 같은 유저가 같은 유저를 여러 번 신고해도 **1번만 처리**
2. **정지 기준**: k번 이상 신고당하면 **게시판 이용 정지**
3. **메일 발송**: 신고한 유저가 정지되면 **신고자에게 메일 발송**

### 예시
```
id_list = ["muzi", "frodo", "apeach", "neo"]
report = ["muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"]
k = 2

신고 관계:
- muzi → frodo, neo
- apeach → frodo, muzi  
- frodo → neo

신고당한 횟수:
- frodo: 2번 (muzi, apeach) → 정지 ✅
- neo: 2번 (muzi, frodo) → 정지 ✅
- muzi: 1번 (apeach) → 정지 안됨

메일 발송:
- muzi: frodo(정지), neo(정지) → 2개
- apeach: frodo(정지) → 1개
- frodo: neo(정지) → 1개  
- neo: 0개

결과: [2, 1, 1, 0]
```

## 💡 사용자 원본 코드 분석

```java
public int[] solution(String[] id_list, String[] report, int k) {
    Map<String, Set<String>> callers = new HashMap<>();     // 신고자별 신고 대상
    Map<String, Integer> banneder = new HashMap<>();        // 피신고자별 신고당한 횟수  
    Map<String, Integer> reciever = new HashMap<>();        // 신고자별 받을 메일 수
    
    for (int i = 0; i < id_list.length; i++) {
        for (int j = 0; j < report.length; j++) {
            if(report[j].split(" ")[0].equals(id_list[i])) {
                callers.put(id_list[i], Set.of(report[j].split(" ")[1])); // Set.of() 문제!
            }
        }
    }
    
    for (int j = 0; j < report.length; j++) {
        banneder.put(report[j].split(" ")[1], banneder.getOrDefault(report[j].split(" ")[1], 0) + 1);
    }
    
    for(String name : banneder.keySet()){
        if(banneder.get(name) >= k){
            for(String caller : callers.keySet()){
                if(callers.get(caller).contains(name)){
                    reciever.put(caller, reciever.getOrDefault(caller, 0) + 1);
                }
            }
        }
    }
    
    return answer; // 빈 배열 반환
}
```

**✅ 사용자 원본 코드 평가:**
- **핵심 아이디어**: Map 3개를 활용한 체계적 접근 - 매우 뛰어난 구조적 사고!
- **getOrDefault 패턴**: null 안전 프로그래밍 완벽 적용
- **문제 해결 단계**: 신고 관계 → 신고 횟수 → 메일 개수의 명확한 3단계 처리

**🔍 주요 문제점:**
1. **Set.of() 오용**: 매번 새로운 단일 요소 Set으로 덮어씀 (중복 제거 안됨)
2. **이중 반복문**: O(N²) 시간복잡도 (비효율적)
3. **결과 배열 누락**: 빈 배열 반환으로 결과 미완성
4. **중복 신고 미처리**: report 배열의 중복 신고가 그대로 카운트됨

## 🚀 개선된 해법들

### 1. Set 올바른 사용 + 중복 제거
```java
public int[] solutionOptimized(String[] id_list, String[] report, int k) {
    // 1단계: 중복 신고 제거
    Set<String> reportSet = new HashSet<>(Arrays.asList(report));
    
    Map<String, Set<String>> reportMap = new HashMap<>();
    Map<String, Integer> reportedCount = new HashMap<>();
    
    // 2단계: 신고 관계 및 신고당한 횟수 집계
    for (String rep : reportSet) {
        String[] parts = rep.split(" ");
        String reporter = parts[0];
        String reported = parts[1];
        
        // computeIfAbsent로 안전한 Set 관리
        reportMap.computeIfAbsent(reporter, k1 -> new HashSet<>()).add(reported);
        reportedCount.put(reported, reportedCount.getOrDefault(reported, 0) + 1);
    }
    
    // 3단계: 정지된 유저 찾기
    Set<String> bannedUsers = new HashSet<>();
    for (String user : reportedCount.keySet()) {
        if (reportedCount.get(user) >= k) {
            bannedUsers.add(user);
        }
    }
    
    // 4단계: 각 유저별 메일 개수 계산
    int[] answer = new int[id_list.length];
    for (int i = 0; i < id_list.length; i++) {
        String user = id_list[i];
        Set<String> reportedByUser = reportMap.getOrDefault(user, new HashSet<>());
        
        int mailCount = 0;
        for (String reported : reportedByUser) {
            if (bannedUsers.contains(reported)) {
                mailCount++;
            }
        }
        answer[i] = mailCount;
    }
    
    return answer;
}
```

### 2. 스트림 기반 함수형 해법
```java
public int[] solutionStream(String[] id_list, String[] report, int k) {
    // 중복 제거 및 파싱
    List<String[]> reports = Arrays.stream(report)
            .distinct()
            .map(r -> r.split(" "))
            .collect(Collectors.toList());
    
    // 신고당한 횟수 계산
    Map<String, Long> reportedCount = reports.stream()
            .collect(Collectors.groupingBy(
                r -> r[1],
                Collectors.counting()
            ));
    
    // 정지된 유저 찾기
    Set<String> bannedUsers = reportedCount.entrySet().stream()
            .filter(entry -> entry.getValue() >= k)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    
    // 각 유저별 메일 개수 계산
    return Arrays.stream(id_list)
            .mapToInt(user -> (int) reports.stream()
                    .filter(r -> r[0].equals(user) && bannedUsers.contains(r[1]))
                    .count())
            .toArray();
}
```

## 🔍 핵심 패턴 분석

### 1. Set.of() vs computeIfAbsent() 비교
```java
// ❌ 원본의 문제점
callers.put(id_list[i], Set.of(report[j].split(" ")[1]));
// → 매번 새로운 단일 요소 Set으로 덮어씀

// ✅ 올바른 방법
reportMap.computeIfAbsent(reporter, k -> new HashSet<>()).add(reported);
// → 키가 없으면 HashSet 생성, 있으면 기존 Set에 추가 (중복 자동 제거)
```

### 2. 중복 제거 패턴
```java
// 방법 1: HashSet으로 사전 제거
Set<String> reportSet = new HashSet<>(Arrays.asList(report));

// 방법 2: Stream distinct()
Arrays.stream(report).distinct().forEach(...)

// 방법 3: Set 자료구조 자체로 중복 제거
Map<String, Set<String>> reportMap = new HashMap<>(); // Set이 중복 자동 제거
```

### 3. Map + Set 조합 패턴
```java
// 1:N 관계를 표현하는 표준 패턴
Map<String, Set<String>> userFollowers = new HashMap<>();    // 팔로워 관계
Map<String, Set<String>> categoryProducts = new HashMap<>(); // 카테고리-상품 관계
Map<String, Set<String>> rolePermissions = new HashMap<>();  // 역할-권한 관계
```

## 📊 성능 비교

| 접근법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|--------|------------|------------|------|------|
| 원본 (수정 전) | O(N×M×K) | O(N) | 직관적 구조 | Set 오용, 중복 미처리 |
| 개선 (Set 활용) | O(N+M) | O(N) | 최적 성능, 명확한 의도 | - |
| 스트림 | O(N+M) | O(N) | 함수형 스타일 | 가독성 차이 |

*N: report 길이, M: id_list 길이*

## 💡 학습 포인트

### 1. Set 자료구조의 핵심 가치
```java
// 중복 제거가 핵심인 문제에서 Set은 필수!
Set<String> uniqueReports = new HashSet<>();  // 자동 중복 제거
Set<String> bannedUsers = new HashSet<>();    // O(1) contains() 연산
```

### 2. computeIfAbsent() 마스터
```java
// 전통적인 방법 (길고 실수 가능)
if (!map.containsKey(key)) {
    map.put(key, new HashSet<>());
}
map.get(key).add(value);

// 현대적인 방법 (간결하고 안전)
map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
```

### 3. 문제 분해 능력
```java
// 복잡한 문제를 단계별로 분해
// 1단계: 중복 제거
// 2단계: 관계 구축
// 3단계: 조건 필터링  
// 4단계: 결과 계산
```

### 4. 자료구조 선택 기준
```java
// 중복 제거 필요 → Set
// 빠른 조회 필요 → HashMap
// 순서 보장 필요 → LinkedHashMap/ArrayList
// 1:N 관계 표현 → Map<K, Set<V>>
```

## 🎯 테스트 케이스

| id_list | report | k | 예상 결과 | 검증 포인트 |
|---------|--------|---|----------|------------|
| `["muzi","frodo","apeach","neo"]` | `["muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"]` | `2` | `[2,1,1,0]` | 기본 케이스 |
| `["con","ryan"]` | `["ryan con","ryan con","ryan con","ryan con"]` | `3` | `[0,0]` | 중복 신고 처리 |
| `["a","b","c"]` | `["a b","b c","c a"]` | `1` | `[1,1,1]` | 모든 유저 정지 |

## 🔗 관련 개념

### Set 컬렉션 심화
- **HashSet**: O(1) 평균 시간, 순서 보장 안함
- **LinkedHashSet**: O(1) 평균 시간, 삽입 순서 보장  
- **TreeSet**: O(log N) 시간, 자동 정렬

### 실무 적용 사례
```java
// 사용자 권한 관리
Map<String, Set<String>> userPermissions = new HashMap<>();

// 상품 카테고리 관리
Map<String, Set<String>> categoryProducts = new HashMap<>();

// 팔로우 관계 관리
Map<String, Set<String>> userFollowers = new HashMap<>();
```

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐⭐ (Map + Set 조합 이해 필요)
- **자료구조 선택**: ⭐⭐⭐⭐ (적절한 자료구조 선택 중요)
- **실수 가능성**: ⭐⭐⭐ (Set 사용법, 중복 처리)

## 🎁 보너스 팁

### 디버깅용 출력
```java
System.out.println("신고 관계: " + reportMap);
System.out.println("신고 횟수: " + reportedCount);  
System.out.println("정지 유저: " + bannedUsers);
```

### 성능 최적화
```java
// Set 초기 용량 설정
Set<String> reportSet = new HashSet<>(report.length);

// StringBuilder로 문자열 최적화 (많은 split() 호출시)
String[] parts = rep.split(" ", 2); // 최대 2개로 제한
```

### 확장 가능한 설계
```java
// 신고 사유별 분류 (확장 버전)
Map<String, Map<String, Set<String>>> reportByReason = new HashMap<>();
```

## 🎓 Set.of() vs HashSet 완전 정리

### Set.of() - 불변 집합 (Java 9+)
```java
Set<String> fixed = Set.of("a", "b", "c");  // 고정된 요소들
// fixed.add("d");  // ❌ UnsupportedOperationException
```

### HashSet - 가변 집합
```java
Set<String> mutable = new HashSet<>();
mutable.add("a");      // ✅ 가능
mutable.add("a");      // ✅ 중복 자동 제거
mutable.remove("a");   // ✅ 가능
```

---

**Day 4 완료! Set과 Map의 고급 활용 패턴을 완전 마스터했습니다! 🎯**