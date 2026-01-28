package platform.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 1181번 - 단어 정렬
 * https://www.acmicpc.net/problem/1181
 * 시간복잡도: O(N log N) (정렬 알고리즘)
 * 공간복잡도: O(N) (문자열 배열)
 */
public class day5_add {
    // 사용자 원본 해법 (길이순 + 사전순 정렬)
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        String[] strs = new String[N];

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine()); // 문제점: StringTokenizer 불필요 (한 줄에 단어 하나)
            strs[n] = st.nextToken();
        }

        Arrays.sort(strs,(a,b)-> {
            if(a.length()!=b.length()){
                return a.length()-b.length(); // 길이 오름차순
            }else{
                return a.compareTo(b); // 사전순 오름차순
            }
        });

        System.out.println(Arrays.toString(strs)); // 문제점: 배열 형태로 출력 (문제 요구사항과 다름)
    }

    // 개선된 해법 1: 올바른 출력 형식 + 중복 제거
    public static void solutionFixed() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        String[] strs = new String[N];

        for (int i = 0; i < N; i++) {
            strs[i] = br.readLine(); // StringTokenizer 제거 (더 간단)
        }

        // 중복 제거를 위해 정렬 후 중복 체크
        Arrays.sort(strs, (a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length(); // 길이 우선
            } else {
                return a.compareTo(b); // 사전순 보조
            }
        });

        // 올바른 출력 형식 (한 줄씩) + 중복 제거
        System.out.println(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].equals(strs[i-1])) { // 중복 제거
                System.out.println(strs[i]);
            }
        }
    }

    // 개선된 해법 2: Set을 활용한 중복 제거 + Stream 정렬
    public static void solutionOptimized() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // Set으로 중복 제거 + Stream으로 정렬 후 출력
        Set<String> wordSet = new HashSet<>();
        for (int i = 0; i < N; i++) {
            wordSet.add(br.readLine());
        }

        wordSet.stream()
            .sorted((a, b) -> {
                if (a.length() != b.length()) {
                    return a.length() - b.length();
                } else {
                    return a.compareTo(b);
                }
            })
            .forEach(System.out::println);
    }

    // 개선된 해법 3: HashSet을 배열로 변환 후 정렬 (Stream 미사용)
    public static void solutionHashSetWithArray() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        Set<String> wordSet = new HashSet<>();

        for (int i = 0; i < N; i++) {
            wordSet.add(br.readLine());
        }

        // HashSet을 배열로 변환
        String[] words = wordSet.toArray(new String[0]);
        
        // 배열 정렬 (Stream 없이)
        Arrays.sort(words, (a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            } else {
                return a.compareTo(b);
            }
        });
        
        // 출력
        for (String word : words) {
            System.out.println(word);
        }
    }
    
    // 개선된 해법 4: HashSet을 List로 변환 후 정렬
    public static void solutionHashSetWithList() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        Set<String> wordSet = new HashSet<>();

        for (int i = 0; i < N; i++) {
            wordSet.add(br.readLine());
        }

        // HashSet을 List로 변환
        List<String> wordList = new ArrayList<>(wordSet);
        
        // List 정렬 (Stream 없이)
        wordList.sort((a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            } else {
                return a.compareTo(b);
            }
        });
        
        // 출력
        for (String word : wordList) {
            System.out.println(word);
        }
    }
    
    // 개선된 해법 5: TreeSet 활용 (자동 정렬)
    public static void solutionTreeSet() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        
        // TreeSet에 커스텀 Comparator 설정하면 자동으로 정렬됨
        Set<String> wordSet = new TreeSet<>((a, b) -> {
            if (a.length() != b.length()) {
                return a.length() - b.length();
            } else {
                return a.compareTo(b);
            }
        });

        for (int i = 0; i < N; i++) {
            wordSet.add(br.readLine());
        }

        // TreeSet은 이미 정렬되어 있으므로 바로 출력
        for (String word : wordSet) {
            System.out.println(word);
        }
    }

    // 테스트용 메서드 (각각 독립적으로 실행)
    public static void testSolutionFixed() throws IOException {
        System.out.println("=== 개선된 해법 1: 올바른 출력 + 중복 제거 ===");
        solutionFixed();
    }

    public static void testSolutionOptimized() throws IOException {
        System.out.println("=== 개선된 해법 2: Set + Stream API ===");
        solutionOptimized();
    }

    public static void testHashSetWithArray() throws IOException {
        System.out.println("=== 해법 3: HashSet → 배열 변환 후 정렬 ===");
        solutionHashSetWithArray();
    }

    public static void testHashSetWithList() throws IOException {
        System.out.println("=== 해법 4: HashSet → List 변환 후 정렬 ===");
        solutionHashSetWithList();
    }

    public static void testTreeSet() throws IOException {
        System.out.println("=== 해법 5: TreeSet 자동 정렬 ===");
        solutionTreeSet();
    }
}

/*
 * 코드 분석 및 개선점:
 *
 * ✅ 원본 해법 (사용자 코드):
 * - 아이디어: 길이 우선 → 사전순 정렬 - 정확한 접근!
 * - Arrays.sort + 커스텀 Comparator 활용 - 적절한 선택
 * - 다중 조건 정렬 구현 - 올바른 로직
 * - 문제점 1: StringTokenizer 불필요 (한 줄에 단어 하나만 있음)
 * - 문제점 2: Arrays.toString() 출력 - 배열 형태 출력 (문제 요구사항: 한 줄씩)
 * - 문제점 3: 중복 단어 제거 누락 (문제 조건: 같은 단어 여러 번 입력시 한 번만 출력)
 *
 * 🚀 개선된 해법들:
 * 1. solutionFixed: 올바른 출력 형식 + 정렬 후 중복 제거
 * 2. solutionOptimized: HashSet으로 중복 제거 + Stream API 활용
 * 3. solutionHashSetWithArray: HashSet → 배열 변환 후 Arrays.sort (Stream 미사용)
 * 4. solutionHashSetWithList: HashSet → List 변환 후 List.sort (Stream 미사용)
 * 5. solutionTreeSet: TreeSet 커스텀 Comparator로 자동 정렬 (가장 효율적)
 *
 * 🔍 핵심 패턴 분석:
 * - 다중 조건 정렬: 길이 우선 → 사전순 보조
 * - 중복 제거 방법 2가지: Set 활용 vs 정렬 후 비교
 * - 출력 형식: Arrays.toString() ❌ vs 개별 출력 ✅
 * - 입력 처리: StringTokenizer vs br.readLine() 직접 사용
 *
 * 📊 성능 비교:
 * - 해법 1-4: O(N log N) 시간, O(N) 공간
 * - 해법 5 (TreeSet): O(N log N) 시간, O(N) 공간 (삽입 시 자동 정렬)
 * - HashSet → 변환: 중복 제거 효율적, 변환 과정 추가 비용
 * - TreeSet: 가장 효율적 (삽입과 동시에 정렬, 별도 정렬 불필요)
 * - Stream vs 비Stream: 성능 차이 미미, 가독성 vs 호환성
 *
 * 💡 학습 포인트:
 * 1. 백준 문제 출력 형식 주의:
 *    - Arrays.toString() → [a, b, c] (잘못됨)
 *    - 개별 출력 → a\nb\nc (올바름)
 * 2. 입력 처리 효율성:
 *    - StringTokenizer: 공백으로 구분된 여러 토큰
 *    - br.readLine(): 한 줄 전체 (단어 하나일 때 더 간단)
 * 3. 중복 제거 방법:
 *    - HashSet: 입력 시점에 중복 제거 (효율적)
 *    - 정렬 후 비교: 추가 메모리 없이 중복 제거
 * 4. 다중 조건 정렬 패턴:
 *    - if-else 분기 vs Comparator.comparing 체이닝
 * 5. HashSet을 Stream 없이 정렬하는 3가지 방법:
 *    - toArray(): HashSet → 배열 변환 후 Arrays.sort()
 *    - ArrayList 생성자: new ArrayList<>(hashSet) 후 List.sort()
 *    - TreeSet: 생성 시 Comparator 설정으로 자동 정렬 (가장 효율적)
 * 6. Stream vs 비Stream 선택:
 *    - Stream: 함수형 스타일, 가독성 좋음
 *    - 비Stream: 호환성 좋음, 메모리 효율성 약간 나음
 */
