import React from 'react'
import { Admin, fetchUtils, Resource, withLifecycleCallbacks } from 'react-admin';
import simpleRestProvider from "ra-data-simple-rest";
import ProductList from './ProductList';
import EditProduct from './EditProduct';
import CreateProduct from './CreateProduct';
import { uploadImageApi } from '../../api/fileUploadApi';
import CategoryList from './Category/CategoryList';
import EditCategory from './Category/EditCategory';


const httpClient = (url, options={}) => {
    const token = localStorage.getItem("authToken");
    if (!options.headers) {
        options.headers = new Headers();
    }
    options.headers.set("Authorization", `Bearer ${token}`);
    return fetchUtils.fetchJson(url, options);
}

const dataProvider = withLifecycleCallbacks(
  simpleRestProvider("http://localhost:8080/api", httpClient),
  [
    {
      resource: "products",
      beforeSave: async (params, dataProvider) => {
        try {
          const requestBody = { ...params };

          // Upload thumbnail
          if (params?.thumbnail?.rawFile) {
            const url = await uploadImageApi(params.thumbnail.rawFile);
            requestBody.thumbnail = url;
          }

          // Upload productResources
          const productResources = params?.productResources ?? [];
          const newProductResList = await Promise.all(
            productResources.map(async (resource) => {
              if (resource?.url?.rawFile) {
                const url = await uploadImageApi(resource.url.rawFile);
                return {
                  ...resource,
                  url
                };
              }
              return resource;
            })
          );

          const request = {
            ...requestBody,
          productResources:newProductResList
          }
          console.log("Request Body ",request);
          return request;
        } catch (err) {
          console.error("Upload error:", err);
          throw err;
        }
      }
    }
  ]
);

const AdminPanel = () => {


  return (
    <Admin dataProvider={dataProvider} basename='/admin'>
      <Resource name='products' list={ProductList} edit={EditProduct} create={CreateProduct}/>
      <Resource name='categories' list={CategoryList} edit={EditCategory}/>
    </Admin>
  )
}

export default AdminPanel