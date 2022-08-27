class ValidationException(Exception):
    def __init__(self, message):
        self._message = message

    def __str__(self):
        return self._message


class RepositoryException(Exception):
    def __init__(self, message):
        self._message = message

    def __str__(self):
        return self._message


class UndoRedoException(Exception):
    def __init__(self, message):
        self._message = message

    def __str__(self):
        return self._message