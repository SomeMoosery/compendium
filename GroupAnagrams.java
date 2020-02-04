import java.util.*;

public class GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] c = strs[i].toCharArray();
            Arrays.sort(c);
            String tempString = String.valueOf(c);
            // System.out.println(strs[i]);
            if (map.containsKey(tempString)) {
                map.get(tempString).add(strs[i]);
            } else {
                map.put(tempString, new ArrayList<String>());
                map.get(tempString).add(strs[i]);
            }
        } 
        
        // System.out.println(map);
        
        List<List<String>> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        
        return list;
    }

    public static void main(String[] args) {
        String[] test1 = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(test1);

        System.out.println("Original List: ");
        for (String s : test1) System.out.print(s + ", ");
        System.out.println("\n");

        System.out.println("Grouped List: ");
        for (List<String> list : result) {
            for (String s : list) {
                System.out.print(s + " | ");
            }
            System.out.println();
        }
    }
}