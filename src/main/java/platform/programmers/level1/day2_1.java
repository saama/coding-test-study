package platform.programmers.level1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 프로그래머스 Lv1 - K번째 수
 * https://school.programmers.co.kr/learn/courses/30/lessons/42748
 * 시간복잡도: O(N*M*log(M)), 공간복잡도: O(M)
 * N: commands 길이, M: 부분 배열의 평균 길이
 */
public class day2_1 {

    // 사용자 원본 코드 - 정상 동작하지만 개선 가능
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = {}; // 문제: 빈 배열을 선언했지만 사용하지 않음
        List<Integer> answerList = new ArrayList<Integer>();
        for (int i = 0; i < commands.length; i++) {
            int start = commands[i][0] - 1;
            int end = commands[i][1] - 1;
            List<Integer> list = new ArrayList<Integer>();
            for (int j = start; j <= end; j++) {
                list.add(array[j]);
            }
            list.sort(Integer::compare);
            answerList.add(list.get(commands[i][2] - 1));
        }

        return answerList.stream().mapToInt(i -> i).toArray();
    }

    // 개선된 해법 - 더 효율적인 방식
    public int[] solutionImproved(int[] array, int[][] commands) {
        int[] answer = new int[commands.length]; // 수정: 적절한 배열 초기화
        
        for (int i = 0; i < commands.length; i++) {
            int start = commands[i][0] - 1;  // 1-기반 인덱스를 0-기반으로 변환
            int end = commands[i][1] - 1;    // 1-기반 인덱스를 0-기반으로 변환 
            int k = commands[i][2] - 1;      // 1-기반 인덱스를 0-기반으로 변환
            
            // Arrays.copyOfRange 사용하여 부분 배열 추출 (더 효율적)
            int[] subArray = Arrays.copyOfRange(array, start, end + 1);
            
            // 부분 배열 정렬
            Arrays.sort(subArray);
            
            // K번째 요소 저장
            answer[i] = subArray[k];
        }
        
        return answer;
    }
    
    // 스트림 기반 해법 (가장 간결)
    public int[] solutionStream(int[] array, int[][] commands) {
        return Arrays.stream(commands)
                .mapToInt(cmd -> {
                    int[] sub = Arrays.copyOfRange(array, cmd[0] - 1, cmd[1]);
                    Arrays.sort(sub);
                    return sub[cmd[2] - 1];
                })
                .toArray();
    }

    public static void main(String[] args) {
        day2_1 sol = new day2_1();
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2,5,3},{4,4,1},{1,7,3}};

        System.out.println("원본코드:  " + Arrays.toString(sol.solution(array, commands)));
        System.out.println("개선코드:  " + Arrays.toString(sol.solutionImproved(array, commands)));
        System.out.println("스트림:   " + Arrays.toString(sol.solutionStream(array, commands)));
        System.out.println("예상결과:  [5, 6, 3]");
    }
}

/*
 * 코드 분석:
 * 
 * 원본 코드의 문제점:
 * 1. 사용하지 않는 변수: 'answer' 배열을 선언했지만 사용하지 않음
 * 2. 효율성 문제: 직접 배열 조작 대신 List와 stream 변환을 사용
 * 3. 하지만 로직은 정확하고 올바른 결과를 생성함!
 * 
 * 개선사항:
 * 1. 성능 향상을 위해 List 대신 직접 배열 사용
 * 2. 더 깔끔한 부분 배열 추출을 위해 Arrays.copyOfRange 사용
 * 3. 사용하지 않는 변수 제거
 * 4. 비교를 위한 stream 기반 대안 추가
 * 
 * 모든 방식이 동일한 올바른 결과를 생성: [5, 6, 3]
 */
