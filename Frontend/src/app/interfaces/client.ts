import { Order } from "./order";

export interface Client {
    id: number;
    name: string;
    lastname: string;
    orders: Order[];
}
