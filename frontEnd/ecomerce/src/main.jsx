import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Shop from './Shop.jsx'
import 'react-multi-carousel/lib/styles.css';
import React from 'react';
import ReactDOM from 'react-dom/client'
import { BrowserRouter, RouterProvider } from 'react-router-dom';
import { router } from './routes.jsx';
import { Provider} from 'react-redux'
import Navigation from './components/Navigation/Navigation.jsx';
import store from './store/store.jsx'
import ShopApplicationWrapper from './Pages/ShopApplicationWrapper.jsx';

// createRoot(document.getElementById('root')).render(
//   <StrictMode>
//     <App />
//   </StrictMode>,
// )

ReactDOM.createRoot(document.getElementById('root')).render(
    <Provider store={store}>
    <RouterProvider router={router}>
      <ShopApplicationWrapper/>
    </RouterProvider>
    </Provider>
);
