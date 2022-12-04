package Reversy;

class Matrix {  // поле, которое будем обновлять при ходе игрока
    private final Box[][] matrix;

    Matrix(Box defaultBox) {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Location location : Ranges.getAllLocations()) {
            matrix[location.x][location.y] = defaultBox;
        }
    }

    Box get (Location location) {
        if (Ranges.inRange(location))
            return  matrix[location.x][location.y];
        return null;
    }

    void set (Location location, Box box) {
        if (Ranges.inRange(location))
            matrix[location.x][location.y] = box;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Matrix mat = null;
        try {
            mat = (Matrix) super.clone();
        } catch (CloneNotSupportedException e) {
            mat = new Matrix(Box.sqrt1);
        }

        for (Location location : Ranges.getAllLocations()) {
            mat.set(location, this.get(location));
        }

        return mat;
    }
}
