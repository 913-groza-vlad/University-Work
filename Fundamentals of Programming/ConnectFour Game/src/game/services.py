import random
import math
from enum import Enum
from random import shuffle
from copy import deepcopy


class Game:

    def __init__(self, board, strategy):
        """
        Constructor for the game, consisting of the board and a strategy
        :param board: the Connect4 game board
        :param strategy: a strategy of computer player
        """
        self._board = board
        self._strategy = strategy

    @property
    def board(self):
        """
        Property for the board
        :return: the board
        """
        return self._board

    def game_state(self):
        """
        Method that returns the state of the game.
        The game can be ongoing or finished (draw or win)
        """
        if self.board.is_board_won():
            return GameState.WON
        if self.board.is_board_full():
            return GameState.DRAW
        return GameState.ONGOING

    def computer_move(self, board):
        """
        Method that returns the position computer
        picks to move its symbol
        :param board: the board of Connect4 game
        :return: move performed by computer
        """
        return self._strategy.move(board)


class GameState(Enum):
    """
    Class for enumerating the state of the game
    (ongoing, draw or won)
    """
    ONGOING = 0
    DRAW = 1
    WON = 2


class MoveRandomStrategy:

    def move(self, board):
        """
        The computer moves in a random, but valid square.
        :param board: the board of Connect four
        :return: a row and a column of the board / an available position on the board
        """
        choices = []
        for col in range(board.COL_NR):
            row = board.next_available_row(col)
            if row is not False:
                if board.get_position(row, col) == 0:
                    choices.append((row, col))
        shuffle(choices)
        return choices[0]


class MoveToWinStrategy:

    def move(self, board):
        """
        Method that returns the best move which computer
        can perform
        :param board: the board of Connect four game
        :return:
        """
        return self.best_move(board)

    def score_on_line(self, lineOF4):
        """
        Method that computes the score on a line, depending
        of what elements are on that line
        :param lineOF4: a line consisting of 4 elements
        :return: the score on a line
        """
        score = 0
        if lineOF4.count(2) == 4:
            score += 500
        if lineOF4.count(2) == 3 and lineOF4.count(0) == 1:
            score += 30
        if lineOF4.count(2) == 2 and lineOF4.count(0) == 2:
            score += 10
        if lineOF4.count(1) == 3 and lineOF4.count(0) == 1:
            score -= 75

        return score

    def score(self, board):
        """
        Method that computes the total score of the board
        by calculating the score on every line of four
        :param board: the board of Connect four game
        :return: the score
        """
        score = 0

        # try to win on row
        for row in range(board.ROW_NR):
            row_symbols = []
            for i in range(board.COL_NR):
                row_symbols.append(board.get_position(row, i))
            for col in range(board.COL_NR-3):
                lineOF4 = []
                for k in range(col, col+4):
                    lineOF4.append(row_symbols[k])
                score += self.score_on_line(lineOF4)

        # try to win on column
        for col in range(board.COL_NR):
            col_symbols = []
            for i in range(board.ROW_NR):
                col_symbols.append(board.get_position(i, col))
            for row in range(board.ROW_NR-3):
                lineOF4 = []
                for k in range(row, row+4):
                    lineOF4.append(col_symbols[k])
                score += self.score_on_line(lineOF4)

        # try to win on diagonals
        for row in range(board.ROW_NR-3):
            for col in range(board.COL_NR-3):
                lineOF4 = []
                for i in range(4):
                    lineOF4.append(board.get_position(row+i, col+i))
                score += self.score_on_line(lineOF4)

        for row in range(3, board.ROW_NR):
            for col in range(board.COL_NR-3):
                lineOF4 = []
                for i in range(4):
                    lineOF4.append(board.get_position(row-i, col+i))
                score += self.score_on_line(lineOF4)

        return score

    def valid_locations(self, board):
        """
        Method that returns all the valid/possible locations in
        which a move can be performed
        :param board: the board of Connect four game
        :return: the available locations
        """
        valid_loc = []
        for col in range(board.COL_NR):
            if board.valid_location(col):
                valid_loc.append(col)
        return valid_loc

    def best_move(self, board):
        """
        Method that computes the score of the board for
        every possible (next) move and returns the position (row, col)
        of the board with the highest score
        :param board: the board of Connect four
        :return: row, col (the location to perform the move on)
        """
        equal_scores = []
        valid_loc = self.valid_locations(board)
        best_score = -100000
        picked_col = random.choice(valid_loc)
        picked_row = board.next_available_row(picked_col)
        for col in valid_loc:
            row = board.next_available_row(col)
            possible_board = deepcopy(board)
            possible_board.set_position(row, col, 2)
            score = self.score(possible_board)
            if score > best_score:
                best_score = score
                picked_col = col
                picked_row = row
            if score == best_score and board.count(2) == 0:
                equal_scores.append((row, col))
            if score == best_score and board.count(2) == 0 and board.count(1) == 0:
                picked_col = random.choice([2, 3, 4])
                picked_row = 0
        if len(equal_scores) > 0:
            for pos in range(len(equal_scores)):
                if 0 < equal_scores[pos][1] < 6 and (board.get_position(0, equal_scores[pos][1] - 1) == 1 or board.get_position(0, equal_scores[pos][1] + 1) == 1):
                    picked_row, picked_col = equal_scores[pos]

        return picked_row, picked_col


class MoveIntelligentStrategy(MoveToWinStrategy):

    def move(self, board):
        """
        Method that returns the move computer performs
        :param board: the board of Connect four game
        :return: row, col - position on the board
        """
        row, col, val = self.minimax(board, 4, True)
        return row, col

    def valid_locations(self, board):
        return MoveToWinStrategy.valid_locations(self, board)

    def score(self, board):
        return MoveToWinStrategy.score(self, board)

    def is_terminal_node(self, board):
        """
        Method that returns True if the game is won or full
        or False otherwise
        :param board: the board of Connect four game
        :return: True or False
        """
        return board.is_board_won() or board.is_board_full()

    def winning_move(self, board, symbol):
        """
        Method that checks if the board contains a line which
        has 4 of the same symbol in a row (which means the game is won)
        :return: True or False
        :param board: the board of Connect four game
        :param symbol: 1 or 2
        :return: True or False
        """
        for col in range(board.COL_NR-3):
            for row in range(board.ROW_NR):
                if board.get_position(row, col) == board.get_position(row, col+1) == board.get_position(row, col+2) == board.get_position(row, col+3) == symbol:
                    return True

        for col in range(board.COL_NR):
            for row in range(board.ROW_NR-3):
                if board.get_position(row, col) == board.get_position(row+1, col) == board.get_position(row+2, col) == board.get_position(row+3, col) == symbol:
                    return True

        for col in range(board.COL_NR-3):
            for row in range(board.ROW_NR-3):
                if board.get_position(row, col) == board.get_position(row+1, col+1) == board.get_position(row+2, col+2) == board.get_position(row+3, col+3) == symbol:
                    return True

        for col in range(board.COL_NR-3):
            for row in range(3, board.ROW_NR):
                if board.get_position(row, col) == board.get_position(row-1, col+1) == board.get_position(row-2, col+2) == board.get_position(row-3, col+3) == symbol:
                    return True
        return False

    def minimax(self, board, depth, maximizingPlayer):
        """
        The minimax algorithm - a recursive algorithm for choosing
        the next move in an n-player game
        A value is associated with each position or state of the game.
        This value is computed by means of a position evaluation function and
        it indicates how good it would be for a player to reach that position.
        :param board: the board of Connect four
        :param depth: the number of levels of the tree with nodes
        :param maximizingPlayer: True - player move, False - computer move
        :return: row, col, value
        """
        valid_loc = self.valid_locations(board)
        terminal_node = self.is_terminal_node(board)
        if depth == 0 or terminal_node:
            if terminal_node:
                if self.winning_move(board, 2):
                    return None, None, math.inf
                elif self.winning_move(board, 1):
                    return None, None, -math.inf
                else:
                    return None, None, 0
            else:
                return None, None, self.score(board)
        if maximizingPlayer:
            value = -math.inf
            picked_column = random.choice(valid_loc)
            picked_row = board.next_available_row(picked_column)
            for col in valid_loc:
                row = board.next_available_row(col)
                possible_board = deepcopy(board)
                possible_board.set_position(row, col, 2)
                score = self.minimax(possible_board, depth-1, False)[2]
                if score > value:
                    value = score
                    picked_column = col
                    picked_row = row
                if board.count(2) == 0 and (board.count(1) == 0 or board.count(1) == 1):
                    picked_column = random.choice([2, 3, 4])
                    picked_row = board.next_available_row(picked_column)
            return picked_row, picked_column, value
        else:
            value = math.inf
            picked_column = random.choice(valid_loc)
            picked_row = board.next_available_row(picked_column)
            for col in valid_loc:
                row = board.next_available_row(col)
                possible_board = deepcopy(board)
                possible_board.set_position(row, col, 1)
                score = self.minimax(possible_board, depth-1, True)[2]
                if score < value:
                    value = score
                    picked_column = col
                    picked_row = row
            return picked_row, picked_column, value


