"use client"

import { useEffect, useState } from "react"
import type {components} from "@/lib/backend/apiV1/schema"

type OrderResponseDto = components["schemas"]["OrderResponseDto"];
type OrderItemResponseDto = components["schemas"]["OrderItemResponseDto"];
type OrderUpdateReqBody = components["schemas"]["OrderUpdateReqBody"];
type OrderItemUpdateReqBody = components["schemas"]["OrderItemUpdateReqBody"]

function safeParseInt(str: string): number {
    const num = parseInt(str, 10);
    return isNaN(num) ? 0 : num;
  }

function OrderItem({orderItem}:{orderItem:OrderItemResponseDto}){
    const [oi, setOi] = useState<OrderItemResponseDto>(orderItem);
    return (
        <div className="border my-2">
            <p>{orderItem.product_name} <input type="number" value={oi.count} onChange={(e)=>setOi({...oi, count:safeParseInt(e.target.value)})}></input>개</p>
            <p>예상 배송 시간 : <input type="text" value={oi.expectedDeliveryDate} onChange={(e)=>{setOi({...oi, expectedDeliveryDate:e.target.value})}}></input></p>
            <p>배송 상태 : <input type="text" value={oi.delivery_state} onChange={(e)=>setOi({...oi, delivery_state:oi.delivery_state})}></input></p>
        </div>
    )
}

function Order({order, refreshOrders}:{order:OrderResponseDto, refreshOrders:()=>void}){
    const [o, setO] = useState<OrderResponseDto>(order);
    const onModify = async () => {
        const url = "http://localhost:8080/api/orders"
        const data:OrderUpdateReqBody = {
            orderNum:o.order_number!,
            address:o.order_address!,
            items:order.order_items!.map((e)=>{
                const item:OrderItemUpdateReqBody = {
                    productId:e.product_id!,
                    deliveryState:e.delivery_state!,
                    expectedDeliveryTime:e.expectedDeliveryDate!,
                    count:e.count!
                };
                return item;
            })

        }
        const res = await fetch(url,{
            method:"PUT",
            body: JSON.stringify(data)
        })
    }
    const onDelete = async () => {
        const url = `http://localhost:8080/api/orders/${order.order_id}`
        const res = await fetch(url,{
            method:"DELETE"
        })
        refreshOrders();
    }
    return (
        <div className="border p-2 my-2 rounded-xl">
            <p>주문 번호 : {order.order_number}</p>
            <p>주소 : <input className="border" type="text" value={o.order_address} onChange={(e)=>{setO({...o, order_address:e.target.value})}}></input></p>
            <p>총 가격 : <input type="number " value={o.total_price} onChange={(e)=>setO({...o, total_price:safeParseInt(e.target.value)})}></input>원</p>
            <div className="border p-2">
                {order.order_items!.map((e)=><OrderItem orderItem={e} key={e.orderItem_id}></OrderItem>)}

            </div>
            <div className="flex justify-between mt-3">
                <button className="cursor-pointer" onClick={onModify}>수정</button>
                <button className="cursor-pointer" onClick={onDelete}>삭제</button>

            </div>
            
        </div>
    )
}

export default function Page(){
    const [orders, setOrders] = useState<OrderResponseDto[]>([])
    const refreshOrders = async () =>{
        const url = "http://localhost:8080/api/orders";
        const res = await fetch(url);
        const data = await res.json();
        setOrders(data);
    }
    useEffect( ()=>{
        refreshOrders()
    }, [])
    return (
        <div className="flex justify-center text-black">
            <div>
                {orders.map((e)=><Order order={e} refreshOrders={refreshOrders} key={e.order_id}></Order>)}
            </div>
        </div>
    )
}