package platform.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2675번 - 문자열 반복
 * https://www.acmicpc.net/problem/2675
 * 
 * 문제: 문자열 S의 각 문자를 R번 반복해서 새 문자열 P를 만들어라
 * 
 * 입력:
 * - 첫째 줄: 테스트 케이스의 개수 T
 * - 각 테스트 케이스: R S (R: 반복횟수, S: 문자열)
 * 
 * 출력: 
 * - 각 테스트 케이스마다 P를 출력
 */
public class day1_add {
    
    // ✅ 수정된 해법
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 테스트 케이스 개수 입력
        int T = Integer.parseInt(br.readLine());
        
        // 각 테스트 케이스 처리
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken()); // 반복 횟수
            String S = st.nextToken();                // 문자열
            
            StringBuilder sb = new StringBuilder();
            
            // 문자열의 각 문자를 R번 반복
            for (char c : S.toCharArray()) {
                for (int i = 0; i < R; i++) { // 수정: i < R (R번 반복)
                    sb.append(c);
                }
            }
            
            // 결과 출력
            System.out.println(sb.toString());
        }
    }
    
    // 📝 원래 코드 (참고용 - 문제점 있음)
    public static void originalSolution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 문제점 1: T(테스트케이스 개수)를 n으로 잘못 명명
        int n = Integer.parseInt(br.readLine());
        
        // 문제점 2: 단일 테스트케이스만 처리 (반복문 없음)
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken()); // a는 반복횟수 R이어야 함
        String b = st.nextToken();                // b는 문자열 S

        StringBuilder sb = new StringBuilder();
        for(char c : b.toCharArray()){
            // 문제점 3: i <= n (잘못된 반복 조건)
            for (int i = 0; i <= n; i++) {
                sb.append(c);
            }
        }

        System.out.println(sb.toString());
    }
}

/*
 * 💡 수정 내용:
 * 
 * 1. 테스트 케이스 개수 T를 제대로 처리
 * 2. 각 테스트 케이스마다 R과 S를 입력받아 처리
 * 3. 반복 조건을 i < R로 수정 (R번 반복)
 * 4. 변수명을 명확하게 수정 (T, R, S)
 * 5. 주석과 문제 설명 추가
 * 
 * 🎯 테스트 케이스:
 * 입력:
 * 2
 * 3 ABC
 * 5 /HTP
 * 
 * 예상 출력:
 * AAABBBCCC
 * /////HHHHHTTTTTPPPPP
 */
