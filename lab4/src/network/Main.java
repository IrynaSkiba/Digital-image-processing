package network;

public class Main {
    public static void main(String args[]) {
        Network network = new Network();

        network.teaching();

        int[] noiseMas;
        System.out.println("Less or equal");
        network.showMatrix(Network.lessOrEqual);
        network.recognition(Network.lessOrEqual);

        noiseMas = network.addNoise(Network.lessOrEqual, 10);
        System.out.println("\n30% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.lessOrEqual, 18);
        System.out.println("\n50% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.lessOrEqual, 28);
        System.out.println("\n80% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);


        System.out.println("\nMore or equal");
        network.showMatrix(Network.moreOrEqual);
        network.recognition(Network.moreOrEqual);

        noiseMas = network.addNoise(Network.moreOrEqual, 10);
        System.out.println("\n30% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.moreOrEqual, 18);
        System.out.println("\n50% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.moreOrEqual, 28);
        System.out.println("\n80% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);


        System.out.println("\nNot equal");
        network.showMatrix(Network.notEqual);
        network.recognition(Network.notEqual);

        noiseMas = network.addNoise(Network.notEqual, 10);
        System.out.println("\n30% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.notEqual, 18);
        System.out.println("\n50% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.notEqual, 28);
        System.out.println("\n80% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);


        System.out.println("\nEqual");
        network.showMatrix(Network.equal);
        network.recognition(Network.equal);

        noiseMas = network.addNoise(Network.equal, 10);
        System.out.println("\n30% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.equal, 18);
        System.out.println("\n50% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.equal, 28);
        System.out.println("\n80% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);


        System.out.println("\nApproximately equal");
        network.showMatrix(Network.approximatelyEqual);
        network.recognition(Network.approximatelyEqual);

        noiseMas = network.addNoise(Network.approximatelyEqual, 10);
        System.out.println("\n30% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.approximatelyEqual, 18);
        System.out.println("\n50% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);

        noiseMas = network.addNoise(Network.approximatelyEqual, 28);
        System.out.println("\n80% noise");
        network.showMatrix(noiseMas);
        network.recognition(noiseMas);
    }
}
