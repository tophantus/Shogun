import { createBrowserRouter } from "react-router-dom";
import Shop from "./Shop";
import ProductListPage from "./Pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./Pages/ShopApplicationWrapper";
import ProductDetails from "./Pages/ProductDetailsPage/ProductDetails";
import { loadProductBySlug } from "./routes/product";
import AuthenticationWrapper from "./Pages/AuthenticationWrapper";
import Login from "./Pages/Login/Login";
import Register from "./Pages/Register/Register";
import OAuth2LoginCallback from "./Pages/OAuth2LoginCallback";
import Cart from "./Pages/Cart/Cart";
import Account from "./Pages/Account/Account";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import Checkout from "./Pages/Checkout/Checkout";
import PaymentPage from "./Pages/Payment/PaymentPage";

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
            },
            {
                path: "/cart-items",
                element: <Cart/>
            },
            {
                path: "/account-details",
                element: <ProtectedRoute><Account/></ProtectedRoute>
            },
            {
                path: "/checkout",
                element: <ProtectedRoute><Checkout/></ProtectedRoute>
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
    },
    {
        path: "/oauth2/callback",
        element: <OAuth2LoginCallback/>
    }
])