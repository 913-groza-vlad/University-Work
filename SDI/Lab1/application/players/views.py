from django.shortcuts import render
from rest_framework.decorators import api_view
from rest_framework import status
from rest_framework.response import Response
from .models import FootballPlayer
from .serializers import FootballPlayerSerializer


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