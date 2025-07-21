"use client";
import {
  useState,
  useEffect,
  useRef,
  FormEventHandler,
  FormEvent,
} from "react";
import Link from "next/link";
import type { components } from "@/lib/backend/apiV1/schema";

type OrderResponseDto = components["schemas"]["OrderResponseDto"];
type OrderItemResponseDto = components["schemas"]["OrderItemResponseDto"];

interface OrderItem {
  order_item_num: number;
  delivery_state: string;
  product_name: string;
  product_eng_name: string;
  price: number;
  image_url: string;
  count: number;
}

interface Order {
  order_number: string;
  order_address: string;
  total_price: number;
  total_count: number;
  created_date: string;
  order_items: OrderItem[];
}

export function OrderItem({ orderItem }: { orderItem: OrderItemResponseDto }) {
  return (
    <div className="border my-2 rounded-xl flex border-gray-300">
      <div className="p-2">
        <a className="font-bold text-xl">{orderItem.delivery_state}</a>
        <div className="flex">
          <img
            src={orderItem.image_url}
            className="w-[100] h-[100] object-cover"
          ></img>
          <div>
            <div className="w-[400]">
              <a className="text-lg">{orderItem.product_name}</a>
            </div>
            <div className="mt-1">
              <a>{orderItem.product_eng_name}</a>
            </div>
            <div className="mt-2 flex">
              <a className="block">
                {orderItem.price!.toLocaleString("ko-KR")} 원
              </a>
              <a className="block ml-1">{orderItem.count} 개</a>
            </div>
          </div>
        </div>
      </div>

      <div className="border-l border-l-gray-300 ml-3 px-2">
        <div className="border rounded my-2 border-gray-300">
          <Link
            href={`/order/delivery/${orderItem.orderItem_id}`}
            className="text-center block m-1 p-1"
          >
            배송 조회
          </Link>
        </div>
        <div className="border rounded my-2 border-gray-300">
          <a className="text-center block m-1 p-1">교환, 반품 신청</a>
        </div>
        <div className="border rounded my-2 border-gray-300">
          <a className="text-center block m-1 p-1">판매자 문의</a>
        </div>
      </div>
    </div>
  );
}

function OrderList({ data }: { data: OrderResponseDto }) {
  return (
    <div className="p-3 rounded-xl my-3 shadow-[0_0_5px_rgba(0,0,0,0.3)]">
      <span className="flex justify-between">
        <a className="block text-xl font-bold">{data.created_date} 주문</a>
        <Link
          className="text-blue-500 block"
          href={`/order/${data.order_number}`}
        >
          주문 상세 보기&gt;
        </Link>
      </span>
      {data.order_items!.map((e) => (
        <OrderItem orderItem={e} key={e.orderItem_id}></OrderItem>
      ))}
    </div>
  );
}

export default function page() {
  const [memberEmail, setMemberEmail] = useState("");
  const [orders, setOrders] = useState<OrderResponseDto[]>([]);

  const onSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      // const
      const response = await fetch(
        `http://localhost:8080/api/orders/search?memberEmail=${memberEmail}`
      );
      const data: OrderResponseDto[] = await response.json();
      setOrders(data);
    } catch (error) {}
  };

  return (
    <div className="mt-10 text-black">
      <div className="flex justify-center">
        <form onSubmit={onSubmit}>
          <input
            type="email"
            value={memberEmail}
            onChange={(e) => setMemberEmail(e.target.value)}
            className="border p-2"
            placeholder="이메일 주소를 작성해 주세요."
          ></input>
          <button
            className="border ml-3 rounded p-2 cursor-pointer"
            type="submit"
          >
            조회
          </button>
        </form>
      </div>
      <div className="flex justify-center mt-3">
        {orders.length == 0 ? (
          <p>조회된 주문이 없습니다.</p>
        ) : (
          <ul>
            {orders.map((e) => (
              <OrderList data={e} key={e.order_number}></OrderList>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
