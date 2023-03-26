from rest_framework import serializers
from .models import FootballPlayer


class FootballPlayerSerializer(serializers.ModelSerializer):
    class Meta:
        model = FootballPlayer
        fields = "__all__"
