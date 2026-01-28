package platform.programmers.level1;

import java.util.Arrays;

/**
 * 프로그래머스 Lv1 - 가장 큰 수
 * https://school.programmers.co.kr/learn/courses/30/lessons/42746
 * 시간복잡도: O(N log N) (정렬 알고리즘)
 * 공간복잡도: O(N) (배열 복사 + 문자열 변환)
 */
public class day5_2 {
    // 사용자 원본 해법 (첫 번째 문자만 비교)
    public String solution(int[] numbers) {
        String answer = "";

        // int[] copyNumbers = numbers.clone(); // 문제점: int[] 배열은 Comparator 사용 불가 (컴파일 에러)
        // 문제점: 첫 번째 문자만 비교하는 잘못된 접근
        Integer[] copyNumbers = new Integer[numbers.length]; // 컴파일 에러 임시 수정
        for (int i = 0; i < numbers.length; i++) {
            copyNumbers[i] = numbers[i]; // int를 Integer로 변환
        }
        Arrays.sort(copyNumbers,(a,b) -> {
           // String a1 = a.toString(); // 문제점: int.toString() 메서드 없음 (컴파일 에러)
           // String b1 = b.toString(); // 문제점: int.toString() 메서드 없음 (컴파일 에러)
           String a1 = String.valueOf(a); // 컴파일 에러 임시 수정
           String b1 = String.valueOf(b); // 컴파일 에러 임시 수정
           return b1.charAt(0) - a1.charAt(0); // 문제점: 첫 문자만 비교, 전체 조합 고려 안 함
        });
        
        for (int i = 0; i < copyNumbers.length; i++) {
            answer += ""+copyNumbers[i];
        }

        return answer; // 문제점: "000" → "0"으로 처리하는 엣지케이스 누락
    }
    
    // 개선된 해법 1: 올바른 문자열 비교 (두 수를 이어 붙여서 비교)
    public String solutionFixed(int[] numbers) {
        String[] strNumbers = new String[numbers.length];
        
        // int를 String으로 변환
        for (int i = 0; i < numbers.length; i++) {
            strNumbers[i] = String.valueOf(numbers[i]); // 올바른 문자열 변환
        }
        
        // 핵심: a+b vs b+a 비교로 더 큰 조합 찾기
        Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));
        
        // 모든 수가 0인 경우 처리
        if (strNumbers[0].equals("0")) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        for (String str : strNumbers) {
            sb.append(str);
        }
        
        return sb.toString();
    }
    
    // 개선된 해법 2: 람다식 최적화 + Stream 활용
    public String solutionOptimized(int[] numbers) {
        String result = Arrays.stream(numbers)
            .mapToObj(String::valueOf)                          // int를 String으로 변환
            .sorted((a, b) -> (b + a).compareTo(a + b))         // 조합 비교 정렬
            .reduce("", (acc, str) -> acc + str);               // 문자열 연결
        
        // 엣지케이스: 모든 수가 0인 경우
        return result.startsWith("0") ? "0" : result;
    }

    public static void main(String[] args) {
        day5_2 solution = new day5_2();
        
        // 테스트 케이스들
        System.out.println("=== 테스트 케이스 1: {6, 10, 2} ===");
        System.out.println("Original: " + solution.solution(new int[]{6, 10, 2}));        
        System.out.println("Fixed: " + solution.solutionFixed(new int[]{6, 10, 2}));      // "6210"
        
        System.out.println("=== 테스트 케이스 2: {3, 30, 34, 5, 9} (원본 알고리즘 한계 노출) ===");
        System.out.println("Original: " + solution.solution(new int[]{3, 30, 34, 5, 9}));  // 잘못된 결과 예상
        System.out.println("Optimized: " + solution.solutionOptimized(new int[]{3, 30, 34, 5, 9})); // "9534330"
        
        System.out.println("=== 엣지 케이스: {0, 0, 0} ===");
        System.out.println("Original: " + solution.solution(new int[]{0, 0, 0}));         // "000" (잘못됨)
        System.out.println("Fixed: " + solution.solutionFixed(new int[]{0, 0, 0}));       // "0" (정답)
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (사용자 코드):
 * - 아이디어: 첫 번째 문자 비교로 정렬 - 부분적으로 맞지만 불완전함
 * - Arrays.sort + 커스텀 Comparator 활용 - 적절한 선택
 * - 문제점 1: int[] 배열에 Comparator 사용 불가 (컴파일 에러) - Integer[] 필요
 * - 문제점 2: a.toString(), b.toString() - int에 toString() 메서드 없음 (컴파일 에러)
 * - 문제점 3: 첫 문자만 비교 - 잘못된 접근법 (예: "3" vs "30" → 첫 문자 같음)
 * - 문제점 4: [0, 0, 0] → "000" 반환 (정답: "0")
 * - 문제점 5: String 연결에 += 사용 - 비효율적
 *
 * 🚀 개선된 해법들:
 * 1. solutionFixed: String.valueOf() + (b+a).compareTo(a+b) 비교 + 엣지케이스 처리
 * 2. solutionOptimized: Stream API + reduce 활용으로 더 함수형 스타일
 *
 * 🔍 핵심 패턴 분석:
 * - 가장 큰 수 만들기: a+b vs b+a 조합 비교가 핵심!
 * - 문자열 변환: String.valueOf(int) 사용 (int.toString() 아님)
 * - 정렬 기준: (b+a).compareTo(a+b) → 더 큰 조합이 앞으로
 * - 엣지케이스: 모든 수가 0일 때 "0" 반환
 * - 성능 최적화: StringBuilder 또는 Stream reduce 사용
 *
 * 📊 핵심 알고리즘 예시:
 * numbers = [3, 30, 34, 5, 9]
 * 
 * 비교 과정:
 * - "3" vs "30": "330" vs "303" → "330"이 더 큼 → "3"이 앞
 * - "3" vs "34": "334" vs "343" → "343"이 더 큼 → "34"가 앞  
 * - "34" vs "5": "345" vs "534" → "534"가 더 큼 → "5"가 앞
 * 
 * 최종 정렬: ["9", "5", "34", "3", "30"]
 * 결과: "9534330"
 *
 * 💡 학습 포인트:
 * 1. int → String 변환 방법:
 *    - String.valueOf(int) ← 권장
 *    - Integer.toString(int) 
 *    - int + "" ← 비권장 (가독성 떨어짐)
 * 2. 문자열 조합 비교:
 *    - 단순 사전순 비교 ❌: "3".compareTo("30") → "3" < "30" (잘못됨)
 *    - 조합 비교 ✅: "330".compareTo("303") → "330" > "303" (정답)
 * 3. 엣지케이스 처리:
 *    - 모든 수가 0인 경우: "000..." → "0"
 *    - 빈 배열, null 체크도 고려 가능
 * 4. 성능 최적화:
 *    - String += 연결 ❌ → StringBuilder ✅
 *    - Stream reduce 활용 가능
 * 5. 정렬 Comparator:
 *    - (a, b) → (b+a).compareTo(a+b): 내림차순으로 가장 큰 조합
 */