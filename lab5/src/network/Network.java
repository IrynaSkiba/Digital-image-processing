package network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Network {
    public static int[] lessOrEqual = {
            0, 0, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 0,
            0, 0, 1, 0, 0, 0,
            0, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };
    public static int[] moreOrEqual = {
            0, 1, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 0,
            0, 0, 0, 1, 0, 0,
            0, 0, 1, 0, 0, 1,
            0, 1, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };
    public static int[] notEqual = {
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 1, 0, 0,
            0, 1, 1, 1, 1, 0,
            0, 0, 1, 0, 0, 0
    };
    public static int[] equal = {
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0
    };
    public static int[] approximatelyEqual = {
            0, 1, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0,
            0, 1, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };

    private double[] lessOrEqualD = new double[36];
    private double[] moreOrEqualD = new double[36];
    private double[] notEqualD = new double[36];
    private double[] equalD = new double[36];
    private double[] approximatelyEqualD = new double[36];

    public final static int SIZE = 6;
    public final static int IN_SIZE = 36;
    public final static int OUT_SIZE = 5;

    private final static double BETTA = 0.5;

    private double MAX_DK = 0.5;

    private ArrayList<Double> weights;
    private ArrayList<Double> neuronY;
    private ArrayList<Integer> quantityOfWinner; //количество побед

    public Network() {
        weights = new ArrayList<>(IN_SIZE * OUT_SIZE);
        neuronY = new ArrayList<>(OUT_SIZE);
        quantityOfWinner = new ArrayList<>(OUT_SIZE);
        init();
//        normalize(lessOrEqual,lessOrEqualD);
//        normalize(moreOrEqual,moreOrEqualD);
//        normalize(notEqual,notEqualD);
//        normalize(equal,equalD);
//        normalize(approximatelyEqual,approximatelyEqualD);
    }

    private void init() {
        for (int i = 0; i < IN_SIZE * OUT_SIZE; i++) {
            weights.add(getRandomNumber());
        }
        for (int i = 0; i < OUT_SIZE; i++) {
            neuronY.add((double) 0);
            quantityOfWinner.add(1);
        }
    }

    private double getRandomNumber() {
        return (Math.random());
    }

    public void showMatrix(int[] m) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (m[i * SIZE + j] == 0) System.out.print("-");
                else System.out.print("*");
            }
            System.out.print("\n");
        }
    }

    public void normalize() {
        double res = 0;
        ArrayList<Double> vectorLength = new ArrayList<>();
        for (int j = 0; j < OUT_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                res += Math.pow(weights.get(i * OUT_SIZE + j), 2);
            }
            vectorLength.add(Math.sqrt(res));
            res = 0;
        }
        for (int j = 0; j < OUT_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                weights.set(i * OUT_SIZE + j, weights.get(i * OUT_SIZE + j) / vectorLength.get(j));
            }
        }
    }

    public void normalize(int[] X, double[] Y) {
        double res = 0;
        ArrayList<Double> length = new ArrayList<>();
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                res += Math.pow(X[i * SIZE + j], 2);
            }
            length.add(Math.sqrt(res));
            res = 0;
        }
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                Y[i * SIZE + j] = X[i * SIZE + j] / length.get(j);
            }
        }
    }

    private void clearF() {
        for (int i = 0; i < OUT_SIZE; i++) {
            quantityOfWinner.set(i, 1);
        }
    }

    public int teachingPattern(int[] X) {
        int maxIndex;
        passage(X);
        //System.out.println(Collections.max(getDistances(X)));
        if (Collections.max(getDistances(X)) < MAX_DK) return 1;
        maxIndex = getIndexWinner(X);
        updateWinnerQuantity(maxIndex);
        correctingWinnerWeights(X, maxIndex);
        return 0;
    }

    public void teaching() {
        int k = 100;
        while (k != 0) {
            if (teachingPattern(lessOrEqual) == 1) break;
            if (teachingPattern(moreOrEqual) == 1) break;
            if (teachingPattern(notEqual) == 1) break;
            if (teachingPattern(equal) == 1) break;
            if (teachingPattern(approximatelyEqual) == 1) break;
            k--;
        }
    }

    public void recognition(int[] testSelection) {
        passage(testSelection);
        System.out.println(neuronY);
    }

    private void correctingWinnerWeights(int[] X, int j) {
        for (int i = 0; i < IN_SIZE; i++) {
            weights.set(i * OUT_SIZE + j,
                    weights.get(i * OUT_SIZE + j)
                            + BETTA
                            * (X[i] - weights.get(i * OUT_SIZE + j)));
        }
    }

    private void updateWinnerQuantity(int index) {
        quantityOfWinner.set(index, quantityOfWinner.get(index) + 1);
    }

    private int getIndexWinner(int[] X) {
        double res = 0;
        ArrayList<Double> tmp = new ArrayList<>();
        for (int j = 0; j < OUT_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                res += Math.pow(X[i] - weights.get(i * OUT_SIZE + j), 2);
            }
            tmp.add(Math.sqrt(res) * quantityOfWinner.get(j));
            res = 0;
        }
        return tmp.indexOf(Collections.min(tmp));
    }

    public ArrayList<Double> getDistances(int[] X) {
        double res = 0;
        ArrayList<Double> distance = new ArrayList<>();

        for (int j = 0; j < OUT_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                res += Math.pow(X[i] - weights.get(i * OUT_SIZE + j), 2);
            }
            distance.add(Math.sqrt(res));
            res = 0;
        }
        return distance;
    }

    private void passage(int[] selection) {
        double tmp = 0;
        for (int j = 0; j < OUT_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                tmp += weights.get(i * OUT_SIZE + j) * selection[i];
            }
            neuronY.set(j, tmp);
            tmp = 0;
        }
    }

    public int[] addNoise(int[] pattern, int percent) {
        int[] result = new int[SIZE * SIZE];
        System.arraycopy(pattern, 0, result, 0, SIZE * SIZE);
        HashSet<Integer> set = new HashSet<>();
        int random;
        do {
            random = ThreadLocalRandom.current().nextInt(0, SIZE * SIZE);
            if (set.contains(random)) continue;
            set.add(random);
            if (result[random] == 1) result[random] = 0;
            else result[random] = 1;
            percent--;
        } while (percent != 0);
        return result;
    }
}
