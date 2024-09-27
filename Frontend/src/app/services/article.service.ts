import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Article } from '../interfaces/article';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  baseUrl = environment.API_URL;
  private articleApi = `${this.baseUrl}/articles`;

  constructor(private http: HttpClient) { }


  listarArticulos(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.articleApi}/list-articles`);
  }

  crearArticulo(articulo: Article): Observable<Article> {
    return this.http.post<Article>(`${this.articleApi}/create-article`, articulo);
  }

  actualizarArticulo(articulo: Article): Observable<Article> {
    return this.http.put<Article>(`${this.articleApi}/update-article`, articulo)
  }

  obtenerArticuloPorId(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.articleApi}/getById-article/${id}`);
  }

  eliminarArticulo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.articleApi}/delete-article/${id}`);
  }

}
