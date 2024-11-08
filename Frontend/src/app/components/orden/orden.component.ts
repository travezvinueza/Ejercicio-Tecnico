import { ArticleService } from '../../services/article.service';
import { ClientService } from '../../services/client.service';
import { Order } from '../../interfaces/order';
import { OrderService } from '../../services/order.service';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { Client } from '../../interfaces/client';
import { RouterModule } from '@angular/router';
import { OrderArticle } from '../../interfaces/orderArticle';
import { Article } from '../../interfaces/article';

declare var $: any;

@Component({
  selector: 'app-orden',
  standalone: true,
  imports: [CommonModule, ToastModule, ReactiveFormsModule, RouterModule, FormsModule],
  templateUrl: './orden.component.html',
  styleUrl: './orden.component.css'
})
export class OrdenComponent implements OnInit {

  orders: Order[] = [];
  clients: Client[] = [];
  articles: Article[] = [];
  orderDetail !: FormGroup;
  orderToDelete: Order | undefined;

  cartItems: OrderArticle[] = [];
  selectedArticleId: number | undefined;

  constructor(
    private orderService: OrderService,
    private clientService: ClientService,
    private articleService: ArticleService,
    private formBuilder: FormBuilder,
    public msgService: MessageService,
    private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.getAllOrders();
    this.getAllClients();
    this.getAllArticles();
    this.orderDetail = this.formBuilder.group({
      id: [''],
      // code: [''],
      // date: [''],
      clientId: [''],
      cantidad: [1],
      article: [''],
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

  // Método para agregar al carrito
  addToCart() {
    console.log('Método addToCart llamado'); // Mensaje de depuración
    console.log(this.articles); // Verifica si hay artículos disponibles

    this.selectedArticleId = this.orderDetail.value.article; // Obtén el artículo seleccionado del formulario
    console.log('ID del artículo seleccionado:', this.selectedArticleId); // Mensaje de depuración
    const cantidad = this.orderDetail.value.cantidad; // Usa el valor de cantidad desde el formulario
    console.log('Cantidad seleccionada:', cantidad); // Mensaje de depuración

    if (this.selectedArticleId) {
      // Busca el artículo usando Number() para evitar problemas de tipo
      const selectedArticle = this.articles.find(article => article.id === Number(this.selectedArticleId));
      console.log('Artículo encontrado:', selectedArticle); // Verifica si se encontró el artículo

      if (selectedArticle) {
        // Verifica si hay stock disponible
        if (selectedArticle.stock >= cantidad) {
          const existingItem = this.cartItems.find(item => item.articleId === selectedArticle.id);

          if (existingItem) {
            console.log('Artículo ya en el carrito, actualizando cantidad'); // Mensaje de depuración
            existingItem.quantity += cantidad; // Actualizamos la cantidad
            existingItem.totalPrice = existingItem.quantity * existingItem.unitPrice;
          } else {
            console.log('Agregando nuevo artículo al carrito'); // Mensaje de depuración
            this.cartItems.push({
              articleId: selectedArticle.id,
              articleName: selectedArticle.name,
              unitPrice: selectedArticle.unitPrice,
              quantity: cantidad,
              totalPrice: cantidad * selectedArticle.unitPrice
            });
          }
          this.cdr.detectChanges();
          console.log(this.cartItems); // Estado del carrito

          // Actualizar stock después de agregar al carrito
          selectedArticle.stock -= cantidad; // Decrementar el stock
        } else {
          this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Artículo en stock no disponible' });
        }
      } else {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Artículo no encontrado' });
      }

      // Reiniciamos el formulario
      this.orderDetail.patchValue({ article: '', cantidad: 1 }); // Reiniciamos los controles
    } else {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Seleccione un artículo' });
    }
  }

  increaseQuantity(item: OrderArticle) {
    item.quantity++;
    item.totalPrice = item.quantity * item.unitPrice;
  }

  decreaseQuantity(item: OrderArticle) {
    if (item.quantity > 1) {
      item.quantity--;
      item.totalPrice = item.quantity * item.unitPrice;
    }
  }

  removeItem(item: OrderArticle) {
    this.cartItems = this.cartItems.filter(cartItem => cartItem !== item);
  }

  addOrder() {
    const clientId = this.orderDetail.value.clientId;

    if (!clientId) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
      return;
    }

    if (this.cartItems.length === 0) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe agregar artículos' });
      return;
    }

    const nuevaOrden: Order = {
      id: 0,
      clientId: this.orderDetail.value.clientId,
      orderArticles: this.cartItems, // Enlazamos los artículos seleccionados
    };

    this.orderService.crearOrden(nuevaOrden).subscribe({
      next: () => {
        this.msgService.add({ severity: 'info', summary: "Éxito", detail: "Orden creada exitosamente" });
        this.orderDetail.reset();
        this.cartItems = [];
        this.getAllOrders();
      },
      error: (error: HttpErrorResponse) => {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'No hay suficientes artículos' });
        console.error(error);
      }
    });
  }

 updateOrders() {
    const orderId = this.orderDetail.value.id; // ID de la orden a actualizar
    const clientId = this.orderDetail.value.clientId; // ID del cliente
    const selectedArticles = this.cartItems; // Artículos en el carrito

    if (!clientId) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe seleccionar un cliente' });
      return;
    }

    if (selectedArticles.length === 0) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Debe agregar artículos' });
      return;
    }

    // Crear el objeto de la orden actualizada
    const updatedOrder: Order = {
      id: orderId,
      clientId: clientId,
      orderArticles: selectedArticles // Artículos actualizados en la orden
    };

    // Llamar al servicio para actualizar la orden
    this.orderService.actualizarOrden(updatedOrder).subscribe({
      next: () => {
        this.msgService.add({ severity: 'info', summary: 'Éxito', detail: 'Orden actualizada exitosamente' });
        this.orderDetail.reset(); // Reiniciar el formulario
        this.cartItems = []; // Limpiar el carrito
        this.getAllOrders(); // Recargar todas las órdenes
      },
      error: (error: HttpErrorResponse) => {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo actualizar la orden' });
        console.error('Error al actualizar la orden:', error);
      }
    });
}

editOrder(id: number) {
  this.orderService.obtenerOrdenPorId(id).subscribe({
    next: (orden) => {
      // Rellenar los detalles de la orden (cliente, etc.)
      this.orderDetail.patchValue({
        id: orden.id,
        clientId: orden.clientId
      });

      // Rellenar el carrito con los artículos ya existentes en la orden
      this.cartItems = orden.orderArticles.map(article => ({
        articleId: article.articleId,
        articleName: article.articleName,
        unitPrice: article.unitPrice,
        quantity: article.quantity,
        totalPrice: article.totalPrice
      }));

      console.log("Carrito actualizado con los artículos de la orden", this.cartItems);
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
