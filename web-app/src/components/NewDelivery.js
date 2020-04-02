import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import { Formik, Form as Formk, Field, FieldArray } from 'formik';
import dayjs from 'dayjs';

class NewDelivery extends Component {

    render() {
        const day = dayjs('2018-04-04T16:00')
        console.log(dayjs('2020-02-04T16:00'))
        console.log(day.format())
        return (
            <div className="card">
                <Formik
                initialValues={{
                    item: '',
                    description: '',
                    senders: [{ firstName: '', lastName: '', phone: '', email: '' }],
                    pickupTime: '2020-04-02T10:34', //new Date().toISOString(),
                    recipients: [{ firstName: '', lastName: '', phone: '', email: '' }]
                }}
                 onSubmit={(values, { setSubmitting }) => {
                    console.log(values)
                    setTimeout(() => {
                        setSubmitting(false);
                        values.pickupTime = dayjs(values.pickupTime).format()
                        console.log(values.pickupTime)
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
            <Formk>
                <div className="form-group">
                    <label for="item">Item</label>
                    <Field
                        className="form-control"
                        id="item"
                        name="item"
                        value={values.item}
                        placeholder="ex: ventilator components" required
                    />
                </div>

                <div className="form-group">
                    <label for="description">Description</label>
                    <Field
                        as="textarea"
                        className="form-control"
                        id="description"
                        name="description"
                    />
                </div>

                <FieldArray
                    name="senders"
                    render={arrayHelpers => (
                        <>
                            {values.senders.map((sender, index) => (
                            <div>
                                <button
                                    type="button"
                                    onClick={() => arrayHelpers.remove(index)}
                                >remove</button>
                                <div className="row">
                                    <div className="col">
                                        <label for={`senders.${index}.firstName`}>Sender Name</label>
                                    </div>
                                    <div className="col">
                                        <label for={`senders.${index}.lastName`}>Sender Last Name</label>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            id={`senders.${index}.firstName`}
                                            name={`senders.${index}.firstName`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            id={`senders.${index}.lastName`}
                                            name={`senders.${index}.lastName`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col">
                                        <label for={`senders.${index}.phone`}>Sender Phone</label>
                                    </div>
                                    <div className="col">
                                        <label for={`senders.${index}.email`}>Sender Email</label>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            type="tel"
                                            id={`senders.${index}.phone`}
                                            name={`senders.${index}.phone`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            type="email"
                                            id={`senders.${index}.email`}
                                            name={`senders.${index}.email`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                </div>

                                <div>
                                    <label for="pickupTime">Pickup Time</label>
                                    <input className="form-control" id="pickupTime" name="pickupTime" value={values.pickupTime} type="datetime-local" onChange={handleChange} />
                                </div>

                                <button type="button" onClick={() => arrayHelpers.push({})}>add</button>
                            </div>
                        ))}</>
                    )}
                />

                <div className="form-group">
                    <label for="pickupAddress">Pickup Address</label>
                    <Field
                        as="textarea"
                        className="form-control"
                        id="pickupAddress"
                        name="pickupAddress"
                    />
                </div>

                <FieldArray
                    name="recipients"
                    render={arrayHelpers => (
                        <>
                            {values.recipients.map((recipient, index) => (
                            <div>
                                <button
                                    type="button"
                                    onClick={() => arrayHelpers.remove(index)}
                                >remove</button>
                                <div className="row">
                                    <div className="col">
                                        <label for={`recipients.${index}.firstName`}>Recipient Name</label>
                                    </div>
                                    <div className="col">
                                        <label for={`recipients.${index}.lastName`}>Recipient Last Name</label>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            id={`recipients.${index}.firstName`}
                                            name={`recipients.${index}.firstName`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            id={`recipients.${index}.lastName`}
                                            name={`recipients.${index}.lastName`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                </div>

                                <div className="row">
                                    <div className="col">
                                        <label for={`recipients.${index}.phone`}>Recipient Phone</label>
                                    </div>
                                    <div className="col">
                                        <label for={`recipients.${index}.email`}>Recipient Email</label>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            type="tel"
                                            id={`recipients.${index}.phone`}
                                            name={`recipients.${index}.phone`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                    <div className="col">
                                        <Field
                                            className="form-control"
                                            type="email"
                                            id={`recipients.${index}.email`}
                                            name={`recipients.${index}.email`}
                                            placeholder="ex: Mary"
                                        />
                                    </div>
                                </div>
                                <button type="button" onClick={() => arrayHelpers.push({})}>add</button>
                            </div>
                        ))}</>
                    )}
                />

                <div className="form-group">
                    <label for="deliveryAddress">Delivery Address</label>
                    <Field
                        as="textarea"
                        className="form-control"
                        id="deliveryAddress"
                        name="deliveryAddress"
                    />
                </div>

                <Button type="submit">Submit form</Button>
            </Formk>
                    )}
            </Formik>
            </div>
        );
    }
}

export default NewDelivery;