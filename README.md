# Drone Simulation Project

## Project Description

This project is a simulation of a drone that can be controlled manually or through an AI autopilot system. The drone can move in different directions, change speed, and return to its starting point. The project includes obstacle detection to avoid collisions and a visual trail to track the drone's path. The simulation is built using jMonkeyEngine (jME3) for 3D graphics and JavaFX for the control panel.

## Requirements

- Java 8 or higher
- Gradle
- JME3 (jMonkeyEngine 3)
- JavaFX

### Installation

1. **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Install Gradle:**
    Follow the instructions on the [Gradle website](https://gradle.org/install/) to install Gradle on your system.

3. **Build the project:**
    ```bash
    gradle build
    ```

4. **Run the project:**
    ```bash
    gradle run
    ```

## Class Descriptions

### `DroneSimulation`

**Description:**  
This is the main class that sets up the simulation environment. It initializes the 3D map, drone, trail, obstacle detector, and lights. It also sets up the camera to follow the drone and handles user input through a control panel.

**Methods:**
- `simpleInitApp()`: Initializes the 3D environment and loads the map, drone, and other components.
- `simpleUpdate(float tpf)`: Updates the drone's position and checks for obstacles.
- `handleInput(String name, boolean isPressed, float tpf)`: Handles user input from the control panel.
- `returnHome()`: Triggers the return home functionality for the drone.

### `ControlPanel`

**Description:**  
This class creates a GUI control panel using JavaFX. The panel includes buttons for controlling the drone's movements (e.g., start, stop, spin, up, down, speed up, slow down, AI fly, return home). The buttons send commands to the `DroneSimulation` instance to control the drone.

**Methods:**
- `initFX(JFXPanel fxPanel)`: Initializes the JavaFX panel with control buttons.
- `ControlPanel()`: Constructor to set up the control panel UI.

### `Drone`

**Description:**  
This class represents the drone. It handles the drone's movement, direction, speed, and state (e.g., autopilot mode). It also includes methods for handling user input and updating the drone's position based on manual or autopilot control.

**Methods:**
- `handleInput(String name, boolean isPressed, float tpf)`: Processes user input to control the drone.
- `manualMove(float tpf, ObstacleDetector obstacleDetector)`: Manually moves the drone, checking for obstacles.
- `autopilotMove(float tpf, ObstacleDetector obstacleDetector)`: Automatically moves the drone, avoiding obstacles.
- `updateDirection()`: Updates the drone's direction based on its rotation.
- `updateVelocity()`: Updates the drone's velocity based on its current speed and direction.

### `ObstacleDetector`

**Description:**  
This class is responsible for detecting obstacles in the drone's path. It uses a sensor range to check for collisions with other objects in the environment and ignores cross marks used to track the drone's path.

**Methods:**
- `checkObstacle(Vector3f position, Vector3f direction, String directionKey)`: Checks for obstacles in a given direction.

### `CrossPath`

**Description:**  
This class manages the waypoints and cross marks along the drone's path. It updates the cross path based on the drone's current position and handles the return home process by moving the drone back along its path.

**Methods:**
- `updateCrossPath(Vector3f position)`: Adds a cross point to the path.
- `returnHome(Drone drone)`: Guides the drone back to the start point using the marked cross points.

### `Trail`

**Description:**  
This class creates a visual trail behind the drone. It updates the trail based on the drone's current position, creating a line that follows the drone's movements.

**Methods:**
- `updateTrail(Vector3f position)`: Updates the trail with the drone's current position.
- `removeTrail()`: Removes the trail from the map.

## Usage

1. **Start the Simulation:**  
    Run the project using Gradle as described in the installation steps.

2. **Control the Drone:**  
    Use the control panel to send commands to the drone:
    - **Start:** Moves the drone forward.
    - **Stop:** Stops the drone's movement.
    - **Spin 90:** Spins the drone 90 degrees clockwise.
    - **Spin -90:** Spins the drone 90 degrees counterclockwise.
    - **Spin 10:** Spins the drone 10 degrees clockwise.
    - **Spin -10:** Spins the drone 10 degrees counterclockwise.
    - **Spin 30:** Spins the drone 30 degrees clockwise.
    - **Spin -30:** Spins the drone 30 degrees counterclockwise.
    - **Spin 180:** Spins the drone 180 degrees.
    - **Up:** Moves the drone upward.
    - **Down:** Moves the drone downward.
    - **Speed Up:** Increases the drone's speed.
    - **Slow Down:** Decreases the drone's speed.
    - **AI Fly:** Toggles autopilot mode on or off.
    - **Return Home:** Returns the drone to its starting point.

## Examples from the program

The control panel:

![controll panel](https://github.com/nirmeir/Flying-drone-in-3D-map/assets/24902621/e8bc32d5-1a23-44ba-bb6b-b699faa7e326)

Drone recognize an object and flying above him:

![flying above object](https://github.com/nirmeir/Flying-drone-in-3D-map/assets/24902621/2286bc44-f0ca-4d4d-8f2a-8bcf97154fb9)

Sensor recognize the surface and stop:

![sensor to the surface](https://github.com/nirmeir/Flying-drone-in-3D-map/assets/24902621/e560a8b2-4553-4854-8eab-e919282b266d)

