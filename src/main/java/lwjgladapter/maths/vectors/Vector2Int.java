package lwjgladapter.maths.vectors;

import java.util.Objects;

public class Vector2Int extends Vector2<Integer> {

    public Vector2Int(int x, int y) {
        super(x, y);
    }

    @Override
    public Integer getX() {
        return Math.round(x);
    }

    @Override
    public Integer getY() {
        return Math.round(y);
    }

    public static Vector2Int zero() {
        return new Vector2Int(0, 0);
    }

    public static Vector2Int up() {
        return new Vector2Int(0, 1);
    }

    public static Vector2Int right() {
        return new Vector2Int(1, 0);
    }

    public static Vector2Int down() {
        return new Vector2Int(0, -1);
    }

    public static Vector2Int left() {
        return new Vector2Int(-1, 0);
    }

    public static Vector2Int one() {
        return new Vector2Int(1, 1);
    }

    public static Vector2Int normalize(Vector2<Integer> original) {
        return Vector2Int.of(Vector2Int.of(original).normalize());
    }

    public static Vector2Int negate(Vector2<Integer> original) {
        return new Vector2Int(-original.getX(), -original.getY());
    }

    public static Vector2Int add(Vector2<Integer> a, Vector2<Integer> b) {
        return new Vector2Int(a.getX() + b.getX(), a.getY() + b.getY());
    }

    public static Vector2Int subtract(Vector2<Integer> a, Vector2<Integer> b) {
        return new Vector2Int(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Vector2Int multiply(Vector2<Integer> vector, double value) {
        return new Vector2Int((int) Math.round(value * vector.getX()), (int) Math.round(value * vector.getY()));
    }

    public static <V extends Vector2> Vector2Int of(V original) {
        return new Vector2Int(original.getX().intValue(), original.getY().intValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2Int vector2Int = (Vector2Int) o;
        return x == vector2Int.getX() && y == vector2Int.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("[%d|%d]", getX(), getY());
    }


}

