package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TestUtils {
    
    public static String runWithInput(String input, Runnable runnable) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        try {
            ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            
            System.setIn(testIn);
            System.setOut(new PrintStream(testOut));
            
            runnable.run();
            
            return testOut.toString().trim();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
    
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(" ");
        }
        System.out.println();
    }
    
    public static void printArray(long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(" ");
        }
        System.out.println();
    }
    
    public static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            printArray(row);
        }
    }
}