class MaximumSubarray {
    public static int maxSubArray(int[] nums) {
        int currMax = nums[0], max = nums[0];
        
        for (int i = 1; i < nums.length; i++) {            
            currMax = Math.max(nums[i], currMax+nums[i]);
            max = Math.max(currMax, max);
        }
        
        return max;
    }

    public static void main(String[] args) {
        int[] test = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(test));
    }
}