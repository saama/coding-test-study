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

### 성공여부 체크시 정답 코드 작성 규칙
- 사용자가 작성한 원본코드 유지하고 하위에 새로운 정답 코드 작성해줘
- 사용자가 작성한 코드에 잘못된 점을 주석으로 남겨줘
- 무조건 한글로 작성할 것

### 자바파일 명 규칙
- docs/study-plans/6weeks-curriculum.md에서 #### Day 2 - 배열, 조건문 [프로그래머스] K번째 수 (Lv1)는 day2_1.java로 명명 

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

### Day 1 완료 ✅ (2024-01-08)
- [x] **완주하지 못한 선수** (프로그래머스 Lv1) - HashMap + getOrDefault 패턴
- [x] **신규 아이디 추천** (프로그래머스 Lv1) - 정규식 패턴 7단계 처리
- [x] **문자열 반복** (백준 2675) - StringBuilder + 다중 테스트케이스

### Day 1 학습 성과
- ✅ HashMap의 getOrDefault() 활용한 빈도수 카운팅 마스터
- ✅ 정규식 패턴 완벽 가이드 작성 (`docs/algorithm-notes/regex-patterns-guide.md`)
- ✅ 시간복잡도 최적화 (O(N²) → O(N)) 체험
- ✅ 문자열 처리 7단계 구현 및 디버깅 기법
- ✅ 다중 테스트케이스 처리 패턴

### Day 2 진행 중 🔄 (2024-01-09)
- [x] **K번째 수** (프로그래머스 Lv1) - `day2_1.java` 완료 및 개선
- 🔄 **실패율** (프로그래머스 Lv1) - `day2_2.java` 문제점 분석 완료, 복습 필요

### Day 2 학습 성과
- ✅ Arrays.copyOfRange() 활용한 부분 배열 추출 마스터  
- ✅ Arrays 클래스 완벽 가이드 작성 (`docs/algorithm-notes/arrays-utility-guide.md`)
- ✅ 다중 조건 정렬 (실패율 내림차순 → 스테이지 번호 오름차순)
- ✅ 0으로 나누기 방지 및 실수 계산 패턴
- ⚠️ 복잡한 구현 문제에서의 디버깅 기법 (day2_2 복습 예정)

### 기초 알고리즘
- [x] 입출력 및 기본 문법 (FastScanner, BufferedReader)
- [x] 배열과 문자열 (StringBuilder, 정규식)
- [x] HashMap 활용 (빈도수 카운팅, getOrDefault)
- [x] 배열 조작 (Arrays.copyOfRange, 정렬, 이진탐색)

### 핵심 알고리즘  
- [ ] DFS/BFS
- [ ] 동적 프로그래밍
- [ ] 그리디 알고리즘

### 고급 알고리즘
- [ ] 그래프 알고리즘
- [ ] 트리 알고리즘
- [ ] 고급 자료구조

## 📊 현재 학습 현황

### 해결한 문제 (4개)
1. 완주하지 못한 선수 - HashMap 빈도수 패턴
2. 신규 아이디 추천 - 정규식 7단계 처리
3. 문자열 반복 - StringBuilder + 다중 케이스
4. K번째 수 - Arrays.copyOfRange + 정렬

### 진행 중인 문제 (1개)
- 실패율 - 다중 조건 정렬, 실수 계산 (복습 필요)

### 생성된 학습 자료
- `docs/problem-solutions/prog-level1-완주하지못한선수.md` - 상세 문제 해설
- `docs/algorithm-notes/regex-patterns-guide.md` - 정규식 완벽 가이드  
- `docs/algorithm-notes/arrays-utility-guide.md` - Java Arrays 클래스 완벽 가이드
- `docs/algorithm-notes/coding-test-patterns.md` - 핵심 패턴 모음
- `docs/algorithm-notes/java-best-practices.md` - Java 베스트 프랙티스
- `docs/study-plans/6weeks-curriculum.md` - 6주 커리큘럼

### 다음 목표
- **내일 우선**: day2_2 실패율 문제 복습 완료
- Day 2 완료: 배열 조작, 정렬 기초 마무리
- Day 3: 문자열 파싱 심화 (다트 게임, 숫자 문자열과 영단어)
- Week 1 목표: 기본기 & 구현 복구

### 복습 체크리스트
- [ ] day2_2 실패율 계산 공식: `(double) fail / total`
- [ ] 0으로 나누기 방지: `(total == 0) ? 0.0 : ...`  
- [ ] 다중 조건 정렬: 실패율 내림차순 → 스테이지 번호 오름차순
- [ ] Arrays 클래스 활용법 전반 복습

---

**Happy Coding! 🎯**

*Last Updated: 2024-01-09*
