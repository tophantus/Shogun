import React from 'react'
import Navigation from '../components/Navigation/Navigation'
import { Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux'
import Spinner from '../components/Spinner/Spinner'

const ShopApplicationWrapper = () => {
  const isLoading = useSelector((state) => state?.commonState?.loading)
  return (
    <div>
        <Navigation/>
        <Outlet/>
        {isLoading && <Spinner></Spinner>}
    </div>
  )
}

export default ShopApplicationWrapper