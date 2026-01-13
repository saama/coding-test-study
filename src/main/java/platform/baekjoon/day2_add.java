package platform.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 백준 최솟값, 최댓값 구하기 문제
 * https://www.acmicpc.net/problem/10818 (백준 10818번)
 * 시간복잡도: O(N log N) → O(N) 최적화 가능
 * 공간복잡도: O(N) → O(1) 최적화 가능
 */
public class day2_add {

    // 사용자 원본 해법 (정렬 사용)
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 입력 받기
         int T = Integer.parseInt(br.readLine()); // T → n으로 변수명 개선 권장
         st = new StringTokenizer(br.readLine());
         int[] arr = new int[T]; // 배열 저장 (O(N) 공간복잡도)
        for (int i = 0; i < T; i++) {
            int a = Integer.parseInt(st.nextToken());
            arr[i] = a;
        }

        Arrays.sort(arr); // O(N log N) 시간복잡도 - 최솟값/최댓값만 구할 때는 과도함

        // 결과 출력
        System.out.println(arr[0]+" "+arr[arr.length-1]); // 정렬 후 첫번째/마지막 → 최솟값/최댓값
        
        // ✅ 원본 로직은 완전히 정확함. 다만 효율성 개선 여지 있음
    }
    
    // 개선된 해법 (O(N) 시간복잡도, O(1) 공간복잡도)
    public static void solutionOptimized() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int n = Integer.parseInt(br.readLine()); // 명확한 변수명 사용
        st = new StringTokenizer(br.readLine());
        
        int min = Integer.MAX_VALUE; // 최댓값으로 초기화
        int max = Integer.MIN_VALUE; // 최솟값으로 초기화
        
        // 한 번의 순회로 최솟값과 최댓값 동시 추적
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            min = Math.min(min, num); // 현재까지의 최솟값 갱신
            max = Math.max(max, num); // 현재까지의 최댓값 갱신
        }
        
        System.out.println(min + " " + max);
    }

}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (정렬 사용):
 * - 정확성: 완전히 정확한 해법
 * - 시간복잡도: O(N log N) - Arrays.sort() 사용
 * - 공간복잡도: O(N) - 배열 저장 필요
 * - 단점: 최솟값/최댓값만 구할 때는 정렬이 불필요함
 *
 * 🚀 개선된 해법 (Math.min/max 활용):
 * - 시간복잡도: O(N) - 배열을 한 번만 순회
 * - 공간복잡도: O(1) - 추가 배열 저장하지 않음
 * - 장점: 메모리 효율적, 더 빠른 실행시간
 * - 핵심 패턴: Math.min(a, b), Math.max(a, b) 활용
 *
 * 학습 포인트:
 * 1. Integer.MAX_VALUE, Integer.MIN_VALUE를 활용한 초기화
 * 2. 한 번의 순회로 여러 정보를 동시에 추적하는 기법
 * 3. 문제 요구사항에 맞는 최적 알고리즘 선택 (정렬 vs 순회)
 * 4. 시간복잡도 O(N log N) → O(N) 최적화 체험
 */