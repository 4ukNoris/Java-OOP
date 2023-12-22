package harpoonDiver.common;

public class NullOrNegative {
    public NullOrNegative() {

    }

    public static boolean NullOrEmpty(String word) {
        return word == null || word.trim().isEmpty();
    }
    public static boolean isNegative(double number) {
        return number < 0;
    }
}
