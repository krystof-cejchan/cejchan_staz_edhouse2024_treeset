package cz.krystofcejchan;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Třída Algorithm skrývá většinu logiky za celým programem
 */
public class Algorithm {
    final private TreeSet<? super LocationComparable> containers;
    final private LinkedList<Coordinate> specialSymbols;
    private int containerValueSum;

    /**
     * Kontruktor
     *
     * @param containers     TreeSet kontejnerů
     * @param specialSymbols LinkedList umístění speciálních symbolů
     */
    public Algorithm(TreeSet<? super LocationComparable> containers, LinkedList<Coordinate> specialSymbols) {
        this.containers = containers;
        this.specialSymbols = specialSymbols;
        sumUpContainers();
    }

    /**
     * Metoda je volána v kontruktoru třídy.
     * Pro každý speciální symbol spustí metodu handleOffsetElements
     */
    private void sumUpContainers() {
        specialSymbols.forEach(this::handleOffsetElements);
    }

    /**
     * Tato metoda zjistí, zda se v okolí speciálního symbolu nachází kontejner; pokud ano, tak jeho hodnotu přičte do containerValueSum
     *
     * @param specialSymbol {@link Coordinate} umístění speciálního symbolu
     */
    private void handleOffsetElements(Coordinate specialSymbol) {
        for (Coordinate coordinate : generateOffset(specialSymbol)) {
            if (containers.isEmpty()) return;
            //v TreeSetu se najde Container pomocí coordinate – containers ukládá <? super LocationComparable>, proto musí být přetypován
            var containerFromTreeSet = (Container) containers.floor(coordinate);
            //pokud se v TreeSetu našel kontejner a tento kontejner se nachází na lokacích, které obsahují corrdinate,
            //tak se přičte hodnota kontejneru a odstraní se z TreeSetu, neboť kontejner může sousedit s více speciálními znaky
            if (containerFromTreeSet != null && containerFromTreeSet.containsWithBinarySearch(coordinate)) {
                containerValueSum += containerFromTreeSet.getValue();
                containers.remove(containerFromTreeSet);
            }
        }
    }

    /**
     * funkce generuje {@link Coordinate}[], které sousedí s {@link Coordinate} zadaným v parametru funkce
     *
     * @param coordinate {@link Coordinate} umístění speciálního symbolu
     * @return Coordinate[] sousedící s parametrem funkce
     */
    private Coordinate[] generateOffset(Coordinate coordinate) {
        return Stream.of(new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}})
                .map(offset -> new Coordinate(coordinate.x() + offset[0], coordinate.y() + offset[1]))
                .toArray(Coordinate[]::new);
    }

    /**
     * Getter vracící součet hodnot aktivních kontejnerů
     *
     * @return součet hodnot aktivních kontejnerů
     */
    public int getContainerValueSum() {
        return containerValueSum;
    }
}