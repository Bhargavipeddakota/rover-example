package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.Boundary;
import com.tw.step.rover.boundary.Plateau;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.commands.CommandNotFoundException;
import com.tw.step.rover.commands.RoverCommands;
import com.tw.step.rover.position.Coordinate;
import com.tw.step.rover.position.Direction;
import com.tw.step.rover.position.Navigator;
import com.tw.step.rover.rover.Rover;

import java.util.*;

public class RoverSystemParser {
    private final RoverSystemScanner scanner;
    private final Navigator navigator;
//    private final Boundary boundary;
    private final CommandCreator commandCreator;

    public RoverSystemParser(RoverSystemScanner scanner, Navigator navigator, CommandCreator commandCreator) {
        this.scanner = scanner;
        this.navigator = navigator;
//        this.boundary = boundary;
        this.commandCreator = commandCreator;
    }
    private List<Rover> parseRovers() {
        List<Rover> rovers = new ArrayList<>();
        while (scanner.hasNext() && !scanner.peek().contains(":")){
            String id = scanner.consume();
            Coordinate coordinate = scanner.scanCoordinate();
            Direction heading = scanner.scanDirection();
            rovers.add(new Rover(id,coordinate, heading));
        }

        return rovers;

    }


    public RoverSystem parse() {
        Boundary boundary = parseBoundary();
        RoverSystem roverSystem = new RoverSystem();
        List<Rover> rovers = parseRovers();
        roverSystem.addRover(rovers);
        Map<String, RoverCommands> roverCommands = parseRoverCommands(boundary);
        roverSystem.addCommands(roverCommands);
        return roverSystem;
    }

    private Boundary parseBoundary() {
        Coordinate topRight = scanner.scanCoordinate();
        return new Plateau(new Coordinate(0,0),topRight);
    }


    private Map<String, RoverCommands> parseRoverCommands(Boundary boundary) {
        Map<String, RoverCommands> commandMap = new HashMap<>();
        while(scanner.hasNext()){
            String roverId = scanner.consume().replace(":","");
            String instructions = scanner.consume();
            RoverCommands commands = new RoverCommands();
            for (char c : instructions.toCharArray()) {
                try {
                    commands.add(commandCreator.create(c, navigator, boundary));
                }
                catch (CommandNotFoundException e){
                    System.out.println(e.toString());
                }
            }
             commandMap.put(roverId,commands);
        }
        return commandMap;
    }
}
