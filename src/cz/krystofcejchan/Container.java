package cz.krystofcejchan;

import java.util.ArrayList;

/**
 * Třída kontejneru s vlastnostmi ukládající celkovou hodnotu kontejneru a jeho lokaci
 */
non-sealed class Container extends LocationComparable {
    private final int value;
    private final ArrayList<Coordinate> coordinates;

    /**
     * Konstruktor
     *
     * @param value       int hodnota kontejneru
     * @param coordinates {@link Coordinate}, kde se nachází kontejner, tj. x y souřadnice
     */
    Container(int value, ArrayList<Coordinate> coordinates) {
        this.value = value;
        this.coordinates = coordinates;
    }

    /**
     * Volání binárního vyhledávání
     *
     * @param coordinate {@link Coordinate} target
     * @return true - pokud tento kontejner se "rozpíná" přes {@link Coordinate} z parametru
     */
    boolean containsWithBinarySearch(Coordinate coordinate) {
        return binarySearch(coordinate, 0, coordinates.size() - 1, coordinates);
    }

    public int getValue() {
        return value;
    }

    @Override
    public int x() {
        return coordinates.get(0).x();
    }

    @Override
    public int y() {
        return coordinates.get(0).y();
    }

    /**
     * Rekurzivní binární vyhledávání v {@link ArrayList}
     *
     * @param target   cílový element
     * @param left     index prvního elementu
     * @param right    index posledního elementu
     * @param elements vstupní seznam
     * @param <T>      generické T musí implementovat {@link LocationComparable}, tj. musí být {@link Container} či {@link Coordinate}
     * @return true, pokud seznam obsahuje cílový element; jinak false
     */
    private <T extends LocationComparable> boolean binarySearch(T target, int left, int right, ArrayList<T> elements) {
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

    @Override
    public String toString() {
        return "Container[" +
                "value=" + value + ", " +
                "coordinates=" + coordinates + ']';
    }

}
