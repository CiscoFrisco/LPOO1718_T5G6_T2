package com.ciscominas.airhockeymania.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ciscominas.airhockeymania.actors.Bot;
import com.ciscominas.airhockeymania.actors.Handle;
import com.ciscominas.airhockeymania.actors.Puck;
import com.ciscominas.airhockeymania.box2d.PuckUserData;
import com.ciscominas.airhockeymania.controller.entities.BotBody;
import com.ciscominas.airhockeymania.controller.entities.EntityBody;
import com.ciscominas.airhockeymania.controller.entities.HandleBody;
import com.ciscominas.airhockeymania.controller.entities.LineBody;
import com.ciscominas.airhockeymania.controller.entities.PowerUpBody;
import com.ciscominas.airhockeymania.controller.entities.PuckBody;
import com.ciscominas.airhockeymania.model.GameModel;
import com.ciscominas.airhockeymania.model.entities.BotModel;
import com.ciscominas.airhockeymania.model.entities.EntityModel;
import com.ciscominas.airhockeymania.model.entities.HandleModel;
import com.ciscominas.airhockeymania.model.entities.LineModel;
import com.ciscominas.airhockeymania.model.entities.PuckModel;
import com.ciscominas.airhockeymania.utils.BodyUtils;
import com.ciscominas.airhockeymania.utils.Constants;

import java.util.ArrayList;
import java.util.Date;

import static com.ciscominas.airhockeymania.utils.WorldUtils.randPowerUp;

public class GameController implements ContactListener {

    private static GameController instance;

    private final World world;

    public static final int ARENA_WIDTH = 20;

    public static final int ARENA_HEIGHT = 15;
    private final PuckBody puckBody;
    private final HandleBody handleBody;
    private float accumulator;
    private String lastTouch;
    private BotBody botBody;

    private Date begin;
    private Date end;
    private PowerUpBody powerUpBody;

    private GameController() {
        world = new World(new Vector2(0, 0), true);

        puckBody = new PuckBody(world, GameModel.getInstance().getPuck(), BodyDef.BodyType.DynamicBody);

        handleBody = new HandleBody(world, GameModel.getInstance().getHandle(), BodyDef.BodyType.DynamicBody);

        botBody = new BotBody(world, GameModel.getInstance().getBot(), BodyDef.BodyType.DynamicBody);

        createEdges();

        world.setContactListener(this);
    }

    private void createEdges() {
        ArrayList<LineModel> edges = GameModel.getInstance().getEdges();

        short mask = EntityBody.PUCK_BODY | EntityBody.HANDLE_BODY;

        //Criar linhas de golo
        float goalWidth = 15, goalHeight = 1;
        new LineBody(world, edges.get(0), BodyDef.BodyType.StaticBody, goalWidth, goalHeight, mask);
        new LineBody(world, edges.get(1), BodyDef.BodyType.StaticBody, goalWidth, goalHeight, mask);
        new LineBody(world, edges.get(2), BodyDef.BodyType.StaticBody, goalWidth, goalHeight, mask);
        new LineBody(world, edges.get(3), BodyDef.BodyType.StaticBody, goalWidth, goalHeight, mask);

        //Criar laterais
        float latWidth = 10, latHeight = 40;
        new LineBody(world, edges.get(4), BodyDef.BodyType.StaticBody, latWidth, latHeight, mask);
        new LineBody(world, edges.get(5), BodyDef.BodyType.StaticBody, latWidth, latHeight, mask);

        //Criar linha do meio
        float midWidth = 40, midHeight = 0.1f;
        new LineBody(world, edges.get(6), BodyDef.BodyType.StaticBody, midWidth, midHeight, mask);

        //Criar linhas de baliza
        float limitWidth = 40, limitHeight = 0.1f;
        new LineBody(world, edges.get(7), BodyDef.BodyType.StaticBody, limitWidth, limitHeight, mask);
        new LineBody(world, edges.get(8), BodyDef.BodyType.StaticBody, limitWidth, limitHeight, mask);
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void update(float delta)
    {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        end = new Date();

        long timeElapsed = (end.getTime() - begin.getTime())/1000;

        if(timeElapsed >= Constants.POWERUP_FREQUENCY && powerUpBody == null)
            setUpPowerUp();

        if(powerUpBody!=null)
        {

        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    private void setUpPowerUp() {
        powerUpBody = new PowerUpBody(world, randPowerUp(), BodyDef.BodyType.StaticBody);
    }


    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        if(b1.getUserData() instanceof PuckModel && b2.getUserData() instanceof HandleModel)
        {
            b1.setLinearVelocity(handleBody.getVel().add(b1.getLinearVelocity()));
            lastTouch = "PLAYER";
            ((BotModel) botBody.getUserData()).setDefended(false);
            ((BotModel) botBody.getUserData()).setTrajectoryFlag(false);

            ((PuckModel) b1.getUserData()).resetWallBounce();
        }
        else if (b2.getUserData() instanceof PuckModel && b1.getUserData() instanceof HandleModel)
        {
            b2.setLinearVelocity(handleBody.getVel().add(b2.getLinearVelocity()));
            lastTouch = "PLAYER";
            ((BotModel) botBody.getUserData()).setDefended(false);
            ((BotModel) botBody.getUserData()).setTrajectoryFlag(false);

            ((PuckModel) b2.getUserData()).resetWallBounce();

        }
        else if(b1.getUserData() instanceof PuckModel && b2.getUserData() instanceof BotBody)
        {
            lastTouch = "BOT";
            ((BotModel) botBody.getUserData()).setDefended(true);
            ((PuckModel) b1.getUserData()).resetWallBounce();
        }
        else if (b2.getUserData() instanceof PuckModel && b1.getUserData() instanceof BotBody) {
            lastTouch = "BOT";
            ((BotModel) botBody.getUserData()).setDefended(true);
            ((PuckModel) b2.getUserData()).resetWallBounce();

        }
        else if(b1.getUserData() instanceof PuckModel && ((LineModel) b2.getUserData()).getPos().equals("Lat"))
        {
            ((PuckModel) b1.getUserData()).incWallBounce();
        }
        else if(b2.getUserData() instanceof PuckModel && ((LineModel) b1.getUserData()).getPos().equals("Lat"))
        {
            ((PuckModel) b2.getUserData()).incWallBounce();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public HandleBody getHandle() {
        return handleBody;
    }

    public String getLastTouch() {
        return lastTouch;
    }

    public World getWorld() {
        return world;
    }
}
