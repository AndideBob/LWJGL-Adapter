package lwjgladapter.maths.shapes;

import lwjgladapter.maths.vectors.Vector2Int;
import lwjgladapter.maths.vectors.Vector2IntGridIterator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RectIntTest {

    @Test
    void create_shouldSetValuesProperly() {
        RectInt rect = new RectInt(1, 2, 2, 2);
        assertEquals(1, rect.left);
        assertEquals(3, rect.right);
        assertEquals(2, rect.bottom);
        assertEquals(4, rect.top);
    }

    @Test
    void create_shouldThrowIfWidthNegative() {
        assertThrows(IllegalArgumentException.class, () -> new RectInt(0, 0, -5, 5));
    }

    @Test
    void create_shouldThrowIfHeightNegative() {
        assertThrows(IllegalArgumentException.class, () -> new RectInt(0, 0, 5, -5));
    }

    @Test
    void getContainedPoints_shouldReturnAllExpectedPoints() {
        Set<Vector2Int> expectedPoints = Set.of(
                new Vector2Int(0, 0),
                new Vector2Int(1, 0),
                new Vector2Int(2, 0),
                new Vector2Int(0, 1),
                new Vector2Int(1, 1),
                new Vector2Int(2, 1),
                new Vector2Int(0, 2),
                new Vector2Int(1, 2),
                new Vector2Int(2, 2)
        );
        RectInt rect = new RectInt(0, 0, 2, 2);
        int points = 0;
        for (Vector2IntGridIterator it = rect.getContainedPoints(); it.hasNext(); ) {
            assertTrue(expectedPoints.contains(it.next()));
            points++;
        }
        assertEquals(expectedPoints.size(), points);
    }

    @Test
    void contains_shouldReturnTrueIfContains() {
        RectInt rect = new RectInt(0, 0, 5, 5);
        for (Vector2IntGridIterator it = rect.getContainedPoints(); it.hasNext(); ) {
            assertTrue(rect.contains(it.next()));
        }
    }

    @Test
    void contains_shouldReturnFalseIfDoesntContain() {
        RectInt rectA = new RectInt(0, 0, 4, 4);
        RectInt rectB = new RectInt(5, 5, 4, 4);
        assertFalse(rectA.intersects(rectB));
        for (Vector2IntGridIterator it = rectA.getContainedPoints(); it.hasNext(); ) {
            assertFalse(rectB.contains(it.next()));
        }
    }

    @Test
    void intersects_shouldReturnTrueIfIntersect() {
        RectInt rectA = new RectInt(0, 0, 5, 5);
        RectInt rectB = new RectInt(2, 2, 5, 5);
        assertTrue(rectA.intersects(rectB));
        assertTrue(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnTrueIfIntersectOnEdge() {
        RectInt rectA = new RectInt(0, 0, 5, 5);
        RectInt rectB = new RectInt(5, 5, 5, 5);
        assertTrue(rectA.intersects(rectB));
        assertTrue(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersect() {
        RectInt rectA = new RectInt(0, 0, 5, 5);
        RectInt rectB = new RectInt(6, 6, 5, 5);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersectOnYAxis() {
        RectInt rectA = new RectInt(0, 0, 5, 5);
        RectInt rectB = new RectInt(2, 6, 2, 5);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersectOnXAxis() {
        RectInt rectA = new RectInt(0, 0, 5, 5);
        RectInt rectB = new RectInt(6, 2, 5, 2);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }
}