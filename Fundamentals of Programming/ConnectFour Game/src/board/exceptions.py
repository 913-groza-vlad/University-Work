class ValidationException(Exception):
    """
    Exception Class for validating the position on the board
    """
    def __init__(self, message):
        self._message = message

    def __str__(self):
        return self._message

