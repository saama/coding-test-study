package platform.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 백준 알파벳 빈도수 문제 (추정)
 * 대소문자 구분없이 문자별 빈도수 계산
 * 시간복잡도: O(N), 공간복잡도: O(1) - 알파벳 26개 최대
 */
public class day3_add {
    
    // 사용자 원본 해법 (HashMap 활용)
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 로직 구현
        st = new StringTokenizer(br.readLine());
        String S = st.nextToken();
        S = S.toLowerCase(); // 대소문자 통일 (좋은 접근!)
        
        Map<String,Integer> map = new HashMap<String,Integer>(); // Character 타입 권장
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if(map.get(c+"") == null){ // getOrDefault() 사용 권장
                map.put(c+"",1);
            }else{
                map.put(c+"",map.get(c+"")+1);
            }
        }

        //Map을 반복문 돌리는법을 모르겠어!!!!!!!!!!!
        
        System.out.println(map); // Map 전체 출력

        // ✅ HashMap 활용 아이디어는 완벽함! Map 반복 방법만 추가하면 완성
    }
    
    // 개선된 해법 1 (getOrDefault 활용)
    public static void solutionOptimized() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine().toLowerCase();
        
        Map<Character, Integer> map = new HashMap<>(); // Character 타입 사용
        
        // getOrDefault()로 간결하게 빈도수 계산
        for (char c : S.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        // Map 반복하는 여러 방법들
        System.out.println("=== 방법 1: entrySet() ===");
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\n=== 방법 2: keySet() ===");
        for (Character key : map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }
        
        System.out.println("\n=== 방법 3: forEach() (Java 8+) ===");
        map.forEach((key, value) -> 
            System.out.println(key + ": " + value));
    }
    
    // 배열 기반 해법 (알파벳만 처리하는 경우)
    public static void solutionArray() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine().toLowerCase();
        
        int[] count = new int[26]; // a-z 빈도수 배열
        
        for (char c : S.toCharArray()) {
            if (c >= 'a' && c <= 'z') { // 알파벳만 처리
                count[c - 'a']++;
            }
        }
        
        // 결과 출력
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                System.out.println((char)('a' + i) + ": " + count[i]);
            }
        }
    }
    
    // 최빈 문자 찾기 (백준 1157번 스타일)
    public static void findMostFrequent() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine().toLowerCase();
        
        Map<Character, Integer> map = new HashMap<>();
        
        for (char c : S.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        // 최빈 문자와 빈도수 찾기
        char mostFreqChar = ' ';
        int maxCount = 0;
        boolean isDuplicate = false;
        
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFreqChar = entry.getKey();
                maxCount = entry.getValue();
                isDuplicate = false;
            } else if (entry.getValue() == maxCount) {
                isDuplicate = true;
            }
        }
        
        if (isDuplicate) {
            System.out.println("?"); // 최빈 문자가 여러 개인 경우
        } else {
            System.out.println(Character.toUpperCase(mostFreqChar));
        }
    }
    
    // 테스트 실행
    public static void runTests() {
        // 사용자가 궁금해하는 Map 반복 방법 예시
        Map<Character, Integer> testMap = new HashMap<>();
        testMap.put('a', 3);
        testMap.put('b', 2);
        testMap.put('c', 1);
        
        System.out.println("=== Map 반복 방법들 ===");
        
        // 방법 1: entrySet() - 키와 값 모두 필요할 때
        for (Map.Entry<Character, Integer> entry : testMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        
        // 방법 2: keySet() - 키만 순회하고 값은 get()으로
        for (Character key : testMap.keySet()) {
            System.out.println(key + " => " + testMap.get(key));
        }
        
        // 방법 3: values() - 값만 필요할 때
        for (Integer value : testMap.values()) {
            System.out.println("빈도수: " + value);
        }
        
        // 방법 4: forEach (Java 8+) - 람다식 활용
        testMap.forEach((k, v) -> System.out.println(k + " => " + v));
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (HashMap 활용):
 * - 아이디어: HashMap으로 문자별 빈도수 계산 - 완벽한 접근!
 * - 전처리: toLowerCase()로 대소문자 통일 - 좋은 판단
 * - 궁금증: Map 반복 방법을 몰라서 막힌 상황
 * - 개선점 1: String 대신 Character 타입 사용
 * - 개선점 2: getOrDefault()로 null 체크 대신 간단히
 * - 개선점 3: c+"" 대신 직접 char 사용
 *
 * 🚀 Map 반복 방법 4가지:
 * 1. entrySet(): 키와 값 모두 필요할 때 (가장 효율적)
 * 2. keySet(): 키만 순회하고 값은 get()으로 접근
 * 3. values(): 값만 필요할 때
 * 4. forEach(): Java 8+ 람다식 스타일
 *
 * 학습 포인트:
 * 1. HashMap vs 배열: 범용성 vs 성능
 * 2. getOrDefault(): null 체크 없이 안전한 기본값 설정
 * 3. Character vs String: 타입 선택의 중요성
 * 4. Map 반복 패턴: 상황에 맞는 방법 선택
 * 5. 문자열 전처리: toLowerCase(), 특수문자 필터링
 */