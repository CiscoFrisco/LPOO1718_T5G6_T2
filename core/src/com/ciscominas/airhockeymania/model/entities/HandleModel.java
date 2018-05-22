package com.ciscominas.airhockeymania.model.entities;

public class HandleModel extends EntityModel {

    public HandleModel(float x, float y) {
        super(x, y);
    }

    @Override
    public ModelType getType() {
        return ModelType.HANDLE;
    }
}
