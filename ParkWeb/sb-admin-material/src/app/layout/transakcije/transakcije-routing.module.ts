import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TransakcijeComponent } from './transakcije.component';

const routes: Routes = [
    {
        path: '',
        component: TransakcijeComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TransakcijeRoutingModule {}
