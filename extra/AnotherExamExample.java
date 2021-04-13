import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class AnotherExamExample {
    private final static int numberOfStudents = 100;

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser();
        Examiner examiner = new Examiner(phaser, numberOfStudents);
        Student[] students = new Student[numberOfStudents];
        for (int i = 0; i < numberOfStudents; i++) {
            students[i] = new Student(phaser, i + 1); // register students to phaser first before
                                                      // running
        }
        examiner.start();
        Thread.sleep(100); // to force examiners to be first
        for (int i = 0; i < numberOfStudents; i++) {
            students[i].start();
        }
    }
}


class Student extends Thread {
    private int id;
    private Phaser phaser;

    Student(final Phaser phaser, int id) {
        this.phaser = phaser;
        this.id = id;
        phaser.register();
    }

    public void run() {
        System.out.println("Student " + id + " is ready to begin");
        // wait for exam to start
        phaser.arriveAndAwaitAdvance();
        System.out.println("Student " + id + " is taking the exam!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("oops not supposed to happen");
        }
        System.out.println("Student " + id + " handed in his script and left");
        phaser.arrive(); // ended his exam
    }
}


class Examiner extends Thread {
    private Phaser phaser;
    private int numberOfStudents;

    Examiner(final Phaser phaser, int numberOfStudents) {
        this.phaser = phaser;
        this.numberOfStudents = numberOfStudents;
        phaser.register();
    }

    public void run() {
        System.out.println(
                "Examiner is waiting patiently for " + numberOfStudents + " students to get ready");
        phaser.arriveAndAwaitAdvance(); // wait for exam to start
        phaser.arriveAndAwaitAdvance(); // wait for everyone to hand in their scripts
        System.out.println("Exam has ended, Examiner leaves with scripts");
    }
}
