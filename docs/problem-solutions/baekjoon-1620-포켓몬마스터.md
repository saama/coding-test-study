# 백준 1620번 - 나는야 포켓몬 마스터 이다솜

**난이도**: Silver IV  
**링크**: https://www.acmicpc.net/problem/1620  
**태그**: 해시맵, 자료구조  

## 문제 요약
N개의 포켓몬 이름이 주어지고, M개의 쿼리에 대해:
- 숫자가 주어지면 → 해당 번호의 포켓몬 이름 출력
- 이름이 주어지면 → 해당 포켓몬의 번호 출력

## 핵심 아이디어
**양방향 매핑**: 번호→이름, 이름→번호 두 방향 모두 빠르게 검색 필요
- `String[] numToName`: 번호로 이름 찾기 (O(1))
- `HashMap<String, Integer> nameToNum`: 이름으로 번호 찾기 (O(1))

## 풀이 과정

### 1단계: 문제 분석
- N개 포켓몬: 1번부터 N번까지 순서대로 입력
- M개 쿼리: 숫자 또는 이름
- 양방향 검색이 필요 → 두 개의 자료구조 사용

### 2단계: 자료구조 설계
```java
String[] numToName = new String[N + 1];      // 1-based 인덱싱
Map<String, Integer> nameToNum = new HashMap<>();  // 이름→번호 매핑
```

### 3단계: 입력 처리
```java
for (int i = 1; i <= N; i++) {
    String name = br.readLine();
    numToName[i] = name;           // 번호→이름
    nameToNum.put(name, i);        // 이름→번호
}
```

### 4단계: 쿼리 처리
```java
String query = br.readLine();
if (Character.isDigit(query.charAt(0))) {
    // 숫자 → 이름 출력
    System.out.println(numToName[Integer.parseInt(query)]);
} else {
    // 이름 → 번호 출력
    System.out.println(nameToNum.get(query));
}
```

## 최종 코드

### 해법 1: 기본 구현 (Character.isDigit 사용)
```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        // 양방향 매핑
        String[] numToName = new String[N + 1];
        Map<String, Integer> nameToNum = new HashMap<>();
        
        for (int i = 1; i <= N; i++) {
            String name = br.readLine();
            numToName[i] = name;
            nameToNum.put(name, i);
        }
        
        // 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            String query = br.readLine();
            
            if (Character.isDigit(query.charAt(0))) {
                sb.append(numToName[Integer.parseInt(query)]).append('\n');
            } else {
                sb.append(nameToNum.get(query)).append('\n');
            }
        }
        
        System.out.print(sb.toString());
    }
}
```

### 해법 2: try-catch 방식 (숫자 판별)
```java
for (int i = 0; i < M; i++) {
    String query = br.readLine();
    
    try {
        int num = Integer.parseInt(query);
        System.out.println(numToName[num]);
    } catch (NumberFormatException e) {
        System.out.println(nameToNum.get(query));
    }
}
```

## 핵심 포인트

### 1. 양방향 매핑 패턴
```java
// 일반적인 단방향 매핑
Map<String, Integer> map = new HashMap<>();

// 양방향 매핑이 필요할 때
String[] indexToValue = new String[N + 1];    // 인덱스→값
Map<String, Integer> valueToIndex = new HashMap<>();  // 값→인덱스
```

### 2. 1-based 인덱싱
- 문제에서 1번부터 시작 → 배열도 1-based로 설계
- `new String[N + 1]`로 크기 할당하고 인덱스 1부터 사용

### 3. 숫자/문자 판별 방법
```java
// 방법 1: Character.isDigit() - 가장 빠름
if (Character.isDigit(str.charAt(0))) { ... }

// 방법 2: try-catch - 코드가 간단함
try {
    int num = Integer.parseInt(str);
    // 숫자인 경우
} catch (NumberFormatException e) {
    // 문자인 경우
}

// 방법 3: 정규식 - 비권장 (성능 느림)
if (str.matches("\\d+")) { ... }
```

### 4. 메모리 최적화
```java
// 비효율적: 모든 쿼리를 배열에 저장 후 처리
String[] queries = new String[M];
String[] answers = new String[M];

// 효율적: 즉시 처리
for (int i = 0; i < M; i++) {
    String query = br.readLine();
    // 바로 처리하고 출력
}
```

## 시간/공간 복잡도
- **시간복잡도**: O(N + M)
  - 포켓몬 입력: O(N)
  - 쿼리 처리: O(M) (HashMap 검색이 O(1))
- **공간복잡도**: O(N)
  - 배열과 HashMap 모두 N개 원소 저장

## 실수하기 쉬운 포인트

1. **인덱싱 오류**
   ```java
   // 잘못된 방법: 1-based 문제인데 0-based로 접근
   answer[i] = pocketmonNames[Integer.parseInt(Q[i]) - 1];
   
   // 올바른 방법: 1-based 배열 사용
   answer[i] = pocketmonNames[Integer.parseInt(Q[i])];
   ```

2. **불필요한 배열 저장**
   ```java
   // 비효율적
   String[] Q = new String[M];
   String[] answer = new String[M];
   
   // 효율적
   StringBuilder sb = new StringBuilder();
   ```

3. **출력 최적화**
   ```java
   // 느림: 매번 System.out.println() 호출
   System.out.println(result);
   
   // 빠름: StringBuilder로 한번에 출력
   StringBuilder sb = new StringBuilder();
   sb.append(result).append('\n');
   System.out.print(sb.toString());
   ```

## 관련 문제
- 백준 17219번: 비밀번호 찾기 (HashMap 기본)
- 백준 9375번: 패션왕 신해빈 (HashMap 응용)
- 백준 1302번: 베스트셀러 (HashMap 카운팅)

## 학습 포인트
1. **양방향 검색**: Array + HashMap 조합
2. **인덱싱 주의**: 1-based vs 0-based
3. **문자열 판별**: Character.isDigit() 활용
4. **메모리 최적화**: 즉시 처리 방식