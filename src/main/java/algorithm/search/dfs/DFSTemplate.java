package algorithm.search.dfs;

import java.util.*;

/**
 * DFS 문제 해결 템플릿
 * 
 * 사용 예시:
 * - 모든 경로 탐색 (순열, 조합)
 * - 백트래킹 (N-Queen, 스도쿠)
 * - 연결 요소 개수 (섬의 개수, 네트워크)
 * - 사이클 검출
 */
public class DFSTemplate {
    
    // 2D 배열용 DFS 템플릿
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public int dfs2D(int[][] grid, int startX, int startY) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        
        return dfsHelper(grid, startX, startY, visited);
    }
    
    private int dfsHelper(int[][] grid, int x, int y, boolean[][] visited) {
        int n = grid.length;
        int m = grid[0].length;
        
        // 범위 체크
        if (x < 0 || x >= n || y < 0 || y >= m) return 0;
        
        // 방문 체크 및 조건 체크
        if (visited[x][y] || grid[x][y] == 0) return 0;
        
        visited[x][y] = true;
        int count = 1; // 현재 위치 카운트
        
        // 4방향으로 재귀 탐색
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            count += dfsHelper(grid, nx, ny, visited);
        }
        
        return count;
    }
    
    // 그래프용 DFS 템플릿 (인접 리스트)
    public void dfsGraph(List<List<Integer>> graph, int start, boolean[] visited) {
        visited[start] = true;
        
        // 현재 노드 처리
        processNode(start);
        
        // 인접한 노드들을 재귀적으로 방문
        for (int next : graph.get(start)) {
            if (!visited[next]) {
                dfsGraph(graph, next, visited);
            }
        }
    }
    
    // 순열 생성 DFS (백트래킹)
    public void permutation(int[] arr, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        // 기저 조건: 모든 원소를 선택했을 때
        if (current.size() == arr.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = 0; i < arr.length; i++) {
            if (used[i]) continue; // 이미 사용된 원소는 건너뛰기
            
            // 선택
            used[i] = true;
            current.add(arr[i]);
            
            // 재귀 호출
            permutation(arr, used, current, result);
            
            // 백트래킹 (선택 취소)
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    // 조합 생성 DFS (백트래킹)
    public void combination(int[] arr, int start, int k, List<Integer> current, List<List<Integer>> result) {
        // 기저 조건: k개를 선택했을 때
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            // 선택
            current.add(arr[i]);
            
            // 재귀 호출 (다음 인덱스부터 선택)
            combination(arr, i + 1, k, current, result);
            
            // 백트래킹 (선택 취소)
            current.remove(current.size() - 1);
        }
    }
    
    // N-Queen 문제 템플릿
    public int nQueens(int n) {
        boolean[] cols = new boolean[n];           // 세로선 체크
        boolean[] diag1 = new boolean[2 * n - 1]; // 대각선1 체크
        boolean[] diag2 = new boolean[2 * n - 1]; // 대각선2 체크
        
        return nQueensHelper(0, n, cols, diag1, diag2);
    }
    
    private int nQueensHelper(int row, int n, boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) return 1; // 모든 퀸을 배치했으면 성공
        
        int count = 0;
        for (int col = 0; col < n; col++) {
            int d1 = row - col + n - 1;
            int d2 = row + col;
            
            // 충돌 체크
            if (cols[col] || diag1[d1] || diag2[d2]) continue;
            
            // 퀸 배치
            cols[col] = diag1[d1] = diag2[d2] = true;
            
            // 다음 행으로 재귀
            count += nQueensHelper(row + 1, n, cols, diag1, diag2);
            
            // 백트래킹
            cols[col] = diag1[d1] = diag2[d2] = false;
        }
        
        return count;
    }
    
    // 타겟 넘버 문제 (프로그래머스 Level 2)
    public int targetNumber(int[] numbers, int target) {
        return targetNumberHelper(numbers, 0, 0, target);
    }
    
    private int targetNumberHelper(int[] numbers, int index, int current, int target) {
        // 기저 조건: 모든 숫자를 사용했을 때
        if (index == numbers.length) {
            return current == target ? 1 : 0;
        }
        
        int count = 0;
        // +를 선택하는 경우
        count += targetNumberHelper(numbers, index + 1, current + numbers[index], target);
        // -를 선택하는 경우  
        count += targetNumberHelper(numbers, index + 1, current - numbers[index], target);
        
        return count;
    }
    
    // 연결 요소 개수 구하기
    public int countComponents(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        int components = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfsHelper(grid, i, j, visited);
                    components++;
                }
            }
        }
        
        return components;
    }
    
    // 노드 처리 헬퍼 메서드
    private void processNode(int node) {
        // 노드 처리 로직을 여기에 구현
        System.out.println("Processing node: " + node);
    }
    
    // 사용 예시
    public static void main(String[] args) {
        DFSTemplate template = new DFSTemplate();
        
        // 예시 1: 2D 배열에서 연결된 영역 크기
        int[][] grid = {
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1}
        };
        
        int areaSize = template.dfs2D(grid, 0, 0);
        System.out.println("연결된 영역 크기: " + areaSize);
        
        // 예시 2: 순열 생성
        int[] arr = {1, 2, 3};
        boolean[] used = new boolean[3];
        List<Integer> current = new ArrayList<>();
        List<List<Integer>> permutations = new ArrayList<>();
        
        template.permutation(arr, used, current, permutations);
        System.out.println("순열: " + permutations);
        
        // 예시 3: 타겟 넘버
        int[] numbers = {1, 1, 1, 1, 1};
        int target = 3;
        int ways = template.targetNumber(numbers, target);
        System.out.println("타겟을 만드는 방법의 수: " + ways);
    }
}