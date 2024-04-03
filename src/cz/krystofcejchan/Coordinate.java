package cz.krystofcejchan;

/**
 * Třída ukládající umístění.
 * Je použita pro ukládání speciálních symbolů a umístění {@link Container}
 */
non-sealed class Coordinate extends LocationComparable {
    private final int x;
    private final int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
