import axios from "axios";
import { API_BASE_URL } from "./constant"
import {getHeaders} from "./constant"
import { data } from "react-router-dom";

export const placeOrderApi = async (data)=>{
    const url = API_BASE_URL + '/api/order';
    try{
        const response = await axios(url,{
            method:"POST",
            data: data,
            headers:getHeaders()
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

export const confirmPaymentApi = async (data)=>{
    const url = API_BASE_URL + '/api/order/update-payment';
    try{
        const response = await axios(url,{
            method:"POST",
            data: data,
            headers:getHeaders()
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}


export const fetchOrderApi = async ()   => {
    const url = API_BASE_URL + `/api/order/user`;
    try{
        const response = await axios(url,{
            method:"GET",
            headers:getHeaders()
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}


export const cancelOrderApi = async (id)   => {
    const url = API_BASE_URL + `/api/order/${id}`;
    try{
        const response = await axios(url,{
            method:"DELETE",
            headers:getHeaders()
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}