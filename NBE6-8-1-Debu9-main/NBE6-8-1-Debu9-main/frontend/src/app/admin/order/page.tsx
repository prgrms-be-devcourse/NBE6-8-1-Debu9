"use client"

import { useEffect, useState } from "react"
import type {components} from "@/lib/backend/apiV1/schema"

type OrderResponseDto = components["schemas"]["OrderResponseDto"]
type OrderItemResponseDto = components["schemas"]["OrderItemResponseDto"]

function OrderItem({orderItem}:{orderItem:OrderItemResponseDto}){
    return (
        <div className="border my-2">
            <p>{orderItem.product_name} {orderItem.count}개</p>
        </div>
    )
}

function Order({order, refreshOrders}:{order:OrderResponseDto, refreshOrders:()=>void}){
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
            <p>주소 : {order.order_address}</p>
            <p>총 가격 : {order.total_price}원</p>
            <div className="border p-2">
                {order.order_items!.map((e)=><OrderItem orderItem={e} key={e.orderItem_id}></OrderItem>)}

            </div>    
            <button className="cursor-pointer flex justify-self-end mt-3" onClick={onDelete}>삭제</button>
            
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