import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Articulo } from '../interfaces/articulo';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ArticuloService {

  baseUrl = environment.API_URL;
  private articleApi = `${this.baseUrl}/articulos`;

  constructor(private http: HttpClient) { }


  listarArticulos(): Observable<Articulo[]> {
    return this.http.get<Articulo[]>(`${this.articleApi}/listar-articulos`);
  }

  crearArticulo(articulo: Articulo): Observable<Articulo> {
    return this.http.post<Articulo>(`${this.articleApi}/crear-articulo`, articulo);
  }

  actualizarArticulo(articulo: Articulo, ordenId: number): Observable<Articulo> {
    return this.http.put<Articulo>(`${this.articleApi}/actualizar-articulo/${ordenId}`, articulo)
  }

  obtenerArticuloPorId(id: number): Observable<Articulo> {
    return this.http.get<Articulo>(`${this.articleApi}/obtener-articulo/${id}`);
  }

  eliminarArticulo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.articleApi}/eliminar-articulo/${id}`);
  }

}
