export interface Article {
    id: number;
    code: string;
    name: string;
    stock: number;
    unitPrice: number;
}

export interface OrderArticleDto {
    id: number;
    cantidad: number;
    name: string
}
