import { useCart } from "@/hooks/useCart";
import { CartItem } from "@/lib/type/cartItem";
import { useState } from "react";

const CartOrder = () => {
  const { selectedItems, total_price, total_item } = useCart();

  return (
    <div className="border-l border-l-gray-300 pt-10 px-20 w-1/2 flex flex-col gap-8">
      <OrderSummary total_price={total_price} />
      <UserInfo
        selectedItems={selectedItems}
        total_price={total_price}
        total_item={total_item}
      />
    </div>
  );
};

const OrderSummary = ({ total_price }: { total_price: number }) => {
  return (
    <div className="flex flex-col gap-5 border-b border-b-gray-300 pb-5">
      <p className="text-xl font-semibold mb-3">구매 금액</p>
      <p className="text-s font-light">상품 금액: {total_price}원</p>
      <p className="text-s font-light">배송비: 무료배송</p>
    </div>
  );
};

const UserInfo = ({
  selectedItems,
  total_price,
  total_item,
}: {
  selectedItems: CartItem[];
  total_price: number;
  total_item: number;
}) => {
  const { removeSelectedItems } = useCart();
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");

  const orderSubmit = async () => {
    const orderData = {
      email: email,
      address: address,
      items: selectedItems.map((item) => ({
        productId: item.product.id,
        count: item.quantity,
      })),
    };

    try {
      const response = await fetch("http://localhost:8080/api/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
      });
      console.log("보낼 주문 데이터:", orderData);

      const result = await response.json();
      console.log("주문 응답:", result);
      alert("주문이 완료되었습니다.");

      removeSelectedItems();
      setEmail("");
      setAddress("");
    } catch (err) {
      console.error("주문 실패:", err);
      alert("주문을 실패하였습니다.");
    }
  };
  return (
    <div className="flex flex-col gap-4">
      <p className="text-xl font-semibold mb-5">구매자 정보</p>
      <label>이메일</label>
      <input
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="email"
        className="border border-gray-300 bg-white rounded-lg px-3 py-2
        bg-[url('/images/email.png')] bg-no-repeat bg-[length:20px_20px] bg-[position:15px_center] pl-15"
      />
      <label>주소</label>
      <input
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        placeholder="address"
        className="border border-gray-300 bg-white rounded-lg px-3 py-2
        bg-[url('/images/address.png')] bg-no-repeat bg-[length:20px_20px] bg-[position:15px_center] pl-15"
      />
      <button
        onClick={orderSubmit}
        className="mt-10 cursor-pointer mt-4 px-4 py-2 bg-[#005034] text-white rounded"
      >
        {total_price}원 주문하기 ({total_item}개)
      </button>
    </div>
  );
};

export default CartOrder;
