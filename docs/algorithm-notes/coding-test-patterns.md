# 코딩테스트 핵심 패턴 & 학습 팁

## 🎯 알고리즘별 핵심 패턴

### 1. 자료구조 활용 패턴

#### HashMap 패턴
```java
// 빈도수 계산
Map<String, Integer> countMap = new HashMap<>();
for (String item : items) {
    countMap.put(item, countMap.getOrDefault(item, 0) + 1);
}

// 그룹핑
Map<String, List<String>> groupMap = new HashMap<>();
for (String item : items) {
    groupMap.computeIfAbsent(getKey(item), k -> new ArrayList<>()).add(item);
}
```

#### Stack 패턴
```java
// 괄호 검증
Stack<Character> stack = new Stack<>();
for (char c : str.toCharArray()) {
    if (c == '(') stack.push(c);
    else if (c == ')' && !stack.isEmpty()) stack.pop();
    else return false; // 잘못된 괄호
}
return stack.isEmpty();

// 다음 큰 원소 찾기 (NGE)
Stack<Integer> stack = new Stack<>();
int[] result = new int[arr.length];
for (int i = arr.length - 1; i >= 0; i--) {
    while (!stack.isEmpty() && stack.peek() <= arr[i]) {
        stack.pop();
    }
    result[i] = stack.isEmpty() ? -1 : stack.peek();
    stack.push(arr[i]);
}
```

#### Queue 패턴
```java
// 순서대로 처리
Queue<Task> queue = new LinkedList<>();
while (!queue.isEmpty()) {
    Task current = queue.poll();
    // 처리 로직
    if (hasNext) queue.offer(nextTask);
}

// 우선순위 큐 (최소힙)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// 우선순위 큐 (최대힙)  
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

---

### 2. DFS/BFS 핵심 패턴

#### 2D 배열 BFS 템플릿
```java
public int bfs(int[][] grid) {
    int n = grid.length, m = grid[0].length;
    boolean[][] visited = new boolean[n][m];
    Queue<int[]> queue = new LinkedList<>();
    
    // 방향 벡터 (상하좌우)
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    
    queue.offer(new int[]{startX, startY});
    visited[startX][startY] = true;
    
    while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int x = current[0], y = current[1];
        
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            // 범위 체크
            if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
            // 방문 체크 + 조건 체크
            if (visited[nx][ny] || grid[nx][ny] == 0) continue;
            
            visited[nx][ny] = true;
            queue.offer(new int[]{nx, ny});
        }
    }
}
```

#### DFS 템플릿
```java
public void dfs(int x, int y, int[][] grid, boolean[][] visited) {
    visited[x][y] = true;
    
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    
    for (int i = 0; i < 4; i++) {
        int nx = x + dx[i];
        int ny = y + dy[i];
        
        if (nx < 0 || nx >= grid.length || ny < 0 || ny >= grid[0].length) continue;
        if (visited[nx][ny] || grid[nx][ny] == 0) continue;
        
        dfs(nx, ny, grid, visited);
    }
}
```

#### 그래프 DFS (인접리스트)
```java
List<List<Integer>> graph = new ArrayList<>();
boolean[] visited = new boolean[n];

void dfs(int node) {
    visited[node] = true;
    
    for (int next : graph.get(node)) {
        if (!visited[next]) {
            dfs(next);
        }
    }
}
```

---

### 3. 정렬 & 이분탐색 패턴

#### 커스텀 정렬
```java
// 문자열 길이순 정렬
Arrays.sort(strings, (a, b) -> Integer.compare(a.length(), b.length()));

// 2차원 배열 정렬 (첫번째 요소 기준)
Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));

// 여러 조건 정렬
Arrays.sort(students, (a, b) -> {
    if (a.grade != b.grade) return Integer.compare(b.grade, a.grade); // 성적 내림차순
    return a.name.compareTo(b.name); // 이름 오름차순
});
```

#### 이분탐색 템플릿
```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1; // 찾지 못함
}

// 파라메트릭 서치 (조건을 만족하는 최대/최소값)
boolean canSolve(int value) { /* 조건 체크 */ }

int parametricSearch(int left, int right) {
    int answer = -1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (canSolve(mid)) {
            answer = mid;
            left = mid + 1; // 더 큰 값 탐색
        } else {
            right = mid - 1;
        }
    }
    return answer;
}
```

---

### 4. 그리디 알고리즘 패턴

#### 활동 선택 문제 (회의실 배정)
```java
Arrays.sort(meetings, (a, b) -> Integer.compare(a[1], b[1])); // 끝나는 시간 기준 정렬

int count = 1;
int lastEndTime = meetings[0][1];
for (int i = 1; i < meetings.length; i++) {
    if (meetings[i][0] >= lastEndTime) {
        count++;
        lastEndTime = meetings[i][1];
    }
}
```

#### 최소 동전 문제
```java
Arrays.sort(coins, Collections.reverseOrder()); // 큰 동전부터
int count = 0;
for (int coin : coins) {
    count += amount / coin;
    amount %= coin;
}
```

---

## 📋 문제별 접근법 판단 기준

### 문제 유형 식별법

1. **자료구조 문제**
   - 키워드: "가장 최근", "순서", "우선순위", "빈도수"
   - Stack: 괄호, 후입선출, 가장 최근
   - Queue: 대기열, 선입선출, BFS  
   - Heap: 최대/최소값, 우선순위
   - Hash: 빈도수, O(1) 검색

2. **DFS/BFS 문제**  
   - 키워드: "연결", "경로", "최단거리", "영역", "섬"
   - BFS: 최단거리, 레벨별 탐색
   - DFS: 모든 경로, 순열/조합, 백트래킹

3. **DP 문제** (6주 커리큘럼에서는 우선순위 낮음)
   - 키워드: "최적해", "경우의 수", "최대/최소", "부분문제"

4. **그리디 문제**
   - 키워드: "최대", "최소", 정렬 후 선택이 명확한 경우

---

## ⚠️ 자주하는 실수 & 해결법

### 1. 인덱스 실수
```java
// 잘못된 예
for (int i = 0; i <= arr.length; i++) // ArrayIndexOutOfBoundsException

// 올바른 예  
for (int i = 0; i < arr.length; i++)
```

### 2. 문자열 비교 실수
```java
// 잘못된 예
if (str1 == str2) // 참조 비교

// 올바른 예
if (str1.equals(str2)) // 값 비교
```

### 3. 배열 초기화 실수
```java
// 2D 배열 초기화 주의
int[][] arr = new int[n][m]; // 자동으로 0으로 초기화
boolean[][] visited = new boolean[n][m]; // false로 초기화
```

### 4. 큐 빈 상태 체크 누락
```java
// 잘못된 예
int element = queue.poll(); // null이 나올 수 있음

// 올바른 예  
if (!queue.isEmpty()) {
    int element = queue.poll();
}
```

---

## 🔥 시간복잡도 최적화 팁

### 시간복잡도별 문제 크기 가이드
- O(1): 상수 시간
- O(log N): N ≤ 10^6
- O(N): N ≤ 10^6  
- O(N log N): N ≤ 10^5
- O(N²): N ≤ 3,000
- O(N³): N ≤ 300

### 최적화 기법
1. **불필요한 연산 제거**: 반복문 안에서 동일한 계산 반복 피하기
2. **조기 종료**: 조건을 만족하면 즉시 return
3. **자료구조 선택**: HashMap vs TreeMap, ArrayList vs LinkedList
4. **캐싱**: 이미 계산한 값 저장해서 재사용

---

## 💡 Java 코딩테스트 필수 문법

### String 조작
```java
String str = "hello world";
str.charAt(0);           // 'h'
str.substring(0, 5);     // "hello"  
str.split(" ");          // ["hello", "world"]
str.replace("l", "x");   // "hexxo worxd"
str.toUpperCase();       // "HELLO WORLD"
```

### StringBuilder 활용
```java
StringBuilder sb = new StringBuilder();
sb.append("hello");
sb.append(" ");
sb.append("world");
String result = sb.toString(); // "hello world"
```

### Collections 유틸
```java
Collections.sort(list);                    // 오름차순 정렬
Collections.sort(list, Collections.reverseOrder()); // 내림차순
Collections.min(list);                     // 최솟값
Collections.max(list);                     // 최댓값
Collections.frequency(list, element);      // 빈도수
```

이제 본격적으로 코딩테스트 준비를 시작하면 됩니다! 🚀