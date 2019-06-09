import { Component, OnInit, ViewChild } from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource, MatDialogRef } from '@angular/material';
import {Observable} from 'rxjs';
import {map, filter, switchMap, tap} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import * as _ from 'lodash';
import {DodajUComponent} from '../dodajU/dodajU.component';
import * as firebase from 'firebase';

interface User {
  uid: string;
  email: string;
  password: string;
  walletAddress: string;
}

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'Bearer ' + firebase.auth().currentUser.getIdToken(),
  })
};

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
      this.naloziPodatke();
    }

    ngOnInit() {
    }

    naloziPodatke() {
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
      data: { email: '', password: '', walletAddress: '' }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      console.log('The dialog was closed');
      this.dodaj(result)
        .subscribe( odg => this.naloziPodatke());
    });
  }

  dodaj (data: Object): Observable<Object> {
    return this.http.post<Object>('http://45.77.58.205:8000/parkchain/user', data, httpOptions)
      .pipe(
      );
    console.log(data);
    console.log(httpOptions);
  }

  brisiUsera (uid: number): Observable<{}> {
    const url = 'http://45.77.58.205:8000/parkchain/user/' + uid;
    return this.http.delete(url, httpOptions)
      .pipe(
      );
  }

  izbrisi(obj: any) {
    if (confirm('Res želite izbrisati uporabnika z emailom: ' + obj.email + '?')) {
      console.log(obj);
      this.brisiUsera(obj.uid).subscribe(odg => this.naloziPodatke());
    }
  }
}

