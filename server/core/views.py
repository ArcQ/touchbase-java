from django.shortcuts import render
from core.models import Base
from django.shortcuts import get_object_or_404
from core.serializers import BaseSerializer
from rest_framework import viewsets
from rest_framework.response import Response


# Create your views here.

class BaseViewSet(viewsets.ModelViewSet):
    """
    A simple ViewSet for listing or retrieving bases.
    """
    queryset = Base.objects.all()
    serializer_class = BaseSerializer
