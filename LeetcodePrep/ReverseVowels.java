package LeetcodePrep;

class ReverseVowels {
    public static String reverseVowels(String s) {
        String vowels = "aAeEiIoOuU";
        char[] chars = s.toCharArray();
        for (int i = 0, j = s.length()-1; i < j; i++) {
            if (vowels.contains(s.substring(i,i+1))) {
                while (!vowels.contains(s.substring(j,j+1))) {
                    j--;
                }
                char temp = s.charAt(i);
                chars[i] = chars[j];
                chars[j] = temp;
                j--;
            }
        }
        
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        String test = "leetcode";
        System.out.println("Test: " + test);
        System.out.println("Result: " + reverseVowels(test));
    }
}