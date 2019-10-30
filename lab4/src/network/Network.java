package network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Network {
    public final static int[] lessOrEqual = {
            0, 0, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 0,
            0, 0, 1, 0, 0, 0,
            0, 1, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };
    public final static int[] moreOrEqual = {
            0, 1, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 0,
            0, 0, 0, 1, 0, 0,
            0, 0, 1, 0, 0, 1,
            0, 1, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };
    public final static int[] notEqual = {
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 1, 0, 0,
            0, 1, 1, 1, 1, 0,
            0, 0, 1, 0, 0, 0
    };
    public final static int[] equal = {
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0
    };
    public final static int[] approximatelyEqual = {
            0, 1, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0,
            0, 1, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0
    };

    public final static int[] YlessOrEqual = {
            1, 0, 0, 0, 0
    };
    public final static int[] YmoreOrEqual = {
            0, 1, 0, 0, 0
    };
    public final static int[] YnotEqual = {
            0, 0, 1, 0, 0
    };
    public final static int[] Yequal = {
            0, 0, 0, 1, 0
    };
    public final static int[] YapproximatelyEqual = {
            0, 0, 0, 0, 1
    };

    public final static int SIZE = 6;
    public final static int IN_SIZE = 36;
    public final static int HIDDEN_SIZE = 10;
    public final static int OUT_SIZE = 5;

    private final static double ALPHA = 0.5;
    private final static double BETTA = 0.5;
    private final static double D = 0.1;

    private double maxDk = 0;

    private ArrayList<Double> hiddenW;
    private ArrayList<Double> outW;

    private ArrayList<Double> thresholdQ;
    private ArrayList<Double> thresholdT;

    private ArrayList<Double> neuronG;
    private ArrayList<Double> neuronY;

    public Network() {
        hiddenW = new ArrayList<>(IN_SIZE * HIDDEN_SIZE);
        outW = new ArrayList<>(HIDDEN_SIZE * OUT_SIZE);
        thresholdQ = new ArrayList<>(HIDDEN_SIZE);
        thresholdT = new ArrayList<>(OUT_SIZE);
        neuronG = new ArrayList<>(HIDDEN_SIZE);
        neuronY = new ArrayList<>(OUT_SIZE);
        init();
    }

    private void init() {
        for (int i = 0; i < IN_SIZE * HIDDEN_SIZE; i++) {
            hiddenW.add(getRandomNumber());
        }
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            thresholdQ.add(getRandomNumber());
            neuronG.add((double) 0);
        }
        for (int i = 0; i < HIDDEN_SIZE * OUT_SIZE; i++) {
            outW.add(getRandomNumber());
        }
        for (int i = 0; i < OUT_SIZE; i++) {
            thresholdT.add(getRandomNumber());
            neuronY.add((double) 0);
        }
    }

    private double getRandomNumber() {
        return (Math.random() * 2) - 1;
    }

    public void showMatrix(int[] m) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (m[i * SIZE + j] == 0) System.out.print("-");
                else System.out.print("*");//m[i * SIZE + j]);// + ", ");
            }
            System.out.print("\n");
        }
    }

    private double activationFunction(double x) {
        int f = (int) (1 / (1 + Math.pow(Math.E, x * (-1))));
        return 1 / (1 + Math.pow(Math.E, x * (-1)));
    }

    public void teachingPattern(int[] X, int[] Y) {
        do {
            startHiddenW(X);
            startOutW();
            maxDk = 0;
            correctingOut(Y);
            correctingHidden(X, Y);
        } while (mistakeIsBig());
    }

    public void teaching() {
        for (int i = 0; i < 20; i++) {
            teachingPattern(lessOrEqual, YlessOrEqual);
            teachingPattern(moreOrEqual, YmoreOrEqual);
            teachingPattern(notEqual, YnotEqual);
            teachingPattern(equal, Yequal);
            teachingPattern(approximatelyEqual, YapproximatelyEqual);
        }
    }

    public void recognition(int[] testSelection) {
        startHiddenW(testSelection);
        startOutW();
        System.out.println(neuronY);
    }

    private boolean mistakeIsBig() {
        if (Math.abs(maxDk) > D) return true; //ошибка больше чем заданная, точность не достигнута
        return false;
    }

    private void updateDk(double dk) {
        if (Math.abs(dk) > maxDk) maxDk = Math.abs(dk);
    }

    private void correctingOut(int[] Yr) {
        for (int k = 0; k < OUT_SIZE; k++) {
            updateDk(Yr[k] - neuronY.get(k)); //запоминаем максимальную ошибку

            for (int j = 0; j < HIDDEN_SIZE; j++) {

                outW.set(j * OUT_SIZE + k,
                        outW.get(j * OUT_SIZE + k)
                                + ALPHA * neuronY.get(k)
                                * (1 - neuronY.get(k))
                                * (Yr[k] - neuronY.get(k)) //dk
                                * neuronG.get(j));
            }
            thresholdT.set(k,
                    thresholdT.get(k)
                            + ALPHA * neuronY.get(k)
                            * (1 - neuronY.get(k))
                            * (Yr[k] - neuronY.get(k)));    //dk
        }
    }

    private void correctingHidden(int[] X, int[] Yr) {
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                hiddenW.set(i * HIDDEN_SIZE + j,
                        hiddenW.get(i * HIDDEN_SIZE + j)
                                + BETTA * neuronG.get(j)
                                * (1 - neuronG.get(j))
                                * getE(j, Yr) * X[i]);
            }
            thresholdQ.set(j,
                    thresholdQ.get(j)
                            + BETTA * neuronG.get(j)
                            * (1 - neuronG.get(j))
                            * getE(j, Yr));
        }
    }

    private double getE(int j, int[] Yr) {
        double res = 0;
        for (int k = 0; k < OUT_SIZE; k++) {
            res += (Yr[k] - neuronY.get(k)) //dk
                    * neuronY.get(k)
                    * (1 - neuronY.get(k))
                    * outW.get(j * OUT_SIZE + k);
        }
        return res;
    }

    private void startHiddenW(int[] selection) {
        double tmp = 0;
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            for (int i = 0; i < IN_SIZE; i++) {
                tmp += hiddenW.get(i * HIDDEN_SIZE + j) * selection[i];
            }
            neuronG.set(j, activationFunction(tmp + thresholdQ.get(j)));
            tmp = 0;
        }
    }

    private void startOutW() {
        double tmp = 0;
        for (int k = 0; k < OUT_SIZE; k++) {
            //tmp = outW.get(i);
            for (int j = 0; j < HIDDEN_SIZE; j++) {
                tmp += outW.get(j * OUT_SIZE + k) * neuronG.get(j);
            }
            neuronY.set(k, activationFunction(tmp + thresholdT.get(k)));
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
