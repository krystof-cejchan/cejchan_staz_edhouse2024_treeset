package cz.krystofcejchan;

public record Coordinate(int x, int y) implements ContainerComparable {

    @Override
    public int compareTo(ContainerComparable o) {
        int yComparison = this.y() - o.y();
        if (yComparison != 0) {
            return yComparison;
        } else {
            return this.x() - o.x();
        }
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
