from models import Person, Base
from py2neo import Node

def if_found(func):
    def wrapper(model, **kwargs):
        if model.count() > 0:
            return func(model.first(), **kwargs)
        else:
            return None

    return wrapper

@if_found
def map_person(person: Person):
    return {
        "email": person.email,
        "score": person.score,
        "first_name": person.first_name,
        "last_name": person.last_name,
        "username": person.username,
        "age": person.age
    }

def map_base(base: Node):
    return { k: v for k,v in base.items() }
