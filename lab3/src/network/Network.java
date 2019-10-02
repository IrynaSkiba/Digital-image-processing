package network;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Network {
    public final static int SIZE = 10;
    public final static int[] LETTER_D = {
            0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1};

    public final static int[] LETTER_N = {
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1};

    public final static int[] LETTER_H = {
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1,
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1,
            0, 1, 1, 1, 0, 0, 1, 1, 1, 0,
            0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
            0, 0, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 1, 1, 1, 0, 0, 1, 1, 1, 0,
            1, 1, 1, 0, 0, 0, 0, 1, 1, 1,
            1, 1, 0, 0, 0, 0, 0, 0, 1, 1};

    private int[] bipolarD;
    private int[] bipolarN;
    private int[] bipolarH;
    private int[][] w;

    public Network() {
        toBipolarMatrix();
        this.w = new int[SIZE * SIZE][SIZE * SIZE];
    }

    public int[] getBipolarD() {
        return bipolarD;
    }

    public int[] getBipolarN() {
        return bipolarN;
    }

    public int[] getBipolarH() {
        return bipolarH;
    }

    public int[] changeSignToMinus(int[] source) {
        int[] result = new int[SIZE * SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (source[i * SIZE + j] == 0) {
                    result[i * SIZE + j] = -1;
                } else {
                    result[i * SIZE + j] = 1;
                }
            }
        }
        return result;
    }

    public int[] changeToZero(int[] source) {
        int[] result = new int[SIZE * SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (source[i * SIZE + j] == -1) {
                    result[i * SIZE + j] = 0;
                } else {
                    result[i * SIZE + j] = 1;
                }
            }
        }
        return result;
    }

    public void showMatrix(int[] m) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (m[i * SIZE + j] == 0) System.out.print("-");
                else System.out.print(m[i * SIZE + j]);// + ", ");
            }
            System.out.print("\n");
        }
    }

    private void toBipolarMatrix() {
        bipolarD = changeSignToMinus(LETTER_D);
        bipolarN = changeSignToMinus(LETTER_N);
        bipolarH = changeSignToMinus(LETTER_H);
    }

    public void teaching() {

        createW(bipolarD);
        createW(bipolarN);
        createW(bipolarH);
    }

    private void createW(int[] pattern) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            for (int j = 0; j < SIZE * SIZE; j++) {
                if (i == j) {
                    w[i][j] = 0;
                } else {
                    w[i][j] += pattern[i] * pattern[j];
                }
            }
        }
    }

    public int[] recognition(int[] pattern) {
        int[] prevState = new int[SIZE * SIZE];
        int[] result = new int[SIZE * SIZE];
        //int[] temp = new int[SIZE * SIZE];
        int cur, j = 0;
        System.arraycopy(pattern, 0, result, 0, SIZE * SIZE);
        do {
            System.arraycopy(result, 0, prevState, 0, SIZE * SIZE);
            // randomNum = ThreadLocalRandom.current().nextInt(0, SIZE * SIZE + 1);
            for (int k = 0; k < SIZE * SIZE; k++) {
                cur = 0;
                for (int i = 0; i < SIZE * SIZE; i++) {
                    cur += result[i] * w[i][k];
                }
                result[k] = activationFunction(cur);
            }
            j++;
            if (j > 500) {
                System.err.println("Threshold overcome.");
                return result;
            }
            //System.arraycopy(temp, 0, result, 0, SIZE * SIZE);
        } while (!Arrays.equals(prevState,result));//compare(prevState, result));
        return result;
    }

    private int activationFunction(int x) {
        if (x > 0) return 1;
        return -1;
    }

    private boolean compare(int[] mas1, int[] mas2) {
        for (int i = 0; i < SIZE * SIZE; i++) {
            if (mas1[i] != mas2[i]) return false;
        }
        return true;
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
            if (result[random] == 1) result[random] = -1;
            else result[random] = 1;
            percent--;
        } while (percent != 0);
        return result;
    }
}