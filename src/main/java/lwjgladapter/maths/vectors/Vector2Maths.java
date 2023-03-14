package lwjgladapter.maths.vectors;

import java.util.Comparator;

public final class Vector2Maths {

    public static final Comparator<Vector2> X_COMPARATOR = new Vector2XComparator();
    public static final Comparator<Vector2> Y_COMPARATOR = new Vector2YComparator();

    private static final class Vector2XComparator implements Comparator<Vector2> {
        @Override
        public int compare(Vector2 o1, Vector2 o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null || o2 == null) {
                return o1 == null ? -1 : 1;
            }
            return Integer.compare(o1.x, o2.x);
        }
    }

    private static final class Vector2YComparator implements Comparator<Vector2> {
        @Override
        public int compare(Vector2 o1, Vector2 o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null || o2 == null) {
                return o1 == null ? -1 : 1;
            }
            return Integer.compare(o1.y, o2.y);
        }
    }
}
