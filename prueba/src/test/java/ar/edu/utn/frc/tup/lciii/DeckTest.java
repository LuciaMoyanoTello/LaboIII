package ar.edu.utn.frc.tup.lciii;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void createDeckTest() {
        Deck deck = new Deck();
        List<Card> cards = deck.getCards();

        assertEquals(40, cards.size()); // Validar que el mazo se crea con 40 cartas

        for (Card card : cards) {
            assertNotEquals(8, card.getNumber()); // Validar que no se incluyen los 8
            assertNotEquals(9, card.getNumber()); // Validar que no se incluyen los 9
        }
        // Validar que todas las cartas de un mazo de 40 cartas estén presentes
        for (CardSuit suit : CardSuit.values()) {
            for (int i = 1; i <= 7; i++) {
                assertTrue(cards.contains(new Card(suit, i,i)));
            }

            assertTrue(cards.add(new Card(suit, 10, 8)));
            assertTrue(cards.add(new Card(suit, 11, 9)));
            assertTrue(cards.add(new Card(suit, 12, 10)));
        }
    }

    @Test
    void takeCardTest() {
        Deck deck = new Deck();
        int initialSize = deck.getCards().size();
        Card topCard = deck.getCards().peek(); // Obtener la carta en la parte superior del mazo
        Card takenCard = deck.takeCard(); // Tomar una carta del mazo

        assertEquals(initialSize - 1, deck.getCards().size()); // Validar que la cantidad de cartas disminuye en 1
        assertEquals(topCard, takenCard); // Validar que la carta tomada es la que se esperaba
    }


    @Test
    void isEmptyTest() {
        Deck deck = new Deck();
        assertFalse(deck.isEmpty()); // Validar que el mazo no está vacío al principio

        while (!deck.isEmpty()) {
            deck.takeCard(); // Sacar todas las cartas del mazo
        }

        assertTrue(deck.isEmpty()); // Validar que el mazo está vacío después de sacar todas las cartas
    }

    @Test
    void shuffleDeckTest() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck2.shuffleDeck(); // Mezclar el segundo mazo

        assertNotEquals(deck1.getCards(), deck2.getCards()); // Validar que las cartas no están en el mismo orden
    }
}