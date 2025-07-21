"use client";
import { use, useEffect, useState } from "react";
import { OrderItem } from "../list/page";
import { components } from "@/lib/backend/apiV1/schema";
import { useParams } from "next/navigation";

type OrderResponseDto = components["schemas"]["OrderResponseDto"];

export default function Page() {
  const param = useParams();
  const order_number = param.order_number;
  const [data, setData] = useState<OrderResponseDto>();

  useEffect(() => {
    const order_content = async () => {
      const response = await fetch(
        `http://localhost:8080/api/orders/${order_number}`
      );
      const data: OrderResponseDto = await response.json();
      setData(data);
    };

    order_content();
  }, [order_number]);

  if (!data) return <div>로딩 중 ...</div>;
  return (
    <div className="text-black flex justify-center h-[1300]">
      <div>
        <div className="mt-10 py-2">
          <a className="text-2xl font-bold">주문상세</a>
        </div>
        <div className="mb-10">
          <a className="font-bold">{data.created_date} 주문</a>
          <a className="ml-3">주문 번호 : {data.order_number}</a>
        </div>
        <ul className="mb-4">
          {data.order_items?.map((e) => (
            <OrderItem orderItem={e} key={e.orderItem_id}></OrderItem>
          ))}
        </ul>
        <a className="text-xl font-semibold mt-10 block">받는사람 정보</a>
        <hr className="border-1"></hr>
        <table className="w-full">
          <tbody>
            <tr className="border-b border-b-gray-300">
              <td scope="col" className="w-[100]">
                받는사람
              </td>
              <td scope="col">{data.email}</td>
            </tr>
            <tr className="border-b border-b-gray-300">
              <td scope="col">주소</td>
              <td scope="col">{data.order_address}</td>
            </tr>
          </tbody>
        </table>

        <a className="text-xl font-semibold block mt-10">결제 정보</a>
        <hr className="border-1"></hr>
        <div className="w-full flex justify-between">
          <a className="text-lg font-midium">총 결제 금액</a>
          <a className="text-xl font-bold">{data.total_price}원</a>
        </div>
      </div>
    </div>
  );
}
