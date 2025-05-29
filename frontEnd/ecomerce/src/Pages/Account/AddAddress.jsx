import React, { useState } from 'react'

const AddAddress = () => {
    const [values, setValues] = useState({
        name: null,
        phoneNumber: null,
        street: null,
        city: null,
        state: null,
        zipCode: null
    })
  return (
    <div>AddAddress</div>
  )
}

export default AddAddress