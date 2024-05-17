package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipMatchTest {


    @Test
    void testGetYesNoAnswer() {
        assertTrue(BattleShipMatch.getYesNoAnswer("y"));
        assertTrue(BattleShipMatch.getYesNoAnswer("Y"));

        assertFalse(BattleShipMatch.getYesNoAnswer("n"));
        assertFalse(BattleShipMatch.getYesNoAnswer("N"));

        assertNull(BattleShipMatch.getYesNoAnswer("foo"));


        // TODO: Probar este metodo privado
    }
}