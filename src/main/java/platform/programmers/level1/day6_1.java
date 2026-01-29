package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Lv2 - 오픈채팅방
 * https://school.programmers.co.kr/learn/courses/30/lessons/42888
 * 시간복잡도: O(N * M) (N: 레코드 수, M: 평균 닉네임 수)
 * 공간복잡도: O(N) (Map과 List 저장)
 */
public class day6_1 {
    // 사용자 원본 해법 (2단계 처리 방식)
    public String[] solution(String[] record) {
        String[] answer = {};
        List<String> answerList = new ArrayList<>();

        // 최종 닉네임 결정 (Enter, Change 시에만)
        Map<String,String> lastNickName = new HashMap<>();
        for (int i = 0; i < record.length; i++) {
            if(record[i].split(" ")[0].equals("Enter") || record[i].split(" ")[0].equals("Change")){
                lastNickName.put(record[i].split(" ")[1],record[i].split(" ")[2]); // 최종 닉네임 저장
                if(record[i].split(" ")[0].equals("Enter")){
                    answerList.add(record[i].split(" ")[1]+"님이 들어왔습니다."); // 문제점: 아이디로 임시 저장
                }
            }else{
                answerList.add(record[i].split(" ")[1]+"님이 나갔습니다."); // 문제점: 아이디로 임시 저장
            }
        }

        // 아이디를 최종 닉네임으로 치환 (문제점: replaceAll의 잘못된 사용)
        answer = new String[answerList.size()];
        int k = 0;
        for(String word : answerList){
            for(String id : lastNickName.keySet()){
                word = word.replace(id,lastNickName.get(id)); // 문제점: 의도치 않은 부분 치환 위험
            }
            answer[k++] = word;
        }

        return answer;
    }

    // 개선된 해법 1: 명령어별 분리 처리 + 안전한 치환
    public String[] solutionFixed(String[] record) {
        // 1단계: 최종 닉네임 수집
        Map<String, String> userMap = new HashMap<>();
        for (String rec : record) {
            String[] parts = rec.split(" ");
            String action = parts[0];
            String userId = parts[1];

            if (action.equals("Enter") || action.equals("Change")) {
                String nickname = parts[2];
                userMap.put(userId, nickname); // 최종 닉네임만 저장
            }
        }

        // 2단계: 출력 메시지 생성 (Enter, Leave만)
        List<String> result = new ArrayList<>();
        for (String rec : record) {
            String[] parts = rec.split(" ");
            String action = parts[0];
            String userId = parts[1];

            if (action.equals("Enter")) {
                result.add(userMap.get(userId) + "님이 들어왔습니다.");
            } else if (action.equals("Leave")) {
                result.add(userMap.get(userId) + "님이 나갔습니다.");
            }
            // Change는 출력하지 않음
        }

        return result.toArray(new String[0]);
    }

    // 개선된 해법 2: 한 번에 처리 + 명령어 객체 분리
    public String[] solutionOptimized(String[] record) {
        Map<String, String> userMap = new HashMap<>();
        List<String[]> actions = new ArrayList<>(); // 액션 정보만 저장

        // 1단계: 파싱과 동시에 처리
        for (String rec : record) {
            String[] parts = rec.split(" ");
            String action = parts[0];
            String userId = parts[1];

            if (action.equals("Enter") || action.equals("Change")) {
                userMap.put(userId, parts[2]); // 닉네임 업데이트
            }

            // Enter, Leave만 결과에 포함
            if (action.equals("Enter") || action.equals("Leave")) {
                actions.add(new String[]{action, userId});
            }
        }

        // 2단계: 최종 닉네임으로 메시지 생성
        String[] result = new String[actions.size()];
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i)[0];
            String userId = actions.get(i)[1];
            String nickname = userMap.get(userId);

            if (action.equals("Enter")) {
                result[i] = nickname + "님이 들어왔습니다.";
            } else { // Leave
                result[i] = nickname + "님이 나갔습니다.";
            }
        }

        return result;
    }

    // 개선된 해법 3: Stream API 활용
    public String[] solutionStream(String[] record) {
        // 최종 닉네임 수집
        Map<String, String> userMap = new HashMap<>();
        Arrays.stream(record)
            .map(rec -> rec.split(" "))
            .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Change"))
            .forEach(parts -> userMap.put(parts[1], parts[2]));

        // 결과 생성
        return Arrays.stream(record)
            .map(rec -> rec.split(" "))
            .filter(parts -> parts[0].equals("Enter") || parts[0].equals("Leave"))
            .map(parts -> {
                String action = parts[0];
                String userId = parts[1];
                String nickname = userMap.get(userId);

                if (action.equals("Enter")) {
                    return nickname + "님이 들어왔습니다.";
                } else {
                    return nickname + "님이 나갔습니다.";
                }
            })
            .toArray(String[]::new);
    }

    public static void main(String[] args) {
        day6_1 solution = new day6_1();
        String[] testCase = {"Enter uid1234 Muzi", "Enter uid4567 Prodo",
                           "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"};

        System.out.println("=== Test Results ===");
        System.out.println("original: " + Arrays.toString(solution.solution(testCase)));
        System.out.println("Fixed: " + Arrays.toString(solution.solutionFixed(testCase)));
        System.out.println("Optimized: " + Arrays.toString(solution.solutionOptimized(testCase)));
        System.out.println("Stream: " + Arrays.toString(solution.solutionStream(testCase)));

        // Expected output:
        // ["Prodo님이 들어왔습니다.", "Ryan님이 들어왔습니다.", "Prodo님이 나갔습니다.", "Prodo님이 들어왔습니다."]
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (사용자 코드):
 * - 아이디어: 2단계 처리 (최종 닉네임 수집 → 치환) - 올바른 접근!
 * - HashMap으로 최종 닉네임 관리 - 적절한 선택
 * - 문제점 1: split(" ") 반복 호출 - 비효율적 (매 비교마다 파싱)
 * - 문제점 2: replaceAll() 잘못된 사용 - 의도치 않은 부분 문자열 치환 위험
 * - 문제점 3: Change 명령어 출력 누락 확인 필요
 * - 문제점 4: 배열 크기 미리 계산하지 않고 List 사용 후 변환
 *
 * 🚀 개선된 해법들:
 * 1. solutionFixed: 안전한 치환 + 명확한 분리 처리
 * 2. solutionOptimized: 액션 객체 분리로 메모리 최적화
 * 3. solutionStream: Stream API로 함수형 스타일
 *
 * 🔍 핵심 패턴 분석:
 * - 2단계 처리: 데이터 수집 → 결과 생성 (일반적 패턴)
 * - 최신 상태 관리: Map의 put으로 자동 업데이트
 * - 명령어 분류: Enter/Leave (출력) vs Change (업데이트만)
 * - 안전한 문자열 치환: replaceAll 대신 직접 조합
 *
 * 📊 성능 비교:
 * - 원본: O(N * M * K) (N: 레코드, M: 유저 수, K: 평균 문자열 길이)
 * - 개선: O(N) (각 레코드를 최대 2번 순회)
 * - 메모리: O(N) (Map + 결과 배열)
 *
 * 💡 학습 포인트:
 * 1. 문자열 처리 최적화:
 *    - split() 결과 재사용 vs 반복 호출
 *    - replaceAll() vs 직접 문자열 조합
 * 2. 2단계 처리 패턴:
 *    - 데이터 수집 단계 vs 결과 생성 단계 분리
 *    - 최신 상태만 유지하여 메모리 절약
 * 3. 명령어 패턴 처리:
 *    - 조건별 분기 vs 명령어 객체 활용
 * 4. Map 활용 패턴:
 *    - put()의 자동 업데이트 특성 활용
 *    - 최종 상태만 관리하는 전략
 * 5. 배열 vs List 선택:
 *    - 크기 예측 가능: 배열
 *    - 크기 가변적: List → toArray()
 */
