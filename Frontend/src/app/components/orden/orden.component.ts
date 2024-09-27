import { ArticuloService } from './../../services/articulo.service';
import { ClienteService } from './../../services/cliente.service';
import { Orden } from './../../interfaces/orden';
import { OrdenService } from './../../services/orden.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
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
  clientes: Cliente[] = [];
  articulos: Articulo[] = [];
  ordenDetalle !: FormGroup;
  ordenToDelete: Orden | undefined;

  constructor(
    private ordenService: OrdenService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private formBuilder: FormBuilder,
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
        this.articulos = data;
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

    this.ordenService.crearOrden(nuevaOrden).subscribe({
      next: () => {
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden creada exitosamente" });
        this.getAllOrdenes();
        this.ordenDetalle.reset();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al crear la orden', error);
      }
    });
  }

  actualizarOrden() {
    const ordenId = this.ordenDetalle.value.id;
    const clienteId = this.ordenDetalle.value.clienteId;

    if (!clienteId) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
      return;
    }

    const selectedArticulo = this.ordenDetalle.value.articulo;

    if (!selectedArticulo) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un artículo' });
      return;
    }

    debugger;

    const ordenActualizada: Orden = {
      id: ordenId,
      codigo: this.ordenDetalle.value.codigo,
      fecha: this.ordenDetalle.value.fecha,
      articulos: [selectedArticulo],
      clienteId: clienteId
    };
    debugger;
    this.ordenService.actualizarOrden(ordenActualizada).subscribe({
      next: () => {
        debugger
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden actualizada exitosamente" });
        this.getAllOrdenes();
        this.ordenDetalle.reset();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar la orden', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar la orden' });
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
          clienteId: orden.clienteId,
          articulo: orden.articulos[0]
        });
      },
      error: (error) => {
        console.error("Error al obtener la orden: ", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener la orden' });
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
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar la orden', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar la orden' });
      }
    });
  }

}
