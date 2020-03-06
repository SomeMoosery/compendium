package LeetcodePrep;

import java.util.*;

class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target-nums[i])) {
                return new int[]{map.get(target-nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1,-1};
    }

    public static void main(String[] args) {
        int[] test = new int[]{2,7,11,15};
        System.out.print("Original: ");
        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();
        int[] result = twoSum(test, 9);
        for (int i : result) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}