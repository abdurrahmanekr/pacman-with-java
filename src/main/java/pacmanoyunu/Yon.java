package main.java.pacmanoyunu;

public enum Yon {
    EAST(0),
    WEST(180),
    NORTH(90),
    SOUTH(270);

    private final int value;
    private Yon(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isX() {
        return this == EAST || this == WEST;
    }

    public boolean isY() {
        return this == NORTH || this == SOUTH;
    }
}
