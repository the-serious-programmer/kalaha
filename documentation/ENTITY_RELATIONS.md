# Entity relations

In `documentation/diagrams` you will find the Entity Relationship Diagram (ERD).

The ERD shows that a game has multiple players, and a player has multiple pits.

## Field explanation

The status field on the Game entity represents the status (still playing or ended) of the game.

The type field on the Player entity helps to figure out who should be shown where on the board.  
The turn field on the Player entity helps to figure out who to show and use in the calculation for sowing pits as the
Player who has the current turn.

The type field on the Pit entity separates the score/Kalaha pits from the regular pits.  
The position field on the Pit entity is used to determine which field should be shown where and used in the calculation
to sow pits in order.  
The stones field on the Pit entity represents the amount of stones in a pit.
