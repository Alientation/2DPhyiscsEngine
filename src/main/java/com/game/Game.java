package com.game;

import com.physics2d.PhysicsEngine;
import com.util.Time;

public class Game {
    private static final int TARGET_FPS = 60;
    private static final long TARGET_FRAME_TIME_NS = Time.NANOSECONDS_IN_SECOND / TARGET_FPS;
    private static final long THREAD_SLEEP_TIME_MS = 5;
    private final GameStatistics gameStatistics;

    private boolean isRunning;
    private final PhysicsEngine physicsEngine;

    public Game() {
        this.isRunning = false;
        this.gameStatistics = new GameStatistics(this);
        this.physicsEngine = new PhysicsEngine(this);
    }

    public void start() {
        //already running, so don't start another game loop
        if (isRunning) return;
        isRunning = true;

        //times in nanoseconds
        long gameTimeNS = 0; //total time passed in game
        long lastSecTimeNS = 0; //time at last update of fps and tps

        long startTimeNS = Time.getTimeNano(); //time at beginning of update cycle
        long endTimeNS; //time at end of update cycle
        long lastTimeNS; //time at the beginning of the previous update cycle
        long elapsedTimeNS = 0L; //time passed between update cycles
        long updateTimeNS; //time passed for an update
        long sleepTimeNS; //time a thread should sleep

        int fps; //current frames per second
        int tps; //current ticks per second
        int framesCount = 0; //frames since last second
        int tickCount = 0; //ticks since last second

        //game loop
        while(isRunning) {
            //update time of update cycle starting
            lastTimeNS = startTimeNS;
            startTimeNS = Time.getTimeNano();

            //time in between update cycles
            elapsedTimeNS += startTimeNS - lastTimeNS;
            gameStatistics.updateElapsedTime(startTimeNS - lastTimeNS); //todo events instead

            //in case of small accumulations of time that adds up, run through extra ticks if needed
            while (elapsedTimeNS >= TARGET_FRAME_TIME_NS) {
                tick(Time.getDeltaTime(lastTimeNS, startTimeNS)); //ticks with delta time converted to seconds
                tickCount++;
                gameStatistics.updateTickCount(); //todo events instead

                elapsedTimeNS -= TARGET_FRAME_TIME_NS;
                gameTimeNS += TARGET_FRAME_TIME_NS;
                gameStatistics.updateGameTime(gameTimeNS); //todo events instead

                //one second has passed since last fps and tps update
                if (gameTimeNS - lastSecTimeNS >= Time.NANOSECONDS_IN_SECOND) {
                    lastSecTimeNS += Time.NANOSECONDS_IN_SECOND;
                    fps = framesCount;
                    tps = tickCount;
                    framesCount = 0;
                    tickCount = 0;

                    gameStatistics.updateFPS(fps); //todo events instead
                    gameStatistics.updateTPS(tps); //todo events instead
                }
            }

            render();
            framesCount++;
            gameStatistics.updateFrameCount(); //todo events instead

            //calculate how much extra time for this update cycle
            endTimeNS = Time.getTimeNano();
            updateTimeNS = endTimeNS - startTimeNS;
            sleepTimeNS = TARGET_FRAME_TIME_NS - updateTimeNS;

            //sleep the thread in small increments to pass off that time
            while (sleepTimeNS > THREAD_SLEEP_TIME_MS * Time.NANOSECONDS_IN_MILLISECOND) {
                try {
                    //thread sleep is in milliseconds todo update to ScheduledThreadPoolExecutor
                    Thread.sleep(THREAD_SLEEP_TIME_MS);

                    sleepTimeNS -= THREAD_SLEEP_TIME_MS * Time.NANOSECONDS_IN_MILLISECOND;
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
