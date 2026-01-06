package algorithm.greedy;

import java.util.*;

/**
 * 그리디 알고리즘 템플릿
 * 
 * 사용 예시:
 * - 활동 선택 문제 (회의실 배정)
 * - 최소 동전 문제
 * - 최소 스패닝 트리 (크루스칼)
 * - 최단 경로 (다익스트라)
 * - 허프만 코딩
 */
public class GreedyTemplate {
    
    // 회의실 배정 문제 (Activity Selection)
    public int maxMeetings(int[][] meetings) {
        // 끝나는 시간 기준으로 정렬
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[1], b[1]));
        
        int count = 1; // 첫 번째 회의는 항상 선택
        int lastEndTime = meetings[0][1];
        
        for (int i = 1; i < meetings.length; i++) {
            // 다음 회의의 시작 시간이 이전 회의의 끝나는 시간보다 크거나 같으면
            if (meetings[i][0] >= lastEndTime) {
                count++;
                lastEndTime = meetings[i][1];
            }
        }
        
        return count;
    }
    
    // 동전 교환 문제 (그리디로 해결 가능한 경우)
    public int minCoins(int[] coins, int amount) {
        Arrays.sort(coins); // 오름차순 정렬
        
        int count = 0;
        // 큰 동전부터 사용 (역순으로 순회)
        for (int i = coins.length - 1; i >= 0; i--) {
            while (amount >= coins[i]) {
                amount -= coins[i];
                count++;
            }
        }
        
        return amount == 0 ? count : -1; // 거슬러줄 수 없으면 -1
    }
    
    // 분수 배낭 문제 (Fractional Knapsack)
    static class Item {
        int weight;
        int value;
        double ratio;
        
        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.ratio = (double) value / weight;
        }
    }
    
    public double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        Item[] items = new Item[n];
        
        for (int i = 0; i < n; i++) {
            items[i] = new Item(weights[i], values[i]);
        }
        
        // 가치/무게 비율 기준으로 내림차순 정렬
        Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
        
        double totalValue = 0;
        
        for (Item item : items) {
            if (capacity >= item.weight) {
                // 전체 아이템을 넣을 수 있는 경우
                capacity -= item.weight;
                totalValue += item.value;
            } else {
                // 아이템의 일부만 넣을 수 있는 경우
                totalValue += item.value * ((double) capacity / item.weight);
                break;
            }
        }
        
        return totalValue;
    }
    
    // 허프만 코딩
    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left, right;
        
        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.freq, other.freq);
        }
    }
    
    public Map<Character, String> huffmanCoding(Map<Character, Integer> freqMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        // 리프 노드 생성
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.offer(new Node(entry.getKey(), entry.getValue()));
        }
        
        // 허프만 트리 구성
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            
            Node merged = new Node('\0', left.freq + right.freq);
            merged.left = left;
            merged.right = right;
            
            pq.offer(merged);
        }
        
        Node root = pq.poll();
        Map<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);
        
        return codes;
    }
    
    private void generateCodes(Node node, String code, Map<Character, String> codes) {
        if (node == null) return;
        
        if (node.ch != '\0') { // 리프 노드
            codes.put(node.ch, code);
            return;
        }
        
        generateCodes(node.left, code + "0", codes);
        generateCodes(node.right, code + "1", codes);
    }
    
    // 점프 게임 (Jump Game)
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) return false; // 도달할 수 없는 위치
            
            maxReach = Math.max(maxReach, i + nums[i]);
            
            if (maxReach >= nums.length - 1) return true;
        }
        
        return maxReach >= nums.length - 1;
    }
    
    // 가장 큰 수 만들기
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        
        // 사용자 정의 비교: a+b vs b+a
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        
        // 모든 수가 0인 경우
        if (strs[0].equals("0")) return "0";
        
        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
        }
        
        return result.toString();
    }
    
    // 주유소 문제 (Gas Station)
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;
        int tank = 0, start = 0;
        
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            
            tank += gas[i] - cost[i];
            
            // 연료 부족시 다음 주유소부터 다시 시작
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        
        return totalGas >= totalCost ? start : -1;
    }
    
    // 체육복 문제 (프로그래머스)
    public int gymSuit(int n, int[] lost, int[] reserve) {
        boolean[] hasGym = new boolean[n + 1];
        Arrays.fill(hasGym, true);
        
        // 잃어버린 학생 표시
        for (int l : lost) {
            hasGym[l] = false;
        }
        
        // 여벌 있는 학생이 본인이 잃어버린 경우 처리
        for (int r : reserve) {
            if (!hasGym[r]) {
                hasGym[r] = true;
                continue;
            }
            
            // 앞번호 학생에게 빌려주기
            if (r > 1 && !hasGym[r - 1]) {
                hasGym[r - 1] = true;
            }
            // 뒷번호 학생에게 빌려주기
            else if (r < n && !hasGym[r + 1]) {
                hasGym[r + 1] = true;
            }
        }
        
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (hasGym[i]) count++;
        }
        
        return count;
    }
    
    // 문자열 압축 (그리디 접근)
    public int compress(char[] chars) {
        int write = 0;
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 0;
            
            // 동일한 문자의 개수 세기
            while (i < chars.length && chars[i] == currentChar) {
                i++;
                count++;
            }
            
            // 문자 저장
            chars[write++] = currentChar;
            
            // 개수가 1보다 크면 숫자도 저장
            if (count > 1) {
                String countStr = String.valueOf(count);
                for (char c : countStr.toCharArray()) {
                    chars[write++] = c;
                }
            }
        }
        
        return write;
    }
    
    // 사용 예시
    public static void main(String[] args) {
        GreedyTemplate template = new GreedyTemplate();
        
        // 회의실 배정 문제
        int[][] meetings = {{1, 4}, {3, 5}, {0, 6}, {5, 7}, {3, 8}, {5, 9}, {6, 10}, {8, 11}};
        System.out.println("최대 회의 수: " + template.maxMeetings(meetings));
        
        // 동전 교환
        int[] coins = {1, 5, 10, 25};
        int amount = 30;
        System.out.println("최소 동전 개수: " + template.minCoins(coins, amount));
        
        // 분수 배낭
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 50;
        System.out.println("최대 가치: " + template.fractionalKnapsack(weights, values, capacity));
        
        // 가장 큰 수 만들기
        int[] nums = {3, 30, 34, 5, 9};
        System.out.println("가장 큰 수: " + template.largestNumber(nums));
    }
}