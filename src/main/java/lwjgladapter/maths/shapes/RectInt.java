package lwjgladapter.maths.shapes;

import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.maths.vectors.Vector2Int;
import lwjgladapter.maths.vectors.Vector2IntGridIterator;

public class RectInt extends Rect<Integer> {
    public RectInt(Integer x, Integer y, Integer width, Integer height) {
        super(x, y, width, height);
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Rect Size has to be greater than 1");
        }
    }

    @Override
    public Vector2<Integer> getPosition() {
        return new Vector2Int(getX(), getY());
    }

    @Override
    public Vector2<Integer> getSize() {
        return new Vector2Int(getWidth(), getHeight());
    }

    @Override
    public Integer getX() {
        return Math.round(x);
    }

    @Override
    public Integer getY() {
        return Math.round(y);
    }

    @Override
    public Integer getWidth() {
        return Math.round(width);
    }

    @Override
    public Integer getHeight() {
        return Math.round(height);
    }

    @Override
    public Integer getLeft() {
        return Math.round(left);
    }

    @Override
    public Integer getRight() {
        return Math.round(right);
    }

    @Override
    public Integer getTop() {
        return Math.round(top);
    }

    @Override
    public Integer getBottom() {
        return Math.round(bottom);
    }

    public Vector2IntGridIterator getContainedPoints() {
        return new Vector2IntGridIterator(getLeft(), getRight(), getBottom(), getTop());
    }
}
