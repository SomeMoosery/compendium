class GCDString {
    public static String gcdOfStrings(String str1, String str2) {
        String result = "";
        int start = 0, end = 0;
        
        int i = 0;
        while (i < str1.length() && i < str2.length()) {
            if (str1.charAt(i) == str2.charAt(i)) {
                end++;
            } else {
                start = end+1;
                end = end+1;
            }
            i++;
        }
        
        if (start == end) return "";
        if (str1.length() > str2.length() && str1.charAt(end) != str1.charAt(0)) return "";
        if (str2.length() > str1.length() && str2.charAt(end) != str2.charAt(0)) return "";
        
        result = str1.substring(start, end);
        
        while (str1.length() % result.length() != 0 || str2.length() % result.length() != 0) {
            end--;
            result = str1.substring(start, end);
        }
        
        return result;
    }

    public static void main(String[] args) {
        String str1 = "ABCABC";
        String str2 = "ABC";
        System.out.println(str1 + " | " + str2 + " | result: " + gcdOfStrings(str1, str2));

        str1 = "ABABAB";
        str2 = "ABAB";
        System.out.println(str1 + " | " + str2 + " | result: " + gcdOfStrings(str1, str2));

        str1 = "LEET";
        str2 = "CODE";
        System.out.println(str1 + " | " + str2 + " | result: " + gcdOfStrings(str1, str2));

        str1 = "TAUXXTAUXXTAUXXTAUXXTAUXX";
        str2 = "TAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXX";
        System.out.println(str1 + " | " + str2 + " | result: " + gcdOfStrings(str1, str2));
    }
}