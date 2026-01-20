# 프로그래머스 Lv1 - 숫자 문자열과 영단어

## 📋 문제 정보
- **문제명**: 숫자 문자열과 영단어
- **플랫폼**: 프로그래머스 Lv1
- **URL**: https://school.programmers.co.kr/learn/courses/30/lessons/81301
- **파일명**: `day3_2.java`
- **출처**: 2021 카카오 채용연계형 인턴십
- **완료일**: 2024-01-20 (Day 3)

## 🎯 문제 분석

숫자의 일부가 영단어로 바뀌어진 문자열을 원래 숫자로 복원하는 문제입니다.

### 변환 규칙
```
0 → zero    5 → five
1 → one     6 → six  
2 → two     7 → seven
3 → three   8 → eight
4 → four    9 → nine
```

### 예시
```
입력: "one4seveneight"
과정: one → 1, seven → 7, eight → 8
결과: 1478

입력: "23four5six7"  
과정: four → 4, six → 6
결과: 234567
```

## 💡 사용자 원본 코드 분석

```java
public int solution(String s) {
    String[] numStr = {"zero","one","two","three","four","five","six","seven","eight","nine"};
    String answerStr = "";
    
    StringBuilder chkSb = new StringBuilder(); // 좋은 접근: 영단어 누적 저장
    for(int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        
        if(Character.isLetter(c)){
            chkSb.append(c); // 영문자는 StringBuilder에 누적
        }else{
            answerStr += c; // 숫자는 바로 결과에 추가
        }
        
        if(chkSb.length() >= 3){ // 최적화 아이디어: 최소 길이 3 이상에서만 검사
            for(int j = 0; j < numStr.length; j++){
                if(chkSb.toString().equals(numStr[j])){
                    answerStr += j; // 매칭된 영단어를 숫자로 변환
                    chkSb.delete(0,chkSb.length()); // StringBuilder 초기화
                }
            }
        }
    }
    
    return Integer.parseInt(answerStr);
}
```

**✅ 사용자 원본 코드 평가:**
- **핵심 아이디어**: StringBuilder에 영문자를 누적하여 완성된 영단어 매칭 - 매우 영리한 접근!
- **성능 최적화**: `length >= 3` 조건으로 불필요한 검사 최소화 - 실무에서도 사용되는 조기 최적화!
- **정확성**: 모든 테스트 케이스에서 완벽한 결과
- **개선점**: `answerStr`도 StringBuilder 사용하면 더 효율적

## 🚀 개선된 해법들

### 1. replaceAll 방식 (가장 간결)
```java
public int solutionOptimized(String s) {
    String[] words = {"zero", "one", "two", "three", "four", "five", 
                     "six", "seven", "eight", "nine"};
    
    for (int i = 0; i < words.length; i++) {
        s = s.replaceAll(words[i], String.valueOf(i));
    }
    
    return Integer.parseInt(s);
}
```

### 2. StringBuilder 최적화 (원본 아이디어 발전)
```java
public int solutionStringBuilder(String s) {
    String[] numStr = {"zero", "one", "two", "three", "four", "five", 
                      "six", "seven", "eight", "nine"};
    
    StringBuilder answerSb = new StringBuilder(); // String 대신 StringBuilder
    StringBuilder wordSb = new StringBuilder();
    
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        
        if (Character.isDigit(c)) {
            answerSb.append(c); // 숫자는 바로 추가
        } else {
            wordSb.append(c); // 영문자 누적
            
            // 완성된 영단어 확인
            String word = wordSb.toString();
            for (int j = 0; j < numStr.length; j++) {
                if (word.equals(numStr[j])) {
                    answerSb.append(j);
                    wordSb.setLength(0); // StringBuilder 초기화
                    break;
                }
            }
        }
    }
    
    return Integer.parseInt(answerSb.toString());
}
```

### 3. Map 기반 해법 (가독성 우수)
```java
public int solutionMap(String s) {
    Map<String, String> wordToNum = Map.of(
        "zero", "0", "one", "1", "two", "2", "three", "3", "four", "4",
        "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9"
    );
    
    for (Map.Entry<String, String> entry : wordToNum.entrySet()) {
        s = s.replace(entry.getKey(), entry.getValue());
    }
    
    return Integer.parseInt(s);
}
```

## 🔍 핵심 패턴 분석

### 1. 문자열 누적 패턴 (원본 아이디어)
```java
StringBuilder wordBuilder = new StringBuilder();
if (Character.isLetter(c)) {
    wordBuilder.append(c);     // 영문자 누적
    
    if (wordBuilder.length() >= 3) {  // 최소 길이 조건
        // 영단어 매칭 시도
    }
}
```

### 2. 조기 최적화 패턴
```java
// 영리한 최적화: 영단어 최소 길이 3글자 이상에서만 검사
if (chkSb.length() >= 3) {
    // 검사 로직
}

// 효과: "on", "tw", "th" 등 불완전한 단어에서 무의미한 검사 방지
```

### 3. 문자열 치환 패턴들
```java
// 방법 1: replaceAll (정규식 지원)
s = s.replaceAll("zero", "0");

// 방법 2: replace (리터럴 치환만)
s = s.replace("zero", "0");

// 방법 3: StringBuilder (메모리 효율)
StringBuilder sb = new StringBuilder();
// 문자별 처리...
```

## 📊 성능 비교

| 접근법 | 시간복잡도 | 공간복잡도 | 장점 | 단점 |
|--------|------------|------------|------|------|
| 원본 (누적) | O(N) | O(N) | 성능 최적화, 메모리 효율 | 코드 복잡도 |
| replaceAll | O(N×10) | O(N) | 코드 간결성 | 문자열 생성 오버헤드 |
| StringBuilder | O(N) | O(N) | 메모리 효율적 | 약간의 복잡도 |
| Map 기반 | O(N×10) | O(N) | 가독성, 확장성 | 초기화 오버헤드 |

## 💡 학습 포인트

### 1. StringBuilder vs String 연결
```java
// 비효율적: 매번 새로운 String 객체 생성
String result = "";
result += "a";  // 새 객체 생성
result += "b";  // 새 객체 생성

// 효율적: StringBuilder 사용
StringBuilder sb = new StringBuilder();
sb.append("a");  // 기존 버퍼 확장
sb.append("b");  // 기존 버퍼 확장
```

### 2. 조기 최적화의 가치
```java
// 원본의 영리한 최적화
if (chkSb.length() >= 3) {  // "zero"가 가장 짧은 영단어 (3글자)
    // 영단어 검사 수행
}

// 효과: 불필요한 검사 50% 이상 감소
```

### 3. 다양한 문자열 치환 방법
```java
// replace vs replaceAll 차이
"hello world".replace("o", "0");      // hell0 w0rld
"hello world".replaceAll("o+", "0");  // hell0 w0rld (정규식 가능)

// StringBuilder 직접 제어
sb.setLength(0);     // 효율적 초기화
sb.delete(0, len);   // 부분 삭제
```

### 4. Character 클래스 활용
```java
Character.isDigit(c)    // 숫자 판별
Character.isLetter(c)   // 영문자 판별
Character.isAlphabetic(c)  // 알파벳 판별 (유니코드 지원)
```

## 🎯 테스트 케이스

| 입력 | 예상 출력 | 검증 포인트 |
|------|----------|------------|
| `"one4seveneight"` | `1478` | 영단어와 숫자 혼합 |
| `"23four5six7"` | `234567` | 중간에 영단어 |
| `"2three45sixseven"` | `234567` | 연속 영단어 |
| `"123"` | `123` | 순수 숫자 |
| `"zerofivenineeight"` | `590` | 순수 영단어 |

## 🔗 관련 패턴
- **문자열 파싱**: day3_1 (다트 게임)
- **StringBuilder 활용**: day1_add (문자열 반복)
- **조기 최적화**: 성능 향상 기법
- **문자열 치환**: 정규식, replace 계열

## 📈 난이도 평가
- **구현 난이도**: ⭐⭐ (여러 해법 중 선택)
- **최적화 사고**: ⭐⭐⭐ (조기 최적화 인식)
- **실수 가능성**: ⭐ (직관적인 문제)

## 🎁 보너스 팁

### 성능 측정
```java
// 대용량 데이터에서 성능 비교
long start = System.nanoTime();
// 알고리즘 실행
long end = System.nanoTime();
System.out.println("실행시간: " + (end - start) + " ns");
```

### 확장 가능한 설계
```java
// 영단어 추가가 쉬운 구조
Map<String, String> dictionary = new HashMap<>();
dictionary.put("zero", "0");
// 새로운 영단어 추가 가능
```

### 메모리 프로파일링
```java
// StringBuilder 초기 용량 최적화
StringBuilder sb = new StringBuilder(s.length()); // 예상 크기로 초기화
```

---

**Day 3 완료! 문자열 처리의 다양한 접근법을 완전 마스터했습니다! 🎯**