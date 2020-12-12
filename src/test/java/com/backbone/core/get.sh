# read requests

echo "get hostname (meaningful in a server)"
curl localhost:8084/dummy

echo ""
curl localhost:8084/dummy/Tansu

printf "\n review/1 >>"
curl localhost:8084/review/1

printf "\n review/101 : >>"
curl localhost:8084/review/101
