# Java UNO Game

A semi functional UNO card game built in **Java** with a **JavaFX** interface.  
It was originally developed as a **group project**, and while the core functionality is complete, it remains a **work in progress** with ongoing improvements focused on multiplayer support.

---

## Features

- **Local Play**
  - Human vs. Computer opponents
  - AI players with basic strategy
  - Support for up to 10 players
- **Game Logic**
  - Full implementation of UNO rules (reverse, skip, draw, wild cards, etc.)
  - Turn order and direction tracking
  - Automatic validation of playable moves
- **User Interface**
  - Built with JavaFX
  - Displays your hand of cards, other players’ card counts, and turn indicators
  - Menus for New Game, Save, Load, Options, and Rules
- **Customization**
  - Multiple card themes (e.g., Classic, City, Jungle, Space)
  - Color themes for the UI
  - Customizable key bindings

---

## Technologies Used

- **Java** — Core logic, networking, and data handling
- **JavaFX** — User interface and game rendering
- **Object-Oriented Design** — Flexible class hierarchy for cards, decks, and players
- **Sockets/Multithreading** — Networking and multiplayer functionality

---

## Project Structure

- `UnoCard`, `UnoDeck`, `UnoCardMachine` — Core card and deck logic
- `GameManager` — Handles players, rules, and turn flow
- `Player`, `HumanPlayer`, `ComputerPlayer` — Player hierarchy
- `Menu` and UI Classes — JavaFX-based menus and overlays
- `Multiplayer` — Networking code for server and client connections

---

## Getting Started

### 1. Prerequisites
Before running the project, make sure you have:
- **Java JDK 17+** installed ([Download here](https://adoptium.net/) if needed).  
- **JavaFX SDK** installed ([Download here](https://openjfx.io/)).  
- An IDE such as **IntelliJ IDEA**, **Eclipse**, or **NetBeans**.  

### 2. Clone the Repository
```bash
git clone https://github.com/your-username/java-uno-game.git
