
class Deliveries {
    constructor(url) {
        this.url = url;
    }

    getAll() {
        return fetch(this.url)
            .then(res => res.json())
    }

    static local() {
        const [serverHost, serverPort] = window.location.host.split(':');
        return new Deliveries(`${window.location.protocol}//${serverHost}:8080/deliveries`)
    }

    static fake() {
        return new Deliveries("http://jsonplaceholder.typicode.com/users")
    }
}

const deliveries = Deliveries.local();

export { deliveries };