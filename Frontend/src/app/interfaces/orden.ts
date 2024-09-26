import { Articulo } from "./articulo";

export interface Orden {
    id: number;
    codigo: string;
    fecha: Date;
    clienteId: number;
    articulos: Articulo[];
}
