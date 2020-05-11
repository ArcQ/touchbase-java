from py2neo.ogm import GraphObject, Property, RelatedTo
from settings import graph

class BasicModel(GraphObject):
    """
    BasicModel to inherit from
    """

    created_at = Property()
    updated_at = Property()

    def __init__(self, **kwargs):
        for key, value in kwargs.items():
            if hasattr(self, key):
                setattr(self, key, value)

    @property
    def all(self):
        return self.match(graph)

    def save(self):
        graph.push(self)


class Person(BasicModel):
    __primarykey__ = 'email'

    email = Property()
    score = Property()
    first_name = Property()
    last_name = Property()
    username = Property()
    age = Property()
    in_base = RelatedTo('Base', 'MEMBERS')
    created = RelatedTo('Base', 'CREATED_BY')
    owns = RelatedTo('Base', 'OWNED_BY')

    def __str__(self):
        return self.username


class Base(BasicModel):
    __primarykey__ = 'uuid'

    uuid = Property()
    name = Property()
    score = Property(default=0)
    image_url = Property()

    members = RelatedTo(Person)
    created_by = RelatedTo(Person)
    owned_by = RelatedTo(Person)

    def __str__(self):
        return self.name

