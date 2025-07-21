"use client";

import { useEffect, useState } from "react";
import { components } from "@/lib/backend/apiV1/schema";
import { useParams } from "next/navigation";

type DeliveryDto = components["schemas"]["DeliveryDto"];

function getMessage(deliveryState: string): string {
  switch (deliveryState) {
    case "배송완료":
      return "고객님이 주문하신 상품이 배송완료 되었습니다.";
    case "배송중":
      return " 상품이 배송 중입니다. 곧 도착할 예정입니다.";
    case "배송준비중":
      return "상품이 배송 준비 중입니다.";
  }
  return "";
}

export default function Page() {
  const param = useParams();
  const orderitem_id = param?.orderitem_id;

  const [data, setData] = useState<DeliveryDto>();

  useEffect(() => {
    console.log(param);
    const delevery = async () => {
      const response = await fetch(
        `http://localhost:8080/api/orders/${orderitem_id}/delivery-schedule`
      );
      const data: DeliveryDto = await response.json();
      setData(data);
    };

    delevery();
  }, [orderitem_id]);
  if (!data) return <div className="text-center">로딩 중...</div>;
  const isoString = data.expectedDeliveryDate;
  const date = new Date(isoString.split(".")[0]);
  const formatted = date.toLocaleDateString("en-US", {
    month: "2-digit",
    day: "2-digit",
  });

  return (
    <div className="flex justify-center text-black mt-10">
      <div>
        <p className="text-2xl font-bold">배송조회</p>
        <div className="bg-gray-300 w-[800] border border-gray-400 px-10 py-3 mt-4">
          <div className="flex justify-center">
            <p className="block text-3xl">{formatted}</p>
            <p className="block ml-5 text-3xl">{data.deliveryState}</p>
          </div>
          <p className="block justify-self-center">
            {data?.deliveryState && getMessage(data.deliveryState)}
          </p>
        </div>
        <table className="flex justify-center mt-10">
          <tbody>
            <tr>
              <td className="text-gray-500 w-[100] text-lg">받는사람</td>
              <td className="text-lg">{data.email}</td>
            </tr>
            <tr>
              <td className="text-gray-500 w-[100] text-lg">받는주소</td>
              <td className="text-lg">{data.address}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}
