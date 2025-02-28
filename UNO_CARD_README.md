Author: Josiah Stoltzfus
Date: 2/28/2025

Comments have been added to each class file if you want to view the code. If you notice anything that I might have overlooked, or if something doesn't work properly, please let me know, and feel free to edit.

The important classes that you need to know about are:
  - UnoDeck
  - UnoCardMachine
  - UnoCardImageManager
  - UnoEdition
  - UnoCardTheme

The UnoCardMachine is our controller to manage the piles of cards (the deck, draw pile, discard pile).

The UnoDeck creates the deck of cards.

The UnoCardImageManager retrieves card images and sets the card theme.

The UnoEdition enum gets the editions.

The UnoCardTheme enum gets the themes.

All other class are not meant for our use in the program. They include:
  - UnoCard
  - UnoDeckFactory
  - UnoDiscardPile
  - UnoDrawPile
  - Card
  - Pile
  - Deck
  - UnoSuit
  - UnoValue

