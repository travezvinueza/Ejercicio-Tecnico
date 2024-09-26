import { Orden } from "./orden";

export interface Cliente {
    id: number;
    nombre: string;
    apellido: string;
    ordenes: Orden[];
}
