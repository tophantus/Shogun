import React from "react";
import Wishlist from "../common/Wishlist";
import AccountIcon from "../common/AccountIcon";
import CartIcon from "../common/CartIcon";
import { Link, NavLink } from "react-router-dom";
import './Navigation.css';

const Navigation = ({variant="default"}) => {
    return (
        <>
        <nav className='flex items-center py-6 px-16 justify-between gap-40'>
            <div className='flex items-center gap-6'>
                {/* Logo */}
                <h2 className='text-3xl font-bold text-black gap-8' href="/">Shogun</h2>
            </div>
            {variant==='default' &&
            <div className='flex flex-wrap items-center gap-10 flex-1'>
                {/* Nav items */}
                <ul className="flex gap-14" >
                    <li className="text-gray-600 hover:text-black"><NavLink to="/" className={({isActive})=>isActive ?'active-link': ''}>Shop</NavLink></li>
                    <li className="text-gray-600 hover:text-black"><NavLink to="/men" className={({isActive})=>isActive ?'active-link': ''}>Men</NavLink></li>
                    <li className="text-gray-600 hover:text-black"><NavLink to="/women" className={({isActive})=>isActive ?'active-link': ''}>Women</NavLink></li>
                    <li className="text-gray-600 hover:text-black"><NavLink to="/kids" className={({isActive})=>isActive ?'active-link': ''}>Kids</NavLink></li>
                </ul>
            </div>
            }
            {variant==='default' &&
            <div className="flex justify-center">
                {/* Search bar */}
                <div className="border rounded flex overflow-hidden">
                    <div className="flex items-center justify-center px-4 border-1">
                        <svg className="h-4 w-4 text-grey-dark" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M16.32 14.9l5.39 5.4a1 1 0 0 1-1.42 1.4l-5.38-5.38a8 8 0 1 1 1.41-1.41zM10 16a6 6 0 1 0 0-12 6 6 0 0 0 0 12z"/></svg>
                        <input type="text" className="px-4 py-2 outline-none" placeholder="Search"/>
                    </div> 
                </div>
            </div>
            }
            <div className="flex flex-wrap items-center gap-4">
                {/* Action items */}
                {variant==='default' &&
                <ul className="flex gap-8">
                    <li><button><Wishlist/></button></li>
                    <li><button><AccountIcon/></button></li>
                    <li><Link to="/cart-items"><CartIcon/></Link></li>
                </ul>
                }
                {variant==='auth' &&
                    <ul className="flex gap-8">
                        <li className="text-black border border-black hover:bg-slate-100 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none"><NavLink to={"/v1/login"} className={({isActive})=>isActive ?'active-link': ''}>Login</NavLink></li>
                        <li className="text-black border border-black hover:bg-slate-100 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none"><NavLink to={"/v1/register"} className={({isActive})=>isActive ?'active-link': ''}>Register</NavLink></li>
                    </ul>
                }
            </div>
        </nav>
        </>
    )
}

export default Navigation;