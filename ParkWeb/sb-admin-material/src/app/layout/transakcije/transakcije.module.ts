import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatTableModule } from '@angular/material';
import { MatFormFieldModule, MatPaginatorModule } from '@angular/material';
import { MatInputModule } from '@angular/material';

import { TransakcijeRoutingModule } from './transakcije-routing.module';
import { TransakcijeComponent } from './transakcije.component';

@NgModule({
    imports: [
        CommonModule,
        TransakcijeRoutingModule,
        MatTableModule,
        MatFormFieldModule,
        MatPaginatorModule,
        MatInputModule
    ],
    declarations: [TransakcijeComponent]
})
export class TransakcijeModule {}
