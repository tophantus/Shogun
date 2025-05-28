import React,{useCallback, useEffect, useMemo, useState} from 'react'
import { Link, useLoaderData, useParams } from 'react-router-dom'
import Breadcrumb from '../../components/Breadcrumb/Breadcrumb';
import content from '../../data/content.json'
import Rating from '../../components/Rating/Rating';
import SizeFilter from '../../components/Filters/SizeFilter';
import ProductColors from './ProductColors';
import CartIcon from '../../components/common/CartIcon'
import SvgCreditCard from '../../components/common/SvgCreditCard'
import SvgCloth from '../../components/common/SvgCloth'
import SvgShipping from '../../components/common/SvgShipping'
import SvgReturn from '../../components/common/SvgReturn'
import SectionHeading from "../../components/Sections/SectionHeading/SectionHeading"
import ProductCard from '../ProductListPage/ProductCard';
import { useDispatch, useSelector } from 'react-redux';
import _ from 'lodash';
import { getAllProducts } from '../../api/fetchProducts';
import { addItemToCartAction } from '../../store/actions/cartAction';
//const categories = content?.categories;

const extraSections = [
  {
    icon: <SvgCreditCard />,
    label: "Secure payment"
  },
  {
    icon: <SvgCloth />,
    label: "Size & fit"
  },
  {
    icon: <SvgShipping />,
    label: "Free shipping"
  },
  {
    icon: <SvgReturn />,
    label: "Free shipping & return"
  }
]

const ProductDetails = () => {
  const {product} = useLoaderData();
  const [image, setImage] = useState()
  const dispatch = useDispatch();
  const cartItems = useSelector((state) => state?.cartState?.cart);
  const [similarProducts, setSimilarProducts] = useState([]);
  const [selectedSize, setSelectedSize] = useState('');
  const [error, setError] = useState("");

  const categories = useSelector((state) => state?.categoryState?.categories);

  const productCategory = useMemo(() => {
    return categories?.find((category) => category?.id === product?.categoryId)
  }, [product, categories]);


  useEffect(()=>{
    getAllProducts(product?.categoryId,product?.categoryTypeId).then(res=>{
      const excludedProduct = res?.filter((item)=> item?.id !== product?.id);
      setSimilarProducts(excludedProduct);
    }).catch(()=>[
        
    ])
  },[product?.categoryId, product?.categoryTypeId, product?.id]);

  useEffect(() => {
    setImage(product?.thumbnail);
  }, [productCategory, product])

  const breadcrumbLink = useMemo(() => {
    const links = [{ title: 'Shop', path: '/' }];

    const category = categories?.find(c => c.id === product?.category_id);
    if (category) {
      links.push({ title: productCategory?.name, path: productCategory?.path });

      const productType = productCategory?.categoryType?.find((item) => item?.id === product?.categoryTypeId);
      if (productType) {
        links.push({
          title: productType?.name,
          path: productType?.name
        })
      }
    }

    return links;
  }, [product]);

  const addItemsToCart = useCallback(() => {
    console.log("selectedSize: ", selectedSize);
    if (!selectedSize) {
      setError("Please select size!");
    } else {
      const selectedVariant = product?.productVariants?.filter((variant) => variant?.size === selectedSize)?.[0];
      console.log("selectedVariant: ", selectedVariant);
      if (selectedVariant?.stockQuantity > 0) {
        dispatch(addItemToCartAction({
          productId: product?.id,
          name: product?.name,
          thumbnail: product?.thumbnail,
          quantity: 1,
          variant: selectedVariant,
          price: product?.price,
          subTotal: product?.price
        }))
      } else {
        setError("Out of Stock!")
      }
    }
  }, [selectedSize, dispatch, product])

  useEffect(() => {
    if (selectedSize) {
      setError("");
    }
  }, [selectedSize])

  const colors = useMemo(()=>{
      const colorSet = _.uniq(_.map(product?.productVariants,'color'));
      return colorSet
  
    },[product]);
  
    const sizes = useMemo(()=>{
      const sizeSet = _.uniq(_.map(product?.productVariants,'size'));
      return sizeSet
  
    },[product]);

  return (
    <>
    <div className='flex flex-col md:flex-row px-10'>
      <div className='w-[100%] lg:w-[50%] md:w-[40%]'>
        {/* Images */}
        <div className='flex flex-col md:flex-row'>
          <div className='w-[100%] md:w-[20%] justify-center h-[40px] md:h-[420px]'>
            {/* Stack Image */}
            <div className='flex flex-row md:flex-col justify-center h-full'>
              {
                product?.productResources?.map((item, index) => (
                  <button onClick={() => setImage(item?.url)} className='rounded-lg w-fit p-2'>
                    <img src={item?.url} className='h-[60px] w-[60px] bg-cover bg-center hover:scale-105' alt={"sample"+index} />
                  </button>
                ))
              }
            </div>
            
          </div>
          <div className='w-full md:w-[80%] flex justify-center md:pt-0 pt-10'>
            {/* Image */}
            <img src={image} alt={product?.title} className='h-full w-full max-h-[520px]
         border rounded-lg cursor-pointer object-cover block'/>
          </div>
        </div>
      </div>
      <div className='w-[60%] px-10'>
        {/* Products description */}
        <Breadcrumb links={breadcrumbLink}/>
        <p className='text-3xl pt-2'>{product?.name}</p>
        <Rating rating={product?.rating ?? 4}/>
        {/* Price */}
        <p className='text-xl bold py-2'>${product?.price}</p>
        <div className='flex flex-col pb-4'>
          <div className='flex gap-2'>
            <p className='text-sm bold'>Select size</p>
            <Link className='text-sm text-gray-500 hover:text-gray-900' to={'https://cdn.shopify.com/s/files/1/0373/1190/5851/files/WOMEN-WEBSITE-SIZE-CHARTai-copy_2048x2048.png?v=1595500410'}>{"Size guide ->"}</Link>
          </div>
        </div>
        <SizeFilter sizes={sizes} hiddenTitle={true} multi={false} onChange={(values) => {setSelectedSize(values?.[0] ?? '')}}/>
        <div>
          <p className='text-lg bold'>Colors available</p>
          <ProductColors colors={colors} />
        </div>
        <div className='flex pt-4'>
          <button className='bg-black rounded-lg w-[155px] p-2' onClick={addItemsToCart}>
            <div className='flex items-center rounded-lg bg-black text-white'><CartIcon bgColor={'black'}/>
              Add to cart
            </div>
          </button>
        </div>
        {error && <p className='text-lg text-red-600'>{error}</p>}
        <div className='grid grid-cols-2 pt-4 gap-4'>
          {
            extraSections?.map((section) => (
              <div className='flex items-center'>
                {section?.icon}
                <p className='px-2'>{section?.label}</p>
              </div>
            ))
          }
        </div>
      </div>

    </div>
    <div className='md:w-[50%] w-full pt-5'>
      <SectionHeading title={"Product Description"}/>
      <p className='px-10'>{product?.description}</p>
    </div>
    <div className='md:w-[50%] w-full pt-5'>
      <SectionHeading title={"Similar product"}/>
      <div className='pt-4 grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 gap-8 px-10 pb-10'>
      {
        similarProducts?.map((item, index) => (
          <ProductCard key={index} {...item}/>
        ))
      }
      {
        !similarProducts?.length && <p>No product found!</p>
      }
      </div>
    </div>
    </>
  )
}

export default ProductDetails