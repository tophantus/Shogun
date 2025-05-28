import React from 'react'
import { PaymentElement } from '@stripe/react-stripe-js'

const CheckoutPayment = ({children}) => {
  return (
    <form action="" className='items-center p-2 mt-4 w-[320px] h-[320px]'>
        <PaymentElement/>
        {children}
    </form>
  )
}

export default CheckoutPayment