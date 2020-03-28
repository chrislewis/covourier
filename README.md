#### `/deliveries`

##### GET

Get all deliveries.

Returns an array of delivery models.

#### `/delivery`

##### GET

Request a single delivery.

##### POST

Create a new delivery.

### Models

#### Delivery

A delivery represents a named item or set of items bound from one location to another, to be delivered by a courier.

```json
{
    "item": "Ventilator components",
    "courier": {
        "id": "",
        "firstName": "",
        "lastName": ""
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
    "deliveryContacts": [
        {
            "firstName": "",
            "lastName": "",
            "phone": ""
        }
    ],

}
```

##### Field: `status`

Indicates the status of the delivery.

type: enum: `unassigned | assigned | enroute | delivered | cancelled`
