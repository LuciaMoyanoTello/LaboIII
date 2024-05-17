package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        Position position1 = new Position(2,3);
        Position position2 = new Position(2,3);
        Position position3 = new Position(2,4);

        assertTrue(position1.equals(position2));
        assertFalse(position1.equals(position3));


        // TODO: Probar este metodo publico
    }
}