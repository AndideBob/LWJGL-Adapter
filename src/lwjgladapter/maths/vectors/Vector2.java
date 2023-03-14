package lwjgladapter.maths.vectors;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Vector2 {
    public int x;
    public int y;

    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 UP = new Vector2(0, 1);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 DOWN = new Vector2(0, -1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 ONE = new Vector2(1, 1);

    public Vector2 negate() {
        x = -x;
        y = -y;
        return this;
    }

    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2 subtract(Vector2 other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2 multiply(double value) {
        x = (int) Math.round(value * x);
        y = (int) Math.round(value * y);
        return this;
    }

    public static Vector2 negate(Vector2 original) {
        return new Vector2(-original.x, -original.y);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 multiply(Vector2 vector, double value) {
        return new Vector2((int) Math.round(value * vector.x), (int) Math.round(value * vector.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x && y == vector2.y;
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

