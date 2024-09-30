import { Article, OrderArticleDto } from "./article";

export interface Order {
    id: number;
    code?: string;
    date?: Date;
    clientId: number;
    orderArticleDtos: OrderArticleDto[];
    articles: Article[];
}
