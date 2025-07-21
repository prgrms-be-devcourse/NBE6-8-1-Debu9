"use client";
import React, { useEffect, useState } from "react";
import { Product } from "../../lib/type/product";
import ProductList from "@/components/ProductList";
import ProductInfo from "@/components/ProductInfo";

export default function Page() {
  const [products, setProducts] = useState<Product[]>([]); //상품 목록
  const [selectedProd, setSelectedProd] = useState<Product | null>(null); //상품 상세 내용(선택된 상품 매개변수로 넘김)

  useEffect(() => {
    const fetchProducts = async () => {
      const url = "http://localhost:8080/api/products";
      console.log("fetching from:", url);
      const res = await fetch(url);
      const data = await res.json();
      setProducts(data);
    };

    fetchProducts();
  }, []);

  return (
    <div className="text-black">
      <ProductList products={products} onSelect={setSelectedProd} />
      {selectedProd && (
        <ProductInfo
          product={selectedProd}
          onClose={() => setSelectedProd(null)}
        />
      )}
    </div>
  );
}
