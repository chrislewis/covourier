import React, { Component } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Formik } from 'formik';

class NewDelivery extends Component {

    getGMapsURI(raw) {
        const clean = raw.replace("\n", ",");
        const encoded = encodeURIComponent(clean);
        return `https://www.google.com/maps/search/?api=1&query=${encoded}`;
    }

    render() {
        return (
            <div className="card">
                <Formik
                initialValues={{
                    item: ''
                }}
                 onSubmit={(values, { setSubmitting }) => {
                    console.log(values)
                    setTimeout(() => {
                        setSubmitting(false);
                      alert(JSON.stringify(values, null, 2));
                      
                    }, 400);
                    }}
                    >
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        values,
                        touched,
                        isValid,
                        errors,
                    }) => (
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="item">
                    <Form.Label>Item</Form.Label>
                    <Form.Control type="text" name="item" onChange={handleChange} value={values.item} placeholder="ex: ventilator components" required />
                </Form.Group>

                <Form.Group controlId="sender-name">
                    <Form.Label>Sender Name</Form.Label>
                    <Form.Control type="text" name="senderName" placeholder="ex: Mary" />
                </Form.Group>

                <Form.Group controlId="sender-email">
                    <Form.Label>Sender Email</Form.Label>
                    <Form.Control type="email" name="senderEmail" placeholder="doc@foo.com" />
                </Form.Group>

                <Form.Group controlId="sender-phone">
                    <Form.Label>Sender Phone</Form.Label>
                    <Form.Control type="text" name="senderPhone" placeholder="555 555 5555" />
                </Form.Group>

                <Form.Group controlId="pickup-address">
                    <Form.Label>Pickup Address</Form.Label>
                    <Form.Control as="textarea" name="pickupAddress" rows="3" onBlur={(e) => {
                        const maps = this.getGMapsURI(e.currentTarget.value);
                        console.log(maps);
                    }}/>
                </Form.Group>

                <Form.Group controlId="recipient-name">
                    <Form.Label>Recipient Name</Form.Label>
                    <Form.Control type="text" name="recipientName" placeholder="ex: Mary" />
                </Form.Group>

                <Form.Group controlId="recipient-email">
                    <Form.Label>Recipient Email</Form.Label>
                    <Form.Control type="email" name="recipientEmail" placeholder="doc@foo.com" />
                </Form.Group>

                <Form.Group controlId="recipient-phone">
                    <Form.Label>Recipient Phone</Form.Label>
                    <Form.Control type="text" name="recipientPhone" placeholder="555 555 5555" />
                </Form.Group>

                <Form.Group controlId="delivery-address">
                    <Form.Label>Delivery Address</Form.Label>
                    <Form.Control as="textarea" name="deliveryAddress" rows="3" />
                </Form.Group>
                <Button type="submit">Submit form</Button>
            </Form>
                    )}
            </Formik>
            </div>
        );
    }
}

export default NewDelivery;