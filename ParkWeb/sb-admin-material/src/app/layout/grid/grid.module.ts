import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule} from '@angular/material';
import { GridRoutingModule } from './grid-routing.module';
import { GridComponent } from './grid.component';

@NgModule({
  imports: [
    CommonModule,
    GridRoutingModule,
    MatCardModule,
    FlexLayoutModule.withConfig({addFlexToParent: false}),
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule
  ],
    declarations: [GridComponent]
})
export class GridModule {}
