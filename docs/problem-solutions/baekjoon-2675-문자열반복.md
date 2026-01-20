# 백준 2675번 - 문자열 반복

## 📋 문제 정보
- **문제명**: 문자열 반복
- **플랫폼**: 백준 (BOJ)
- **번호**: 2675번
- **URL**: https://www.acmicpc.net/problem/2675
- **파일명**: `day1_add.java`
- **완료일**: 2024-01-08 (Day 1)

## 🎯 문제 분석

문자열 S의 각 문자를 R번 반복해서 새 문자열 P를 만드는 문제입니다.

### 입력 형식
```
T                    (테스트 케이스 개수)
R S                  (반복횟수 R, 문자열 S)
R S
...
```

### 출력 형식
```
P                    (각 문자가 R번 반복된 문자열)
P
...
```

### 예시
```
입력:
2
3 ABC
5 /HTP

출력:
AAABBBCCC
/////HHHHHTTTTTPPPPP
```

## 💡 원본 코드 분석

```java
// 원래 코드 (문제점 있음)
int n = Integer.parseInt(br.readLine());        // 변수명 혼동
st = new StringTokenizer(br.readLine());        // 단일 케이스만 처리
int a = Integer.parseInt(st.nextToken());       // 의미 불분명
String b = st.nextToken();
for(char c : b.toCharArray()){
    for (int i = 0; i <= n; i++) {              // 잘못된 반복 조건
        sb.append(c);
    }
}
```

**🔍 원본 코드의 문제점:**
1. **변수명 혼동**: `n`은 테스트케이스 개수인데 반복횟수로 사용
2. **테스트케이스 미처리**: 다중 테스트케이스 없이 단일 케이스만 처리
3. **반복 조건 오류**: `i <= n` (n+1번 반복) 대신 `i < R` (R번 반복)
4. **변수명 불분명**: `a`, `b` 대신 `R`, `S` 사용 권장

## 🚀 개선된 해법

```java
public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    // 테스트 케이스 개수 입력
    int T = Integer.parseInt(br.readLine());
    
    // 각 테스트 케이스 처리
    for (int t = 0; t < T; t++) {
        st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken()); // 반복 횟수
        String S = st.nextToken();                // 문자열
        
        StringBuilder sb = new StringBuilder();
        
        // 문자열의 각 문자를 R번 반복
        for (char c : S.toCharArray()) {
            for (int i = 0; i < R; i++) { // R번 반복
                sb.append(c);
            }
        }
        
        System.out.println(sb.toString());
    }
}
```

## 🔍 핵심 패턴 분석

### 1. 다중 테스트케이스 처리 패턴
```java
int T = Integer.parseInt(br.readLine());
for (int t = 0; t < T; t++) {
    // 각 테스트케이스 처리
    st = new StringTokenizer(br.readLine());
    // ...
}
```

### 2. 문자별 반복 패턴
```java
for (char c : S.toCharArray()) {    // 각 문자에 대해
    for (int i = 0; i < R; i++) {   // R번 반복
        sb.append(c);
    }
}
```

### 3. StringBuilder 효율적 사용
```java
StringBuilder sb = new StringBuilder();  // 테스트케이스마다 새로 생성
// 문자 추가
System.out.println(sb.toString());      // 결과 출력
```

## 📊 복잡도 분석

- **시간복잡도**: O(T × N × R)
  - T: 테스트케이스 개수
  - N: 문자열 길이  
  - R: 반복 횟수
- **공간복잡도**: O(N × R) - StringBuilder 크기

## 🎯 테스트 케이스

| 입력 | 설명 | 예상 출력 |
|------|------|----------|
| `3 ABC` | 각 문자 3번 반복 | `AAABBBCCC` |
| `5 /HTP` | 특수문자 포함 5번 반복 | `/////HHHHHTTTTTPPPPP` |
| `1 Hello` | 1번 반복 (원본 유지) | `Hello` |
| `2 a` | 단일 문자 2번 반복 | `aa` |

## 💡 학습 포인트

### 1. 다중 테스트케이스 패턴
```java
// 표준 패턴
int T = Integer.parseInt(br.readLine());
for (int t = 0; t < T; t++) {
    // 각 테스트케이스 독립 처리
}
```

### 2. 문자열 효율적 처리
- **StringBuilder 활용**: String 연결보다 훨씬 효율적
- **toCharArray()**: 문자별 접근시 효과적
- **테스트케이스별 초기화**: 매번 새로운 StringBuilder 생성

### 3. 변수명 명명 규칙
```java
// 좋은 예
int T = testCaseCount;    // 테스트케이스 개수
int R = repeatCount;      // 반복 횟수
String S = inputString;   // 입력 문자열

// 나쁜 예  
int n = ...;              // 의미 불분명
int a = ...;              // 용도 파악 어려움
```

### 4. 반복 조건 주의사항
```java
// 올바른 R번 반복
for (int i = 0; i < R; i++)     // 0, 1, ..., R-1 (R번)

// 잘못된 R+1번 반복
for (int i = 0; i <= R; i++)    // 0, 1, ..., R (R+1번)
```

### 5. BufferedReader + StringTokenizer 패턴
```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer st = new StringTokenizer(br.readLine());
int R = Integer.parseInt(st.nextToken());
String S = st.nextToken();
```

## 🔗 관련 패턴
- **다중 테스트케이스**: BOJ에서 매우 자주 나오는 패턴
- **문자열 조작**: day1_2 (신규 아이디 추천)
- **StringBuilder 활용**: 문자열 연결이 많은 모든 문제

## 📈 난이도 평가
- **구현 난이도**: ⭐ (매우 직관적)
- **실수 가능성**: ⭐⭐ (반복 조건, 테스트케이스 처리)
- **최적화 필요성**: ⭐⭐ (StringBuilder 필수)

## 🎁 보너스 팁

### 성능 최적화
```java
// StringBuilder 초기 용량 설정 (선택적)
StringBuilder sb = new StringBuilder(S.length() * R);

// 또는 String.repeat() 활용 (Java 11+)
for (char c : S.toCharArray()) {
    sb.append(String.valueOf(c).repeat(R));
}
```

### 입력 검증
```java
// 실제 코테에서는 보통 생략하지만, 안전한 코드 작성시
if (R <= 0 || S.isEmpty()) {
    System.out.println("");
    continue;
}
```

### 디버깅 팁
```java
// 테스트케이스별 구분을 위한 디버그 출력
System.err.println("Test case " + (t+1) + ": R=" + R + ", S=" + S);
```

---

**Day 1 완료! 다중 테스트케이스와 문자열 처리의 기초를 확실히 다졌습니다! 🎯**