package com.ciscominas.airhockeymania.model.entities;

public class LineModel extends EntityModel {

    private static int pos;

    public LineModel(float x, float y, float width, float height,  int pos) {
        super(x, y, width, height);
        this.pos = pos;
    }

    @Override
    public ModelType getType() {
        return ModelType.LINE;
    }

    public int getPos() {
        return pos;
    }

    public void multWidth(float ratio) {
        this.width*=ratio;
    }
}
