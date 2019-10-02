package network;

public class Main {
    public static void main(String args[]) {

        Network network = new Network();

        network.teaching();

        System.out.println("Образ буквы Д");
        network.showMatrix(Network.LETTER_D);
        System.out.println();
        int[] mas;
        for (int i = 5; i < 100; i += 10) {
            System.out.println("Зашумление равно = " + i);
            mas = network.addNoise(network.getBipolarD(), i);
            network.showMatrix(network.changeToZero(mas));
            System.out.println();
            network.showMatrix(network.changeToZero(network.recognition(mas)));
            System.out.println();
        }

        System.out.println("Образ буквы Н");
        network.showMatrix(Network.LETTER_N);
        System.out.println();
        for (int i = 10; i < 100; i += 10) {
            System.out.println("Зашумление равно = " + i);
            mas = network.addNoise(network.getBipolarN(), i);
            network.showMatrix(network.changeToZero(mas));
            System.out.println();
            network.showMatrix(network.changeToZero(network.recognition(mas)));
            System.out.println();
        }

        System.out.println("Образ буквы Х");
        network.showMatrix(Network.LETTER_H);
        System.out.println();
        for (int i = 10; i < 100; i += 10) {
            System.out.println("Зашумление равно = " + i);
            mas = network.addNoise(network.getBipolarH(), i);
            network.showMatrix(network.changeToZero(mas));
            System.out.println();
            network.showMatrix(network.changeToZero(network.recognition(mas)));
            System.out.println();
        }
    }
}
