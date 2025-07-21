"use client";
import { Product } from "@/lib/type/product";
import { useCart } from "@/hooks/useCart";
import { useState } from "react";

const ProductInfo = ({
  product,
  onClose,
}: {
  product: Product;
  onClose: () => void;
}) => {
  const { addToCart } = useCart();
  const [quantity, setQuantity] = useState<number>(1); //장바구니에 담을 수량 (초기값 1)

  const increase = () => {
    setQuantity(quantity + 1);
  };
  const decrease = () => {
    if (quantity == 1) {
      alert("최소 주문 수량은 1개 입니다.");
      return;
    }
    setQuantity(quantity - 1);
  };

  return (
    <div className="absolute top-0 w-[50%] h-full left-[50%] bg-white border-l border-l-gray-300">
      <img
        src="/images/goToBack.png"
        width={30}
        height={30}
        onClick={onClose}
        className="ml-3 mt-3 hover:opacity-50"
      />

      <div className="flex p-10 gap-20">
        <img src={product.imageUrl} width={250} height={250} />
        <div className="mt-15">
          <p className="text-sm font-bold">{product.name}</p>
          <p className="text-sm font-thin">{product.engName}</p>
          <br />
          <p className="text-xs">{product.price}원</p>
          <div className="flex gap-2 mt-3">
            <img onClick={decrease} src="/images/minus.png" />
            <p>{quantity}</p>
            <img onClick={increase} src="/images/plus.png" />
          </div>
          <button
            onClick={() => addToCart(product, quantity)}
            className="cursor-pointer text-white bg-[#005034] rounded-xl py-1 px-8 mt-6"
          >
            장바구니 담기
          </button>
        </div>
      </div>
      <hr className="mt-5 mx-10 border border-gray-300"></hr>
      <p className="p-20">{product.info}</p>
    </div>
  );
};

export default ProductInfo;
