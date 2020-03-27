#### `/deliveries`

##### GET

Get all deliveries.

#### `/delivery`

##### GET

Request a single delivery.

##### POST

Create a new delivery.

### Models

#### Delivery

```json
{
    "item": "Ventilator components",
    "courier": {
        "id": "",
        "name": ""
    },
    "status": "unassigned",
    "pickupAddress": {
        "streetAddress": "150 55th Street",
        "city": "Brooklyn",
        "state": "NY",
        "zip": "11220"
    },
    "pickupContacts": [
        {
            "firstName": "",
            "lastName": "",
            "phone": ""
        }
    ],
    "deliveryAddress": {
        "streetAddress": "6 MetroTech Center",
        "city": "Brooklyn",
        "state": "NY",
        "zip": "11201"
    },
    "pickupContacts": [
        {
            "firstName": "",
            "lastName": "",
            "phone": ""
        }
    ],

}
```

##### `status`

/deliveries
