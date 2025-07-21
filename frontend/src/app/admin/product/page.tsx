"use client"

import { useEffect, useState } from "react"
import type {components} from "@/lib/backend/apiV1/schema"

type ProductDto = components["schemas"]["ProductDto"];
type ProductReqBody = components["schemas"]["ProductReqBody"];

function safeParseInt(str: string): number {
    const num = parseInt(str, 10);
    return isNaN(num) ? 0 : num;
  }

function Product({product, refreshData}:{product:ProductDto, refreshData:()=>void}){
    const [bean, setBean] = useState<ProductDto>(product);
    
    const onModify = async() => {
        const url = `http://localhost:8080/api/products/${bean.id}`;
        const data:ProductReqBody = {
            name:bean.name!,
            imageUrl:bean.imageUrl!,
            info:bean.info!,
            price:bean.price!,
            engName:bean.engName!
        };
        const res = await fetch(url, {
            method:"PUT",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(data)
        });
    }

    const onDelete = async () => {
        const url = `http://localhost:8080/api/products/${bean.id}`;
        const res = await fetch(url, {
            method:"DELETE",
            headers:{
                "Content-Type":"application/json"
            },
        })
        refreshData()
    }

    return (
        <div className="border rounded my-2 p-2">
            <div className="flex">
                <div>
                    <input type="text" className="border block" value={bean.name} onChange={(e)=>{setBean({...bean, name:e.target.value});}}></input>
                    <input type="text" className="border block mt-3" value={bean.engName} onChange={(e)=>{setBean({...bean, engName:e.target.value})}}></input>
                    <input type="number" className="border block mt-3" value={bean.price} onChange={(e)=>{setBean({...bean, price:safeParseInt(e.target.value)})}}></input>
                    <input type="text" className="border block mt-3" value={bean.imageUrl} onChange={(e)=>{setBean({...bean, imageUrl:e.target.value})}}></input>
                </div>
                <img src={bean.imageUrl} className="ml-10 w-[100] h-[100] object-cover"></img>
            </div>

            <textarea className="border block mt-3" rows={10} cols={50} value={bean.info} onChange={(e)=>{setBean({...bean, info:e.target.value})}}></textarea>
            <div className="flex justify-between">
                <button className="p-2 cursor-pointer" onClick={onModify}>수정</button>
                <button className="p-2 cursor-pointer" onClick={onDelete}>삭제</button>
            </div>
        </div>
    )
}

export default function(){
    const [products, setProducts] = useState<ProductDto[]>([])
    const refreshData = async () => {
        const url = "http://localhost:8080/api/products";
        const res = await fetch(url);
        const data = await res.json();
        setProducts(data);
    }

    useEffect( ()=> {
        refreshData()
    }, []);

    const onAdd = () => {
        (async ()=>{
            const url:string = "http://localhost:8080/api/products";
            const data:ProductReqBody = {
                name:"예시 상품",
                imageUrl:"/images/logo.png",
                info:"예시 상품입니다.",
                price:0,
                engName:"example product"
            };
            const res = await fetch(url, {
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify(data)
            });
            refreshData();
        })()
    }

    return (
        <div className="text-black flex justify-center mt-10">
            <div>
                <button className="cursor-pointer flex justify-self-end border rounded p-2" onClick={onAdd}>추가</button>
                {products.map((e)=><Product product={e} refreshData={refreshData} key={e.id}></Product>)}
            </div>
        </div>
    )
}