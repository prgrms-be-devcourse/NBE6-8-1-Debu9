"use client";

import React, {
  createContext,
  useContext,
  useState,
  ReactNode,
  useMemo,
} from "react";
import { CartItem } from "@/lib/type/cartItem";
import { Product } from "@/lib/type/product";

type CartContextType = {
  cartItems: CartItem[];
  selectedIds: number[];
  addToCart: (product: Product, quantity: number) => void;
  toggleAll: () => void;
  toggleOne: (id: number) => void;
  removeSelectedItems: () => void;
  isAllSelected: boolean;
  selectedItems: CartItem[];
  total_price: number;
  total_item: number;
  updateQuantity: (productId: number, quantity: number) => void;
};

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider = ({ children }: { children: ReactNode }) => {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  const addToCart = (product: Product, quantity: number) => {
    setCartItems((prev) => {
      const index = prev.findIndex((item) => item.product.id === product.id);
      if (index !== -1) {
        const updated = [...prev];
        updated[index] = {
          ...updated[index],
          quantity: updated[index].quantity + quantity,
        };
        return updated;
      } else {
        return [...prev, { product, quantity }];
      }
    });

    setSelectedIds((prev) => {
      if (!prev.includes(product.id)) {
        return [...prev, product.id];
      }
      return prev;
    });
    alert("장바구니에 담겼습니다.");
  };

  const isAllSelected =
    cartItems.length > 0 && selectedIds.length === cartItems.length;

  const toggleAll = () => {
    if (isAllSelected) {
      setSelectedIds([]);
    } else {
      setSelectedIds(cartItems.map((item) => item.product.id));
    }
  };

  const toggleOne = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((pid) => pid !== id) : [...prev, id]
    );
  };

  const removeSelectedItems = () => {
    const filtered = cartItems.filter(
      (item) => !selectedIds.includes(item.product.id)
    );
    setCartItems(filtered);
    setSelectedIds(filtered.map((item) => item.product.id));
  };

  const selectedItems = useMemo(
    () => cartItems.filter((item) => selectedIds.includes(item.product.id)),
    [cartItems, selectedIds]
  );

  const total_price = useMemo(
    () =>
      selectedItems.reduce(
        (sum, item) => sum + item.product.price * item.quantity,
        0
      ),
    [selectedItems]
  );

  const total_item = useMemo(
    () => selectedItems.reduce((sum, item) => sum + item.quantity, 0),
    [selectedItems]
  );

  const updateQuantity = (productId: number, quantity: number) => {
    setCartItems((prev) =>
      prev.map((item) =>
        item.product.id === productId
          ? { ...item, quantity: Math.max(1, quantity) }
          : item
      )
    );
  };

  return (
    <CartContext.Provider
      value={{
        cartItems,
        selectedIds,
        addToCart,
        toggleAll,
        toggleOne,
        removeSelectedItems,
        isAllSelected,
        selectedItems,
        total_price,
        total_item,
        updateQuantity,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart는 CartProvider 내부에서만 사용해야 합니다.");
  }
  return context;
};
