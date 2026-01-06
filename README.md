# 코딩테스트 스터디 프로젝트

자바를 사용한 코딩테스트 준비를 위한 체계적인 스터디 프로젝트입니다.

## 📁 프로젝트 구조

```
codingtest-study/
├── src/main/java/
│   ├── algorithm/           # 알고리즘별 분류
│   │   ├── search/         # 탐색 알고리즘
│   │   │   ├── binary/     # 이진탐색
│   │   │   ├── bfs/        # 너비우선탐색
│   │   │   └── dfs/        # 깊이우선탐색
│   │   ├── sort/           # 정렬 알고리즘
│   │   ├── graph/          # 그래프 알고리즘
│   │   ├── dp/             # 동적 프로그래밍
│   │   ├── greedy/         # 그리디 알고리즘
│   │   ├── string/         # 문자열 처리
│   │   └── math/           # 수학 관련
│   ├── platform/           # 플랫폼별 분류
│   │   ├── baekjoon/       # 백준 문제
│   │   ├── programmers/    # 프로그래머스 문제
│   │   └── leetcode/       # 리트코드 문제
│   └── utils/              # 유틸리티 클래스
├── templates/              # 코드 템플릿
├── docs/                   # 문서화
│   ├── algorithm-notes/    # 알고리즘 이론 정리
│   ├── problem-solutions/  # 문제 해설
│   └── study-plans/        # 학습 계획
├── build.gradle           # Gradle 빌드 설정
└── README.md
```

## 🚀 시작하기

### 1. 프로젝트 빌드
```bash
./gradlew build
```

### 2. 코드 실행
```bash
./gradlew run
```

### 3. 테스트 실행
```bash
./gradlew test
```

## 🛠️ 유틸리티 클래스

### FastScanner
빠른 입력 처리를 위한 스캐너 클래스
```java
FastScanner sc = new FastScanner();
int n = sc.nextInt();
String s = sc.next();
```

### InputReader
경쟁 프로그래밍용 고성능 입력 리더
```java
InputReader in = new InputReader(System.in);
int[] arr = in.nextIntArray(n);
```

### TestUtils
테스트 및 디버깅을 위한 유틸리티 함수들
```java
String result = TestUtils.runWithInput("5 3", () -> {
    // 테스트할 코드
});
```

## 📋 코드 템플릿

### BasicTemplate.java
기본적인 코딩테스트 템플릿

### FastIOTemplate.java
빠른 입출력을 위한 템플릿

### CompetitiveProgramming.java
경쟁 프로그래밍을 위한 고급 템플릿 (유틸리티 함수 포함)

## 📚 학습 가이드

### 1. 알고리즘 학습
`docs/algorithm-notes/` 디렉토리에서 각 알고리즘의 이론과 구현을 학습하세요.

### 2. 문제 해결
1. 플랫폼별 디렉토리에서 문제를 풀어보세요
2. 해결 후 `docs/problem-solutions/`에 해설을 작성하세요

### 3. 진도 관리
`docs/study-plans/`에서 학습 계획을 세우고 진도를 체크하세요.

## 💡 사용 팁

### 새 문제 시작하기
1. 해당 알고리즘 분류의 패키지에 새 Java 파일 생성
2. 템플릿을 복사해서 기본 구조 설정
3. 문제 해결 후 docs에 해설 작성

### 코드 작성 규칙
- 클래스명은 문제번호나 문제명으로 명명
- 주석으로 문제 링크와 간단한 설명 추가
- 시간복잡도와 공간복잡도 명시

### 예시
```java
package algorithm.dp;

/**
 * 백준 1463번 - 1로 만들기
 * https://www.acmicpc.net/problem/1463
 * 시간복잡도: O(N), 공간복잡도: O(N)
 */
public class BOJ1463 {
    public static void main(String[] args) {
        // 구현
    }
}
```

## 📈 진도 체크리스트

### 기초 알고리즘
- [ ] 입출력 및 기본 문법
- [ ] 배열과 문자열
- [ ] 정렬과 탐색

### 핵심 알고리즘
- [ ] DFS/BFS
- [ ] 동적 프로그래밍
- [ ] 그리디 알고리즘

### 고급 알고리즘
- [ ] 그래프 알고리즘
- [ ] 트리 알고리즘
- [ ] 고급 자료구조

---

**Happy Coding! 🎯**