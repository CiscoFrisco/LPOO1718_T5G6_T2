package com.ciscominas.airhockeymania.model.entities;

public class LineModel extends EntityModel {

    public LineModel(float x, float y) {
        super(x, y);
    }

    @Override
    public ModelType getType() {
        return ModelType.LINE;
    }
}
