package com.game;

//keeps track of any data and variables related the game's runtime
public class GameStatistics {
    private static Game game;

    //todo used event listeners to update these
    private int fps;
    private int frameCount;
    private long elapsedTime;


    public GameStatistics(Game game) {
        GameStatistics.game = game;
    }

    public void updateFPS(int newFPS) {
        this.fps = newFPS;
    }

    public void updateFrameCount() {
        this.frameCount++;
    }

    public void updateElapsedTime(long additionalElapsedTime) {
        this.elapsedTime += elapsedTime;
    }

    public Game getGame() { return game; }
    public int getFps() { return this.fps; }
    public int getFrameCount() { return this.frameCount; }
    public long getElapsedTime() { return this.elapsedTime; }
}
