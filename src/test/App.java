package test;

public class App {
    public static void main(String[] args) {
    }
}
        /*
        CLASSIC UNO deck has 108 cards:
            RED: 0, 1-9 (x2), SKIP, REVERSE, DRAW-TWO
            GREEN: 0, 1-9 (x2), SKIP, REVERSE, DRAW-TWO
            BLUE: 0, 1-9 (x2), SKIP, REVERSE, DRAW-TWO
            YELLOW: 0, 1-9 (x2), SKIP, REVERSE, DRAW-TWO
            WILD: WILD (x4), WILD DRAW FOUR (x4)

        each UnoCard object has a suit and a value

        create a list number values
        create a list of action card values
        create a list of wild card values

        create a list for each suit
        add each list of card values to the suit

        combine the suits together to form the deck

        */

        /*
        public static UnoDeck createDeck(UnoEdition edition) {
            return new UnoDeck(edition);
        }
         */

/*
    (Figure out the exact purpose for each class in order to create the necessary and appropriate methods for each class)

    what all does the Pile class need to do?
        what exactly do all Piles have in common across all games?

            - all piles can get the size of their pile of cards


            - do all piles need to check if their pile is empty?
                - does a draw pile need to check if its pile is empty?
                    - yes. if it's empty, we need to fill it again
                - does a discard pile need to check if its pile is empty?
                    - not necessary, but possibly yes, in case we need to check for error handling purposes

                        - add isEmpty() method to Pile


            - do all piles need to remove a single card?
                - does a draw pile need to remove a single card?
                    - yes, to be added to the discard pile, or to add it to a player's hand
                - does a discard pile need to remove a single card?
                    - no, but it needs to view a single card (the last card played)

                    - add a drawCard() method to the UnoDrawPile
                    - add a getLastPlayedCard() method to the UnoDiscardPile


            - do all piles need to remove all cards?
                - does a draw pile need to remove all cards?
                    - no. the draw pile needs to be filled when it is empty
                - does a discard pile need to remove all cards?
                    - yes. when the draw pile is empty, the discard pile needs to transfer its cards to the draw pile

                - add a transferCardsToDrawPile() method to the UnoDiscardPile


            - do all piles need to add a card?
                - does a draw pile need to add a card?
                    - no. a draw pile removes a card, and adds it to the player's hand or the discard pile
                - does a discard pile need to add a card?
                    - yes. a discard pile receives a card from the player

                - add a addCard() to the UnoDiscardPile

            - do all piles need to fill or add a list of cards?
                - does a draw pile need to fill its pile or add a list of cards?
                    - yes. at the start of the game, it needs to be filled. when its empty, it needs to be filled.
                - does a discard pile need to fill its pile or add a list of cards?
                    - no. a discard pile only receives a single card at a time.

                - add a fill() method to the UnoDrawPile so that it receives a list of cards and fills its list of cards


            - do all piles need to be able to shuffle?
                - does a draw pile need to be shuffled?
                    - yes. when the draw pile is refilled from the discard pile, it will need to shuffle the cards.
                - does a discard pile need to be shuffled?
                    - no. a discard pile doesn't shuffle its pile because it isn't a card dispenser.

                - add a shuffle() method to the UnoDrawPile()


            - do we need to get the pile of cards inside each pile?
                - we do need to get the pile of cards from the UnoDiscardPile, so that they can be added to the DrawPile as a list
                - we don't need to get the pile of cards from the UnoDrawPile all at once.

            - do we need to view the last card that was drawn from the draw pile?
                - sort of. we need to know which card was pulled so it can be added to discard pile
                    - when we remove the card from the draw pile, it returns the card

            - do we need to view the last card that was drawn from the discard pile?
                - yes, because the last card that was drawn from the discard pile will be placed on top of the discard pile
                - the player will need to know what that card is, so the discard pile will need to get that card from its pile

                - add a getLastCardPlayed() method to the UnoDiscardPile


    what all does the Deck class need to do?
        what exactly do all Decks have in common across all games?
            - all decks can be shuffled
            - all decks can get the size of their deck
            - all decks can return their deck
            - all decks can get their edition
            - does a deck need to know if it's empty?
                - no, it shouldn't matter if the deck is empty.

    what all does the UnoDeck need to do?
        - creates the original deck
        - owns the deck
        - transfers the deck to the draw pile
        - shuffles the deck

    what all does the draw pile need to do?
        - owns the draw pile
        - shuffles the draw pile
        - draws a card
        - removes the card that's drawn

    what all does the discard pile need to do?
        - owns the discard pile
        - view the card that is on top
        - transfer the pile to the draw pile
        - adds the card that's drawn

    what does the UnoCardMachine do?
        - disperse cards to players
        - shuffle the draw pile
        - shuffle the deck
        - draw card a card from the draw pile
        - get the last played card

    what will need to be done to make a new Uno Edition?
    what will need to be done to add a card game?
 */