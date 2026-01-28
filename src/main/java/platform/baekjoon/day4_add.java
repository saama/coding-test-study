package platform.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 백준 1620번 - 나는야 포켓몬 마스터 이다솜
 * https://www.acmicpc.net/problem/1620
 * 시간복잡도: O(N + M log N) → O(N + M) 최적화 가능
 * 공간복잡도: O(N)
 */
public class day4_add {
    // 사용자 원본 해법 (배열 + HashMap 양방향 매핑)
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); //포켓몬 수
        int M = Integer.parseInt(st.nextToken()); //문제 수

        String[] pocketmonNames = new String[N+1]; // 훌륭한 아이디어: 1-based 인덱싱
        Map<String,Integer> pocketmonIdxs = new HashMap<>(); // 이름→번호 역방향 매핑
        for (int n = 1; n < N+1; n++) {
            st = new StringTokenizer(br.readLine());
            String pocketmonName = st.nextToken();
            pocketmonNames[n] = pocketmonName; // 번호→이름 매핑
            pocketmonIdxs.put(pocketmonName,n); // 이름→번호 매핑
        }

        String[] Q = new String[M]; // 불필요한 배열 저장 (바로 처리 가능)
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            Q[m] = st.nextToken();
        }

        String[] answer = new String[M]; // 불필요한 배열 저장 (바로 출력 가능)
        for (int i = 0; i < Q.length; i++) {
            //Q[i]가 숫자인지 문자인지 판별
            //1. 숫자이면 pocketmonNames[Integer.parseInt(Q[i])-1] // 오류: -1 불필요 (1-based)
            //2. 문자이면 pocketmonIdxs.get(Q[i])
            //3. answer[i]에 넣기 (미구현)
            
            // 숫자/문자 판별 로직 필요: Character.isDigit() 또는 try-catch
            if (Character.isDigit(Q[i].charAt(0))) {
                answer[i] = pocketmonNames[Integer.parseInt(Q[i])]; // -1 제거 (1-based)
            } else {
                answer[i] = String.valueOf(pocketmonIdxs.get(Q[i]));
            }
        }

        //4. answer[i]출력 (미구현)
        for (String ans : answer) {
            System.out.println(ans);
        }
        
        // ✅ 핵심 아이디어(양방향 매핑, 1-based 인덱싱)는 완벽! 숫자/문자 판별과 출력만 보완하면 완성
    }
    
    // 개선된 해법 1: 바로 처리 (메모리 최적화)
    public static void solutionOptimized() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        // 양방향 매핑
        String[] numToName = new String[N + 1];
        Map<String, Integer> nameToNum = new HashMap<>();
        
        for (int i = 1; i <= N; i++) {
            String name = br.readLine();
            numToName[i] = name;
            nameToNum.put(name, i);
        }
        
        // 쿼리 바로 처리 (배열 저장 없이)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            String query = br.readLine();
            
            if (Character.isDigit(query.charAt(0))) {
                // 숫자 입력 → 이름 출력
                sb.append(numToName[Integer.parseInt(query)]).append('\n');
            } else {
                // 이름 입력 → 번호 출력
                sb.append(nameToNum.get(query)).append('\n');
            }
        }
        
        System.out.print(sb.toString());
    }
    
    // 개선된 해법 2: try-catch 방식 (숫자 판별)
    public static void solutionTryCatch() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        String[] numToName = new String[N + 1];
        Map<String, Integer> nameToNum = new HashMap<>();
        
        for (int i = 1; i <= N; i++) {
            String name = br.readLine();
            numToName[i] = name;
            nameToNum.put(name, i);
        }
        
        for (int i = 0; i < M; i++) {
            String query = br.readLine();
            
            try {
                // 숫자로 변환 시도
                int num = Integer.parseInt(query);
                System.out.println(numToName[num]);
            } catch (NumberFormatException e) {
                // 문자열인 경우
                System.out.println(nameToNum.get(query));
            }
        }
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (배열 + HashMap 양방향 매핑):
 * - 아이디어: 양방향 매핑으로 O(1) 검색 - 완벽한 설계!
 * - 1-based 인덱싱: 직관적이고 실수 방지
 * - BufferedReader 활용: 대용량 입력 처리 최적화
 * - 문제점 1: 불필요한 배열 저장 (Q, answer)
 * - 문제점 2: 숫자/문자 판별 로직 미구현
 * - 문제점 3: 출력 로직 미구현
 * - 문제점 4: -1 인덱싱 오류 (1-based인데 0-based로 계산)
 *
 * 🚀 개선된 해법들:
 * 1. 바로 처리: 배열 저장 없이 즉시 처리로 메모리 절약
 * 2. try-catch: NumberFormatException 활용한 숫자 판별
 * 3. StringBuilder: 출력 최적화
 *
 * 🔍 핵심 패턴 분석:
 * - 양방향 매핑: Array(번호→이름) + HashMap(이름→번호)
 * - 숫자 판별: Character.isDigit() vs try-catch
 * - 1-based 인덱싱: 문제에서 1부터 시작할 때 자연스러운 처리
 * - 메모리 최적화: 불필요한 저장 배열 제거
 *
 * 📊 성능 비교:
 * - 원본: O(N + M) 시간, O(N + M) 공간
 * - 개선 1: O(N + M) 시간, O(N) 공간 (메모리 최적화)
 * - 개선 2: O(N + M) 시간, O(N) 공간 (try-catch 방식)
 *
 * 💡 학습 포인트:
 * 1. 양방향 검색 → 두 자료구조 조합 (Array + Map)
 * 2. 1-based vs 0-based 인덱싱 주의
 * 3. 숫자 문자열 판별 방법 3가지:
 *    - Character.isDigit() ← 권장 (가장 빠름)
 *    - try-catch NumberFormatException ← 간단함
 *    - 정규식 query.matches("\\d+") ← 비권장이지만 이런 방법도 있다~ (성능 느림)
 * 4. 메모리 최적화: 즉시 처리 vs 저장 후 처리
 */
