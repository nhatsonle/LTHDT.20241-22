package model;

public class SharedData {
    // Biến static để lưu object thuật toán hiện tại
    private static Object currentAlgorithm;

    // Getter
    public static Object getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    // Setter
    public static void setCurrentAlgorithm(Object algorithm) {
        currentAlgorithm = algorithm;
    }
}