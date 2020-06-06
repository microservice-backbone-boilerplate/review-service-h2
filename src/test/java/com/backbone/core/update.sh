# update product

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

curl -H 'Content-Type: application/json' \
     -X PUT http://localhost:8084/review


curl localhost:8084/review/10
