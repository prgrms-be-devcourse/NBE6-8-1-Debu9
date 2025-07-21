"use client";
import CartOrder from "@/components/CartOrder";
import ShoppingCart from "@/components/ShoppingCart";

export default function Page() {
  return (
    <div className="h-screen bg-[#F9F9F2] flex gap-8 p-4 text-black">
      <ShoppingCart />
      <CartOrder />
    </div>
  );
}
