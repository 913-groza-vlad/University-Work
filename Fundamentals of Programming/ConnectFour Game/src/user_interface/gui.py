
import pygame
import random
import sys
import math
from src.game.services import GameState
from src.board.exceptions import ValidationException


pygame.init()


class GameGUI:

    def __init__(self, game):
        pygame.init()
        self.__game = game

    @property
    def board(self):
        return self.__game.board

    def create_board(self):
        for col in range(self.board.COL_NR):
            for row in range(self.board.ROW_NR):
                pygame.draw.rect(Display.SCREEN, Display.BLUE, (col * Display.SQUARE, row * Display.SQUARE + Display.SQUARE, Display.SQUARE, Display.SQUARE))
                pygame.draw.circle(Display.SCREEN, Display.WHITE, (int(col * Display.SQUARE+Display.SQUARE/2), int(row * Display.SQUARE+Display.SQUARE+Display.SQUARE/2)), Display.RADIUS)

        for col in range(self.board.COL_NR):
            for row in range(self.board.ROW_NR):
                if self.board.get_position(row, col) == 1:
                    pygame.draw.circle(Display.SCREEN, Display.RED, (int(col * Display.SQUARE + Display.SQUARE / 2), Display.HEIGHT + Display.SQUARE - int(row * Display.SQUARE + Display.SQUARE + Display.SQUARE / 2)), Display.RADIUS + 1)
                if self.board.get_position(row, col) == 2:
                    pygame.draw.circle(Display.SCREEN, Display.YELLOW, (int(col * Display.SQUARE + Display.SQUARE / 2), Display.HEIGHT + Display.SQUARE - int(row * Display.SQUARE + Display.SQUARE + Display.SQUARE / 2)), Display.RADIUS + 1)
        pygame.display.update()

    def start(self):
        self.create_board()
        message = Display.font1.render("   Connect Four ", 1, Display.COLOR)
        Display.SCREEN.blit(message, (15, 7))
        pygame.display.update()

        first_move = [True, False]
        human_turn = random.choice(first_move)
        while self.__game.game_state() == GameState.ONGOING:

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    sys.exit()

                pygame.draw.rect(Display.SCREEN, Display.BLACK, (0, 0, Display.WIDTH, Display.SQUARE))
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if human_turn:
                        try:
                            pos = event.pos[0]
                            col = int(math.floor(pos / Display.SQUARE))
                            row = self.__game.board.next_available_row(col)
                            self.__game.board.set_position(row, col, 1)
                            human_turn = not human_turn
                            self.create_board()
                        except ValidationException as ve:
                            print(ve)

                if not human_turn and self.__game.game_state() == GameState.ONGOING:
                    try:
                        row, col = self.__game.computer_move(self.__game.board)
                        self.__game.board.set_position(row, col, 2)
                        human_turn = not human_turn
                        pygame.time.wait(500)
                        self.create_board()
                    except ValidationException as ve:
                        print(ve)

                if self.__game.game_state() == GameState.DRAW:
                    message = Display.font.render(" It's a draw!", 2, Display.WHITE)
                    Display.SCREEN.blit(message, (30, 10))
                    self.create_board()

                if self.__game.game_state() == GameState.WON:
                    if not human_turn:
                        message = Display.font.render(" Player wins!", 2,  Display.RED)
                        Display.SCREEN.blit(message, (30, 10))
                    else:
                        message = Display.font.render("Computer wins!", 2, Display.YELLOW)
                        Display.SCREEN.blit(message, (30, 10))
                    self.create_board()

                if self.__game.game_state() is not GameState.ONGOING:
                    pygame.time.wait(5000)


class Display:
    BLUE = (0, 120, 250)
    WHITE = (255, 255, 255)
    RED = (252, 0, 0)
    YELLOW = (230, 255, 0)
    BLACK = (0, 0, 0)
    COLOR = (43, 242, 21)

    SQUARE = 75
    WIDTH = 7 * SQUARE
    HEIGHT = 7 * SQUARE

    SIZE = (WIDTH, HEIGHT)
    RADIUS = int(SQUARE / 2 - 10)
    SCREEN = pygame.display.set_mode(SIZE)

    font = pygame.font.SysFont("monospace", 50, "bold")
    font1 = pygame.font.SysFont("monospace", 40, "bold")
