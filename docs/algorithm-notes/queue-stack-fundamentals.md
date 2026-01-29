# Queue & Stack 기초 완벽 가이드

> 코딩테스트에서 자주 출현하는 Queue와 Stack의 핵심 개념과 실전 활용법

## 🎯 목차
1. [Stack & Queue 기본 개념](#stack--queue-기본-개념)
2. [Java에서의 구현과 사용법](#java에서의-구현과-사용법)
3. [핵심 패턴과 알고리즘](#핵심-패턴과-알고리즘)
4. [실전 문제 유형별 접근법](#실전-문제-유형별-접근법)
5. [성능 최적화와 주의사항](#성능-최적화와-주의사항)
6. [고급 활용 패턴](#고급-활용-패턴)

---

## Stack & Queue 기본 개념

### Stack (스택) - LIFO 구조

#### 특징
- **LIFO (Last In, First Out)**: 마지막에 들어온 것이 먼저 나감
- **한쪽 끝에서만** 삽입과 삭제 발생
- **재귀 호출, 괄호 매칭, 뒤로가기** 등에 자연스럽게 활용

#### 기본 연산
```java
Stack<Integer> stack = new Stack<>();

// 삽입 (Push)
stack.push(1);    // [1]
stack.push(2);    // [1, 2]
stack.push(3);    // [1, 2, 3] ← top

// 삭제 (Pop)
int top = stack.pop();    // 3 반환, [1, 2]
int peek = stack.peek();  // 2 반환 (제거하지 않음), [1, 2]

// 상태 확인
boolean isEmpty = stack.empty();  // false
int size = stack.size();         // 2
```

#### 실생활 비유
```java
// 접시 쌓기: 위에 올린 접시부터 빼게 됨
// 웹 브라우저 뒤로가기: 최근 방문 페이지부터 돌아감
// 함수 호출: 가장 최근 호출된 함수부터 종료
```

### Queue (큐) - FIFO 구조

#### 특징
- **FIFO (First In, First Out)**: 먼저 들어온 것이 먼저 나감
- **양쪽 끝에서** 삽입(rear)과 삭제(front) 발생
- **순서 처리, 대기열, BFS** 등에 자연스럽게 활용

#### 기본 연산
```java
Queue<Integer> queue = new LinkedList<>();

// 삽입 (Enqueue)
queue.offer(1);    // [1]
queue.offer(2);    // [1, 2]
queue.offer(3);    // [1, 2, 3] ← rear
                   // ↑ front

// 삭제 (Dequeue)
int front = queue.poll();  // 1 반환, [2, 3]
int peek = queue.peek();   // 2 반환 (제거하지 않음), [2, 3]

// 상태 확인
boolean isEmpty = queue.isEmpty();  // false
int size = queue.size();           // 2
```

#### 실생활 비유
```java
// 은행 대기줄: 먼저 온 사람부터 처리
// 프린터 대기열: 먼저 요청한 문서부터 출력
// 프로세스 스케줄링: 먼저 요청된 작업부터 처리
```

---

## Java에서의 구현과 사용법

### Stack 구현 방법

#### 1. Stack 클래스 (권장하지 않음)
```java
// ❌ 레거시 클래스, 동기화 오버헤드
Stack<Integer> stack = new Stack<>();
stack.push(1);
int top = stack.pop();
```

#### 2. ArrayDeque 사용 (권장)
```java
// ✅ 최신 권장 방법
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);        // 또는 addFirst(1)
int top = stack.pop(); // 또는 removeFirst()
```

#### 3. ArrayList 활용 (단순한 경우)
```java
List<Integer> stack = new ArrayList<>();
stack.add(1);                           // push
int top = stack.remove(stack.size()-1); // pop
int peek = stack.get(stack.size()-1);   // peek
```

### Queue 구현 방법

#### 1. LinkedList (일반적)
```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(1);       // 삽입
int front = queue.poll(); // 삭제
```

#### 2. ArrayDeque (성능 우수)
```java
// ✅ 배열 기반, 메모리 효율적
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(1);
int front = queue.poll();
```

#### 3. PriorityQueue (우선순위 큐)
```java
// 자동 정렬, 최소 힙 기본
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(3); pq.offer(1); pq.offer(2);
int min = pq.poll(); // 1 (가장 작은 값)
```

### 메서드 비교표

| 기능 | Stack | Queue | Deque (양쪽) |
|------|-------|-------|--------------|
| **삽입** | push() | offer(), add() | addFirst(), addLast() |
| **삭제** | pop() | poll(), remove() | removeFirst(), removeLast() |
| **확인** | peek() | peek(), element() | peekFirst(), peekLast() |
| **비어있음** | empty() | isEmpty() | isEmpty() |

---

## 핵심 패턴과 알고리즘

### Stack 활용 패턴

#### 1. 괄호 매칭 검사
```java
public boolean isValidParentheses(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');
    
    for (char c : s.toCharArray()) {
        if (pairs.containsValue(c)) {
            stack.push(c);  // 여는 괄호
        } else if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                return false;  // 닫는 괄호
            }
        }
    }
    
    return stack.isEmpty();
}
```

#### 2. 후위 표기식 계산
```java
public int evaluatePostfix(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack.pop();
            int a = stack.pop();
            stack.push(calculate(a, b, token));
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    
    return stack.pop();
}

private int calculate(int a, int b, String op) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b;
        default: throw new IllegalArgumentException();
    }
}
```

#### 3. 히스토그램 최대 직사각형
```java
public int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>(); // 인덱스 저장
    int maxArea = 0;
    
    for (int i = 0; i <= heights.length; i++) {
        int currentHeight = (i == heights.length) ? 0 : heights[i];
        
        while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        stack.push(i);
    }
    
    return maxArea;
}
```

### Queue 활용 패턴

#### 1. BFS (너비우선탐색)
```java
public int bfs(int[][] graph, int start, int target) {
    Queue<int[]> queue = new ArrayDeque<>(); // {노드, 거리}
    boolean[] visited = new boolean[graph.length];
    
    queue.offer(new int[]{start, 0});
    visited[start] = true;
    
    while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int node = current[0];
        int distance = current[1];
        
        if (node == target) {
            return distance;
        }
        
        for (int next : graph[node]) {
            if (!visited[next]) {
                visited[next] = true;
                queue.offer(new int[]{next, distance + 1});
            }
        }
    }
    
    return -1; // 도달 불가
}
```

#### 2. 슬라이딩 윈도우 최댓값
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>(); // 인덱스 저장 (내림차순)
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // 윈도우 범위 벗어난 인덱스 제거
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // 현재 값보다 작은 값들 제거 (단조 감소 유지)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.offerLast(i);
        
        // 윈도우가 완성되면 최댓값 기록
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

#### 3. 작업 스케줄링 (기능 개발 패턴)
```java
public int[] functionDevelopment(int[] progresses, int[] speeds) {
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

---

## 실전 문제 유형별 접근법

### 문제 유형별 선택 가이드

| 문제 특징 | 자료구조 선택 | 핵심 아이디어 |
|-----------|---------------|---------------|
| **괄호, 중첩 구조** | Stack | 최근 것부터 처리 |
| **계산기, 후위 표기식** | Stack | 연산자 우선순위 |
| **뒤로가기, 실행취소** | Stack | 역순 처리 |
| **순서 처리, 대기열** | Queue | 선입선출 |
| **BFS, 레벨 탐색** | Queue | 단계별 확장 |
| **슬라이딩 윈도우** | Deque | 양쪽 끝 조작 |
| **우선순위 처리** | PriorityQueue | 자동 정렬 |

### 패턴 인식 키워드

#### Stack 키워드
- **"가장 최근에"**, **"마지막으로"**
- **"역순으로"**, **"뒤에서부터"**
- **"중첩된"**, **"쌍을 이루는"**
- **"재귀적인"**, **"단계별 되돌리기"**

#### Queue 키워드  
- **"순서대로"**, **"먼저 온 것부터"**
- **"레벨별로"**, **"단계적으로"**
- **"너비우선"**, **"동심원 확장"**
- **"시뮬레이션"**, **"시간 흐름"**

### 구현 시 체크리스트

#### Stack 구현 체크리스트
- [ ] 빈 스택에서 pop() 시도하지 않는가?
- [ ] push/pop 순서가 올바른가?
- [ ] 모든 여는 괄호에 대응하는 닫는 괄호가 있는가?
- [ ] 최종적으로 스택이 비어있어야 하는가?

#### Queue 구현 체크리스트
- [ ] 빈 큐에서 poll() 시도하지 않는가?
- [ ] FIFO 순서를 올바르게 지키고 있는가?
- [ ] 큐 크기 제한을 고려했는가?
- [ ] BFS에서 방문 체크를 올바르게 했는가?

---

## 성능 최적화와 주의사항

### 성능 비교

| 구현체 | 삽입 | 삭제 | 접근 | 메모리 | 특징 |
|--------|------|------|------|--------|------|
| **Stack\<T>** | O(1) | O(1) | O(1) | 중간 | 레거시, 동기화 오버헤드 |
| **ArrayDeque** | O(1)* | O(1) | O(1) | 우수 | **권장**, 동적 크기 조정 |
| **LinkedList** | O(1) | O(1) | O(n) | 많음 | 포인터 오버헤드 |
| **ArrayList** | O(1)* | O(n) | O(1) | 좋음 | 단순 스택용, 삭제 비효율 |

*: 배열 확장 시 O(n)

### 최적화 팁

#### 1. 적절한 초기 용량 설정
```java
// ✅ 예상 크기를 미리 설정
Deque<Integer> stack = new ArrayDeque<>(expectedSize);
Queue<Integer> queue = new ArrayDeque<>(expectedSize);
```

#### 2. 메모리 사용량 최소화
```java
// ❌ 불필요한 객체 생성
queue.offer(new Integer(i));

// ✅ 기본형 활용
queue.offer(i); // 자동 박싱 최소화
```

#### 3. 조기 종료 조건 활용
```java
// BFS에서 목표 발견 시 즉시 종료
while (!queue.isEmpty()) {
    int current = queue.poll();
    if (current == target) {
        return distance; // 즉시 반환
    }
    // ...
}
```

### 주의사항

#### 1. null 처리
```java
// ❌ null 반환 가능한 메서드들
Integer top = stack.poll();  // 빈 큐/스택에서 null 반환
Integer peek = queue.peek(); // 빈 큐/스택에서 null 반환

// ✅ 안전한 처리
if (!stack.isEmpty()) {
    Integer top = stack.poll();
}
```

#### 2. ConcurrentModificationException
```java
// ❌ 반복 중 수정
for (Integer item : queue) {
    if (condition) {
        queue.remove(item); // Exception 발생!
    }
}

// ✅ Iterator 사용
Iterator<Integer> it = queue.iterator();
while (it.hasNext()) {
    Integer item = it.next();
    if (condition) {
        it.remove(); // 안전한 제거
    }
}
```

---

## 고급 활용 패턴

### Monotonic Stack/Queue (단조 스택/큐)

#### 단조 증가 스택
```java
// 각 원소에 대해 다음으로 큰 원소 찾기
public int[] nextGreaterElement(int[] nums) {
    Deque<Integer> stack = new ArrayDeque<>(); // 인덱스 저장
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);
    
    for (int i = 0; i < nums.length; i++) {
        // 현재 원소가 스택 top보다 클 때까지 pop
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
            int index = stack.pop();
            result[index] = nums[i]; // 다음 큰 원소 발견
        }
        stack.push(i);
    }
    
    return result;
}
```

#### 단조 감소 큐 (슬라이딩 윈도우 최댓값)
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>(); // 감소 순서 유지
    int[] result = new int[nums.length - k + 1];
    
    for (int i = 0; i < nums.length; i++) {
        // 범위 벗어난 원소 제거
        while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
            deque.pollFirst();
        }
        
        // 단조 감소 유지 (현재 값보다 작은 값들 제거)
        while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
            deque.pollLast();
        }
        
        deque.offerLast(i);
        
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()]; // 최댓값
        }
    }
    
    return result;
}
```

### 우선순위 큐 활용

#### 커스텀 정렬
```java
// 최대 힙 (기본은 최소 힙)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// 복합 조건 정렬
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0]; // 첫 번째 요소 오름차순
    return b[1] - a[1];                   // 두 번째 요소 내림차순
});
```

#### Top K 문제
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int num : nums) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }
    
    // 최소 힙으로 K개 유지
    PriorityQueue<Map.Entry<Integer, Integer>> pq = 
        new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
    
    for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
        pq.offer(entry);
        if (pq.size() > k) {
            pq.poll(); // 빈도 낮은 것 제거
        }
    }
    
    return pq.stream().mapToInt(entry -> entry.getKey()).toArray();
}
```

---

## 💡 실전 팁

### 문제 해결 단계

#### 1. 문제 분석
```java
// 키워드 체크
- "최근", "마지막" → Stack
- "순서", "먼저" → Queue  
- "레벨", "단계" → BFS + Queue
- "우선순위" → PriorityQueue
```

#### 2. 자료구조 선택
```java
// 성능 중시: ArrayDeque
// 간단함 중시: LinkedList
// 정렬 필요: PriorityQueue
```

#### 3. 구현 검증
```java
// 경계 조건 확인
- 빈 스택/큐 처리
- 크기 1인 경우
- 모든 원소가 같은 경우
```

### 디버깅 체크포인트
1. **삽입/삭제 순서**: LIFO vs FIFO 올바른 적용
2. **경계 조건**: 빈 컨테이너 처리
3. **인덱스 관리**: 배열 인덱스와 값의 구분
4. **메모리 누수**: 불필요한 객체 보관 여부

**Stack과 Queue는 알고리즘의 기초 중의 기초입니다. 개념을 정확히 이해하고 패턴을 익혀두세요!** 🚀