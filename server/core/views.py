from django.shortcuts import get_object_or_404, render
from rest_framework import viewsets, permissions
from rest_framework.response import Response

from core.models import TBase, User
from core.serializers import TBaseSerializer, UserSerializer


class ProfileViewSet(viewsets.ReadOnlyModelViewSet):
    """
    Public end-points to get information about any user
    NOTE: All end-points provided here is read-only
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = [permissions.AllowAny]
    lookup_field = 'user_id'
    lookup_url_kwarg = 'pk'

class OwnProfileViewSet(viewsets.ModelViewSet):
    """
    End-points to get all details about logged in user
    and update the profile of logged in user
    """
    queryset = User.objects.all()
    permission_classes = [permissions.IsAuthenticated]
    serializer_class = UserSerializer


class TBaseViewSet(viewsets.ModelViewSet):
    """
    A simple ViewSet for listing or retrieving bases.
    """
    queryset = TBase.objects.all()
    serializer_class = TBaseSerializer
