package lwjgladapter.maths.shapes;

import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.maths.vectors.Vector2Int;

import java.util.Objects;

public abstract class Rect<N extends Number> {
    protected final float x;
    protected final float y;
    protected final float left;
    protected final float right;
    protected final float top;
    protected final float bottom;
    protected final float width;
    protected final float height;

    public Rect(N x, N y, N width, N height) {
        if (!(width.floatValue() > 0) || !(height.floatValue() > 0)) {
            throw new IllegalArgumentException("Rect Size has to be greater than 1");
        }
        this.x = x.floatValue();
        this.y = y.floatValue();
        this.width = width.floatValue();
        this.height = height.floatValue();
        left = x.floatValue();
        right = x.floatValue() + width.floatValue();
        bottom = y.floatValue();
        top = y.floatValue() + height.floatValue();
    }

    public abstract Vector2<N> getPosition();

    public abstract Vector2<N> getSize();

    public abstract N getX();

    public abstract N getY();

    public abstract N getWidth();

    public abstract N getHeight();

    public abstract N getLeft();

    public abstract N getRight();

    public abstract N getTop();

    public abstract N getBottom();

    public final float getArea() {
        return width * height;
    }

    public boolean contains(Vector2Int point) {
        return point.getX() >= left && point.getX() <= right
                && point.getY() >= bottom && point.getY() <= top;
    }

    public boolean intersects(Rect other) {
        return left <= other.right && right >= other.left
                && top >= other.bottom && bottom <= other.top;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect<?> rect = (Rect<?>) o;
        return Float.compare(rect.x, x) == 0 && Float.compare(rect.y, y) == 0 && Float.compare(rect.width, width) == 0 && Float.compare(rect.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}