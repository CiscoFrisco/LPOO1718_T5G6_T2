package com.ciscominas.airhockeymania.view.entities;

import com.ciscominas.airhockeymania.AirHockeyMania;
import com.ciscominas.airhockeymania.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.BOT;
import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.HANDLE;
import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.LINE;
import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.POWERUP;
import static com.ciscominas.airhockeymania.model.entities.EntityModel.ModelType.PUCK;

public class ViewFactory {
    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(AirHockeyMania game, EntityModel model) {
        if (!cache.containsKey(model.getType())) {
            if (model.getType() == HANDLE)
                cache.put(model.getType(), new HandleView(game));
            if (model.getType() == PUCK)
                cache.put(model.getType(), new PuckView(game));
            if (model.getType() == BOT)
                cache.put(model.getType(), new BotView(game));
            if (model.getType() == POWERUP)
                cache.put(model.getType(), new PowerUpView(game));
            if (model.getType() == LINE)
                cache.put(model.getType(), new LineView(game));
        }
        return cache.get(model.getType());
    }
}
