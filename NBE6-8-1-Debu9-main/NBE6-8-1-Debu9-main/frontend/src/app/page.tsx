import Image from "next/image";

export default function Home() {
  return <div className="p-2 flex justify-center">
    <div className="mt-10">
      <p className="block text-2xl text-black text-center font-bold">STARBU9에 오신걸 환영합니다.</p>
      <p className="text-xl text-black text-center mt-10 font-bold">소개글</p>
      <p className="text-black mt-10">
        Starbu9는 커피에 진심인 Debu9팀이 만든, 특별한 블렌드를 선보이는 공간입니다.<br></br>
        우리의 대표 상품, Starbu9 블렌드는 신선한 커피 빈만을 엄선해 풍부한 향과 깊은 맛을 담아냈습니다.<br></br>
        커피를 사랑하는 이들을 위해, 단순한 음료가 아닌 경험과 감동을 전하는 한 잔을 제공합니다.
      </p>
    </div>
  </div>;
}
