/*
todo:

Player class needs name
Player class needs a setName
Player class needs a getName
UnoPlayer needs boolean sayUno
UnoPlayer needs method sayUno
UnoPlayer needs method callUno

Every class needs to be examined to see what needs overridden or implemented:
    - toString()
    - hashCode()
    - equals()
    - comparable
    - serializable
    - iterable

Who tracks and manages the direction of play?
    How do we change the direction of play?
    We would need the list of players?
    We would need to know the current order of players?
    We would just need to know the next player, so first step is to make a method to get next player
    and to reverse the next player.
        getNextPlayer()
        reverseDirection()
    Should we make an enum for the Direction? Direction.FORWARD, Direction.REVERSE
    Do I need to put the human player at the start of the list? And then the opponents would be lined up as
    Player2, Player3, Player4 ...
        Will this affect the type of container? Do we actually need to keep track of the order? I think we do need an
        ordered list of players, so the HashMap is not going to cut it.

        Steps:
            Manage next player:
                1. Order the list of players.
                2. Get the current player.
                3. Get the previous player.
                4. Create method that can return the next player based on the previous player?
            Manage direction of play:
                1. Create Direction enum type: FORWARD, REVERSE
                2. Create method to setDirection
                3. Create method to getDirection

Who tracks and manages the order of players?

Who knows about and manages the next player?

Who skips players?

Who forces players to draw cards and when?
    Should we create a dealer class who has methods: What are the downsides?
        - skips players
        - forces players to draw cards in different scenarios

How do we decide which player will go first?

How do we check if the first card drawn is an action card?
    How do we enforce that the first player will play that card?

Who knows the last card played?

Who will compare the card that a player tries to play with the last card played?

How do we save the game?







 */