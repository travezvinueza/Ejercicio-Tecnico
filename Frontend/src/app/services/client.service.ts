import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client } from '../interfaces/client';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  baseUrl = environment.API_URL;
  private clienteApi = `${this.baseUrl}/clients`;

  constructor(public http: HttpClient) { }


  crearCliente(cliente: Client): Observable<Client> {
    return this.http.post<Client>(`${this.clienteApi}/create-client`, cliente);
  }

  obtenerClientePorId(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.clienteApi}/getById-client/${id}`);
  }

  listarClientes(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.clienteApi}/list-clients`);
  }

  actualizarCliente(id: number, cliente: Client): Observable<Client> {
    return this.http.put<Client>(`${this.clienteApi}/update-client/${id}`, cliente);
  }

  eliminarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.clienteApi}/delete-client/${id}`);
  }

}
