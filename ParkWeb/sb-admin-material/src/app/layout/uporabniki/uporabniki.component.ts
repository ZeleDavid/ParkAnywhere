import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource, MatDialogRef } from '@angular/material';
import {Observable} from 'rxjs';
import {map, filter, switchMap, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import * as _ from 'lodash';
import {DodajUComponent} from '../dodajU/dodajU.component';
import * as firebase from 'firebase';

interface User {
  email: string;
}

@Component({
    selector: 'app-tables',
    templateUrl: './uporabniki.component.html',
    styleUrls: ['./uporabniki.component.scss']
})
export class UporabnikiComponent implements OnInit {
    displayedColumns = ['email', 'izbrisi'];
    dataSource: MatTableDataSource<User>;
    users$: Observable<User[]>;
    animal: string;
    name: string;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(private http: HttpClient, public dialog: MatDialog) {
      let userji: User[] = new Array();
      this.http
        .get('http://45.77.58.205:8000/parkchain/users')
        .pipe(
          map(data => _.values(data)),
          tap(users => {this.dataSource = new MatTableDataSource(users[0]); this.dataSource.paginator = this.paginator;
             this.dataSource.sort = this.sort; } )
        )
        .subscribe(users => userji = users);
    }

    ngOnInit() {


    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

  openDialog(): void {
    const dialogRef = this.dialog.open(DodajUComponent, {
      width: '300px',
      data: { name: 'ime', animal: this.animal }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.animal = result;
    });
  }

  izbrisi(ime: any) {
    if (confirm('Res želite izbrisati uporabnika z emailom: ' + ime + '?')) {
      console.log('Implement delete functionality here');
    }
  }
}

