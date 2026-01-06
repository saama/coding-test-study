package algorithm.dp;

import java.util.*;

/**
 * DP 문제 해결 템플릿
 * 
 * 사용 예시:
 * - 최적 부분 구조 문제 (피보나치, 계단 오르기)
 * - 최적화 문제 (최소 비용, 최대 이익)
 * - 경우의 수 문제 (조합, 경로)
 * - 문자열 DP (LCS, 편집 거리)
 */
public class DPTemplate {
    
    // 1차원 DP 템플릿 - 기본형
    public int dp1D(int n) {
        int[] dp = new int[n + 1];
        
        // 기저 조건 설정
        dp[0] = 0;
        if (n > 0) dp[1] = 1;
        
        // 점화식 적용
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2]; // 피보나치 예시
        }
        
        return dp[n];
    }
    
    // 1차원 DP - 공간 최적화
    public int dp1DOptimized(int n) {
        if (n <= 1) return n;
        
        int prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // 2차원 DP 템플릿 - 격자 경로
    public int dp2DGrid(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        
        // 기저 조건
        dp[0][0] = grid[0][0];
        
        // 첫 번째 행 초기화
        for (int j = 1; j < m; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        
        // 첫 번째 열 초기화
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        
        // DP 테이블 채우기
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        
        return dp[n-1][m-1];
    }
    
    // 배낭 문제 (0/1 Knapsack)
    public int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                // 현재 아이템을 넣을 수 없는 경우
                if (weights[i-1] > w) {
                    dp[i][w] = dp[i-1][w];
                } else {
                    // 넣는 경우와 안 넣는 경우 중 최대값
                    dp[i][w] = Math.max(
                        dp[i-1][w], // 안 넣는 경우
                        dp[i-1][w-weights[i-1]] + values[i-1] // 넣는 경우
                    );
                }
            }
        }
        
        return dp[n][capacity];
    }
    
    // LCS (최장 공통 부분 수열)
    public int lcs(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        
        return dp[n][m];
    }
    
    // 편집 거리 (Edit Distance)
    public int editDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[][] dp = new int[n + 1][m + 1];
        
        // 기저 조건
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        Math.min(dp[i-1][j], dp[i][j-1]), // 삭제, 삽입
                        dp[i-1][j-1] // 교체
                    );
                }
            }
        }
        
        return dp[n][m];
    }
    
    // 계단 오르기 문제
    public int climbStairs(int n) {
        if (n <= 2) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        
        return dp[n];
    }
    
    // 최대 연속 부배열 합 (Kadane's Algorithm)
    public int maxSubarray(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    // 동전 교환 문제 (Coin Change)
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // 무한대로 초기화
        dp[0] = 0;
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    // 집 도둑 문제 (House Robber)
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    // 메모이제이션을 사용한 Top-Down DP
    private Map<String, Integer> memo = new HashMap<>();
    
    public int topDownDP(int n, int k) {
        String key = n + "," + k;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int result;
        if (n == 0 || k == 0) {
            result = 0;
        } else if (n == 1) {
            result = 1;
        } else {
            result = topDownDP(n-1, k) + topDownDP(n-1, k-1);
        }
        
        memo.put(key, result);
        return result;
    }
    
    // 사용 예시
    public static void main(String[] args) {
        DPTemplate template = new DPTemplate();
        
        // 피보나치 수열
        System.out.println("피보나치(10): " + template.dp1D(10));
        
        // 배낭 문제
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        int capacity = 7;
        System.out.println("배낭 문제: " + template.knapsack(weights, values, capacity));
        
        // LCS
        String str1 = "ABCDGH";
        String str2 = "AEDFHR";
        System.out.println("LCS: " + template.lcs(str1, str2));
        
        // 계단 오르기
        System.out.println("계단 오르기(5): " + template.climbStairs(5));
        
        // 동전 교환
        int[] coins = {1, 3, 4};
        int amount = 6;
        System.out.println("동전 교환: " + template.coinChange(coins, amount));
    }
}