import React, { useEffect, useMemo, useState } from 'react'
import { useSelector } from 'react-redux'
import { selectCartItems } from '../../store/features/cart'
import { useDispatch } from 'react-redux';
import { setLoading } from '../../store/features/common';
import { fetchUserDetails } from '../../api/userInfo'
import { useNavigate } from 'react-router-dom';
import PaymentPage from '../Payment/PaymentPage';

const Checkout = () => {
    const cartItems = useSelector(selectCartItems);
    const dispatch = useDispatch();
    const [userInfo, setUserInfo] = useState([]);
    const [paymentMethod, setPaymentMethod] = useState('');
    const navigate = useNavigate();

    const subTotal = useMemo(() => {
        let value = 0;
         cartItems?.forEach(e => {
            value += e?.subTotal;
         })
         return value.toFixed(2);
    }, [cartItems]);

    useEffect(() => {
        dispatch(setLoading(true));
        fetchUserDetails().then(res => {
            setUserInfo(res);
            console.log(res)
        }).catch(err => {

        }).finally(() => {
            dispatch(setLoading(false));
        })
    },[dispatch])


  return (
    <div className='flex p-8'>
        <div className='w-[70%] '>
            <div className='flex gap-8'>
                {/* Address */}
                <p className='font-bold'>Delivery Address</p>
                {
                    userInfo?.addresses &&
                    <div>
                        <p>{userInfo?.addresses?.[0]?.city}</p>
                        <p>{userInfo?.addresses?.[0]?.street}</p>
                        <p>{userInfo?.addresses?.[0]?.city}, {userInfo?.addresses?.[0]?.state}, {userInfo?.addresses?.[0]?.zipCode}</p>
                    </div>
                }
            </div>
            <hr className='h-[2px] bg-slate-200 w-[90%] my-4'/>
            <div className='flex flex-col gap-8'>
                {/*  */}
                <p className='font-bold'>Choose Delivery</p>
                <div>
                    <p>Select a day</p>
                    <div className='flex gap-4 mt-4'>
                        <div className='w-[80px] h-[48px] flex flex-col justify-center border text-center mb-4 rounded-lg mr-4 cursor-pointer
                            hover:scale-110 bg-gray-200 border-gray-500 text-gray-500'><p className='text-center'>{'Oct 5'}</p></div>

                        <div className='w-[80px] h-[48px] flex flex-col justify-center border text-center mb-4 rounded-lg mr-4 cursor-pointer
                            hover:scale-110 bg-white border-gray-500 text-gray-500'><p className='text-center'>{'Oct 8'}</p></div>
                    </div>
                </div>
            </div>
            <hr className='h-[2px] bg-slate-200 w-[90%] my-4'></hr>
            <div className='flex flex-col gap-2'>
                {/* Address */}
                <p className='font-bold'>Payment Method</p>
                <div className='mt-4 flex flex-col gap-4'>
                    <div className='flex gap-2'>
                        <input type="radio" name="payment_method" id="" value={'CARD'} onClick={() => setPaymentMethod("CARD")}/>
                        <p>Credit/Debit Card</p>
                    </div>
                    <div className='flex gap-2'>
                        <input type="radio" name="payment_method" id="" value={'COD'} onClick={() => setPaymentMethod("COD")}/>
                        <p>Cash On Delivery</p>
                    </div>
                    <div className='flex gap-2'>
                        <input type="radio" name="payment_method" id="" value={'UPI'} onClick={() => setPaymentMethod("COD")}/>
                        <p>UPI/Wallet</p>
                    </div>
                </div>
            </div>
            {paymentMethod === 'CARD' && <PaymentPage userId={userInfo?.id} addressId={userInfo?.addresses?.[0]?.id}/>}
            {paymentMethod !== 'CARD' && <button className='w-[150px] items-center h-[48px] bg-black border rounded-lg mt-4 text-white hover:bg-gray-800' onClick={()=> navigate('/payment')}>Pay Now</button>}
        </div>
        <div className='w-[30%] border rounded-lg border-gray-500 p-4 flex flex-col gap-4'>
            <p>Order Summary</p>
            <p>Items Count = {cartItems?.length}</p>
            <p>Subtotal = ${subTotal}</p>
            <p>Shipping = FREE</p>
            <hr className='h-[2px] bg-gray-400'></hr>
            <p>Total Amount = ${subTotal}</p>
        </div>
    </div>
  )
}

export default Checkout