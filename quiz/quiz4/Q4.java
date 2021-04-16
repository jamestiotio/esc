import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * Quiz 4.
 */
public class Q4 {
    private final static int numberofstudents = 2;

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser();
        Examiner examiner = new Examiner(phaser);
        Student[] students = new Student[numberofstudents];
        for (int i = 0; i < numberofstudents; i++) {
            // Register students to the phaser before starting the exam
            students[i] = new Student(phaser);
        }
        examiner.start();
        Thread.sleep(1000); // Force the examiner to start first
        for (int i = 0; i < numberofstudents; i++) {
            students[i].start();
        }
    }
}


class Examiner extends Thread {
    private Phaser phaser;

    public Examiner(final Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    public void run() {
        System.out.println("examiner waiting for students to get ready;");
        phaser.arriveAndAwaitAdvance(); // Waiting until all students are ready
        System.out.println("examiner waiting for students to hand in;");
        phaser.arriveAndAwaitAdvance(); // Waiting until everyone has submitted their scripts
        System.out.println("all students have handed in, exam has ended");
    }
}


class Student extends Thread {
    private Phaser phaser;

    public Student(final Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    public void run() {
        Random r = new Random();
        System.out.println("student " + Thread.currentThread().getId() + " ready;");
        phaser.arriveAndAwaitAdvance(); // Waiting for exam to start.
        System.out.println("student " + Thread.currentThread().getId() + " taking exam;");
        try {
            Thread.sleep((long) ((r.nextDouble() + 1) * 1000));// Taking the exam.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("student " + Thread.currentThread().getId() + " handing in exam;");
        System.out.println("student " + Thread.currentThread().getId() + " leaves");
        phaser.arrive(); // Ended the exam.
    }
}
