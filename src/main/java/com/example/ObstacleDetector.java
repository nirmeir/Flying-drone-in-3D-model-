package com.example;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class ObstacleDetector {

    private final Node rootNode;
    private final float sensorRange;
    private final Spatial droneModel; // Reference to the drone's model
    private static final String CROSS_MARK_NAME = "CrossMark"; // Match the name used in CrossPath

    public ObstacleDetector(Node rootNode, float sensorRange, Spatial droneModel) {
        this.rootNode = rootNode;
        this.sensorRange = sensorRange;
        this.droneModel = droneModel;
    }

    public boolean checkObstacle(Vector3f position, Vector3f direction, String directionKey) {
        Ray ray = new Ray(position, direction);
        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);

        // System.out.println("Checking for obstacles...");
        // System.out.println("Position: " + position);
        // System.out.println("Direction: " + direction);
        // System.out.println("Sensor Range: " + sensorRange);
        // System.out.println("Number of collisions detected: " + results.size());

        boolean obstacleDetected = false;
        for (int i = 0; i < results.size(); i++) {
            Geometry collisionGeometry = results.getCollision(i).getGeometry();
            // System.out.println("Collision " + i + ": Distance = " + results.getCollision(i).getDistance() + 
            //                    ", Geometry = " + collisionGeometry.getName());

            if (!collisionGeometry.getName().startsWith("drone") && !CROSS_MARK_NAME.equals(collisionGeometry.getName()) && results.getCollision(i).getDistance() < sensorRange) {
                obstacleDetected = true;
                System.out.println("Obstacle detected in direction: " + directionKey);
                break;
            }
        }

        if (!obstacleDetected) {
            System.out.println("No obstacle detected in direction: " + directionKey);
        }

        return obstacleDetected;
    }
}
