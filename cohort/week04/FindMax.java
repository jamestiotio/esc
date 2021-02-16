// Take note that this implementation of FindMax is purposefully buggy (it does not traverse all the way until/to the end of the array)
public class FindMax {
    public static void main(String[] arg) throws Exception {
        System.out.println(max(new int[]{5,6,17,8,2}));
    }

    public static int max(int[] list) {
        int max = list[0];
        // Should be i < list.length in actual real-life implementation
        for (int i = 1; i < list.length - 1; i++) {
            if (max < list[i]) {
                max = list[i];
            }
        }

        return max;
    }
}