import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Articulo } from '../../interfaces/articulo';
import { Orden } from '../../interfaces/orden';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ArticuloService } from '../../services/articulo.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { OrdenService } from '../../services/orden.service';
import { RouterModule } from '@angular/router';

declare var $: any;

@Component({
  selector: 'app-articulo',
  standalone: true,
  imports: [CommonModule, ToastModule, ReactiveFormsModule, RouterModule],
  templateUrl: './articulo.component.html',
  styleUrl: './articulo.component.css'
})
export class ArticuloComponent implements OnInit {

  articulos: Articulo[] = [];
  ordenes: Orden[] = [];
  articuloDetalle !: FormGroup;
  articuloToDelete: Articulo | undefined;

  constructor(
    private articuloService: ArticuloService,
    private ordenService: OrdenService,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllArticulos();
    this.getAllOrdenes();
    this.articuloDetalle = this.formBuilder.group({
      id: [''],
      codigo: [''],
      nombre: [''],
      precioUnitario: [''],
      ordenId: ['']
    });
  }

  //metodo para darle movimiento al modal
  ngAfterViewInit() {
    $('.modal-dialog').draggable({
      handle: ".modal-header"
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

  getAllArticulos() {
    this.articuloService.listarArticulos().subscribe({
      next: (data: Articulo[]) => {
        this.articulos = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al obtener las ordenes", error);
      }
    });
  }


  editArticulo(id: number) {
    this.articuloService.obtenerArticuloPorId(id).subscribe({
      next: (articulo: Articulo) => {
        this.articuloDetalle.patchValue({
          id: articulo.id,
          codigo: articulo.codigo,
          nombre: articulo.nombre,
          precioUnitario: articulo.precioUnitario,
          ordenId: articulo.ordenId
        });
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error("Error al obtenerel articulo: ", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener articulo' });
      }
    });
  }

  crearArticulo() {
    const articulo: Articulo = this.articuloDetalle.value;

    this.articuloService.crearArticulo(articulo).subscribe({
      next: (nuevoArticulo) => {
        this.articulos.push(nuevoArticulo);
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Artículo creado exitosamente.' });
        this.articuloDetalle.reset();
        this.getAllArticulos();
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al crear el artículo", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo crear el artículo por que ya existe' });
      }
    });
  }

  actualizarArticulo() {
    const articulo: Articulo = this.articuloDetalle.value;

    this.articuloService.actualizarArticulo(articulo).subscribe({
      next: (articuloActualizado) => {
        const index = this.articulos.findIndex(a => a.id === articuloActualizado.id);
        if (index !== -1) {
          this.articulos[index] = articuloActualizado;
        }
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Artículo actualizado exitosamente.' });
        this.articuloDetalle.reset();
        this.getAllArticulos();
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al actualizar el artículo", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo actualizar el artículo.' });
      }
    });
  }

  showConfirmation(articulo: Articulo) {
    this.articuloToDelete = articulo;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar el articulo"
    });
  }

  confirmDelete() {
    if (this.articuloToDelete && this.articuloToDelete.id) {
      this.deleteArticulo(this.articuloToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteArticulo(id: number) {
    this.articuloService.eliminarArticulo(id).subscribe({
      next: () => {
          this.msgService.add({ severity: 'success', summary: 'Exito', detail: 'Articulo eliminado' });
          this.getAllArticulos();
          this.cdr.detectChanges();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar el articulo', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error articulo' });
      }
    });
  }

}
