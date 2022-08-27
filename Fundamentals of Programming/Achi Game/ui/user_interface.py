from board.exception import ValidationException


class UI:

    def __init__(self, game):
        self._game = game

    @property
    def game(self):
        return self._game

    def print_board(self):
        board = self.game.board.draw_board().draw()
        print(board)

    def player_move(self):
        print("Make your move")
        ok = False
        while not ok:
            try:
                x = int(input("select row: "))
                y = int(input("select col: "))
                ok = True
            except ValueError:
                print("Invalid numerical value")

        self._game.board.set_position(x, y, 1)

    def player_movement(self):
        player_pieces = self.game.board.get_players_pieces()
        row, col = self.game.get_empty_pos()
        print("Make your move")
        ok = False
        while not ok:
            try:
                x = int(input("select row you want to move from: "))
                y = int(input("select col you want to move from: "))
                ok = True
            except ValueError:
                print("Invalid numerical value")

        if (x, y) not in player_pieces:
            raise ValidationException("invalid option!")
        if not self.game.adjacent_cells((x, y), (row, col)):
            raise ValidationException("Cannot move from non-adjacent cell")
        self.game.board.set_pos(x, y, 0)
        self.game.board.set_position(row, col, 1)

    def run_game(self):
        player_turn = True
        self.print_board()
        print("Placement phase begins\n")
        while not self.game.game_state():
            if player_turn:
                ok = False
                while not ok:
                    try:
                        self.player_move()
                        ok = True
                    except ValidationException as ve:
                        print(str(ve))

            if not player_turn:
                ok = False
                while not ok:
                    try:
                        row, col = self.game.computer_move()
                        self.game.board.set_position(row, col, 2)
                        print("Computer moves on (" + str(row) + ", " + str(col) + ")")
                        ok = True
                    except ValidationException as ve:
                        print(str(ve))
            self.print_board()
            player_turn = not player_turn
            if self.game.board.is_game_won():
                if player_turn:
                    print("Computer wins!")
                    return
                else:
                    print("Player wins")
                    return
        print("Movement phase begins\n")
        player_turn = True
        while not self.game.board.is_game_won():
            if player_turn:
                ok = False
                while not ok:
                    try:
                        self.player_movement()
                        ok = True
                    except ValidationException as ve:
                        print(str(ve))

            if not player_turn:
                ok = False
                while not ok:
                    try:
                        rowe, cole = self.game.get_empty_pos()
                        row, col = self.game.computer_movement()
                        self.game.board.set_pos(row, col, 0)
                        self.game.board.set_position(rowe, cole, 2)
                        print("Computer moves from (" + str(row) + ", " + str(col) + ")")
                        ok = True
                    except ValidationException as ve:
                        print(str(ve))
            self.print_board()
            player_turn = not player_turn
            if self.game.board.is_game_won():
                if player_turn:
                    print("Computer wins!")
                    return
                else:
                    print("Player wins")
                    return


