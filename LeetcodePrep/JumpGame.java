// https://leetcode.com/problems/jump-game/
import java.util.*;

class JumpGame {
    public static boolean canJump(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return true;
        int end = nums.length-1;
        for (int i = 0; i < nums.length-1; i++) {
            int currNum = nums[i];
            if (canJump(i, i, end, currNum)) return true;
        }
        
        return false;
    }
    
    private static boolean canJump(int start, int currPos, int end, int currNum) {
        if (currNum == 0) return false;
        if (start + currNum == end) return true;
        return canJump(start, start+currNum, end, currNum-1);
    }

    public static void main(String[] args) {
        List<int[]> testSuite = new ArrayList<>();
        int[] test1 = new int[]{2,3,1,1,4};
        testSuite.add(test1);
        int[] test2 = new int[]{3,2,1,0,4};
        testSuite.add(test2);

        for (int[] list : testSuite) {
            for (int i : list) System.out.print(i + " ");
            System.out.println(canJump(list));
        }
    }
}