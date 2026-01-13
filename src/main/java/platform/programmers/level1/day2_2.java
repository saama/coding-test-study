package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Lv1 - 실패율
 * https://school.programmers.co.kr/learn/courses/30/lessons/42889
 * 시간복잡도: O(N²), 공간복잡도: O(N)
 */
public class day2_2 {

    // 사용자 원본 코드 - 여러 문제점 존재
//    public int[] originalSolution(int N, int[] stages) {
//        int[] answer = {}; // 문제: 빈 배열 반환
//        Long[] temp1 = new Long[N]; // 문제: Long 타입 사용, 실제로는 double이어야 함
//        for(int i = 0; i < N; i++){
//            // i+1 = 스테이지
//            int stage = i+1;
//
//            //fail 스테이지 = stages[i] 현재스테이지 도달했으나 실패
//            //tot 스테이지 <= stages[i] 현재스테이지 이상 도달
//            int fail = 0;
//            int tot = 0;
//            for (int j = 0; j < stages.length; j++) {
//                if(stages[j] == stage) fail++;
//                if(stages[j] >= stage) tot++;
//            }
////            temp1[i] = (Long)fail / tot; // 문제: 정수 나눗셈, 0으로 나누기 가능성
//        }
//        System.out.println(Arrays.toString(temp1));
//        return answer; // 문제: 빈 배열 반환, 정렬도 하지 않음
//    }

    public int[] originalSolution(int N, int[] stages) {
        int[] answer = {}; // 문제: 빈 배열 반환

        int[] failCnt = new int[N+2];

        for (int stage : stages) {
            failCnt[stage]++;
        }
//        System.out.println(Arrays.toString(failCnt));;
        int totUserCnt = stages.length;
//        System.out.println("totUserCnt: " + totUserCnt);

//        int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};
        double[][] sortedStages = new double[N][2];
        for(int i = 1; i < failCnt.length-1; i++) {
            int fail = failCnt[i];
            double failRate = totUserCnt == 0 ? 0.0 : (double)fail / totUserCnt;
//            System.out.println(i+" "+fail+" / "+totUserCnt);

            sortedStages[i-1][0] = i;
            sortedStages[i-1][1] = failRate;

            totUserCnt -= fail;
        }

//        for (int i = 0; i < sortedStages.length; i++) {
//            System.out.println(sortedStages[i][0] + " " + sortedStages[i][1]);
//        }

        Arrays.sort(sortedStages,(a,b) -> {
            if(a[1] != b[1]){
                return Double.compare(b[1],a[1]);
            }
            return Double.compare(a[0],b[0]);
        });

//        System.out.println(Arrays.deepToString(sortedStages));

        answer = new int[N];
        for (int i = 0; i < sortedStages.length; i++) {
            answer[i] = (int)sortedStages[i][0];
        }

        return answer;
    }

    // 수정된 해법
    public int[] solution(int N, int[] stages) {
        // 스테이지별 실패율을 저장할 클래스
        class Stage {
            int number;     // 스테이지 번호
            double failRate; // 실패율

            Stage(int number, double failRate) {
                this.number = number;
                this.failRate = failRate;
            }
        }

        List<Stage> stageList = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            int fail = 0;  // 현재 스테이지에서 실패한 사용자 수
            int total = 0; // 현재 스테이지에 도달한 사용자 수

            for (int stage : stages) {
                if (stage == i) fail++;        // 현재 스테이지에서 멈춘 사용자
                if (stage >= i) total++;       // 현재 스테이지에 도달한 사용자
            }

            // 실패율 계산 (0으로 나누기 방지)
            double failRate = (total == 0) ? 0.0 : (double) fail / total;
            stageList.add(new Stage(i, failRate));
        }

        // 실패율 기준 내림차순, 같으면 스테이지 번호 오름차순 정렬
        stageList.sort((a, b) -> {
            if (a.failRate != b.failRate) {
                return Double.compare(b.failRate, a.failRate); // 실패율 내림차순
            }
            return Integer.compare(a.number, b.number); // 스테이지 번호 오름차순
        });

        // 결과 배열 생성
        int[] answer = new int[N];
        for (int i = 0; i < N; i++) {
            answer[i] = stageList.get(i).number;
        }

        return answer;
    }

    // 더 효율적인 해법 (배열 사용)
    public int[] solutionOptimized(int N, int[] stages) {
        // 각 스테이지에 머무른 사용자 수 계산
        int[] stageCounts = new int[N + 2]; // N+1까지 가능하므로 N+2 크기
        for (int stage : stages) {
            stageCounts[stage]++;
        }

        // 스테이지와 실패율을 함께 저장
        double[][] stageFailRates = new double[N][2]; // [스테이지번호, 실패율]

        int totalUsers = stages.length; // 전체 사용자 수

        for (int i = 1; i <= N; i++) {
            int fail = stageCounts[i];     // 현재 스테이지에서 실패한 사용자

            // 실패율 계산
            double failRate = (totalUsers == 0) ? 0.0 : (double) fail / totalUsers;

            stageFailRates[i-1][0] = i;        // 스테이지 번호
            stageFailRates[i-1][1] = failRate; // 실패율

            totalUsers -= fail; // 다음 스테이지로 진행할 사용자 수
        }

        // 실패율 기준 정렬
        Arrays.sort(stageFailRates, (a, b) -> {
            if (a[1] != b[1]) {
                return Double.compare(b[1], a[1]); // 실패율 내림차순
            }
            return Double.compare(a[0], b[0]); // 스테이지 번호 오름차순
        });

        // 결과 반환
        int[] answer = new int[N];
        for (int i = 0; i < N; i++) {
            answer[i] = (int) stageFailRates[i][0];
        }

        return answer;
    }

    public static void main(String[] args) {
        day2_2 obj = new day2_2();
//        int[] stages = {4,4,4,4,4};
        int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};

        System.out.println("수정된 결과: " + Arrays.toString(obj.originalSolution(5, stages)));
//        System.out.println("수정된 결과: " + Arrays.toString(obj.solution(4, stages)));
//        System.out.println("최적화 결과: " + Arrays.toString(obj.solutionOptimized(4, stages)));
//        System.out.println("예상 결과:   [3, 4, 2, 1, 5]");
    }
}

/*
 * 원본 코드 문제점 분석:
 *
 * 1. 타입 오류: Long[] 대신 double 사용해야 함
 * 2. 정수 나눗셈: (Long)fail / tot → (double)fail / tot
 * 3. 0으로 나누기: tot이 0일 때 처리 없음
 * 4. 결과 반환: answer 빈 배열 반환
 * 5. 정렬 누락: 실패율 기준 정렬 안 함
 * 6. 논리 오류: 실패율 계산 후 스테이지 번호 매핑 안 함
 *
 * 수정 사항:
 * 1. double 타입으로 실패율 계산
 * 2. 0으로 나누기 방지 로직 추가
 * 3. Stage 클래스로 스테이지 번호와 실패율 함께 관리
 * 4. 실패율 내림차순, 스테이지 번호 오름차순 정렬
 * 5. 최적화 버전에서는 배열로 더 효율적 구현
 *
 * 결과: [3, 4, 2, 1, 5] (스테이지 3이 실패율 0.5로 가장 높음)
 */
