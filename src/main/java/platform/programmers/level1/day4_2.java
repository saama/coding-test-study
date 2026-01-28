package platform.programmers.level1;

import java.util.*;

/**
 * 프로그래머스 Lv1 - 전화번호 목록
 * https://school.programmers.co.kr/learn/courses/30/lessons/42577
 * 시간복잡도: O(N²×M) → O(N log N) 최적화 가능
 * 공간복잡도: O(N)
 * 2017 카카오코드 예선
 */
public class day4_2 {
    // 사용자 원본 해법 (길이별 분류 접근)
    public boolean solution(String[] phone_book) {
        boolean answer = true;

        //1. length, HashSet<String>으로 각 길이별 전화번호 Map생성
        //2. phone_book만큼 반복문 돌며(길이 작은순으로 정렬하면 좋음) 해당 전화번호의 길이부터 Map에 조회하여 있으면 false 전체조회해도 없으면 true
        Map<Integer, Set<String>> lengthAndPhone= new HashMap<>();
        for (int i = 0; i < phone_book.length; i++) {
            String phone = phone_book[i];
            int length = phone.length();
            lengthAndPhone.computeIfAbsent(length,k1 -> new HashSet<>()).add(phone); // 제네릭 타입 추가 필요
        }
        Arrays.sort(phone_book); // 사전순 정렬 (길이순 원한다면: Arrays.sort(phone_book, Comparator.comparing(String::length)))
        for (int i = 0; i < phone_book.length; i++) {
            int length = phone_book[i].length();
            System.out.println(length +" "+ lengthAndPhone.get(length));

            Set<String> phoneSet = lengthAndPhone.get(length+1); // 문제: length+1만 검사 + null 가능성
            if(phoneSet != null && phoneSet.size() != 0){ // null 체크 필요
                for(String phone : phoneSet){
                    if(phone.startsWith(phone_book[i])){ // 좋은 아이디어: startsWith 활용
                        answer = false;
                        break;
                    }
                }
            }

            if(!answer) break;
        }
        return answer;
        
        // ✅ 핵심 아이디어(길이별 분류, startsWith 활용)는 창의적! 로직 수정과 null 안전 처리만 보완하면 완성
    }
    
    // 개선된 해법 1: 사용자 아이디어 발전 (길이별 분류 + 모든 길이 검사)
    public boolean solutionOptimized(String[] phone_book) {
        Map<Integer, Set<String>> lengthMap = new HashMap<>();
        
        // 길이별로 전화번호 분류
        for (String phone : phone_book) {
            lengthMap.computeIfAbsent(phone.length(), k -> new HashSet<>()).add(phone);
        }
        
        // 길이 순으로 정렬된 키 리스트
        List<Integer> lengths = new ArrayList<>(lengthMap.keySet());
        lengths.sort(Integer::compareTo);
        
        // 각 전화번호에 대해 더 긴 길이들 검사
        for (String phone : phone_book) {
            for (int len : lengths) {
                if (len <= phone.length()) continue; // 자신보다 긴 번호만 검사
                
                Set<String> longerPhones = lengthMap.get(len);
                for (String longerPhone : longerPhones) {
                    if (longerPhone.startsWith(phone)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // 개선된 해법 2: 간단한 정렬 + startsWith (가장 직관적)
    public boolean solutionSimple(String[] phone_book) {
        Arrays.sort(phone_book); // 사전순 정렬
        
        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                return false;
            }
        }
        return true;
    }
    
    // 개선된 해법 3: HashSet 활용 (O(N) 최적화)
    public boolean solutionHashSet(String[] phone_book) {
        Set<String> phoneSet = new HashSet<>();
        for (String phone : phone_book) {
            phoneSet.add(phone);
        }
        
        for (String phone : phone_book) {
            for (int i = 1; i < phone.length(); i++) {
                if (phoneSet.contains(phone.substring(0, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        day4_2 d = new day4_2();
        
        String[] test1 = {"119", "97674223", "1195524421"};
        String[] test2 = {"123","456","789"};
        String[] test3 = {"12","123","1235","567","88"};
        
        System.out.println("=== 테스트 케이스 ===");
        System.out.println("Test1 원본: " + d.solution(test1.clone()));
        System.out.println("Test1 개선: " + d.solutionOptimized(test1.clone()));
        System.out.println("Test1 간단: " + d.solutionSimple(test1.clone()));
        System.out.println("Test1 해시: " + d.solutionHashSet(test1.clone()));
        
        System.out.println("\nTest2 원본: " + d.solution(test2.clone()));
        System.out.println("Test2 개선: " + d.solutionOptimized(test2.clone()));
        System.out.println("Test2 간단: " + d.solutionSimple(test2.clone()));
        System.out.println("Test2 해시: " + d.solutionHashSet(test2.clone()));
        
        System.out.println("\n예상 결과: Test1=false, Test2=true");
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (길이별 분류):
 * - 아이디어: 길이별로 전화번호 분류 후 순차 검사 - 매우 창의적!
 * - startsWith() 활용: 접두어 검사에 최적화된 메서드 선택
 * - computeIfAbsent 패턴: Map + Set 조합 완벽 적용
 * - 문제점 1: 제네릭 타입 누락 (new HashSet() → new HashSet<>())
 * - 문제점 2: length+1만 검사 (모든 더 긴 길이 검사 필요)
 * - 문제점 3: null 체크 누락 (NullPointerException 위험)
 * - 문제점 4: Arrays.sort()가 사전순이라 길이순 정렬 아님
 *
 * 🚀 개선된 해법들:
 * 1. 길이별 분류 발전: 사용자 아이디어 + 모든 길이 검사
 * 2. 간단한 정렬: Arrays.sort() + 인접 요소 비교만으로 해결
 * 3. HashSet 최적화: O(N×M) → O(N×L) 시간복잡도 (L: 평균 번호 길이)
 *
 * 🔍 핵심 패턴 분석:
 * - 접두어 검사: startsWith() vs substring() + contains()
 * - 정렬 활용: 사전순 정렬 시 접두어 관계는 인접하게 배치
 * - Map 분류: 길이별 분류로 비교 범위 축소
 * - 조기 종료: 접두어 발견 즉시 false 반환
 *
 * 📊 성능 비교:
 * - 원본: O(N²×M) (M: 평균 번호 길이)
 * - 개선 1: O(N×K×M) (K: 서로 다른 길이 개수)  
 * - 개선 2: O(N log N + N×M) (정렬 + 비교)
 * - 개선 3: O(N×L²) (L: 평균 번호 길이)
 */
