import unittest
from structure import *


class MyTestCase(unittest.TestCase):

    def setUp(self) -> None:
        """
        Runs before every test method
        """
        self._data = Data()

    def tearDown(self) -> None:
        """
        Runs after every test method
        """
        pass

    def test_gnome_sort(self):
        l = [2, 8, 1, 6, 4, 3]
        self.assertEqual(l, [2, 8, 1, 6, 4, 3])
        sorted_l = self._data.gnomeSort(l, compare)
        self.assertEqual(sorted_l, [1, 2, 3, 4, 6, 8])
        a = [12, 95, 7, 12, 910, 4, -5, 2]
        sorted_a = self._data.gnomeSort(a, compare)
        self.assertEqual(sorted_a, [-5, 2, 4, 7, 12, 12, 95, 910])

    def test_structure(self):
        l = Data()
        l.add(2)
        l.add(7)
        l.add(-3)
        l.add(19)
        l.add(12)
        self.assertEqual(len(l), 5)

        del l[2]
        self.assertEqual(len(l), 4)
        self.assertEqual(l[2], 19)

        with self.assertRaises(IndexError) as ie:
            del l[23]
        self.assertEqual(str(ie.exception), "Invalid index!")

        for el in l:
            el = 2
            p = el
            self.assertEqual(el, 2)
            self.assertEqual(p, el)

        for i in range(len(l)):
            l[i] = 19
            self.assertEqual(l[i], 19)

        with self.assertRaises(IndexError) as ie:
            elem = l[23]
        self.assertEqual(str(ie.exception), "Invalid index!")

        with self.assertRaises(IndexError) as ie:
            l[23] = 12
        self.assertEqual(str(ie.exception), "Invalid index!")

    def test_filter(self):
        l = Data()
        l.add(12)
        l.add(-2)
        l.add(-7)
        l.add(3)
        l.add(14)

        def accept(el):
            if el < 0:
                return True
            return False

        filtered = self._data.filter(l, accept)
        self.assertEqual(filtered, [-2, -7])