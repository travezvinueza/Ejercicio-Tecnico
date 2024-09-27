import { Article } from "./article";

export interface Order {
    id: number;
    code?: string;
    date?: Date;
    clientId: number;
    articles: Article[];
}
