from rest_framework import serializers

class UserSerializer(serializers.Serializer):
    name = serializers.CharField(required=True, allow_blank=False, max_length=255)
    score = serializers.DecimalField(max_digits=9, decimal_places=4)

class BaseSerializer(serializers.Serializer):
    name = serializers.CharField(required=True, allow_blank=False, max_length=255)
    score = serializers.DecimalField(max_digits=9, decimal_places=4)
    users = UserSerializer(many=True)
    created_at = serializers.DateTimeField(allow_null=True, required=False)
    updated_at = serializers.DateTimeField(allow_null=True, required=False)
