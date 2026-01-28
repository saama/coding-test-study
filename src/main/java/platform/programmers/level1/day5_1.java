package platform.programmers.level1;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 프로그래머스 Lv1 - 문자열 내 마음대로 정렬하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/12915
 * 시간복잡도: O(N log N) (정렬 알고리즘)
 * 공간복잡도: O(N) (배열 복사)
 */
public class day5_1 {
    // 사용자 원본 해법 (Arrays.sort + 커스텀 Comparator)
    public String[] solution(String[] strings, int n) {
        String[] answer = {}; // 문제점: 빈 배열 생성 후 반환
        Arrays.sort(strings,(a,b) -> {
            if(a.charAt(n) != b.charAt(n)){
                return a.charAt(n) - b.charAt(n);
            }else{
                // return a - b; // 문제점: 문자열끼리 - 연산 불가능 (컴파일 에러)
                return 0; // 컴파일 에러 방지용 임시 코드
            }
        });
        return answer; // 문제점: 정렬된 strings가 아닌 빈 answer 반환
    }

    // 개선된 해법 1: 원본 보호 + 올바른 문자열 비교
    public String[] solutionFixed(String[] strings, int n) {
        String[] answer = strings.clone(); // 원본 배열 보호
        Arrays.sort(answer, (a, b) -> {
            if (a.charAt(n) != b.charAt(n)) {
                return a.charAt(n) - b.charAt(n); // n번째 문자 우선 비교
            } else {
                return a.compareTo(b); // 사전순 비교 (올바른 문자열 비교)
            }
        });
        return answer;
    }

    // 개선된 해법 2: Comparator.comparing 활용
    public String[] solutionOptimized(String[] strings, int n) {
        String[] answer = strings.clone();
        Arrays.sort(answer, Comparator
            .comparing((String s) -> s.charAt(n))  // n번째 문자 우선
            .thenComparing(s -> s));                // 문자열 전체 비교
        return answer;
    }

    // 개선된 해법 3: 전통적인 Comparator 구현 (Java 8 이전 스타일)
    public String[] solutionOther(String[] strings, int n) {
        Arrays.sort(strings, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                if(s1.charAt(n) > s2.charAt(n)) return 1;
                else if(s1.charAt(n) == s2.charAt(n)) return s1.compareTo(s2);
                else if(s1.charAt(n) < s2.charAt(n)) return -1;
                else return 0; // 문제점: 실제로는 도달 불가능한 코드
            }
        });
        return strings; // 문제점: 원본 배열 직접 반환 (원본 수정됨)
    }

    public static void main(String[] args) {
        day5_1 solution = new day5_1();
        // 모든 개선된 메서드로 테스트
        System.out.println("Fixed: " + Arrays.toString(solution.solutionFixed(new String[]{"sun", "bed", "car"},1)));
        System.out.println("Optimized: " + Arrays.toString(solution.solutionOptimized(new String[]{"sun", "bed", "car"},1)));
        System.out.println("Other: " + Arrays.toString(solution.solutionOther(new String[]{"sun", "bed", "car"},1)));
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (사용자 코드):
 * - 아이디어: n번째 문자 우선, 같으면 사전순 - 정확한 접근!
 * - Arrays.sort + 커스텀 Comparator 활용 - 적절한 선택
 * - 문제점 1: String[] answer = {}로 빈 배열 생성 후 반환
 * - 문제점 2: return a - b; 문자열 뺄셈 불가능 (컴파일 에러)
 * - 문제점 3: 원본 strings 배열 직접 수정
 *
 * 🚀 개선된 해법들:
 * 1. solutionFixed: 원본 보호 + a.compareTo(b) 올바른 비교
 * 2. solutionOptimized: Comparator.comparing + thenComparing 활용
 * 3. solutionOther: 전통적인 Comparator 구현 (Java 8 이전 스타일)
 *
 * 🔍 핵심 패턴 분석:
 * - 다중 조건 정렬: 우선 조건 → 보조 조건
 * - 문자열 비교: a.compareTo(b) 사용 (사전순)
 * - 원본 보호: clone()으로 복사 후 정렬
 * - 람다식 vs Comparator.comparing 선택
 *
 * 🔍 solutionOther 상세 분석:
 * - 장점: 로직이 명확하고 이해하기 쉬움 (if-else 분기)
 * - 단점 1: 원본 배열 직접 수정 (strings 반환)
 * - 단점 2: else return 0; 도달 불가능한 코드 (Dead Code)
 * - 단점 3: Java 8 이전 스타일로 람다식보다 장황함
 * - 개선 방법: char 비교를 s1.charAt(n) - s2.charAt(n)로 단순화 가능
 *
 * 📊 성능 비교:
 * - 모든 해법: O(N log N) 시간, O(N) 공간
 * - solutionFixed/Optimized: 원본 보호됨
 * - solutionOther: 원본 수정되므로 부수효과 있음
 * - Comparator.comparing이 가장 가독성 좋음
 *
 * 💡 학습 포인트:
 * 1. Arrays.sort 커스텀 정렬 3가지 방법:
 *    - 람다식: (a, b) -> 조건문
 *    - Comparator.comparing: 메서드 체이닝
 *    - 전통적인 Comparator: new Comparator<String>() { ... }
 * 2. 문자열 비교 방법:
 *    - a.compareTo(b): 사전순 비교 (-1, 0, 1 반환)
 *    - a.charAt(i) - b.charAt(i): 문자 ASCII 값 차이
 *    - if (a > b) return 1; if (a < b) return -1; : 명시적 비교
 * 3. 다중 조건 정렬:
 *    - if문 분기: 우선 조건 != 0이면 우선 조건 반환
 *    - Comparator.comparing().thenComparing(): 메서드 체이닝
 *    - 명시적 if-else 분기: 가독성 좋지만 코드 길어짐
 * 4. 원본 보호: clone() 또는 Arrays.copyOf() 활용
 * 5. Java 버전별 스타일:
 *    - Java 7 이하: new Comparator<T>() { ... }
 *    - Java 8+: 람다식 (a, b) -> ...
 *    - Java 8+: Comparator.comparing(...)
 */
