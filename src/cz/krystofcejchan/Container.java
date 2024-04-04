package cz.krystofcejchan;

import java.util.ArrayList;

/**
 * Třída kontejneru s vlastnostmi ukládající celkovou hodnotu kontejneru a jeho lokace
 */
non-sealed class Container extends LocationComparable {
    private final int value;
    private final ArrayList<Location> location;

    /**
     * Konstruktor
     *
     * @param value    int hodnota kontejneru
     * @param location {@link Location}, kde se nachází kontejner, tj. x y souřadnice
     */
    Container(int value, ArrayList<Location> location) {
        this.value = value;
        this.location = location;
    }

    /**
     * Volání binárního vyhledávání
     *
     * @param location {@link Location} target
     * @return true - pokud tento kontejner se "rozpíná" přes {@link Location} z parametru
     */
    boolean containsWithBinarySearch(Location location) {
        return binarySearch(location, 0, this.location.size() - 1, this.location);
    }

    /**
     * Getter funkce
     *
     * @return hodnota kontejneru
     */
    public int getValue() {
        return value;
    }

    @Override
    public int x() {
        return location.get(0).x();
    }

    @Override
    public int y() {
        return location.get(0).y();
    }

    /**
     * Rekurzivní binární vyhledávání v {@link ArrayList}
     *
     * @param target   cílový element
     * @param left     index prvního elementu
     * @param right    index posledního elementu
     * @param elements vstupní seznam
     * @param <T>      generické T musí implementovat {@link LocationComparable}, tj. musí být {@link Container} či {@link Location}
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
                "location=" + location + ']';
    }

}
