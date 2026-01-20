# 프로그래머스 Lv1 - 실패율

## 📋 문제 정보
- **문제명**: 실패율
- **플랫폼**: 프로그래머스 Lv1
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/42889
- **파일명**: `day2_2.java`
- **출처**: 2019 KAKAO BLIND RECRUITMENT
- **완료일**: 2024-01-09 (Day 2)

## 🎯 문제 분석

게임의 N개 스테이지에서 각 스테이지의 **실패율**을 구하여 내림차순으로 정렬하는 문제입니다.

### 실패율 정의
```
실패율 = 스테이지에 도달했으나 아직 클리어하지 못한 플레이어 수 / 스테이지에 도달한 플레이어 수
```

### 정렬 규칙
1. **1차 정렬**: 실패율 내림차순
2. **2차 정렬**: 실패율이 같으면 스테이지 번호 오름차순

### 예시
```
N = 5, stages = [2, 1, 2, 6, 2, 4, 3, 3]

각 스테이지별 상황:
- 스테이지 1: 실패 1명, 도달 8명 → 실패율 1/8 = 0.125
- 스테이지 2: 실패 3명, 도달 7명 → 실패율 3/7 ≈ 0.429  
- 스테이지 3: 실패 2명, 도달 4명 → 실패율 2/4 = 0.5
- 스테이지 4: 실패 1명, 도달 2명 → 실패율 1/2 = 0.5
- 스테이지 5: 실패 0명, 도달 1명 → 실패율 0/1 = 0

정렬 결과: [3, 4, 2, 1, 5]
```

## 💡 사용자 원본 코드 분석

```java
public int[] originalSolution(int N, int[] stages) {
    int[] answer = {}; // 문제: 빈 배열을 반환하게 됨
    
    int[] failCnt = new int[N+2]; // 좋은 접근: 빈도수 배열 활용
    
    for (int stage : stages) {
        failCnt[stage]++; // 각 스테이지별 사용자 수 카운팅
    }
    
    int totUserCnt = stages.length;
    double[][] sortedStages = new double[N][2]; // [스테이지번호, 실패율]
    
    for(int i = 1; i < failCnt.length-1; i++) {
        int fail = failCnt[i];
        double failRate = totUserCnt == 0 ? 0.0 : (double)fail / totUserCnt;
        
        sortedStages[i-1][0] = i;        // 스테이지 번호
        sortedStages[i-1][1] = failRate; // 실패율
        
        totUserCnt -= fail; // 다음 스테이지로 진행하는 사용자 수 업데이트
    }
    
    // 다중 조건 정렬: 실패율 내림차순 → 스테이지 번호 오름차순
    Arrays.sort(sortedStages,(a,b) -> {
        if(a[1] != b[1]){
            return Double.compare(b[1],a[1]); // 실패율 내림차순
        }
        return Double.compare(a[0],b[0]); // 스테이지 번호 오름차순
    });
    
    answer = new int[N]; // 배열 크기 설정
    for (int i = 0; i < sortedStages.length; i++) {
        answer[i] = (int)sortedStages[i][0];
    }
    
    return answer;
}
```

**✅ 원본 코드 평가:**
- **핵심 아이디어**: 빈도수 배열과 누적 계산 방식 - 매우 뛰어난 접근!
- **정확성**: 실패율 계산과 다중 조건 정렬이 완벽하게 구현됨
- **효율성**: O(N) 시간복잡도로 최적화된 해법
- **작은 결함**: 초기 `answer` 배열 선언 이슈 (나중에 올바르게 수정함)

## 🚀 개선된 해법들

### 1. 클래스 기반 명확한 해법
```java
public int[] solution(int N, int[] stages) {
    class Stage {
        int number;
        double failRate;
        
        Stage(int number, double failRate) {
            this.number = number;
            this.failRate = failRate;
        }
    }
    
    List<Stage> stageList = new ArrayList<>();
    
    for (int i = 1; i <= N; i++) {
        int fail = 0, total = 0;
        
        for (int stage : stages) {
            if (stage == i) fail++;
            if (stage >= i) total++;
        }
        
        double failRate = (total == 0) ? 0.0 : (double) fail / total;
        stageList.add(new Stage(i, failRate));
    }
    
    // 다중 조건 정렬
    stageList.sort((a, b) -> {
        if (a.failRate != b.failRate) {
            return Double.compare(b.failRate, a.failRate);
        }
        return Integer.compare(a.number, b.number);
    });
    
    return stageList.stream().mapToInt(s -> s.number).toArray();
}
```

### 2. 최적화 해법 (원본 코드 스타일)
```java
public int[] solutionOptimized(int N, int[] stages) {
    // 각 스테이지별 사용자 수 카운팅
    int[] stageCounts = new int[N + 2];
    for (int stage : stages) {
        stageCounts[stage]++;
    }
    
    double[][] stageFailRates = new double[N][2];
    int totalUsers = stages.length;
    
    for (int i = 1; i <= N; i++) {
        int fail = stageCounts[i];
        double failRate = (totalUsers == 0) ? 0.0 : (double) fail / totalUsers;
        
        stageFailRates[i-1][0] = i;
        stageFailRates[i-1][1] = failRate;
        
        totalUsers -= fail;
    }
    
    Arrays.sort(stageFailRates, (a, b) -> {
        if (a[1] != b[1]) return Double.compare(b[1], a[1]);
        return Double.compare(a[0], b[0]);
    });
    
    return Arrays.stream(stageFailRates)
                 .mapToInt(s -> (int)s[0])
                 .toArray();
}
```

## 🔍 핵심 패턴 분석

### 1. 실패율 계산 패턴
```java
// 방법 1: 매번 전체 배열 순회 (명확하지만 O(N²))
for (int i = 1; i <= N; i++) {
    int fail = 0, total = 0;
    for (int stage : stages) {
        if (stage == i) fail++;
        if (stage >= i) total++;
    }
}

// 방법 2: 빈도수 + 누적 계산 (효율적 O(N))
int[] stageCounts = new int[N + 2];
for (int stage : stages) stageCounts[stage]++;

int totalUsers = stages.length;
for (int i = 1; i <= N; i++) {
    double failRate = (double) stageCounts[i] / totalUsers;
    totalUsers -= stageCounts[i];
}
```

### 2. 다중 조건 정렬 패턴
```java
Arrays.sort(array, (a, b) -> {
    // 1차 조건: 실패율 내림차순
    if (a[1] != b[1]) {
        return Double.compare(b[1], a[1]);
    }
    // 2차 조건: 스테이지 번호 오름차순  
    return Double.compare(a[0], b[0]);
});
```

### 3. 0으로 나누기 방지 패턴
```java
// 삼항 연산자 활용
double failRate = (totalUsers == 0) ? 0.0 : (double) fail / totalUsers;

// if-else 활용
if (totalUsers == 0) {
    failRate = 0.0;
} else {
    failRate = (double) fail / totalUsers;
}
```

## 📊 성능 비교

| 해법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|------|------------|------------|------|------|
| 원본 (빈도수) | O(N + M) | O(N) | 최고 효율성 | 코드 복잡도 |
| 클래스 기반 | O(N × M) | O(N) | 가독성 우수 | 이중 반복문 |
| 최적화 | O(N + M) | O(N) | 효율+가독성 | - |

*N: 스테이지 수, M: 사용자 수*

## 💡 학습 포인트

### 1. 실패율 계산의 핵심
```java
// 핵심: 도달한 사용자 = 현재 스테이지 + 이후 스테이지 모든 사용자
int total = 0;
for (int stage : stages) {
    if (stage >= currentStage) total++; // 현재 스테이지에 도달한 모든 사용자
}

// 실패한 사용자 = 현재 스테이지에 머물러 있는 사용자만
int fail = stageCounts[currentStage];
```

### 2. 누적 계산 최적화
```java
// 효율적: 전체 사용자에서 점진적으로 차감
int totalUsers = stages.length;
for (int i = 1; i <= N; i++) {
    double failRate = (double) stageCounts[i] / totalUsers;
    totalUsers -= stageCounts[i]; // 다음 스테이지로 진행
}
```

### 3. Double 비교와 정렬
```java
// Double 안전 비교
if (a.failRate != b.failRate) {
    return Double.compare(b.failRate, a.failRate); // NaN, infinity 안전
}

// 또는 epsilon 활용 (매우 정밀한 경우)
if (Math.abs(a.failRate - b.failRate) > 1e-9) {
    return Double.compare(b.failRate, a.failRate);
}
```

### 4. 배열 vs 클래스 선택
```java
// 배열: 메모리 효율적, 빠름
double[][] stageData = new double[N][2];

// 클래스: 가독성 우수, 유지보수 용이  
class Stage {
    int number;
    double failRate;
}
```

## 🎯 테스트 케이스

| N | stages | 예상 결과 | 검증 포인트 |
|---|--------|----------|------------|
| 5 | `[2,1,2,6,2,4,3,3]` | `[3,4,2,1,5]` | 기본 케이스 |
| 4 | `[4,4,4,4,4]` | `[4,1,2,3]` | 실패율 동일시 |
| 3 | `[1,1,1]` | `[1,2,3]` | 모든 실패율 0 |
| 2 | `[3,3,3,3]` | `[2,1]` | 도달하지 못한 스테이지 |

## 🔗 관련 개념

### 정렬 알고리즘
- **다중 조건 정렬**: Comparator 체이닝
- **커스텀 정렬**: 람다 표현식 활용
- **안정 정렬**: 동일 값에서 순서 보장

### 실수 계산 주의사항
- **정수 나눗셈 vs 실수 나눗셈**: `(double)` 캐스팅
- **0으로 나누기**: 사전 조건 검사
- **Double 비교**: `Double.compare()` 사용

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐⭐ (실패율 개념 이해 + 다중 정렬)
- **최적화 난이도**: ⭐⭐⭐⭐ (O(N²) → O(N) 최적화)
- **실수 가능성**: ⭐⭐⭐ (0으로 나누기, 정렬 조건)

## 🎁 보너스 팁

### 디버깅용 출력
```java
System.out.printf("스테이지 %d: 실패 %d명, 도달 %d명, 실패율 %.3f%n",
                  i, fail, total, failRate);
```

### 함수형 스타일 구현
```java
return IntStream.rangeClosed(1, N)
    .boxed()
    .sorted((a, b) -> {
        double rateA = calculateFailRate(a, stages);
        double rateB = calculateFailRate(b, stages);
        return rateA != rateB ? Double.compare(rateB, rateA) : Integer.compare(a, b);
    })
    .mapToInt(Integer::intValue)
    .toArray();
```

---

**Day 2 완료! 실패율 계산과 다중 조건 정렬의 핵심을 완전 마스터했습니다! 🎯**