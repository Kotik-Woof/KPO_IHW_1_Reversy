package Reversy;

public enum Box {
    sqrt1,
    sqrt_Marked1,
    white_circle1,
    pink_circle1;

    public Object image;  // тут будем хранить картинку

    public String ToString() {
        if (this == sqrt1) {
            return "empty cell";
        } else if (this == sqrt_Marked1) {
            return "marked cell";
        } else if (this == white_circle1) {
            return "White";
        } else {
            return "Pink";
        }
    }
}