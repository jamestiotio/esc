public class Disk {
    private int x;
    private int y;

    Disk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // (x, y) = {(4, 11), (1001, -2), (4, 13)} should have 100% statement coverage, 100% branch coverage and 100% path coverage (as well as condition coverage since condition coverage == branch coverage when each conditional statement has only one condition)
    // Condition coverage is separate from the rest
    public void manipulate() {
        int threshold = 1000;
        // Loops and recursions have potential to be non-terminating
        while ((threshold - (x + y)) > 0) {
            if (x > 5) {
                threshold = threshold - 1;
            }
            else if (y <= 12) {
                threshold = threshold - 2;
            }
            if (x <= 1000) {
                threshold = threshold - 3;
            }
            // The false condition branch of the following conditional statement is unreachable/inexecutable/infeasible (should not be considered in total branch coverage but static analysis tools will not be able to detect this and hence still consider the unreachable branch)
            else if (y < 1) {
                threshold = threshold + 1;
            }
        }
    }
}