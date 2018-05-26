package com.ciscominas.airhockeymania.model.entities;

public class BotModel extends EntityModel{

    public BotModel(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public ModelType getType() {
        return ModelType.BOT;
    }

}
