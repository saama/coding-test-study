package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Level 1 - 완주하지 못한 선수 (개선된 풀이)
 *
 * 원래 코드의 문제점:
 * 1. 시간복잡도 O(N²) - 비효율적
 * 2. 동명이인 처리 불가
 * 3. 매번 completion 배열 전체 탐색
 */
public class day1_1 {

    // ✅ 최적 해법: HashMap 사용 (시간복잡도 O(N))
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();

        // 참가자들을 HashMap에 등록 (빈도수 카운트)
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

    // 📝 원래 코드 (참고용 - 문제점 있음)
    public String originalSolution(String[] participant, String[] completion) {
        String answer = "";
        for(int i=0; i<participant.length; i++){
            boolean isSame = false;
            for(int j=0; j<completion.length; j++){
                if(participant[i].equals(completion[j])){
                    isSame = true;
                    break;
                }
            }
            if(!isSame){
                answer = participant[i];
                break;
            }
        }
        return answer;
    }

    // 🔧 원래 코드 약간 개선 (여전히 O(N²))
    public String improvedOriginal(String[] participant, String[] completion) {
        List<String> completionList = new ArrayList<>(Arrays.asList(completion));

        for (String name : participant) {
            if (completionList.contains(name)) {
                completionList.remove(name); // 동명이인 처리
            } else {
                return name;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        day1_1 sol = new day1_1();

        // 테스트 케이스 1
        String[] participant1 = {"leo", "kiki", "eden"};
        String[] completion1 = {"eden", "kiki"};
        System.out.println("Test 1: " + sol.solution(participant1, completion1)); // "leo"

        // 테스트 케이스 2
        String[] participant2 = {"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] completion2 = {"josipa", "filipa", "marina", "nikola"};
        System.out.println("Test 2: " + sol.solution(participant2, completion2)); // "vinko"

        // 테스트 케이스 3 (동명이인 - 원래 코드로는 처리 불가)
        String[] participant3 = {"mislav", "stanko", "mislav", "ana"};
        String[] completion3 = {"stanko", "ana", "mislav"};
        System.out.println("Test 3: " + sol.solution(participant3, completion3)); // "mislav"

        System.out.println("\n=== 원래 코드로 테스트 3 실행 ===");
        System.out.println("원래 코드 결과: " + sol.originalSolution(participant3, completion3)); // 잘못된 결과
    }
}

/*
 * 💡 학습 포인트:
 *
 * 1. HashMap의 활용
 *    - getOrDefault(): 안전한 기본값 설정
 *    - 빈도수 카운팅 패턴 숙지
 *
 * 2. 시간복잡도 개선
 *    - O(N²) → O(N) 으로 대폭 개선
 *    - 대용량 데이터에서 성능 차이 극명
 *
 * 3. 엣지 케이스 처리
 *    - 동명이인 상황 고려
 *    - 빈 배열, null 체크 (실제 코테에서는 추가 필요)
 *
 * 4. 코드 품질
 *    - 가독성과 효율성 모두 고려
 *    - 주석과 테스트 케이스로 검증
 */
