package games;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

interface Rules {

    boolean validatePlay();

/*
at the beginning of the game, a player is selected at random within the list of players

the rules class will need a string containing the rules so it can be read from, or we can have a txt file for
the rules, and we can read that file

for UNO validatePlay, this method will need to know the last played card, and it will need to be passed the card
that the player is trying to play. it needs to check to make sure that they're a match

we need to keep track of the direction of play

we need to keep track of the next player, and to change who the next player is when the direction of play changes

we need to be able to skip a player

the rules class should basically be the referee

the rules class will need to be passed the game state so it can track players, their cards, the direction of play,
the last played card, and the

if the first card turned up from the draw pile (to form the discard pile) is an action
card, the action from that card applies and the first player must perform that
action

a player takes their turn by selecting any card from their hand that matches the
faced-up card in the discard pile. The player places their selected card face up
on the discard pile. then we pass that card to the validatePlay method to check if it's legal.

if the player has no cards that match, or they choose not to play, they must draw
a card from the draw pile. if the card they draw matches, then they can play it,
otherwise they keep the card.

the player earns points when they have no cards remaining. the number of
points is determined by the total value of all remaining cards from the other
players. if the final card played was an action card, e.g., a Draw Two or a Skip
card, the next player must still draw those cards

after scoring, either the game ends or a new round begins. the player with the
highest score at the end wins.

for two players, a Reverse works like a Skip

when an opponent presses the call uno button: loop through all the players and check their cards
exclude the player that pressed the call uno button. we can do this with a stream I think.

playerList.stream().filter(e -> e != playerWhoCalledUno).

    if (player.cardsRemaining() == 1 && player.sayUno == false) {
        // add cards to the player
    }

random thought:
    - we could add AI for players to be able to randomly select the best card
    - we could allow the ability to suggest a card that the player should play, if they are
    learning to play the game.



 */

}
