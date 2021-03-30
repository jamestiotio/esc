import java.util.Calendar;
import java.util.Date;

public class exercise4 {
    public static void main(String[] args) throws InterruptedException {
        Calendar cal1 = new CalendarSubclass();
        cal1.setTime(new Date());
        Thread.sleep(1000);
        Calendar cal2 = new CalendarSubclass();
        cal2.setTime(new Date());
        System.out.println(cal2.after(cal1));   // True
        System.out.println(cal1.after(cal2));   // False
        System.out.println(cal1.after(cal1));   // True
        System.out.println(cal2.after(cal2));   // True
    }
}

class CalendarSubclass extends Calendar {
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    Calendar calendar = Calendar.getInstance();

    @Override
    public boolean after(Object when) {
        if (when instanceof Calendar && super.compareTo((Calendar) when) == 0) {
            // if (when instanceof Calendar && ((Calendar) when).toString().equals(this.toString())) {
            // if (when instanceof Calendar && equals((Calendar) when)) {
            // System.out.println("lala");
            return true;
        }
        return calendar.after(when);
    }

    @Override
    public int compareTo(Calendar anotherCalendar) {
        return compareDays(this.getFirstDayOfWeek(), anotherCalendar.getFirstDayOfWeek());
    }

    private int compareDays(int currentFirstDayOfWeek, int anotherFirstDayOfWeek) {
        return (currentFirstDayOfWeek > anotherFirstDayOfWeek) ? 1
                : (currentFirstDayOfWeek == anotherFirstDayOfWeek) ? 0 : -1;
    }

    // Implementation of other Calendar abstract methods skipped

    @Override
    public void add(int field, int amount) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void computeFields() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void computeTime() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getGreatestMinimum(int field) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLeastMaximum(int field) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaximum(int field) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMinimum(int field) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void roll(int field, boolean up) {
        // TODO Auto-generated method stub
    }
}
