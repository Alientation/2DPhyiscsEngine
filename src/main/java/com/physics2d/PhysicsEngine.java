package com.physics2d;

import com.game.Game;

public class PhysicsEngine {
    private final Game game;


    public PhysicsEngine(Game game) {
        this.game = game;
    }

    public void update(float dt) {

    }

    public Game getGame() { return game; }
}
