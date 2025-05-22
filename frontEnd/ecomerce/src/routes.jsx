import { createBrowserRouter } from "react-router-dom";
import Shop from "./Shop";
import ProductListPage from "./Pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./Pages/ShopApplicationWrapper";
import ProductDetails from "./Pages/ProductDetailsPage/ProductDetails";
import { loadProductBySlug } from "./routes/product";
import AuthenticationWrapper from "./Pages/AuthenticationWrapper";
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <ShopApplicationWrapper/>,
        children: [
            {
                path: "/",
                element: <Shop/>
            },
            {
                path: "/women",
                element: <ProductListPage categoryType={"WOMEN"}/>
            },
            {
                path: "/men",
                element: <ProductListPage categoryType={"MEN"}/>
            },
            {
                path: "/product/:slug",
                loader: loadProductBySlug,
                element: <ProductDetails />
            }
        ]
    },
    {
        path: "/v1/",
        element: <AuthenticationWrapper/>,
        children: [
            {
                path:"login",
                element: <Login/>
            },
            {
                path:"register",
                element: <Register/>
            }
        ]
    }
])