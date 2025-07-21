import { Product } from "../../lib/type/product";
import { CartItem } from "../../lib/type/cartItem";

export const dummyCartItems: CartItem[] = [
  {
    product: {
      id: 2,
      name: "스타버구 블렌드2",
      engName: "Starbu9 Blend2",
      price: 20000,
      imageUrl: "/images/starbu9_blend2.png",
      info: "저쩌구 어쩌구",
    },
    quantity: 2,
  },

  {
    product: {
      id: 1,
      name: "스타버구 블렌드1",
      engName: "Starbu9 Blend1",
      price: 20000,
      imageUrl: "/images/starbu9_blend1.png",
      info: "저쩌구 어쩌구",
    },
    quantity: 2,
  },
];
