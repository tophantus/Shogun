import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import React from 'react';
import CheckoutPayment from "./CheckoutPayment";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLIC_KEY);

const PaymentPage = ({children}) => {

  const options = {
    mode: "payment",
    amount: 100,
    currency: "usd",
    appearance: {
      theme: "flat"
    }
  }

  return (
    <div>
      <Elements stripe={stripePromise} options={options}>
        <CheckoutPayment children={children}/>
      </Elements>
    </div>
  )
}

export default PaymentPage