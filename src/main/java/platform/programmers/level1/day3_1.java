package platform.programmers.level1;

import java.util.Arrays;

/**
 * 프로그래머스 Lv1 - [1차] 다트 게임
 * https://school.programmers.co.kr/learn/courses/30/lessons/17682
 * 시간복잡도: O(N), 공간복잡도: O(1)
 */
public class day3_1 {

    // 사용자 원본 해법 (배열 분리 접근)
    public int solution(String dartResult) {

        int scoreIdx = 0;
        int optionIdx = 0;
        int[] scores = new int[3];
        char[] options = new char[3];
        int[] tempNum = new int[2]; // 두 자리 수 처리 시도 (좋은 접근!)
        for (int i = 0; i < dartResult.length(); i++) {
            char c = dartResult.charAt(i);
            if(Character.isDigit(c)){
                //두자리 숫자일수도 있어서 tempNum을 크기2로 만들고 넣어서 두개의 수를 합치려했는데 방법을 모르겠어.
                System.out.println("c "+ c);
//                tempNum = Character.getNumericValue(c); // 타입 오류: int[]에 int 대입 불가
                continue;
            }else if(Character.isAlphabetic(c)){
                System.out.println("tempNum "+tempNum);
                if(c == 'S'){
//                    scores[scoreIdx] = tempNum; // tempNum이 배열이라 오류 발생
                }else if(c == 'D'){
//                    scores[scoreIdx] = tempNum*tempNum;
                }else if(c == 'T'){
//                    scores[scoreIdx] = tempNum*tempNum*tempNum;
                }
            }

            if(c == '*' || c == '#'){
                options[scoreIdx] = c; // 옵션 위치 추적 로직 필요
            }

            scoreIdx++; // 매 반복마다 증가하면 인덱스 초과 발생
//            tempNum = 0; // 배열을 0으로 초기화 불가
        }
        //결과적으로 scores {1,4,7}, options {,*,} 모양으로 만드려했는데 scoreIdx에서 로직이 잘못됐어

        System.out.println(Arrays.toString(scores));
        System.out.println(Arrays.toString(options));

        int answer = 0;
        return answer; // 최종 계산 로직 미완성

        // ✅ 핵심 아이디어(배열 분리, 두 자리 수 고려)는 훌륭함! 구현 세부사항만 수정 필요
    }

    // 개선된 해법 (문자열 파싱 + 스택 활용)
    public int solutionOptimized(String dartResult) {
        int[] scores = new int[3]; // 각 라운드별 점수 저장
        int round = -1; // 현재 라운드 인덱스

        for (int i = 0; i < dartResult.length(); i++) {
            char c = dartResult.charAt(i);

            if (Character.isDigit(c)) {
                round++; // 새로운 라운드 시작
                int score = c - '0'; // 숫자 변환

                // 10점 처리 (두 자리 수)
                if (c == '1' && i + 1 < dartResult.length() && dartResult.charAt(i + 1) == '0') {
                    score = 10;
                    i++; // '0' 건너뛰기
                }
                scores[round] = score;

            } else if (Character.isAlphabetic(c)) {
                // Single, Double, Triple 처리
                if (c == 'D') {
                    scores[round] *= scores[round];
                } else if (c == 'T') {
                    scores[round] = scores[round] * scores[round] * scores[round];
                }
                // 'S'는 그대로 유지 (1제곱)

            } else if (c == '*' || c == '#') {
                // 옵션 처리
                if (c == '*') {
                    // 스타상: 현재와 이전 점수 2배
                    scores[round] *= 2;
                    if (round > 0) {
                        scores[round - 1] *= 2;
                    }
                } else if (c == '#') {
                    // 아차상: 현재 점수 음수
                    scores[round] *= -1;
                }
            }
        }

        // 총합 계산
        return scores[0] + scores[1] + scores[2];
    }

    public static void main(String[] args) {
        day3_1 solution = new day3_1();
        System.out.println("원본 결과: " + solution.solution("1S2D*3T"));
        System.out.println("개선 결과: " + solution.solutionOptimized("1S2D*3T"));
        System.out.println("예상 결과: 37"); // 1 + (2*2*2) + (3*3*3) = 1 + 8 + 27 = 36, *2 = 37
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 분석:
 * - 아이디어: 점수와 옵션을 배열로 분리 저장 (좋은 접근!)
 * - 두 자리 수 고려: 10점 처리에 대한 인식 (핵심 포인트!)
 * - 문제점 1: tempNum 타입 오류 (int[] vs int)
 * - 문제점 2: scoreIdx 증가 로직 (매 문자마다 증가)
 * - 문제점 3: 옵션 처리 타이밍 (이전 점수 영향 미고려)
 * - 문제점 4: 최종 계산 로직 미완성
 *
 * 🚀 개선된 해법:
 * - 시간복잡도: O(N) - 문자열 한 번 순회
 * - 핵심 패턴 1: 문자 타입별 분기 처리
 * - 핵심 패턴 2: 10점 두 자리 수 처리 (연속된 '1', '0' 검사)
 * - 핵심 패턴 3: 스타상의 이전 점수 영향 처리
 * - 핵심 패턴 4: 라운드 진행 상태 관리
 *
 * 학습 포인트:
 * 1. 문자열 파싱: Character.isDigit(), isAlphabetic() 활용
 * 2. 두 자리 수 처리: 연속 문자 검사 및 인덱스 스킵
 * 3. 상태 관리: 현재 라운드 추적으로 정확한 처리
 * 4. 옵션 효과: 현재/이전 점수에 대한 즉시 적용
 * 5. Math.pow() 대신 직접 곱셈이 더 효율적
 */
