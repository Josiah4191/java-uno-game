# Java UNO Game

---

## Project Overview
UNO card game built in **Java** with a **JavaFX** interface.  
It was originally developed as a **group project**, and while the core functionality is complete, it remains a **work in progress** with ongoing improvements focused on multiplayer support.

---

### Features

#### Local Play
- Human vs. Computer opponents
- AI players with basic strategy
- Support for up to 10 players
#### Game Logic
- Full implementation of UNO rules (reverse, skip, draw, wild cards, etc.)
- Turn order and direction tracking
- Automatic validation of playable moves
#### User Interface
- Built with JavaFX
- Displays your hand of cards, other players’ card counts, and turn indicators
- Menus for New Game, Save, Load, Options, and Rules
#### Customization
- Code structured to allow future customization (e.g., multiple card themes, colors, and styles) — not implemented yet, but designed with expansion in mind

---

### Technologies Used

- **Java** — Core logic, networking, and data handling
- **JavaFX** — User interface and game rendering
- **Object-Oriented Design** — Flexible class hierarchy for cards, decks, and players
- **Sockets/Multithreading** — Networking and multiplayer functionality

---

### Project Structure

- `UnoCard`, `UnoDeck`, `UnoCardMachine` — Core card and deck logic
- `GameManager` — Handles players, rules, and turn flow
- `Player`, `HumanPlayer`, `ComputerPlayer` — Player hierarchy
- `Multiplayer` — Networking code for server and client connections
- `Menu and UI Classes` — JavaFX-based menus and overlays

---
