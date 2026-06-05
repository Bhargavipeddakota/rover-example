package com.tw.step.rover.roversystem;

import com.tw.step.rover.commands.CommandCreator;
import com.tw.step.rover.position.Navigator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RoverSystemParserTest {
    @Test
    void shouldParseAndExecuteRoverSystem() {
//        RoverSystemScanner scanner = RoverSystemScanner.from("""
//            5 5
//            R1 1 2 N
//            R1: RFF
//            """);
        RoverSystemScanner scanner = mock(RoverSystemScanner.class);
        Navigator Navigator  = mock(Navigator.class);
        CommandCreator commandCreator = mock(CommandCreator.class);
        RoverSystemParser parser = new RoverSystemParser(scanner,Navigator,commandCreator);

        RoverSystem roverSystem = parser.parse();
        roverSystem.execute();

        assertEquals("3 2 EACTIVE", roverSystem.toString());
    }


    @Test
    void shouldExecuteCommandsForMultipleRovers() {
        String input = """
        5 5
        R1 1 2 N
        R2 3 3 E
        R1: F
        R2: F
        """;

        RoverSystemScanner scanner = RoverSystemScanner.from(input);
        RoverSystemParser parser =
                new RoverSystemParser(scanner,Navigator.create(),new CommandCreator());

        RoverSystem system = parser.parse();
        system.execute();

        String output = system.toString();

//        assertEquals("1 3 NACTIVE", system.toString());
//        assertEquals("4 3 EACTIVE", system.toString());
        assertTrue(output.contains("1 3 NACTIVE"));
        assertTrue(output.contains("4 3 E"));
    }
    @Test
    void shouldHaveOneRoverActiveAndOneLost() {

        String input = """
        5 5
        R1 1 2 N
        R2 0 0 S
        R1: FF
        R2: F
        """;

        RoverSystemScanner scanner = RoverSystemScanner.from(input);

        RoverSystemParser parser =
                new RoverSystemParser(scanner,Navigator.create() ,new CommandCreator());

        RoverSystem system = parser.parse();
        system.execute();

        String output = system.toString();

        assertTrue(output.contains("1 4 NACTIVE"));
        assertTrue(output.contains("0 0 SLOST"));
    }
    @Test
    void shouldNotTeriminateIfFoundINvalidCmd() {
        RoverSystemScanner scanner = RoverSystemScanner.from("""
            5 5
            R1 1 2 N
            R1: kFFkk
            """);
        RoverSystemParser parser = new RoverSystemParser(scanner, Navigator.create(), new CommandCreator());

        RoverSystem roverSystem = parser.parse();
        roverSystem.execute();

    }
}
