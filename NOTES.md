# info
http://127.0.0.1:8011/persons
http://127.0.0.1:8011/persons/search
# json
{"firstName": "Gigi", "lastName": "Kent"}
# POST
curl 'http://127.0.0.1:8011/persons' -H 'Content-Type: application/json;charset=UTF-8' --data-binary '{"firstName": "Gigi", "lastName": "Kent"}'
curl 'http://127.0.0.1:8011/cats' -H 'Content-Type: application/json;charset=UTF-8' --data-binary '{"name": "cat1", "person_id": "1"}'
# GET
curl http://127.0.0.1:8011/persons
# jq
curl http://127.0.0.1:8011/persons | jq '.["_embedded"]'
curl http://127.0.0.1:8011/persons | jq '._embedded'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[]'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[] | {id, firstName, lastName}'
curl http://127.0.0.1:8011/persons | jq '._embedded.persons[] | del(._links)'
