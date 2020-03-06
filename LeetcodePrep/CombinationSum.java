package LeetcodePrep;

import java.util.*;

class CombinationSum {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        if(candidates == null || candidates.length == 0){
            return result;
        }
        
        List<Integer> sum = new ArrayList<>();
        Arrays.sort(candidates);
        
        dfs(sum, result, candidates, target, 0);
        return result;
    }
    
    private static void dfs(List<Integer> sum, List<List<Integer>> result, int[] candidates, int target, int index){
        
        if(target == 0){
            result.add(new ArrayList<>(sum));
            return;
        }
        
        for(int i = index; i < candidates.length; i ++){
            if(candidates[i] > target){
                break;
            }
            
            sum.add(candidates[i]);
            
            dfs(sum, result, candidates, target-candidates[i], i);
            
            sum.remove(sum.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] test = new int[]{2,3,5};
        int target = 8;

        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Target: " + target);
        
        List<List<Integer>> result = combinationSum(test, target);
        for (List<Integer> list : result) {
            for (int i : list) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}