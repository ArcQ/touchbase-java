MATCH (n) DETACH DELETE n;

//create user
CREATE (p:Person { username: "arcq", firstName: "eddie", lastName: "law", email: "eddielaw296@gmail.com", score: 1.0})
RETURN p;

//create base for user
MATCH (p:Person { username: "arcq" })
CREATE (b:Base {uuid: randomUUID(), name:"base1", score: 2, imageUrl: "https://source.unsplash.com/random/800x800"})
CREATE (b)-[r1:HAS_MEMBER]->(p)
CREATE (b)-[r2:CREATED_BY]->(p)
CREATE (b)-[r3:OWNED_BY]->(p)
RETURN b;

MATCH (p:Person { username: "arcq" })
CREATE (b:Base {uuid: randomUUID(), name:"base2", score: 3, imageUrl: "https://source.unsplash.com/random/800x800"})
CREATE (b)-[r1:HAS_MEMBER]->(p)
CREATE (b)-[r2:CREATED_BY]->(p)
CREATE (b)-[r3:OWNED_BY]->(p)
RETURN b;

//create user for base
MATCH (b:Base {name: "base2"})
CREATE (p:Person { username: "sita", firstName: "alanna", lastName: "tai", email: "alanna.tai@gmail.com", score: 2.0 })
CREATE (b)-[r1:HAS_MEMBER]->(p)
RETURN b;

//find all of user's bases
MATCH (b:Base {name:"base1"})-[r:HAS_MEMBER]-(p:Person { username: "arcq" })
RETURN b;
