
import configparser
from src.board.board import Board
from src.game.services import Game
from src.game.services import MoveRandomStrategy, MoveToWinStrategy, MoveIntelligentStrategy
from src.user_interface.ui import GameUI
from src.user_interface.gui import GameGUI

if __name__ == '__main__':

    config = configparser.RawConfigParser()
    config.read('settings.properties')
    options = dict(config.items('OPTIONS'))

    board = Board()
    strategy = None

    if options['strategy'] == "random":
        strategy = MoveRandomStrategy()
    elif options['strategy'] == "try_to_win":
        strategy = MoveToWinStrategy()
    elif options['strategy'] == "AI_strategy":
        strategy = MoveIntelligentStrategy()

    game = Game(board, strategy)

    if options['user_interface'] == "ui":
        ui = GameUI(game)
        ui.run_game()
    elif options['user_interface'] == "gui":
        gui = GameGUI(game)
        gui.start()
