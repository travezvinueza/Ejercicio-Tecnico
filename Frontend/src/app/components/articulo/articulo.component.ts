import { Component, OnInit } from '@angular/core';
import { Article } from '../../interfaces/article';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ArticleService } from '../../services/article.service';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
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

  articles: Article[] = [];
  articleDetail !: FormGroup;
  articleToDelete: Article | undefined;

  constructor(
    private articleService: ArticleService,
    private formBuilder: FormBuilder,
    public msgService: MessageService) { }

  ngOnInit(): void {
    this.getAllArticles();
    this.articleDetail = this.formBuilder.group({
      id: [''],
      code: [''],
      name: [''],
      unitPrice: [''],
    });
  }

  //metodo para darle movimiento al modal
  ngAfterViewInit() {
    $('.modal-dialog').draggable({
      handle: ".modal-header"
    });
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

  editArticle(id: number) {
    this.articleService.obtenerArticuloPorId(id).subscribe({
      next: (articulo) => {
        this.articleDetail.patchValue(articulo);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error);
      }
    });
  }

  createArticle() {
    const articulo: Article = this.articleDetail.value;

    this.articleService.crearArticulo(articulo).subscribe({
      next: (nuevoArticulo) => {
        this.articles.push(nuevoArticulo);
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Artículo creado exitosamente.' });
        this.articleDetail.reset();
        this.getAllArticles();
      },
      error: (error: HttpErrorResponse) => {
        console.error("Error al crear el artículo", error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo crear el artículo por que ya existe' });
      }
    });
  }

  updateArticle() {
    const articleUpdate: Article = {
      id: this.articleDetail.value.id,
      code: this.articleDetail.value.code,
      name: this.articleDetail.value.name,
      unitPrice: this.articleDetail.value.unitPrice
    };
    this.articleService.actualizarArticulo(articleUpdate).subscribe({
      next: () => {
        this.msgService.add({ severity: 'warn', summary: "Éxito", detail: "Articulo actualizado" });
        this.articleDetail.reset();
        this.getAllArticles();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al actualizar el articulo:', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar el articulo' });
      }
    });
  }

  showConfirmation(articulo: Article) {
    this.articleToDelete = articulo;
    this.msgService.add({
      key: 'confirm',
      sticky: true,
      severity: 'info',
      summary: 'Confirmar Eliminacion',
      detail: "¿Estás seguro de eliminar el articulo"
    });
  }

  confirmDelete() {
    if (this.articleToDelete && this.articleToDelete.id) {
      this.deleteArticulo(this.articleToDelete.id);
      this.msgService.clear('confirm');
    }
  }

  deleteArticulo(id: number) {
    this.articleService.eliminarArticulo(id).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Exito', detail: 'Articulo eliminado' });
        this.getAllArticles();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error al eliminar el articulo', error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error articulo' });
      }
    });
  }

}
