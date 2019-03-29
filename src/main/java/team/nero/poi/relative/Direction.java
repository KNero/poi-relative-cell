package team.nero.poi.relative;

public enum Direction {
    TOP(-1, 0),
    BOTTOM(1, 0),
    RIGHT(0, 1),
    RIGHT_TOP(-1, 1),
    RIGHT_BOTTOM(1, 1),
    LEFT (0, -1),
    LEFT_TOP(-1, -1),
    LEFT_BOTTOM(1, -1);

    private int row;
    private int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
