package cz.krystofcejchan;

/**
 * Sealed abstraktní třída, která implementuje rozhraní Comparable, aby se mohla využít pro {@link java.util.TreeSet},
 * který se uspořádává podle funkce compareTo v tomto rozhraní
 * <p>
 * Třída také obsahuje dvě abstraktní funkce, které musí implementovat dědící třídy, aby mohla být využita funkce compareTo
 * <p>
 * Třída je sealed, aby ji mohli využít pouze třídy {@link Container} a {@link Location}, které také dědí od této třídy
 * <p>
 * Třída je abstraktní, aby mohla definovat compareTo funkci pro dědící třídy jenom jednou, nikoliv v každá dědící třídě zvlášť.
 * Ze stejného důvodu nebylo využito rozhraní/interface, neboť nedovoluje implementovat metodu compareTo univerzálně pouze jednou,
 * ale všechny implementující třídy by ji musely mít nadefinovanou separátně.
 */
sealed abstract class LocationComparable implements Comparable<LocationComparable> permits Container, Location {
    abstract int x();

    abstract int y();

    /**
     * Funkce implementovaná z {@link Comparable}.
     * Srovnává dva objekty podle jejich y souřadnice(řádek) a poté podle jejich x souřadnice(sloupec)
     * <p>
     * Funkce je final, aby ji nemohly definovat třídy dědící tuto abstraktní třídu - docílí se tím univerzálnost komparace mezi třídami
     *
     * @param comparable objekt dědící {@link LocationComparable} k porovnání
     * @return výsledek komparace
     */
    @Override
    public final int compareTo(LocationComparable comparable) {
        int yComparison = this.y() - comparable.y();
        if (yComparison != 0) {
            return yComparison;
        }
        return this.x() - comparable.x();
    }
}
