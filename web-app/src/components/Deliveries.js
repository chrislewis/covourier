import React, { Component } from 'react'
import {Button, Modal} from 'react-bootstrap'

class Deliveries extends Component {
    state = {
        show: false
    }

    render() {
        const { deliveryService, deliveries } = this.props;
        return (
            <>
                <center><h2>Active Deliveries</h2></center>
                <div>
                    {deliveries.map((delivery) => (
                        <Delivery key={delivery.id} deliveryService={deliveryService} delivery={delivery} />
                    ))}
                </div>
            </>
      );
    }
}

class Delivery extends Component {
    state = { show: false }

    constructor({ deliveryService }) {
        super();
        this.deliveryService = deliveryService;
      }

    handleAccept({ currentTarget }, id) {
        this.setState({ show: true, deliveryDOMNode: currentTarget, deliveryId: id });
    }

    handleFulfillPartial() {
        fetch('http://jsonplaceholder.typicode.com/users')
            .then(res => res.json())
            .then((data) => {
                this.state.deliveryDOMNode.style.backgroundColor = 'yellow';
                this.setState({ show: false });
            })
            .catch(console.log)
    }

    handleFulfillComplete() {
        fetch('http://jsonplaceholder.typicode.com/users')
            .then(res => res.json())
            .then((data) => {
                this.state.deliveryDOMNode.style.backgroundColor = 'green';
                this.setState({ show: false });
            })
            .catch(console.log)
    }

    render() {
        const { delivery } = this.props;
        return (
            <>
                <Modal show={this.state.show} onHide={() => this.setState({show: false})} animation={false} centered>
                    <Modal.Header closeButton>
                        <Modal.Title>Fulfill Delivery ({this.state.deliveryId})</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Can you fulfill this entire delivery?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="warning" onClick={() => this.handleFulfillPartial()}>
                            Part of it
                        </Button>
                        <Button variant="primary" onClick={() => this.handleFulfillComplete()}>
                            All of it
                        </Button>
                    </Modal.Footer>
                </Modal>

                <div className="card" key={delivery.id} onClick={(e) => this.handleAccept(e, delivery.id)}>
                    <div className="card-body">
                        <h5 className="card-title">{delivery.item}</h5>
                        <h6 className="card-subtitle mb-2 text-muted">todo</h6>
                        <div>From</div>
                        <Address address={delivery.pickupAddress}/>
                        <Contacts contacts={delivery.pickupContacts}/>
                        <div>To</div>
                        <Address address={delivery.deliveryAddress}/>
                        <Contacts contacts={delivery.deliveryContacts}/>
                    </div>
                </div>
            </>
      );
    }
}

const getGMapsURI = (addressStr) => {
    const encoded = encodeURIComponent(addressStr);
    return `https://www.google.com/maps/search/?api=1&query=${encoded}`;
};

const AA = ({href, text}) => <a onClick={(e) => e.stopPropagation()} href={href}>{text}</a>

const Phone = ({phone}) => <AA href={`tel:${phone}`} text={phone}/>

const Address = ({address}) => {
    const { streetAddress, city, state, zip } = address;
    const addressStr = [streetAddress, city, state, zip].join(" ");
    return <AA href={getGMapsURI(addressStr)} text={`${streetAddress}, ${city} ${state}, ${zip}`}/>
}

const Contacts = ({contacts}) =>
    <div>
        {contacts.map((contact) => (
            <div>{contact.firstName} - <Phone phone={contact.phone}/></div>
        ))}
    </div>
  
export default Deliveries;