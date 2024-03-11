'use client'
import {useEffect, useState} from "react";

export const useBrowserLanguage = () => {
  const [browserLanguage, setBrowserLanguage] = useState<string>()
  useEffect(() => {
    setBrowserLanguage(window.navigator.language)
  }, []);
  return browserLanguage
}