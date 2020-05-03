from rest_framework import serializers
from core.models import TBase

class UserSerializer(serializers.Serializer):
    name = serializers.CharField(required=True, allow_blank=False, max_length=255)
    score = serializers.DecimalField(max_digits=9, decimal_places=4)

class TBaseSerializer(serializers.ModelSerializer):
    name = serializers.CharField(required=True, allow_blank=False, max_length=255)
    score = serializers.DecimalField(max_digits=9, decimal_places=4)
    users = UserSerializer(many=True)
    created_at = serializers.DateTimeField(allow_null=True, required=False, read_only=True)
    updated_at = serializers.DateTimeField(allow_null=True, required=False, read_only=True)

    class Meta:
        model = TBase
        fields = ("name", "score", "users", "created_at", "updated_at")
        read_only_fields = ("created_at", "update_at")

    def create(self, validated_data):
        users = validated_data.pop('users')
        album = Album.objects.create(**validated_data)
        for track_data in tracks_data:
            Track.objects.create(album=album, **track_data)
        return album

