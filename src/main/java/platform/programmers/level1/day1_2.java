package platform.programmers.level1;

/**
 * 프로그래머스 Level 1 - 신규 아이디 추천
 * https://school.programmers.co.kr/learn/courses/30/lessons/72410
 * 2021 KAKAO BLIND RECRUITMENT
 *
 * 문제: 신규 유저가 입력한 아이디를 규칙에 따라 변환
 *
 * 7단계 처리 과정:
 * 1. 대문자 → 소문자 변환
 * 2. 허용 문자(알파벳 소문자, 숫자, -, _, .) 외 제거
 * 3. 연속된 마침표(.) → 하나의 마침표로 치환
 * 4. 처음/끝 마침표 제거
 * 5. 빈 문자열이면 "a" 대입
 * 6. 16자 이상이면 15자로 자르기 (끝이 .이면 제거)
 * 7. 2자 이하면 마지막 문자를 반복해서 3자로 만들기
 */
public class day1_2 {

    // ✅ 최적 해법: 단계별 문자열 처리
    public String solution(String new_id) {
        String answer = new_id;

        // 1단계: 대문자 → 소문자 변환
        answer = answer.toLowerCase();

        // 2단계: 허용 문자 외 제거 (알파벳 소문자, 숫자, -, _, .)
        StringBuilder sb = new StringBuilder();
        for (char c : answer.toCharArray()) {
            if (Character.isLowerCase(c) || Character.isDigit(c) || c == '-' || c == '_' || c == '.') {
                sb.append(c);
            }
        }
        answer = sb.toString();

        // 3단계: 연속된 마침표 → 하나의 마침표로 치환
        answer = answer.replaceAll("\\.{2,}", ".");

        // 4단계: 처음과 끝 마침표 제거
        if (answer.startsWith(".")) {
            answer = answer.substring(1);
        }
        if (answer.endsWith(".")) {
            answer = answer.substring(0, answer.length() - 1);
        }

        // 5단계: 빈 문자열이면 "a" 대입
        if (answer.isEmpty()) {
            answer = "a";
        }

        // 6단계: 16자 이상이면 15자로 자르고, 끝이 .이면 제거
        if (answer.length() >= 16) {
            answer = answer.substring(0, 15);
            if (answer.endsWith(".")) {
                answer = answer.substring(0, answer.length() - 1);
            }
        }

        // 7단계: 2자 이하면 마지막 문자를 반복해서 3자로 만들기
        while (answer.length() <= 2) {
            answer += answer.charAt(answer.length() - 1);
        }

        return answer;
    }

    // 🔧 정규식 활용 버전 (더 간결)
    public String solutionRegex(String new_id) {
        String answer = new_id
                .toLowerCase()                          // 1단계
                .replaceAll("[^a-z0-9\\-_.]", "")      // 2단계
                .replaceAll("\\.{2,}", ".")            // 3단계
                .replaceAll("^\\.|\\.$", "");          // 4단계

        if (answer.isEmpty()) answer = "a";             // 5단계

        if (answer.length() >= 16) {                    // 6단계
            answer = answer.substring(0, 15);
            answer = answer.replaceAll("\\.$", "");
        }

        while (answer.length() <= 2) {                  // 7단계
            answer += answer.charAt(answer.length() - 1);
        }

        return answer;
    }

    // 📝 단계별 상세 버전 (디버깅 및 학습용)
    public String solutionDetailed(String new_id) {
        String answer = new_id;
        System.out.println("입력: " + answer);

        // 1단계
        answer = answer.toLowerCase();
        System.out.println("1단계: " + answer);

        // 2단계
        answer = answer.replaceAll("[^a-z0-9\\-_.]", "");
        System.out.println("2단계: " + answer);

        // 3단계
        answer = answer.replaceAll("\\.{2,}", ".");
        System.out.println("3단계: " + answer);

        // 4단계
        answer = answer.replaceAll("^\\.|\\.$", "");
        System.out.println("4단계: " + answer);

        // 5단계
        if (answer.isEmpty()) {
            answer = "a";
        }
        System.out.println("5단계: " + answer);

        // 6단계
        if (answer.length() >= 16) {
            answer = answer.substring(0, 15);
            if (answer.endsWith(".")) {
                answer = answer.substring(0, answer.length() - 1);
            }
        }
        System.out.println("6단계: " + answer);

        // 7단계
        while (answer.length() <= 2) {
            answer += answer.charAt(answer.length() - 1);
        }
        System.out.println("7단계: " + answer);

        return answer;
    }

    public static void main(String[] args) {
        day1_2 sol = new day1_2();

        // 테스트 케이스 1
        String test1 = "...!@BaT#*..y.abcdefghijklm";
        System.out.println("Test 1: " + sol.solution(test1)); // "bat.y.abcdefghi"

        // 테스트 케이스 2
        String test2 = "z-+.^.";
        System.out.println("Test 2: " + sol.solution(test2)); // "z--"

        // 테스트 케이스 3
        String test3 = "=.=";
        System.out.println("Test 3: " + sol.solution(test3)); // "aaa"

        // 테스트 케이스 4
        String test4 = "123_.def";
        System.out.println("Test 4: " + sol.solution(test4)); // "123_.def"

        // 테스트 케이스 5
        String test5 = "abcdefghijklmn.p";
        System.out.println("Test 5: " + sol.solution(test5)); // "abcdefghijklmn"

        System.out.println("\n=== 정규식 버전 테스트 ===");
        System.out.println("Regex Test 1: " + sol.solutionRegex(test1));

        System.out.println("\n=== 단계별 상세 버전 ===");
        sol.solutionDetailed(test1);
    }
}

/*
 * 💡 학습 포인트:
 *
 * 1. 문자열 처리 메서드 활용
 *    - toLowerCase(): 대소문자 변환
 *    - replaceAll(): 정규식을 이용한 치환
 *    - substring(): 문자열 자르기
 *    - charAt(): 특정 위치 문자 접근
 *
 * 2. 정규식 패턴
 *    - [^a-z0-9\\-_.]: 허용 문자 외 제거
 *    - \\.{2,}: 연속된 점 2개 이상
 *    - ^\\.|\\.$: 시작 또는 끝의 점
 *
 * 3. 엣지 케이스 처리
 *    - 빈 문자열 처리
 *    - 길이 제한 처리
 *    - 최소 길이 보장
 *
 * 4. 코드 품질
 *    - 단계별 명확한 구분
 *    - 가독성과 성능의 균형
 *    - 테스트 케이스로 검증
 *
 * 5. 실무 적용
 *    - 사용자 입력 검증
 *    - 데이터 정제/표준화
 *    - 비즈니스 룰 구현
 */
