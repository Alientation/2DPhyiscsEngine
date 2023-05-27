package com.game;

//keeps track of any data and variables related the game's runtime
public class GameStatistics {
    private static Game game;

    //todo used event listeners to update these
    private int fps;
    private int tps;
    private int pups;
    private int frameCount;
    private int gameUpdateCount;
    private int physicsUpdateCount;
    private long elapsedTime;
    private long gameTime;


    public GameStatistics(Game game) {
        GameStatistics.game = game;
    }

    public void updateFPS(int newFPS) {
        this.fps = newFPS;
    }
    public void updateTPS(int newTPS) {
        this.tps = newTPS;
    }

    public void updatePUPS(int newPUPS) {
        this.pups = newPUPS;
    }

    public void updateFrameCount() {
        this.frameCount++;
    }
    public void updateGameUpdateCount() { this.gameUpdateCount++; }
    public void updatePhysicsUpdateCount() {
        this.physicsUpdateCount++;
    }

    public void updateElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public void updateGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public Game getGame() { return game; }
    public int getFps() { return this.fps; }
    public int getTps() { return this.tps; }
    public int getPups() { return this.pups; }
    public int getFrameCount() { return this.frameCount; }
    public int getGameUpdateCount() { return this.gameUpdateCount; }
    public int getPhysicsUpdateCount() { return this.physicsUpdateCount; }
    public long getElapsedTime() { return this.elapsedTime; }
    public long getGameTime() { return this.gameTime; }
}
