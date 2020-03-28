aws dynamodb create-table \
    --table-name Deliveries \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://localhost:8000

aws dynamodb batch-write-item \
    --request-items file://./deliveries.json \
    --endpoint-url http://localhost:8000