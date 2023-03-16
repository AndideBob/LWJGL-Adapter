package lwjgladapter.maths.shapes;

import lwjgladapter.maths.vectors.Vector2;
import lwjgladapter.maths.vectors.Vector2GridIterator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RectTest {

    @Test
    void create_shouldSetValuesProperly() {
        Rect rect = new Rect(1, 2, 2, 2);
        assertEquals(1, rect.left);
        assertEquals(3, rect.right);
        assertEquals(2, rect.bottom);
        assertEquals(4, rect.top);
    }

    @Test
    void create_shouldThrowIfWidthNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Rect(0, 0, -5, 5));
    }

    @Test
    void create_shouldThrowIfHeightNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Rect(0, 0, 5, -5));
    }

    @Test
    void getContainedPoints_shouldReturnAllExpectedPoints() {
        Set<Vector2> expectedPoints = Set.of(
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(2, 1),
                new Vector2(0, 2),
                new Vector2(1, 2),
                new Vector2(2, 2)
        );
        Rect rect = new Rect(0, 0, 2, 2);
        int points = 0;
        for (Vector2GridIterator it = rect.getContainedPoints(); it.hasNext(); ) {
            assertTrue(expectedPoints.contains(it.next()));
            points++;
        }
        assertEquals(expectedPoints.size(), points);
    }

    @Test
    void contains_shouldReturnTrueIfContains() {
        Rect rect = new Rect(0, 0, 5, 5);
        for (Vector2GridIterator it = rect.getContainedPoints(); it.hasNext(); ) {
            assertTrue(rect.contains(it.next()));
        }
    }

    @Test
    void contains_shouldReturnFalseIfDoesntContain() {
        Rect rectA = new Rect(0, 0, 4, 4);
        Rect rectB = new Rect(5, 5, 4, 4);
        assertFalse(rectA.intersects(rectB));
        for (Vector2GridIterator it = rectA.getContainedPoints(); it.hasNext(); ) {
            assertFalse(rectB.contains(it.next()));
        }
    }

    @Test
    void intersects_shouldReturnTrueIfIntersect() {
        Rect rectA = new Rect(0, 0, 5, 5);
        Rect rectB = new Rect(2, 2, 5, 5);
        assertTrue(rectA.intersects(rectB));
        assertTrue(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnTrueIfIntersectOnEdge() {
        Rect rectA = new Rect(0, 0, 5, 5);
        Rect rectB = new Rect(5, 5, 5, 5);
        assertTrue(rectA.intersects(rectB));
        assertTrue(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersect() {
        Rect rectA = new Rect(0, 0, 5, 5);
        Rect rectB = new Rect(6, 6, 5, 5);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersectOnYAxis() {
        Rect rectA = new Rect(0, 0, 5, 5);
        Rect rectB = new Rect(2, 6, 2, 5);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }

    @Test
    void intersects_shouldReturnFalseIfDoesntIntersectOnXAxis() {
        Rect rectA = new Rect(0, 0, 5, 5);
        Rect rectB = new Rect(6, 2, 5, 2);
        assertFalse(rectA.intersects(rectB));
        assertFalse(rectB.intersects(rectA));
    }
}