import React, { Component } from 'react'
import {Button, Modal} from 'react-bootstrap'

class Deliveries extends Component {
    state = {
        show: false
    }

    render() {
        const { deliveries } = this.props;
        return (
            <>
                <div>
                    {deliveries.map((delivery) => (
                        <Delivery key={delivery.id} delivery={delivery} />
                    ))}
                </div>
            </>
      );
    }
}

class Delivery extends Component {
    state = { show: false }

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
                        <h5 className="card-title">{delivery.name}</h5>
                        <h6 className="card-subtitle mb-2 text-muted">{delivery.email}</h6>
                        <p className="card-text">{delivery.company.catchPhrase}</p>
                    </div>
                </div>
            </>
      );
    }
}
  
export default Deliveries;