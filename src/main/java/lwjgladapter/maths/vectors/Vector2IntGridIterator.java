package lwjgladapter.maths.vectors;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vector2IntGridIterator implements Iterator<Vector2Int> {

    private final int minX;
    private final int maxX;
    private final int maxY;
    private int currentX;
    private int currentY;

    public Vector2IntGridIterator(int maxX, int maxY) {
        this(0, maxX, 0, maxY);
    }

    public Vector2IntGridIterator(int minX, int maxX, int minY, int maxY) {
        if (minX > maxX || minY > maxY) {
            throw new IllegalArgumentException("Max values of Vector2GridIterator must be greater than or equal to Min values!");
        }
        currentX = minX;
        currentY = minY;
        this.minX = minX;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public boolean hasNext() {
        if (currentY < maxY) {
            return true;
        }
        return currentX <= maxX;
    }

    @Override
    public Vector2Int next() {
        if (currentX > maxX) {
            currentX = minX;
            currentY++;
        }
        if (currentY > maxY) {
            throw new NoSuchElementException();
        }
        return new Vector2Int(currentX++, currentY);
    }
}
