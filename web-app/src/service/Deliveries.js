
class Deliveries {
    constructor(url) {
        this.url = url;
    }

    getAll() {
        return fetch(this.url)
            .then(res => res.json())
    }

    static local() {
        const serverHost = window.location.host.split(':')[0];
        return new Deliveries(`${window.location.protocol}//${serverHost}:8080/deliveries`)
    }

    static dummy() {
        return {
            getAll: () => Promise.resolve([{
                "id": "fake-id",
                "item": "Ventilator components",
                "description": "Small box, approximately 10x5 inches",
                // "courier": { // TODO
                //     "id": "",
                //     "firstName": "",
                //     "lastName": ""
                // },
                "status": "unassigned",
                "pickupAddress": {
                    "streetAddress": "150 55th Street",
                    "city": "Brooklyn",
                    "state": "NY",
                    "zip": "11220"
                },
                "pickupTime": "2020-03-31T13:04:52Z",
                "pickupContacts": [
                    {
                        "firstName": "Mary",
                        "lastName": "F",
                        "phone": "555 555 5555",
                        "email": "maryf@fake.fke"
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
                        "firstName": "Bill",
                        "lastName": "D",
                        "phone": "444 444 4444",
                        "email": "billd@fake.fke"
                    }
                ],
            
            }])
        };
    }
}

const deliveries = Deliveries.dummy();

export { deliveries };