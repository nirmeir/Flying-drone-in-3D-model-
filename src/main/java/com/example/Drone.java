package com.example;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Drone {

    private Spatial drone;
    private Vector3f velocity = new Vector3f(0, 0, 0);
    private Vector3f direction = new Vector3f(0, 0, -1);
    private float rotationSpeed = 2f;
    private boolean autopilot = false;
    private int detectionFrameSkip = 20; // Check for obstacles every 20 frames
    private int currentFrame = 0;
    private float sensorRange = 0.5f; // Reduced sensor range for closer detection
    private CrossPath crossPath;
    private float speed = 2f; // Define the speed variable

    public Drone(AssetManager assetManager, Node rootNode, CrossPath crossPath) {
        this.crossPath = crossPath;
        drone = assetManager.loadModel("Models/drone.obj");
        rootNode.attachChild(drone);
        // Set initial position above the ground and slightly offset from any initial geometry
        drone.setLocalTranslation(0, 5, 0); // Adjust this as necessary to avoid initial collisions
    }

    public Spatial getModel() {
        return drone;
    }

    public void setLocalScale(float scale) {
        drone.setLocalScale(scale);
    }

    public void setLocalTranslation(float x, float y, float z) {
        drone.setLocalTranslation(x, y, z);
    }

    public boolean isAutopilotEnabled() {
        return autopilot;
    }

    public void setAutopilotEnabled(boolean autopilot) {
        this.autopilot = autopilot;
    }

    public void handleInput(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (autopilot && !name.equals("Autopilot")) return; // Ignore manual controls in autopilot mode

            switch (name) {
                case "Start":
                    System.out.println("Moving Forward");
                    updateVelocity(direction.mult(speed));
                    break;
                case "Stop":
                    System.out.println("Stopping");
                    updateVelocity(new Vector3f(0, 0, 0));
                    break;
                case "Spin90":
                    System.out.println("Spinning 90");
                    rotateDrone(90);
                    updateVelocity();
                    break;
                case "Spin-90":
                    System.out.println("Spinning -90");
                    rotateDrone(-90);
                    updateVelocity();
                    break;
                case "Spin10":
                    System.out.println("Spinning 10");
                    rotateDrone(10);
                    updateVelocity();
                    break;
                case "Spin-10":
                    System.out.println("Spinning -10");
                    rotateDrone(-10);
                    updateVelocity();
                    break;
                case "Spin30":
                    System.out.println("Spinning 30");
                    rotateDrone(30);
                    updateVelocity();
                    break;
                case "Spin-30":
                    System.out.println("Spinning -30");
                    rotateDrone(-30);
                    updateVelocity();
                    break;
                case "Spin180":
                    System.out.println("Spinning 180");
                    rotateDrone(180);
                    updateVelocity();
                    break;
                case "Up":
                    System.out.println("Moving Up");
                    updateVelocity(new Vector3f(0, 2, 0));
                    break;
                case "Down":
                    System.out.println("Moving Down");
                    updateVelocity(new Vector3f(0, -2, 0));
                    break;
                case "SpeedUp":
                    System.out.println("Speeding Up");
                    speed = Math.min(speed + 1, 10); // Max speed limit
                    updateVelocity(speed);
                    break;
                case "SlowDown":
                    System.out.println("Slowing Down");
                    speed = Math.max(speed - 1, 0); // Min speed limit, can stop
                    updateVelocity(speed);
                    break;
                case "Autopilot":
                    autopilot = !autopilot;
                    System.out.println("Autopilot " + (autopilot ? "enabled" : "disabled"));
                    if (autopilot) {
                        updateVelocity(direction.mult(speed)); // Start moving forward in autopilot mode
                    } else {
                        updateVelocity(new Vector3f(0, 0, 0)); // Stop moving when autopilot is disabled
                    }
                    break;
            }
        } else {
            if (!autopilot) {
                switch (name) {
                    case "Start":
                    case "Stop":
                        velocity.z = 0;
                        break;
                    case "Up":
                    case "Down":
                        velocity.y = 0;
                        break;
                }
            }
        }
    }

    private void rotateDrone(float angle) {
        drone.rotate(0, (float) Math.toRadians(angle), 0);
        updateDirection();
        updateVelocity(direction.mult(speed)); // Update the velocity to the new direction
    }

    private void updateVelocity() {
        velocity = direction.mult(speed);
    }

    public void manualMove(float tpf, ObstacleDetector obstacleDetector) {
        // Check for obstacles only if there is a velocity
        if (velocity.length() != 0) {
            boolean obstacleDetected = obstacleDetector.checkObstacle(drone.getLocalTranslation(), velocity.normalize(), "Move");
            if (!obstacleDetected) {
                // Update drone position based on velocity
                System.out.println("Drone Position Before Move: " + drone.getLocalTranslation());
                drone.move(velocity.mult(tpf));
                System.out.println("Drone Position After Move: " + drone.getLocalTranslation());
                System.out.println("Current Velocity: " + velocity);
            } else {
                System.out.println("Obstacle detected, stopping drone.");
                updateVelocity(new Vector3f(0, 0, 0)); // Stop the drone
            }
        }
    }

    public void autopilotMove(float tpf, ObstacleDetector obstacleDetector) {
        // Only check for obstacles every `detectionFrameSkip` frames
        if (currentFrame % detectionFrameSkip == 0) {
            boolean obstacleDetected = obstacleDetector.checkObstacle(drone.getLocalTranslation(), direction, "Forward");

            if (obstacleDetected) {
                updateVelocity(new Vector3f(0, 0, 0)); // Stop moving
                int attempts = 0;
                while (obstacleDetector.checkObstacle(drone.getLocalTranslation(), direction, "Forward") && attempts < 8) {
                    drone.rotate(0, (float) Math.toRadians(45), 0); // Rotate 45 degrees
                    updateDirection();
                    attempts++;
                }
                if (attempts < 8) {
                    updateVelocity(direction.mult(speed)); // Move forward in the new direction
                } else {
                    System.out.println("No clear path found. Drone stopped.");
                }
            }
        }
        drone.move(velocity.mult(tpf));
        currentFrame++;
    }

    private void updateDirection() {
        direction = drone.getLocalRotation().mult(new Vector3f(0, 0, -1));
        if (autopilot) {
            updateVelocity(direction.mult(speed));
        }
    }

    private void updateVelocity(Vector3f newVelocity) {
        velocity = newVelocity;
    }
    private void updateVelocity(float newSpeed) {
        velocity = direction.mult(newSpeed);
    }
}
