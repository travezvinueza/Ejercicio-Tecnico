import { Component, OnInit } from '@angular/core';
import { ToastModule } from 'primeng/toast';
import { Client } from '../../interfaces/client';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ClientService } from '../../services/client.service';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

declare var $: any;

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ToastModule, ReactiveFormsModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  clients: Client[] = [];
  clientDetail !: FormGroup;
  clientToDelete: Client | undefined;

  constructor(
    private clientService: ClientService,
    private formBuilder: FormBuilder,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllClientes();
    this.clientDetail = this.formBuilder.group({
      id: [''],
      name: [''],
      lastname: [''],
    });
  }

  //metodo para darle movimiento al modal
  ngAfterViewInit() {
    $('.modal-dialog').draggable({
      handle: ".modal-header"
    });
  }

  getAllClientes() {
    this.clientService.listarClientes().subscribe(
      (data) => {
        this.clients = data;
      }),
      (error: HttpErrorResponse) => {
        console.error(error);
      }
  }

  addCliente() {
    const nuevoCliente: Client = {
      id: 0,
      name: this.clientDetail.value.name,
      lastname: this.clientDetail.value.lastname,
      orders: []
    };

    this.clientService.crearCliente(nuevoCliente).subscribe({
      next: () => {
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Cliente creado exitosamente" });
        this.clientDetail.reset();
        this.getAllClientes();
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
      }
    });
  }

  editCliente(id: number) {
    this.clientService.obtenerClientePorId(id).subscribe({
      next: (client) => {
        this.clientDetail.patchValue(client);
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
      }
    });
  }

  updateClient() {
    const clienteActualizado: Client = {
      id: this.clientDetail.value.id,
      name: this.clientDetail.value.name,
      lastname: this.clientDetail.value.lastname,
      orders: []
    };

    this.clientService.actualizarCliente(clienteActualizado.id, clienteActualizado).subscribe({
      next: () => {
        this.msgService.add({ severity: 'warn', summary: "Éxito", detail: "Cliente actualizado" });
        this.clientDetail.reset();
        this.getAllClientes();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar el cliente:', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar el cliente' });
      }
    });
  }

  showConfirmation(cliente: Client) {
    this.clientToDelete = cliente;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar el cliente?"
    });
  }

  confirmDelete() {
    if (this.clientToDelete && this.clientToDelete.id) {
      this.deleteClient(this.clientToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteClient(id: number) {
    this.clientService.eliminarCliente(id).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Exito', detail: 'Cliente eliminado' });
        this.getAllClientes();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar el cliente', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar el cliente' });
      }
    });
  }

}
