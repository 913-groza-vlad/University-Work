from board.exception import ValidationException
from texttable import Texttable


class Board:
    def __init__(self):
        """
        Constructor which initialize the board
        and keep the value of row and column number
        """
        self.ROW_NR = 3
        self.COL_NR = 3
        self._board = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]

    def get_position(self, x, y):
        """
        Get the element on position x and y
        :param x: integer between 0 and 2
        :param y: integer between 0 and 2
        :return: board[x][y]
                raise ValidationException otherwise
        """
        if x < 0 or x > 2:
            raise ValidationException("Position outside the board!")
        if y < 0 or y > 2:
            raise ValidationException("Position outside the board!")
        return self._board[x][y]

    def set_position(self, x, y, symbol):
        """
        Get the element on position x and y
        :param x: integer between 0 and 2
        :param y: integer between 0 and 2
        :return: change the value of board[x][y]
                raise ValidationException otherwise
        :param symbol: 0, 1, or 2
        """
        if x < 0 or x > 2:
            raise ValidationException("Position outside the board!")
        if y < 0 or y > 2:
            raise ValidationException("Position outside the board!")
        if symbol not in [0, 1, 2]:
            raise ValidationException("Invalid symbol")
        if self.get_position(x, y) in [1, 2]:
            raise ValidationException("Position already occupied!")
        self._board[x][y] = symbol

    def set_pos(self, x, y, symbol):
        if x < 0 or x > 2:
            raise ValidationException("Position outside the board!")
        if y < 0 or y > 2:
            raise ValidationException("Position outside the board!")
        if symbol not in [0, 1, 2]:
            raise ValidationException("Invalid symbol")
        self._board[x][y] = symbol

    def draw_board(self):
        """
        Method which draws the board using Texttable
        :return: the board
        """
        board = Texttable()
        for row in range(self.ROW_NR):
            first_el = second_el = third_el = ''
            if self._board[row][0] == 1:
                first_el = 'X'
            if self._board[row][0] == 2:
                first_el = 'o'
            if self._board[row][1] == 1:
                second_el = 'X'
            if self._board[row][1] == 2:
                second_el = 'o'
            if self._board[row][2] == 1:
                third_el = 'X'
            if self._board[row][2] == 2:
                third_el = 'o'
            board.add_row([first_el, second_el, third_el])
        return board

    def is_game_won(self):
        """
        Method that checks if one of
        the players won
        :return: True or False
        """
        #check if game on row
        for row in range(self.ROW_NR):
            if self._board[row][0] in [1, 2] and self._board[row][0] == self._board[row][1] == self._board[row][2]:
                return True

        #check if game won on column
        for col in range(self.COL_NR):
            if self._board[0][col] in [1, 2] and self._board[0][col] == self._board[1][col] == self._board[2][col]:
                return True

        #check if game on diagonals
        if self._board[0][0] in [1, 2] and self._board[0][0] == self._board[1][1] == self._board[2][2]:
            return True
        if self._board[0][2] in [1, 2] and self._board[0][2] == self._board[1][1] == self._board[2][0]:
            return True

        return False

    def is_board_full(self):
        """
        Method that checks if all moves from placement phase
        were performed
        :return: True or False
        """
        cont = 0
        for i in range(self.ROW_NR):
            for j in range(self.COL_NR):
                if self._board[i][j] != 0:
                    cont += 1
        if cont == 8:
            return True
        return False

    def get_players_pieces(self):
        positions = []
        for i in range(self.ROW_NR):
            for j in range(self.COL_NR):
                if self._board[i][j] == 1:
                    positions.append((i, j))
        return positions

    def get_computer_pieces(self):
        positions = []
        for i in range(self.ROW_NR):
            for j in range(self.COL_NR):
                if self._board[i][j] == 2:
                    positions.append((i, j))
        return positions

