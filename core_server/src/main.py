import json
import sys
sys.path.append('./src')

from ariadne import ObjectType, make_executable_schema, load_schema_from_path
from ariadne.asgi import GraphQL
from models import Person
from settings import graph
from starlette.applications import Starlette
from mapper import map_person, map_base
from pypher import Pypher, __

type_defs = load_schema_from_path("./src/schema.graphql")

query = ObjectType("Query")

@query.field("person")
def resolve_person(_, info, **args):
    # request = info.context["request"]
    # user_agent = request.headers.get("user-agent", "guest")
    return map_person(Person.match(graph).where(**args))

@query.field("bases")
def resolve_bases(_, info, **args):
    # request = info.context["request"]
    # user_agent = request.headers.get("user-agent", "guest")

    query = Pypher()

    query.MATCH.node('bases', labels='Base').rel('r', labels='HAS_MEMBER').node('person', labels='Person')
    query.WHERE.person.property('username') == 'arcq'
    query.RETURN.bases

    return graph.run(str(query)).evaluate()

@query.field("bases")
def resolve_my_bases(_, info, me, **args):
    request = info.context["request"]
    auth_header = request.headers.get("authorization")
    auth_header

    query = Pypher()
    query.MATCH.node('base', labels='Base').rel('r', labels='HAS_MEMBER').node('person', labels='Person')
    query.WHERE.person.property('username') == 'arcq'
    query.RETURN.base

    res_list = graph.run(str(query), **query.bound_params).data()
    return [map_base(res['base']) for res in res_list] if res_list else []

schema = make_executable_schema(type_defs, query)

app = Starlette()
app.mount("/", GraphQL(schema, debug=True))
