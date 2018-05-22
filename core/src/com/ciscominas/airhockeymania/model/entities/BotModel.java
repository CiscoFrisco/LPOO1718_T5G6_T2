package com.ciscominas.airhockeymania.model.entities;

public class BotModel extends EntityModel{
    private boolean defended;
    private boolean trajectoryFlag;

    public BotModel(float x, float y) {
        super(x, y);
        defended = false;
        trajectoryFlag = false;
    }

    @Override
    public ModelType getType() {
        return ModelType.BOT;
    }

    public void setDefended(boolean defended) {
        this.defended = defended;
    }

    public void setTrajectoryFlag(boolean trajectoryFlag) {
        this.trajectoryFlag = trajectoryFlag;
    }
}
