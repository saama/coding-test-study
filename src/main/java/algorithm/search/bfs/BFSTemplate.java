package algorithm.search.bfs;

import java.util.*;

/**
 * BFS 문제 해결 템플릿
 * 
 * 사용 예시:
 * - 최단거리 문제 (미로, 게임 맵)
 * - 레벨별 탐색 (트리의 레벨별 노드 방문)
 * - 영역 탐색 (연결된 구역의 크기)
 */
public class BFSTemplate {
    
    // 2D 배열용 BFS 템플릿 (가장 자주 사용됨)
    public int bfs2D(int[][] grid, int startX, int startY) {
        int n = grid.length;
        int m = grid[0].length;
        
        // 방향 벡터 (상, 하, 좌, 우)
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        
        // 시작점 설정
        queue.offer(new int[]{startX, startY});
        visited[startX][startY] = true;
        
        int distance = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            // 현재 레벨의 모든 노드 처리
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];
                
                // 목표 지점 도달 체크
                if (isTarget(x, y)) {
                    return distance;
                }
                
                // 4방향 탐색
                for (int dir = 0; dir < 4; dir++) {
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    
                    // 범위 체크
                    if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
                    
                    // 방문 체크
                    if (visited[nx][ny]) continue;
                    
                    // 조건 체크 (벽, 장애물 등)
                    if (grid[nx][ny] == 0) continue; // 0은 벽이라고 가정
                    
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
            distance++;
        }
        
        return -1; // 도달할 수 없음
    }
    
    // 그래프용 BFS 템플릿 (인접 리스트)
    public int bfsGraph(List<List<Integer>> graph, int start, int target) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(start);
        visited[start] = true;
        
        int distance = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                
                if (current == target) {
                    return distance;
                }
                
                // 인접한 노드들 탐색
                for (int next : graph.get(current)) {
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
            distance++;
        }
        
        return -1; // 도달할 수 없음
    }
    
    // 다중 시작점 BFS (여러 시작점에서 동시 탐색)
    public int multiSourceBFS(int[][] grid, List<int[]> starts) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        
        // 모든 시작점을 큐에 추가
        for (int[] start : starts) {
            queue.offer(start);
            visited[start[0]][start[1]] = true;
        }
        
        int time = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];
                
                for (int dir = 0; dir < 4; dir++) {
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    
                    if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
                    if (visited[nx][ny] || grid[nx][ny] == -1) continue;
                    
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
            time++;
        }
        
        return time;
    }
    
    // 조건 확인 헬퍼 메서드
    private boolean isTarget(int x, int y) {
        // 목표 조건을 여기에 구현
        return false;
    }
    
    // 사용 예시: 미로 탐색
    public static void main(String[] args) {
        BFSTemplate template = new BFSTemplate();
        
        // 예시 그리드 (1: 이동 가능, 0: 벽)
        int[][] grid = {
            {1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 0, 1, 0},
            {0, 1, 1, 1, 1}
        };
        
        int distance = template.bfs2D(grid, 0, 0); // (0,0)에서 시작
        System.out.println("최단 거리: " + distance);
    }
}