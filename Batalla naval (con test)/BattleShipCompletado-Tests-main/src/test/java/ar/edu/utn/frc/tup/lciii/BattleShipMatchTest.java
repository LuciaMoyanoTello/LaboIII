package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BattleShipMatchTest {


    @Test
    void testGetYesAnswerTrue() {
        // TODO: Probar este metodo privado
        String inputTest = "Y";
        BattleShipGame game = new BattleShipGame();
        Optional<Method> metodoProbar = ReflectionSupport.findMethod(BattleShipMatch.class,"getYesNoAnswer");
        metodoProbar.ifPresent(method -> assertTrue((Boolean) ReflectionSupport.invokeMethod(method, game, inputTest)));
    }

    @Test
    void testGetNoAnswerFalse(){
        String inputTest = "N";
        BattleShipGame game = new BattleShipGame();
        Optional<Method> metodoProbar = ReflectionSupport.findMethod(BattleShipMatch.class,"getYesNoAnswer");
        metodoProbar.ifPresent(method -> assertFalse((Boolean) ReflectionSupport.invokeMethod(method, game, inputTest)));
        //assertFalse(BattleShipMatch.getYesNoAnswer(inputTest));
    }

    @Test
    void testGetAnswerNull(){
        String inputTest = "Prueba405226-chancha";
        BattleShipGame game = new BattleShipGame();
        Optional<Method> metodoProbar = ReflectionSupport.findMethod(BattleShipMatch.class,"getYesNoAnswer");
        metodoProbar.ifPresent(method -> assertNull(ReflectionSupport.invokeMethod(method, game, inputTest)));
        //assertNull(BattleShipMatch.getYesNoAnswer(inputTest));
    }
}