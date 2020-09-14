WarShips by Marko Dojkić - Java console game (avaiable to download on my website)

Project for entrance into advanced student's group on faculty. Although only needed things to implement were Ship, BattleShip and Cargo classes at the time, i've gradually created whole game.

Features:

- Creating cargo, battle, and command ships.
- Choosing their speed.
- Battling with small, big battle ships and command ship.
- Automatic creation of ships with random values.
- Automatic game simulation (computer playing by himself).
- Text styling with colors.
- Rank list. 
- In game instructions how to play.

Gameplay:
1. Start game from main menu.
2. Choose number of players (command ships) min. 2.
3. Create other small cargo, big cargo, small battle and big battle ships manually or with random values.
4. Battle one on one with battle ships. (attacking is based on positive diffrence in speed - <base_damage>*<diff_in_speeds>)
5. GOAL: Destroy all opponents ships.

Ships info: (Health, Speed, Damage, Shield, Capacity*)
- SmallCargoShip (100, <user_input>, N/A, 20, 4.000)
- BigCargoShip   (200, <user_input>, N/A, 50, 10.000)
- SmallBattleShip (300, <user_input>, 50, 30, N/A)
- BigBattleShip  (500, <user_input>, 100, 100, N/A)
- CommandShip (1000, <user_input>, 150, 200 N/A)

* Used to create delay of 4 or 10 seconds when cargo ship is destroyed.
