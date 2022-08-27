
class Data:

    def __init__(self):
        self._data = []

    def __iter__(self):
        self._poz = 0
        return self

    def __next__(self):
        if self._poz >= len(self._data):
            raise StopIteration()
        self._poz += 1
        return self._data[self._poz-1]

    def __setitem__(self, key, value):
        if key >= len(self._data):
            raise IndexError("Invalid index!")
        self._data[key] = value

    def __getitem__(self, key):
        if key >= len(self._data):
            raise IndexError("Invalid index!")
        return self._data[key]

    def __delitem__(self, key):
        if key >= len(self._data):
            raise IndexError("Invalid index!")
        del self._data[key]

    def __len__(self):
        return len(self._data)

    def add(self, element):
        self._data.append(element)

    def gnomeSort(self, l, compare):
        i = 0
        length = len(l)
        while i < length:
            if i == 0 or compare(l[i], l[i - 1]):
                i += 1
            else:
                l[i - 1], l[i] = l[i], l[i - 1]
                i -= 1
        return l

    def filter(self, l, accept):
        result = []
        for element in l:
            if accept(element):
                result.append(element)
        return result


def compare(x, y):
    return x >= y


