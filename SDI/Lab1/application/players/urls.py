from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from .views import player_detail, player_list

urlpatterns = [
    path('players/', player_list),
    path('players/<int:pk>/', player_detail),
]

urlpatterns = format_suffix_patterns(urlpatterns)