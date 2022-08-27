import unittest
from board.exception import ValidationException
from board.board import Board
from game.services import Game


class TestGame(unittest.TestCase):

    def setUp(self) -> None:
        self._board = Board()
        self._game = Game(self._board)

    def test_create_board(self):
        board = self._board
        self.assertEqual(board.get_position(0, 1), 0)
        board.set_position(1, 2, 1)
        self.assertEqual(board.get_position(1, 2), 1)
        with self.assertRaises(ValidationException) as ve:
            board.set_position(7, 2, 1)
        self.assertEqual(str(ve.exception), "Position outside the board!")
        with self.assertRaises(ValidationException) as ve:
            board.set_position(1, 8, 1)
        self.assertEqual(str(ve.exception), "Position outside the board!")
        with self.assertRaises(ValidationException) as ve:
            board.set_position(1, 2, 1)
        self.assertEqual(str(ve.exception), "Position already occupied!")
        with self.assertRaises(ValidationException) as ve:
            board.set_position(0, 0, 8)
        self.assertEqual(str(ve.exception), "Invalid symbol")

    def test_board_won(self):
        board = self._board
        game = self._game
        self.assertEqual(game.game_state(), False)
        board.set_position(0, 0, 1)
        board.set_position(0, 1, 1)
        board.set_position(0, 2, 1)
        self.assertEqual(game.game_state(), True)

    def test_board_draw(self):
        board = self._board
        game = self._game
        self.assertEqual(game.game_state(), False)
        board.set_position(0, 0, 1)
        board.set_position(0, 1, 2)
        board.set_position(0, 2, 1)
        board.set_position(1, 0, 2)
        board.set_position(1, 1, 1)
        board.set_position(1, 2, 2)
        board.set_position(2, 0, 2)
        board.set_position(2, 1, 1)
        board.draw_board().draw()
        self.assertEqual(game.game_state(), True)

    def test_computer_move(self):
        board = self._board
        game = self._game
        board.set_position(0, 1, 1)
        board.set_position(0, 2, 1)
        pos = game.computer_move()
        board.set_position(0, 0, 2)
        self.assertEqual(pos[0], 0)
        self.assertEqual(pos[1], 0)
        board.set_position(1, 2, 1)
        pos = game.computer_move()
        board.set_position(2, 2, 2)
        self.assertEqual(pos[0], 2)
        self.assertEqual(pos[1], 2)



if __name__ == '__main__':
    unittest.main()

