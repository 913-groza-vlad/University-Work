from game.services import Game
import pickle


class GameFile(Game):

    def __init__(self, board, file_name):
        Game.__init__(self, board)
        self._file_name = file_name
        self._board = board
        self._load_file()

    def _load_file(self):
        with open(self._file_name, "rb") as f:
            try:
                self._board = pickle.load(f)
            except EOFError:
                pass

    def _save_file(self):
        with open(self._file_name, "wb") as f:
            pickle.dump(self._board, f)