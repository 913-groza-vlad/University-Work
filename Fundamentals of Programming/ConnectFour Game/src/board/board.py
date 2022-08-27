
from src.board.exceptions import ValidationException


class Board:

    def __init__(self):
        """
        Constructor that initializes the game board
        and (all positions on the board have the value 0)
        and the number of rows and columns of the board
        """
        self.__board = [[0, 0, 0, 0, 0, 0, 0],
                        [0, 0, 0, 0, 0, 0, 0],
                        [0, 0, 0, 0, 0, 0, 0],
                        [0, 0, 0, 0, 0, 0, 0],
                        [0, 0, 0, 0, 0, 0, 0],
                        [0, 0, 0, 0, 0, 0, 0]]
        self.ROW_NR = 6
        self.COL_NR = 7

    def get_position(self, x, y):
        """
        Method (getter) that returns the element on position
        with indices x and y, or raises an exception
        if the position is not valid
        :param x: index of the row
        :param y: index of the column
        :return: the element on position (x, y)
        """
        if y < 0 or y > 6:
            raise ValidationException("Position outside the board!")
        return self.__board[x][y]

    def set_position(self, x, y, symbol):
        """
        Method (setter) that sets the value (1 or 2) on the position
        with indices x and y, otherwise it raises exceptions if the symbol
        or position are not valid or a symbol has already been set on that position
        :param x: index of the row
        :param y: index of the column
        :param symbol: 1 or 2 (symbol of the player/computer)
        """
        if symbol not in [1, 2]:
            raise ValidationException("Invalid symbol")
        if not (x in (0, 1, 2, 3, 4, 5)) or not (y in (0, 1, 2, 3, 4, 5, 6)):
            raise ValidationException("Cannot move outside of board!")
        if self.get_position(x, y) != 0:
            raise ValidationException("Cannot overwrite board!")
        self.__board[x][y] = symbol

    def next_available_row(self, col):
        """
        Method which returns the next available position on
        the column col or False if the column is already full
        :param col: a column
        :return: - row, if there are available pos
                 - False, otherwise
        """
        if col < 0 or col > 6:
            raise ValidationException("Position outside the board!")
        for row in range(self.ROW_NR):
            if self.__board[row][col] == 0:
                return row
        return False

    def valid_location(self, col):
        """
        Method that checks if a column is full of elements
        :param col: a column
        :return: True or False
        """
        return self.__board[5][col] == 0

    def is_board_full(self):
        """
        Method that returns True if there are available positions
        on the board or False if the board is full (there is no
        0 element on the board)
        :return: True or False
        """
        for row in range(self.ROW_NR):
            for col in range(self.COL_NR):
                if self.get_position(row, col) == 0:
                    return False
        return True

    def __str__(self):
        """
        Method which overwrites the 'str' method, converts
        the board to a string and returns it
        :return:
        """
        board = " "
        for i in range(7):
            board += ' -- '
        board += '\n'
        for row in range(self.ROW_NR - 1, -1, -1):
            board += ' | '
            for col in range(7):
                board = board + str(self.get_position(row, col)) + ' | '
            board += '\n '
            for col in range(self.COL_NR):
                board += ' -- '
            board += '\n'
        return board

    def count(self, symbol):
        """
        Method that counts the number of appearances of the symbol
        on the board
        :param symbol: 1 or 2 (symbol of the player/computer)
        :return: nr, number of a symbol
        """
        nr = 0
        for row in range(self.ROW_NR):
            for col in range(self.COL_NR):
                if self.get_position(row, col) == symbol:
                    nr += 1
        return nr

    def is_board_won(self):
        """
        Method that checks if the board contains a line which
        has 4 of the same element (1 - player or 2 - computer)
        in a row (which means the game is won)
        :return: True or False
        """

        # Check if the game is won on horizontal line
        for col in range(self.COL_NR - 3):
            for row in range(self.ROW_NR):
                if self.get_position(row, col) in [1, 2] and \
                        self.get_position(row, col) == self.get_position(row, col+1) == self.get_position(row, col+2) == self.get_position(row, col+3):
                    return True

        # Check if the game is won on vertical line
        for col in range(self.COL_NR):
            for row in range(self.ROW_NR - 3):
                if self.get_position(row, col) in [1, 2] and \
                        self.get_position(row, col) == self.get_position(row+1, col) == self.get_position(row+2, col) == self.get_position(row+3, col):
                    return True

        # Check if the game is won on diagonal (1)
        for col in range(self.COL_NR - 3):
            for row in range(self.ROW_NR - 3):
                if self.get_position(row, col) in [1, 2] and \
                        self.get_position(row, col) == self.get_position(row+1, col+1) == self.get_position(row+2, col+2) == self.get_position(row+3, col+3):
                    return True

        # Check if the game is won on diagonal (2)
        for col in range(self.COL_NR - 3):
            for row in range(3, self.ROW_NR):
               if self.get_position(row, col) in [1, 2] and \
                        self.get_position(row, col) == self.get_position(row-1, col+1) == self.get_position(row-2, col+2) == self.get_position(row-3, col+3):
                    return True

        return False


