package com.example;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javax.swing.*;

public class ControlPanel {
    public ControlPanel() {
        JFrame frame = new JFrame("Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 100);

        JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setVisible(true);

        Platform.runLater(() -> initFX(fxPanel));
    }

    private void initFX(JFXPanel fxPanel) {
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
    }
}
