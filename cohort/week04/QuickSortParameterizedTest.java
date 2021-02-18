import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(Parameterized.class)
public class QuickSortParameterizedTest {
    public int[] outputArr, inputArr;

    public QuickSortParameterizedTest(int[] outputArr, int[] inputArr) {
        this.outputArr = outputArr;
        this.inputArr = inputArr;
    }

    @Parameters public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object [][] {{new int[]{1}, new int[]{1}}, {new int[]{1, 2, 3}, new int[]{1, 2, 3}}, {new int[]{1, 2, 3}, new int[]{3, 2, 1}}, {new int[]{1, 3, 6, 10}, new int[]{10, 3, 1, 6}}, {new int[]{-2, -2, 0, 9, 9, 16}, new int[]{0, -2, -2, 9, 16, 9}}, {new int[]{-865, -863, -842, -721, -453, -314, -309, 13, 111, 274, 289, 297, 513, 661, 932}, new int[]{932, -865, 289, -309, 111, -842, -863, 661, 13, -314, 297, 274, -721, -453, 513}}, {null, null}, {null, new int[]{}}});
    }

    @Test public void quickSortTest() {
        QuickSort quickSort = new QuickSort();
        quickSort.sort(inputArr);
        assertArrayEquals(outputArr, quickSort.getArray());
    }
}