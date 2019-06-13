import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {MatButtonModule, MatSelectModule, MatSortModule, MatTableModule} from '@angular/material';
import { MatFormFieldModule, MatPaginatorModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { MatDialogModule } from '@angular/material';

import { TablesRoutingModule } from './tables-routing.module';
import { TablesComponent } from './tables.component';
import {DodajComponent} from '../dodaj/dodaj.component';
import {FormsModule} from '@angular/forms';
// import {FlexModule} from '@angular/flex-layout';

@NgModule({
  imports: [
    CommonModule,
    TablesRoutingModule,
    MatTableModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatInputModule,
    MatButtonModule,
    MatSortModule,
    MatDialogModule,
    FormsModule,
    MatSelectModule,
    // FlexModule
  ],
    declarations: [TablesComponent,
      DodajComponent],
  entryComponents: [DodajComponent],
  })
export class TablesModule {}
