package lwjgladapter.maths.shapes;

import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.maths.vectors.Vector2Float;

public class RectFloat extends Rect<Float> {
    public RectFloat(Float x, Float y, Float width, Float height) {
        super(x, y, width, height);
    }

    @Override
    public Vector2<Float> getPosition() {
        return new Vector2Float(x, y);
    }

    @Override
    public Vector2<Float> getSize() {
        return new Vector2Float(width, height);
    }

    @Override
    public Float getX() {
        return x;
    }

    @Override
    public Float getY() {
        return y;
    }

    @Override
    public Float getWidth() {
        return width;
    }

    @Override
    public Float getHeight() {
        return height;
    }

    @Override
    public Float getLeft() {
        return left;
    }

    @Override
    public Float getRight() {
        return right;
    }

    @Override
    public Float getTop() {
        return top;
    }

    @Override
    public Float getBottom() {
        return bottom;
    }
}
