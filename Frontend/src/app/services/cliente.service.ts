import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../interfaces/cliente';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  baseUrl = environment.API_URL;
  private clienteApi = `${this.baseUrl}/clientes`;

  constructor(public http: HttpClient) { }


  crearCliente(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(`${this.clienteApi}/crear-cliente`, cliente);
  }

  obtenerClientePorId(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.clienteApi}/obtener-cliente/${id}`);
  }

  listarClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.clienteApi}/listar-clientes`);
  }

  actualizarCliente(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.clienteApi}/actualizar-cliente/${id}`, cliente);
  }

  eliminarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.clienteApi}/eliminar-cliente/${id}`);
  }

}
