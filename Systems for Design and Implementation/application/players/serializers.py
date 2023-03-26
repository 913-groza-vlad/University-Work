from django.core.validators import MinValueValidator, MaxValueValidator
from rest_framework import serializers
from .models import FootballPlayer, FootballClub, Competition, Record


class FootballPlayerSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballPlayer
        fields = "__all__"
        depth = 1


class FootballClubSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballClub
        fields = ['id', 'name', 'establishment_year', 'country', 'city', 'budget', 'home_kit']


class ClubPlayersSerializer(serializers.ModelSerializer):
    players = FootballPlayerSerializer(many=True, read_only=True)

    class Meta:
        model = FootballClub
        fields = ['id', 'name', 'establishment_year', 'country', 'city', 'budget', 'home_kit', 'players']

    def create(self, validated_data):
        players_data = validated_data.pop('club_players')
        club = FootballClub.objects.create(**validated_data)
        for player in players_data:
            FootballPlayer.objects.create(club=club, **player)
        return club


class ClubCompetitionsSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballClub
        fields = '__all__'
        depth = 1


class CompetitionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Competition
        fields = "__all__"


class RecordSerializer(serializers.ModelSerializer):
    class Meta:
        model = Record
        fields = "__all__"
        depth = 1


class ClubRecordSerializer(serializers.ModelSerializer):
    name = serializers.CharField(max_length=100)
    total_trophies = serializers.IntegerField(read_only=True)

    class Meta:
        model = FootballClub
        fields = ('id', 'name', 'total_trophies')


class ClubPlayersAgeSerializer(serializers.ModelSerializer):
    name = serializers.CharField(max_length=100)
    establishment_year = serializers.IntegerField(validators=[MinValueValidator(1850), MaxValueValidator(2023)])
    country = serializers.CharField(max_length=30)
    average_age = serializers.FloatField(read_only=True)

    class Meta:
        model = FootballClub
        fields = ('id', 'name', 'establishment_year', 'country', 'average_age')