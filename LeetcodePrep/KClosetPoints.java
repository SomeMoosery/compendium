package LeetcodePrep;

import java.util.*;

class KClosestPoints {
    public static int[][] kClosest(int[][] points, int K) {
        List<Double> list = new ArrayList<>();
        Map<Double, List<int[]>> map = new HashMap<>();
        int[][] result = new int[K][2];
        
        for (int[] point : points) {
            double tempDist = Math.sqrt((point[0] * point[0]) + (point[1] * point[1]));
            list.add(tempDist);
            if (!map.containsKey(tempDist)) {
                map.put(tempDist, new ArrayList<>());
            }
            map.get(tempDist).add(point);
        }
        
        Collections.sort(list);
        
        int count = 0, listCount = 0;
        while(count < K) {
            List<int[]> tempList = map.get(list.get(listCount));
            for (int[] point : tempList) {
                result[count] = point;
                count++;
            }
            listCount++;
        }
        
        return result;
    }

    public static void main(String[] args) {
        int[][] test1 = new int[][]{{3,3}, {5,-1}, {-2,4}};
        int[][] test2 = new int[][]{{1,0}, {0,1}};
        int dist = 2;

        System.out.println(kClosest(test1, dist));
        System.out.println(kClosest(test2, dist));
    }
}