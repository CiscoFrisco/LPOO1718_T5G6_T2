package com.ciscominas.airhockeymania.model.entities;

public class LineModel extends EntityModel {

    private String pos;

    public LineModel(float x, float y, String pos) {
        super(x, y);
        this.pos = pos;
    }

    @Override
    public ModelType getType() {
        return ModelType.LINE;
    }

    public String getPos() {
        return pos;
    }
}
