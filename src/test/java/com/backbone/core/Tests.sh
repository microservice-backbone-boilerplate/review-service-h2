# read requests

echo "get hostname (meaningful in a server)"
curl localhost:8084/dummy

echo ""
curl localhost:8084/dummy/Tansu

#read
printf "\n review/1 >>"
curl localhost:8084/review/1

printf "\n review/101 : >>"
curl localhost:8084/review/101

curl localhost:8084/reviews
curl localhost:8084/reviews/page/0/size/1

#save
#PUT for partial update (not implemented yet)
#POST is creation (w/o ID or w/ not available ID) or update (u have to provide all fields, w/ available ID)
curl localhost:8084/review/10

curl -d '{"id":10,
          "userName":"viladamir34",
          "productId":7,
          "title":"title",
          "rating":4,
          "description":"description",
          "verifiedPurchase":true,
          "helpful":true,
          "abuse":true}' \
     -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review

#partial update is not possible, for now,
# so below gets an exception
curl -d '{"id":10,
          "title":"title XXX"}' \
     -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review

#if id is not available, it creates a new record and assign a new id
curl -d '{"id":110,
          "userName":"viladamir34",
          "productId":7,
          "title":"title",
          "rating":4,
          "description":"description",
          "verifiedPurchase":true,
          "helpful":true,
          "abuse":true}' \
     -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review

#normal creation
curl -d '{"userName":"viladamir34",
          "productId":7,
          "title":"title",
          "rating":4,
          "description":"description",
          "verifiedPurchase":true,
          "helpful":true,
          "abuse":true}' \
     -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review

#bad request
curl -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review


curl localhost:8084/review/10

#delete
curl -H 'Content-Type: application/json' -X DELETE http://localhost:8084/review/100

curl localhost:8084/review/100