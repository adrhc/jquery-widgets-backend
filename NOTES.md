# info
http://127.0.0.1:8011/persons
http://127.0.0.1:8011/persons/search
# json
{"firstName": "Gigi", "lastName": "Kent"}
# POST
curl 'http://127.0.0.1:8011/persons' -H 'Content-Type: application/json;charset=UTF-8' --data-binary '{"firstName": "Gigi", "lastName": "Kent"}'
curl 'http://127.0.0.1:8011/cats' -H 'Content-Type: application/json;charset=UTF-8' --data-binary '{"name": "cat"}'
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:8011/persons/1" http://127.0.0.1:8011/cats/2/person
# GET
curl http://127.0.0.1:8011/persons
curl http://127.0.0.1:8011/persons/1/cats
curl http://127.0.0.1:8011/persons/search/findAll
# jq
curl http://127.0.0.1:8011/persons/1/cats | jq '._embedded'
curl http://127.0.0.1:8011/persons | jq '.["_embedded"]'
curl http://127.0.0.1:8011/persons | jq '._embedded'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[]'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[] | {id, firstName, lastName}'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[] | del(._links)'
# associating relationships
https://www.baeldung.com/spring-data-rest-relationships
### add cat 4 to person 1
curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://127.0.0.1:8011/persons/1" http://127.0.0.1:8011/cats/4/person
