import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatFormFieldControl} from '@angular/material';

@Component({
    selector: 'app-dialog-overview',
    templateUrl: './dodajU.component.html',
    styleUrls: ['./dodajU.component.scss']
})
export class DodajUComponent implements OnInit {
    checked = false;

    ngOnInit() {}
    constructor(
        public dialogRef: MatDialogRef<DodajUComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    onNoClick(): void {
        this.dialogRef.close();
    }
}
