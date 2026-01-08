# [프로그래머스] 완주하지 못한 선수

## 문제 링크
- [프로그래머스 - 완주하지 못한 선수](https://school.programmers.co.kr/learn/courses/30/lessons/42576)
- **난이도**: Level 1
- **주제**: 해시(Hash)

## 문제 요약
마라톤에 참여한 선수들의 이름이 담긴 배열 `participant`와 완주한 선수들의 이름이 담긴 배열 `completion`이 주어질 때, 완주하지 못한 선수의 이름을 return 하는 문제입니다.

### 제약조건
- 마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다
- completion의 길이는 participant의 길이보다 1 작습니다
- 참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다
- **참가자 중에는 동명이인이 있을 수 있습니다**

## 접근 방법

### 1️⃣ 첫 번째 접근 (비효율적)
```java
// 이중 반복문 사용 - O(N²) 시간복잡도
public String solution(String[] participant, String[] completion) {
    String answer = "";
    for(int i=0; i<participant.length; i++){
        boolean isSame = false;
        for(int j=0; j<completion.length; j++){
            if(participant[i].equals(completion[j])){
                isSame = true;
                break;
            }
        }
        if(!isSame){
            answer = participant[i];
            break;
        }
    }
    return answer;
}
```

**문제점:**
- ❌ **시간복잡도 O(N²)** - 대용량 데이터에서 성능 저하
- ❌ **동명이인 처리 불가** - 같은 이름이 여러 명일 때 오작동
- ❌ **비효율적인 탐색** - completion 배열을 매번 처음부터 검색

### 2️⃣ 최적 접근 (HashMap 활용)
HashMap을 사용하여 **빈도수 카운팅** 방식으로 해결

1. 참가자들을 HashMap에 등록하며 빈도수 카운트
2. 완주자들을 HashMap에서 차감
3. 카운트가 0이 아닌 선수가 완주하지 못한 선수

## 핵심 아이디어
- **HashMap의 `getOrDefault()` 활용**: 안전한 기본값 설정으로 빈도수 카운팅
- **시간복잡도 O(N)**: HashMap의 get/put 연산이 평균 O(1)이므로 전체 O(N)
- **동명이인 처리**: 빈도수로 관리하여 완벽 해결

## 코드 구현

### ✅ 최적 해법 (HashMap)
```java
import java.util.*;

public class Solution {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();
        
        // 참가자들을 HashMap에 등록 (빈도수 카운트)
        for (String name : participant) {
            map.put(name, map.getOrDefault(name, 0) + 1);
        }
        
        // 완주자들을 HashMap에서 차감
        for (String name : completion) {
            map.put(name, map.get(name) - 1);
        }
        
        // 카운트가 0이 아닌 선수가 완주하지 못한 선수
        for (String name : map.keySet()) {
            if (map.get(name) != 0) {
                return name;
            }
        }
        
        return "";
    }
}
```

### 🔧 대안 해법 1: 정렬 활용
```java
public String solution(String[] participant, String[] completion) {
    Arrays.sort(participant);
    Arrays.sort(completion);
    
    for (int i = 0; i < completion.length; i++) {
        if (!participant[i].equals(completion[i])) {
            return participant[i];
        }
    }
    
    // 마지막 참가자가 완주하지 못한 경우
    return participant[participant.length - 1];
}
```

### 🔧 대안 해법 2: 해시값 이용
```java
public String solution(String[] participant, String[] completion) {
    int hash = 0;
    
    // 참가자들의 해시값 더하기
    for (String name : participant) {
        hash += name.hashCode();
    }
    
    // 완주자들의 해시값 빼기
    for (String name : completion) {
        hash -= name.hashCode();
    }
    
    // 남은 해시값에 해당하는 이름 찾기
    for (String name : participant) {
        if (name.hashCode() == hash) {
            return name;
        }
    }
    
    return "";
}
```

## 테스트 케이스

```java
public static void main(String[] args) {
    Solution sol = new Solution();
    
    // 테스트 1: 기본 케이스
    String[] participant1 = {"leo", "kiki", "eden"};
    String[] completion1 = {"eden", "kiki"};
    System.out.println(sol.solution(participant1, completion1)); // "leo"
    
    // 테스트 2: 일반적인 케이스  
    String[] participant2 = {"marina", "josipa", "nikola", "vinko", "filipa"};
    String[] completion2 = {"josipa", "filipa", "marina", "nikola"};
    System.out.println(sol.solution(participant2, completion2)); // "vinko"
    
    // 테스트 3: 동명이인 케이스
    String[] participant3 = {"mislav", "stanko", "mislav", "ana"};
    String[] completion3 = {"stanko", "ana", "mislav"};
    System.out.println(sol.solution(participant3, completion3)); // "mislav"
}
```

### 실행 결과
```
leo
vinko
mislav
```

## 성능 분석

| 해법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|------|-----------|-----------|------|------|
| HashMap | O(N) | O(N) | 최고 성능, 직관적 | 추가 공간 필요 |
| 정렬 | O(N log N) | O(1) | 공간 효율적 | 정렬 오버헤드 |
| 해시값 | O(N) | O(1) | 공간 효율적 | 해시 충돌 위험 |

## 학습 포인트

### 1. HashMap 핵심 패턴
```java
// 빈도수 카운팅 표준 패턴
map.put(key, map.getOrDefault(key, 0) + 1);

// 안전한 값 감소
map.put(key, map.get(key) - 1);
```

### 2. getOrDefault() 활용
- **문법**: `map.getOrDefault(key, defaultValue)`
- **용도**: 키가 없을 때 기본값 반환으로 NPE 방지
- **실무 활용**: 카운팅, 그룹핑 등에서 필수

### 3. 시간복잡도 최적화
- 이중 반복문 O(N²) → HashMap O(N)으로 대폭 성능 향상
- 대용량 데이터(N=100,000)에서 극명한 차이

### 4. 동명이인 처리 기법
- 빈도수 카운팅으로 중복 이름 완벽 처리
- contains() 방식으로는 처리 불가

## 관련 문제
- [프로그래머스] 폰켓몬 (Level 1) - HashMap 활용
- [프로그래머스] 위장 (Level 2) - HashMap 응용
- [백준] 1620번 나는야 포켓몬 마스터 이다솜 - HashMap 활용

## 실무 적용
이 문제는 실무에서 자주 마주하는 **데이터 매칭 및 차집합 구하기** 상황과 유사합니다:
- 회원 가입 시 중복 체크
- 재고 관리 시스템에서 입출고 차이 계산
- 로그 분석에서 특정 조건 필터링

**Remember**: HashMap + getOrDefault() 패턴은 코딩테스트의 기본기! 🚀