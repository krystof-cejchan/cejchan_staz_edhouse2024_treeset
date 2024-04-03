package cz.krystofcejchan;

public sealed interface ContainerComparable extends Comparable<ContainerComparable> permits Container, Coordinate {
    int x();
    int y();
}
