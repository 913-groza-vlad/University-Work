from django.shortcuts import render
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.response import Response
from rest_framework import generics
from .models import FootballPlayer, FootballClub
from .serializers import FootballPlayerSerializer, FootballClubSerializer
from django_filters import rest_framework as filters


@api_view(['GET', 'POST'])
def player_list(request, format=None):
    '''
    List all the players / create a new player
    '''
    if request.method == 'GET':
        players = FootballPlayer.objects.all()
        serializer = FootballPlayerSerializer(players, many=True)
        return Response(serializer.data)
    elif request.method == 'POST':
        serializer = FootballPlayerSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'DELETE'])
def player_detail(request, pk, format=None):
    '''
    Get/Update/Delete a football player
    '''
    try:
        player = FootballPlayer.objects.get(pk=pk)
    except FootballPlayer.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = FootballPlayerSerializer(player)
        return Response(serializer.data)
    elif request.method == 'PUT':
        serializer = FootballPlayerSerializer(player, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    elif request.method == 'DELETE':
        player.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


@api_view(['GET', 'POST'])
def club_list(request, format=None):
    '''
    List all the football clubs / add a new club
    '''
    if request.method == 'GET':
        clubs = FootballClub.objects.all()
        serializer = FootballClubSerializer(clubs, many=True)
        return Response(serializer.data)
    elif request.method == 'POST':
        serializer = FootballClubSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'DELETE'])
def club_detail(request, pk, format=None):
    '''
    Get/Update/Delete a football club
    '''
    try:
        club = FootballClub.objects.get(pk=pk)
    except FootballClub.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = FootballClubSerializer(club)
        return Response(serializer.data)
    elif request.method == 'PUT':
        serializer = FootballClubSerializer(club, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    elif request.method == 'DELETE':
        club.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


class FootballPlayerFilter(filters.FilterSet):
    age = filters.NumberFilter(field_name='age', lookup_expr='gt')

    class Meta:
        model = FootballPlayer
        fields = ['age']


class PlayersWithAge(generics.ListAPIView):
    serializer_class = FootballPlayerSerializer
    filter_backends = [DjangoFilterBackend]
    queryset = FootballPlayer.objects.all()
    filterset_class = FootballPlayerFilter

    def get_queryset(self):
        return self.queryset

