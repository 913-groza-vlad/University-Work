from board.board import Board
from game.services import Game
from ui.user_interface import UI


# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    board = Board()

    game = Game(board)

    ui = UI(game)

    ui.run_game()

