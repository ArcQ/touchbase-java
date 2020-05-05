from django.shortcuts import render
from core.models import TBase
from django.shortcuts import get_object_or_404
from core.serializers import TBaseSerializer
from rest_framework import viewsets
from rest_framework.response import Response


# Create your views here.

class TBaseViewSet(viewsets.ModelViewSet):
    """
    A simple ViewSet for listing or retrieving bases.
    """
    queryset = TBase.objects.all()
    serializer_class = TBaseSerializer
