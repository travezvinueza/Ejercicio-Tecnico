import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Order } from '../interfaces/order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  baseUrl = environment.API_URL;
  private ordenApi = `${this.baseUrl}/orders`;

  constructor(private http: HttpClient) { }


  listarOrdenes(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.ordenApi}/list-orders`);
  }

  crearOrden(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.ordenApi}/create-order`, order);
  }

  actualizarOrden(order: Order): Observable<Order> {
    return this.http.put<Order>(`${this.ordenApi}/update-order`, order);
  }

  obtenerOrdenPorId(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.ordenApi}/getById-order/${id}`);
  }

  eliminarOrden(id: number): Observable<void> {
    return this.http.delete<void>(`${this.ordenApi}/delete-order/${id}`);
  }

}
