package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.commands.MoveCommand;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverSystemTest {
    @Test
    void shouldExecuteCommandsForAddedRover() {
        RoverSystem roverSystem = new RoverSystem();
        Rover rover = new Rover("R1", new Coordinate(0, 0), Direction.N);
        List<Rover> rovers = List.of(rover);
        RoverCommands commands = new RoverCommands();
        commands.add(new MoveCommand(Navigator.create(), new InfinitePlateau()));

        Map<String, RoverCommands> commandMap = Map.of(
                "R1", commands
        );

        roverSystem.addRover(rovers);
        roverSystem.addCommands(commandMap);
        roverSystem.execute();

        assertEquals("0 1 NACTIVE", roverSystem.toString());
    }
}
