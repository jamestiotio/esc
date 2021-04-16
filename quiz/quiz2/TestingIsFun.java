class TestingIsFun {
    private int x;
    private int y;

    public TestingIsFun(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void test() {
        int threshold = 1000;
        int result = 0;

        while (threshold >= 0) {
            try {
                result += this.y / (this.x + 1);
                if (this.x > 3) {
                    result *= this.y / 2;
                } else if (this.x < -1 || this.y > 5) {
                    result /= (this.y - 2);
                }
                threshold -= 1;
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
}
