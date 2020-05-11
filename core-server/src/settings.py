import os
from py2neo import GraphService, Graph

NEO4J_URL = os.environ["NEO4J_BOLT_URL"] if "NEO4J_BOLT_URL" in os.environ else 'bolt://neo4j:test@localhost:7687'

graph_service = GraphService(NEO4J_URL)
# graph = graph_service.default_graph
graph = Graph(NEO4J_URL)

