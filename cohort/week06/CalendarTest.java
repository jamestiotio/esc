import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
    private Calendar cal1;
    private Calendar cal2;

    @Before
    public void setupCalendar() {
        cal1 = new CalendarSubclass();
        cal1.setTime(new Date());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cal2 = new CalendarSubclass();
        cal2.setTime(new Date());
    }

    @Test
    public void testCalendar() {
        assertTrue(cal2.after(cal1));
        assertFalse(cal1.after(cal2));
        assertTrue(cal1.after(cal1));
        assertTrue(cal2.after(cal2));
    }
}