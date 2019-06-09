import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-dialog-overview',
    templateUrl: './dodaj.component.html',
    styleUrls: ['./dodaj.component.scss']
})
export class DodajComponent implements OnInit {
    ngOnInit() {}
    constructor(
        public dialogRef: MatDialogRef<DodajComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    onNoClick(): void {
        this.dialogRef.close();
    }

  dodajPark() {

  }
}
