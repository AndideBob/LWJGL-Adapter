package lwjgladapter.maths.vectors;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2GridIteratorTest {

    @Test
    void shouldIterateAllPositions() {
        Vector2[] expectedList = new Vector2[]{
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(2, 0),
                new Vector2(3, 0),
                new Vector2(0, 1),
                new Vector2(1, 1),
                new Vector2(2, 1),
                new Vector2(3, 1),
                new Vector2(0, 2),
                new Vector2(1, 2),
                new Vector2(2, 2),
                new Vector2(3, 2),
                new Vector2(0, 3),
                new Vector2(1, 3),
                new Vector2(2, 3),
                new Vector2(3, 3)
        };
        List<Vector2> actual = new ArrayList<>();
        for (Vector2GridIterator it = new Vector2GridIterator(3, 3); it.hasNext(); ) {
            Vector2 pos = it.next();
            System.out.println("next: " + pos);
            actual.add(pos);
        }
        assertEquals(expectedList.length, actual.size());
        for (int i = 0; i < expectedList.length; i++) {
            assertEquals(expectedList[i], actual.get(i));
        }
    }
}