package lwjgladapter.maths.vectors;

public abstract class Vector2<N extends Number> {

    protected float x;
    protected float y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    protected Vector2(N x, N y) {
        set(x, y);
    }

    public abstract N getX();

    public abstract N getY();

    public final void setX(N value) {
        x = value.floatValue();
    }

    public final void setY(N value) {
        y = value.floatValue();
    }

    public final void set(N x, N y) {
        this.x = x.floatValue();
        this.y = y.floatValue();
    }

    public final Vector2<N> add(Vector2<N> other) {
        x += other.getX().floatValue();
        y += other.getY().floatValue();
        return this;
    }

    public final Vector2<N> subtract(Vector2<N> other) {
        x -= other.getX().floatValue();
        y -= other.getY().floatValue();
        return this;
    }

    public final Vector2<N> multiply(double value) {
        x *= value;
        y *= value;
        return this;
    }

    public final Vector2<N> negate() {
        x = -x;
        y = -y;
        return this;
    }

    public final double getLength() {
        return Math.sqrt(x * x + y * y);
    }
}
