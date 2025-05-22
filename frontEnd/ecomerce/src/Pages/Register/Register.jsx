import React, { useCallback, useState } from 'react'
import GoogleSignIn from '../../components/Button/GoogleSignIn'
import { Link, useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { setLoading } from '../../store/features/common'
import { loginApi, registerApi } from '../../api/authentication'
import { saveToken } from '../../utils/jwt-helper'
import VerifyCode from './VerifyCode'

const Register = () => {
  const navigate = useNavigate();

    const [values, setValues] = useState({
        email: "",
        password: "",
        firstName: "",
        lastName: "",
        phoneNumber: ""
    });

    const [enableVerify, setEnableVerify] = useState(false);
    const [error, setError] = useState('');

    const dispatch = useDispatch()

    const onSubmit = useCallback((e) => {
        e.preventDefault()
        setError("")
        dispatch(setLoading(true))
        registerApi(values).then(res => {
            if (res?.code === 200) {
                setEnableVerify(true);
            }
        }).catch(err => {
            setError("Invalid or Email already exist!");
        }).finally(() => {
            dispatch(setLoading(false));
        })

    }, [dispatch, values]);

    const handleOnChange = useCallback((e) => {
        e.persist();
        setValues(values => ({
            ...values,
            [e.target.name]:e.target?.value
        }))
    }, [])

  return (
    <div className='px-8 w-full lg:w-[70%]'>
        {!enableVerify &&
        <>
        <p className='text-3xl font-bold pb-4 pt-4'>
            Sign Up
        </p>
        <GoogleSignIn/>
        <p className='text-gray-500 items-center text-center w-full py-2'>OR</p>
        <div className='mb-4'>
            <form onSubmit={onSubmit} autoComplete='off'>
                <label>Email Address</label>
                <input type="email" name='email' value={values?.email} onChange={handleOnChange} placeholder='Email address' className='h-[48px] w-full border p-2 mt-2 mb-6 border-gray-400'required autoComplete='off'/>
                <label className='mt-6'>Password</label>
                <input type="password" name='password' value={values.password} onChange={handleOnChange} placeholder='Password' className='h-[48px] mt-2 w-full border p-2 border-gray-400' required autoComplete='new password'/>
                <button className='border w-full rounded-lg h-[48px] bg-black text-white hover:opacity-80 mt-4'>Sign Up</button>
            </form>
        </div>
        <Link to={"/v1/login"} className='underline mt-2 text-gray-500 hover:text-black'>Already have an account? Log in</Link>
        {error && <p className='text-lg text-red-700'>{error}</p>}
        </>}
        {enableVerify && <VerifyCode email={values?.email}/>}
    </div>
  )
}

export default Register