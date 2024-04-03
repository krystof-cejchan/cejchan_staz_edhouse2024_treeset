package cz.krystofcejchan;

import java.util.ArrayList;


public record Container(int value, ArrayList<Coordinate> coordinates) implements ContainerComparable {

    @Override
    public int x() {
        return coordinates.get(0).x();
    }

    @Override
    public int y() {
        return coordinates.get(0).y();
    }

    public boolean containsWithBinarySearch(Coordinate coordinate) {
        return binarySearch(coordinate, 0, coordinates.size() - 1, coordinates);
    }

    @Override
    public int compareTo(ContainerComparable o) {
        int yComparison = this.y() - o.y();
        if (yComparison != 0) {
            return yComparison;
        }
        return this.x() - o.x();
    }

    private <T extends ContainerComparable> boolean binarySearch(T target, int left, int right, ArrayList<T> elements) {
        if (left <= right) {
            int mid = left + (right - left) / 2;

            if (elements.get(mid).compareTo(target) == 0)
                return true;

            if (elements.get(mid).compareTo(target) < 0)
                return binarySearch(target, mid + 1, right, elements);
            else
                return binarySearch(target, left, mid - 1, elements);
        }
        return false;
    }
}
