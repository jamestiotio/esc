public class GA {
    public static void main(String[] args) {
        // Set a candidate solution
        // FitnessCalc.setSolution("This is the string test input that we wanted!");
        FitnessCalc.setSolution("http://www.ietf.org/rfc/rfc1738.txt");

        // Create an initial population
        Population myPop = new Population(50, true);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: "
                    + myPop.getFittest().getFitness());
            System.out.println("Current Fittest: " + myPop.getFittest());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        System.out.println("\nSolution: " + myPop.getFittest());
    }
}
