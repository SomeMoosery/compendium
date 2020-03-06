package LeetcodePrep;

import java.util.Arrays;

// This is otherwise known as the Dutch National Flag problem!
class SortColors {
    public static void sortColors(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int i : nums){
            max = Math.max(max, i);
        }
        int[] arr = new int[max+1];
        Arrays.fill(arr, 0);
        for(int i = 0; i < nums.length; i++){
            arr[nums[i]] += 1;
        }
        int i = 0, j = 0;
        while(i < nums.length){
            if(arr[j] != 0){
                nums[i] = j;
                arr[j]--;
                i++;
            }
            else{
                j++;
            }
            
        }
    }

    public static void main(String[] args) {
        int[] test = new int[]{2,0,2,1,1,0};
        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();

        sortColors(test);
        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}