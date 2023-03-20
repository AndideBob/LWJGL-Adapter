package lwjgladapter.maths.vectors;

import java.util.Objects;

public class Vector2Float extends Vector2<Float> {

    public Vector2Float(float x, float y) {
        super(x, y);
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getY() {
        return y;
    }

    public static Vector2Float zero() {
        return new Vector2Float(0, 0);
    }

    public static Vector2Float up() {
        return new Vector2Float(0, 1);
    }

    public static Vector2Float right() {
        return new Vector2Float(1, 0);
    }

    public static Vector2Float down() {
        return new Vector2Float(0, -1);
    }

    public static Vector2Float left() {
        return new Vector2Float(-1, 0);
    }

    public static Vector2Float one() {
        return new Vector2Float(1, 1);
    }

    public static Vector2Float negate(Vector2<Float> original) {
        return new Vector2Float(-original.getX(), -original.getY());
    }

    public static Vector2Float add(Vector2<Float> a, Vector2<Float> b) {
        return new Vector2Float(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Vector2Float subtract(Vector2<Float> a, Vector2<Float> b) {
        return new Vector2Float(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Vector2Float multiply(Vector2<Float> vector, double value) {
        return new Vector2Float((int) Math.round(value * vector.getX()), (int) Math.round(value * vector.getY()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2Float vector2Int = (Vector2Float) o;
        return x == vector2Int.getX() && y == vector2Int.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("[%d|%d]", x, y);
    }


}

