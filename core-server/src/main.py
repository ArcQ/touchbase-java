import sys
import json
sys.path.append('./src')

import graphene
from fastapi import FastAPI
from starlette.graphql import GraphQLApp
from models import Person
from settings import graph
from mapper import map_person


class Query(graphene.ObjectType):
    hello = graphene.String(username=graphene.String(default_value="stranger"))

    def resolve_hello(self, info, **kwargs):
        person = Person.match(graph).where(**kwargs)
        if (person.count() > 0):
            return map_person(person.first())
        else:
            return None



app = FastAPI()
app.add_route("/", GraphQLApp(schema=graphene.Schema(query=Query)))
