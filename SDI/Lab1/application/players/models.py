from django.db import models
'''
from pygments.lexers import get_all_lexers
from pygments.styles import get_all_styles

LEXERS = [item for item in get_all_lexers() if item[1]]
LANGUAGE_CHOICES = sorted([(item[1][0], item[0]) for item in LEXERS])
STYLE_CHOICES = sorted([(item, item) for item in get_all_styles()])
'''


# Create your models here.
class FootballPlayer(models.Model):
    # id = models.PositiveIntegerField(primary_key=True, editable=False)
    first_name = models.CharField(max_length=50, default='')
    last_name = models.CharField(max_length=50, default='')
    nationality = models.CharField(max_length=30)
    age = models.PositiveIntegerField()
    club = models.CharField(max_length=100)

    class Meta:
        ordering = ['last_name', 'first_name']