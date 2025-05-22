import { Component, useEffect, useState } from 'react'
import './Shop.css'

import Navigation from './components/Navigation/Navigation'
import HeroSection from './components/HeroSection/HeroSection'
import NewArrivals from './components/Sections/NewArrivals'
import Category from './components/Sections/Categories/Category'
import 'react-multi-carousel/lib/styles.css';
import content from "./data/content.json"
import Footer from './components/Footer/Footer'
import { fetchCategories } from './api/fetchCategories'
import { useDispatch } from 'react-redux'
import { loadCategories } from './store/features/category'
import { setLoading } from './store/features/common'

function Shop() {

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(setLoading(true))
    fetchCategories().then(res => {
      dispatch(loadCategories(res));
    }).catch(err => {

    }).finally(() => {
      dispatch(setLoading(false));
    })
  }, [dispatch])

  return (
    <>
      <HeroSection/>
      <NewArrivals/>
      {content?.pages?.shop?.sections && content?.pages?.shop?.sections?.map((item, index) => <Category key={item?.title+index} {...item} />)}
      <Footer content={content?.footer}/>
    </>
  )
}

export default Shop
