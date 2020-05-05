from django.db import models
from django.contrib.auth.models import User as DjangoUser


class GenericModel(models.Model):
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True


class User(GenericModel, DjangoUser):
    score = models.IntegerField(default=0)

    def __str__(self):
        return self.username


class TBase(GenericModel):
    score = models.IntegerField(default=0)
    users = models.ManyToManyField(User)
    created_by = models.ForeignKey(User, null=True, on_delete=models.SET_NULL, related_name='created_by')
    owned_by = models.ForeignKey(User, null=True, on_delete=models.SET_NULL, related_name='owned_by')
    image_url = models.URLField(max_length=255, default='https://source.unsplash.com/random/1000x1000')
