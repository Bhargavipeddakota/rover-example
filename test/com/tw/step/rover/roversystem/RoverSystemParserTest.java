package com.tw.step.rover.roversystem;

import com.tw.step.rover.boundary.InfinitePlateau;
import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.position.Navigator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverSystemParserTest {
    @Test
    void shouldParseAndExecuteRoverSystem() {
        RoverSystemScanner scanner = RoverSystemScanner.from("""
                5 5 
                1 2 N
                RFF
                """);
        RoverSystemParser parser = new RoverSystemParser(scanner, Navigator.create(), new CommandCreator());

        RoverSystem roverSystem = parser.parse();
        roverSystem.execute();

        assertEquals("3 2 EACTIVE", roverSystem.toString());
    }
}
