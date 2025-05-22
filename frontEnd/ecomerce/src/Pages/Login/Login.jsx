import React, { useCallback, useState } from 'react'
import GoogleSignIn from '../../components/Button/GoogleSignIn'
import { Link, useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { setLoading } from '../../store/features/common'
import { loginApi } from '../../api/authentication'
import { saveToken } from '../../utils/jwt-helper'

const Login = () => {
    const navigate = useNavigate();

    const [values, setValues] = useState({
        username: "",
        password: "",
    });

    const [error, setError] = useState('');

    const dispatch = useDispatch()

    const onSubmit = useCallback((e) => {
        e.preventDefault()
        setError("")
        dispatch(setLoading(true))
        loginApi(values).then(res => {
            if (res?.token) {
                saveToken(res?.token)
                navigate("/")
            } else {
                setError("Something went wrong!");
            }
        }).catch(err => {
            setError("Invalid credential!");
        }).finally(() => {
            dispatch(setLoading(false));
        })

    }, [dispatch, navigate, values]);

    const handleOnChange = useCallback((e) => {
        e.persist();
        setValues(values => ({
            ...values,
            [e.target.name]:e.target?.value
        }))
    }, [])

  return (
    <div className='px-8 w-full lg:w-[70%]'>
        <p className='text-3xl font-bold pb-4 pt-4'>
            Sign In
        </p>
        <GoogleSignIn/>
        <p className='text-gray-500 items-center text-center w-full py-2'>OR</p>
        <div className='mb-4'>
            <form onSubmit={onSubmit}>
                <input type="email" name='username' value={values?.username} onChange={handleOnChange} placeholder='Email address' className='h-[48px] w-full border p-2 border-gray-400'required/>
                <input type="password" name='password' value={values.password} onChange={handleOnChange} placeholder='Password' className='h-[48px] mt-8 w-full border p-2 border-gray-400' required autoComplete='new password'/>
                <Link className='text-right w-full float-right underline text-gray-500 mt-4 hover:text-black'>Forgot password?</Link>
                <button className='border w-full rounded-lg h-[48px] bg-black text-white hover:opacity-80 mt-4'>Sign In</button>
            </form>
        </div>
        <Link to={"/v1/register"} className='underline mt-2 text-gray-500 hover:text-black'>Don't have an account? Sign Up</Link>
        {error && <p className='text-lg text-red-700'>{error}</p>}
        
    </div>
  )
}

export default Login