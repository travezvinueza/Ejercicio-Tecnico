import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ToastModule } from 'primeng/toast';
import { Cliente } from '../../interfaces/cliente';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
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
export class HomeComponent implements OnInit{

  clientes: Cliente[] = [];
  clienteDetalle !: FormGroup;
  clienteToDelete: Cliente | undefined;

  constructor(
    private clienteService: ClienteService,
    private formBuilder: FormBuilder,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllClientes();
    this.clienteDetalle = this.formBuilder.group({
      id: [''],
      nombre: [''],
      apellido: [''],
    });
  }

  //metodo para darle movimiento al modal
  ngAfterViewInit() {
    $('.modal-dialog').draggable({
      handle: ".modal-header"
    });
  }

  getAllClientes() {
    this.clienteService.listarClientes().subscribe({
      next: (data: Cliente[]) => {
        debugger
        this.clientes = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al obtener los clientes", error);
      }
    });
  }

  addCliente() {
    const nuevoCliente: Cliente = {
      id: 0,
      nombre: this.clienteDetalle.value.nombre,
      apellido: this.clienteDetalle.value.apellido,
      ordenes: []
    };

    this.clienteService.crearCliente(nuevoCliente).subscribe({
      next: (response: any) => {
        debugger
        if (response) {
          this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Cliente creado exitosamente" });
          this.getAllClientes();
          this.clienteDetalle.reset();
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al crear el cliente', error);
      }
    });
  }

  editCliente(id: number) {
    this.clienteService.obtenerClientePorId(id).subscribe({
      next: (producto: Cliente) => {
        this.clienteDetalle.patchValue({
          id: producto.id,
          nombre: producto.nombre,
          apellido: producto.apellido,
        });
      },
      error: (error) => {
        console.error("Error al obtener el cliente por ID", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener el cliente por ID' });
      }
    });
  }

  actualizarCliente() {
    const clienteActualizado: Cliente = {
      id: this.clienteDetalle.value.id,
      nombre: this.clienteDetalle.value.nombre,
      apellido: this.clienteDetalle.value.apellido,
      ordenes: []
    };

    this.clienteService.actualizarCliente(clienteActualizado.id, clienteActualizado).subscribe({
      next: () => {
          this.msgService.add({ severity: 'warn', summary: "Éxito", detail: "Cliente actualizado" });
          this.getAllClientes();
          this.clienteDetalle.reset();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar el cliente:', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar el cliente' });
      }
    });
  }

  showConfirmation(cliente: Cliente) {
    this.clienteToDelete = cliente;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar el cliente?"
    });
  }

  confirmDelete() {
    if (this.clienteToDelete && this.clienteToDelete.id) {
      this.deleteClient(this.clienteToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteClient(id: number) {
    this.clienteService.eliminarCliente(id).subscribe({
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
