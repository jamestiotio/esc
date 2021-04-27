import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
        for (Student student : students) {
            student.start();
        }

        try {
            examiner.join();
            for (Student student : students)
                student.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        CyclicBarrier barrier = new CyclicBarrier(numberofstudents + 1);
        Quiz4Examiner quiz4examiner = new Quiz4Examiner(barrier);
        for (int i = 0; i < numberofstudents; i++) {
            new Quiz4Student(barrier).start();
        }
        quiz4examiner.start();
        */
    }
}


class Examiner extends Thread {
    private Phaser phaser;

    public Examiner(final Phaser phaser) {
        this.phaser = phaser;
        this.phaser.register();
    }

    public void run() {
        System.out.println("examiner waiting for students to get ready;");
        this.phaser.arriveAndAwaitAdvance(); // Waiting until all students are ready
        System.out.println("examiner waiting for students to hand in;");
        this.phaser.arriveAndAwaitAdvance(); // Waiting until everyone has submitted their scripts
        System.out.println("all students have handed in, exam has ended");
    }
}


class Student extends Thread {
    private Phaser phaser;

    public Student(final Phaser phaser) {
        this.phaser = phaser;
        this.phaser.register();
    }

    public void run() {
        Random r = new Random();
        System.out.println("student " + Thread.currentThread().getId() + " ready;");
        this.phaser.arriveAndAwaitAdvance(); // Waiting for exam to start.
        System.out.println("student " + Thread.currentThread().getId() + " taking exam;");
        try {
            Thread.sleep((long) ((r.nextDouble() + 1) * 1000));// Taking the exam.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("student " + Thread.currentThread().getId() + " handing in exam;");
        this.phaser.arriveAndDeregister(); // Ended the exam.
        System.out.println("student " + Thread.currentThread().getId() + " leaves");
    }
}

class Quiz4Examiner extends Thread {
    private CyclicBarrier barrier;
    public Quiz4Examiner(final CyclicBarrier barrier) {
        this.barrier = barrier;

    }

    public void run() {
        System.out.println("examiner waiting for students to get ready;");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("examiner waiting for students to hand in;");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("all students have handed in, exam has ended");
    }
}

class Quiz4Student extends Thread {
    private CyclicBarrier barrier;
    public Quiz4Student(final CyclicBarrier barrier) {
        this.barrier = barrier;

    }

    public void run() {
        Random r = new Random();
        System.out.println("student " + Thread.currentThread().getId() + " ready;");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("student " + Thread.currentThread().getId() + " taking exam;");
        try {
            Thread.sleep((long) ((r.nextDouble() + 1) * 1000));//taking the exam.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("student " + Thread.currentThread().getId() + " handing in exam;");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("student " + Thread.currentThread().getId() + " leaves");
    }
}
