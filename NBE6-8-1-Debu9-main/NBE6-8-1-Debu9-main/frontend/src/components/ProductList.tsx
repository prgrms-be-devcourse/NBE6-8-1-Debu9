import { Product } from "@/lib/type/product";

const ProductList = ({
  products,
  onSelect,
}: {
  products: Product[];
  onSelect: (product: Product) => void;
}) => {
  return (
    <>
      <div className="relative">
        <ul className="mt-15 px-25 divide-y divide-gray-300">
          {products.map((product) => (
            <li
              key={product.id}
              className="flex gap-8 cursor-pointer hover:opacity-60"
              onClick={() => onSelect(product)}
            >
              <img src={product.imageUrl} width={120} height={120} />

              <div className="mt-6">
                <p className="text-sm font-bold">{product.name}</p>
                <p className="text-sm font-thin">{product.engName}</p>
                <br />
                <p className="text-xs">{product.price}원</p>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default ProductList;
