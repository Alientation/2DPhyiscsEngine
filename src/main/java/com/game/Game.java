package com.game;

import com.physics2d.PhysicsEngine;
import com.util.Time;

public class Game {
    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME_NANOSECONDS = 1_000_000_000 / TARGET_FPS;
    private static final long THREAD_SLEEP_TIME_MILLISECONDS = 5;
    private final GameStatistics gameStatistics;

    private boolean isRunning;
    private final PhysicsEngine physicsEngine;

    public Game() {
        this.isRunning = false;
        this.gameStatistics = new GameStatistics(this);
        this.physicsEngine = new PhysicsEngine(this);
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;

        long startTime;
        long endTime = Time.getTimeNano();
        long elapsedTime = 0L;
        long sleepTime;

        int fps = 60;
        int frames = 0;

        while(isRunning) {
            startTime = Time.getTimeNano();

            tick(Time.getDeltaTime(endTime,startTime));

            render();
            frames++;
            gameStatistics.updateFrameCount(); //todo events instead

            endTime = Time.getTimeNano();
            sleepTime = TARGET_FRAME_TIME_NANOSECONDS - (endTime - startTime);
            elapsedTime += endTime - startTime;
            gameStatistics.updateElapsedTime(elapsedTime); //todo events instead

            if (elapsedTime >= 1) {
                elapsedTime--;
                fps = frames;
                frames = 0;

                gameStatistics.updateFPS(fps); //todo events instead
            }

            if (sleepTime > 0) {
                try {
                    Thread.sleep(THREAD_SLEEP_TIME_MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void tick(float dt) {


        physicsEngine.tick(dt);
    }

    public void render() {

    }
}
