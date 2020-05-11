import json
from models import Person

def map_person(person: Person):
    return json.dumps({
        "email": person.email
    })

