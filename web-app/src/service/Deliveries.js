
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
                "id": "fake",
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
                        "firstName": "Mary",
                        "lastName": "F",
                        "phone": "555 555 5555"
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
            
            }])
        };
    }
}

const deliveries = Deliveries.dummy();

export { deliveries };