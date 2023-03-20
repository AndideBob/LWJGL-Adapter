package lwjgladapter.maths.vectors;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2IntGridIteratorTest {

    @Test
    void shouldIterateAllPositions() {
        Vector2Int[] expectedList = new Vector2Int[]{
                new Vector2Int(0, 0),
                new Vector2Int(1, 0),
                new Vector2Int(2, 0),
                new Vector2Int(3, 0),
                new Vector2Int(0, 1),
                new Vector2Int(1, 1),
                new Vector2Int(2, 1),
                new Vector2Int(3, 1),
                new Vector2Int(0, 2),
                new Vector2Int(1, 2),
                new Vector2Int(2, 2),
                new Vector2Int(3, 2),
                new Vector2Int(0, 3),
                new Vector2Int(1, 3),
                new Vector2Int(2, 3),
                new Vector2Int(3, 3)
        };
        List<Vector2Int> actual = new ArrayList<>();
        for (Vector2IntGridIterator it = new Vector2IntGridIterator(3, 3); it.hasNext(); ) {
            Vector2Int pos = it.next();
            System.out.println("next: " + pos);
            actual.add(pos);
        }
        assertEquals(expectedList.length, actual.size());
        for (int i = 0; i < expectedList.length; i++) {
            assertEquals(expectedList[i], actual.get(i));
        }
    }
}