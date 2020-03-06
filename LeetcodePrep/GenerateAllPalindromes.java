package LeetcodePrep;

import java.util.*;

public class GenerateAllPalindromes { 
    public static void generateAllPalindromes(String s) {
        if (s == null || s.length() < 1) System.out.println("Invalid input");
        
        for (int i = 0; i < s.length(); i++) {
            expand(s, i, i);
            if (i+1 < s.length() && s.charAt(i) == s.charAt(i+1)) {
                expand(s, i, i+1);
            }
        }
    }

    private static void expand(String s, int left, int right) {
        if (s == null || left > right) System.out.println("Invalid string");
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
            System.out.print("Palindrome: " + s.substring(left+1, right) + " | ");
        }
        return;
    }

    public static void main(String[] args) {
        List<String> testSuite = new ArrayList<>();
        testSuite.add("aaabbaaa");
        testSuite.add("racecar");
        testSuite.add("abcddefg");
        testSuite.add("");
        testSuite.add("abcdefg");
        
        for (int i = 0; i < testSuite.size(); i++) {
            System.out.println("Test " + i + ": " + testSuite.get(i));
            generateAllPalindromes(testSuite.get(i));
            System.out.println();
        }
    }
}