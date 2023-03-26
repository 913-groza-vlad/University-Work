from rest_framework import serializers
from .models import FootballPlayer, FootballClub, Competition


class FootballPlayerSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballPlayer
        fields = "__all__"


class FootballClubSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballClub
        fields = "__all__"

class CompetitionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Competition
        field = "__all__"
