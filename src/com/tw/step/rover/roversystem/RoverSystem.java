package com.tw.step.rover.roversystem;

import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.rover.Rover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoverSystem {
    private List<Rover> rovers = new ArrayList<>();
    private Map<String, RoverCommands> roverCommandMap = new HashMap<>();

    public void addRover(List<Rover> rover) {

        this.rovers.addAll(rover);
    }

    public void addCommands(Map<String, RoverCommands> commandMap) {
        this.roverCommandMap = commandMap;
    }

    public void execute() {
        for (Rover rover : rovers) {
            RoverCommands commands = roverCommandMap.get(rover.getId());
            commands.execute(rover);
        }
    }


    @Override
    public String toString() {
        return rovers.stream()
                .map(Rover::toString)
                .collect(Collectors.joining("\n"));
    }
}
