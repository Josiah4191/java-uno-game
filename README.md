# Java UNO Game
## Project Overview
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
  - Code structured to allow future customization (e.g., multiple card themes, colors, and styles) — not implemented yet, but designed with expansion in mind

## Technologies Used

- **Java** — Core logic, networking, and data handling
- **JavaFX** — User interface and game rendering
- **Object-Oriented Design** — Flexible class hierarchy for cards, decks, and players
- **Sockets/Multithreading** — Networking and multiplayer functionality

## Project Structure

- `UnoCard`, `UnoDeck`, `UnoCardMachine` — Core card and deck logic
- `GameManager` — Handles players, rules, and turn flow
- `Player`, `HumanPlayer`, `ComputerPlayer` — Player hierarchy
- `Menu` and UI Classes — JavaFX-based menus and overlays
- `Multiplayer` — Networking code for server and client connections

## Setup

1. Install Java 17+ and the JavaFX SDK.

2. Clone this repository.

3. Open the project in your preferred IDE (IntelliJ, Eclipse, NetBeans).

4. Add the following VM options in your Run/Debug configuration:
   - --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media

6. Run the main class to start the application.

## Possible Improvements
I coded the majority of this project myself, and if I were to revisit it, several areas could be improved:

- **Refactoring for SOLID principles:** While I aimed to follow SOLID, there are clear violations. Some packages and classes would benefit from restructuring, and certain code should be extracted into interfaces to better support dependency inversion.

- **Client/Server architecture:** The networking code was rushed, and much of it could be refactored into smaller methods to better follow the Single Responsibility Principle.

- **Exception handling:** There are still some handled exceptions being thrown occasionally in the server class that would need investigation.

Overall, this application isn’t highly polished, but it was a valuable learning experience. The key takeaway for me is how I can apply what I’ve learned here to future projects.

---
