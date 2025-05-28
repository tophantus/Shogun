import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { confirmPaymentApi } from '../../api/order';
import { useDispatch } from 'react-redux';
import { setLoading } from '../../store/features/common';
import { useSelector } from 'react-redux';
import Spinner from '../../components/Spinner/Spinner';

const ConfirmPayment = () => {
    const location = useLocation();
    const dispatch = useDispatch();
    const [error, setError] = useState("");
    const loading = useSelector((state) => state?.commonState?.loading);
    const navigate = useNavigate();


    useEffect(() => {
        const query = new URLSearchParams(location.search);
        const clientSecret = query.get('payment_intent_client_secret');
        const redirectStatus = query.get('redirect_status');
        const paymentIntent = query.get('payment_intent');
        

        if (redirectStatus === "succeeded") {
            dispatch(setLoading(true));
            confirmPaymentApi({
                paymentIntent: paymentIntent,
                status: paymentIntent
            }).then(res => {
                const orderId = res?.orderId;
                navigate(`/order-confirmed?orderId=${orderId}`);
            }).catch(err => {
                setError("Something went wrong!");
            }).finally(() => {
                dispatch(setLoading(false));
            })
        } else {
            setError('Payment Failed - '+ redirectStatus)
        }
    }, [location.search, dispatch]);

  return (
    <>
        <div>
            Processing Payment...
        </div>
        {loading && <Spinner></Spinner>}
    </>
  )
}

export default ConfirmPayment