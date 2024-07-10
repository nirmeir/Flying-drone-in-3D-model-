package com.example;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public class CrossPath {
    private final AssetManager assetManager;
    private final Node rootNode;
    private final float distanceThreshold;
    private final LinkedList<Vector3f> waypoints = new LinkedList<>();
    private Vector3f lastWaypoint;

    private static final Vector3f INITIAL_DRONE_POSITION = new Vector3f(20, 5, 20); // Must match the drone's initial position
    private static final String CROSS_MARK_NAME = "CrossMark";

    public CrossPath(AssetManager assetManager, Node rootNode, float distanceThreshold) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.distanceThreshold = distanceThreshold;
        this.lastWaypoint = null; // Initialize with null to set on first update
    }

    public void updateCrossPath(Vector3f currentPosition) {
        if (lastWaypoint == null) {
            lastWaypoint = currentPosition.clone(); // Set the initial position
            // Do not add the first waypoint if it matches the drone's starting position
            if (!lastWaypoint.equals(INITIAL_DRONE_POSITION)) {
                addWaypoint(currentPosition);
                System.out.println("Initial waypoint added at: " + currentPosition);
            }
            return;
        }

        System.out.println("Updating cross path: Last waypoint = " + lastWaypoint + ", Current position = " + currentPosition);
        if (lastWaypoint.distance(currentPosition) >= distanceThreshold) {
            addWaypoint(currentPosition);
            lastWaypoint = currentPosition.clone(); // Use clone to create a deep copy
            System.out.println("Added waypoint at: " + currentPosition);
        }
    }

    private void addWaypoint(Vector3f position) {
        waypoints.add(position.clone()); // Store a clone to ensure deep copy
        Sphere sphere = new Sphere(10, 10, 0.5f); // Adjust the size as necessary
        Geometry crossMark = new Geometry(CROSS_MARK_NAME, sphere);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        crossMark.setMaterial(mat);
        crossMark.setLocalTranslation(position);
        enqueueToRootNode(() -> {
            rootNode.attachChild(crossMark);
            return null;
        });
    }

    public void returnHome(Drone drone) {
        System.out.println("Returning home");
        new Thread(() -> {
            while (!waypoints.isEmpty()) {
                Vector3f waypoint = waypoints.removeLast();
                moveDroneToWaypoint(drone, waypoint, waypoints.isEmpty());
                removeCrossMark(waypoint);
                try {
                    Thread.sleep(1000); // Adjust the sleep duration as necessary
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void moveDroneToWaypoint(Drone drone, Vector3f waypoint, boolean isLastWaypoint) {
        Vector3f currentPosition = drone.getModel().getLocalTranslation();
        float distance = currentPosition.distance(waypoint);
        float speed = 2f; // Adjust the speed as necessary
        float travelTime = distance / speed; // Time to travel to the waypoint

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (long) (travelTime * 1000);

        new Thread(() -> {
            while (System.currentTimeMillis() < endTime) {
                float t = (System.currentTimeMillis() - startTime) / (float) (endTime - startTime);
                Vector3f newPosition = currentPosition.interpolateLocal(waypoint, t);
                enqueueToRootNode(() -> {
                    drone.setLocalTranslation(newPosition.x, newPosition.y, newPosition.z);
                    return null;
                });
                try {
                    Thread.sleep(50); // Reduce the update frequency to allow smoother transitions
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enqueueToRootNode(() -> {
                drone.setLocalTranslation(waypoint.x, waypoint.y, waypoint.z);
                if (isLastWaypoint) {
                    drone.handleInput("Stop", true, 0); // Stop the drone at the last waypoint
                }
                return null;
            });
        }).start();
    }

    private void removeCrossMark(Vector3f position) {
        enqueueToRootNode(() -> {
            for (Spatial child : rootNode.getChildren()) {
                if (child instanceof Geometry && CROSS_MARK_NAME.equals(child.getName()) && child.getLocalTranslation().equals(position)) {
                    rootNode.detachChild(child);
                    break;
                }
            }
            return null;
        });
    }

    private void enqueueToRootNode(Callable<Void> callable) {
        DroneSimulation.getInstance().enqueue(callable);
    }
}
