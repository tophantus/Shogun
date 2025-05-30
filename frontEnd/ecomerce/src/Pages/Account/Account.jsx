import React, { useCallback, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { logOut } from '../../utils/jwt-helper';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { setLoading } from '../../store/features/common';
import { fetchUserDetails } from '../../api/userInfo';
import { loadUserInfo, selectIsUserAdmin, selectUserInfo } from '../../store/features/user';
import { NavLink } from 'react-router-dom';
import { Link } from 'react-router-dom';
import ProfileIcon  from '../../components/common/ProfileIcon';
import OrdersIcon from '../../components/common/OrdersIcon';
import SettingIcon from '../../components/common/SettingIcon';
import { Outlet } from 'react-router-dom';

const Account = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userInfo = useSelector(selectUserInfo);
  const isUserAdmin = useSelector(selectIsUserAdmin)

  useEffect(() => {
    dispatch(setLoading(true));
    fetchUserDetails().then(res => {
      dispatch(loadUserInfo(res));
    }).catch(err => {

    }).finally(() => {
      dispatch(setLoading(false));
    });
  }, [])

  return (
    <div className='p-8'>
      {isUserAdmin && <div className='text-right'><Link to={"/admin"} className="text-lg text-blue-900 underline">Manage Admin</Link></div>}
      {userInfo?.email && <>
        <p className='text-lg font-bold'>Hello {userInfo?.firstName}</p>
        <p>Welcome to your account</p>
        <div className='md:flex mt-4'>
          <ul className='lex-column space-y space-y-4 text-sm font-medium text-gray-500 dark:text-gray-400 md:me-4 mb-4 md:mb-0'>
            <li >
              <NavLink 
                to={"/account-details/profile"} 
                className={({isActive})=> [
                  isActive? "bg-black hover:bg-gray-400":"bg-gray-400 hover:bg-black",
                  "inline-flex items-center px-4 py-3 text-white rounded-lg active w-full"
                ].join(" ")}
              >
                <ProfileIcon/>
                Profile
              </NavLink>
            </li>
            <li >
              <NavLink 
                to={"/account-details/orders"} 
                className={({isActive})=> [
                  isActive? "bg-black hover:bg-gray-400":"bg-gray-400 hover:bg-black",
                  "inline-flex items-center px-4 py-3 text-white rounded-lg active w-full"
                ].join(" ")}
              >
                <OrdersIcon/>
                Orders
              </NavLink>
            </li>
            <li >
              <NavLink 
                to={"/account-details/settings"} 
                className={({isActive})=> [
                  isActive? "bg-black hover:bg-gray-400":"bg-gray-400 hover:bg-black",
                  "inline-flex items-center px-4 py-3 text-white rounded-lg active w-full"
                ].join(" ")}
              >
                <SettingIcon/>
                Settings
              </NavLink>
            </li>
          </ul>
          <div className="px-4 w-full rounded-lg">
            <Outlet />
          </div>
        </div>
      </>}
    </div>
  )
}

export default Account