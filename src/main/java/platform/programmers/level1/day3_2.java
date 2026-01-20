package platform.programmers.level1;

/**
 * 프로그래머스 Lv1 - 숫자 문자열과 영단어
 * https://school.programmers.co.kr/learn/courses/30/lessons/81301
 * 시간복잡도: O(N), 공간복잡도: O(N)
 * 2021 카카오 채용연계형 인턴십
 */
public class day3_2 {

    // 사용자 원본 해법 (StringBuilder 활용)
    public int solution(String s) {
        int answer = 0;

        String[] numStr = {"zero","one","two","three","four","five","six","seven","eight","nine"};
        String answerStr = ""; // StringBuilder 사용 권장 (문자열 연결 최적화)

        StringBuilder chkSb = new StringBuilder(); // 좋은 접근: 영단어 누적 저장
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(Character.isLetter(c)){
                chkSb.append(c); // 영문자는 StringBuilder에 누적
            }else{
                answerStr += c; // 숫자는 바로 결과에 추가
            }

            if(chkSb.length() >= 3){ // 최적화 아이디어: 최소 길이 3 이상에서만 검사
                for(int j = 0; j < numStr.length; j++){
                    if(chkSb.toString().equals(numStr[j])){
                        answerStr += j; // 매칭된 영단어를 숫자로 변환
                        chkSb.delete(0,chkSb.length()); // StringBuilder 초기화
                    }
                }
            }
        }

        answer = Integer.parseInt(answerStr);
        return answer;

        // ✅ 핵심 아이디어(StringBuilder 누적, 최소길이 검사)는 훌륭함! 소폭 개선 가능
    }

    // 개선된 해법 (replaceAll 활용 - 가장 간결)
    public int solutionOptimized(String s) {
        String[] words = {"zero", "one", "two", "three", "four", "five",
                         "six", "seven", "eight", "nine"};

        for (int i = 0; i < words.length; i++) {
            s = s.replaceAll(words[i], String.valueOf(i));
        }

        return Integer.parseInt(s);
    }

    // 효율성 중시 해법 (StringBuilder 최적화)
    public int solutionStringBuilder(String s) {
        String[] numStr = {"zero", "one", "two", "three", "four", "five",
                          "six", "seven", "eight", "nine"};

        StringBuilder answerSb = new StringBuilder(); // String 대신 StringBuilder
        StringBuilder wordSb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                answerSb.append(c); // 숫자는 바로 추가
            } else {
                wordSb.append(c); // 영문자 누적

                // 완성된 영단어 확인
                String word = wordSb.toString();
                for (int j = 0; j < numStr.length; j++) {
                    if (word.equals(numStr[j])) {
                        answerSb.append(j);
                        wordSb.setLength(0); // StringBuilder 초기화
                        break;
                    }
                }
            }
        }

        return Integer.parseInt(answerSb.toString());
    }

    // 맵 기반 해법 (가독성 우수)
    public int solutionMap(String s) {
        java.util.Map<String, String> wordToNum = java.util.Map.of(
            "zero", "0", "one", "1", "two", "2", "three", "3", "four", "4",
            "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9"
        );

        for (java.util.Map.Entry<String, String> entry : wordToNum.entrySet()) {
            s = s.replace(entry.getKey(), entry.getValue());
        }

        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        day3_2 solution = new day3_2();

        System.out.println("=== 원본 결과 ===");
        System.out.println(solution.solution("one4seveneight"));    // 1478
        System.out.println(solution.solution("23four5six7"));       // 234567
        System.out.println(solution.solution("2three45sixseven"));  // 234567
        System.out.println(solution.solution("123"));               // 123

        System.out.println("\n=== 개선 결과 ===");
        System.out.println(solution.solutionOptimized("one4seveneight"));
        System.out.println(solution.solutionStringBuilder("23four5six7"));
        System.out.println(solution.solutionMap("2three45sixseven"));
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (StringBuilder 누적):
 * - 아이디어: 영문자를 StringBuilder에 누적하여 영단어 매칭
 * - 최적화: length >= 3 조건으로 불필요한 검사 최소화 (영리한 접근!)
 * - 정확성: 모든 테스트 케이스에서 완벽한 결과
 * - 개선점 1: answerStr도 StringBuilder 사용하면 더 효율적
 *
 * 🚀 개선된 해법들:
 * 1. replaceAll 방식: 가장 간결하고 직관적 (코테 추천)
 * 2. StringBuilder 최적화: 메모리 효율성 극대화
 * 3. Map 기반: 가독성과 확장성 우수
 * 4. 원본 아이디어 발전: chkSb.setLength(0) 활용
 *
 * 학습 포인트:
 * 1. 문자열 치환: replace() vs replaceAll() 차이
 * 2. StringBuilder 효율성: String 연결보다 월등한 성능
 * 3. 조기 최적화: length >= 3 조건으로 성능 향상
 * 4. 다양한 접근법: 치환 vs 파싱 vs 맵 활용
 * 5. 영단어 매칭: 순차 검사 vs 해시맵 활용
 */
