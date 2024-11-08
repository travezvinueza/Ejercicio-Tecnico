import { OrderArticle } from "./orderArticle";

export interface Order {
    id: number;
    code?: string;
    date?: Date;
    clientId: number;
    orderArticles: OrderArticle[];
}
