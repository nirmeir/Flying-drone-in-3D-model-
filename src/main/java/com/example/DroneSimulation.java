package com.example;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.JmeCanvasContext;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;

public class DroneSimulation extends SimpleApplication {

    private static DroneSimulation instance;
    private Drone drone;
    private Trail trail;
    private ObstacleDetector obstacleDetector;
    private CrossPath crossPath;

    public static void main(String[] args) {
        DroneSimulation app = new DroneSimulation();
        instance = app;
        app.createCanvas(); // create the canvas
        JmeCanvasContext ctx = (JmeCanvasContext) app.getContext();
        ctx.setSystemListener(app);
        Dimension dim = new Dimension(1920, 1080);
        ctx.getCanvas().setPreferredSize(dim);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drone Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Create the JavaFX panel
            JFXPanel fxPanel = new JFXPanel(); // This will initialize the JavaFX environment
            Platform.runLater(() -> {
                GridPane gridPane = new GridPane();
                Button btnStart = new Button("Start");
                Button btnStop = new Button("Stop");
                Button btnSpin90 = new Button("Spin 90");
                Button btnSpinMinus90 = new Button("Spin -90");
                Button btnSpin10 = new Button("Spin 10");
                Button btnSpinMinus10 = new Button("Spin -10");
                Button btnSpin30 = new Button("Spin 30");
                Button btnSpinMinus30 = new Button("Spin -30");
                Button btnSpin180 = new Button("Spin 180");
                Button btnUp = new Button("Up");
                Button btnDown = new Button("Down");
                Button btnSpeedUp = new Button("Speed Up");
                Button btnSlowDown = new Button("Slow Down");
                Button btnAiFly = new Button("AI Fly");
                Button btnReturnHome = new Button("Return Home");

                gridPane.add(btnStart, 0, 0);
                gridPane.add(btnStop, 1, 0);
                gridPane.add(btnSpin90, 2, 0);
                gridPane.add(btnSpinMinus90, 3, 0);
                gridPane.add(btnSpin10, 4, 0);
                gridPane.add(btnSpinMinus10, 5, 0);
                gridPane.add(btnSpin30, 6, 0);
                gridPane.add(btnSpinMinus30, 7, 0);
                gridPane.add(btnSpin180, 8, 0);
                gridPane.add(btnUp, 9, 0);
                gridPane.add(btnDown, 10, 0);
                gridPane.add(btnSpeedUp, 11, 0);
                gridPane.add(btnSlowDown, 12, 0);
                gridPane.add(btnAiFly, 13, 0);
                gridPane.add(btnReturnHome, 14, 0);

                btnStart.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Start", true, 0)));
                btnStop.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Stop", true, 0)));
                btnSpin90.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin90", true, 0)));
                btnSpinMinus90.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin-90", true, 0)));
                btnSpin10.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin10", true, 0)));
                btnSpinMinus10.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin-10", true, 0)));
                btnSpin30.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin30", true, 0)));
                btnSpinMinus30.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin-30", true, 0)));
                btnSpin180.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Spin180", true, 0)));
                btnUp.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Up", true, 0)));
                btnDown.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Down", true, 0)));
                btnSpeedUp.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("SpeedUp", true, 0)));
                btnSlowDown.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("SlowDown", true, 0)));
                btnAiFly.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().handleInput("Autopilot", true, 0)));
                btnReturnHome.setOnAction(event -> Platform.runLater(() -> DroneSimulation.getInstance().returnHome()));

                Scene scene = new Scene(gridPane, 800, 100);
                fxPanel.setScene(scene);
            });

            frame.add(ctx.getCanvas(), BorderLayout.CENTER);
            frame.add(fxPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
        });
        app.startCanvas(); // start the canvas
    }

    public static DroneSimulation getInstance() {
        return instance;
    }

    @Override
    public void simpleInitApp() {
        // Register the assets directory
        assetManager.registerLocator("assets/", FileLocator.class);

        // Load the 3D map
        Spatial map = assetManager.loadModel("Models/map.obj");
        if (map == null) {
            System.out.println("Failed to load map model.");
            return;
        }
        map.setLocalScale(4f); // Increase the scale to make the map bigger
        map.setLocalTranslation(0, -1, 0); // Adjust the position if needed
        rootNode.attachChild(map);
        System.out.println("Map model loaded successfully.");

        // Initialize CrossPath with distance threshold of 5 units
        crossPath = new CrossPath(assetManager, rootNode, 5f);

        // Load the drone
        drone = new Drone(assetManager, rootNode, crossPath);
        drone.setLocalScale(0.1f);
        drone.setLocalTranslation(2, 1, -4); // Adjust the initial position
      
        // Initialize trail
        trail = new Trail(assetManager, rootNode);

        // Initialize obstacle detector
        obstacleDetector = new ObstacleDetector(rootNode, 1.3f, drone.getModel());

        // Add directional light
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);

        // Add ambient light
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(ambient);

        // Setup camera to follow the drone
        ChaseCamera chaseCam = new ChaseCamera(cam, drone.getModel(), inputManager);
        chaseCam.setDefaultDistance(10); // Adjust the distance from the drone
        chaseCam.setDefaultVerticalRotation((float) Math.toRadians(20)); // Adjust the vertical rotation angle
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (drone.isAutopilotEnabled()) {
            drone.autopilotMove(tpf, obstacleDetector);
        } else {
            drone.manualMove(tpf, obstacleDetector);
        }
        // Update the trail with the drone's current position
        trail.updateTrail(drone.getModel().getLocalTranslation());

        // Update the cross path with the drone's current position
        // System.out.println("Drone current position: " + drone.getModel().getLocalTranslation());
        crossPath.updateCrossPath(drone.getModel().getLocalTranslation());

        // Update camera to follow the drone and match its rotation
        cam.setLocation(drone.getModel().getLocalTranslation().add(0, 1.5f, -5));
        cam.lookAt(drone.getModel().getLocalTranslation(), Vector3f.UNIT_Y);
    }

    public void handleInput(String name, boolean isPressed, float tpf) {
        drone.handleInput(name, isPressed, tpf);
    }

    public void returnHome() {
        if (drone.isAutopilotEnabled()) {
            drone.setAutopilotEnabled(false);
        }
        crossPath.returnHome(drone);
    }
}
