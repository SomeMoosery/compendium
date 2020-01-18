import java.util.*;

public class LongestPalindrome {
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return null;
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int oneLetterExpand = expand(s, i, i);
            int twoLetterExpand = expand(s, i, i+1);
            int tempMax = Math.max(oneLetterExpand, twoLetterExpand); 

            if (tempMax > end - start) {
                start = i - ((tempMax-1)/2);
                end = i + (tempMax/2);
            }
        }

        return s.substring(start, end+1);
    }

    private static int expand(String s, int left, int right) {
        // Check edge cases
        if (s == null || left > right) return 0;

        // Expand outwards from the given left and right values
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
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
            System.out.println("Longest palindrome: " + longestPalindrome(testSuite.get(i)) + "\n");
        }
    }
}