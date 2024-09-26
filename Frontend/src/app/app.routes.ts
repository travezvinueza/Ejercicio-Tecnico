import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { OrdenComponent } from './components/orden/orden.component';
import { ArticuloComponent } from './components/articulo/articulo.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Página de inicio clientes'
    },
    {
        path: 'orden',
        component: OrdenComponent,
        title: 'Página de ordenes'
    },
    {
        path: 'articulo',
        component: ArticuloComponent,
        title: 'Página de articulos'
    },
   
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];
