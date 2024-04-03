package cz.krystofcejchan;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Algorithm {
    final private TreeSet<? super ContainerComparable> containers;
    final private LinkedList<Coordinate> specialSymbols;
    private int containerValueSum;

    public Algorithm(TreeSet<? super ContainerComparable> containers, LinkedList<Coordinate> specialSymbols) {
        this.containers = containers;
        this.specialSymbols = specialSymbols;
        sumUpContainers();
    }

    private void sumUpContainers() {
        specialSymbols.forEach(this::handleOffsetElements);
    }

    private void handleOffsetElements(Coordinate specialSymbol) {
        for (Coordinate coordinate : generateOffset(specialSymbol)) {
            var containerFromTreeSet = (Container) containers.floor(coordinate);
            if (containerFromTreeSet != null && containerFromTreeSet.containsWithBinarySearch(coordinate)) {
                containerValueSum += containerFromTreeSet.value();
                containers.remove(containerFromTreeSet);
            }
        }
    }

    private Coordinate[] generateOffset(Coordinate coordinate) {
        final int[][] offsets = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {-1, 1},
                {1, 1},
                {-1, -1},
                {1, -1}
        };
        return Stream.of(offsets)
                .map(offset -> new Coordinate(coordinate.x() + offset[0], coordinate.y() + offset[1]))
                .toArray(Coordinate[]::new);
    }

    public int getContainerValueSum() {
        return containerValueSum;
    }
}