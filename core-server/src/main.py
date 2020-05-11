import sys
import json
sys.path.append('./src')

import graphene
from fastapi import FastAPI
from starlette.graphql import GraphQLApp
from models import Person
from settings import graph


class Query(graphene.ObjectType):
    hello = graphene.String(name=graphene.String(default_value="stranger"))

    def resolve_hello(self, info, **kwargs):
        print(info)
        person = Person.match(graph).where(**kwargs)
        if person is None:
            return "hi"
        else:
            return json.dumps(person.__str__())



app = FastAPI()
app.add_route("/", GraphQLApp(schema=graphene.Schema(query=Query)))
