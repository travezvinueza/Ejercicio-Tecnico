import { ArticleService } from '../../services/article.service';
import { ClientService } from '../../services/client.service';
import { Order } from '../../interfaces/order';
import { OrderService } from '../../services/order.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { Client } from '../../interfaces/client';
import { RouterModule } from '@angular/router';
import { Article } from '../../interfaces/article';

declare var $: any;

@Component({
  selector: 'app-orden',
  standalone: true,
  imports: [CommonModule, ToastModule, ReactiveFormsModule, RouterModule],
  templateUrl: './orden.component.html',
  styleUrl: './orden.component.css'
})
export class OrdenComponent implements OnInit {

  orders: Order[] = [];
  clients: Client[] = [];
  articles: Article[] = [];
  orderDetail !: FormGroup;
  orderToDelete: Order | undefined;

  constructor(
    private orderService: OrderService,
    private clientService: ClientService,
    private articleService: ArticleService,
    private formBuilder: FormBuilder,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllOrders();
    this.getAllClients();
    this.getAllArticles();
    this.orderDetail = this.formBuilder.group({
      id: [''],
      code: [''],
      date: [''],
      clientId: [''],
      articles: [[]]
    });
  }

  //metodo para darle movimiento al modal
  ngAfterViewInit() {
    $('.modal-dialog').draggable({
      handle: ".modal-header"
    });
  }

  getAllClients() {
    this.clientService.listarClientes().subscribe(
      (data) => {
        this.clients = data;
      }),
      (error: HttpErrorResponse) => {
        console.error(error);
      }
  }

  getAllArticles() {
    this.articleService.listarArticulos().subscribe(
      (data) => {
        this.articles = data;
      }),
      (error: HttpErrorResponse) => {
        console.error(error);
      }
  }

  getAllOrders() {
    this.orderService.listarOrdenes().subscribe(
      (data) => {
        this.orders = data;
      }),
      (error: HttpErrorResponse) => {
        console.error(error);
      }
  }

  addOrder() {
    const clientId = this.orderDetail.value.clientId;

    if (!clientId) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
      return;
    }

    const selectedArticles = this.orderDetail.value.articles;

    if (!selectedArticles) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar al menos un articulo' });
      return;
    }

    const nuevaOrden: Order = {
      id: 0,
      clientId: clientId,
      articles: selectedArticles,
    };

    this.orderService.crearOrden(nuevaOrden).subscribe({
      next: () => {
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden creada exitosamente" });
        this.orderDetail.reset();
        this.getAllOrders();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al crear la orden', error);
      }
    });
  }

  updateOrders() {
    const orderId = this.orderDetail.value.id;
    const clientId = this.orderDetail.value.clientId;
    const selectedArticles = this.orderDetail.value.articles;

    if (!clientId) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
      return;
    }

    if (!selectedArticles) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un artículo' });
      return;
    }

    const ordenActualizada: Order = {
      id: orderId,
      clientId: clientId,
      articles: selectedArticles.map((id: number) => ({ id }))
    };
 
    this.orderService.actualizarOrden(ordenActualizada).subscribe({
      next: () => {
        debugger
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden actualizada exitosamente" });
        this.getAllOrders();
       this.orderDetail.reset();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar la orden', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar la orden' });
      }
    });
  }

  editOrder(id: number) {
    this.orderService.obtenerOrdenPorId(id).subscribe({
      next: (orden) => {
        this.orderDetail.patchValue(orden);

        const selectedArticles = orden.articles.map(a => a.id); 
        this.orderDetail.controls['articles'].setValue(selectedArticles); 
      },
      error: (error) => {
        console.error("Error al obtener la orden: ", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener la orden' });
      }
    });
  }

  showConfirmation(orden: Order) {
    this.orderToDelete = orden;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar la orden"
    });
  }

  confirmDelete() {
    if (this.orderToDelete && this.orderToDelete.id) {
      this.deleteOrden(this.orderToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteOrden(id: number) {
    this.orderService.eliminarOrden(id).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Orden eliminada exitosamente' });
        this.getAllOrders();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar la orden', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar la orden' });
      }
    });
  }

}
