import random

from src.game.services import GameState
from src.board.exceptions import ValidationException


class GameUI:
    def __init__(self, game):
        self.__game = game

    def run_game(self):
        first_move = [True, False]
        human_turn = random.choice(first_move)
        while self.__game.game_state() == GameState.ONGOING:
            print("Game board: ")
            print(str(self.__game.board))

            if human_turn:
                valid_move = False
                while not valid_move:
                    try:
                        col = int(input("Select the column you want to make the move: "))
                        row = self.__game.board.next_available_row(col)
                        self.__game.board.set_position(row, col, 1)
                        valid_move = True
                    except ValidationException as ve:
                        print(ve)
                    except ValueError:
                        print("Your choice must be a numerical value!\n")
            else:
                try:
                    row, col = self.__game.computer_move(self.__game.board)
                    self.__game.board.set_position(row, col, 2)
                    print("Computer performs the move on position (" + str(row) + ", " + str(col) + ")")
                except ValidationException as ve:
                    print(ve)

            human_turn = not human_turn
            if self.__game.game_state() == GameState.DRAW:
                print("It's a draw!\n")
                print(str(self.__game.board))
            if self.__game.game_state() == GameState.WON:
                if human_turn:
                    print("Computer wins!\n")
                else:
                    print("Player wins!\n")
                print(str(self.__game.board))


