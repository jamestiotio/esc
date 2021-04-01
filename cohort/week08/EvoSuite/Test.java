import java.util.List;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.result.TestGenerationResult;

public class Test {
    @SuppressWarnings("unchecked")
    public void evosuite(String targetClass, String cp) {
        EvoSuite evo = new EvoSuite();
        Properties.TARGET_CLASS = targetClass;

        String[] command = new String[] {"-generateSuite", "-Dalgorithm", "MONOTONIC_GA", "-class",
                targetClass, "-projectCP", cp};

        long startTime = System.nanoTime();

        List<List<TestGenerationResult>> list =
                (List<List<TestGenerationResult>>) evo.parseCommandLine(command);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;

        System.out.println("Generating tests for the " + targetClass + " class takes " + duration + " milliseconds.");

        for (List<TestGenerationResult> l : list) {
            for (TestGenerationResult r : l) {
                System.out.println(r);
            }
        }

        // Terminate JVM
        System.exit(0);
    }

    public static void main(String[] args) {
        String targetClass = "Example";
        String cp = ".";

        Test t = new Test();
        t.evosuite(targetClass, cp);
    }
}
