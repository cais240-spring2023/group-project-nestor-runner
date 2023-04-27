package edu.wsu.model.entities;

public class FloorPiece extends Entity {

    private int width;

    public FloorPiece() {
        super();
    }

    @Override
    public int getWidth() {
        return width;
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
