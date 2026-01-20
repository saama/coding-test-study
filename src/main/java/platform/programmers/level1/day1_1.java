package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Lv1 - 완주하지 못한 선수
 * https://school.programmers.co.kr/learn/courses/30/lessons/42576
 * 시간복잡도: O(N²) → O(N) 최적화
 * 공간복잡도: O(N)
 */
public class day1_1 {
    
    // 사용자 원본 해법 (이중 반복문 사용)
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        for(int i = 0; i < participant.length; i++){
            boolean isSame = false;
            for(int j = 0; j < completion.length; j++){ // O(N²) 시간복잡도 - 비효율적
                if(participant[i].equals(completion[j])){
                    isSame = true;
                    break;
                }
            }
            if(!isSame){
                answer = participant[i];
                break; // 동명이인이 있으면 잘못된 결과 가능
            }
        }
        return answer;
        
        // ✅ 원본 로직은 기본 케이스에서 정확함. 다만 동명이인과 효율성 개선 필요
    }
    
    // 개선된 해법 (HashMap 활용 - O(N) 시간복잡도)
    public String solutionOptimized(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();

        // 참가자들을 HashMap에 등록 (빈도수 카운팅)
        for (String name : participant) {
            map.put(name, map.getOrDefault(name, 0) + 1);
        }

        // 완주자들을 HashMap에서 차감
        for (String name : completion) {
            map.put(name, map.get(name) - 1);
        }

        // 카운트가 0이 아닌 선수가 완주하지 못한 선수
        for (String name : map.keySet()) {
            if (map.get(name) != 0) {
                return name;
            }
        }

        return "";
    }
    
    // 원본 코드 약간 개선 (동명이인 처리 추가)
    public String solutionImproved(String[] participant, String[] completion) {
        List<String> completionList = new ArrayList<>(Arrays.asList(completion));

        for (String name : participant) {
            if (completionList.contains(name)) {
                completionList.remove(name); // 동명이인 처리
            } else {
                return name;
            }
        }
        return "";
        // 여전히 O(N²) 시간복잡도지만 동명이인 문제는 해결
    }

    public static void main(String[] args) {
        day1_1 sol = new day1_1();

        // 테스트 케이스 1
        String[] participant1 = {"leo", "kiki", "eden"};
        String[] completion1 = {"eden", "kiki"};
        System.out.println("원본 결과: " + sol.solution(participant1, completion1)); // "leo"
        System.out.println("개선 결과: " + sol.solutionOptimized(participant1, completion1)); // "leo"

        // 테스트 케이스 2
        String[] participant2 = {"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] completion2 = {"josipa", "filipa", "marina", "nikola"};
        System.out.println("원본 결과: " + sol.solution(participant2, completion2)); // "vinko"
        System.out.println("개선 결과: " + sol.solutionOptimized(participant2, completion2)); // "vinko"

        // 테스트 케이스 3 (동명이인 - 원본 코드의 한계)
        String[] participant3 = {"mislav", "stanko", "mislav", "ana"};
        String[] completion3 = {"stanko", "ana", "mislav"};
        System.out.println("\n=== 동명이인 테스트 ===");
        System.out.println("원본 결과: " + sol.solution(participant3, completion3)); // 잘못된 결과 가능
        System.out.println("개선 결과: " + sol.solutionOptimized(participant3, completion3)); // "mislav"
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (이중 반복문):
 * - 정확성: 기본 케이스에서는 완전히 정확
 * - 시간복잡도: O(N²) - 대용량 데이터에서 비효율적
 * - 동명이인 처리: 첫 번째로 매칭되는 이름만 처리하여 오류 가능성
 * - 가독성: 직관적이고 이해하기 쉬운 로직
 *
 * 🚀 개선된 해법 (HashMap 활용):
 * - 시간복잡도: O(N) - 각 배열을 한 번씩만 순회
 * - 공간복잡도: O(N) - HashMap 저장 공간
 * - 동명이인 처리: 빈도수 카운팅으로 완벽 처리
 * - 핵심 패턴: getOrDefault() 활용한 안전한 카운팅
 *
 * 학습 포인트:
 * 1. HashMap의 getOrDefault() - 안전한 기본값 설정
 * 2. 빈도수 카운팅 패턴 - 중복 요소 처리의 표준 방법
 * 3. 시간복잡도 최적화 - O(N²) → O(N) 극적 개선
 * 4. 동명이인 케이스 - 실제 코테에서 자주 나오는 함정
 * 5. contains()와 remove()의 O(N) 복잡도 주의
 */