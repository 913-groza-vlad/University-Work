from random import shuffle, choice


class Game:

    def __init__(self, board):
        self._board = board

    @property
    def board(self):
        """
        Property of the instance of class Game
        :return: self._board
        """
        return self._board

    def game_state(self):
        """
        Method that checks if the game is finished or not
        :return: True or false
        """
        if self.board.is_game_won() or self._board.is_board_full():
            return True
        return False

    def board_almost_won(self):
        """
        Method that helps the computer to block
        player from winning
        :return: False if player can't win
                otherwise, position to be blocked
        """
        for row in range(self.board.ROW_NR):
            if self.board.get_position(row, 0) == self.board.get_position(row, 1) == 1 and self.board.get_position(row, 2) == 0:
                return row, 2
            if self.board.get_position(row, 0) == self.board.get_position(row, 2) == 1 and self.board.get_position(row, 1) == 0:
                return row, 1
            if self.board.get_position(row, 1) == self.board.get_position(row, 2) == 1 and self.board.get_position(row, 0) == 0:
                return row, 0
        for col in range(self.board.COL_NR):
            if self.board.get_position(0, col) == self.board.get_position(1, col) == 1 and self.board.get_position(2, col) == 0:
                return 2, col
            if self.board.get_position(0, col) == self.board.get_position(2, col) == 1 and self.board.get_position(1, col) == 0:
                return 1, col
            if self.board.get_position(1, col) == self.board.get_position(2, col) == 1 and self.board.get_position(0, col) == 0:
                return 0, col
        #check almost won on diagonals
        if self.board.get_position(0, 0) == self.board.get_position(1, 1) == 1 and self.board.get_position(2, 2) == 0:
            return 2, 2
        if self.board.get_position(1, 1) == self.board.get_position(2, 2) == 1 and self.board.get_position(0, 0) == 0:
            return 0, 0
        if self.board.get_position(2, 2) == self.board.get_position(0, 0) == 1 and self.board.get_position(1, 1) == 0:
            return 1, 1
        if self.board.get_position(0, 2) == self.board.get_position(1, 1) == 1 and self.board.get_position(2, 0) == 0:
            return 2, 0
        if self.board.get_position(0, 2) == self.board.get_position(2, 0) == 1 and self.board.get_position(1, 1) == 0:
            return 1, 1
        if self.board.get_position(2, 0) == self.board.get_position(1, 1) == 1 and self.board.get_position(0, 2) == 0:
            return 0, 2

        return False

    def computer_move(self):
        """
        Method which returns the next move of the computer
        :return:
        """
        possible_moves = []
        if self.board_almost_won() is False:
            for row in range(self.board.ROW_NR):
                for col in range(self.board.COL_NR):
                    if self.board.get_position(row, col) == 0:
                        possible_moves.append((row, col))
            shuffle(possible_moves)
            return possible_moves[0]
        return self.board_almost_won()

    def adjacent_cells(self, cell1, cell2):
        if (cell1[0] + 1 == cell2[0] and cell1[1] + 1 == cell2[1]) or (cell1[0] + 1 == cell2[0] and cell1[1] == cell2[1]) or  (cell1[0] + 1 == cell2[0] and cell1[1] -1 == cell2[1]) or (cell1[0] == cell2[0] and cell1[1] + 1 == cell2[1]) or (cell1[0] == cell2[0] and cell1[1] - 1 == cell2[1]) or  (cell1[0] - 1 == cell2[0] and cell1[1] + 1 == cell2[1]) or (cell1[0] - 1 == cell2[0] and cell1[1] == cell2[1]) or (cell1[0] - 1 == cell2[0] and cell1[1] - 1 == cell2[1]):
            return True
        return

    def computer_movement(self):
        computer_pieces = self.board.get_computer_pieces()
        empty_position = self.get_empty_pos()
        position = choice(computer_pieces)
        while not self.adjacent_cells(position, empty_position):
            position = choice(computer_pieces)
        return position

    def get_empty_pos(self):
        row = 0
        col = 0
        for i in range(self.board.ROW_NR):
            for j in range(self.board.COL_NR):
                if self.board.get_position(i, j) == 0:
                    row = i
                    col = j
        return row, col





