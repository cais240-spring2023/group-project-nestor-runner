package edu.wsu.model.entities;

public class FloorPiece extends Entity {

    public static final int GROUND_Y = 400;
    private int width;

    public FloorPiece(int width) {
        super();
        setX(0);
        setY(GROUND_Y);
        this.width = width;
    }

    public FloorPiece() {
        this(0);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public boolean setWidth(int newWidth) {
        if (newWidth < 0) return false;

        width = newWidth;
        return true;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public Type type() {
        return Type.FloorPiece;
    }
}
