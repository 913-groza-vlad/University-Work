import unittest
from src.board.board import Board
from src.game.services import Game, GameState, MoveRandomStrategy, MoveToWinStrategy, MoveIntelligentStrategy
from src.board.exceptions import ValidationException
from copy import deepcopy


class TestGame(unittest.TestCase):

    def setUp(self) -> None:
        """
        Runs before every test method
        """
        self._board = Board()
        self._strategy = MoveRandomStrategy()
        self._game = Game(self._board, self._strategy)

    def tearDown(self) -> None:
        """
        Runs after every test method
        """
        pass

    def test_board(self):
        board = self._board
        self.assertEqual(board.get_position(0, 0), 0)
        board.set_position(0, 0, 1)
        self.assertEqual(board.get_position(0, 0), 1)

        with self.assertRaises(ValidationException) as ve:
            board.get_position(3, 10)
        self.assertEqual(str(ve.exception), "Position outside the board!")

        with self.assertRaises(ValidationException) as ve:
            board.set_position(7, 2, 2)
        self.assertEqual(str(ve.exception), "Cannot move outside of board!")

        with self.assertRaises(ValidationException) as ve:
            board.set_position(0, 0, 2)
        self.assertEqual(str(ve.exception), "Cannot overwrite board!")

        with self.assertRaises(ValidationException) as ve:
            board.set_position(3, 2, 'o')
        self.assertEqual(str(ve.exception), "Invalid symbol")

        displayed_board = board.__str__()
        self.assertEqual(displayed_board,  "  --  --  --  --  --  --  -- \n | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n | 0 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n | 1 | 0 | 0 | 0 | 0 | 0 | 0 | \n  --  --  --  --  --  --  -- \n")

    def test_available_row(self):
        board = self._board
        board.set_position(0, 1, 2)
        board.set_position(1, 1, 1)

        next_row = board.next_available_row(1)
        self.assertEqual(next_row, 2)

        board.set_position(2, 1, 1)
        board.set_position(3, 1, 2)
        board.set_position(4, 1, 2)
        board.set_position(5, 1, 1)

        player1_moves = board.count(1)
        player2_moves = board.count(2)
        self.assertEqual(player1_moves, 3)
        self.assertEqual(player2_moves, 3)

        next_row = board.next_available_row(1)
        self.assertEqual(next_row, False)

        with self.assertRaises(ValidationException) as ve:
            next_row = board.next_available_row(-2)
        self.assertEqual(str(ve.exception), "Position outside the board!")

    def test_full_board(self):
        board = self._board
        board_full = board.is_board_full()
        self.assertEqual(board_full, False)
        for row in range(board.ROW_NR):
            for col in range(board.COL_NR):
                board.set_position(row, col, 1)
        col_3_valid = board.valid_location(3)
        self.assertEqual(col_3_valid, False)
        board_full = board.is_board_full()
        self.assertEqual(board_full, True)

    def test_won_board(self):
        board = self._board
        board1 = deepcopy(board)
        board2 = deepcopy(board)
        self.assertEqual(board.is_board_won(), False)

        board.set_position(2, 1, 2)
        board.set_position(3, 1, 2)
        board.set_position(4, 1, 2)
        board.set_position(5, 1, 2)
        self.assertEqual(board.is_board_won(), True)

        board.set_position(4, 3, 1)
        board.set_position(4, 4, 1)
        board.set_position(4, 5, 1)
        board.set_position(4, 6, 1)
        self.assertEqual(board.is_board_won(), True)

        board1.set_position(1, 3, 1)
        board1.set_position(2, 4, 1)
        board1.set_position(3, 5, 1)
        board1.set_position(4, 6, 1)
        self.assertEqual(board1.is_board_won(), True)

        board2.set_position(0, 3, 2)
        board2.set_position(1, 2, 2)
        board2.set_position(2, 1, 2)
        board2.set_position(3, 0, 2)
        self.assertEqual(board2.is_board_won(), True)

    def test_game(self):
        game = self._game
        board = game.board
        board1 = deepcopy(board)
        game1 = Game(board1, self._strategy)

        self.assertEqual(game.game_state(), GameState.ONGOING)

        board.set_position(4, 3, 1)
        board.set_position(4, 4, 1)
        board.set_position(4, 5, 1)
        board.set_position(4, 6, 1)
        self.assertEqual(game.game_state(), GameState.WON)

        for row in range(board1.ROW_NR-3):
            for col in range(board1.COL_NR):
                if col % 2 == 0:
                    board1.set_position(row, col, 1)
                else:
                    board1.set_position(row, col, 2)
        for row in range(3, board1.ROW_NR):
            for col in range(board1.COL_NR):
                if col % 2 == 0:
                    board1.set_position(row, col, 2)
                else:
                    board1.set_position(row, col, 1)
        self.assertEqual(game1.game_state(), GameState.DRAW)

    def test_random_strategy(self):
        game = self._game
        board = game.board

        for row in range(board.ROW_NR-3):
            for col in range(board.COL_NR):
                if col % 2 == 0:
                    board.set_position(row, col, 1)
                else:
                    board.set_position(row, col, 2)
        for row in range(3, board.ROW_NR):
            for col in range(0, board.COL_NR, 2):
                if col % 2 == 0:
                    board.set_position(row, col, 2)

        columns = game.computer_move(board)
        self.assertIn(columns, [(3, 1), (3, 3), (3, 5)])

    def test_move_win_strategy(self):
        strategy = MoveToWinStrategy()
        game = Game(self._board, strategy)
        board = game.board

        row, col = game.computer_move(board)
        self.assertEqual(row, 0)
        self.assertIn(col, [2, 3, 4])

        board.set_position(0, 0, 1)
        board.set_position(0, 1, 1)
        board.set_position(0, 2, 1)
        row, col = game.computer_move(board)
        board.set_position(row, col, 2)
        self.assertEqual(row, 0)
        self.assertEqual(col, 3)

        board.set_position(1, 3, 2)
        board.set_position(2, 3, 2)
        row, col = game.computer_move(board)
        board.set_position(row, col, 2)
        self.assertEqual(row, 3)
        self.assertEqual(col, 3)

    def test_intelligent_strategy(self):
        board = self._board
        strategy = MoveIntelligentStrategy()
        game = Game(board, strategy)

        row, col = game.computer_move(board)
        self.assertIn((row, col), [(0, 2), (0, 3), (0, 4), (1, 3)])

        game.board.set_position(0, 3, 1)
        game.board.set_position(0, 4, 2)

        game.board.set_position(0, 2, 1)
        row, col = game.computer_move(board)
        self.assertEqual(row, 0)
        self.assertEqual(col, 0)
        game.board.set_position(0, 0, 2)

        game.board.set_position(1, 3, 1)
        row, col = game.computer_move(board)
        self.assertEqual(row, 1)
        self.assertEqual(col, 2)
        game.board.set_position(1, 2, 2)

        game.board.set_position(0, 5, 1)
        row, col = game.computer_move(board)
        self.assertEqual(row, 2)
        self.assertEqual(col, 2)
        game.board.set_position(2, 2, 2)

        game.board.set_position(1, 4, 1)
        row, col = game.computer_move(board)
        self.assertEqual(row, 3)
        self.assertEqual(col, 2)
        game.board.set_position(3, 2, 2)

        game.board.set_position(1, 0, 1)
        row, col = game.computer_move(board)
        self.assertEqual(row, 4)
        self.assertEqual(col, 2)
        game.board.set_position(4, 2, 2)





