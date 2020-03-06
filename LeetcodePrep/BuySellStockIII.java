package LeetcodePrep;

class BuySellStockIII {
    public static int maxProfit(int[] prices) {
        if(prices == null || prices.length < 2) return 0;
        
        int minLR = prices[0];
        int maxRL = prices[prices.length - 1];
        int[] dpLR = new int[prices.length];
        int[] dpRL = new int[prices.length];
        int max = 0;
        
        for(int i = 1; i < prices.length; i++){
            if(prices[i] < minLR) minLR = prices[i];
            dpLR[i] = Math.max(dpLR[i - 1], prices[i] - minLR);
        }

        for (int i : dpLR) {
            System.out.print(i + " ");
        }
        System.out.println();

        for(int i = prices.length - 2; i >= 0; i--){
            if(prices[i] > maxRL) maxRL = prices[i];
            dpRL[i] = Math.max(dpRL[i + 1], maxRL - prices[i]);
        }

        for (int i : dpRL) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        for(int i = 0; i < prices.length; i++){
            max = Math.max(max, dpLR[i] + dpRL[i]);
        }
        
        return max;
    }

    public static void main(String[] args) {
        int[] test = new int[]{3,3,5,0,0,3,1,4};
        for (int i : test) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Max profit: " + maxProfit(test));
    }
}