import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Orden } from '../interfaces/orden';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrdenService {

  baseUrl = environment.API_URL;
  private ordenApi = `${this.baseUrl}/ordenes`;

  constructor(private http: HttpClient) { }


  crearOrden(orden: Orden): Observable<Orden> {
    return this.http.post<Orden>(`${this.ordenApi}/crear/orden`, orden);
  }

  actualizarOrden(orden: Orden): Observable<Orden> {
    return this.http.put<Orden>(`${this.ordenApi}/actualizar-orden`, orden);
  }

  obtenerOrdenPorId(id: number): Observable<Orden> {
    return this.http.get<Orden>(`${this.ordenApi}/obtener-orden/${id}`);
  }

  listarOrdenes(): Observable<Orden[]> {
    return this.http.get<Orden[]>(`${this.ordenApi}/listar-ordenes`);
  }

  eliminarOrden(id: number): Observable<void> {
    return this.http.delete<void>(`${this.ordenApi}/eliminar-orden/${id}`);
  }

}
