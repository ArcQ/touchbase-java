from django.db import models


class GenericModel(models.Model):
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True


class User(models.Model):
    name = models.CharField(max_length=255)
    score = models.IntegerField(default=0)

    def __str__(self):
        return self.name


class Base(GenericModel):
    name = models.CharField(max_length=255)
    score = models.IntegerField(default=0)
    users = models.ManyToManyField(User)


class Message(GenericModel):
    content = models.CharField(max_length=255)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    base = models.ForeignKey(Base, on_delete=models.CASCADE)
