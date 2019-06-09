import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {MatButtonModule, MatCheckboxModule, MatSlideToggleModule, MatSortModule, MatTableModule} from '@angular/material';
import { MatFormFieldModule, MatPaginatorModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { MatDialogModule } from '@angular/material';

import { UporabnikiRoutingModule } from './uporabniki-routing.module';
import { UporabnikiComponent } from './uporabniki.component';
import {DodajUComponent} from '../dodajU/dodajU.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    UporabnikiRoutingModule,
    MatTableModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatInputModule,
    MatButtonModule,
    MatSortModule,
    MatDialogModule,
    FormsModule,
    MatCheckboxModule,
    MatSlideToggleModule
  ],
    declarations: [UporabnikiComponent,
      DodajUComponent],
  entryComponents: [DodajUComponent],
  })
export class UporabnikiModule {}
