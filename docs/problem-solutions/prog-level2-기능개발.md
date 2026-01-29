# 프로그래머스 Lv2 - 기능개발

**난이도**: Level 2  
**링크**: https://school.programmers.co.kr/learn/courses/30/lessons/42586  
**태그**: Queue, Stack, 시뮬레이션, 순차 처리, 올림 연산  

## 문제 요약
프로그래머스 팀에서는 기능 개선 작업을 수행 중이다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있다. 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포된다. 먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 반환하라.

**입력**: 
- progresses: `[93, 30, 55]`
- speeds: `[1, 30, 5]`

**출력**: `[2, 1]`  
**설명**: 1번 기능은 7일, 2번 기능은 3일, 3번 기능은 9일이 걸린다. 하지만 2번 기능은 1번 기능이 완료될 때까지 기다려야 하므로 1,2번이 7일째에 함께 배포되고, 3번 기능은 9일째에 단독 배포된다.

## 핵심 아이디어
1. **순차 처리**: 앞선 기능이 완료되지 않으면 뒤 기능도 배포 불가 (Queue 특성)
2. **올림 연산**: 완료 소요일 = ⌈(100 - 진도) / 속도⌉
3. **그룹화**: 동시에 배포 가능한 기능들을 하나의 그룹으로 묶기

## 주요 함정과 해결책

### 🚨 함정 1: 문제 이해 오류

#### ❌ 잘못된 이해 (원본 코드)
```java
// 단순히 완료된 기능 수만 세는 잘못된 접근
int completeCnt = 0;
for (int i = 0; i < progresses.length; i++) {
    if(progresses[i] + (speeds[i]*day) >= 100){
        completeCnt++; // 순서 무시한 카운팅
    }
}
```

**문제점**: 기능 배포는 **순차적**이어야 함. 뒤의 기능이 먼저 완료되어도 앞의 기능이 완료되지 않으면 배포 불가능.

#### ✅ 올바른 이해
```java
// FIFO 방식으로 순차 처리
Queue<Integer> queue = new ArrayDeque<>();
// 각 기능의 완료일을 미리 계산하여 큐에 저장
// 앞선 기능의 완료일을 기준으로 뒤따르는 기능들 그룹화
```

### 🚨 함정 2: 배열 인덱스 오류

#### ❌ 치명적인 버그
```java
for (int k : result) {
    answer[k] = result.get(k); // k는 값, 인덱스가 아님!
}
// ArrayIndexOutOfBoundsException 확정!
```

#### ✅ 올바른 배열 복사
```java
// 방법 1: 전통적 방식
for (int i = 0; i < result.size(); i++) {
    answer[i] = result.get(i);
}

// 방법 2: Stream 활용
return result.stream().mapToInt(Integer::intValue).toArray();
```

### 🚨 함정 3: 올림 연산 처리

#### 문제 상황
```java
// 진도 99%, 속도 2 → 며칠 걸릴까?
int remain = 100 - 99; // 1
int days = remain / 2; // 0 (잘못됨!)
```

#### ✅ 올바른 올림 연산
```java
// 방법 1: Math.ceil() 사용
int daysNeeded = (int) Math.ceil((double) remainWork / speed);

// 방법 2: 정수 연산으로 올림
int daysNeeded = (remainWork + speed - 1) / speed;
```

## 최종 해법들

### 해법 1: Queue 기반 처리 (권장)

```java
public int[] solution(int[] progresses, int[] speeds) {
    // 1단계: 각 기능별 완료 소요일 계산
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < progresses.length; i++) {
        int remainWork = 100 - progresses[i];
        int daysNeeded = (int) Math.ceil((double) remainWork / speeds[i]);
        queue.offer(daysNeeded);
    }
    
    // 2단계: 배포 그룹 단위로 처리
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int currentDay = queue.poll(); // 현재 배포할 기능의 완료일
        int count = 1; // 현재 기능 포함
        
        // 뒤따르는 기능들 중에서 현재 배포일에 함께 배포 가능한 것들
        while (!queue.isEmpty() && queue.peek() <= currentDay) {
            queue.poll();
            count++;
        }
        
        result.add(count);
    }
    
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

### 해법 2: 직접 시뮬레이션 (직관적)

```java
public int[] solution(int[] progresses, int[] speeds) {
    List<Integer> result = new ArrayList<>();
    boolean[] completed = new boolean[progresses.length]; // 완료 상태 추적
    int deployedIndex = 0; // 다음 배포할 기능의 인덱스
    
    int day = 0;
    while (deployedIndex < progresses.length) {
        day++;
        
        // 모든 기능의 진행률 업데이트
        for (int i = 0; i < progresses.length; i++) {
            if (!completed[i]) {
                progresses[i] += speeds[i];
                if (progresses[i] >= 100) {
                    completed[i] = true;
                }
            }
        }
        
        // 배포 가능한 기능들 확인 (순차적으로)
        int deployCount = 0;
        while (deployedIndex < progresses.length && completed[deployedIndex]) {
            deployedIndex++;
            deployCount++;
        }
        
        if (deployCount > 0) {
            result.add(deployCount);
        }
    }
    
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

### 해법 3: 수학적 계산 + 포인터 (최적화)

```java
public int[] solution(int[] progresses, int[] speeds) {
    List<Integer> result = new ArrayList<>();
    int index = 0;
    
    while (index < progresses.length) {
        // 현재 기능의 완료 소요일 계산
        int remainWork = 100 - progresses[index];
        int currentDeployDay = (int) Math.ceil((double) remainWork / speeds[index]);
        
        int count = 1; // 현재 기능 포함
        index++;
        
        // 연속적으로 배포 가능한 기능들 찾기
        while (index < progresses.length) {
            int nextRemainWork = 100 - progresses[index];
            int nextDeployDay = (int) Math.ceil((double) nextRemainWork / speeds[index]);
            
            if (nextDeployDay <= currentDeployDay) {
                count++;
                index++;
            } else {
                break; // 배포 불가능하면 중단
            }
        }
        
        result.add(count);
    }
    
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

## 알고리즘 분석

### 핵심 개념: FIFO 순차 처리

#### 문제의 본질
```java
// 기능 개발 = 작업 스케줄링 문제
// 선행 작업이 완료되어야 후행 작업 배포 가능
// Queue의 FIFO 특성과 완벽히 일치
```

#### 예시 분석
```java
progresses = [93, 30, 55], speeds = [1, 30, 5]

// 각 기능별 완료 소요일:
// 기능1: (100-93)/1 = 7일
// 기능2: (100-30)/30 = 2.33... → 3일 (올림)
// 기능3: (100-55)/5 = 9일

// 배포 스케줄:
// 7일차: 기능1 완료 → 기능2도 이미 완료됨 → [기능1, 기능2] 함께 배포
// 9일차: 기능3 완료 → [기능3] 단독 배포

// 결과: [2, 1]
```

### 시간/공간 복잡도

| 해법 | 시간복잡도 | 공간복잡도 | 특징 |
|------|-----------|-----------|------|
| **해법 1** | O(N) | O(N) | Queue 활용, 가장 직관적 |
| **해법 2** | O(D×N) | O(N) | 시뮬레이션, 가장 이해하기 쉬움 |
| **해법 3** | O(N) | O(N) | 수학적 계산, 가장 효율적 |

- N: 기능 개수, D: 최대 소요일수

## 실수하기 쉬운 포인트

### 1. 순서 의존성 무시
```java
// ❌ 잘못된 접근: 완료된 기능만 카운팅
if (isCompleted(feature)) {
    count++;
}

// ✅ 올바른 접근: 순차적 배포 확인
while (queue.peek() <= currentDay) {
    count++;
}
```

### 2. 소수점 처리 오류
```java
// ❌ 잘못된 나눗셈
int days = remainWork / speed; // 소수점 버림

// ✅ 올바른 올림 처리
int days = (int) Math.ceil((double) remainWork / speed);
```

### 3. 배열 vs List 변환 실수
```java
// ❌ 인덱스/값 혼동
for (int value : list) {
    array[value] = ...; // value를 인덱스로 잘못 사용
}

// ✅ 올바른 변환
for (int i = 0; i < list.size(); i++) {
    array[i] = list.get(i);
}
```

### 4. 큐 빈 상태 확인 누락
```java
// ❌ NullPointerException 위험
while (queue.peek() <= currentDay) { // 빈 큐에서 peek() → null

// ✅ 안전한 확인
while (!queue.isEmpty() && queue.peek() <= currentDay) {
    queue.poll();
}
```

## 확장 가능한 패턴

### 유사한 문제들

#### 1. 프린터 작업 스케줄링
```java
// 우선순위가 높은 문서가 있으면 뒤로 미루기
public int solution(int[] priorities, int location) {
    Queue<int[]> queue = new LinkedList<>(); // {우선순위, 원래인덱스}
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    // ... Queue + PriorityQueue 활용
}
```

#### 2. 다리를 지나는 트럭
```java
// 다리 위의 트럭들을 Queue로 관리
public int solution(int bridge_length, int weight, int[] truck_weights) {
    Queue<Integer> bridge = new LinkedList<>();
    // 시간에 따른 상태 변화 시뮬레이션
}
```

#### 3. 주식가격
```java
// 가격이 떨어지지 않은 기간 계산 (Stack 활용)
public int[] solution(int[] prices) {
    Deque<Integer> stack = new ArrayDeque<>(); // 인덱스 저장
    // 단조 스택으로 다음 작은 값 찾기
}
```

### 실전 최적화

#### 1. 메모리 최적화
```java
// Queue 대신 포인터로 직접 처리
int index = 0;
while (index < progresses.length) {
    int currentDeployDay = calculateDays(index);
    int count = 1;
    // 연속 처리로 Queue 메모리 절약
}
```

#### 2. 조기 종료 최적화
```java
// 모든 기능이 완료되면 즉시 종료
if (allCompleted()) {
    break;
}
```

## 학습 포인트

### 1. Queue/Stack 선택 기준
- **순차 처리** → Queue (FIFO)
- **역순 처리** → Stack (LIFO)
- **우선순위** → PriorityQueue

### 2. 수학적 처리
- **올림 연산**: `Math.ceil()` vs `(a + b - 1) / b`
- **나머지 연산**: 주기적 패턴 활용
- **최대공약수**: 반복 주기 계산

### 3. 시뮬레이션 vs 수학적 계산
- **직관적 이해**: 시뮬레이션
- **성능 최적화**: 수학적 계산
- **디버깅 용이성**: 시뮬레이션

### 4. 배열/리스트 변환 패턴
```java
// List → 배열
result.stream().mapToInt(Integer::intValue).toArray();

// 배열 → List
Arrays.asList(array); // 원시 배열은 boxed() 필요
```

## 관련 문제
- **프로그래머스 Lv2**: 다리를 지나는 트럭 (Queue 시뮬레이션)
- **프로그래머스 Lv2**: 프린터 (Queue + PriorityQueue)
- **백준 18258번**: 큐 2 (기본 Queue 연산)
- **백준 10828번**: 스택 (기본 Stack 연산)

**이 문제의 핵심은 'Queue의 FIFO 특성'과 '순차 처리 개념'입니다!** ⚡🚀