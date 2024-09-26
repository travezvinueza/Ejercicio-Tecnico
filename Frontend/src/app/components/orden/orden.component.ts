import { ArticuloService } from './../../services/articulo.service';
import { ClienteService } from './../../services/cliente.service';
import { Orden } from './../../interfaces/orden';
import { OrdenService } from './../../services/orden.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { Cliente } from '../../interfaces/cliente';
import { RouterModule } from '@angular/router';
import { Articulo } from '../../interfaces/articulo';

declare var $: any;

@Component({
  selector: 'app-orden',
  standalone: true,
  imports: [CommonModule, ToastModule, ReactiveFormsModule, RouterModule],
  templateUrl: './orden.component.html',
  styleUrl: './orden.component.css'
})
export class OrdenComponent implements OnInit {

  ordenes: Orden[] = [];
  ordenDetalle !: FormGroup;
  clientes: Cliente[] = [];
  articulos: Articulo[] = [];
  ordenToDelete: Orden | undefined;

  constructor(
    private ordenService: OrdenService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllOrdenes();
    this.getAllClientes();
    this.getAllArticulos();
    this.ordenDetalle = this.formBuilder.group({
      id: [''],
      codigo: [''],
      fecha: [''],
      clienteId: [''],
      articulo: this.formBuilder.control('') 
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
        this.clientes = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al obtener los clientes", error);
      }
    });
  }

  getAllArticulos() {
    this.articuloService.listarArticulos().subscribe({
      next: (data: Articulo[]) => {
        this.articulos = data; // <-- Guarda los artículos en una lista para mostrarlos en el formulario
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al obtener los artículos", error);
      }
    });
  }

  getAllOrdenes() {
    this.ordenService.listarOrdenes().subscribe({
      next: (data: Orden[]) => {
        this.ordenes = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al obtener las ordenes", error);
      }
    });
  }

  addOrden() {
    const clienteId = this.ordenDetalle.value.clienteId;

    if (!clienteId) {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
        return;
    }

    const selectedArticulo = this.ordenDetalle.value.articulo; 

    if (!selectedArticulo) {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Artículo no encontrado' });
        return;
    }

    const nuevaOrden: Orden = {
        id: 0,
        codigo: this.ordenDetalle.value.codigo,
        fecha: this.ordenDetalle.value.fecha,
        articulos: [selectedArticulo], 
        clienteId: clienteId
    };

    this.ordenService.crearOrden(nuevaOrden, clienteId).subscribe({
        next: () => {
            this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden creada exitosamente" });
            this.getAllOrdenes();
            this.cdr.detectChanges();
            this.ordenDetalle.reset();
        },
        error: (error: HttpErrorResponse) => {
            console.error('Error al crear la orden', error);
        }
    });
}

  editOrden(id: number) {
    this.ordenService.obtenerOrdenPorId(id).subscribe({
      next: (orden: Orden) => {
        this.ordenDetalle.patchValue({
          id: orden.id,
          codigo: orden.codigo,
          fecha: orden.fecha,
        });
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error("Error al obtener la orden: ", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener la orden' });
      }
    });
  }

  actualizarOrden() {
    const ordenActualizado: Orden = {
      id: this.ordenDetalle.value.id,
      codigo: this.ordenDetalle.value.codigo,
      fecha: this.ordenDetalle.value.fecha,
      articulos: this.ordenDetalle.value.articulo,
      clienteId: 0
    };

    const clienteId = this.ordenDetalle.value.clienteId;

    this.ordenService.actualizarOrden(ordenActualizado.id, ordenActualizado).subscribe({
      next: () => {
        this.msgService.add({ severity: 'warn', summary: "Éxito", detail: "Orden actualizada correctamente" });
        this.getAllClientes();
        this.cdr.detectChanges();
        this.ordenDetalle.reset();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar la orden:', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar la orden' });
      }
    });
  }

  showConfirmation(orden: Orden) {
    this.ordenToDelete = orden;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar la orden"
    });
  }

  confirmDelete() {
    if (this.ordenToDelete && this.ordenToDelete.id) {
      this.deleteOrden(this.ordenToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteOrden(id: number) {
    this.ordenService.eliminarOrden(id).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Orden eliminada exitosamente' });
        this.getAllOrdenes();
        this.cdr.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar la orden', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar la orden' });
      }
    });
  }


}