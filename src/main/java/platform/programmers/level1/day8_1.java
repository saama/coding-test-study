package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Lv2 - 기능개발
 * https://school.programmers.co.kr/learn/courses/30/lessons/42586
 * 시간복잡도: O(D * N) (D: 최대 소요일수, N: 기능 개수)
 * 공간복잡도: O(N) (결과 리스트)
 */
public class day8_1 {
    // 사용자 원본 해법 (논리적 오류 다수)
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};

        List<Integer> result = new ArrayList<>();
        int day = 1;
        int totProgress = 0;  // 문제점: 완료된 기능의 누적 개수 (잘못된 개념)
        int completeCnt = 0;
        while (totProgress < progresses.length) { // 문제점: 종료 조건 잘못됨
            completeCnt = 0;
            for (int i = 0; i < progresses.length; i++) { // 문제점: 이미 배포된 기능도 중복 체크
                if(progresses[i] + (speeds[i]*day) >= 100){
                    completeCnt++;
                }
            }
            if(completeCnt>0){ // 문제점: 배포 순서 무시 (앞선 기능이 완료되지 않으면 배포 불가)
                totProgress += completeCnt;
                result.add(completeCnt);
            }
            day++;
        }

        answer = new int[result.size()];
        for (int k : result){ // 문제점: 배열 복사 오류 (k는 값, 인덱스가 아님)
            answer[k] = result.get(k); // ArrayIndexOutOfBoundsException 발생 가능!
        }

        return answer;
    }
    
    // 개선된 해법 1: 올바른 Queue 기반 처리 (스택/큐 개념)
    public int[] solutionFixed(int[] progresses, int[] speeds) {
        // 1단계: 각 기능별 완료 소요일 계산
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < progresses.length; i++) {
            int remainWork = 100 - progresses[i];
            int daysNeeded = (int) Math.ceil((double) remainWork / speeds[i]);
            // 또는: int daysNeeded = (remainWork + speeds[i] - 1) / speeds[i]; // 올림 연산
            queue.offer(daysNeeded);
        }
        
        // 2단계: 배포 그룹 단위로 처리
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int currentDay = queue.poll(); // 현재 배포할 기능의 완료일
            int count = 1; // 현재 기능 포함
            
            // 뒤따르는 기능들 중에서 현재 배포일에 함께 배포 가능한 것들
            while (!queue.isEmpty() && queue.peek() <= currentDay) {
                queue.poll();
                count++;
            }
            
            result.add(count);
        }
        
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 개선된 해법 2: 직접 시뮬레이션 (명확한 논리)
    public int[] solutionSimulation(int[] progresses, int[] speeds) {
        List<Integer> result = new ArrayList<>();
        boolean[] completed = new boolean[progresses.length]; // 완료 상태 추적
        int deployedIndex = 0; // 다음 배포할 기능의 인덱스
        
        int day = 0;
        while (deployedIndex < progresses.length) {
            day++;
            
            // 모든 기능의 진행률 업데이트
            for (int i = 0; i < progresses.length; i++) {
                if (!completed[i]) {
                    progresses[i] += speeds[i];
                    if (progresses[i] >= 100) {
                        completed[i] = true;
                    }
                }
            }
            
            // 배포 가능한 기능들 확인 (순차적으로)
            int deployCount = 0;
            while (deployedIndex < progresses.length && completed[deployedIndex]) {
                deployedIndex++;
                deployCount++;
            }
            
            if (deployCount > 0) {
                result.add(deployCount);
            }
        }
        
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 개선된 해법 3: 수학적 계산 + 포인터 기법 (최적화)
    public int[] solutionOptimized(int[] progresses, int[] speeds) {
        List<Integer> result = new ArrayList<>();
        int index = 0;
        
        while (index < progresses.length) {
            // 현재 기능의 완료 소요일 계산
            int remainWork = 100 - progresses[index];
            int currentDeployDay = (int) Math.ceil((double) remainWork / speeds[index]);
            
            int count = 1; // 현재 기능 포함
            index++;
            
            // 연속적으로 배포 가능한 기능들 찾기
            while (index < progresses.length) {
                int nextRemainWork = 100 - progresses[index];
                int nextDeployDay = (int) Math.ceil((double) nextRemainWork / speeds[index]);
                
                if (nextDeployDay <= currentDeployDay) {
                    count++;
                    index++;
                } else {
                    break; // 배포 불가능하면 중단
                }
            }
            
            result.add(count);
        }
        
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 올림 연산 유틸리티
    private int ceilDivision(int dividend, int divisor) {
        return (dividend + divisor - 1) / divisor;
    }

    public static void main(String[] args) {
        day8_1 solution = new day8_1();
        int[] progresses = {93, 30, 55};
        int[] speeds = {1, 30, 5};
        
        System.out.println("=== Test Results ===");
        System.out.println("Original: " + Arrays.toString(solution.solution(progresses, speeds)));
        
        // 배열이 원본 메서드에서 수정될 수 있으므로 복사본 사용
        System.out.println("Fixed: " + Arrays.toString(solution.solutionFixed(progresses.clone(), speeds.clone())));
        System.out.println("Simulation: " + Arrays.toString(solution.solutionSimulation(progresses.clone(), speeds.clone())));
        System.out.println("Optimized: " + Arrays.toString(solution.solutionOptimized(progresses.clone(), speeds.clone())));
        
        // Expected output: [2, 1]
        // 설명: 1일차에 1,2번 기능이 완료되어 함께 배포, 3번 기능은 나중에 단독 배포
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ❌ 원본 해법의 주요 문제점들:
 * 1. 논리적 오류: 배포 순서 무시
 *    - 문제 핵심을 이해하지 못함: 앞선 기능이 완료되지 않으면 뒤의 기능도 배포 불가
 *    - 단순히 완료된 기능 수를 세는 것이 아니라, 순차적 배포가 핵심
 * 2. 중복 계산: 이미 배포된 기능을 반복 확인
 *    - 매 루프마다 모든 기능을 다시 체크하는 비효율성
 * 3. 배열 복사 오류: answer[k] = result.get(k)
 *    - k는 값(value)이지 인덱스가 아님
 *    - 큰 값이 나오면 ArrayIndexOutOfBoundsException 발생
 * 4. 종료 조건 오류: totProgress < progresses.length
 *    - 실제 배포 완료와 무관한 조건
 * 5. 변수 이름 혼란: totProgress (전체 진행률이 아니라 배포된 기능 수)
 *
 * ✅ 개선된 해법들의 접근:
 * 1. solutionFixed: Queue를 활용한 선입선출 처리
 *    - 각 기능의 완료 소요일을 미리 계산
 *    - 배포 가능한 기능들을 그룹화
 * 2. solutionSimulation: 직접 시뮬레이션
 *    - 날짜별로 진행률 업데이트
 *    - 완료 상태를 명시적으로 추적
 * 3. solutionOptimized: 수학적 계산 + 포인터
 *    - 완료일 계산 후 순차적 그룹화
 *    - 가장 효율적인 접근
 *
 * 🔍 핵심 개념 분석:
 * 1. 스택/큐 문제의 특성:
 *    - FIFO(First In, First Out): 먼저 시작한 기능이 먼저 배포
 *    - 순차적 처리: 앞선 작업이 완료되어야 다음 작업 처리 가능
 * 2. 올림 연산 (Ceiling Division):
 *    - Math.ceil((double) remainWork / speed)
 *    - 또는 (remainWork + speed - 1) / speed
 * 3. 그룹화 패턴:
 *    - 조건을 만족하는 연속된 요소들을 하나로 묶기
 *    - 배포 단위별 카운팅
 *
 * 📊 성능 비교:
 * - 원본: O(D * N) + 논리적 오류 (D: 최대 소요일수, N: 기능 개수)
 * - 개선: O(N) (각 기능을 한 번씩만 처리)
 * - 메모리: 모두 O(N) (결과 저장)
 *
 * 💡 학습 포인트:
 * 1. 문제 핵심 파악: 단순 카운팅이 아닌 순차적 처리
 * 2. 배열 vs 값 구분: 반복문에서 인덱스와 값의 차이
 * 3. Queue/Stack 활용: FIFO/LIFO 특성을 문제에 적용
 * 4. 수학적 최적화: 시뮬레이션 vs 직접 계산
 * 5. 엣지케이스 고려: 모든 기능이 동시 완료, 이미 완료된 기능 등
 */
