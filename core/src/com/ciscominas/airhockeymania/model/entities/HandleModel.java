package com.ciscominas.airhockeymania.model.entities;

public class HandleModel extends EntityModel {

    public HandleModel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public ModelType getType() {
        return ModelType.HANDLE;
    }

    public void setWidth(float width) {
        this.width*= width;
    }
}
