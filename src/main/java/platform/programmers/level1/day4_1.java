package platform.programmers.level1;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 프로그래머스 Lv1 - 신고 결과 받기
 * https://school.programmers.co.kr/learn/courses/30/lessons/92334
 * 시간복잡도: O(N²) → O(N) 최적화 가능
 * 공간복잡도: O(N)
 * 2022 KAKAO BLIND RECRUITMENT
 */
public class day4_1 {

    // 사용자 원본 해법 (Map 3개 활용)
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = {}; // 크기 설정 필요

        Map<String, Set<String>> callers = new HashMap<>();
        Map<String, Integer> banneder = new HashMap<>();
        Map<String, Integer> reciever = new HashMap<>();

        for (int i = 0; i < id_list.length; i++) {
            //1. 신고한 유저 중복제거해서 넣기 caller
            for (int j = 0; j < report.length; j++) { // O(N²) 시간복잡도 - 개선 가능
                if(report[j].split(" ")[0].equals(id_list[i])) {
                    callers.put(id_list[i],Set.of(report[j].split(" ")[1])); // 문제: Set.of()는 단일 요소만 저장, 중복 제거 안됨
//                    System.out.println(callers);
                }
            }
        }

        //2. 신고당한 유저 count banneder
        for (int j = 0; j < report.length; j++) {
            banneder.put(report[j].split(" ")[1],banneder.getOrDefault(report[j].split(" ")[1],0) + 1); // 좋은 패턴: getOrDefault 활용
        }

        //3. 신고한 유저가 몇개의 메일을 받는지 체크
        for(String name : banneder.keySet()){
            if(banneder.get(name) >= k){
                for(String caller : callers.keySet()){ // 중첩 반복문으로 비효율
                    if(callers.get(caller).contains(name)){
                        reciever.put(caller,reciever.getOrDefault(caller,0) + 1);
                    }
                }
            }
        }
        System.out.println(reciever);

        return answer; // 빈 배열 반환 - 결과 배열 생성 필요

        // ✅ 핵심 아이디어(Map 활용, getOrDefault 패턴)는 훌륭함! Set 사용법과 결과 배열 생성만 수정하면 완성
    }

    // 개선된 해법 (Set 올바른 사용 + 중복 제거)
    public int[] solutionOptimized(String[] id_list, String[] report, int k) {
        // 중복 신고 제거
        Set<String> reportSet = new HashSet<>(Arrays.asList(report));

        // 각 유저가 신고한 사람들 저장 (중복 제거)
        Map<String, Set<String>> reportMap = new HashMap<>();
        // 신고당한 횟수 카운트
        Map<String, Integer> reportedCount = new HashMap<>();

        for (String rep : reportSet) {
            String[] parts = rep.split(" ");
            String reporter = parts[0];
            String reported = parts[1];

            // 신고자별 신고한 사람들 저장
            reportMap.computeIfAbsent(reporter, k1 -> new HashSet<>()).add(reported);
            // 신고당한 횟수 증가
            reportedCount.put(reported, reportedCount.getOrDefault(reported, 0) + 1);
        }

        // 정지된 유저들 찾기
        Set<String> bannedUsers = new HashSet<>();
        for (String user : reportedCount.keySet()) {
            if (reportedCount.get(user) >= k) {
                bannedUsers.add(user);
            }
        }

        // 각 유저별 메일 받을 횟수 계산
        int[] answer = new int[id_list.length];
        for (int i = 0; i < id_list.length; i++) {
            String user = id_list[i];
            Set<String> reportedByUser = reportMap.getOrDefault(user, new HashSet<>());

            int mailCount = 0;
            for (String reported : reportedByUser) {
                if (bannedUsers.contains(reported)) {
                    mailCount++;
                }
            }
            answer[i] = mailCount;
        }

        return answer;
    }

    // 스트림 기반 해법 (함수형 스타일)
    public int[] solutionStream(String[] id_list, String[] report, int k) {
        // 중복 제거 및 파싱
        List<String[]> reports = Arrays.stream(report)
                .distinct()
                .map(r -> r.split(" "))
                .collect(Collectors.toList());

        // 신고당한 횟수 계산
        Map<String, Long> reportedCount = reports.stream()
                .collect(Collectors.groupingBy(
                    r -> r[1],
                    Collectors.counting()
                ));

        // 정지된 유저 찾기
        Set<String> bannedUsers = reportedCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        // 각 유저별 메일 개수 계산
        return Arrays.stream(id_list)
                .mapToInt(user -> (int) reports.stream()
                        .filter(r -> r[0].equals(user) && bannedUsers.contains(r[1]))
                        .count())
                .toArray();
    }

    public static void main(String[] args) {
        day4_1 d = new day4_1();

        String[] id_list = {"muzi", "frodo", "apeach", "neo"};
        String[] report = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
        int k = 2;

//        System.out.println("원본 결과: " + Arrays.toString(d.solution(id_list, report, k)));
        System.out.println("개선 결과: " + Arrays.toString(d.solutionOptimized(id_list, report, k)));
//        System.out.println("스트림 결과: " + Arrays.toString(d.solutionStream(id_list, report, k)));
//        System.out.println("예상 결과: [2, 1, 1, 0]");
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (Map 3개 활용):
 * - 아이디어: Map을 활용한 체계적 접근 - 매우 좋은 사고!
 * - getOrDefault 패턴: null 안전 프로그래밍 완벽 적용
 * - 문제점 1: Set.of() 잘못된 사용 - 단일 요소만 저장, 중복 제거 안됨
 * - 문제점 2: 이중 반복문으로 O(N²) 시간복잡도
 * - 문제점 3: 결과 배열 생성 로직 누락
 * - 문제점 4: 중복 신고 제거 로직 없음
 *
 * 🔍 Set.of() vs HashSet 차이점:
 * - Set.of(element): 불변 Set, 단일/다중 요소 생성용
 * - new HashSet<>(): 가변 Set, 요소 추가/삭제 가능, 중복 자동 제거***
 * - computeIfAbsent(): 키가 없으면 값 생성 후 추가하는 편리한 메서드
 *
 * 🚀 개선된 해법:
 * - 중복 신고 사전 제거: HashSet으로 report 배열 중복 제거
 * - 올바른 Set 사용: HashSet으로 신고 대상 중복 제거 관리
 * - 시간복잡도 최적화: O(N²) → O(N) 개선
 * - 결과 배열 생성: id_list 순서에 맞는 결과 배열 반환
 *
 * 🎓 Set 학습 포인트:
 * 1. Set.of() - 불변 집합 생성 (Java 9+)
 * 2. HashSet - 가변 집합, 중복 자동 제거
 * 3. computeIfAbsent() - 없으면 생성, 있으면 기존 값 반환
 * 4. contains() - O(1) 평균 시간으로 빠른 조회
 * 5. 중복 제거 - HashSet의 핵심 기능
 */
