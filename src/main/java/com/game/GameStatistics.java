package com.game;

//keeps track of any data and variables related the game's runtime
public class GameStatistics {
    private static Game game;

    //todo used event listeners to update these
    private int fps;
    private int tps;
    private int frameCount;
    private int tickCount;
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

    public void updateFrameCount() {
        this.frameCount++;
    }
    public void updateTickCount() { this.tickCount++; }

    public void updateElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public void updateGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public Game getGame() { return game; }
    public int getFps() { return this.fps; }
    public int getTps() { return this.tps; }
    public int getFrameCount() { return this.frameCount; }
    public int getTickCount() { return this.tickCount; }
    public long getElapsedTime() { return this.elapsedTime; }
    public long getGameTime() { return this.gameTime; }
}
