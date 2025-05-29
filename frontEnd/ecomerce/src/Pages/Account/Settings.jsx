import React, { useCallback } from 'react'
import { useNavigate } from 'react-router-dom';
import { logOut } from '../../utils/jwt-helper';

const Settings = () => {
  const navigate = useNavigate();

    const onLogout = useCallback(() => {
      logOut();
      navigate("/");
    }, [navigate]);

  return (
    <div>
      <button onClick={onLogout} className='w-[150px] items-center h-[48px] bg-black border rounded-lg mt-4 text-white hover:bg-gray-800'>Logout</button>
    </div>
  )
}

export default Settings