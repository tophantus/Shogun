import React, { useCallback, useState } from 'react'
import { useSelector } from 'react-redux'
import { removeAddress, selectUserInfo } from '../../store/features/user'
import { useDispatch } from 'react-redux';
import AddAddress from './AddAddress';
import { setLoading } from '../../store/features/common';
import { deleteAddressApi } from '../../api/userInfo';

const Profile = () => {
    const userInfo = useSelector(selectUserInfo);
    const [addAddress, setAddAddress] = useState(false);
    const dispatch = useDispatch();

    const onDeleteAddress = useCallback((id) => {
        dispatch(setLoading(true));
        deleteAddressApi(id).then(res => {
            dispatch(removeAddress(res));
        }).catch(err => {

        }).finally(() => {
            dispatch(setLoading(false));
        })
    }, [dispatch])

  return (
    <div>
        <p className='text-2xl'>My Info</p>
        {!addAddress && (
            <div>
                <div className='flex gap-2'>
                    <h2 className='text-xl pt-4'>Contact Details</h2>
                    <button className="underline text-blue-900 mt-4">Edit</button>
                </div>
                <div className="pt-4">
                    <p className="text-gray-700 py-2 font-bold">Full Name</p>
                    <p>{userInfo?.firstName} {userInfo?.lastName}</p>
                    <p className="text-gray-700 py-2 font-bold">Phone Number</p>
                    <p>{userInfo?.phoneNumber?.trim() ? userInfo.phoneNumber : "None"}</p>
                    <p className="text-gray-700 py-2 font-bold">Email</p>
                    <p>{userInfo?.email}</p>
                </div>
                {/* Address */}
                <div className='pt-4'>
                    <div className='flex gap-12'>
                        <h3 className='text-lg font-bold'>Address</h3>
                        <button onClick={() => setAddAddress(true)} className='underline text-blue-900'>Add New</button>
                    </div>
                    <div className='pt-4 grid grid-cols-1 lg:grid-cols-4 md:grid-cols-3 gap-8 pb-10 mb-8'>
                        {
                            userInfo?.addresses?.map((address, index) => {
                                return (
                                    <div key={address?.id} className='bg-gray-200 border rounded-lg p-4'>
                                        <p className='pn-2 font-bold'>{address?.name}</p>
                                        <p className="pb-2">{address?.phoneNumber}</p>
                                        <p className="pb-2">
                                            {address?.street},{address?.city},{address?.state}
                                        </p>
                                        <p>{address?.zipCode}</p>
                                        <div className='flex gap-2'>
                                            <button className="underline text-blue-900">Edit</button>
                                            <button onClick={() => onDeleteAddress(address?.id)} className="underline text-blue-900">
                                                Remove
                                            </button>
                                        </div>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
            </div>
            
        )}
        {addAddress && <AddAddress onCancel={() => setAddAddress(false)}/>}
    </div>
  )
}

export default Profile