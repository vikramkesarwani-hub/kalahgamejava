## kalahgamejava
Java8+Springboot

## Game use cases - 
### UseCase 1
Game setup applicable for two player/user, no computer or AI. 
### UseCase 2
6 pits for each player called houses, total 12 pits (p1....p6 - player1 & p7...p12 - player2)
2 large pits (zones) for each player (p0 & p13)
### UseCase 3
Starting point - all 12 pits in each player having 6 stones in it, large pits are empty. 
### UseCase 4
Each player will run at a time, player can start move from his/her pits only and pick all stone from that pit. 
### UseCase 5
Stones will be filled in each pit from pit next to starting pit including player his/her own kalah. Run move for number of stones in picked pit. No stone count increase in opposite player kalah during this move. 
### UseCase 6
If last stone lands on players own kalah, he will get another move. This is infinite condition. 
### UseCase 7
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalah. 
### UseCase 8
GameOver pre-condition
If any of the player stone in pits are empty mean count in all pits is zero. GameOver post-condition
The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah. 
### UseCase 9
The winner of the game is the player who has the most stones in his Kalah. 

### Following are pre-conditions for game play, need improvement - 
1) Player should understand the game. 
2) Both player should play each move fairly one by one. 
3) Currently not all messages are returned using as json message and logged in console.
