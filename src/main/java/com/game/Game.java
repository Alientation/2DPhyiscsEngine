package com.game;

import com.physics2d.PhysicsEngine;
import com.util.Time;

public class Game {
    private static final int TARGET_FPS = 60;
    private static final int TARGET_PHYSICS_UPDATE_PER_SECOND = 60;
    private static final long TARGET_UPDATE_TIME_NS =  Time.NANOSECONDS_IN_SECOND / TARGET_FPS;
    private static final long TARGET_PHYSICS_UPDATE_TIME_NS = Time.NANOSECONDS_IN_SECOND / TARGET_PHYSICS_UPDATE_PER_SECOND;
    private static final long THREAD_SLEEP_TIME_MS = 5;
    private final GameStatistics gameStatistics;

    private boolean isRunning;
    private final PhysicsEngine physicsEngine;

    public Game() {
        this.isRunning = false;
        this.gameStatistics = new GameStatistics(this);
        this.physicsEngine = new PhysicsEngine(this);
    }

    /*
        initialization
        game loop
        clean up
     */
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

        //todo use a better queue based system to track realtime fps, tps, and pups
        int fps; //current frames per second
        int tps; //current game updates per second
        int pups; //current physics updates per second
        int framesCount = 0; //frames since last second
        int gameUpdateCount = 0; //game updates since last second
        int physicsUpdateCount = 0; //physics updates since last second

        /* game loop
            Physics update
            Inputs update
            Game update
            Render
            - Scene render
            - GUI render
            Pause
         */
        while(isRunning) {
            //update time of update cycle starting
            lastTimeNS = startTimeNS;
            startTimeNS = Time.getTimeNano();

            //time in between update cycles
            elapsedTimeNS += startTimeNS - lastTimeNS;
            gameStatistics.updateElapsedTime(elapsedTimeNS); //todo events instead

            //physics todo decide whether to have physics calls happen together appropriately in tandem with update calls
            long tempElapsedTime = elapsedTimeNS;
            while (tempElapsedTime >= TARGET_PHYSICS_UPDATE_TIME_NS) {
                updatePhysics(Time.convertNanoToSeconds(TARGET_PHYSICS_UPDATE_TIME_NS));
                physicsUpdateCount++;
                gameStatistics.updatePhysicsUpdateCount(); //todo events instead

                tempElapsedTime-=TARGET_PHYSICS_UPDATE_TIME_NS;
            }

            //user inputs
            processInput();

            //in case of small accumulations of time that adds up, run through extra ticks if needed
            while (elapsedTimeNS >= TARGET_UPDATE_TIME_NS) {
                updateGame(Time.convertNanoToSeconds(TARGET_UPDATE_TIME_NS)); //ticks with delta time converted to seconds
                gameUpdateCount++;
                gameStatistics.updateGameUpdateCount(); //todo events instead

                elapsedTimeNS -= TARGET_UPDATE_TIME_NS;
                gameTimeNS += TARGET_UPDATE_TIME_NS;
                gameStatistics.updateGameTime(gameTimeNS); //todo events instead

                //one second has passed since last fps and tps update
                if (gameTimeNS - lastSecTimeNS >= Time.NANOSECONDS_IN_SECOND) {
                    lastSecTimeNS += Time.NANOSECONDS_IN_SECOND;
                    fps = framesCount;
                    tps = gameUpdateCount;
                    pups = physicsUpdateCount;
                    framesCount = 0;
                    gameUpdateCount = 0;
                    physicsUpdateCount = 0;

                    gameStatistics.updateFPS(fps); //todo events instead
                    gameStatistics.updateTPS(tps); //todo events instead
                    gameStatistics.updatePUPS(pups); //todo events instead
                }
            }

            render((float) elapsedTimeNS / TARGET_UPDATE_TIME_NS);
            framesCount++;
            gameStatistics.updateFrameCount(); //todo events instead

            //calculate how much extra time for this update cycle
            endTimeNS = Time.getTimeNano();
            updateTimeNS = endTimeNS - startTimeNS;
            sleepTimeNS = TARGET_UPDATE_TIME_NS - updateTimeNS;

            //sleep the thread in small increments to pass off that time
            while (sleepTimeNS > THREAD_SLEEP_TIME_MS * Time.NANOSECONDS_IN_MILLISECOND) {
                try {
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

    public void updatePhysics(float dt) {

    }

    public void processInput() {

    }

    public void updateGame(float dt) {

    }

    public void render(float dt) {

    }
}
