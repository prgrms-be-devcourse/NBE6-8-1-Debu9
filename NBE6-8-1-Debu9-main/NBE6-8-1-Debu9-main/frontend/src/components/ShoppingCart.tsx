import { useCart } from "@/hooks/useCart";

const ShoppingCart = () => {
  const {
    cartItems,
    selectedIds,
    toggleAll,
    toggleOne,
    removeSelectedItems,
    isAllSelected,
    updateQuantity,
  } = useCart();

  const increase = (id: number, currentQty: number) => {
    updateQuantity(id, currentQty + 1);
  };

  const decrease = (id: number, currentQty: number) => {
    if (currentQty === 1) {
      alert("최소 주문 수량은 1개 입니다.");
      return;
    }
    updateQuantity(id, currentQty - 1);
  };

  return (
    <div className="flex flex-col px-20 py-10 bg-white pt-10 w-1/2">
      <div className="flex items-center gap-2 mb-4 border-b border-b-gray-300 pb-3">
        <input type="checkbox" onChange={toggleAll} checked={isAllSelected} />
        <span className="text-sm">전체 선택</span>
        <button
          onClick={removeSelectedItems}
          className="cursor-pointer ml-53 border border-gray-300 text-sm py-1 px-3 rounded-xl"
        >
          선택 삭제
        </button>
      </div>

      <ul className="w-full flex flex-col gap-4">
        {cartItems.map((item) => (
          <li
            key={item.product.id}
            className="flex w-full gap-4 py-3 items-start"
          >
            <input
              type="checkbox"
              checked={selectedIds.includes(item.product.id)}
              onChange={() => toggleOne(item.product.id)}
            />

            <img src={item.product.imageUrl} className="w-40 h-40" />

            <div className="flex flex-col gap-8">
              <div className="mt-3 w-full">
                <p className="text-sm font-bold">{item.product.name}</p>
                <p className="text-sm text-gray-500">{item.product.engName}</p>
                <p className="text-xs mt-1">
                  {item.quantity}개 / {item.product.price * item.quantity}원
                </p>
              </div>
              <div className="flex gap-2">
                <img
                  src="/images/minus.png"
                  onClick={() => decrease(item.product.id, item.quantity)}
                />
                <p>{item.quantity}</p>
                <img
                  src="/images/plus.png"
                  onClick={() => increase(item.product.id, item.quantity)}
                />
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ShoppingCart;
