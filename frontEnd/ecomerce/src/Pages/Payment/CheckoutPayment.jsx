import React, { useCallback, useState } from 'react'
import { PaymentElement, useElements, useStripe } from '@stripe/react-stripe-js'
import { useSelector } from 'react-redux';
import { selectCartItems } from '../../store/features/cart';
import { createOrderRequest } from '../../utils/order-util';
import { placeOrderApi } from '../../api/order';
import { useDispatch } from 'react-redux';
import { setLoading } from '../../store/features/common';

const CheckoutPayment = ({userId, addressId}) => {
  const stripe = useStripe();
  const elements = useElements();
  const cartItems = useSelector(selectCartItems);
  const [error, setError] = useState("");
  const [paymentSuccess, setPaymentSuccess] = useState(false);
  const dispatch = useDispatch();
  const [orderResponse, setOrderResponse] = useState();

  const handleSubmit = useCallback(async (event) => {
    event?.preventDefault();

    const orderRequest = createOrderRequest(cartItems, userId, addressId);
    dispatch(setLoading(true))
    setError("");
    setOrderResponse({});

    const {error} = await elements.submit();
    if (error?.message) {
      setError(error?.message);
      dispatch(setLoading(false));
      return;
    }

    if (elements) {
      console.log("orderRequest: ", orderRequest)
      placeOrderApi(orderRequest).then(async res => {
        setOrderResponse(res);
        console.log("res", res)
        
        stripe.confirmPayment({
            elements,
            clientSecret: res?.credentials?.client_secret,
            
            confirmParams:{
                payment_method:'pm_card_visa',
                return_url:'http://localhost:5173/confirm-payment'
            }
        }).then(res=>{
            console.log("Response ",res);
        })

      }).catch(err => {

      }).finally(() => {
        dispatch(setLoading(false));
      })
    }
    
  }, [addressId, cartItems, dispatch, elements, stripe, userId])

  return (
    <form action="" onSubmit={handleSubmit} className='items-center p-2 mt-4 w-[320px] h-[320px]'>
        <PaymentElement/>
        <button type='submit' disabled={!stripe} className='w-[150px] items-center h-[48px] bg-black border rounded-lg mt-4 text-white hover:bg-gray-800'>Pay Now</button>
        {error && <p className='text-sm pt-4 text-red-600'>{error}</p>}
    </form>
  )
}

export default CheckoutPayment