import { head } from 'lodash';
import React from 'react'
import { Admin, fetchUtils, withLifecycleCallbacks } from 'react-admin';
import simpleRestProvider from "ra-data-simple-rest";


const httpClient = (url, option={}) => {
    const token = localStorage.getItem("authToken");
    if (!option.headers) {
        option.headers = new Headers();
        option.headers.set("Authorization", `Bearer ${token}`);
        return fetchUtils.fetchJson(url, headers);
    }
}

const dataProvider = withLifecycleCallbacks(simpleRestProvider('http://localhost:8080/api',httpClient),[
    {

    }
])

const AdminPanel = () => {


  return (
    <Admin>

    </Admin>
  )
}

export default AdminPanel