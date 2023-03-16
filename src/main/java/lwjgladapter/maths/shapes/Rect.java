package lwjgladapter.maths.shapes;

import lombok.Getter;
import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.maths.vectors.Vector2GridIterator;

import java.util.Objects;

public class Rect {

    @Getter
    private final Vector2 position;
    @Getter
    private final Vector2 size;

    public final int left;
    public final int right;
    public final int top;
    public final int bottom;

    public Rect(int x, int y, int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Rect Size has to be greater than 1");
        }
        position = new Vector2(x, y);
        size = new Vector2(width, height);
        left = x;
        right = x + width;
        bottom = y;
        top = y + height;
    }

    public Vector2 getOuterPoint() {
        return new Vector2(right, top);
    }

    public int getArea() {
        return size.x * size.y;
    }

    public Vector2GridIterator getContainedPoints() {
        return new Vector2GridIterator(left, right, bottom, top);
    }

    public boolean contains(Vector2 point) {
        return point.x >= left && point.x <= right
                && point.y >= bottom && point.y <= top;
    }

    public boolean intersects(Rect other) {
        return left <= other.right && right >= other.left
                && top >= other.bottom && bottom <= other.top;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return position.equals(rect.position) && size.equals(rect.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, size);
    }
}