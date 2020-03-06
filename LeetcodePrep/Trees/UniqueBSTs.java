package LeetcodePrep.Trees;

class UniqueBSTs {
    public static int numTrees(int n) {

        if (n < 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        int arr[] = new int[n + 1];
        arr[0] = 1;
        arr[1] = 1;
        arr[2] = 2;

        for (int i = 3; i <= n; i++) {
            int j = 1;
            while (j <= (i + 1) / 2) {
                int k = arr[i - j] * arr[j - 1];
                if (i - j != j - 1)
                    k *= 2;
                arr[i] += k;
                j++;
            }
        }

        return arr[n];

    }

    public static void main(String[] args) {
        System.out.println(3 + ": " + numTrees(3));
        System.out.println(4 + ": " + numTrees(4));
        System.out.println(5 + ": " + numTrees(5));
    }
}