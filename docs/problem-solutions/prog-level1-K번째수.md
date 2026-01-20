# 프로그래머스 Lv1 - K번째 수

## 📋 문제 정보
- **문제명**: K번째 수
- **플랫폼**: 프로그래머스 Lv1  
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/42748
- **파일명**: `day2_1.java`
- **완료일**: 2024-01-09 (Day 2)

## 🎯 문제 분석

배열에서 특정 구간을 자르고 정렬한 후 K번째 수를 구하는 문제입니다.

### 문제 설명
배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째 있는 수를 구하는 문제입니다.

### 예시
```
array = [1, 5, 2, 6, 3, 7, 4]
commands = [[2,5,3], [4,4,1], [1,7,3]]

1. [2,5,3]: 2~5번째 → [5,2,6,3] → [2,3,5,6] → 3번째 = 5
2. [4,4,1]: 4~4번째 → [6] → [6] → 1번째 = 6  
3. [1,7,3]: 1~7번째 → [1,5,2,6,3,7,4] → [1,2,3,4,5,6,7] → 3번째 = 3

결과: [5, 6, 3]
```

## 💡 사용자 원본 코드 분석

```java
public int[] solution(int[] array, int[][] commands) {
    int[] answer = {}; // 문제: 빈 배열을 선언했지만 사용하지 않음
    List<Integer> answerList = new ArrayList<Integer>();
    for (int i = 0; i < commands.length; i++) {
        int start = commands[i][0] - 1;
        int end = commands[i][1] - 1;
        List<Integer> list = new ArrayList<Integer>();
        for (int j = start; j <= end; j++) {
            list.add(array[j]);
        }
        list.sort(Integer::compare);
        answerList.add(list.get(commands[i][2] - 1));
    }
    return answerList.stream().mapToInt(i -> i).toArray();
}
```

**✅ 원본 코드 평가:**
- **정확성**: 완전히 정확한 로직으로 올바른 결과 도출
- **장점**: List 활용으로 동적 크기 관리, 명확한 단계별 처리
- **개선점**: 불필요한 변수(`answer` 배열), List-Stream 변환 오버헤드

## 🚀 개선된 해법들

### 1. 배열 기반 최적화 해법
```java
public int[] solutionImproved(int[] array, int[][] commands) {
    int[] answer = new int[commands.length];
    
    for (int i = 0; i < commands.length; i++) {
        int start = commands[i][0] - 1;  // 1-기반 → 0-기반 변환
        int end = commands[i][1] - 1;    
        int k = commands[i][2] - 1;      
        
        // Arrays.copyOfRange로 부분 배열 추출
        int[] subArray = Arrays.copyOfRange(array, start, end + 1);
        Arrays.sort(subArray);
        answer[i] = subArray[k];
    }
    
    return answer;
}
```

### 2. 스트림 기반 간결 해법
```java
public int[] solutionStream(int[] array, int[][] commands) {
    return Arrays.stream(commands)
            .mapToInt(cmd -> {
                int[] sub = Arrays.copyOfRange(array, cmd[0] - 1, cmd[1]);
                Arrays.sort(sub);
                return sub[cmd[2] - 1];
            })
            .toArray();
}
```

## 🔍 핵심 패턴 분석

### 1. 부분 배열 추출 패턴
```java
// 방법 1: 직접 복사 (원본 방식)
List<Integer> list = new ArrayList<>();
for (int j = start; j <= end; j++) {
    list.add(array[j]);
}

// 방법 2: Arrays.copyOfRange (권장)
int[] subArray = Arrays.copyOfRange(array, start, end + 1);
```

### 2. 인덱스 변환 패턴
```java
// 문제에서 1-기반 인덱스 → 자바의 0-기반 인덱스
int start = commands[i][0] - 1;  // i번째 → (i-1)번째
int end = commands[i][1] - 1;    // j번째 → (j-1)번째  
int k = commands[i][2] - 1;      // k번째 → (k-1)번째
```

### 3. 정렬 및 접근 패턴
```java
// List 방식
list.sort(Integer::compare);
int result = list.get(k);

// 배열 방식 (더 효율적)
Arrays.sort(subArray);
int result = subArray[k];
```

## 📊 성능 비교

| 해법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|------|------------|------------|------|------|
| 원본 (List) | O(N×M×log M) | O(M) | 가독성 좋음 | List-Stream 오버헤드 |
| 개선 (Array) | O(N×M×log M) | O(M) | 메모리 효율적 | 코드 길이 증가 |
| 스트림 | O(N×M×log M) | O(M) | 코드 간결 | 스트림 오버헤드 |

*N: commands 길이, M: 부분 배열 평균 길이*

## 💡 학습 포인트

### 1. Arrays.copyOfRange() 활용
```java
// 원본 배열에서 [start, end) 범위 추출
int[] subArray = Arrays.copyOfRange(array, start, end + 1);

// 주의: end는 exclusive이므로 +1 필요
// [2, 5] 범위라면 copyOfRange(array, 1, 6)
```

### 2. 인덱스 변환 주의사항
```java
// 문제: "2번째부터 5번째까지"
int start = commands[i][0] - 1;  // 2 → 1 (0-기반)
int end = commands[i][1] - 1;    // 5 → 4 (0-기반)

// copyOfRange는 end가 exclusive
Arrays.copyOfRange(array, start, end + 1);  // [1, 5)
```

### 3. 정렬 알고리즘 선택
```java
// 작은 배열: Arrays.sort() (Dual-Pivot Quicksort)
Arrays.sort(subArray);

// List: sort() 메서드  
list.sort(Integer::compare);
// 또는
Collections.sort(list);
```

### 4. List vs Array 성능
```java
// List 방식 - 동적이지만 오버헤드
List<Integer> list = new ArrayList<>();
list.add(element);

// Array 방식 - 고정 크기지만 효율적
int[] array = new int[size];
array[i] = element;
```

## 🎯 테스트 케이스

| array | commands | 예상 결과 | 검증 포인트 |
|-------|----------|----------|------------|
| `[1,5,2,6,3,7,4]` | `[[2,5,3],[4,4,1],[1,7,3]]` | `[5,6,3]` | 기본 케이스 |
| `[1,2,3,4,5]` | `[[1,5,1],[1,5,5]]` | `[1,5]` | 첫째/마지막 요소 |
| `[5,4,3,2,1]` | `[[1,3,2]]` | `[4]` | 역순 정렬 |
| `[1]` | `[[1,1,1]]` | `[1]` | 단일 요소 |

## 🔗 관련 개념

### Arrays 유틸리티 메서드
```java
// 배열 복사
Arrays.copyOfRange(original, from, to)
Arrays.copyOf(original, newLength)

// 정렬
Arrays.sort(array)
Arrays.sort(array, comparator)

// 검색 (정렬된 배열에서)
Arrays.binarySearch(array, key)

// 배열 비교 및 출력
Arrays.equals(array1, array2)
Arrays.toString(array)
```

### 정렬 관련 패턴
- **부분 배열 정렬**: 특정 구간만 정렬
- **K번째 요소**: QuickSelect 알고리즘 (고급)
- **다중 기준 정렬**: Comparator 활용

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐ (인덱스 변환 주의)
- **최적화 난이도**: ⭐⭐⭐ (여러 구현 방식 비교)
- **실수 가능성**: ⭐⭐ (1-기반 vs 0-기반 인덱스)

## 🎁 보너스 팁

### 디버깅을 위한 출력
```java
System.out.println("구간: [" + start + ", " + end + "]");
System.out.println("부분배열: " + Arrays.toString(subArray));
System.out.println("정렬후: " + Arrays.toString(subArray));
System.out.println("K번째: " + subArray[k]);
```

### 메모리 최적화 (심화)
```java
// 매번 새 배열 생성하지 않고 기존 배열 활용
public int findKthElement(int[] array, int start, int end, int k) {
    // 부분 배열을 별도로 만들지 않고 정렬
    // (원본 배열 수정이 허용되는 경우)
}
```

---

**Day 2 완료! Arrays 클래스와 배열 조작의 핵심 패턴을 마스터했습니다! 🎯**