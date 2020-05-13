//create base for user
MATCH (p:Person { username: "arcq" })
CREATE (b:Base {uuid:"1", name:"base1", score: "2", image_url: "https://source.unsplash.com/random/800x800"})
CREATE (b)-[r1:HAS_MEMBER]->(p)
CREATE (b)-[r2:CREATED_BY]->(p)
CREATE (b)-[r3:OWNED_BY]->(p)
RETURN b;

//create user for base
MATCH (b:Base {uuid:"1"})
CREATE (p:Person { username: "sita", first_name: "alanna", last_name: "tai", age: "29"})
CREATE (b)-[r1:HAS_MEMBER]->(p)
RETURN b;

//find all of user's bases
MATCH (b:Base {uuid:"0"})-[r:HAS_MEMBER]-(p:Person { username: "arcq" })
RETURN b;
