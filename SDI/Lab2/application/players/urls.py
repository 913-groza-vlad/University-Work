from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from .views import player_detail, player_list, club_detail, club_list, PlayersWithAge

urlpatterns = [
    path('players/', player_list),
    path('players/<int:pk>/', player_detail),
    path('clubs/', club_list),
    path('clubs/<int:pk>/', club_detail),
    path('players/filter-age/', PlayersWithAge.as_view())
]

urlpatterns = format_suffix_patterns(urlpatterns)
