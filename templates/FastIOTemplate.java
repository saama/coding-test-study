import java.io.*;
import java.util.*;

public class FastIOTemplate {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    
    public static void main(String[] args) throws IOException {
        // 입력 받기
        // int n = nextInt();
        // String s = nextLine();
        
        // 로직 구현
        
        // 결과 출력
        // bw.write(result + "\n");
        bw.flush();
        bw.close();
    }
    
    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }
    
    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    
    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }
    
    static String nextLine() throws IOException {
        return br.readLine();
    }
}