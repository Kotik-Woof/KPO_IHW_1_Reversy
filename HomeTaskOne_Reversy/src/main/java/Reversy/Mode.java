package Reversy;

public enum Mode {
    FRIEND,
    MIDDLE,
    SENIOR;

    private static Mode mode_;

    public static void setMode(Mode mode) {
        mode_ = mode;
    }

    public static Mode getMode() {
        return mode_;
    }
}
