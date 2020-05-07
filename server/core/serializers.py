from rest_framework import serializers
from core.models import TBase, User


class BaseSerializer(serializers.ModelSerializer):
    created_at       = serializers.SerializerMethodField()
    updated_at      = serializers.SerializerMethodField()

    class Meta:
        fields = ('created_at, updated_at')


class UserSerializer(BaseSerializer):

    username = serializers.CharField(required=True, allow_blank=False, max_length=30)
    first_name = serializers.CharField(required=True, allow_blank=False, max_length=30)
    last_name = serializers.CharField(required=True, allow_blank=False, max_length=150)
    email = serializers.EmailField(required=True, allow_blank=False, max_length=150)
    score = serializers.DecimalField(max_digits=9, decimal_places=4)
    password = serializers.CharField(required=False, write_only=True, help_text=('Write-only field used to change the password.'))

    class Meta:
        model = User
        fields = ('username', 'first_name', 'last_name', 'email', 'score', 'password')
        extra_kwargs = {
            'last_login': {'read_only': True}
        }


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


